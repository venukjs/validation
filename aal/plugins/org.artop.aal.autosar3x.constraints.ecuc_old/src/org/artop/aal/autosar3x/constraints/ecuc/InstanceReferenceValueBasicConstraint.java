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

import org.artop.aal.autosar3x.constraints.ecuc.internal.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.ConfigReferenceValue;
import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucdescription.InstanceReferenceValue;
import autosar3x.ecucdescription.instanceref.InstanceReferenceValueValue;
import autosar3x.ecucdescription.instanceref.InstancerefPackage;
import autosar3x.ecucparameterdef.ConfigReference;
import autosar3x.ecucparameterdef.InstanceReferenceParamDef;
import autosar3x.genericstructure.infrastructure.identifiable.Identifiable;

public class InstanceReferenceValueBasicConstraint extends AbstractConfigReferenceValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof InstanceReferenceValue;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		InstanceReferenceValue instanceReferenceValue = (InstanceReferenceValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, instanceReferenceValue);
		if (status.isOK()) {
			// the validation of the value requires valid access to the IntegerParamDef
			status = validateValue(ctx, instanceReferenceValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, ConfigReferenceValue configReferenceValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, configReferenceValue);
		if (status.isOK()) {
			ConfigReference configReferenceDef = configReferenceValue.getDefinition();
			if (!(configReferenceDef instanceof InstanceReferenceParamDef)) {
				status = ctx
						.createFailureStatus("definition not of type InstanceReferenceParamDef, ChoiceReferenceParamDef or ForeignReferenceParamDef");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, InstanceReferenceValue instanceReferenceValue) {
		// default
		final IStatus status;

		if (false == instanceReferenceValue.eIsSet(EcucdescriptionPackage.eINSTANCE.getInstanceReferenceValue_Value())) {
			status = ctx.createFailureStatus("InstanceReferenceValueValue not found "); //$NON-NLS-1$
		} else {
			// CHECK if value of InstanceReferenceValue_value available
			InstanceReferenceValueValue instanceReferenceValueValue = instanceReferenceValue.getValue();
			if (null == instanceReferenceValueValue.getValue()) {
				status = ctx.createFailureStatus("value of InstanceReferenceValueValue not found"); //$NON-NLS-1$
			} else {
				MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, "Valiation of InstanceRef", null);
				multiStatus.add(valiateInstanceReferenceTargetDestination(ctx, instanceReferenceValue, instanceReferenceValueValue));
				multiStatus.add(valiateInstanceReferenceContextDestination(ctx, instanceReferenceValue, instanceReferenceValueValue));
				status = multiStatus;
			}
		}

		return status;
	}

	private IStatus valiateInstanceReferenceTargetDestination(IValidationContext ctx, InstanceReferenceValue instanceReferenceValue,
			InstanceReferenceValueValue instanceReferenceValueValue) {
		final IStatus status;

		assert null != instanceReferenceValue;
		assert null != instanceReferenceValueValue;

		if (!instanceReferenceValueValue.eIsSet(InstancerefPackage.eINSTANCE.getInstanceReferenceValueValue_Value())) {
			// TODO: create testcase
			status = ctx.createFailureStatus("target is not set");
		} else {
			InstanceReferenceParamDef referenceDef = (InstanceReferenceParamDef) instanceReferenceValue.getDefinition();
			assert null != referenceDef; // this should have been checked before
			String destinationTypeName = referenceDef.getDestinationType();

			EObject valueObject = instanceReferenceValueValue.getValue();
			if (null == valueObject) {
				// TODO: create testcase
				status = ctx.createFailureStatus("target is not set");
			} else if (valueObject.eIsProxy()) {
				// TODO: create testcase
				status = ctx.createFailureStatus("target could not be resolved");
			} else if (null == destinationTypeName || destinationTypeName.length() == 0) {
				status = ctx.createFailureStatus("target destinationType not available"); //$NON-NLS-1$
			} else if (!isInstanceOfDestinationType(valueObject, destinationTypeName)) {
				status = ctx.createFailureStatus("type of value don't correspond with DestinationType");
			} else {
				status = ctx.createSuccessStatus();
			}
		}
		return status;

	}

	private IStatus valiateInstanceReferenceContextDestination(IValidationContext ctx, InstanceReferenceValue instanceReferenceValue,
			InstanceReferenceValueValue instanceReferenceValueValue) {
		// TODO: CAUTION this algorithm only works in case no inheritance is used

		final IStatus status;

		// Assert that definition of InstanceReferenceValue is available
		InstanceReferenceParamDef referenceDef = (InstanceReferenceParamDef) instanceReferenceValue.getDefinition();

		// CHECK if context of InstanceReferenceValue_value is compatible to destinationContext
		EList<Identifiable> contextList = instanceReferenceValueValue.getContexts();
		String destinationContext = referenceDef.getDestinationContext();

		// CHECK if destinationContext available
		if (null != destinationContext && 0 != destinationContext.length()) {

			StringBuffer contextBuffer = new StringBuffer();

			// CHECK if value context available
			if (contextList.size() > 0) {
				// convert value context to a String, each item separated by a space
				for (int i = 0; i < contextList.size(); i++) {
					contextBuffer.append(contextList.get(i).eClass().getName());
					contextBuffer.append(" "); //$NON-NLS-1$
				}
			}

			// CHECK if value context String matches the definitionContext regular expression
			String destinationContentRegex = getDestinationContextRegex(destinationContext);
			if (!contextBuffer.toString().matches(destinationContentRegex)) {
				status = ctx.createFailureStatus("value context does not match the defined destinationContext"); //$NON-NLS-1$
			} else {
				status = ctx.createSuccessStatus();
			}

		} else if (0 != contextList.size()) {
			status = ctx.createFailureStatus("value context available but no destinationContext specified"); //$NON-NLS-1$
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}

	private String getDestinationContextRegex(String destinationContext) {
		String destinationContentRegex = new String();

		String[] contextList = destinationContext.split("\\s+"); //$NON-NLS-1$

		for (String element : contextList) {
			String item = element;
			if (item.endsWith("*")) { //$NON-NLS-1$
				item = "(".concat(item); //$NON-NLS-1$
				item = item.substring(0, item.length() - 1);
				item = item.concat(" )*"); //$NON-NLS-1$
			} else {
				item = "(".concat(item); //$NON-NLS-1$
				item = item.concat(" )"); //$NON-NLS-1$
			}

			destinationContentRegex = destinationContentRegex + item;
		}

		return destinationContentRegex;
	}
}
