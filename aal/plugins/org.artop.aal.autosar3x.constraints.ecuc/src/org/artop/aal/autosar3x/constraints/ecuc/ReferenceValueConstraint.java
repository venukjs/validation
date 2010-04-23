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
import org.eclipse.emf.common.util.EList;
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

		final IStatus status;
		if (false == referenceValue.eIsSet(EcucdescriptionPackage.eINSTANCE.getReferenceValue_Value())) {
			status = ctx.createFailureStatus("value not available");
		} else {
			EObject valueObject = referenceValue.getValue();
			if (null == valueObject) {
				status = ctx.createFailureStatus("value not available");
			} else if (valueObject.eIsProxy()) {
				status = ctx.createFailureStatus("value coul not be resolved");
			} else {
				ConfigReference configReference = referenceValue.getDefinition();
				if (configReference instanceof ChoiceReferenceParamDef) {
					status = validateReferenceValue_ChoiceReference(ctx, referenceValue);
				} else if (configReference instanceof ReferenceParamDef) {
					status = validateReferenceValue_Reference(ctx, referenceValue);
				} else if (configReference instanceof ForeignReferenceParamDef) {
					status = validateReferenceValue_ForeignReference(ctx, referenceValue);
				} else {
					status = ctx.createSuccessStatus();
				}
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

		// CHECK if reference element is of correct type
		String destinationTypeName = foreignReferenceDef.getDestinationType();
		if (null == destinationTypeName || destinationTypeName.length() == 0) {
			status = ctx.createFailureStatus("foreign destinationType not available"); //$NON-NLS-1$
		} else if (!isInstanceOfDestinationType(valueObject, destinationTypeName)) {
			status = ctx.createFailureStatus("type of value don't correspond with DestinationType");
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}

}
