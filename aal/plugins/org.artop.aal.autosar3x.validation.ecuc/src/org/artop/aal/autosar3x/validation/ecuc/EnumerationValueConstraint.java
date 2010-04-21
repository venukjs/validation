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

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.EnumerationValue;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.EnumerationLiteralDef;
import autosar3x.ecucparameterdef.EnumerationParamDef;

public class EnumerationValueConstraint extends AbstractParameterValueConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof EnumerationValue;

		EnumerationValue enumerationValue = (EnumerationValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, enumerationValue);
		if (status.isOK()) {
			// the validation of the value requires valid access to the IntegerParamDef
			status = validateValue(ctx, enumerationValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, ParameterValue parameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, parameterValue);
		if (status.isOK()) {
			if (!(parameterValue.getDefinition() instanceof EnumerationParamDef)) {
				status = ctx.createFailureStatus("definition not of type EnumerationParamDef");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, EnumerationValue enumerationValue) {
		// default
		IStatus status = ctx.createSuccessStatus();

		if (false == enumerationValue.isSetValue()) {
			status = ctx.createFailureStatus("no value found");
		} else if (null == enumerationValue.getValue()) {
			status = ctx.createFailureStatus("value is null");
		} else {
			EnumerationParamDef enumerationParamDef = (EnumerationParamDef) enumerationValue.getDefinition();
			String value = enumerationValue.getValue();

			if (0 == value.length()) {
				status = ctx.createFailureStatus("value has empty length");
			} else {
				List<EnumerationLiteralDef> literalList = enumerationParamDef.getLiterals();
				boolean valueFound = false;

				for (int i = 0; i < literalList.size(); i++) {
					if (literalList.get(i).getShortName().equals(value)) {
						valueFound = true;
						break; // leave the for loop
					}
				}

				if (false == valueFound) {
					status = ctx.createFailureStatus("value not included in definition literals");
				}
			}
		}

		return status;
	}

}
