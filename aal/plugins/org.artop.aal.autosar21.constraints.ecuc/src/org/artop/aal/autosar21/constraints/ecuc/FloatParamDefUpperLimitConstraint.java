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

import org.artop.aal.gautosar.constraints.ecuc.AbstractGFloatParamDefUpperLimitConstraint;
import org.eclipse.emf.ecore.EObject;

import autosar21.ecucparameterdef.FloatParamDef;

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
