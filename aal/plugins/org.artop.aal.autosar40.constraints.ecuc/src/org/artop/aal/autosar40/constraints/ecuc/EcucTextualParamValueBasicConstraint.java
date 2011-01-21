/**
 * <copyright>
 * 
 * Copyright (c) Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Continental Engineering Services - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GEnumerationLiteralDef;

import java.util.List;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGParameterValueConstraint;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar40.ecucdescription.EcucTextualParamValue;
import autosar40.ecucparameterdef.EcucAddInfoParamDef;
import autosar40.ecucparameterdef.EcucEnumerationParamDef;
import autosar40.ecucparameterdef.EcucFunctionNameDef;
import autosar40.ecucparameterdef.EcucLinkerSymbolDef;
import autosar40.ecucparameterdef.EcucMultilineStringParamDef;
import autosar40.ecucparameterdef.EcucStringParamDef;

public class EcucTextualParamValueBasicConstraint extends AbstractGParameterValueConstraint {

	final String STRING_PATTERN = "[a-zA-Z]([a-zA-Z0-9_])*"; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcucTextualParamValue;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		EcucTextualParamValue ecucTextualParamValue = (EcucTextualParamValue) ctx.getTarget();

		status = validateDefinitionRef(ctx, ecucTextualParamValue);
		if (status.isOK()) {
			status = validateValue(ctx, ecucTextualParamValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, GParameterValue gParameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gParameterValue);
		if (status.isOK()) {
			GConfigParameter definition = gParameterValue.gGetDefinition();
			if (!(definition instanceof EcucStringParamDef) && !(definition instanceof EcucMultilineStringParamDef)
					&& !(definition instanceof EcucLinkerSymbolDef) && !(definition instanceof EcucFunctionNameDef)
					&& !(definition instanceof EcucEnumerationParamDef) && !(definition instanceof EcucAddInfoParamDef)) {
				status = ctx.createFailureStatus(NLS.bind(Messages.generic_definitionNotOfType,
						"EcucStringParamDef/EcucLinkerSymbolDef/EcucFunctionNameDef")); //$NON-NLS-1$
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, EcucTextualParamValue ecucTextualParamValue) {
		String value = ecucTextualParamValue.getValue();
		GConfigParameter definition = ecucTextualParamValue.gGetDefinition();

		IStatus status = validateValueSet(ctx, ecucTextualParamValue, value);
		if (!status.isOK()) {
			return status;
		}

		if (value != null) {

			// check that value length is between 1 and 255 characters
			if (0 == value.length()) {
				return ctx.createFailureStatus(Messages.generic_emptyValue);
			}
			if (definition instanceof EcucLinkerSymbolDef || definition instanceof EcucFunctionNameDef) {
				if (255 < value.length()) {
					return ctx.createFailureStatus(Messages.string_valueTooBig);
				}
				if (false == value.matches(STRING_PATTERN)) {
					return ctx.createFailureStatus(Messages.string_valueNoIdentifier);
				}
			}

			if (definition instanceof EcucEnumerationParamDef) {
				EcucEnumerationParamDef enumerationParamDef = (EcucEnumerationParamDef) definition;
				List<GEnumerationLiteralDef> literalList = enumerationParamDef.gGetLiterals();
				boolean valueFound = false;

				for (int i = 0; i < literalList.size(); i++) {
					if (literalList.get(i).gGetShortName().equals(value)) {
						valueFound = true;
						break; // leave the for loop
					}
				}

				if (false == valueFound) {
					return ctx.createFailureStatus(Messages.enumeration_valueNotInLiterals);
				}
			}
		}

		return ctx.createSuccessStatus();
	}

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		// TODO Auto-generated method stub
		return true;
	}

}
