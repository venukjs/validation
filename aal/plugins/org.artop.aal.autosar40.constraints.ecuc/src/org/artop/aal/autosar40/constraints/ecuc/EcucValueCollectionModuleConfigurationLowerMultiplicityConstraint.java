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
 *     see4Sys - Initial API and implementation for AUTOSAR 4.0
 * 
 * </copyright>
 */
package org.artop.aal.autosar40.constraints.ecuc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.AbstractModelConstraintWithPrecondition;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.artop.ecl.emf.util.EObjectUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar40.ecucdescription.EcucModuleConfigurationValues;
import autosar40.ecucdescription.EcucModuleConfigurationValuesRefConditional;
import autosar40.ecucdescription.EcucValueCollection;
import autosar40.ecucparameterdef.EcucModuleDef;
import autosar40.genericstructure.varianthandling.PositiveIntegerValueVariationPoint;

/**
 * The class validate the constraint for EcucValueCollection. EcucModuleConfigurationValues reference in each
 * EcucValueCollection must respect the lower multiplicity of the imported EcucModuleDef
 */
public class EcucValueCollectionModuleConfigurationLowerMultiplicityConstraint extends AbstractModelConstraintWithPrecondition {

	static final String MULTIPLICITY_ZERO = "0"; //$NON-NLS-1$
	static final String SEPARATOR = ", "; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcucValueCollection;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		EcucValueCollection ecucValueCollection = (EcucValueCollection) ctx.getTarget();

		/*
		 * Retrieve Module Definitions that are mandatory; that is Module Definitions for which at least one Module
		 * Configuration must be present inside the current ECU Configuration target.
		 */
		List<EcucModuleDef> mandatoryModuleDefinitions = findMandatoryModuleDefinitions(ecucValueCollection);

		String invalidModuleDefs = new String();

		if (!mandatoryModuleDefinitions.isEmpty()) {
			for (EcucModuleDef mandatoryModuleDef : mandatoryModuleDefinitions) {
				/*
				 * Obtain the list of Module Configurations having the current Module Definition as definition.
				 */
				List<EcucModuleConfigurationValues> moduleConfigurations = getMatchingModuleConfigurations(ecucValueCollection, mandatoryModuleDef);

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
						AutosarURIFactory.getAbsoluteQualifiedName(ecucValueCollection) }));
			}
		}

		return status;
	}

	/**
	 * Find mandatory EcucModuleDef in the model which have the lower multiplicity was set
	 * 
	 * @param ecuConfiguration
	 *            The EcucValueCollection
	 * @return The EcucModuleDef which have the lower multiplicity was set
	 */
	private List<EcucModuleDef> findMandatoryModuleDefinitions(EcucValueCollection ecuConfiguration) {
		List<EcucModuleDef> mandatoryModuleDefs = new ArrayList<EcucModuleDef>();
		Set<EcucModuleDef> refinedModuleDefs = new HashSet<EcucModuleDef>();
		Set<EcucModuleDef> vSpecifModuleDefs = new HashSet<EcucModuleDef>();

		List<EcucModuleDef> ecucModuleDefs = EObjectUtil.getAllInstancesOf(ecuConfiguration, EcucModuleDef.class, true);
		for (EcucModuleDef ecucModuleDef : ecucModuleDefs) {
			String lowerMultiplicity = new String();
			PositiveIntegerValueVariationPoint integerVarPoint = ecucModuleDef.getLowerMultiplicity();
			if (integerVarPoint != null) {
				lowerMultiplicity = integerVarPoint.getMixedText();
			}

			if (lowerMultiplicity != null && !lowerMultiplicity.equals("0")) { //$NON-NLS-1$
				EcucModuleDef refinedModuleDef = ecucModuleDef.getRefinedModuleDef();
				if (refinedModuleDef == null) {
					refinedModuleDefs.add(ecucModuleDef);
				} else {
					vSpecifModuleDefs.add(ecucModuleDef);
				}
			}
		}
		/*
		 * Add Vendor Specific Module Definitions.
		 */
		for (EcucModuleDef vSpecifModuleDef : vSpecifModuleDefs) {
			// avoid duplicate
			if (!mandatoryModuleDefs.contains(vSpecifModuleDef)) {
				mandatoryModuleDefs.add(vSpecifModuleDef);
				refinedModuleDefs.remove(vSpecifModuleDef.getRefinedModuleDef());
			}
		}
		/*
		 * Add Refined Module Definitions.
		 */
		for (EcucModuleDef refinedModuleDef : refinedModuleDefs) {
			// avoid duplicate
			if (!mandatoryModuleDefs.contains(refinedModuleDef)) {
				mandatoryModuleDefs.add(refinedModuleDef);
			}
		}

		return mandatoryModuleDefs;
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
	protected List<EcucModuleConfigurationValues> getMatchingModuleConfigurations(EcucValueCollection ecuConfiguration, EcucModuleDef moduleDef) {
		List<EcucModuleConfigurationValues> ecucModuleConfigs = new ArrayList<EcucModuleConfigurationValues>();
		for (EcucModuleConfigurationValuesRefConditional ecucModuleConfigValuesRefs : ecuConfiguration.getEcucValues()) {
			EcucModuleConfigurationValues ecucModuleConfig = ecucModuleConfigValuesRefs.getEcucModuleConfigurationValues();
			if (ecucModuleConfig != null && moduleDef.equals(ecucModuleConfig.getDefinition())) {
				ecucModuleConfigs.add(ecucModuleConfig);
			}
		}
		return ecucModuleConfigs;
	}

}
