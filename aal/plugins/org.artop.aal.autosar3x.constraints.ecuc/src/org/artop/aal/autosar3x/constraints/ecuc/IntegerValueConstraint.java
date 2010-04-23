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

import java.math.BigInteger;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.IntegerValue;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.IntegerParamDef;

public class IntegerValueConstraint extends AbstractParameterValueConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof IntegerValue;

		IntegerValue integerValue = (IntegerValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, integerValue);
		if (status.isOK()) {
			// the validation of the value requires valid access to the IntegerParamDef
			status = validateValue(ctx, integerValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, ParameterValue parameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, parameterValue);
		if (status.isOK()) {
			if (!(parameterValue.getDefinition() instanceof IntegerParamDef)) {
				status = ctx.createFailureStatus("definition not of type IntegerParamDef");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, IntegerValue integerValue) {
		// default
		IStatus status = ctx.createSuccessStatus();

		if (false == integerValue.isSetValue()) {
			status = ctx.createFailureStatus("no value found");
		} else if (null == integerValue.getValue()) {
			status = ctx.createFailureStatus("value is null");
		} else {
			IntegerParamDef integerParamDef = (IntegerParamDef) integerValue.getDefinition();
			BigInteger value = integerValue.getValue();

			// only check against min or max if integerParameterDef is complete
			// min limit
			if (true == integerParamDef.isSetMin()) {
				BigInteger minLimit = integerParamDef.getMin();
				if (value.compareTo(minLimit) < 0) {
					status = ctx.createFailureStatus("value is under the defined min limit");
				}
			}

			// max limit
			if (true == integerParamDef.isSetMax()) {
				BigInteger maxLimit = integerParamDef.getMax();
				if (value.compareTo(maxLimit) > 0) {
					status = ctx.createFailureStatus("value is above the defined max limit");
				}
			}
		}

		return status;
	}

}
