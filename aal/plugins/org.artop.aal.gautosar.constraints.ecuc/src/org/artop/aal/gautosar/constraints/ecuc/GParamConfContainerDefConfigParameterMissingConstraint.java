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

import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GModuleDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 */
public class GParamConfContainerDefConfigParameterMissingConstraint extends AbstractSplitModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GParamConfContainerDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GParamConfContainerDef containerDef = (GParamConfContainerDef) ctx.getTarget();

		/* Retrieves the parent Module Definition. */
		GModuleDef vSpecifModuleDef = EcucUtil.getParentModuleDefForContainerDef(containerDef);

		/* Try to get the Refined Module Definition. */
		GModuleDef refinedModuleDef = vSpecifModuleDef.gGetRefinedModuleDef();

		/*
		 * If Refined Module Definition is not null, the target is a Container Definition from the Vendor Specific side.
		 */
		if (refinedModuleDef != null) {
			GParamConfContainerDef vSpecifParamConfContainerDef = containerDef;

			//
			// Retrieves the Container Definition from the Refined side corresponding to the given Container
			// Definition from the Vendor Specific side.
			//
			GParamConfContainerDef refinedParamConfContainerDef = (GParamConfContainerDef) EcucUtil.findContainerDefInModuleDef(refinedModuleDef,
					vSpecifParamConfContainerDef);

			if (refinedParamConfContainerDef != null) {
				EList<GConfigParameter> vSpecifCommonConfigurationAttributes = vSpecifParamConfContainerDef.gGetParameters();
				EList<GConfigParameter> refinedCommonConfigurationAttributes = refinedParamConfContainerDef.gGetParameters();

				//
				// Perform the comparison between the two lists of Common Configuration Attributes.
				//
				String[] results = EcucUtil.inspectCommonConfigurationParameter(refinedCommonConfigurationAttributes,
						vSpecifCommonConfigurationAttributes);

				if (results.length > 0) {
					String invalidConfigParameters = ""; //$NON-NLS-1$
					for (String invalid : results) {
						invalidConfigParameters += invalid + ", "; //$NON-NLS-1$
					}
					// remove redundant ", " at the end
					invalidConfigParameters = invalidConfigParameters.substring(0, invalidConfigParameters.length() - 2);
					String tmp1 = " "; //$NON-NLS-1$
					String tmp2 = " is"; //$NON-NLS-1$
					if (results.length > 1) {
						tmp1 = "s "; //$NON-NLS-1$
						tmp2 = " are"; //$NON-NLS-1$
					}
					return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.paramConfigContainerDef_configParameterMissing, new Object[] {
							tmp1, invalidConfigParameters, tmp2, AutosarURIFactory.getAbsoluteQualifiedName(containerDef) }));
				}
			} else {
				//
				// Refined Container Definition is null.
				// Does nothing more.
				//
			}
		} else {
			//
			// Refined Module Definition is null; it means the target is not from the Vendor Specific side.
			// Does nothing more.
			//
		}
		return ctx.createSuccessStatus();
	}

}
