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

import gautosar.gecucdescription.GContainer;
import gautosar.gecucparameterdef.GContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

/**
 * 
 * Superclass for the constraints implementations on container.
 * 
 */

public class GContainerBasicConstraint extends AbstractModelConstraintWithPrecondition
{
	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return ctx.getTarget() instanceof GContainer;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) 
	{
		return validateDefinitionRef(ctx, (GContainer) ctx.getTarget());
	}

	/**
	 * Performs the validation on the definition of the given
	 * <code>gContainer</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gContainer
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	private IStatus validateDefinitionRef(IValidationContext ctx, GContainer gContainer)
	{
		// check if definition is set and available
		final IStatus status;
		
		GContainerDef containerDef = gContainer.gGetDefinition();
		if (null == containerDef) 
		{
			status = ctx.createFailureStatus(Messages.generic_definitionReferenceNotSet);
		}
		else if (containerDef.eIsProxy())
		{
			status = ctx.createFailureStatus(Messages.generic_definitionReferenceNotResolved);
		} 
		else
		{
			status = ctx.createSuccessStatus();
		}
	
		return status;
	}

}
