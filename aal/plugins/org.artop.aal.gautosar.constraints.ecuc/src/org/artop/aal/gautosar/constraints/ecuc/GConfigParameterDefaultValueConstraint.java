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

import gautosar.gecucparameterdef.GBooleanParamDef;
import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GEnumerationParamDef;
import gautosar.gecucparameterdef.GFloatParamDef;
import gautosar.gecucparameterdef.GFunctionNameDef;
import gautosar.gecucparameterdef.GIntegerParamDef;
import gautosar.gecucparameterdef.GLinkerSymbolDef;
import gautosar.gecucparameterdef.GStringParamDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 */
public class GConfigParameterDefaultValueConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GConfigParameter;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GConfigParameter cfParam = (GConfigParameter) ctx.getTarget();

		/*
		 * The corresponding Configuration Parameter from the Refined Module Definition
		 */
		GConfigParameter configParamInRefinedModuleDef = EcucUtil.getConfigParameterInRefinedModuleDef(cfParam);

		/* If Configuration Parameter can not be found in Refined Module Definition, just return. */
		if (configParamInRefinedModuleDef == null) {
			return ctx.createSuccessStatus();
		}

		/* Flag used to mark the default value as modified or not. */
		boolean valid = true;

		/*
		 * Default value of the Configuration Parameter in the Refined Module Definition.
		 */
		// FIXME default value of ConfigParameter in AR4.0 is something special, need to be reworked
		String defaultValueInRefinedModuleDef = EcucUtil.getFeatureValue(configParamInRefinedModuleDef, "defaultValue"); //$NON-NLS-1$

		if (defaultValueInRefinedModuleDef != null) {
			/*
			 * Default value of the Configuration Parameter in the Vendor Specific Module Definition.
			 */
			String defaultValue = EcucUtil.getFeatureValue(cfParam, "defaultValue"); //$NON-NLS-1$

			/*
			 * A warning is raised if default value has been modified in the Vendor Specific ModuleDef.
			 */
			valid = defaultValue != null ? defaultValue.equals(defaultValueInRefinedModuleDef) : defaultValueInRefinedModuleDef == null;
		}

		if (!valid) {
			return ctx.createFailureStatus(NLS.bind(Messages.configParameter_defaultValueChanged, new Object[] { getConfigParameterType(cfParam),
					AutosarURIFactory.getAbsoluteQualifiedName(cfParam),
					AutosarURIFactory.getAbsoluteQualifiedName(EcucUtil.getParentModuleDef(configParamInRefinedModuleDef)) }));
		}

		return ctx.createSuccessStatus();
	}

	protected String getConfigParameterType(GConfigParameter parameter) {
		String type = "Config Parameter"; //$NON-NLS-1$
		if (GBooleanParamDef.class.isInstance(parameter)) {
			type = "Boolean Parameter"; //$NON-NLS-1$
		} else if (GEnumerationParamDef.class.isInstance(parameter)) {
			type = "Enumeration Parameter"; //$NON-NLS-1$
		} else if (GIntegerParamDef.class.isInstance(parameter)) {
			type = "Integer Parameter"; //$NON-NLS-1$
		} else if (GFloatParamDef.class.isInstance(parameter)) {
			type = "Float Parameter"; //$NON-NLS-1$
		} else if (GStringParamDef.class.isInstance(parameter)) {
			type = "String Parameter"; //$NON-NLS-1$
		} else if (GFunctionNameDef.class.isInstance(parameter)) {
			type = "Function Name Parameter"; //$NON-NLS-1$
		} else if (GLinkerSymbolDef.class.isInstance(parameter)) {
			type = "Linker Symbol Parameter"; //$NON-NLS-1$
		}
		return type;
	}

}
