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

import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GIntegerParamDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import java.math.BigInteger;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractGIntegerParamDefLowerLimitConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GIntegerParamDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();

		GIntegerParamDef integerParamDef = (GIntegerParamDef) ctx.getTarget();
		/*
		 * The corresponding Integer Parameter Definition from the Refined Module Definition
		 */
		GConfigParameter refinedIntegerParamDef = EcucUtil.getFromRefined(integerParamDef);

		if (refinedIntegerParamDef != null) {
			/* Flag used to mark the lower limit as valid or not. */
			boolean valid = true;

			/*
			 * Lower limit of the Integer Parameter Definition in the Refined Module Definition.
			 */
			BigInteger refinedMinLimit = getMin(refinedIntegerParamDef);
			if (isSetMin(refinedIntegerParamDef)) {
				/*
				 * Lower limit of the Integer Parameter Definition in the Vendor Specific Module Definition.
				 */
				BigInteger vSpecifMinLimit = getMin(integerParamDef);

				/*
				 * A warning is raised if lower limit has been modified in the Vendor Specific ModuleDef.
				 */
				valid = refinedMinLimit.equals(vSpecifMinLimit);
			}

			if (!valid) {
				GParamConfContainerDef parent = (GParamConfContainerDef) refinedIntegerParamDef.eContainer();
				EObject refineModuleDef = EcucUtil.getParentModuleDefForContainerDef(parent);

				return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.integerParamDef_LowerLimitChangedInVendorSpecificModuleDefinition,
						AutosarURIFactory.getAbsoluteQualifiedName(integerParamDef), AutosarURIFactory.getAbsoluteQualifiedName(refineModuleDef)));
			}
		} else {
			//
			// Refined Integer Parameter Definition is null.
			// Does nothing more.
			//
		}
		return status;
	}

	protected abstract BigInteger getMin(EObject obj);

	protected abstract boolean isSetMin(EObject obj);

}
