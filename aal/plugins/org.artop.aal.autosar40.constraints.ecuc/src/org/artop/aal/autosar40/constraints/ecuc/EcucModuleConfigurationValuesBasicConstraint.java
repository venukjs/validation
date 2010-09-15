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


import gautosar.gecucdescription.GModuleConfiguration;

import java.util.List;

import org.artop.aal.autosar40.constraints.ecuc.internal.Messages;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGModuleConfigurationBasicConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar40.ecucdescription.EcucModuleConfigurationValues;
import autosar40.ecucparameterdef.EcucConfigurationVariantEnum;
import autosar40.ecucparameterdef.EcucModuleDef;

public class EcucModuleConfigurationValuesBasicConstraint extends AbstractGModuleConfigurationBasicConstraint 
{

	@Override
	protected IStatus validateImplementationConfigVariant
	(
			IValidationContext ctx, GModuleConfiguration gModuleConfiguration)
	{
	
		final IStatus status;
		EcucModuleConfigurationValues moduleConfiguration = (EcucModuleConfigurationValues)gModuleConfiguration;

		if (false == moduleConfiguration.isSetImplementationConfigVariant())
		{
			status = ctx
					.createFailureStatus(Messages.moduleConfig_ImplConfigVariantNotSet);
		} 
		else
		{
			EcucConfigurationVariantEnum configVariant = moduleConfiguration
					.getImplementationConfigVariant();
			EcucModuleDef moduleDef = moduleConfiguration.getDefinition();
			List<EcucConfigurationVariantEnum> supportedConfigVariants = moduleDef
					.getSupportedConfigVariants();
			if (supportedConfigVariants.contains(configVariant)) 
			{
				status = ctx.createSuccessStatus();
			}
			else
			{
				status = ctx
						.createFailureStatus(Messages.moduleConfig_ImplConfigVariantNotSupported);
			}
		}
		return status;
	}
}
