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

import gautosar.gecucparameterdef.GAbstractStringParamDef;
import gautosar.gecucparameterdef.GFunctionNameDef;
import gautosar.gecucparameterdef.GLinkerSymbolDef;

import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>LinkerSymbolDef</em>'s default value
 */
public class GLinkerSymbolDefDefaultValueConstraint extends GStringParamDefDefaultValueConstraint {

	final String LINKER_STRING_REGEX = "[a-zA-Z][a-zA-Z0-9_]{0,31}"; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		// FIXME what is the default value of EcucLinkderSymbolDef (AR4.0)?
		return ctx.getTarget() instanceof GLinkerSymbolDef && !(ctx.getTarget() instanceof GFunctionNameDef);
	}

	@Override
	protected IStatus validateValue(IValidationContext ctx, GAbstractStringParamDef gLinkerSymbolDef) {
		String value = EcucUtil.getFeatureValue(gLinkerSymbolDef, "defaultValue"); //$NON-NLS-1$

		IStatus status = validateValueSet(ctx, gLinkerSymbolDef, value);
		if (!status.isOK()) {
			return status;
		}
		if (false == value.matches(LINKER_STRING_REGEX)) {
			return ctx.createFailureStatus(NLS.bind(Messages.paramDef_defaultValueNoIdentifier, gLinkerSymbolDef.gGetShortName()));
		}

		return ctx.createSuccessStatus();
	}

}
