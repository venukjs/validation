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
package org.artop.aal.autosar20.constraints.ecuc;

import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGContainerSubContainerMultiplicityConstraint;

public class ContainerSubContainerMultiplicityConstraint extends AbstractGContainerSubContainerMultiplicityConstraint
{

	@Override
	protected boolean isMultipleConfigurationContainer(
			GParamConfContainerDef containerDef)
	{
		return false;
	}

}
