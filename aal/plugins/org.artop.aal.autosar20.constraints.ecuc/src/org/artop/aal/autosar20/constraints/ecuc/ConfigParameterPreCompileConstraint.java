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
package org.artop.aal.autosar20.constraints.ecuc;

import org.artop.aal.gautosar.constraints.ecuc.AbstractModelConstraintWithPrecondition;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar20.ecucparameterdef.ConfigParameter;
import autosar20.ecucparameterdef.ConfigurationClass;
import autosar20.ecucparameterdef.ModuleDef;

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
			if (!(cp.getImplementationConfigClass() == ConfigurationClass.PRE_COMPILE)) {
				return ctx.createFailureStatus(NLS.bind(Messages.configParameter_configurationVariantRespectAsPreCompile, cp.getShortName()));
			}

		}

		return status;
	}

}
