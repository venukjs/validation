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

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucparameterdef.GConfigReference;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>GConfigReferenceValue</em>'s <i>Reference</i> value
 */
public abstract class AbstractGConfigReferenceValueBasicConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GConfigReferenceValue;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GConfigReferenceValue configReferenceValue = (GConfigReferenceValue) ctx.getTarget();

		GConfigReference configReference = configReferenceValue.gGetDefinition();

		// the ConfigReferenceValue must be connected to an ConfigReference
		if (configReference == null) {
			return ctx.createSuccessStatus();
		}

		if (null == getValue(configReferenceValue)) {
			return ctx.createFailureStatus(NLS.bind(Messages.configReferenceValue_valueNotSet, configReference.gGetShortName()));
		}

		return ctx.createSuccessStatus();
	}

	/**
	 * Retrieve the value of ConfigReferenceValue
	 * 
	 * @param configReferenceValue
	 *            The GConfigReferenceValue
	 * @return value
	 */
	protected abstract Object getValue(GConfigReferenceValue configReferenceValue);

}
