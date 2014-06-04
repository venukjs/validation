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
 *     Continental AG - refactoring
 *     
 * 
 * </copyright>
 */
package org.artop.aal.autosar21.constraints.ecuc;

import org.artop.aal.gautosar.constraints.ecuc.AbstractImplConfigClassAndVariantConstraint;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar21.ecucparameterdef.ConfigParameter;
import autosar21.ecucparameterdef.ConfigurationClass;
import autosar21.ecucparameterdef.ModuleDef;

public class ConfigParameterPreCompileConstraint extends AbstractImplConfigClassAndVariantConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof ConfigParameter;
	}

	@Override
	protected String getConfigParameterName(EObject target) {
		ConfigParameter cp = (ConfigParameter) target;

		if (cp != null) {
			return cp.gGetShortName();
		}

		return ""; //$NON-NLS-1$

	}

	@Override
	protected Enumerator getConfigClassEnum(EObject target) {
		ConfigParameter cp = (ConfigParameter) target;

		return cp.getImplementationConfigClass();

	}

	@Override
	protected boolean isExpectedConfigVariant(EObject target) {

		ConfigParameter cp = (ConfigParameter) target;

		// Let's obtain the parent ModulDef
		ModuleDef md = (ModuleDef) EcucUtil.getParentModuleDef(cp);

		if (md == null) {
			return false;
		}

		String configVariant = md.getImplementationConfigVariant();
		if (!md.isSetImplementationConfigVariant()) {
			return false;
		}

		return configVariant.equals("VARIANT-PRE-COMPILE"); //$NON-NLS-1$
	}

	@Override
	protected Enumerator[] getAllowedConfigClassEnum(EObject target) {
		return new Enumerator[] { ConfigurationClass.PRE_COMPILE, ConfigurationClass.PUBLISHED_INFORMATION };
	}

}
