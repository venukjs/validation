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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.ConfigReferenceValue;
import autosar3x.ecucdescription.Container;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ConfigReference;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public class ConfigReferenceValueStructuralIntegrityConstraint extends AbstractModelConstraint {
	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof ConfigReferenceValue;

		final IStatus status;
		ConfigReferenceValue configReferenceValue = (ConfigReferenceValue) ctx.getTarget();

		EObject parent = configReferenceValue.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus("element has no parent");
		} else {
			assert parent instanceof Container;
			Container parentContainer = (Container) parent;
			status = validateStructuralIntegrity(ctx, configReferenceValue, parentContainer);
		}
		return status;

	}

	private IStatus validateStructuralIntegrity(IValidationContext ctx, ConfigReferenceValue configReferenceValue, Container parentContainer) {
		final IStatus status;

		ContainerDef parentContainerDef = parentContainer.getDefinition();
		ConfigReference configReference = configReferenceValue.getDefinition();

		if (null == configReference || configReference.eIsProxy() || null == parentContainerDef || parentContainerDef.eIsProxy()) {
			// error in the definitions are REPORTED in other constraints
			status = ctx.createSuccessStatus();
		} else if (parentContainerDef instanceof ChoiceContainerDef) {
			// TODO: create testcase
			status = ctx.createFailureStatus("ReferenceValue not allowed in choice containers");
		} else if (parentContainerDef instanceof ParamConfContainerDef) {
			// the parent containers definition is a ParamConfContainerDef
			ParamConfContainerDef parentParamConfContainerDef = (ParamConfContainerDef) parentContainerDef;
			if (EcucUtil.getAllReferencesOf(parentParamConfContainerDef).contains(configReference)) {
				status = ctx.createSuccessStatus(); // reference is valid
			} else {
				status = ctx.createFailureStatus("containement problem: reference with definition " + configReference.getShortName()
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
