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
 *     Continental AG - Mark class as Splitable aware.
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractGConfigParameterSymbolicNameValueModifyConstraint extends AbstractSplitModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GConfigParameter;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GConfigParameter configParameter = (GConfigParameter) ctx.getTarget();

		GConfigParameter refinedConfigParameter = EcucUtil.getConfigParameterInRefinedModuleDef(configParameter);

		if (refinedConfigParameter != null) {
			String vSpecifParamConfContainerDefPath = AutosarURIFactory.getAbsoluteQualifiedName(configParameter);
			String refinedModuleDefPath = AutosarURIFactory.getAbsoluteQualifiedName(EcucUtil.getParentRefinedModuleDef(configParameter));

			IStatus failureStatus = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.configParameter_symbolicNameValueModified,
					vSpecifParamConfContainerDefPath, refinedModuleDefPath));

			Boolean vSpecifSymbolicNameValue = configParameter.gGetSymbolicNameValue();
			Boolean refinedSymbolicNameValue = refinedConfigParameter.gGetSymbolicNameValue();

			if (!isSetSymbolicNameValue(configParameter) && !isSetSymbolicNameValue(refinedConfigParameter)) {
				// No failure notification since values are the same!
			} else if (isSetSymbolicNameValue(configParameter) ^ isSetSymbolicNameValue(refinedConfigParameter)) {
				return failureStatus;
			} else if (!vSpecifSymbolicNameValue.equals(refinedSymbolicNameValue)) {
				return failureStatus;
			}
		}

		return ctx.createSuccessStatus();
	}

	protected abstract boolean isSetSymbolicNameValue(GConfigParameter configParameter);

}
