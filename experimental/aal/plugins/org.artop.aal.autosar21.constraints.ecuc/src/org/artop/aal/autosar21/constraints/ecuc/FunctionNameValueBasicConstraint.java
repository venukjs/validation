package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGFunctionNameValueBasicConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar21.ecucdescription.FunctionNameValue;

public class FunctionNameValueBasicConstraint extends AbstractGFunctionNameValueBasicConstraint
{

	@Override
	protected boolean isValueSet(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		FunctionNameValue value = (FunctionNameValue) gParameterValue;
		return value.isSetValue();
	}

}
