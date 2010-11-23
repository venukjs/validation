/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GContainer;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import java.util.ArrayList;
import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Superclass for the constraints implementations on the subcontainers of a container.
 */
public abstract class AbstractGContainerSubContainerMultiplicityConstraint extends AbstractModelConstraintWithPreconditionAndIndex {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		boolean isApplicable = false;
		if (ctx.getTarget() instanceof GContainer) {
			GContainer gContainer = (GContainer) ctx.getTarget();
			GContainerDef gContainerDef = gContainer.gGetDefinition();
			isApplicable = null != gContainerDef && false == gContainerDef.eIsProxy();
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof GContainer;

		final IStatus status;

		GContainer gContainer = (GContainer) ctx.getTarget();
		GContainerDef gContainerDef = gContainer.gGetDefinition();

		if (gContainerDef instanceof GChoiceContainerDef) {
			status = validateChoiceContainer(ctx, gContainer, (GChoiceContainerDef) gContainerDef);
		} else {
			status = validateParamConfContainer(ctx, gContainer, (GParamConfContainerDef) gContainerDef);
		}

		return status;
	}

	private IStatus validateChoiceContainer(IValidationContext ctx, GContainer GContainer, GChoiceContainerDef gChoiceContainerDef) {
		final IStatus status;

		List<GContainer> allSubContainers = getEcucValidationIndex(ctx).getAllSubContainersOf(GContainer);

		List<GIdentifiable> gIdentifiables = new ArrayList<GIdentifiable>();
		gIdentifiables.addAll(allSubContainers);
		int numberOfUniqueShortNames = EcucUtil.getNumberOfUniqueShortNames(gIdentifiables);

		// choice GContainer may only contain a single subcontainer
		if (1 != numberOfUniqueShortNames) {
			status = ctx.createFailureStatus(NLS.bind(Messages.multiplicity_subContainersExpected, "choice container"));
			ctx.addResults(allSubContainers);
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}

	/**
	 * Returns whether the <code>multipleConfigurationContainer</code> flag for the given <code>containerDef</code> is
	 * set to true.
	 * 
	 * @param containerDef
	 * @return
	 */
	protected abstract boolean isMultipleConfigurationContainer(GParamConfContainerDef containerDef);

	/**
	 * Validates that the number of subcontainers of the given <code>gContainer</code> is >=
	 * <code>lowerMultiplicity</code> and <= <code>upperMultiplicity</code>. If one subcontainer has the
	 * <code>multipleConfigurationContainer</code> set to true, then the <code>upperMultiplicity</code> is considered
	 * <code>*</code>.
	 * 
	 * @param ctx
	 * @param gContainer
	 * @param gParamConfContainerDef
	 * @return
	 */
	private IStatus validateParamConfContainer(IValidationContext ctx, GContainer gContainer, GParamConfContainerDef gParamConfContainerDef) {
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		List<GContainer> allSubGContainers = getEcucValidationIndex(ctx).getAllSubContainersOf(gContainer);
		List<GContainerDef> subGContainerDefs = gParamConfContainerDef.gGetSubContainers();
		for (GContainerDef currentSubGContainerDef : subGContainerDefs) {
			int numberOfSubContainers = EcucUtil.getNumberOfUniqueContainersByDefinition(allSubGContainers, currentSubGContainerDef);
			if (!EcucUtil.isValidLowerMultiplicity(numberOfSubContainers, currentSubGContainerDef)) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(
						Messages.multiplicity_minElementsExpected,
						new Object[] { EcucUtil.getLowerMultiplicity(currentSubGContainerDef), "subcontainers",
								AutosarURIFactory.getAbsoluteQualifiedName(currentSubGContainerDef), numberOfSubContainers })));
			}

			if (currentSubGContainerDef instanceof GParamConfContainerDef) {
				if (isMultipleConfigurationContainer((GParamConfContainerDef) currentSubGContainerDef)) {
					return multiStatus;
				}
			}

			if (!EcucUtil.isValidUpperMultiplicity(numberOfSubContainers, currentSubGContainerDef)) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(
						Messages.multiplicity_maxElementsExpected,
						new Object[] { EcucUtil.getUpperMultiplicity(currentSubGContainerDef), "subcontainers",
								AutosarURIFactory.getAbsoluteQualifiedName(currentSubGContainerDef), numberOfSubContainers })));
			}
		}
		return multiStatus;
	}
}
