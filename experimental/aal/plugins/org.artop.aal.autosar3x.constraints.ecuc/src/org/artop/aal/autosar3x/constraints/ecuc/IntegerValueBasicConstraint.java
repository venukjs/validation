/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy,  Continental Engineering Services  and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar 
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import gautosar.gecucdescription.GIntegerValue;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GIntegerParamDef;

import java.math.BigInteger;

import org.artop.aal.gautosar.constraints.ecuc.GIntegerValueBasicConstraint;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.IntegerValue;
import autosar3x.ecucparameterdef.IntegerParamDef;

public class IntegerValueBasicConstraint extends GIntegerValueBasicConstraint {

	@Override
	protected IStatus validateBoundary(IValidationContext ctx, GIntegerValue gIntegerValue) {
		IStatus status = ctx.createSuccessStatus();

		GIntegerParamDef gIntegerParamDef = (GIntegerParamDef) gIntegerValue.gGetDefinition();
		IntegerParamDef definition = (IntegerParamDef) gIntegerParamDef;

		BigInteger value = gIntegerValue.gGetValue();
		if (value != null) {

			// only check against min or max if integerParameterDef is complete
			// min limit
			if (true == definition.isSetMin()) {
				BigInteger minLimit = definition.getMin();
				if (value.compareTo(minLimit) < 0) {
					status = ctx.createFailureStatus(Messages.boundary_valueUnderMin);
				}
			}

			// max limit
			if (true == definition.isSetMax()) {
				BigInteger maxLimit = definition.getMax();
				if (value.compareTo(maxLimit) > 0) {
					status = ctx.createFailureStatus(Messages.boundary_valueAboveMax);
				}
			}
		}
		return status;
	}

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		IntegerValue value = (IntegerValue) gParameterValue;
		return value.isSetValue();
	}
}
