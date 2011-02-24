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
package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucparameterdef.GFloatParamDef;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGFloatParamDefDefaultValueConstraint;

import autosar21.ecucparameterdef.FloatParamDef;

public class FloatParamDefDefaultValueConstraint extends AbstractGFloatParamDefDefaultValueConstraint {

	@Override
	protected Double getMin(GFloatParamDef floatParamDef) {
		return ((FloatParamDef) floatParamDef).getMin();
	}

	@Override
	protected Double getMax(GFloatParamDef floatParamDef) {
		return ((FloatParamDef) floatParamDef).getMax();
	}

	@Override
	protected Double getDefaultValue(GFloatParamDef floatParamDef) {
		return ((FloatParamDef) floatParamDef).getDefaultValue();
	}

	@Override
	protected boolean isDefaultValueSet(GFloatParamDef floatParamDef) {
		return ((FloatParamDef) floatParamDef).isSetDefaultValue();
	}

}
