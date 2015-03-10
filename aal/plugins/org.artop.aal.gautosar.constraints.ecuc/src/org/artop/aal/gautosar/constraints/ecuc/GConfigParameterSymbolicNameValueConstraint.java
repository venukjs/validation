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
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

/**
 * The class validate the constraints implementations on an <em>ConfigParameter</em>'s SymbolicNameValue
 */
public class GConfigParameterSymbolicNameValueConstraint extends AbstractSplitModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GConfigParameter;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GConfigParameter configParameter = (GConfigParameter) ctx.getTarget();

		/*
		 * The Symbolic Name Value attribute of the current Configuration Parameter.
		 */
		Boolean symbNameValue = configParameter.gGetSymbolicNameValue();

		if (Boolean.TRUE.equals(symbNameValue)) {
			/*
			 * The parent Parameter Configuration Container Definition.
			 */
			EObject containerDef = configParameter.eContainer();
			if (containerDef instanceof GParamConfContainerDef) {
				GParamConfContainerDef paramConfContainerDef = (GParamConfContainerDef) containerDef;
				for (GConfigParameter configParam : paramConfContainerDef.gGetParameters()) {
					/* Configuration Parameter being validated should not analyzed twice. */
					if (configParam.equals(configParameter)) {
						continue;
					}

					if (Boolean.TRUE.equals(configParam.gGetSymbolicNameValue())) {
						/*
						 * Constraint can not be validated if more that one Configuration Parameter has a 'Symbolic Name
						 * Value' set to "true".
						 */
						return ctx.createFailureStatus(/* NLS.bind( */EcucConstraintMessages.configParameter_symbolicNameValueIsMultiDeclared/*
																																			 * ,
																																			 * AutosarURIFactory
																																			 * .
																																			 * getAbsoluteQualifiedName
																																			 * (
																																			 * configParameter
																																			 * )
																																			 * )
																																			 */);
					}
				}
			}
		} else {
			//
			// Symbolic Name Value is null or false. Constraint is not applicable.
			//
		}

		return ctx.createSuccessStatus();
	}
}
