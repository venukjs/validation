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
package org.artop.aal.autosar20.constraints.ecuc;

import java.math.BigInteger;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGIntegerParamDefUpperLimitConstraint;
import org.eclipse.emf.ecore.EObject;

import autosar20.ecucparameterdef.IntegerParamDef;

public class IntegerParamDefUpperLimitConstraint extends AbstractGIntegerParamDefUpperLimitConstraint {

	@Override
	protected BigInteger getMax(EObject obj) {
		if (obj instanceof IntegerParamDef) {
			return ((IntegerParamDef) obj).getMax();
		}
		return null;
	}

	@Override
	protected boolean isSetMax(EObject obj) {
		if (obj instanceof IntegerParamDef) {
			return ((IntegerParamDef) obj).isSetMax();
		}
		return false;
	}

}
