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
import gautosar.gecucparameterdef.GFloatParamDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractGFloatParamDefLowerLimitConstraint extends AbstractSplitModelConstraintWithPrecondition {

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
				 * An error is raised if lower limit in the Vendor Specific ModuleDef smaller than corresponding one in
				 * the Refined ModuleDef
				 */
				if (refinedMinLimit.isInfinite()) {
					valid = true;
				} else {
					if (vSpecifMinLimit.isInfinite()) {
						valid = false;
					} else {
						valid = vSpecifMinLimit.compareTo(refinedMinLimit) >= 0 ? true : false;
					}
				}
			}

			if (!valid) {
				GParamConfContainerDef parent = (GParamConfContainerDef) refinedFloatParamDef.eContainer();
				EObject refineModuleDef = EcucUtil.getParentModuleDefForContainerDef(parent);

				return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.floatParamDef_LowerLimitSmallerInVendorSpecificModuleDefinition,
						AutosarURIFactory.getAbsoluteQualifiedName(refineModuleDef)));
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
