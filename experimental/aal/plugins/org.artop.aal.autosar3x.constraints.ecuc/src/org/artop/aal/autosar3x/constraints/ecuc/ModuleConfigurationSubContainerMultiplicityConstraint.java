/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy,  Continental Engineering Services  and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar 
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGContainerSubContainerMultiplicityConstraint;

import autosar3x.ecucparameterdef.ParamConfContainerDef;

public class ModuleConfigurationSubContainerMultiplicityConstraint extends AbstractGContainerSubContainerMultiplicityConstraint {

	@Override
	protected boolean isMultipleConfigurationContainer(GParamConfContainerDef containerDef) {
		ParamConfContainerDef container = (ParamConfContainerDef) containerDef;
		return container.getMultipleConfigurationContainer();
	}

}
