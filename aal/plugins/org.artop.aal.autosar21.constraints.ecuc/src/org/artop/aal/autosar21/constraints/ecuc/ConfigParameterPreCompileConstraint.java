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
package org.artop.aal.autosar21.constraints.ecuc;

import org.artop.aal.gautosar.constraints.ecuc.AbstractModelConstraintWithPrecondition;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar21.ecucparameterdef.ConfigParameter;
import autosar21.ecucparameterdef.ConfigurationClass;
import autosar21.ecucparameterdef.ModuleDef;

public class ConfigParameterPreCompileConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof ConfigParameter;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();

		ConfigParameter cp = (ConfigParameter) ctx.getTarget();

		// Let's obtain the parent ModulDef
		ModuleDef md = (ModuleDef) EcucUtil.getModuleDef(cp);

		if (md == null) {
			return status;
		}

		String configVariant = md.getImplementationConfigVariant();
		if (!md.isSetImplementationConfigVariant()) {
			return status;
		}
		if (configVariant.equals("VARIANT-PRE-COMPILE")) { //$NON-NLS-1$
			if (cp.getImplementationConfigClass() != ConfigurationClass.PRE_COMPILE
					&& cp.getImplementationConfigClass() != ConfigurationClass.PUBLISHED_INFORMATION) {
				return ctx.createFailureStatus(NLS.bind(Messages.configParameter_configurationVariantRespectAsPreCompileOrPublished, cp
						.getShortName()));
			}

		}

		return status;
	}

}
