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

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

/**
 * 
 * Abstract superclass for the constraint implementations that check before
 * validation that a precondition is fulfilled.
 * 
 */
public abstract class AbstractModelConstraintWithPrecondition extends
		AbstractModelConstraint
{
	@Override
	final public IStatus validate(IValidationContext ctx)
	{
		final IStatus status;
		if (isApplicable(ctx))
		{
			status = doValidate(ctx);
		} else
		{
			status = ctx.createSuccessStatus();
		}
		return status;
	}

	/**
	 * Returns whether the constraint is applicable by checking that a
	 * precondition is fulfilled.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @return
	 */
	abstract protected boolean isApplicable(IValidationContext ctx);

	/**
	 * Performs the validation, by checking condition(s), depending on the
	 * target object on which the constraint is applicable.
	 * 
	 * @param ctx
	 * @return
	 */
	abstract protected IStatus doValidate(IValidationContext ctx);

}
