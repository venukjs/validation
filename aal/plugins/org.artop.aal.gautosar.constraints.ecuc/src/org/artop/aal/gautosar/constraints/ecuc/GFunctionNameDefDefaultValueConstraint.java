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

import gautosar.gecucparameterdef.GFunctionNameDef;

import org.eclipse.emf.validation.IValidationContext;

/**
 * The class validate the constraints implementations on an <em>LinkerSymbolDef</em>'s value
 */
public class GFunctionNameDefDefaultValueConstraint extends GLinkerSymbolDefDefaultValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		// FIXME what is the default value of EcucFunctionNameDef (AR4.0)?
		return ctx.getTarget() instanceof GFunctionNameDef;
	}

}
