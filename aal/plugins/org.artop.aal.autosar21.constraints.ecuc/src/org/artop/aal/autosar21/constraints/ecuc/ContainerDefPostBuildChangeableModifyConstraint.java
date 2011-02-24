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
