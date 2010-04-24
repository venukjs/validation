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
import autosar3x.ecucdescription.LinkerSymbolValue;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.FunctionNameDef;

public class FunctionNameValueConstraint extends AbstractParameterValueConstraint {
	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof FunctionNameValue;

		FunctionNameValue functionNameValue = (FunctionNameValue) ctx.getTarget();

		MultiStatus status = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		status.add(validateDefinitionRef(ctx, functionNameValue));
		status.add(validateValue(ctx, functionNameValue));

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, ParameterValue parameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, parameterValue);
		if (status.isOK()) {
			if (!(parameterValue.getDefinition() instanceof FunctionNameDef)) {
				status = ctx
						.createFailureStatus("[ecuc sws 3005] A FunctionNameValue stores a configuration value that is of definition type FunctionNameParamDef.");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, LinkerSymbolValue linkerSymbolValue) {

		final IStatus status;
		if (false == linkerSymbolValue.isSetValue() || null == linkerSymbolValue.getValue()) {
			status = ctx
					.createFailureStatus("[ecuc sws 3034] each FunctionNameValue needs to have a value specified even if it is just copied from the defaultValue of the ECU Configuration Definition");
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}
}
