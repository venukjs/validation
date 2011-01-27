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

import org.artop.aal.gautosar.constraints.ecuc.AbstractGFloatParamDefLowerLimitConstraint;
import org.eclipse.emf.ecore.EObject;

import autosar3x.ecucparameterdef.FloatParamDef;

public class FloatParamDefLowerLimitConstraint extends AbstractGFloatParamDefLowerLimitConstraint {

	@Override
	protected Double getMin(EObject obj) {
		if (obj instanceof FloatParamDef) {
			return ((FloatParamDef) obj).getMin();
		} else {
			return null;
		}
	}

	@Override
	protected boolean isSetMin(EObject obj) {
		if (obj instanceof FloatParamDef) {
			return ((FloatParamDef) obj).isSetMin();
		}
		return false;
	}

}
