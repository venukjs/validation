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

import gautosar.gecucparameterdef.GIntegerParamDef;

import java.math.BigInteger;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>GIntegerParamDef</em>'s default value
 */
public abstract class AbstractGIntegerParamDefDefaultValueConstraint extends AbstractSplitModelConstraintWithPrecondition {

	BigInteger AUTOSAR_INTEGER_LOWERBOUNDSIGNED = new BigInteger("-9223372036854775808"); //$NON-NLS-1$
	BigInteger AUTOSAR_INTEGER_UPPERBOUNDSIGNED = new BigInteger("9223372036854775807"); //$NON-NLS-1$
	BigInteger AUTOSAR_INTEGER_UPPERBOUNDUNSIGNED = new BigInteger("18446744073709551615"); //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GIntegerParamDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		GIntegerParamDef integerParamDef = (GIntegerParamDef) ctx.getTarget();

		if (!isDefaultValueSet(integerParamDef)) {
			// default value is not set, ignored
			return status;
		}

		BigInteger defaultValue = getDefaultValue(integerParamDef);
		if (defaultValue == null) {
			// default value is set with wrong Integer format
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.integerParamDef_defaultValueIsNotInteger,
					AutosarURIFactory.getAbsoluteQualifiedName(integerParamDef)));
		}

		BigInteger min = getMin(integerParamDef);
		BigInteger max = getMax(integerParamDef);
		if (min == null) {
			if (max == null) {
				min = AUTOSAR_INTEGER_LOWERBOUNDSIGNED;
				max = AUTOSAR_INTEGER_UPPERBOUNDUNSIGNED;
			} else if (max.compareTo(AUTOSAR_INTEGER_UPPERBOUNDSIGNED) <= 0) {
				min = AUTOSAR_INTEGER_LOWERBOUNDSIGNED;
			} else {
				min = BigInteger.ZERO;
			}
		} else if (max == null) {
			if (min.compareTo(BigInteger.ZERO) < 0) {// less than 0
				max = AUTOSAR_INTEGER_UPPERBOUNDSIGNED;
			} else {
				max = AUTOSAR_INTEGER_UPPERBOUNDUNSIGNED;
			}
		}
		if (min.compareTo(max) >= 0) {
			// invalid min and max value-> ignore
		} else {
			if (defaultValue.compareTo(min) < 0 || defaultValue.compareTo(max) > 0) {
				return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.integerParamDef_defaultValueIsOutOfRange, new String[] {
						AutosarURIFactory.getAbsoluteQualifiedName(integerParamDef), defaultValue.toString(), min.toString(), max.toString() }));
			}
		}

		return ctx.createSuccessStatus();
	}

	/**
	 * Get Min value of <em>GIntegerParamDef</em>
	 * 
	 * @param integerParamDef
	 *            The GIntegerParamDef
	 * @return Min value
	 */
	protected abstract BigInteger getMin(GIntegerParamDef integerParamDef);

	/**
	 * Get Max value of <em>GIntegerParamDef</em>
	 * 
	 * @param integerParamDef
	 *            The GIntegerParamDef
	 * @return Max value
	 */
	protected abstract BigInteger getMax(GIntegerParamDef integerParamDef);

	/**
	 * Get default value of <em>GIntegerParamDef</em>
	 * 
	 * @param integerParamDef
	 *            The GIntegerParamDef
	 * @return default value
	 */
	protected abstract BigInteger getDefaultValue(GIntegerParamDef integerParamDef);

	/**
	 * Check default value is set or not
	 * 
	 * @param integerParamDef
	 *            The GIntegerParamDef
	 * @return boolean value to show that default value is set or not
	 */
	protected abstract boolean isDefaultValueSet(GIntegerParamDef integerParamDef);

}
