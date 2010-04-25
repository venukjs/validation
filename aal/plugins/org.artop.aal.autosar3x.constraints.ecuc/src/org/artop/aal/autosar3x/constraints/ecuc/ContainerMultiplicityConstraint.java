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

import java.util.ArrayList;
import java.util.List;

import org.artop.aal.autosar3x.constraints.ecuc.internal.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;
import autosar3x.genericstructure.infrastructure.identifiable.Identifiable;

public class ContainerMultiplicityConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof Container;

		final IStatus status;

		Container container = (Container) ctx.getTarget();
		ContainerDef containerDef = container.getDefinition();

		if (null != containerDef && false == containerDef.eIsProxy()) {
			if (containerDef instanceof ChoiceContainerDef) {
				status = validateChoiceContainer(ctx, container, (ChoiceContainerDef) containerDef);
			} else {
				status = validateParamConfContainer(ctx, container, (ParamConfContainerDef) containerDef);
			}

		} else {
			// if there is no definition, we assume, that multiplicity is OK
			status = ctx.createSuccessStatus();
		}

		return status;
	}

	private IStatus validateChoiceContainer(IValidationContext ctx, Container container, ChoiceContainerDef choiceContainerDef) {
		final IStatus status;

		List<Container> allSubContainers = EcucUtil.getAllSubContainersOf(container);

		List<Identifiable> identifiables = new ArrayList<Identifiable>();
		identifiables.addAll(allSubContainers);
		int numberOfUniqueShortNames = EcucUtil.getNumberOfUniqueShortNames(identifiables);

		// choice container may only contain a single subcontainer
		if (1 != numberOfUniqueShortNames) {
			status = ctx.createFailureStatus("ChoiceContainer must contain exactly one subcontainer");
			ctx.addResults(allSubContainers);
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}

	private IStatus validateParamConfContainer(IValidationContext ctx, Container container, ParamConfContainerDef paramConfContainerDef) {
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		List<Container> allSubContainers = EcucUtil.getAllSubContainersOf(container);
		List<ContainerDef> subContainerDefs = paramConfContainerDef.getSubContainers();
		for (ContainerDef currentSubContainerDef : subContainerDefs) {
			int numberOfSubContainers = EcucUtil.getNumberOfUniqueContainersByDefinition(allSubContainers, currentSubContainerDef);
			multiStatus.add(validateLowerMultiplicity(ctx, numberOfSubContainers, currentSubContainerDef));
			multiStatus.add(validateUpperMultiplicity(ctx, numberOfSubContainers, currentSubContainerDef));
		}
		return multiStatus;
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
