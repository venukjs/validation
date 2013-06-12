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
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractGParamConfContainerDefMultipleConfigurationModifyConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GParamConfContainerDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GParamConfContainerDef containerDef = (GParamConfContainerDef) ctx.getTarget();

		GContainerDef containerDefInRefinedModuleDef = EcucUtil.getContainerDefInRefinedModuleDef(containerDef);

		/* If no Refined Container Definition can be found, just return. */
		if (containerDefInRefinedModuleDef == null || !(containerDefInRefinedModuleDef instanceof GParamConfContainerDef)) {
			return ctx.createSuccessStatus();
		}
		GParamConfContainerDef refinedParamConfContainerDef = (GParamConfContainerDef) containerDefInRefinedModuleDef;

		String vSpecifParamConfContainerDefPath = AutosarURIFactory.getAbsoluteQualifiedName(containerDef);
		String refinedParamConfContainerDefPath = AutosarURIFactory.getAbsoluteQualifiedName(refinedParamConfContainerDef);

		IStatus failureStatus = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.paramConfigContainerDef_multipleConfigurationModified,
				vSpecifParamConfContainerDefPath, refinedParamConfContainerDefPath));

		Boolean vSpecifMultipleConfiguration = getMultipleConfigurationContainer(containerDef);
		Boolean refinedMultipleConfiguration = getMultipleConfigurationContainer(refinedParamConfContainerDef);

		if (!isSetMultipleConfigurationContainer(containerDef) && !isSetMultipleConfigurationContainer(refinedParamConfContainerDef)) {
			// No failure notification since values are the same!
		} else if (isSetMultipleConfigurationContainer(containerDef) ^ isSetMultipleConfigurationContainer(refinedParamConfContainerDef)) {
			return failureStatus;
		} else if (!vSpecifMultipleConfiguration.booleanValue() == refinedMultipleConfiguration.booleanValue()) {
			return failureStatus;
		}

		return ctx.createSuccessStatus();
	}

	protected abstract Boolean getMultipleConfigurationContainer(GContainerDef containerDef);

	protected abstract Boolean isSetMultipleConfigurationContainer(GContainerDef containerDef);

}
