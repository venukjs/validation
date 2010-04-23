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

import org.artop.aal.autosar3x.constraints.ecuc.internal.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucdescription.StringValue;
import autosar3x.ecucparameterdef.StringParamDef;

public class StringValueConstraint extends AbstractParameterValueConstraint {
	final String STRING_PATTERN = "[a-zA-Z]([a-zA-Z0-9_])*"; //$NON-NLS-1$

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof StringValue;

		MultiStatus status = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);
		StringValue stringValue = (StringValue) ctx.getTarget();

		status.add(validateDefinitionRef(ctx, stringValue));
		status.add(validateValue(ctx, stringValue));

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, ParameterValue parameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, parameterValue);
		if (status.isOK()) {
			if (!(parameterValue.getDefinition() instanceof StringParamDef)) {
				status = ctx.createFailureStatus("definition not of type StringParamDef");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, StringValue stringValue) {
		final IStatus status;
		if (false == stringValue.isSetValue()) {
			status = ctx.createFailureStatus("no value found");
		} else if (null == stringValue.getValue()) {
			status = ctx.createFailureStatus("value is null");
		} else {
			String value = stringValue.getValue();
			// check that value length is between 1 and 255 characters
			if (0 == value.length()) {
				status = ctx.createFailureStatus("empty string parameter value"); //$NON-NLS-1$
			} else if (255 < value.length()) {
				status = ctx.createFailureStatus("length of string parameter value is greater than 255 characters"); //$NON-NLS-1$
			} else if (false == value.matches(STRING_PATTERN)) {
				status = ctx.createFailureStatus("string parameter value is not a \"common programming language identifier\"");
			} else {
				status = ctx.createSuccessStatus();
			}
		}

		return status;
	}

}
