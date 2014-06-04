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
package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GModuleDef;

import java.util.ArrayList;
import java.util.List;

import org.artop.aal.gautosar.constraints.ecuc.AbstractEcuModuleConfigReferenceLowerMultiplicityConstraint;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar40.ecucdescription.EcucModuleConfigurationValues;
import autosar40.ecucdescription.EcucModuleConfigurationValuesRefConditional;
import autosar40.ecucdescription.EcucValueCollection;

/**
 * The class validate the constraint for EcucValueCollection. EcucModuleConfigurationValues reference in each
 * EcucValueCollection must respect the lower multiplicity of the imported EcucModuleDef
 */
public class EcucValueCollectionModuleConfigurationLowerMultiplicityConstraint extends AbstractEcuModuleConfigReferenceLowerMultiplicityConstraint {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcucValueCollection;
	}

	/**
	 * Get EcucModuleConfigurationValues which have the given EcucModuleDef as definition
	 * 
	 * @param ecuConfiguration
	 *            The EcucModuleConfigurationValues whose EcucModuleConfigurationValues having the given EcucModuleDef
	 *            as definition must be returned.
	 * @param moduleDef
	 *            The EcucModuleDef that matching EcucModuleConfigurationValues must have as definition.
	 * @return The EcucModuleConfigurationValues which have the given EcucModuleDef as definition.
	 */

	@Override
	protected List<GModuleConfiguration> getMatchingModuleConfigurations(EObject target, GModuleDef moduleDef) {
		EcucValueCollection ecuConfiguration = (EcucValueCollection) target;
		List<GModuleConfiguration> ecucModuleConfigs = new ArrayList<GModuleConfiguration>();
		for (EcucModuleConfigurationValuesRefConditional ecucModuleConfigValuesRefs : ecuConfiguration.getEcucValues()) {
			EcucModuleConfigurationValues ecucModuleConfig = ecucModuleConfigValuesRefs.getEcucModuleConfigurationValues();
			if (ecucModuleConfig != null && moduleDef.equals(ecucModuleConfig.getDefinition())) {
				ecucModuleConfigs.add(ecucModuleConfig);
			}
		}
		return ecucModuleConfigs;
	}

}
