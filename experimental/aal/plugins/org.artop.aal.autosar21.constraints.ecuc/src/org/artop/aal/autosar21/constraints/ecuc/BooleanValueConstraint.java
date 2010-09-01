package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGBooleanValueBasicConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar21.ecucdescription.BooleanValue;


public class BooleanValueConstraint extends AbstractGBooleanValueBasicConstraint {

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		BooleanValue value = (BooleanValue) gParameterValue;
		return value.isSetValue();
	}

}
