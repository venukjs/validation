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
import gautosar.gecucparameterdef.GFloatParamDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Superclass for the constraints implementations on a FloatParamDef upper limit.
 */
public abstract class AbstractGFloatParamDefUpperLimitConstraint extends AbstractModelConstraintWithPrecondition {

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
			/* Flag used to mark the Upper limit as valid or not. */
			boolean valid = true;

			/*
			 * Upper limit of the Float Parameter Definition in the Refined Module Definition.
			 */
			Double refinedMaxLimit = getMax(refinedFloatParamDef);
			if (isSetMax(refinedFloatParamDef)) {
				/*
				 * Upper limit of the Float Parameter Definition in the Vendor Specific Module Definition.
				 */
				Double vSpecifMaxLimit = getMax(floatParamDef);

				/*
				 * An error is raised if upper limit in the Vendor Specific ModuleDef is bigger than Refined ModuleDef
				 */
				if(refinedMaxLimit.isInfinite() ){
					valid = true;
				}else{
					if(vSpecifMaxLimit.isInfinite()){
						valid = false;
					}else{
						valid = vSpecifMaxLimit.compareTo(refinedMaxLimit)<=0 ? true : false;
					}
				}
			}

			if (!valid) {
				GParamConfContainerDef parent = (GParamConfContainerDef) refinedFloatParamDef.eContainer();
				EObject refinedModuleDef = EcucUtil.getParentModuleDefForContainerDef(parent);

				return ctx.createFailureStatus(NLS.bind(
						EcucConstraintMessages.floatParamDef_UpperLimitBiggerInVendorSpecificModuleDefinition,
						new Object[] { AutosarURIFactory.getAbsoluteQualifiedName(floatParamDef),
								AutosarURIFactory.getAbsoluteQualifiedName(refinedModuleDef) }));
			}
		} else {
			//
			// Refined Float Parameter Definition is null.
			// Does nothing more.
			//
		}

		return status;
	}

	protected abstract Double getMax(EObject obj);

	protected abstract boolean isSetMax(EObject obj);

}
