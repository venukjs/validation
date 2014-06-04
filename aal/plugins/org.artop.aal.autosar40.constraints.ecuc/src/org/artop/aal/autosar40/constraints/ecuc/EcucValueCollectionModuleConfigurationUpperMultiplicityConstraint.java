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
 *      Continental AG - refactoring
 * 
 * </copyright>
 */
package org.artop.aal.autosar40.constraints.ecuc;

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

import autosar40.ecucdescription.EcucModuleConfigurationValuesRefConditional;
import autosar40.ecucdescription.EcucValueCollection;

/**
 * The class validate the constraint for EcucValueCollection. EcucModuleConfigurationValues reference in each
 * EcucValueCollection must respect the upper multiplicity of the imported EcucModuleDef
 */
public class EcucValueCollectionModuleConfigurationUpperMultiplicityConstraint extends AbstractEcuModuleConfigReferenceUpperMultiplicityConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcucValueCollection;
	}

	@Override
	protected Map<GModuleDef, GModuleConfiguration> getRefinedModuleDefs(EObject target) {
		EcucValueCollection ecucValueCollection = (EcucValueCollection) target;

		Map<GModuleDef, GModuleConfiguration> refinedModuleDefs = new HashMap<GModuleDef, GModuleConfiguration>();

		for (EcucModuleConfigurationValuesRefConditional ecucModuleConfigValuesRef : ecucValueCollection.getEcucValues()) {
			/*
			 * Get Module Definition from Module Configuration included in the target ECUConfiguration
			 */
			GModuleConfiguration ecucModuleConfigValues = ecucModuleConfigValuesRef.getEcucModuleConfigurationValues();
			if (ecucModuleConfigValues != null) {
				GModuleDef ecucModuleDef = ecucModuleConfigValues.gGetDefinition();
				if (ecucModuleDef == null) {
					continue;
				}
				/*
				 * If this Module Definition does not exist in the list Add the refined Module Definition into list
				 */
				if (!refinedModuleDefs.containsKey(ecucModuleDef)) {
					refinedModuleDefs.put(ecucModuleDef, ecucModuleConfigValues);
				}
			}
		}

		return refinedModuleDefs;
	}

	@Override
	protected List<GModuleConfiguration> getSimilarModuleConfigurations(EObject target, GModuleConfiguration moduleConfiguration) {

		EcucValueCollection ecuConfiguration = (EcucValueCollection) target;
		List<GModuleConfiguration> similarModuleConfs = new ArrayList<GModuleConfiguration>();

		/*
		 * Retrieve the definition of the given Module Configuration.
		 */
		GModuleDef moduleDef = moduleConfiguration.gGetDefinition();

		if (moduleDef != null) {

			/*
			 * Candidates list is initialized with Module Configurations declared inside the given ECU Configuration.
			 */
			EList<EcucModuleConfigurationValuesRefConditional> ecucModuleConfValuesRefs = ecuConfiguration.getEcucValues();

			for (EcucModuleConfigurationValuesRefConditional ecucModuleConfValuesRef : ecucModuleConfValuesRefs) {
				GModuleConfiguration candidateModuleConf = ecucModuleConfValuesRef.getEcucModuleConfigurationValues();
				if (moduleDef.equals(candidateModuleConf.gGetDefinition())) {
					similarModuleConfs.add(candidateModuleConf);
				}
			}
		}

		return similarModuleConfs;
	}

}
