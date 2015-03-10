/**
 * <copyright>
 *
 * Copyright (c) Continental AG and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 *
 * Contributors:
 *     Continental AG - Initial API and implementation
 *
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GModuleDef;

import java.util.List;
import java.util.Map;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractEcuModuleConfigReferenceUpperMultiplicityConstraint extends AbstractSplitModelConstraintWithPrecondition {

	protected static final String MULTIPLICITY_INFINITY = "*"; //$NON-NLS-1$
	protected static final String SEPARATOR = ", "; //$NON-NLS-1$

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();

		EObject target = ctx.getTarget();

		String invalidModuleDefs = new String();
		String invalidmoduleConf = new String();

		Map<GModuleDef, GModuleConfiguration> refinedModuleDefs = getRefinedModuleDefs(ctx.getTarget());

		for (GModuleDef moduleDef : refinedModuleDefs.keySet()) {

			/*
			 * The upper multiplicity definition.
			 */
			String upperMultiplicity = moduleDef.gGetUpperMultiplicityAsString();
			if (upperMultiplicity != null && !upperMultiplicity.equals("") && !upperMultiplicity.equals(MULTIPLICITY_INFINITY) //$NON-NLS-1$
					&& Boolean.FALSE.equals(moduleDef.gGetUpperMultiplicityInfinite())) {
				/*
				 * Retrieve Modules having the same Definition.
				 */
				List<GModuleConfiguration> similarModuleConfs = getSimilarModuleConfigurations(target, refinedModuleDefs.get(moduleDef));
				/*
				 * Verify if upper multiplicity is respected or not.
				 */
				try {
					if (similarModuleConfs.size() > Integer.valueOf(upperMultiplicity)) {
						invalidModuleDefs += moduleDef.gGetShortName() + SEPARATOR;
						invalidmoduleConf += refinedModuleDefs.get(moduleDef).gGetShortName() + SEPARATOR;
					}
				} catch (NumberFormatException ex) {
					return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.generic_notValidFormat, upperMultiplicity));
				}
			}
		}

		if (invalidModuleDefs.length() != 0) {
			// Remove redundant ", " at the end
			invalidModuleDefs = invalidModuleDefs.substring(0, invalidModuleDefs.length() - SEPARATOR.length());
			invalidmoduleConf = invalidmoduleConf.substring(0, invalidmoduleConf.length() - SEPARATOR.length());

			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.modulesConfiguration_moduleDefTooMuch, new Object[] { invalidModuleDefs,
					invalidmoduleConf }));
		}

		return status;
	}

	protected abstract Map<GModuleDef, GModuleConfiguration> getRefinedModuleDefs(EObject target);

	/**
	 * Get the similar ModuleConfigurations which have the same definition with the given moduleConfiguration.
	 *
	 * @param ecuConfiguration
	 *            The EcucValueCollection
	 * @param moduleConfiguration
	 *            The module configuration
	 * @return The similar module configurations
	 */
	protected abstract List<GModuleConfiguration> getSimilarModuleConfigurations(EObject ecuConfiguration, GModuleConfiguration moduleConfiguration);
}