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
import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

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
 * Superclass for the constraints implementations on the multiplicity of subcontainers of a module configuration.
 */
public abstract class AbstractGModuleConfigurationSubContainerMultiplicityConstraint extends AbstractModelConstraintWithPreconditionAndIndex {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		boolean isApplicable = false;
		if (ctx.getTarget() instanceof GModuleConfiguration) {
			GModuleConfiguration gModuleConfiguration = (GModuleConfiguration) ctx.getTarget();
			GModuleDef gModuleDef = gModuleConfiguration.gGetDefinition();
			isApplicable = null != gModuleDef && false == gModuleDef.eIsProxy();
		}
		return isApplicable;
	}

	/**
	 * Returns whether the <code>multipleConfigurationContainer</code> flag for the given <code>containerDef</code> is
	 * set to true.
	 * 
	 * @param containerDef
	 * @return
	 */
	protected abstract boolean isMultipleConfigurationContainer(GParamConfContainerDef containerDef);

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		GModuleConfiguration gModuleConfiguration = (GModuleConfiguration) ctx.getTarget();
		GModuleDef gModuleDef = gModuleConfiguration.gGetDefinition();

		List<GContainer> allSubContainers = getEcucValidationIndex(ctx).getAllContainersOf(gModuleConfiguration);
		List<GContainerDef> subGContainerDefs = gModuleDef.gGetContainers();

		for (GContainerDef currentSubGContainerDef : subGContainerDefs) {
			int numberOfSubContainers = EcucUtil.getNumberOfUniqueContainersByDefinition(allSubContainers, currentSubGContainerDef);
			if (!EcucUtil.isValidLowerMultiplicity(numberOfSubContainers, currentSubGContainerDef)) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_minElementsExpected,
						new Object[] { EcucUtil.getLowerMultiplicity(currentSubGContainerDef), "subcontainers", //$NON-NLS-1$
								AutosarURIFactory.getAbsoluteQualifiedName(currentSubGContainerDef), numberOfSubContainers })));
			}

			if (currentSubGContainerDef instanceof GParamConfContainerDef) {
				if (isMultipleConfigurationContainer((GParamConfContainerDef) currentSubGContainerDef)) {
					return multiStatus;
				}
			}

			if (!EcucUtil.isValidUpperMultiplicity(numberOfSubContainers, currentSubGContainerDef)) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_maxElementsExpected,
						new Object[] { EcucUtil.getUpperMultiplicity(currentSubGContainerDef), "subcontainers", //$NON-NLS-1$
								AutosarURIFactory.getAbsoluteQualifiedName(currentSubGContainerDef), numberOfSubContainers })));
			}
		}

		return multiStatus;
	}
}
