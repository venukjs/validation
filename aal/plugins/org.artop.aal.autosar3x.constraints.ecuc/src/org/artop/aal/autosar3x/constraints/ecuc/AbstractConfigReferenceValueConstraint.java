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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.ConfigReferenceValue;
import autosar3x.ecucdescription.EcucdescriptionPackage;

public abstract class AbstractConfigReferenceValueConstraint extends AbstractModelConstraintWithPrecondition {

	protected IStatus validateDefinitionRef(IValidationContext ctx, ConfigReferenceValue configReferenceValue) {
		// check if definition is set and available
		final IStatus status;
		if (false == configReferenceValue.eIsSet(EcucdescriptionPackage.eINSTANCE.getParameterValue_Definition())) {
			status = ctx.createFailureStatus("definition reference not set");
		} else if (configReferenceValue.getDefinition().eIsProxy()) {
			status = ctx.createFailureStatus("reference to definition could not be resolved");
		} else {
			status = ctx.createSuccessStatus();
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
