/**
 * <copyright>
 * 
 * Copyright (c) Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Continental Engineering Services - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucparameterdef.GAbstractStringParamDef;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGStringParamDefDefaultValueConstraint;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;

public class StringParamDefDefaultValueConstraint extends AbstractGStringParamDefDefaultValueConstraint {

	@Override
	protected String getDefaultValue(GAbstractStringParamDef stringParamDef) {
		return EcucUtil.getFeatureValue(stringParamDef, "defaultValue"); //$NON-NLS-1$
	}

}
