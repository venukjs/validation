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
 *     Continental AG - abstractization and refactoring
 *
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractImplConfigClassAndVariantConstraint extends AbstractSplitModelConstraintWithPrecondition {

	@Override
	protected IStatus doValidate(IValidationContext ctx) {

		EObject target = ctx.getTarget();
		if (isExpectedConfigVariant(target)) {
			Enumerator configClasEnum = getConfigClassEnum(target);
			if (configClasEnum != null) {
				Enumerator[] allowedConfigClassEnum = getAllowedConfigClassEnum(target);
				for (Enumerator element : allowedConfigClassEnum) {
					if (element == configClasEnum) {
						return ctx.createSuccessStatus();
					}
				}
				String name = getConfigParameterName(ctx.getTarget());
				return ctx.createFailureStatus(/*
												 * NLS.bind(/* EcucConstraintMessages.
												 * configParameter_configurationVariantRespectAsPreCompilePublishedOrLink
												 * /*, name)
												 */);
			}

		}

		return ctx.createSuccessStatus();

	}

	protected abstract String getConfigParameterName(EObject target);

	protected abstract Enumerator getConfigClassEnum(EObject target);

	protected abstract boolean isExpectedConfigVariant(EObject target);

	protected abstract Enumerator[] getAllowedConfigClassEnum(EObject target);

}
