/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services and others.
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

import gautosar.gecucdescription.GLinkerSymbolValue;
import gautosar.gecucdescription.GStringValue;
import gautosar.gecucparameterdef.GLinkerSymbolDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Abstract superclass for the constraints implementations on a linker symbol
 * value.
 * 
 */
public abstract class AbstractGLinkerSymbolValueBasicConstraint extends
		AbstractGStringValueBasicConstraint
{

	final String STRING_PATTERN = "[a-zA-Z]([a-zA-Z0-9_])*"; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		// return
		// GecucdescriptionPackage.eINSTANCE.getGLinkerSymbolValue().equals(ctx.getTarget().eClass());
		return ctx.getTarget() instanceof GLinkerSymbolValue;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{
		GLinkerSymbolValue gLinkerSymbolValue = (GLinkerSymbolValue) ctx
				.getTarget();
		IStatus status = ctx.createSuccessStatus();

		status = validateDefinition(ctx, gLinkerSymbolValue);
		if (status.isOK())
		{
			status = validateValue(ctx, gLinkerSymbolValue);
		}
		return status;
	}

	/**
	 * Performs the validation on the definition of the given
	 * <code>gLinkerSymbolValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gLinkerSymbolValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	private IStatus validateDefinition(IValidationContext ctx,
			GLinkerSymbolValue gLinkerSymbolValue)
	{
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gLinkerSymbolValue);
		if (status.isOK())
		{
			if (!(gLinkerSymbolValue.gGetDefinition() instanceof GLinkerSymbolDef))
			{
				status = ctx.createFailureStatus(NLS.bind(
						Messages.generic_definitionNotOfType,
						"linker symbol param def"));
			}
		}
		return status;
	}

	@Override
	protected IStatus validateValue(IValidationContext ctx,
			GStringValue gLinkerSymbolValue)
	{
		IStatus status = super.validateValue(ctx, gLinkerSymbolValue);
		if (!status.isOK())
		{
			return status;
		}

		String value = gLinkerSymbolValue.gGetValue();
		// check that value length is between 1 and 255 characters
		if (0 == value.length())
		{
			return ctx.createFailureStatus(Messages.generic_emptyValue);
		} else if (255 < value.length())
		{
			return ctx.createFailureStatus(Messages.string_valueTooBig); //$NON-NLS-1$
		} else if (false == value.matches(STRING_PATTERN))
		{
			return ctx
					.createFailureStatus(Messages.string_valueNoIdentifier);
		}
		
		return ctx.createSuccessStatus();

	}
}
