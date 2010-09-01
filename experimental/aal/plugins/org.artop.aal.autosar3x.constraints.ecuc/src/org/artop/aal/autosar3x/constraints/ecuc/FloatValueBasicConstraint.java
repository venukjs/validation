package org.artop.aal.autosar3x.constraints.ecuc;

import gautosar.gecucdescription.GFloatValue;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GFloatParamDef;

import org.artop.aal.gautosar.constraints.ecuc.GFloatValueBasicConstraint;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.FloatValue;
import autosar3x.ecucparameterdef.FloatParamDef;

public class FloatValueBasicConstraint extends GFloatValueBasicConstraint {

	@Override
	protected IStatus validateBoundary(IValidationContext ctx, GFloatValue gFloatValue) {
		IStatus status = ctx.createSuccessStatus();
		GFloatParamDef gFloatParamDef = (GFloatParamDef) gFloatValue.gGetDefinition();
		FloatParamDef definition = (FloatParamDef) gFloatParamDef;
		Double value = gFloatValue.gGetValue();
		if (value != null) {

			// min limit
			if (true == definition.isSetMin()) {
				Double minLimit = definition.getMin();
				if (value.compareTo(minLimit) < 0) {
					status = ctx.createFailureStatus(Messages.boundary_valueUnderMin);
				}
			}

			// max limit
			if (true == definition.isSetMax()) {
				Double maxLimit = definition.getMax();
				if (value.compareTo(maxLimit) > 0) {
					status = ctx.createFailureStatus(Messages.boundary_valueAboveMax);
				}
			}
		}
		return status;
	}

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		FloatValue value = (FloatValue) gParameterValue;
		return value.isSetValue();
	}

}
