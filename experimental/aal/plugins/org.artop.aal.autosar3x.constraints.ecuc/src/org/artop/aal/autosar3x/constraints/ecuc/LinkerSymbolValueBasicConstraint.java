package org.artop.aal.autosar3x.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGLinkerSymbolValueBasicConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.LinkerSymbolValue;

public class LinkerSymbolValueBasicConstraint extends AbstractGLinkerSymbolValueBasicConstraint {

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		LinkerSymbolValue value = (LinkerSymbolValue) gParameterValue;
		return value.isSetValue();
	}
}
