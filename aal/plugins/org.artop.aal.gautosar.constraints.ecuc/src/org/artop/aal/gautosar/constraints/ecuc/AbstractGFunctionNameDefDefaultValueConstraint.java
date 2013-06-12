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

import gautosar.gecucparameterdef.GFunctionNameDef;

import org.eclipse.emf.validation.IValidationContext;

/**
 * The class validate the constraints implementations on an <em>LinkerSymbolDef</em>'s value
 */
public abstract class AbstractGFunctionNameDefDefaultValueConstraint extends AbstractGLinkerSymbolDefDefaultValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GFunctionNameDef;
	}

}
