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
package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GModuleDef;

import java.util.ArrayList;
import java.util.List;

import org.artop.aal.gautosar.constraints.ecuc.AbstractEcuModuleConfigReferenceLowerMultiplicityConstraint;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar21.ecucdescription.EcuConfiguration;
import autosar21.ecucdescription.ModuleConfiguration;

/**
 * The class validate the constraint for EcuConfiguration. Module Configuration reference in each Ecu Configuration must
 * respect the lower multiplicity of the imported Module Definition
 */
public class EcuConfigurationModuleConfigurationLowerMultiplicityConstraint extends AbstractEcuModuleConfigReferenceLowerMultiplicityConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcuConfiguration;
	}

	@Override
	protected List<GModuleConfiguration> getMatchingModuleConfigurations(EObject target, GModuleDef moduleDef) {
		EcuConfiguration ecuConfiguration = (EcuConfiguration) target;
		List<GModuleConfiguration> moduleConfigurations = new ArrayList<GModuleConfiguration>();
		for (ModuleConfiguration moduleConfiguration : ecuConfiguration.getModules()) {
			if (moduleDef.equals(moduleConfiguration.getDefinition())) {
				moduleConfigurations.add(moduleConfiguration);
			}
		}
		return moduleConfigurations;
	}
}
