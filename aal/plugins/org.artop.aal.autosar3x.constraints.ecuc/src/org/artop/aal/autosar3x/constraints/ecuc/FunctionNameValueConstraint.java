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

import autosar3x.ecucdescription.FunctionNameValue;
import autosar3x.ecucparameterdef.FunctionNameDef;

public class FunctionNameValueConstraint extends LinkerSymbolValueConstraint {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof FunctionNameValue;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		FunctionNameValue functionNameValue = (FunctionNameValue) ctx.getTarget();
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);
		multiStatus.add(validateDefinition(ctx, functionNameValue));
		multiStatus.add(validateValue(ctx, functionNameValue));
		return multiStatus;
	}

	protected IStatus validateDefinition(IValidationContext ctx, FunctionNameValue functionNameValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, functionNameValue);
		if (status.isOK()) {
			if (!(functionNameValue.getDefinition() instanceof FunctionNameDef)) {
				status = ctx
						.createFailureStatus("[ecuc sws 3005] A FunctionNameValue stores a configuration value that is of definition type FunctionNameParamDef.");
			}
		}
		return status;
	}

}
