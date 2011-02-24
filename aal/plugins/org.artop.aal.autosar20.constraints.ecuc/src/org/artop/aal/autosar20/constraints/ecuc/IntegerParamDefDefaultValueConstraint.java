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
package org.artop.aal.autosar20.constraints.ecuc;

import gautosar.gecucparameterdef.GIntegerParamDef;

import java.math.BigInteger;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGIntegerParamDefDefaultValueConstraint;

import autosar20.ecucparameterdef.IntegerParamDef;

public class IntegerParamDefDefaultValueConstraint extends AbstractGIntegerParamDefDefaultValueConstraint {

	@Override
	protected BigInteger getMin(GIntegerParamDef integerParamDef) {
		return ((IntegerParamDef) integerParamDef).getMin();
	}

	@Override
	protected BigInteger getMax(GIntegerParamDef integerParamDef) {
		return ((IntegerParamDef) integerParamDef).getMax();
	}

	@Override
	protected BigInteger getDefaultValue(GIntegerParamDef integerParamDef) {
		return ((IntegerParamDef) integerParamDef).getDefaultValue();
	}

	@Override
	protected boolean isDefaultValueSet(GIntegerParamDef integerParamDef) {
		return ((IntegerParamDef) integerParamDef).isSetDefaultValue();
	}

}
