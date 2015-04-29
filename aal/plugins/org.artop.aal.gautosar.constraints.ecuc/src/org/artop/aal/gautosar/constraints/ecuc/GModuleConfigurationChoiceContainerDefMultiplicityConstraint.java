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
 *     Continental AG - Mark class as Splitable aware.
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;

import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Class for the constraint implementation on an <em>GModuleConfiguration</em> ChoiceContainerDef multiplicity
 */
public class GModuleConfigurationChoiceContainerDefMultiplicityConstraint extends AbstractSplitModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GModuleConfiguration;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		GModuleConfiguration moduleConfiguration = (GModuleConfiguration) ctx.getTarget();

		GModuleDef moduleDef = null;
		moduleDef = moduleConfiguration.gGetDefinition();

		// Check if we have a definition linked to this configuration
		if (moduleDef == null) { // Nothing to do
			return multiStatus;
		}

		for (GContainerDef current : moduleDef.gGetContainers()) {
			if (current instanceof GChoiceContainerDef) {

				int numberOfChoiceContainers = EcucUtil.filterChoiceContainersByDefinition((GChoiceContainerDef) current, moduleConfiguration).size();
				if (!EcucUtil.isValidLowerMultiplicity(numberOfChoiceContainers, current)) {
					multiStatus
							.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.choiceContainerDef_multiplicityNotRespected, new Object[] {
									current.gGetShortName(), " is", "min", EcucUtil.getLowerMultiplicity(current), numberOfChoiceContainers }))); //$NON-NLS-1$ //$NON-NLS-2$
				}
				if (!EcucUtil.isValidUpperMultiplicity(numberOfChoiceContainers, current)) {
					multiStatus
							.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.choiceContainerDef_multiplicityNotRespected, new Object[] {
									current.gGetShortName(), " is", "max", EcucUtil.getUpperMultiplicity(current), numberOfChoiceContainers }))); //$NON-NLS-1$ //$NON-NLS-2$
				}
			}
		}

		return multiStatus;
	}

}
