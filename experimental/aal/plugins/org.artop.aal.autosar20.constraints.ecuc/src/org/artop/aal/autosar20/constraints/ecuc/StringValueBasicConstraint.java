package org.artop.aal.autosar20.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGStringValueBasicConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar20.ecucdescription.StringValue;


public class StringValueBasicConstraint extends AbstractGStringValueBasicConstraint
{

	@Override
	protected boolean isValueSet(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		StringValue value = (StringValue)gParameterValue;
		return value.isSetValue();
	}

}
