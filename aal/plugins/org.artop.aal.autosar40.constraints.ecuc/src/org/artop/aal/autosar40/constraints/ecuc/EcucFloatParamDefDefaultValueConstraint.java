/**
 * <copyright>
 * 
 * Copyright (c) See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucparameterdef.GFloatParamDef;

import org.artop.aal.autosar40.constraints.ecuc.util.EcucUtil40;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGFloatParamDefDefaultValueConstraint;

import autosar40.ecucparameterdef.EcucFloatParamDef;
import autosar40.genericstructure.varianthandling.FloatValueVariationPoint;

public class EcucFloatParamDefDefaultValueConstraint extends AbstractGFloatParamDefDefaultValueConstraint {

	@Override
	protected Double getMin(GFloatParamDef floatParamDef) {
		Object obj = EcucUtil40.getMin(floatParamDef);
		if (obj instanceof Double) {
			return (Double) obj;
		}
		return null;
	}

	@Override
	protected Double getMax(GFloatParamDef floatParamDef) {
		Object obj = EcucUtil40.getMax(floatParamDef);
		if (obj instanceof Double) {
			return (Double) obj;
		}
		return null;
	}

	@Override
	protected Double getDefaultValue(GFloatParamDef floatParamDef) {
		try {

			String defaultValueAsString = EcucUtil40.getDefaultValue(floatParamDef);
			return EcucUtil40.convertStringToDouble(defaultValueAsString);

		} catch (NumberFormatException ex) {
		}
		return null;
	}

	@Override
	protected boolean isDefaultValueSet(GFloatParamDef floatParamDef) {
		FloatValueVariationPoint defaultValue = ((EcucFloatParamDef) floatParamDef).getDefaultValue();
		return defaultValue != null && defaultValue.isSetMixed();
	}

	@Override
	protected String getDoubleAsString(Double value) {

		return EcucUtil40.convertDoubleToString(value);
	}

}
