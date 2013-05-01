/**
 * <copyright>
 * 
 * Copyright (c) Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Continental Engineering Services - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucdescription.GInstanceReferenceValue;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGInstanceReferenceValueBasicConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.validation.IValidationContext;

import autosar40.ecucdescription.EcucInstanceReferenceValue;
import autosar40.genericstructure.generaltemplateclasses.anyinstanceref.AnyInstanceRef;

public class EcucInstanceReferenceValueBasicConstraint extends AbstractGInstanceReferenceValueBasicConstraint {

	@Override
	protected IStatus doValidateValueSet(IValidationContext ctx, GInstanceReferenceValue gInstanceReferenceValue) {
		EcucInstanceReferenceValue instanceReferenceValue = (EcucInstanceReferenceValue) gInstanceReferenceValue;
		AnyInstanceRef value = instanceReferenceValue.getValue();

		return validateValueSet(ctx, instanceReferenceValue, value);
	}

	@Override
	protected EObject getTargetDestination(GInstanceReferenceValue gInstanceReferenceValue) {
		EcucInstanceReferenceValue instanceReferenceValue = (EcucInstanceReferenceValue) gInstanceReferenceValue;
		AnyInstanceRef value = instanceReferenceValue.getValue();

		return value.getTarget();
	}

	@Override
	protected EList<? extends GIdentifiable> getTargetContexts(GInstanceReferenceValue gInstanceReferenceValue) {
		EcucInstanceReferenceValue instanceReferenceValue = (EcucInstanceReferenceValue) gInstanceReferenceValue;
		AnyInstanceRef value = instanceReferenceValue.getValue();

		return value.getContextElements();
	}

	@Override
	protected String getMetaClassName(EClass eClass) {
		return ExtendedMetaData.INSTANCE.getName(eClass);
	}
}
