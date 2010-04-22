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

import org.artop.aal.autosar3x.validation.ecuc.internal.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.ConfigReferenceValue;
import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucdescription.InstanceReferenceValue;
import autosar3x.ecucdescription.instanceref.InstanceReferenceValueValue;
import autosar3x.ecucparameterdef.ConfigReference;
import autosar3x.ecucparameterdef.InstanceReferenceParamDef;
import autosar3x.genericstructure.infrastructure.identifiable.Identifiable;

public class InstanceReferenceValueConstraint extends AbstractConfigReferenceValueConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof InstanceReferenceValue;

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

		InstanceReferenceParamDef referenceDef = (InstanceReferenceParamDef) instanceReferenceValue.getDefinition();

		// CHECK if destination type available
		String destinationType = referenceDef.getDestinationType();
		if (null == destinationType || 0 == destinationType.length()) {
			status = ctx.createFailureStatus("destinationType not found in reference definition"); //$NON-NLS-1$
		} else {

			// CHECK if value type corresponds to destinationType
			Identifiable target = instanceReferenceValueValue.getValue();
			String valueClassName = target.eClass().getName();
			if (!valueClassName.equals(destinationType)) {
				// TODO: make sure this works with supertypes
				status = ctx.createFailureStatus("type of value does not correspond with destinationType"); //$NON-NLS-1$
			} else {
				status = ctx.createSuccessStatus();
			}
		}

		return status;

	}

	private IStatus valiateInstanceReferenceContextDestination(IValidationContext ctx, InstanceReferenceValue instanceReferenceValue,
			InstanceReferenceValueValue instanceReferenceValueValue) {
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

	private boolean isAtpPrototoype(EClass eClass) {
		boolean isAtpPrototype = false;

		String stereotype = EcoreUtil.getAnnotation(eClass, "Stereotype", "Stereotype");
		if ("atpPrototype".equals(stereotype)) {
			isAtpPrototype = true;
		} else {
			// Check whether a reference exists (either in the type itself or in
			// one of its supertypes) that is stereotyped/annotated with "isOfType"
			EList<EStructuralFeature> featureList = eClass.getEAllStructuralFeatures();

			for (int j = 0; j < featureList.size(); j++) {
				stereotype = EcoreUtil.getAnnotation(featureList.get(j), "Stereotype", "Stereotype"); //$NON-NLS-1$ //$NON-NLS-2$
				if ("isOfType".equals(stereotype)) { //$NON-NLS-1$
					isAtpPrototype = true;
					// leave the inner loop
					break;
				}
			}
		}

		return isAtpPrototype;
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
