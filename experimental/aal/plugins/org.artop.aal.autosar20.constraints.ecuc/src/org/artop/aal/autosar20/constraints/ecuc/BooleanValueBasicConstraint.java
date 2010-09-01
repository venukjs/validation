package org.artop.aal.autosar20.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGBooleanValueBasicConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar20.ecucdescription.BooleanValue;


public class BooleanValueBasicConstraint extends AbstractGBooleanValueBasicConstraint {

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		BooleanValue value = (BooleanValue) gParameterValue;
		return value.isSetValue();
	}
}
