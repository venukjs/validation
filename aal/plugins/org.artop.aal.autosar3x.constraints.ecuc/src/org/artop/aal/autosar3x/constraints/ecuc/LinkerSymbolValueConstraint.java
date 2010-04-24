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
import autosar3x.ecucdescription.LinkerSymbolValue;
import autosar3x.ecucparameterdef.LinkerSymbolDef;

public class LinkerSymbolValueConstraint extends StringValueConstraint {
	final String STRING_PATTERN = "[a-zA-Z]([a-zA-Z0-9_])*"; //$NON-NLS-1$

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof LinkerSymbolValue;

		final IStatus status;

		LinkerSymbolValue linkerSymbolValue = (LinkerSymbolValue) ctx.getTarget();

		// apply this constraint to LinkerSymbolValues but not to its sub types
		if (EcucdescriptionPackage.eINSTANCE.getLinkerSymbolValue().equals(linkerSymbolValue.eClass())) {
			MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);
			multiStatus.add(validateDefinition(ctx, linkerSymbolValue));
			multiStatus.add(validateValue(ctx, linkerSymbolValue));
			status = multiStatus;
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}

	private IStatus validateDefinition(IValidationContext ctx, LinkerSymbolValue linkerSymbolValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, linkerSymbolValue);
		if (status.isOK()) {
			if (!(linkerSymbolValue.getDefinition() instanceof LinkerSymbolDef)) {
				status = ctx
						.createFailureStatus("[ecuc sws 3041] A LinkerSymbolValue stores a configuration value that is of definition type LinkerSymbolParameter.");
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, LinkerSymbolValue linkerSymbolValue) {

		IStatus status = super.validateValue(ctx, linkerSymbolValue);
		if (status.isOK()) {
			String value = linkerSymbolValue.getValue();
			// check that value length is between 1 and 255 characters
			if (0 == value.length()) {
				status = ctx.createFailureStatus("empty value"); //$NON-NLS-1$
			} else if (255 < value.length()) {
				status = ctx.createFailureStatus("length of value is greater than 255 characters"); //$NON-NLS-1$
			} else if (false == value.matches(STRING_PATTERN)) {
				status = ctx.createFailureStatus("value is not a \"common programming language identifier\"");
			} else {
				status = ctx.createSuccessStatus();
			}
		}

		return status;
	}
}
