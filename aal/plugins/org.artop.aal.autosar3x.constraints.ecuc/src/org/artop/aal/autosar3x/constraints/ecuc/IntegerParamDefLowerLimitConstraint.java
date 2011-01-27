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
package org.artop.aal.autosar3x.constraints.ecuc;

import java.math.BigInteger;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGIntegerParamDefLowerLimitConstraint;
import org.eclipse.emf.ecore.EObject;

import autosar3x.ecucparameterdef.IntegerParamDef;

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
