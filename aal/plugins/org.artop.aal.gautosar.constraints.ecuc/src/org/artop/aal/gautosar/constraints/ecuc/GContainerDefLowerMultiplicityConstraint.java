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

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;

public class GContainerDefLowerMultiplicityConstraint extends AbstractSplitModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GContainerDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();

		GContainerDef containerDef = (GContainerDef) ctx.getTarget();

		/* Retrieves the parent Module Definition. */
		GModuleDef vSpecifModuleDef = EcucUtil.getParentModuleDefForContainerDef(containerDef);

		/* Try to get the Refined Module Definition. */
		GModuleDef refinedModuleDef = null;
		if (vSpecifModuleDef != null) {
			refinedModuleDef = vSpecifModuleDef.gGetRefinedModuleDef();
		}
		/*
		 * If Refined Module Definition is not null, the target is a Container Definition from the Vendor Specific side.
		 */
		if (refinedModuleDef != null) {

			GContainerDef vSpecifContainerDef = containerDef;

			/*
			 * Retrieves the Container Definition from the Refined side corresponding to the given Container Definition
			 * from the Vendor Specific side.
			 */
			GContainerDef refinedContainerDef = EcucUtil.findContainerDefInModuleDef(refinedModuleDef, vSpecifContainerDef);

			if (refinedContainerDef != null) {
				//
				// Perform the comparison between the two Container Definitions.
				//

				/* Flag used to mark the lower multiplicity as valid or not. */
				boolean valid = true;

				/* Lower multiplicity of the Container Definition in the Refined Module Definition. */
				String lowerMultiplicityInRefinedModuleDef = refinedContainerDef.gGetLowerMultiplicityAsString();

				/* Lower multiplicity of the Container Definition in the Vendor Specific Module Definition. */
				String lowerMultiplicity = vSpecifContainerDef.gGetLowerMultiplicityAsString();

				if (lowerMultiplicityInRefinedModuleDef == null || lowerMultiplicity == null) {
					// 1 of lower multiplicity is null, ignored
				} else if (!lowerMultiplicityInRefinedModuleDef.equals("*")) { //$NON-NLS-1$
					if (lowerMultiplicity.equals("*")) { //$NON-NLS-1$
						valid = false;
					} else {
						if (Integer.valueOf(lowerMultiplicity) < Integer.valueOf(lowerMultiplicityInRefinedModuleDef)) {
							valid = false;
						}
					}
				}

				if (!valid) {
					return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.containerDef_lowerMultiplicityMismatching,

							AutosarURIFactory.getAbsoluteQualifiedName(refinedContainerDef)));
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
		return status;
	}

}
