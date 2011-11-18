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
package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucparameterdef.GAbstractStringParamDef;

import org.artop.aal.autosar40.constraints.ecuc.util.EcucUtil40;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGLinkerSymbolDefDefaultValueConstraint;

public class EcucLinkerSymbolDefDefaultValueConstraint extends AbstractGLinkerSymbolDefDefaultValueConstraint {

	@Override
	protected String getDefaultValue(GAbstractStringParamDef linkerParamDef) {
		return EcucUtil40.getDefaultValue(linkerParamDef);
	}

}
