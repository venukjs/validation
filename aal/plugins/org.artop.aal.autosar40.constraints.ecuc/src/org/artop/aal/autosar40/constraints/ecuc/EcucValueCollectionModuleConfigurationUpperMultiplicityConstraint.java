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
import java.util.HashMap;
import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.AbstractModelConstraintWithPrecondition;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar40.ecucdescription.EcucModuleConfigurationValues;
import autosar40.ecucdescription.EcucModuleConfigurationValuesRefConditional;
import autosar40.ecucdescription.EcucValueCollection;
import autosar40.ecucparameterdef.EcucModuleDef;
import autosar40.genericstructure.varianthandling.PositiveIntegerValueVariationPoint;

/**
 * The class validate the constraint for EcucValueCollection. EcucModuleConfigurationValues reference in each
 * EcucValueCollection must respect the upper multiplicity of the imported EcucModuleDef
 */
public class EcucValueCollectionModuleConfigurationUpperMultiplicityConstraint extends AbstractModelConstraintWithPrecondition {

	static final String MULTIPLICITY_INFINITY = "*"; //$NON-NLS-1$
	static final String SEPARATOR = ", "; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcucValueCollection;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		EcucValueCollection ecucValueCollection = (EcucValueCollection) ctx.getTarget();

		String invalidModuleDefs = new String();
		String invalidmoduleConf = new String();

		HashMap<EObject, EObject> refinedModuleDefs = new HashMap<EObject, EObject>();

		for (EcucModuleConfigurationValuesRefConditional ecucModuleConfigValuesRef : ecucValueCollection.getEcucValues()) {
			/*
			 * Get Module Definition from Module Configuration included in the target ECUConfiguration
			 */
			EcucModuleConfigurationValues ecucModuleConfigValues = ecucModuleConfigValuesRef.getEcucModuleConfigurationValues();
			if (ecucModuleConfigValues != null) {
				EcucModuleDef ecucModuleDef = ecucModuleConfigValues.getDefinition();
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

		for (EObject moduleDef : refinedModuleDefs.keySet()) {

			/*
			 * The upper multiplicity definition.
			 */
			String upperMultiplicity = new String();
			PositiveIntegerValueVariationPoint upperVarPoint = ((EcucModuleDef) moduleDef).getUpperMultiplicity();
			if (upperVarPoint != null) {
				upperMultiplicity = upperVarPoint.getMixedText();
			}

			if (upperMultiplicity != null && !upperMultiplicity.equals(MULTIPLICITY_INFINITY)) {
				/*
				 * Retrieve Modules having the same Definition.
				 */
				List<EObject> similarModuleConfs = getSimilarModuleConfigurations(ecucValueCollection,
						(EcucModuleConfigurationValues) refinedModuleDefs.get(moduleDef));
				/*
				 * Verify if upper multiplicity is respected or not.
				 */
				try {
					if (similarModuleConfs.size() > Integer.valueOf(upperMultiplicity)) {
						invalidModuleDefs += ((EcucModuleDef) moduleDef).getShortName() + SEPARATOR;
						invalidmoduleConf += ((EcucModuleConfigurationValues) refinedModuleDefs.get(moduleDef)).getShortName() + SEPARATOR;
					}
				} catch (NumberFormatException ex) {
					return ctx.createFailureStatus(NLS.bind(Messages.generic_notValidFormat, upperMultiplicity));
				}
			}
		}

		if (invalidModuleDefs.length() != 0) {
			// Remove redundant ", " at the end
			invalidModuleDefs = invalidModuleDefs.substring(0, invalidModuleDefs.length() - SEPARATOR.length());
			invalidmoduleConf = invalidmoduleConf.substring(0, invalidmoduleConf.length() - SEPARATOR.length());

			return ctx.createFailureStatus(NLS.bind(Messages.modulesConfiguration_moduleDefTooMuch, new Object[] { invalidModuleDefs,
					AutosarURIFactory.getAbsoluteQualifiedName(ecucValueCollection), invalidmoduleConf }));
		}

		return status;
	}

	/**
	 * Get the similar EcucModuleConfigurationValues which have the same definition with the given
	 * EcucModuleConfigurationValues.
	 * 
	 * @param ecuConfiguration
	 *            The EcucValueCollection
	 * @param moduleConfiguration
	 *            The EcucModuleConfigurationValues
	 * @return The similar EcucModuleConfigurationValues have the same definition with the given
	 *         EcucModuleConfigurationValues
	 */
	private List<EObject> getSimilarModuleConfigurations(EcucValueCollection ecuConfiguration, EcucModuleConfigurationValues moduleConfiguration) {

		List<EObject> similarModuleConfs = new ArrayList<EObject>();

		/*
		 * Retrieve the definition of the given Module Configuration.
		 */
		EcucModuleDef moduleDef = moduleConfiguration.getDefinition();

		if (moduleDef != null) {

			/*
			 * Candidates list is initialized with Module Configurations declared inside the given ECU Configuration.
			 */
			EList<EcucModuleConfigurationValuesRefConditional> ecucModuleConfValuesRefs = ecuConfiguration.getEcucValues();

			for (EcucModuleConfigurationValuesRefConditional ecucModuleConfValuesRef : ecucModuleConfValuesRefs) {
				EcucModuleConfigurationValues candidateModuleConf = ecucModuleConfValuesRef.getEcucModuleConfigurationValues();
				if (moduleDef.equals(candidateModuleConf.getDefinition())) {
					similarModuleConfs.add(candidateModuleConf);
				}
			}
		}

		return similarModuleConfs;
	}

}
