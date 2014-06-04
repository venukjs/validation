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
 *     Continental AG - migration to gautosar, added splitable support for validation
 * 
 * </copyright>
 */
package org.artop.aal.validation.constraints;

import org.artop.aal.gautosar.services.splitting.Splitable;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

/**
 * Abstract superclass for the constraint implementations that check before validation that a precondition is fulfilled.
 */
public abstract class AbstractModelConstraintWithPrecondition extends AbstractModelConstraint {

	private boolean isSplit;

	@Override
	final public IStatus validate(IValidationContext ctx) {
		final IStatus status;
		isSplit = ctx.getTarget() instanceof Splitable;

		if (isApplicable(ctx) && (!isSplit || isSplit && isSplitAware())) {
			status = doValidate(ctx);
		} else {
			status = ctx.createSuccessStatus();
		}
		return status;
	}

	/**
	 * Returns whether the constraint is applicable by checking that a precondition is fulfilled.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @return <code>true</code>, if the constraint is applicable, <code>false</code>, otherwise.
	 */
	abstract protected boolean isApplicable(IValidationContext ctx);

	/**
	 * Performs the validation, by checking condition(s), depending on the target object on which the constraint is
	 * applicable.
	 * 
	 * @param ctx
	 *            the context containing the model element, on which the constraint is executed
	 * @return a status with the result of the validation
	 */
	abstract protected IStatus doValidate(IValidationContext ctx);

	/**
	 * Returns whether the implementation of the constraint can also handle splitable objects, not only file-based. By
	 * default, it returns <code>false</code>.
	 * 
	 * @return <code>true</code>, if the constraint is splitable-aware, <code>false</code>, otherwise.
	 */
	protected boolean isSplitAware() {
		return false;
	}

	/**
	 * Returns whether this constraint is executed in a splitable-context. The implementation of the constraint needs to
	 * be adapted to this context, that indicates if the target object, on which the constraint is executed, is
	 * file-based or is a merged instance of a split object.
	 * 
	 * @return <code>true</code>, if the constraint is executed in splitable context, <code>false</code>, otherwise
	 */
	protected boolean inSplitableContext() {
		return isSplit;
	}

}
