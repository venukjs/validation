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
package org.artop.aal.autosar3x.validation.ecuc;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ConfigParameter;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public abstract class AbstractParameterValueConstraint extends AbstractModelConstraint {

	protected IStatus validateDefinitionRef(IValidationContext ctx, ParameterValue parameterValue) {
		// check if definition is set and available
		final IStatus status;
		if (false == parameterValue.eIsSet(EcucdescriptionPackage.eINSTANCE.getParameterValue_Definition())) {
			status = ctx.createFailureStatus("definition reference not set");
		} else if (parameterValue.getDefinition().eIsProxy()) {
			status = ctx.createFailureStatus("reference to definition could not be resolved");
		} else {
			status = validateContainmentStructure(ctx, parameterValue);
		}
		return status;
	}

	private IStatus validateContainmentStructure(IValidationContext ctx, ParameterValue parameterValue) {
		final IStatus status;

		EObject parent = parameterValue.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus("element has no parent");
		} else {
			ConfigParameter configParameter = parameterValue.getDefinition();
			if (parent instanceof Container) {
				// the current Container is contained in another Container
				Container parentContainer = (Container) parent;
				ContainerDef parentContainerDef = parentContainer.getDefinition();

				if (parentContainerDef instanceof ParamConfContainerDef) {
					// the parent containers definition is a ParamConfContainerDef
					ParamConfContainerDef parentParamConfContainerDef = (ParamConfContainerDef) parentContainerDef;
					if (parentParamConfContainerDef.getParameters().contains(configParameter)) {
						status = ctx.createSuccessStatus(); // reference is valid
					} else {
						status = ctx.createFailureStatus("containement problem: parameter value with definition " + configParameter.getShortName()
								+ " not allowed here");
					}
				} else if (parentContainerDef instanceof ChoiceContainerDef) {
					// TODO: create testcase
					status = ctx.createFailureStatus("ParameterValue not allowed in choice containers");
				} else {
					status = ctx.createSuccessStatus();
				}
			} else {
				// we only expect an object of type Container
				status = ctx.createSuccessStatus();
			}

		}
		return status;

	}

}
