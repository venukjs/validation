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
package org.artop.aal.autosar40.constraints.ecuc;

import org.artop.aal.gautosar.constraints.ecuc.AbstractModelConstraintWithPrecondition;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar40.ecucparameterdef.EcucConfigurationClassEnum;
import autosar40.ecucparameterdef.EcucConfigurationVariantEnum;
import autosar40.ecucparameterdef.EcucImplementationConfigurationClass;
import autosar40.ecucparameterdef.EcucParameterDef;

public class EcucImplementationConfigurationClassPreCompileConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcucImplementationConfigurationClass;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		EcucImplementationConfigurationClass implementationConfigClass = (EcucImplementationConfigurationClass) ctx.getTarget();

		if (!(implementationConfigClass.eContainer() instanceof EcucParameterDef)) {
			return ctx.createSuccessStatus();
		}

		EcucParameterDef cp = (EcucParameterDef) implementationConfigClass.eContainer();

		EcucConfigurationVariantEnum cv = implementationConfigClass.getConfigVariant();
		EcucConfigurationClassEnum cc = implementationConfigClass.getConfigClass();

		if (cv == EcucConfigurationVariantEnum.VARIANT_PRE_COMPILE) {
			if (cc != EcucConfigurationClassEnum.PRE_COMPILE && cc != EcucConfigurationClassEnum.PUBLISHED_INFORMATION) {
				return ctx.createFailureStatus(NLS.bind(Messages.configParameter_configurationVariantRespectAsPreCompileOrPublished, cp
						.getShortName()));
			}
		}

		return ctx.createSuccessStatus();
	}

}
