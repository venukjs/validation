/**
 * <copyright>
 * 
 * Copyright (c) See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucparameterdef.GAbstractStringParamDef;
import gautosar.gecucparameterdef.GFunctionNameDef;
import gautosar.gecucparameterdef.GLinkerSymbolDef;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>LinkerSymbolDef</em>'s default value
 */
public abstract class AbstractGLinkerSymbolDefDefaultValueConstraint extends AbstractGStringParamDefDefaultValueConstraint {

	final String LINKER_STRING_REGEX = "[a-zA-Z][a-zA-Z0-9_]{0,31}"; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GLinkerSymbolDef && !(ctx.getTarget() instanceof GFunctionNameDef);
	}

	@Override
	protected IStatus validateValue(IValidationContext ctx, GAbstractStringParamDef gLinkerSymbolDef) {
		//		String value = EcucUtil.getFeatureValue(gLinkerSymbolDef, "defaultValue"); //$NON-NLS-1$

		if (!isDefaultValueSet(gLinkerSymbolDef)) {
			// default value is not set, ignored
			return ctx.createSuccessStatus();
		}
		//		String value = EcucUtil.getFeatureValue(gStringParamDef, "defaultValue"); //$NON-NLS-1$
		// IStatus status = validateValueSet(ctx, gStringParamDef, value);
		// if (!status.isOK()) {
		// return status;
		// }

		String value = getDefaultValue(gLinkerSymbolDef);
		if (false == value.matches(LINKER_STRING_REGEX)) {
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.paramDef_defaultValueNoIdentifier, gLinkerSymbolDef.gGetShortName()));
		}

		return ctx.createSuccessStatus();
	}

}
