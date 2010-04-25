/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public abstract class AbstractModelConstraintWithPrecondition extends AbstractModelConstraint {
	@Override
	final public IStatus validate(IValidationContext ctx) {
		final IStatus status;
		if (isApplicable(ctx)) {
			status = doValidate(ctx);
		} else {
			status = ctx.createSuccessStatus();
		}
		return status;
	}

	abstract protected boolean isApplicable(IValidationContext ctx);

	abstract protected IStatus doValidate(IValidationContext ctx);
}
