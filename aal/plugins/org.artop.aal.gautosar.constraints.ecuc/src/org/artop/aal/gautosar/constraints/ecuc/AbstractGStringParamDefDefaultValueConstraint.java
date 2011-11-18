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

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>StringParamDef</em>'s default value
 */
public abstract class AbstractGStringParamDefDefaultValueConstraint extends AbstractModelConstraintWithPrecondition {

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

		if (!isDefaultValueSet(gStringParamDef)) {
			// default value is not set, ignored
			return ctx.createSuccessStatus();
		}
		//		String value = EcucUtil.getFeatureValue(gStringParamDef, "defaultValue"); //$NON-NLS-1$
		// IStatus status = validateValueSet(ctx, gStringParamDef, value);
		// if (!status.isOK()) {
		// return status;
		// }

		String value = getDefaultValue(gStringParamDef);
		if (false == value.matches(BASIC_STRING_REGEX)) {
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.paramDef_defaultValueNoIdentifier, gStringParamDef.gGetShortName()));
		}

		return ctx.createSuccessStatus();

	}

	// /**
	// * Performs the validation on the value of the given <code>gParameterValue</code>.
	// *
	// * @param ctx
	// * the validation context that provides access to the current constraint evaluation environment
	// * @param gParameterValue
	// * the element on which the validation is performed.
	// * @return a status object describing the result of the validation.
	// */
	// protected IStatus validateValueSet(IValidationContext ctx, GAbstractStringParamDef gStringParamDef, Object value)
	// {
	//		if (null == value || value.equals("")) { //$NON-NLS-1$
	// return ctx.createFailureStatus(EcucConstraintMessages.generic_defaultValueNotSet);
	// }
	//
	// return ctx.createSuccessStatus();
	// }

	/**
	 * Check if the default value is set on the given parameter definition.
	 * 
	 * @param stringParamDef
	 *            the given parameter def
	 * @return <code>true</code> if the default value is set, <code>false</code> otherwise
	 */
	protected boolean isDefaultValueSet(GAbstractStringParamDef stringParamDef) {
		String defaultValue = getDefaultValue(stringParamDef);
		return defaultValue != null && !defaultValue.equals(""); //$NON-NLS-1$
	}

	/**
	 * Returns the default value of the given parameter definition.
	 * 
	 * @param stringParamDef
	 *            the given parameter def
	 * @return the default value
	 */
	protected abstract String getDefaultValue(GAbstractStringParamDef stringParamDef);

}
