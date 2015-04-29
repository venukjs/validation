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
package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;

import org.artop.aal.gautosar.constraints.ecuc.AbstractImplConfigClassAndVariantConstraint;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar40.ecucparameterdef.EcucConfigurationClassEnum;
import autosar40.ecucparameterdef.EcucConfigurationVariantEnum;
import autosar40.ecucparameterdef.EcucImplementationConfigurationClass;
import autosar40.ecucparameterdef.EcucParameterDef;

public class EcucImplementationConfigurationClassLinkTimeConstraint extends AbstractImplConfigClassAndVariantConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcucImplementationConfigurationClass;
	}

	@Override
	protected String getConfigParameterName(EObject target) {
		EcucImplementationConfigurationClass implementationConfigClass = (EcucImplementationConfigurationClass) target;
		GConfigParameter cp = (GConfigParameter) implementationConfigClass.eContainer();
		if (cp != null) {
			return cp.gGetShortName();
		}

		return ""; //$NON-NLS-1$

	}

	@Override
	protected Enumerator getConfigClassEnum(EObject target) {
		EcucImplementationConfigurationClass implementationConfigClass = (EcucImplementationConfigurationClass) target;
		return implementationConfigClass.getConfigClass();

	}

	@Override
	protected boolean isExpectedConfigVariant(EObject target) {
		EcucImplementationConfigurationClass implementationConfigClass = (EcucImplementationConfigurationClass) target;

		if (!(implementationConfigClass.eContainer() instanceof GConfigParameter)) {
			return false;
		}

		EcucConfigurationVariantEnum cv = implementationConfigClass.getConfigVariant();

		return cv == EcucConfigurationVariantEnum.VARIANT_LINK_TIME;
	}

	@Override
	protected Enumerator[] getAllowedConfigClassEnum(EObject target) {
		return new Enumerator[] { EcucConfigurationClassEnum.PRE_COMPILE, EcucConfigurationClassEnum.PUBLISHED_INFORMATION,
				EcucConfigurationClassEnum.LINK };
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		EcucImplementationConfigurationClass implementationConfigClass = (EcucImplementationConfigurationClass) ctx.getTarget();

		if (!(implementationConfigClass.eContainer() instanceof GConfigParameter)) {
			return ctx.createSuccessStatus();
		}

		EcucParameterDef cp = (EcucParameterDef) implementationConfigClass.eContainer();

		EcucConfigurationVariantEnum cv = implementationConfigClass.getConfigVariant();
		EcucConfigurationClassEnum cc = implementationConfigClass.getConfigClass();

		if (cv == EcucConfigurationVariantEnum.VARIANT_LINK_TIME) {
			if (cc != EcucConfigurationClassEnum.PRE_COMPILE && cc != EcucConfigurationClassEnum.PUBLISHED_INFORMATION
					&& cc != EcucConfigurationClassEnum.LINK) {
				return ctx.createFailureStatus(NLS.bind(
						EcucConstraintMessages.configParameter_configurationVariantRespectAsPreCompilePublishedOrLink, cp.getShortName()));
			}
		}

		return ctx.createSuccessStatus();
	}
}
