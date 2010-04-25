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
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.FloatValue;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.FloatParamDef;

public class FloatValueConstraint extends AbstractParameterValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof FloatValue;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		FloatValue floatValue = (FloatValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, floatValue);
		if (status.isOK()) {
			// the validation of the value requires valid access to the IntegerParamDef
			status = validateValue(ctx, floatValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, ParameterValue parameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, parameterValue);
		if (status.isOK()) {
			if (!(parameterValue.getDefinition() instanceof FloatParamDef)) {
				status = ctx.createFailureStatus("definition not of type FloatParamDef");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, FloatValue floatValue) {
		// default
		IStatus status = ctx.createSuccessStatus();

		if (false == floatValue.isSetValue()) {
			status = ctx.createFailureStatus("no value found");
		} else if (null == floatValue.getValue()) {
			status = ctx.createFailureStatus("value is null");
		} else {
			FloatParamDef floatParamDef = (FloatParamDef) floatValue.getDefinition();
			Double value = floatValue.getValue();

			// min limit
			if (true == floatParamDef.isSetMin()) {
				Double minLimit = floatParamDef.getMin();
				if (value.compareTo(minLimit) < 0) {
					status = ctx.createFailureStatus("value is under the defined min limit");
				}
			}

			// max limit
			if (true == floatParamDef.isSetMax()) {
				Double maxLimit = floatParamDef.getMax();
				if (value.compareTo(maxLimit) > 0) {
					status = ctx.createFailureStatus("value is above the defined max limit");
				}
			}
		}

		return status;
	}

}
