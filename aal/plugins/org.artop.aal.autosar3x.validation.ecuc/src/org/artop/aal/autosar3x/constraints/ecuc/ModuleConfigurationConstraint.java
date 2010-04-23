/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucdescription.ModuleConfiguration;
import autosar3x.ecucparameterdef.ConfigurationVariant;
import autosar3x.ecucparameterdef.ModuleDef;

public class ModuleConfigurationConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof ModuleConfiguration;

		ModuleConfiguration moduleConfiguration = (ModuleConfiguration) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, moduleConfiguration);
		if (status.isOK()) {
			status = validateImplementationConfigVariant(ctx, moduleConfiguration);
		}

		return status;
	}

	private IStatus validateDefinitionRef(IValidationContext ctx, ModuleConfiguration moduleConfiguration) {
		// check if definition is set and available
		final IStatus status;
		if (false == moduleConfiguration.eIsSet(EcucdescriptionPackage.eINSTANCE.getModuleConfiguration_Definition())) {
			status = ctx.createFailureStatus("definition reference not set");
		} else {
			ModuleDef moduleDef = moduleConfiguration.getDefinition();
			if (null == moduleDef) {
				status = ctx.createFailureStatus("definition reference not set");
			} else if (moduleDef.eIsProxy()) {
				status = ctx.createFailureStatus("reference to definition could not be resolved");
			} else {
				status = ctx.createSuccessStatus();
			}
		}
		return status;
	}

	private IStatus validateImplementationConfigVariant(IValidationContext ctx, ModuleConfiguration moduleConfiguration) {
		final IStatus status;

		if (false == moduleConfiguration.isSetImplementationConfigVariant()) {
			status = ctx.createFailureStatus("ImplementationConfigVariant not set");
		} else {
			ConfigurationVariant configVariant = moduleConfiguration.getImplementationConfigVariant();
			ModuleDef moduleDef = moduleConfiguration.getDefinition();
			List<ConfigurationVariant> supportedConfigVariants = moduleDef.getSupportedConfigVariants();
			if (supportedConfigVariants.contains(configVariant)) {
				status = ctx.createSuccessStatus();
			} else {
				status = ctx.createFailureStatus("ImplementationConfigVariant not in the list of supported ConfigurationVariants");
			}
		}
		return status;
	}
}
