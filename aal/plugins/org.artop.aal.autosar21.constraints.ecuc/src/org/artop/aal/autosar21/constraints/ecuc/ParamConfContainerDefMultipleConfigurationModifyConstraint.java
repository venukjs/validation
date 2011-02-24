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

import org.artop.aal.gautosar.constraints.ecuc.AbstractGParamConfContainerDefMultipleConfigurationModifyConstraint;

import autosar21.ecucparameterdef.ParamConfContainerDef;

public class ParamConfContainerDefMultipleConfigurationModifyConstraint extends AbstractGParamConfContainerDefMultipleConfigurationModifyConstraint {

	@Override
	protected Boolean getMultipleConfigurationContainer(GContainerDef containerDef) {
		if (containerDef instanceof ParamConfContainerDef) {
			return ((ParamConfContainerDef) containerDef).getMultipleConfigurationContainer();
		}
		return null;
	}

	@Override
	protected Boolean isSetMultipleConfigurationContainer(GContainerDef containerDef) {
		if (containerDef instanceof ParamConfContainerDef) {
			return ((ParamConfContainerDef) containerDef).isSetMultipleConfigurationContainer();
		}
		return null;
	}

}
