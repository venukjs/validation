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

public class EcucFloatParamDefDefaultValueConstraint extends AbstractGFloatParamDefDefaultValueConstraint {

	@Override
	protected Double getMin(GFloatParamDef floatParamDef) {
		return (Double) EcucUtil40.getMin(floatParamDef);
	}

	@Override
	protected Double getMax(GFloatParamDef floatParamDef) {
		return (Double) EcucUtil40.getMax(floatParamDef);
	}

	@Override
	protected Double getDefaultValue(GFloatParamDef floatParamDef) {
		try {
			return new Double(EcucUtil40.getDefaultValue(floatParamDef));
		} catch (NumberFormatException ex) {
		}
		return null;
	}

	@Override
	protected boolean isDefaultValueSet(GFloatParamDef floatParamDef) {
		return getDefaultValue(floatParamDef) != null;
	}

}
