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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.AbstractModelConstraintWithPrecondition;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar21.ecucdescription.EcuConfiguration;
import autosar21.ecucdescription.ModuleConfiguration;
import autosar21.ecucparameterdef.ModuleDef;

/**
 * The class validate the constraint for EcuConfiguration. Module Configuration reference in each Ecu Configuration must
 * respect the upper multiplicity of the imported Module Definition
 */
public class EcuConfigurationModuleConfigurationUpperMultiplicityConstraint extends AbstractModelConstraintWithPrecondition {

	static final String MULTIPLICITY_INFINITY = "*"; //$NON-NLS-1$
	static final String SEPARATOR = ", "; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcuConfiguration;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		EcuConfiguration ecuConfiguration = (EcuConfiguration) ctx.getTarget();

		String invalidModuleDefs = new String();
		String invalidModuleConf = new String();

		HashMap<EObject, EObject> refinedModuleDefs = new HashMap<EObject, EObject>();

		for (ModuleConfiguration moduleCongiguration : ecuConfiguration.getModules()) {
			/*
			 * Get Module Definition from Module Configuration included in the target ECUConfiguration
			 */
			ModuleDef moduleDef = moduleCongiguration.getDefinition();
			if (moduleDef == null) {
				continue;
			}
			/*
			 * If this Module Definition does not exist in the list Add the refined Module Definition into list
			 */
			if (!refinedModuleDefs.containsKey(moduleDef)) {
				refinedModuleDefs.put(moduleDef, moduleCongiguration);
			}
		}

		for (EObject moduleDef : refinedModuleDefs.keySet()) {
			/*
			 * The upper multiplicity definition.
			 */
			String upperMultiplicity = ((ModuleDef) moduleDef).getUpperMultiplicity();

			if (((ModuleDef) moduleDef).isSetUpperMultiplicity() && upperMultiplicity != null && !upperMultiplicity.equals(MULTIPLICITY_INFINITY)) {
				/*
				 * Retrieve Modules having the same Definition.
				 */
				List<EObject> similarModuleConfs = getSimilarModuleConfigurations(ecuConfiguration,
						(ModuleConfiguration) refinedModuleDefs.get(moduleDef));
				/*
				 * Verify if upper multiplicity is respected or not.
				 */
				try {
					if (similarModuleConfs.size() > Integer.valueOf(upperMultiplicity)) {
						invalidModuleDefs += ((ModuleDef) moduleDef).getShortName() + SEPARATOR;
						invalidModuleConf += ((ModuleConfiguration) refinedModuleDefs.get(moduleDef)).getShortName() + SEPARATOR;
					}
				} catch (NumberFormatException ex) {
					return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.generic_notValidFormat, upperMultiplicity));
				}
			}
		}

		if (invalidModuleDefs.length() != 0) {
			// Remove redundant ", " at the end
			invalidModuleDefs = invalidModuleDefs.substring(0, invalidModuleDefs.length() - SEPARATOR.length());
			invalidModuleConf = invalidModuleConf.substring(0, invalidModuleConf.length() - SEPARATOR.length());

			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.modulesConfiguration_moduleDefTooMuch, new Object[] { invalidModuleDefs,
					AutosarURIFactory.getAbsoluteQualifiedName(ecuConfiguration), invalidModuleConf }));
		}

		return status;
	}

	/**
	 * Get the similar Module Configurations which have the same definition with the given Module Configuration.
	 * 
	 * @param ecuConfiguration
	 *            The Ecu Configuration
	 * @param moduleConfiguration
	 *            The Module Configuration
	 * @return The similar Module Configurations have the same definition with the given Module Configuration
	 */
	private List<EObject> getSimilarModuleConfigurations(EcuConfiguration ecuConfiguration, ModuleConfiguration moduleConfiguration) {
		List<EObject> similarModuleConfs = new ArrayList<EObject>();

		/*
		 * Retrieve the definition of the given Module Configuration.
		 */
		ModuleDef moduleDef = moduleConfiguration.getDefinition();

		if (moduleDef != null) {
			/*
			 * Candidates list is initialized with Module Configurations declared inside the given ECU Configuration.
			 */
			EList<ModuleConfiguration> candidateModuleConfs = ecuConfiguration.getModules();

			for (ModuleConfiguration candidateModuleConf : candidateModuleConfs) {
				if (moduleDef.equals(candidateModuleConf.getDefinition())) {
					similarModuleConfs.add(candidateModuleConf);
				}
			}
		}

		return similarModuleConfs;
	}

}
