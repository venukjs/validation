/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import org.artop.aal.autosar3x.constraints.ecuc.internal.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucparameterdef.ParamConfMultiplicity;

public class ParamConfMultiplicityBasicConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof ParamConfMultiplicity;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		ParamConfMultiplicity paramConfMultiplicity = (ParamConfMultiplicity) ctx.getTarget();

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		// validate lower multiplicity
		if (paramConfMultiplicity.isSetLowerMultiplicity()) {
			String lowerMultiplicityString = paramConfMultiplicity.getLowerMultiplicity();
			try {
				int lowerMultiplicity = Integer.parseInt(lowerMultiplicityString);
				if (lowerMultiplicity < 0) {
					multiStatus.add(ctx.createFailureStatus("lowerMultiplicity must be a positive number"));
				} else {
					multiStatus.add(ctx.createSuccessStatus());
				}
			} catch (NumberFormatException nfe) {
				multiStatus.add(ctx.createFailureStatus("lowerMultiplicity: " + nfe.getMessage()));
			}
		}

		// validate upper multiplicity
		if (paramConfMultiplicity.isSetUpperMultiplicity()) {
			String upperMultiplicityString = paramConfMultiplicity.getUpperMultiplicity();
			if ("*".equals(upperMultiplicityString)) {
				multiStatus.add(ctx.createSuccessStatus());
			} else {
				try {
					int upperMultiplicity = Integer.parseInt(upperMultiplicityString);
					if (upperMultiplicity < 0) {
						multiStatus.add(ctx.createFailureStatus("upperMultiplicity must be a positive number"));
					} else {
						multiStatus.add(ctx.createSuccessStatus());
					}
				} catch (NumberFormatException nfe) {
					multiStatus.add(ctx.createFailureStatus("upperMultiplicity: " + nfe.getMessage()));
				}
			}
		}

		return multiStatus;
	}
}
