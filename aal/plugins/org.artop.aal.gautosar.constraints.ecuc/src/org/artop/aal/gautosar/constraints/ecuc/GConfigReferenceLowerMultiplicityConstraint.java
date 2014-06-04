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

import gautosar.gecucparameterdef.GCommonConfigurationAttributes;
import gautosar.gecucparameterdef.GConfigReference;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

/**
 * 
 */
public class GConfigReferenceLowerMultiplicityConstraint extends AbstractSplitModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GConfigReference;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GCommonConfigurationAttributes current = (GCommonConfigurationAttributes) ctx.getTarget();

		String[] result = EcucUtil.vendorSpecificCommonConfigurationAttributesLowerMultiplicity(current);
		if (result != null) {
			return ctx.createFailureStatus(EcucConstraintMessages.configReference_lowerMultiplicityChanged, new Object[] { result[0], result[1] });
		}
		return ctx.createSuccessStatus();
	}

}
