/**
 * <copyright>
 * 
 * Copyright (c) see4Sys and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Artop Software License 
 * Based on Released AUTOSAR Material (ASLR) which accompanies this 
 * distribution, and is available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     see4Sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Abstract superclass for the constraints implementations on a parameter value.
 */
public abstract class AbstractGParameterValueBasicConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GParameterValue;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		GParameterValue parameterValue = (GParameterValue) ctx.getTarget();

		if (parameterValue.gGetDefinition() == null) {
			return status;
		}

		if (!isSetValue(parameterValue)) {
			return ctx.createFailureStatus(NLS.bind(Messages.parameterValue_valueNotSet, parameterValue.gGetDefinition().gGetShortName()));
		}

		return status;
	}

	/**
	 * Return <code>true</code> if value is set, otherwise <code>false</code>
	 * 
	 * @param parameterValue
	 *            The GParameterValue
	 * @return <code>true</code> if value is set, otherwise <code>false</code>
	 */
	protected abstract boolean isSetValue(GParameterValue parameterValue);

}
