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
