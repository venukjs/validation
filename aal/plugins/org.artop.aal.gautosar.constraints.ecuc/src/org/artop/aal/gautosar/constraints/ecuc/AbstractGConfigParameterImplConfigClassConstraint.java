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
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Superclass for the constraint implementations on ImplementationConfigurationClass of ConfigParameter
 */
public abstract class AbstractGConfigParameterImplConfigClassConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GConfigParameter;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GConfigParameter cfParam = (GConfigParameter) ctx.getTarget();

		/*
		 * The corresponding Parameter Definition from the Refined Module Definition
		 */
		GConfigParameter refinedParameterDef = EcucUtil.getConfigParameterInRefinedModuleDef(cfParam);

		/* If Parameter Definition can not be found in Refined Module Definition, just return. */
		if (refinedParameterDef == null) {
			return ctx.createSuccessStatus();
		}

		/* Flag used to mark the 'implementation configuration class' as modified or not. */
		boolean valid = compareImplConfigurationClass(cfParam, refinedParameterDef);

		if (!valid) {
			return ctx.createFailureStatus(NLS.bind(
					EcucConstraintMessages.configParameter_implConfigClassChanged,
					new Object[] { AutosarURIFactory.getAbsoluteQualifiedName(cfParam),
							AutosarURIFactory.getAbsoluteQualifiedName(EcucUtil.getParentModuleDef(refinedParameterDef)) }));
		}

		return ctx.createSuccessStatus();
	}

	/**
	 * Compare ImplementationConfigurationClass of 2 input ConfigParameter
	 * 
	 * @param cfParam1
	 *            ConfigParameter obj 1
	 * @param cfParam2
	 *            ConfigParameter obj 2
	 * @return
	 */
	protected abstract boolean compareImplConfigurationClass(GConfigParameter cfParam1, GConfigParameter cfParam2);
}
