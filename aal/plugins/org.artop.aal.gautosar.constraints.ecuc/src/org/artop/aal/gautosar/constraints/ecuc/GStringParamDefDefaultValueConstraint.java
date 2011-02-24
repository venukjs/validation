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

import gautosar.gecucparameterdef.GAbstractStringParamDef;
import gautosar.gecucparameterdef.GFunctionNameDef;
import gautosar.gecucparameterdef.GLinkerSymbolDef;
import gautosar.gecucparameterdef.GStringParamDef;

import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>StringParamDef</em>'s default value
 */
public class GStringParamDefDefaultValueConstraint extends AbstractModelConstraintWithPrecondition {

	final String BASIC_STRING_REGEX = "^[a-zA-Z][\\w]+"; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		// FIXME what is the default value of EcucStringParamDef (AR4.0)?
		// FIXME do we need to add exception for EcucMultilineStringParamDef (AR4.0)?
		return ctx.getTarget() instanceof GStringParamDef && !(ctx.getTarget() instanceof GLinkerSymbolDef)
				&& !(ctx.getTarget() instanceof GFunctionNameDef);
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();

		GAbstractStringParamDef stringParamDef = (GAbstractStringParamDef) ctx.getTarget();

		status = validateValue(ctx, stringParamDef);

		return status;
	}

	/**
	 * Performs the validation on the value of the given <code>gStringParamDef</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gStringParamDef
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateValue(IValidationContext ctx, GAbstractStringParamDef gStringParamDef) {
		String value = EcucUtil.getFeatureValue(gStringParamDef, "defaultValue"); //$NON-NLS-1$

		IStatus status = validateValueSet(ctx, gStringParamDef, value);
		if (!status.isOK()) {
			return status;
		}
		if (false == value.matches(BASIC_STRING_REGEX)) {
			return ctx.createFailureStatus(NLS.bind(Messages.paramDef_defaultValueNoIdentifier, gStringParamDef.gGetShortName()));
		}

		return ctx.createSuccessStatus();

	}

	/**
	 * Performs the validation on the value of the given <code>gParameterValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gParameterValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateValueSet(IValidationContext ctx, GAbstractStringParamDef gStringParamDef, Object value) {
		if (null == value || value.equals("")) { //$NON-NLS-1$
			return ctx.createFailureStatus(Messages.generic_defaultValueNotSet);
		}

		return ctx.createSuccessStatus();
	}

}
