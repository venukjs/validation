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

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 */
public class GEnumerationParamDefEnumerationLiteralConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GEnumerationParamDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GEnumerationParamDef enumParam = (GEnumerationParamDef) ctx.getTarget();

		/*
		 * The corresponding Enumeration Parameter Definition from the Refined Module Definition
		 */
		GEnumerationParamDef refinedEnumParamDef = (GEnumerationParamDef) EcucUtil.getConfigParameterInRefinedModuleDef(enumParam);

		/* If Enumeration Parameter Definition can not be found in Refined Module Definition, just return. */
		if (refinedEnumParamDef == null) {
			return ctx.createSuccessStatus();
		}

		/* Flag used to mark the list of 'literals' as modified or not. */
		boolean valid = true;

		/*
		 * Literals of the Enumeration Parameter Definition in the Refined Module Definition.
		 */
		EList<GEnumerationLiteralDef> refinedLiterals = refinedEnumParamDef.gGetLiterals();

		/*
		 * Literals of the Enumeration Parameter Definition in the Vendor Specific Module Definition.
		 */
		EList<GEnumerationLiteralDef> vSpecifLiterals = enumParam.gGetLiterals();

		/*
		 * A warning is raised if at least one literal has been modified in the Vendor Specific ModuleDef.
		 */
		if (refinedLiterals == null) {
			valid = vSpecifLiterals == null;
		} else {
			for (GEnumerationLiteralDef vSpecifLiteralDef : vSpecifLiterals) {
				boolean vSpecifLiteralDefFoundInRefined = false;
				for (GEnumerationLiteralDef refinedLiteralDef : refinedLiterals) {
					if (refinedLiteralDef.gGetShortName().equals(vSpecifLiteralDef.gGetShortName())) {
						vSpecifLiteralDefFoundInRefined = true;
						break;
					}
				}
				if (!vSpecifLiteralDefFoundInRefined) {
					valid = false;
					break;
				}
			}
		}

		if (!valid) {
			return ctx.createFailureStatus(NLS.bind(Messages.enumerationParamDef_enumLiteralChanged, new Object[] {
					AutosarURIFactory.getAbsoluteQualifiedName(enumParam),
					AutosarURIFactory.getAbsoluteQualifiedName(EcucUtil.getParentModuleDef(refinedEnumParamDef)) }));
		}

		return ctx.createSuccessStatus();
	}
}
