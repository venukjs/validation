/**
 * <copyright>
 * 
 * Copyright (c) see4Sys and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Artop Software License 
 * Based on Released AUTOSAR Material (ASLR) which accompanies this 
 * distribution, and is available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     see4Sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucparameterdef.GIntegerParamDef;

import java.math.BigInteger;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGIntegerParamDefDefaultValueConstraint;

import autosar21.ecucparameterdef.IntegerParamDef;

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
