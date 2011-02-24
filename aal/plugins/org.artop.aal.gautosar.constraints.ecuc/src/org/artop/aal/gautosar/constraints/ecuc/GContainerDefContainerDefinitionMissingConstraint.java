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

import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 */
public class GContainerDefContainerDefinitionMissingConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GContainerDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GContainerDef containerDef = (GContainerDef) ctx.getTarget();

		/* Retrieves the parent Module Definition. */
		GModuleDef vSpecifModuleDef = EcucUtil.getParentModuleDefForContainerDef(containerDef);

		/* Try to get the Refined Module Definition. */
		GModuleDef refinedModuleDef = vSpecifModuleDef.gGetRefinedModuleDef();

		/*
		 * If Refined Module Definition is not null, the target is a Container Definition from the Vendor Specific side.
		 */
		if (refinedModuleDef != null) {
			/* The parent Container Definition of the target Container Definition. */
			GContainerDef vSpecifParentContainerDef = containerDef;

			/*
			 * If Container Definition is a direct child of a Module Definition, it has no parent Container Definition.
			 * In such a case, nothing more is done.
			 */
			if (vSpecifParentContainerDef != null) {
				//
				// Retrieves the Container Definition from the Refined side corresponding to the given Container
				// Definition from the Vendor Specific side.
				//
				GContainerDef refinedParentContainerDef = EcucUtil.findContainerDefInModuleDef(refinedModuleDef, vSpecifParentContainerDef);
				/* According to the type of the refined parent container... */
				String invalidConfigParameters = ""; //$NON-NLS-1$
				if (refinedParentContainerDef instanceof GParamConfContainerDef) {
					/* ... get sub-containers. */
					EList<GContainerDef> refinedContainers = ((GParamConfContainerDef) refinedParentContainerDef).gGetSubContainers();
					EList<GContainerDef> vSpecifContainers = ((GParamConfContainerDef) vSpecifParentContainerDef).gGetSubContainers();
					String[] results = EcucUtil.inspectContainersSub(refinedContainers, vSpecifContainers);
					if (results.length > 0) {
						for (String invalid : results) {
							invalidConfigParameters += invalid + ", "; //$NON-NLS-1$
						}
						// remove redundant ", " at the end
						invalidConfigParameters = invalidConfigParameters.substring(0, invalidConfigParameters.length() - 2);
					}

				} else if (refinedParentContainerDef instanceof GChoiceContainerDef) {

					/* ... get choices. */
					EList<GParamConfContainerDef> refinedContainers = ((GChoiceContainerDef) refinedParentContainerDef).gGetChoices();
					EList<GParamConfContainerDef> vSpecifContainers = ((GChoiceContainerDef) vSpecifParentContainerDef).gGetChoices();
					String[] results = EcucUtil.inspectContainersChoice(refinedContainers, vSpecifContainers);
					if (results.length > 0) {
						for (String invalid : results) {
							invalidConfigParameters += invalid + ", "; //$NON-NLS-1$
						}
						// remove redundant ", " at the end
						invalidConfigParameters = invalidConfigParameters.substring(0, invalidConfigParameters.length() - 2);
					}
				}

				if (invalidConfigParameters.length() > 0) {
					return ctx.createFailureStatus(NLS.bind(Messages.containerDef_containerDefMissing, new Object[] { invalidConfigParameters,
							AutosarURIFactory.getAbsoluteQualifiedName(containerDef) }));
				}
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
