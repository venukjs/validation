/**
 * <copyright>
 * 
 * Copyright (c) see4Sys and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Artop Software License 
 * Based on Released AUTOSAR Material (ASLR) which accompanies this 
 * distribution, and is available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     see4Sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GFloatParamDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractGFloatParamDefLowerLimitConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GFloatParamDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();

		GFloatParamDef floatParamDef = (GFloatParamDef) ctx.getTarget();
		/*
		 * The corresponding Float Parameter Definition from the Refined Module Definition
		 */
		GConfigParameter refinedFloatParamDef = EcucUtil.getFromRefined(floatParamDef);

		if (refinedFloatParamDef != null) {
			/* Flag used to mark the lower limit as valid or not. */
			boolean valid = true;

			/*
			 * Lower limit of the Float Parameter Definition in the Refined Module Definition.
			 */
			Double refinedMinLimit = getMin(refinedFloatParamDef);
			if (isSetMin(refinedFloatParamDef)) {
				/*
				 * Lower limit of the Float Parameter Definition in the Vendor Specific Module Definition.
				 */
				Double vSpecifMinLimit = getMin(floatParamDef);

				/*
				 * A warning is raised if lower limit has been modified in the Vendor Specific ModuleDef.
				 */
				valid = refinedMinLimit.equals(vSpecifMinLimit);
			}

			if (!valid) {
				GParamConfContainerDef parent = (GParamConfContainerDef) refinedFloatParamDef.eContainer();
				EObject refineModuleDef = EcucUtil.getParentModuleDefForContainerDef(parent);

				return ctx.createFailureStatus(NLS.bind(Messages.floatParamDef_LowerLimitChangedInVendorSpecificModuleDefinition, AutosarURIFactory
						.getAbsoluteQualifiedName(floatParamDef), AutosarURIFactory.getAbsoluteQualifiedName(refineModuleDef)));
			}
		} else {
			//
			// Refined Float Parameter Definition is null.
			// Does nothing more.
			//
		}
		return status;
	}

	protected abstract Double getMin(EObject obj);

	protected abstract boolean isSetMin(EObject obj);
}
