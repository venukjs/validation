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
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;

import org.artop.aal.gautosar.constraints.ecuc.AbstractImplConfigClassAndVariantConstraint;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucparameterdef.ConfigurationClass;
import autosar3x.ecucparameterdef.ConfigurationVariant;
import autosar3x.ecucparameterdef.ImplementationConfigClass;

public class ImplementationConfigClassPreCompileConstraint extends AbstractImplConfigClassAndVariantConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof ImplementationConfigClass;
	}

	@Override
	protected String getConfigParameterName(EObject target) {
		ImplementationConfigClass implementationConfigClass = (ImplementationConfigClass) target;
		GConfigParameter cp = (GConfigParameter) implementationConfigClass.eContainer();
		if (cp != null) {
			return cp.gGetShortName();
		}

		return ""; //$NON-NLS-1$

	}

	@Override
	protected Enumerator getConfigClassEnum(EObject target) {
		ImplementationConfigClass implementationConfigClass = (ImplementationConfigClass) target;
		return implementationConfigClass.getConfigClass();

	}

	@Override
	protected boolean isExpectedConfigVariant(EObject target) {
		ImplementationConfigClass implementationConfigClass = (ImplementationConfigClass) target;

		if (!(implementationConfigClass.eContainer() instanceof GConfigParameter)) {
			return false;
		}

		ConfigurationVariant cv = implementationConfigClass.getConfigVariant();

		return cv == ConfigurationVariant.VARIANT_PRE_COMPILE;
	}

	@Override
	protected Enumerator[] getAllowedConfigClassEnum(EObject target) {
		return new Enumerator[] { ConfigurationClass.PRE_COMPILE, ConfigurationClass.PUBLISHED_INFORMATION };
	}

}
