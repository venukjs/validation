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

import gautosar.gecucparameterdef.GEnumerationLiteralDef;
import gautosar.gecucparameterdef.GEnumerationParamDef;

import java.util.ArrayList;
import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>GEnumerationParamDef</em>'s default value undeclared in
 * Literals
 */
public class GEnumerationParamDefDefaultValueConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GEnumerationParamDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GEnumerationParamDef enumerationParamDef = (GEnumerationParamDef) ctx.getTarget();

		/* Get default value of the current Enumeration Parameter Definition, if any. */
		String defaultValue = enumerationParamDef.gGetDefaultValue();

		if (defaultValue != null && !defaultValue.isEmpty()) {
			/*
			 * Retrieves the literals declared under the current Enumeration Parameter Definition.
			 */
			List<String> literals = new ArrayList<String>();
			for (GEnumerationLiteralDef literal : enumerationParamDef.gGetLiterals()) {
				literals.add(literal.gGetShortName());
			}

			//
			// Perform the comparison between the default value and the declared literals.
			//
			if (!literals.contains(defaultValue)) {
				return ctx.createFailureStatus(NLS.bind(Messages.enumerationParamDef_defaultValueUndeclaredInLiterals, new String[] { defaultValue,
						AutosarURIFactory.getAbsoluteQualifiedName(enumerationParamDef) }));
			}
		}

		return ctx.createSuccessStatus();
	}

}