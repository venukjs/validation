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
import org.artop.aal.common.resource.AutosarURIFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.ModuleConfiguration;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ModuleDef;

public class ModuleConfigurationSubContainerMultiplicityConstraint extends AbstractModelConstraintWithPrecondition {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		boolean isApplicable = false;
		if (ctx.getTarget() instanceof ModuleConfiguration) {
			ModuleConfiguration moduleConfiguration = (ModuleConfiguration) ctx.getTarget();
			ModuleDef moduleDef = moduleConfiguration.getDefinition();
			isApplicable = null != moduleDef && false == moduleDef.eIsProxy();
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		ModuleConfiguration moduleConfiguration = (ModuleConfiguration) ctx.getTarget();
		ModuleDef moduleDef = moduleConfiguration.getDefinition();

		List<Container> allSubContainers = EcucUtil.getAllContainersOf(moduleConfiguration);
		List<ContainerDef> subContainerDefs = moduleDef.getContainers();

		for (ContainerDef currentSubContainerDef : subContainerDefs) {
			int numberOfSubContainers = EcucUtil.getNumberOfUniqueContainersByDefinition(allSubContainers, currentSubContainerDef);
			if (!EcucUtil.isValidLowerMultiplicity(numberOfSubContainers, currentSubContainerDef)) {
				multiStatus.add(ctx.createFailureStatus("Expected min " + EcucUtil.getLowerMultiplicity(currentSubContainerDef)
						+ " SubContainers with definition=" + AutosarURIFactory.getAbsoluteQualifiedName(currentSubContainerDef) + ". Found "
						+ numberOfSubContainers + "."));
			} else if (!EcucUtil.isValidUpperMultiplicity(numberOfSubContainers, currentSubContainerDef)) {
				multiStatus.add(ctx.createFailureStatus("Expected max " + EcucUtil.getUpperMultiplicity(currentSubContainerDef)
						+ " SubContainers with definition=" + AutosarURIFactory.getAbsoluteQualifiedName(currentSubContainerDef) + ". Found "
						+ numberOfSubContainers + "."));
			} else {
				multiStatus.add(ctx.createSuccessStatus());
			}
		}

		return multiStatus;

	}

}
