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

import gautosar.gecucparameterdef.GIntegerParamDef;

import java.math.BigInteger;

import org.artop.aal.autosar40.constraints.ecuc.util.EcucUtil40;
import org.artop.aal.autosar40.gautosar40.ecucparameterdef.GEcucIntegerParamDef40XAdapter;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGIntegerParamDefDefaultValueConstraint;

import autosar40.ecucparameterdef.EcucIntegerParamDef;
import autosar40.genericstructure.formulalanguage.FormulaExpression;

public class EcucIntegerParamDefDefaultValueConstraint extends AbstractGIntegerParamDefDefaultValueConstraint {

	@Override
	protected BigInteger getMin(GIntegerParamDef integerParamDef) {
		Object obj = EcucUtil40.getMin(integerParamDef);
		if (obj instanceof BigInteger) {
			return (BigInteger) obj;
		}
		return null;
	}

	@Override
	protected BigInteger getMax(GIntegerParamDef integerParamDef) {
		Object obj = EcucUtil40.getMax(integerParamDef);
		if (obj instanceof BigInteger) {
			return (BigInteger) obj;
		}
		return null;
	}

	@Override
	protected BigInteger getDefaultValue(GIntegerParamDef integerParamDef) {
		try {
			return new BigInteger(EcucUtil40.getDefaultValue(integerParamDef));
		} catch (NumberFormatException ex) {
		}
		return null;
	}

	@Override
	protected boolean isDefaultValueSet(GIntegerParamDef integerParamDef) {
		FormulaExpression defaultValue = new GEcucIntegerParamDef40XAdapter((EcucIntegerParamDef) integerParamDef).getDefaultValue();
		return defaultValue != null && defaultValue.isSetMixed();
	}

}
