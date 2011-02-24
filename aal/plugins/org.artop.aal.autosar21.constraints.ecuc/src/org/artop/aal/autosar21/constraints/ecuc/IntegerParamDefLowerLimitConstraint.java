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

import java.math.BigInteger;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGIntegerParamDefLowerLimitConstraint;
import org.eclipse.emf.ecore.EObject;

import autosar21.ecucparameterdef.IntegerParamDef;

public class IntegerParamDefLowerLimitConstraint extends AbstractGIntegerParamDefLowerLimitConstraint {

	@Override
	protected BigInteger getMin(EObject obj) {
		if (obj instanceof IntegerParamDef) {
			return ((IntegerParamDef) obj).getMin();
		}
		return null;
	}

	@Override
	protected boolean isSetMin(EObject obj) {
		if (obj instanceof IntegerParamDef) {
			return ((IntegerParamDef) obj).isSetMin();
		}
		return false;
	}
}
