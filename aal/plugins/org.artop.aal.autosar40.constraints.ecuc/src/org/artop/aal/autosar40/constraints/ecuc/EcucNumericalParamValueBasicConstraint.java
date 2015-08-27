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
import gautosar.ggenericstructure.gvarianthandling.GAttributeValueVariationPoint;

import java.math.BigInteger;

import org.artop.aal.autosar40.constraints.ecuc.internal.Activator;
import org.artop.aal.autosar40.constraints.ecuc.util.EcucUtil40;
import org.artop.aal.autosar40.gautosar40.ecucdescription.GEcucNumericalParamValue40XAdapter;
import org.artop.aal.autosar40.gautosar40.ecucparameterdef.GEcucFloatParamDef40XAdapter;
import org.artop.aal.autosar40.gautosar40.ecucparameterdef.GEcucIntegerParamDef40XAdapter;
import org.artop.aal.common.datatypes.UnlimitedIntegerDatatype;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGParameterValueConstraint;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import autosar40.ecucdescription.EcucNumericalParamValue;
import autosar40.ecucparameterdef.EcucBooleanParamDef;
import autosar40.ecucparameterdef.EcucFloatParamDef;
import autosar40.ecucparameterdef.EcucIntegerParamDef;

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
				status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.generic_definitionNotOfType,
						"EcucBooleanParamDef/EcucIntegerParamDef/EcucFloatParamDef")); //$NON-NLS-1$
			}
		}
		return status;
	}

	protected IStatus validateValue(IValidationContext ctx, EcucNumericalParamValue ecucNumericalParamValue) {
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		GAttributeValueVariationPoint valueVarPoint = new GEcucNumericalParamValue40XAdapter(ecucNumericalParamValue).getValue();
		GConfigParameter definition = ecucNumericalParamValue.gGetDefinition();

		IStatus status = validateValueSet(ctx, ecucNumericalParamValue, valueVarPoint);
		if (!status.isOK()) {
			return status;
		}

		String mixedText = valueVarPoint.gGetMixedText();
		status = validateValueSet(ctx, ecucNumericalParamValue, mixedText);
		if (!status.isOK()) {
			return status;
		}

		if (mixedText != null) {
			if (definition instanceof EcucIntegerParamDef) {

				BigInteger value = null;
				try {
					UnlimitedIntegerDatatype unlimitedIntegerDatatype = new UnlimitedIntegerDatatype(mixedText);
					value = unlimitedIntegerDatatype.getValue();
				} catch (NumberFormatException ex) {
					multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.numericalParamValue_valueTypeDoesNotMatchDefType,
							mixedText)));
				}
				if (value != null) {
					EcucIntegerParamDef ecucIntegerParamDef = (EcucIntegerParamDef) definition;

					GAttributeValueVariationPoint minVarPoint = new GEcucIntegerParamDef40XAdapter(ecucIntegerParamDef).getMin();
					if (minVarPoint != null) {
						String mixed = minVarPoint.gGetMixedText();
						if (mixed != null) {
							BigInteger min = new BigInteger(mixed);
							if (value.compareTo(min) < 0) {
								multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.boundary_valueUnderMin, new Object[] { value,
										min })));
							}

						}
					}
					GAttributeValueVariationPoint maxVarPoint = new GEcucIntegerParamDef40XAdapter(ecucIntegerParamDef).getMax();
					if (maxVarPoint != null) {
						String mixed = maxVarPoint.gGetMixedText();
						if (mixed != null) {
							BigInteger max = new BigInteger(mixed);
							if (value.compareTo(max) > 0) {
								multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.boundary_valueAboveMax, new Object[] { value,
										max })));
							}
						}
					}
				}
			}

			if (definition instanceof EcucFloatParamDef) {
				Double value = null;
				try {
					value = EcucUtil40.convertStringToDouble(valueVarPoint.gGetMixedText());

				} catch (NumberFormatException ex) {
					multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.numericalParamValue_valueTypeDoesNotMatchDefType,
							mixedText)));
				}
				if (value != null) {
					EcucFloatParamDef ecucFloatParamDef = (EcucFloatParamDef) definition;

					GAttributeValueVariationPoint minVarPoint = new GEcucFloatParamDef40XAdapter(ecucFloatParamDef).getMin();
					if (minVarPoint != null) {
						String mixed = minVarPoint.gGetMixedText();
						if (mixed != null) {
							try {
								Double min = EcucUtil40.convertStringToDouble(mixed);
								if (value.compareTo(min) < 0) {

									String valueAsString = EcucUtil40.convertDoubleToString(value);
									String minAsString = EcucUtil40.convertDoubleToString(min);

									multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.boundary_valueUnderMin, new Object[] {
											valueAsString, minAsString })));
								}
							} catch (NumberFormatException nfe) {
								multiStatus
										.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.boundary_MinValueException, nfe.getMessage())));
							}
						}
					}

					GAttributeValueVariationPoint maxVarPoint = new GEcucFloatParamDef40XAdapter(ecucFloatParamDef).getMax();
					if (maxVarPoint != null) {
						String mixed = maxVarPoint.gGetMixedText();
						if (mixed != null) {
							try {
								Double max = EcucUtil40.convertStringToDouble(mixed);
								if (value.compareTo(max) > 0) {

									String valueAsString = EcucUtil40.convertDoubleToString(value);
									String maxAsString = EcucUtil40.convertDoubleToString(max);
									multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.boundary_valueAboveMax, new Object[] {
											valueAsString, maxAsString })));
								}
							} catch (NumberFormatException nfe) {
								multiStatus
										.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.boundary_MaxValueException, nfe.getMessage())));
							}
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
