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

import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucdescription.StringValue;
import autosar3x.ecucparameterdef.StringParamDef;

public class StringValueBasicConstraint extends AbstractParameterValueConstraint {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return EcucdescriptionPackage.eINSTANCE.getStringValue().equals(ctx.getTarget().eClass());
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		StringValue stringValue = (StringValue) ctx.getTarget();
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);
		multiStatus.add(validateDefinition(ctx, stringValue));
		multiStatus.add(validateValue(ctx, stringValue));
		return multiStatus;
	}

	protected IStatus validateDefinition(IValidationContext ctx, ParameterValue parameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, parameterValue);
		if (status.isOK()) {
			if (!(parameterValue.getDefinition() instanceof StringParamDef)) {
				status = ctx
						.createFailureStatus("[ecuc sws 3003] A StringValue stores a configuration value that is of definition type StringParamDef.");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, StringValue stringValue) {
		final IStatus status;
		if (false == stringValue.isSetValue() || null == stringValue.getValue()) {
			status = ctx
					.createFailureStatus("[ecuc sws 3034] each StringValue needs to have a value specified even if it is just copied from the defaultValue of the ECU Configuration Definition");
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}
}
