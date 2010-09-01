package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGEnumerationValueBasicConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar21.ecucdescription.EnumerationValue;

public class EnumerationValueBasicConstraint extends AbstractGEnumerationValueBasicConstraint
{

	@Override
	protected boolean isValueSet(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		EnumerationValue value = (EnumerationValue)gParameterValue;
		return value.isSetValue();
	}

}
