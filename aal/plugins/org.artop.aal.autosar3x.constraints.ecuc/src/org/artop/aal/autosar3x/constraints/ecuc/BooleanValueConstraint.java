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

import autosar3x.ecucdescription.BooleanValue;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.BooleanParamDef;

public class BooleanValueConstraint extends AbstractParameterValueConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof BooleanValue;

		MultiStatus status = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);
		BooleanValue booleanValue = (BooleanValue) ctx.getTarget();

		status.add(validateDefinitionRef(ctx, booleanValue));
		status.add(validateValue(ctx, booleanValue));

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, ParameterValue parameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, parameterValue);
		if (status.isOK()) {
			if (!(parameterValue.getDefinition() instanceof BooleanParamDef)) {
				status = ctx.createFailureStatus("definition not of type BooleanParamDef");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, BooleanValue booleanValue) {
		final IStatus status;
		if (false == booleanValue.isSetValue()) {
			status = ctx.createFailureStatus("no value found");
		} else if (null == booleanValue.getValue()) {
			status = ctx.createFailureStatus("value is null");
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}

}
