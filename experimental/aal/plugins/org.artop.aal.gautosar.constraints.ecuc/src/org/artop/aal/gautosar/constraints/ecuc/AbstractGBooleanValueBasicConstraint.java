/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy,  Continental Engineering Services  and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar 
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GBooleanValue;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GBooleanParamDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Abstract superclass for the constraints implementations on a boolean value (validates definition and value).
 *
 */
public abstract class AbstractGBooleanValueBasicConstraint extends
		AbstractGParameterValueConstraint
{

	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return ctx.getTarget() instanceof GBooleanValue;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx)
	{
		IStatus status = ctx.createSuccessStatus();
		GBooleanValue gBooleanValue = (GBooleanValue) ctx.getTarget();

		status = validateDefinitionRef(ctx, gBooleanValue);
		if (status.isOK())
		{
			status = validateValue(ctx, gBooleanValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gParameterValue);
		if (status.isOK())
		{
			if (!(gParameterValue.gGetDefinition() instanceof GBooleanParamDef))
			{
				status = ctx.createFailureStatus(NLS.bind(
						Messages.generic_definitionNotOfType,
						"boolean param def"));
			}
		}
		return status;
	}

	/**
	 * Performs the validation on the value of the given
	 * <code>gBooleanValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gBooleanValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateValue(IValidationContext ctx,
			GBooleanValue gBooleanValue)
	{
		return validateValueSet(ctx, gBooleanValue, gBooleanValue.gGetValue());
	}

}
