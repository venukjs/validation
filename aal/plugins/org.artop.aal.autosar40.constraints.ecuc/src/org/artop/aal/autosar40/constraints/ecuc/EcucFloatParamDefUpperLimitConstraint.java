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

import org.artop.aal.autosar40.constraints.ecuc.util.EcucUtil40;
import org.artop.aal.autosar40.gautosar40.ecucparameterdef.GEcucFloatParamDef40XAdapter;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGFloatParamDefUpperLimitConstraint;
import org.eclipse.emf.ecore.EObject;

import autosar40.ecucparameterdef.EcucFloatParamDef;

public class EcucFloatParamDefUpperLimitConstraint extends AbstractGFloatParamDefUpperLimitConstraint {

	@Override
	protected Double getMax(EObject obj) {
		return (Double) EcucUtil40.getMax(obj);
	}

	@Override
	protected boolean isSetMax(EObject obj) {
		if (obj instanceof EcucFloatParamDef) {
			return new GEcucFloatParamDef40XAdapter((EcucFloatParamDef) obj).getMax() != null;
		}
		return false;
	}

}
