package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGLinkerSymbolValueBasicConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar21.ecucdescription.LinkerSymbolValue;

public class LinkerSymbolValueBasicConstraint extends AbstractGLinkerSymbolValueBasicConstraint
{

	@Override
	protected boolean isValueSet(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		LinkerSymbolValue value = (LinkerSymbolValue)gParameterValue;
		return value.isSetValue();
	}

}
