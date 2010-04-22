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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.ConfigReferenceValue;
import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ConfigReference;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public abstract class AbstractConfigReferenceValueConstraint extends AbstractModelConstraint {

	protected IStatus validateDefinitionRef(IValidationContext ctx, ConfigReferenceValue configReferenceValue) {
		// check if definition is set and available
		final IStatus status;
		if (false == configReferenceValue.eIsSet(EcucdescriptionPackage.eINSTANCE.getParameterValue_Definition())) {
			status = ctx.createFailureStatus("definition reference not set");
		} else if (configReferenceValue.getDefinition().eIsProxy()) {
			status = ctx.createFailureStatus("reference to definition could not be resolved");
		} else {
			status = validateContainmentStructure(ctx, configReferenceValue);
		}
		return status;
	}

	private IStatus validateContainmentStructure(IValidationContext ctx, ConfigReferenceValue configReferenceValue) {
		final IStatus status;

		EObject parent = configReferenceValue.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus("element has no parent");
		} else {
			ConfigReference configReference = configReferenceValue.getDefinition();
			if (parent instanceof Container) {
				// the current Container is contained in another Container
				Container parentContainer = (Container) parent;
				ContainerDef parentContainerDef = parentContainer.getDefinition();

				if (parentContainerDef instanceof ParamConfContainerDef) {
					// the parent containers definition is a ParamConfContainerDef
					ParamConfContainerDef parentParamConfContainerDef = (ParamConfContainerDef) parentContainerDef;
					if (parentParamConfContainerDef.getReferences().contains(configReference)) {
						status = ctx.createSuccessStatus(); // reference is valid
					} else {
						status = ctx.createFailureStatus("containement problem: reference with definition " + configReference.getShortName()
								+ " not allowed here");
					}
				} else if (parentContainerDef instanceof ChoiceContainerDef) {
					// TODO: create testcase
					status = ctx.createFailureStatus("ReferenceValue not allowed in choice containers");
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

	protected boolean isInstanceOfDestinationType(EObject instance, String destinationTypeName) {
		boolean isInstanceOfDestinationType = false;

		EClass metaClass = instance.eClass();
		String metaClassName = metaClass.getName();

		if (metaClassName.equals(destinationTypeName)) {
			isInstanceOfDestinationType = true;
		} else {
			// get all super types of the metaClass and check if destination type is a
			// super type
			for (EClass superType : metaClass.getESuperTypes()) {
				// check if destination type is a super type of value class
				if (superType.getName().equals(destinationTypeName)) {
					isInstanceOfDestinationType = true;
					break;
				}
			}
		}
		return isInstanceOfDestinationType;
	}

}
