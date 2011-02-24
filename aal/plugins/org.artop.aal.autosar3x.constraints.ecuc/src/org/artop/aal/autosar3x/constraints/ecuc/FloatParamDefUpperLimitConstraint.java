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
package org.artop.aal.autosar3x.constraints.ecuc;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGFloatParamDefUpperLimitConstraint;
import org.eclipse.emf.ecore.EObject;

import autosar3x.ecucparameterdef.FloatParamDef;

public class FloatParamDefUpperLimitConstraint extends AbstractGFloatParamDefUpperLimitConstraint {

	@Override
	protected Double getMax(EObject obj) {
		if (obj instanceof FloatParamDef) {
			return ((FloatParamDef) obj).getMax();
		} else {
			return null;
		}
	}

	@Override
	protected boolean isSetMax(EObject obj) {
		if (obj instanceof FloatParamDef) {
			return ((FloatParamDef) obj).isSetMax();
		}
		return false;
	}

}
