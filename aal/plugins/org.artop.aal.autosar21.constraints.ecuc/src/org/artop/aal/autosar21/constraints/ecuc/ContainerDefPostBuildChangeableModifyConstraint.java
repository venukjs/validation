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

import gautosar.gecucparameterdef.GContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGContainerDefPostBuildChangeableModifyConstraint;

import autosar21.ecucparameterdef.ContainerDef;

public class ContainerDefPostBuildChangeableModifyConstraint extends AbstractGContainerDefPostBuildChangeableModifyConstraint {

	@Override
	protected boolean isSetPostBuildChangeable(GContainerDef containerDef) {
		if (containerDef instanceof ContainerDef) {
			return ((ContainerDef) containerDef).isSetPostBuildChangeable();
		}
		return false;
	}

}
