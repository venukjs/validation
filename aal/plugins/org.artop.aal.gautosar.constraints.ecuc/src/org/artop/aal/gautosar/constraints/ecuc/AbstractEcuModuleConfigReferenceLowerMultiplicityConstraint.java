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
 *     Continental AG - abstractization and refactoring
 *
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GModuleDef;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sphinx.emf.util.EObjectUtil;

public abstract class AbstractEcuModuleConfigReferenceLowerMultiplicityConstraint extends AbstractSplitModelConstraintWithPrecondition {

	protected static final String MULTIPLICITY_ZERO = "0"; //$NON-NLS-1$
	protected static final String SEPARATOR = ", "; //$NON-NLS-1$

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		EObject target = ctx.getTarget();

		/*
		 * Retrieve Module Definitions that are mandatory; that is Module Definitions for which at least one Module
		 * Configuration must be present inside the current ECU Configuration target.
		 */
		List<GModuleDef> mandatoryModuleDefinitions = findMandatoryModuleDefinitions(target);

		String invalidModuleDefs = new String();

		if (!mandatoryModuleDefinitions.isEmpty()) {
			for (GModuleDef mandatoryModuleDef : mandatoryModuleDefinitions) {
				/*
				 * Obtain the list of Module Configurations having the current Module Definition as definition.
				 */
				List<GModuleConfiguration> moduleConfigurations = getMatchingModuleConfigurations(target, mandatoryModuleDef);

				/*
				 * Check the multiplicity consistency.
				 */
				if (!EcucUtil.isValidLowerMultiplicity(moduleConfigurations.size(), mandatoryModuleDef)) {
					invalidModuleDefs += mandatoryModuleDef.gGetShortName() + SEPARATOR;
				}
			}

			if (invalidModuleDefs.length() != 0) {
				// Remove redundant ", " at the end
				invalidModuleDefs = invalidModuleDefs.substring(0, invalidModuleDefs.length() - SEPARATOR.length());
				return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.modulesConfiguration_moduleDefMissing,
						new Object[] { invalidModuleDefs /* , AutosarURIFactory.getAbsoluteQualifiedName(target) */}));
			}
		}

		return status;
	}

	/**
	 * Find mandatory GModuleDef in the model which have the lower multiplicity was set
	 *
	 * @param <T>
	 * @param ecuConfiguration
	 *            The EcucValueCollection
	 * @return The GModuleDef which have the lower multiplicity was set
	 */
	protected List<GModuleDef> findMandatoryModuleDefinitions(EObject target) {
		List<GModuleDef> mandatoryModuleDefs = new ArrayList<GModuleDef>();
		Set<GModuleDef> refinedModuleDefs = new HashSet<GModuleDef>();
		Set<GModuleDef> vSpecifModuleDefs = new HashSet<GModuleDef>();

		List<GModuleDef> moduleDefs = EObjectUtil.getAllInstancesOf(target, GModuleDef.class, false);
		for (GModuleDef moduleDef : moduleDefs) {
			String lowerMultiplicity = moduleDef.gGetLowerMultiplicityAsString();

			if (lowerMultiplicity != null && !lowerMultiplicity.equals("") && !lowerMultiplicity.equals(MULTIPLICITY_ZERO)) { //$NON-NLS-1$
				GModuleDef refinedModuleDef = moduleDef.gGetRefinedModuleDef();
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
		for (GModuleDef vSpecifModuleDef : vSpecifModuleDefs) {
			// avoid duplicate
			if (!mandatoryModuleDefs.contains(vSpecifModuleDef)) {
				mandatoryModuleDefs.add(vSpecifModuleDef);
				refinedModuleDefs.remove(vSpecifModuleDef.gGetRefinedModuleDef());
			}
		}
		/*
		 * Add Refined Module Definitions.
		 */
		for (GModuleDef refinedModuleDef : refinedModuleDefs) {
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
	protected abstract List<GModuleConfiguration> getMatchingModuleConfigurations(EObject target, GModuleDef moduleDef);
}
