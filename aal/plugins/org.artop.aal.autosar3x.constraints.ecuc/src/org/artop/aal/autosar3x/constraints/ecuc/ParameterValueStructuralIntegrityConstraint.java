/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ConfigParameter;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public class ParameterValueStructuralIntegrityConstraint extends AbstractModelConstraintWithPrecondition {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		boolean isApplicable = false;

		if (ctx.getTarget() instanceof ParameterValue) {
			// required ECUC description objects
			ParameterValue parameterValue = (ParameterValue) ctx.getTarget();
			Container parentContainer = (Container) parameterValue.eContainer();

			if (null != parentContainer) {
				// required ECUC definition objects
				ContainerDef parentContainerDef = parentContainer.getDefinition();
				ConfigParameter configReference = parameterValue.getDefinition();
				isApplicable = null != parentContainerDef && false == parentContainerDef.eIsProxy();
				isApplicable &= null != configReference && false == configReference.eIsProxy();
			}
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		ParameterValue parameterValue = (ParameterValue) ctx.getTarget();
		Container parentContainer = (Container) parameterValue.eContainer();

		return validateStructuralIntegrity(ctx, parameterValue, parentContainer);

	}

	private IStatus validateStructuralIntegrity(IValidationContext ctx, ParameterValue parameterValue, Container parentContainer) {
		final IStatus status;

		ContainerDef parentContainerDef = parentContainer.getDefinition();
		ConfigParameter configParameter = parameterValue.getDefinition();

		if (parentContainerDef instanceof ChoiceContainerDef) {
			// TODO: create testcase
			status = ctx.createFailureStatus("ReferenceValue not allowed in choice containers");
		} else if (parentContainerDef instanceof ParamConfContainerDef) {
			// the parent containers definition is a ParamConfContainerDef
			ParamConfContainerDef parentParamConfContainerDef = (ParamConfContainerDef) parentContainerDef;
			if (EcucUtil.getAllParametersOf(parentParamConfContainerDef).contains(configParameter)) {
				status = ctx.createSuccessStatus(); // reference is valid
			} else {
				status = ctx.createFailureStatus("containement problem: parameter with definition " + configParameter.getShortName()
						+ " not allowed here");
			}

		} else {
			// in the current metamodel we only find expect ParamConfContainerDef and ChoiceContainerDef
			// The assert will warn in case of metamodel extensions
			assert false;
			status = ctx.createSuccessStatus();
		}
		return status;

	}

}
