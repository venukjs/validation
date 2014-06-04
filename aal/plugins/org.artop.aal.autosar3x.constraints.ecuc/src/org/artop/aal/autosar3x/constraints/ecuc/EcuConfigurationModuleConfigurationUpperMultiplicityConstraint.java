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
 *     Continental AG - refactoring
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GModuleDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.artop.aal.gautosar.constraints.ecuc.AbstractEcuModuleConfigReferenceUpperMultiplicityConstraint;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.EcuConfiguration;

/**
 * The class validate the constraint for EcuConfiguration. Module Configuration reference in each Ecu Configuration must
 * respect the upper multiplicity of the imported Module Definition
 */
public class EcuConfigurationModuleConfigurationUpperMultiplicityConstraint extends AbstractEcuModuleConfigReferenceUpperMultiplicityConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcuConfiguration;
	}

	@Override
	protected Map<GModuleDef, GModuleConfiguration> getRefinedModuleDefs(EObject target) {

		EcuConfiguration ecuConfiguration = (EcuConfiguration) target;
		Map<GModuleDef, GModuleConfiguration> refinedModuleDefs = new HashMap<GModuleDef, GModuleConfiguration>();

		for (GModuleConfiguration moduleConfiguration : ecuConfiguration.getModules()) {
			/*
			 * Get Module Definition from Module Configuration included in the target ECUConfiguration
			 */
			GModuleDef moduleDef = moduleConfiguration.gGetDefinition();
			if (moduleDef == null) {
				continue;
			}
			/*
			 * If this Module Definition does not exist in the list Add the refined Module Definition into list
			 */
			if (!refinedModuleDefs.containsKey(moduleDef)) {
				refinedModuleDefs.put(moduleDef, moduleConfiguration);
			}
		}

		return refinedModuleDefs;
	}

	@Override
	protected List<GModuleConfiguration> getSimilarModuleConfigurations(EObject target, GModuleConfiguration moduleConfiguration) {
		EcuConfiguration ecuConfiguration = (EcuConfiguration) target;

		List<GModuleConfiguration> similarModuleConfs = new ArrayList<GModuleConfiguration>();
		/*
		 * Retrieve the definition of the given Module Configuration.
		 */
		GModuleDef moduleDef = moduleConfiguration.gGetDefinition();

		if (moduleDef != null) {
			/*
			 * Candidates list is initialized with Module Configurations declared inside the given ECU Configuration.
			 */
			EList<? extends GModuleConfiguration> candidateModuleConfs = ecuConfiguration.getModules();

			for (GModuleConfiguration candidateModuleConf : candidateModuleConfs) {
				if (moduleDef.equals(candidateModuleConf.gGetDefinition())) {
					similarModuleConfs.add(candidateModuleConf);
				}
			}
		}

		return similarModuleConfs;
	}

}
