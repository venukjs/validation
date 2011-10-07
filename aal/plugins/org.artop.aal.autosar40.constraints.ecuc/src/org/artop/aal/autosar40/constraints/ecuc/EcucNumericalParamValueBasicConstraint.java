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

import java.math.BigInteger;

import org.artop.aal.autosar40.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGParameterValueConstraint;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar40.ecucdescription.EcucNumericalParamValue;
import autosar40.ecucparameterdef.EcucBooleanParamDef;
import autosar40.ecucparameterdef.EcucFloatParamDef;
import autosar40.ecucparameterdef.EcucIntegerParamDef;
import autosar40.genericstructure.varianthandling.FloatValueVariationPoint;
import autosar40.genericstructure.varianthandling.NumericalValueVariationPoint;
import autosar40.genericstructure.varianthandling.UnlimitedIntegerValueVariationPoint;

public class EcucNumericalParamValueBasicConstraint extends AbstractGParameterValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof EcucNumericalParamValue;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		EcucNumericalParamValue ecucNumericalParamValue = (EcucNumericalParamValue) ctx.getTarget();

		status = validateDefinitionRef(ctx, ecucNumericalParamValue);
		if (status.isOK()) {
			status = validateValue(ctx, ecucNumericalParamValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, GParameterValue gParameterValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gParameterValue);
		if (status.isOK()) {
			GConfigParameter definition = gParameterValue.gGetDefinition();
			if (!(definition instanceof EcucBooleanParamDef) && !(definition instanceof EcucIntegerParamDef)
					&& !(definition instanceof EcucFloatParamDef)) {
				status = ctx.createFailureStatus(NLS.bind(Messages.generic_definitionNotOfType,
						"EcucBooleanParamDef/EcucIntegerParamDef/EcucFloatParamDef")); //$NON-NLS-1$
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, EcucNumericalParamValue ecucNumericalParamValue) {
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		NumericalValueVariationPoint valueVarPoint = ecucNumericalParamValue.getValue();
		GConfigParameter definition = ecucNumericalParamValue.gGetDefinition();

		IStatus status = validateValueSet(ctx, ecucNumericalParamValue, valueVarPoint);
		if (!status.isOK()) {
			return status;
		}

		String mixedText = valueVarPoint.getMixedText();
		status = validateValueSet(ctx, ecucNumericalParamValue, mixedText);
		if (!status.isOK()) {
			return status;
		}

		if (mixedText != null) {

			if (definition instanceof EcucIntegerParamDef) {

				BigInteger value = new BigInteger(mixedText);
				EcucIntegerParamDef ecucIntegerParamDef = (EcucIntegerParamDef) definition;

				UnlimitedIntegerValueVariationPoint minVarPoint = ecucIntegerParamDef.getMin();
				if (minVarPoint != null) {
					String mixed = minVarPoint.getMixedText();
					if (mixed != null) {
						BigInteger min = new BigInteger(mixed);
						if (value.compareTo(min) < 0) {
							multiStatus.add(ctx.createFailureStatus(Messages.boundary_valueUnderMin));
						}

					}
				}
				UnlimitedIntegerValueVariationPoint maxVarPoint = ecucIntegerParamDef.getMax();
				if (maxVarPoint != null) {
					String mixed = maxVarPoint.getMixedText();
					if (mixed != null) {
						BigInteger max = new BigInteger(mixed);
						if (value.compareTo(max) > 0) {
							multiStatus.add(ctx.createFailureStatus(Messages.boundary_valueAboveMax));
						}
					}
				}

			}

			if (definition instanceof EcucFloatParamDef) {

				Double value = Double.valueOf(valueVarPoint.getMixedText());
				EcucFloatParamDef ecucFloatParamDef = (EcucFloatParamDef) definition;

				FloatValueVariationPoint minVarPoint = ecucFloatParamDef.getMin();
				if (minVarPoint != null) {
					String mixed = minVarPoint.getMixedText();
					if (mixed != null) {
						try {
							Double min = Double.valueOf(mixed);
							if (value.compareTo(min) < 0) {
								multiStatus.add(ctx.createFailureStatus(Messages.boundary_valueUnderMin));
							}
						} catch (NumberFormatException nfe) {
							multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.boundary_MinValueException, nfe.getMessage())));
						}
					}
				}

				FloatValueVariationPoint maxVarPoint = ecucFloatParamDef.getMax();
				if (maxVarPoint != null) {
					String mixed = maxVarPoint.getMixedText();
					if (mixed != null) {
						try {
							Double max = Double.valueOf(mixed);
							if (value.compareTo(max) > 0) {
								multiStatus.add(ctx.createFailureStatus(Messages.boundary_valueAboveMax));
							}
						} catch (NumberFormatException nfe) {
							multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.boundary_MaxValueException, nfe.getMessage())));
						}
					}
				}
			}
		}

		if (multiStatus.getChildren().length == 0) {
			return ctx.createSuccessStatus();
		}

		return multiStatus;
	}

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		return true;

	}

}
