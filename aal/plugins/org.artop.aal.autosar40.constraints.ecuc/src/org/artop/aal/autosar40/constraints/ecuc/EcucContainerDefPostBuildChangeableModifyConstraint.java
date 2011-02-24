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

import gautosar.gecucparameterdef.GContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGContainerDefPostBuildChangeableModifyConstraint;

import autosar40.ecucparameterdef.EcucContainerDef;

public class EcucContainerDefPostBuildChangeableModifyConstraint extends AbstractGContainerDefPostBuildChangeableModifyConstraint {

	@Override
	protected boolean isSetPostBuildChangeable(GContainerDef containerDef) {
		if (containerDef instanceof EcucContainerDef) {
			return ((EcucContainerDef) containerDef).isSetPostBuildChangeable();
		}
		return false;
	}

}
