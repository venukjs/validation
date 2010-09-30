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


import gautosar.gecucdescription.GModuleConfiguration;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGModuleConfigurationBasicConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

public class ModuleConfigurationBasicConstraint extends AbstractGModuleConfigurationBasicConstraint
{

	@Override
	protected IStatus validateImplementationConfigVariant
	(
			IValidationContext ctx,
			GModuleConfiguration gModuleConfiguration)
	{
		return ctx.createSuccessStatus();
	}
	

}
