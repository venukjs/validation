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

import gautosar.gecucparameterdef.GFloatParamDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>GFloatParamDef</em>'s default value
 */
public abstract class AbstractGFloatParamDefDefaultValueConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GFloatParamDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		GFloatParamDef floatParamDef = (GFloatParamDef) ctx.getTarget();

		if (!isDefaultValueSet(floatParamDef)) {
			// default value is not set, ignored
			return status;
		}

		Double defaultValue = getDefaultValue(floatParamDef);
		if (defaultValue == null) {
			// default value is set with wrong Float format
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.floatParamDef_defaultValueIsNotFloat,
					AutosarURIFactory.getAbsoluteQualifiedName(floatParamDef)));
		}

		Double min = getMin(floatParamDef);
		Double max = getMax(floatParamDef);
		boolean valid = true;
		String minvalue = null;
		String maxvalue = null;
		if (min == null && max == null) {
			return status;
		}
		if (min == null) {
			valid = defaultValue.compareTo(max) <= 0;
			minvalue = "~"; //$NON-NLS-1$
			maxvalue = max.toString();
		} else if (max == null) {
			valid = defaultValue.compareTo(min) >= 0;
			minvalue = min.toString();
			maxvalue = "~"; //$NON-NLS-1$
		} else {
			valid = defaultValue.compareTo(min) >= 0 && defaultValue.compareTo(max) <= 0;
			minvalue = min.toString();
			maxvalue = max.toString();
		}

		if (!valid) {
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.floatParamDef_defaultValueIsOutOfRange,
					new String[] { AutosarURIFactory.getAbsoluteQualifiedName(floatParamDef), defaultValue.toString(), minvalue, maxvalue }));
		}

		return ctx.createSuccessStatus();
	}

	/**
	 * Get Min value of <em>GFloatParamDef</em>
	 * 
	 * @param floatParamDef
	 *            The GFloatParamDef
	 * @return Min value
	 */
	protected abstract Double getMin(GFloatParamDef floatParamDef);

	/**
	 * Get Max value of <em>GFloatParamDef</em>
	 * 
	 * @param floatParamDef
	 *            The GFloatParamDef
	 * @return Max value
	 */
	protected abstract Double getMax(GFloatParamDef floatParamDef);

	/**
	 * Get default value of <em>GFloatParamDef</em>
	 * 
	 * @param floatParamDef
	 *            The GFloatParamDef
	 * @return default value
	 */
	protected abstract Double getDefaultValue(GFloatParamDef floatParamDef);

	/**
	 * Check default value is set or not
	 * 
	 * @param floatParamDef
	 *            The GFloatParamDef
	 * @return boolean value to show that default value is set or not
	 */
	protected abstract boolean isDefaultValueSet(GFloatParamDef floatParamDef);

}
