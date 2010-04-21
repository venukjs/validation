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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.ConfigReferenceValue;
import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucdescription.ReferenceValue;
import autosar3x.ecucparameterdef.ChoiceReferenceParamDef;
import autosar3x.ecucparameterdef.ConfigReference;
import autosar3x.ecucparameterdef.ForeignReferenceParamDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;
import autosar3x.ecucparameterdef.ReferenceParamDef;

public class ReferenceValueConstraint extends AbstractConfigReferenceValueConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof ReferenceValue;

		ReferenceValue referenceValue = (ReferenceValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, referenceValue);
		if (status.isOK()) {
			// the validation of the value requires valid access to the IntegerParamDef
			status = validateValue(ctx, referenceValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, ConfigReferenceValue configReferenceValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, configReferenceValue);
		if (status.isOK()) {
			ConfigReference configReferenceDef = configReferenceValue.getDefinition();
			if (!(configReferenceDef instanceof ReferenceParamDef || configReferenceDef instanceof ChoiceReferenceParamDef || configReferenceDef instanceof ForeignReferenceParamDef)) {
				status = ctx.createFailureStatus("definition not of type ReferenceParamDef, ChoiceReferenceParamDef or ForeignReferenceParamDef");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, ReferenceValue referenceValue) {
		// default
		IStatus status = ctx.createSuccessStatus();

		if (false == referenceValue.eIsSet(EcucdescriptionPackage.eINSTANCE.getReferenceValue_Value())) {
			status = ctx.createFailureStatus("value not available");
		} else if (null == referenceValue.getValue()) {
			status = ctx.createFailureStatus("value not available");
		} else {
			ConfigReference configReference = referenceValue.getDefinition();
			if (configReference instanceof ChoiceReferenceParamDef) {
				status = validateReferenceValue_ChoiceReference(ctx, referenceValue);
			} else if (configReference instanceof ReferenceParamDef) {
				status = validateReferenceValue_Reference(ctx, referenceValue);
			} else if (configReference instanceof ForeignReferenceParamDef) {
				status = validateReferenceValue_ForeignReference(ctx, referenceValue);
			}
		}

		return status;
	}

	private IStatus validateReferenceValue_Reference(IValidationContext ctx, ReferenceValue referenceValue) {
		EObject valueObject = referenceValue.getValue();
		ReferenceParamDef referenceDef = (ReferenceParamDef) referenceValue.getDefinition();
		assert null != valueObject;
		assert null != referenceDef;

		final IStatus status;

		// CHECK if value is a Container
		if (!(valueObject instanceof Container)) {
			status = ctx.createFailureStatus("value is not of Container type"); //$NON-NLS-1$
		} else {

			// CHECK if ParamConfContainerDef is available for destination
			ParamConfContainerDef containerDefFromDestination = referenceDef.getDestination();
			if (null == containerDefFromDestination || containerDefFromDestination.eIsProxy()) {
				status = ctx.createFailureStatus("no ParamConfContainerDef for destination"); //$NON-NLS-1$
			} else {
				Container container = (Container) valueObject;
				ParamConfContainerDef containerDefFromDefinition = (ParamConfContainerDef) container.getDefinition();

				if (null == containerDefFromDefinition || containerDefFromDefinition.eIsProxy()) {
					// CHECK if ParamConfContainerDef is available for container
					status = ctx.createFailureStatus("no ParamConfContainerDef for container");
				} else if (!containerDefFromDestination.equals(containerDefFromDefinition)) {
					// CHECK if ParamConfContainerDef is the same for container and destination
					status = ctx.createFailureStatus("different ParamConfContainerDef for container (" + containerDefFromDefinition.getShortName()
							+ ") and destination (" + containerDefFromDestination.getShortName() + ")");
				} else {
					status = ctx.createSuccessStatus();
				}
			}
		}

		return status;
	}

	private IStatus validateReferenceValue_ChoiceReference(IValidationContext ctx, ReferenceValue referenceValue) {
		// assert that the value is available
		EObject valueObject = referenceValue.getValue();
		ChoiceReferenceParamDef choiceReferenceDef = (ChoiceReferenceParamDef) referenceValue.getDefinition();
		assert null != valueObject;
		assert null != choiceReferenceDef;

		IStatus status = ctx.createSuccessStatus();

		// CHECK if value is a Container
		if (!(valueObject instanceof Container)) {
			status = ctx.createFailureStatus("value is not of Container type"); //$NON-NLS-1$
		} else {
			// CHECK if destination available
			EList<ParamConfContainerDef> containerDefList = choiceReferenceDef.getDestinations();
			if (null == containerDefList || 0 == containerDefList.size()) {
				status = ctx.createFailureStatus("destination not available"); //$NON-NLS-1$
			} else {
				// CHECK if the value Container is included in the destination of the reference parameter
				Container container = (Container) valueObject;
				ParamConfContainerDef containerDefFromDefinition = (ParamConfContainerDef) container.getDefinition();

				if (false == containerDefList.contains(containerDefFromDefinition)) {
					String tmpStr = ""; //$NON-NLS-1$
					for (int i = 0; i < containerDefList.size(); i++) {
						tmpStr = tmpStr + containerDefList.get(i).getShortName() + ","; //$NON-NLS-1$
					}

					status = ctx
							.createFailureStatus("the ParamConfContainerDef can not be found in the ParamConfContainerDef of the destinations (" + tmpStr //$NON-NLS-1$
									+ ")"); //$NON-NLS-1$
				}
			}
		}
		return status;
	}

	private IStatus validateReferenceValue_ForeignReference(IValidationContext ctx, ReferenceValue referenceValue) {
		EObject valueObject = referenceValue.getValue();
		ForeignReferenceParamDef foreignReferenceDef = (ForeignReferenceParamDef) referenceValue.getDefinition();
		assert null != valueObject;
		assert null != foreignReferenceDef;

		final IStatus status;

		// get value type
		String valueClassName = valueObject.eClass().getName();

		// CHECK if destinationType available
		String destinationType = foreignReferenceDef.getDestinationType();
		if (null == destinationType || destinationType.length() == 0) {
			status = ctx.createFailureStatus("foreign destinationType not available"); //$NON-NLS-1$
		} else {
			// get value class
			EClass clas = valueObject.eClass();

			if (valueClassName.equals(destinationType)) {
				// no error found it is the right destination type
				status = ctx.createSuccessStatus();
			} else {
				// get all super types of value class and check if destination type is a
				// super type of value
				EList<EClass> superTypes = clas.getESuperTypes();
				Boolean destinationTypeInSuperTypes = false;
				for (int i = 0; i < superTypes.size(); i++) {
					EClass c = superTypes.get(i);
					// check if destination type is a super type of value class
					if (c.getName().equals(destinationType)) {
						destinationTypeInSuperTypes = true;
					}
				}
				if (destinationTypeInSuperTypes) {
					status = ctx.createSuccessStatus();
				} else {
					status = ctx.createFailureStatus("type of value don't correspond with DestinationType");
				}
			}

		}
		return status;
	}

}
