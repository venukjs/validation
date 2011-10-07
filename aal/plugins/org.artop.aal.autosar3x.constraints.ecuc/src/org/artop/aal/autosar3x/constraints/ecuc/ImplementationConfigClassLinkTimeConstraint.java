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
package org.artop.aal.autosar3x.constraints.ecuc;

import org.artop.aal.gautosar.constraints.ecuc.AbstractModelConstraintWithPrecondition;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar3x.ecucparameterdef.ConfigParameter;
import autosar3x.ecucparameterdef.ConfigurationClass;
import autosar3x.ecucparameterdef.ConfigurationVariant;
import autosar3x.ecucparameterdef.ImplementationConfigClass;

public class ImplementationConfigClassLinkTimeConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof ImplementationConfigClass;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		ImplementationConfigClass implementationConfigClass = (ImplementationConfigClass) ctx.getTarget();

		if (!(implementationConfigClass.eContainer() instanceof ConfigParameter)) {
			return ctx.createSuccessStatus();
		}

		// The target ConfigParameter
		ConfigParameter cp = (ConfigParameter) implementationConfigClass.eContainer();

		ConfigurationVariant cv = implementationConfigClass.getConfigVariant();
		ConfigurationClass cc = implementationConfigClass.getConfigClass();

		if (cv == ConfigurationVariant.VARIANT_LINK_TIME) {
			if (cc != ConfigurationClass.PRE_COMPILE && cc != ConfigurationClass.PUBLISHED_INFORMATION && cc != ConfigurationClass.LINK) {
				return ctx.createFailureStatus(NLS.bind(Messages.configParameter_configurationVariantRespectAsPreCompilePublishedOrLink, cp
						.getShortName()));
			}
		}

		return ctx.createSuccessStatus();
	}

}
