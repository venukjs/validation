/**
 * <copyright>
 * 
 * Copyright (c) see4Sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     see4Sys - Initial API and implementation for AUTOSAR 2.0
 * 
 * </copyright>
 */
package org.artop.aal.autosar20.constraints.ecuc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.AbstractModelConstraintWithPrecondition;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sphinx.emf.util.EObjectUtil;

import autosar20.ecucdescription.EcuConfiguration;
import autosar20.ecucdescription.ModuleConfiguration;
import autosar20.ecucparameterdef.ModuleDef;

/**
 * The class validate the constraint for EcuConfiguration. Module Configuration reference in each Ecu Configuration must
 * respect the lower multiplicity of the imported Module Definition
 */
public class EcuConfigurationModuleConfigurationLowerMultiplicityConstraint extends AbstractModelConstraintWithPrecondition {

	static final String MULTIPLICITY_ZERO = "0"; //$NON-NLS-1$
	static final String SEPARATOR = ", "; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcuConfiguration;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		EcuConfiguration ecuConfiguration = (EcuConfiguration) ctx.getTarget();

		/*
		 * Retrieve Module Definitions that are mandatory; that is Module Definitions for which at least one Module
		 * Configuration must be present inside the current ECU Configuration target.
		 */
		List<ModuleDef> mandatoryModuleDefinitions = findMandatoryModuleDefinitions(ecuConfiguration);

		String invalidModuleDefs = new String();

		if (!mandatoryModuleDefinitions.isEmpty()) {
			for (ModuleDef mandatoryModuleDef : mandatoryModuleDefinitions) {
				/*
				 * Obtain the list of Module Configurations having the current Module Definition as definition.
				 */
				List<ModuleConfiguration> moduleConfigurations = getMatchingModuleConfigurations(ecuConfiguration, mandatoryModuleDef);

				/*
				 * Check the multiplicity consistency.
				 */
				if (!EcucUtil.isValidLowerMultiplicity(moduleConfigurations.size(), mandatoryModuleDef)) {
					invalidModuleDefs += mandatoryModuleDef.getShortName() + SEPARATOR;
				}
			}

			if (invalidModuleDefs.length() != 0) {
				// Remove redundant ", " at the end
				invalidModuleDefs = invalidModuleDefs.substring(0, invalidModuleDefs.length() - SEPARATOR.length());
				return ctx.createFailureStatus(NLS.bind(Messages.modulesConfiguration_moduleDefMissing, new Object[] { invalidModuleDefs,
						AutosarURIFactory.getAbsoluteQualifiedName(ecuConfiguration) }));
			}
		}

		return status;
	}

	/**
	 * Find mandatory Module Definitions in the model which have the lower multiplicity was set
	 * 
	 * @param ecuConfiguration
	 *            The ECU Configuration
	 * @return The Module Definitions which have the lower multiplicity was set
	 */
	private List<ModuleDef> findMandatoryModuleDefinitions(EcuConfiguration ecuConfiguration) {
		List<ModuleDef> mandatoryModuleDefs = new ArrayList<ModuleDef>();
		Set<ModuleDef> refinedModuleDefs = new HashSet<ModuleDef>();
		Set<ModuleDef> vSpecifModuleDefs = new HashSet<ModuleDef>();

		List<ModuleDef> moduleDefs = EObjectUtil.getAllInstancesOf(ecuConfiguration, ModuleDef.class, true);

		for (ModuleDef moduleDef : moduleDefs) {
			String lowerMultiplicity = moduleDef.getLowerMultiplicity();
			if (moduleDef.isSetLowerMultiplicity() && lowerMultiplicity != null && !lowerMultiplicity.equals(MULTIPLICITY_ZERO)) {
				ModuleDef refinedModuleDef = moduleDef.getRefinedModuleDef();
				if (refinedModuleDef == null) {
					refinedModuleDefs.add(moduleDef);
				} else {
					vSpecifModuleDefs.add(moduleDef);
				}
			}
		}
		/*
		 * Add Vendor Specific Module Definitions.
		 */
		for (ModuleDef vSpecifModuleDef : vSpecifModuleDefs) {
			// avoid duplicate
			if (!mandatoryModuleDefs.contains(vSpecifModuleDef)) {
				mandatoryModuleDefs.add(vSpecifModuleDef);
				refinedModuleDefs.remove(vSpecifModuleDef.getRefinedModuleDef());
			}
		}
		/*
		 * Add Refined Module Definitions.
		 */
		for (ModuleDef refinedModuleDef : refinedModuleDefs) {
			// avoid duplicate
			if (!mandatoryModuleDefs.contains(refinedModuleDef)) {
				mandatoryModuleDefs.add(refinedModuleDef);
			}
		}

		return mandatoryModuleDefs;
	}

	/**
	 * Get Module Configurations which have the given Module Definition as definition
	 * 
	 * @param ecuConfiguration
	 *            The ECU Configuration whose Module Configurations having the given Module Definition as definition
	 *            must be returned.
	 * @param moduleDef
	 *            The Module Definition that matching Module Configurations must have as definition.
	 * @return The Module Configurations which have the given Module Definition as definition.
	 */
	protected List<ModuleConfiguration> getMatchingModuleConfigurations(EcuConfiguration ecuConfiguration, ModuleDef moduleDef) {
		List<ModuleConfiguration> moduleConfigurations = new ArrayList<ModuleConfiguration>();
		for (ModuleConfiguration moduleConfiguration : ecuConfiguration.getModules()) {
			if (moduleDef.equals(moduleConfiguration.getDefinition())) {
				moduleConfigurations.add(moduleConfiguration);
			}
		}
		return moduleConfigurations;
	}
}
