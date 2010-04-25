/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import java.util.List;

import org.artop.aal.autosar3x.constraints.ecuc.internal.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.ModuleConfiguration;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ModuleDef;

public class ModuleConfigurationSubContainerMultiplicityConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof ModuleConfiguration;

		final IStatus status;

		ModuleConfiguration moduleConfiguration = (ModuleConfiguration) ctx.getTarget();
		ModuleDef moduleDef = moduleConfiguration.getDefinition();

		if (null == moduleDef || moduleDef.eIsProxy()) {
			// preconditions not met
			status = ctx.createSuccessStatus();
		} else {
			MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

			List<Container> allSubContainers = EcucUtil.getAllContainersOf(moduleConfiguration);
			List<ContainerDef> subContainerDefs = moduleDef.getContainers();
			for (ContainerDef currentSubContainerDef : subContainerDefs) {
				int numberOfSubContainers = EcucUtil.getNumberOfUniqueContainersByDefinition(allSubContainers, currentSubContainerDef);
				multiStatus.add(validateLowerMultiplicity(ctx, numberOfSubContainers, currentSubContainerDef));
				multiStatus.add(validateUpperMultiplicity(ctx, numberOfSubContainers, currentSubContainerDef));
			}
			status = multiStatus;
		}
		return status;

	}

	private IStatus validateLowerMultiplicity(IValidationContext ctx, int numberOfSubContainers, ContainerDef containerDef) {
		final IStatus status;

		// by default the multiplicity is 1
		int lowerMultiplicity = 1;
		try {
			if (containerDef.isSetLowerMultiplicity()) {
				lowerMultiplicity = Integer.parseInt(containerDef.getLowerMultiplicity());
			}
		} catch (NumberFormatException nfe) {
			// ContainerDef is currupt. that problem need to be reported by another constraint
		}

		if (numberOfSubContainers < lowerMultiplicity) {
			status = ctx.createFailureStatus("Expected " + lowerMultiplicity + " subcontainers with definition '" + containerDef.getShortName()
					+ "'. Only " + numberOfSubContainers + " found.");
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}

	private IStatus validateUpperMultiplicity(IValidationContext ctx, int numberOfSubContainers, ContainerDef containerDef) {
		final IStatus status;

		if ("*".equals(containerDef.getUpperMultiplicity())) {
			status = ctx.createSuccessStatus();
		} else {
			int upperMultiplicity = 1;
			try {
				if (containerDef.isSetUpperMultiplicity()) {
					upperMultiplicity = Integer.parseInt(containerDef.getUpperMultiplicity());
				}
			} catch (NumberFormatException nfe) {
				// ContainerDef is currupt. that problem need to be reported by another constraint
			}

			if (upperMultiplicity < numberOfSubContainers) {
				status = ctx.createFailureStatus("Expected max " + upperMultiplicity + " subcontainers with definition '"
						+ containerDef.getShortName() + "'. However " + numberOfSubContainers + " found.");
			} else {
				status = ctx.createSuccessStatus();
			}

		}
		return status;
	}
}
