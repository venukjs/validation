/**
 * <copyright>
 * 
 * Copyright (c) see4Sys and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Artop Software License 
 * Based on Released AUTOSAR Material (ASLR) which accompanies this 
 * distribution, and is available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     see4Sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucparameterdef.GParamConfMultiplicity;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>GParamConfMultiplicity</em>'s lowerMultiplicity and
 * upperMultiplicity
 */
public class GParamConfMultiplicityConsistencyConstraint extends AbstractModelConstraintWithPrecondition {

	String MULTIPLICITY_LOWERBOUND = "0"; //$NON-NLS-1$
	String MULTIPLICITY_UPPERBOUND = "*"; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GParamConfMultiplicity;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GParamConfMultiplicity paramConfMultiplicity = (GParamConfMultiplicity) ctx.getTarget();

		String lowerMultiplicity = paramConfMultiplicity.gGetLowerMultiplicityAsString();
		String upperMultiplicity = paramConfMultiplicity.gGetUpperMultiplicityAsString();

		try {
			if (lowerMultiplicity != null && lowerMultiplicity.length() > 0 && upperMultiplicity != null && upperMultiplicity.length() > 0) {
				if (!lowerMultiplicity.equals(MULTIPLICITY_UPPERBOUND) && Integer.valueOf(lowerMultiplicity) >= 0) {
					if (upperMultiplicity.equals(MULTIPLICITY_UPPERBOUND)) {
						//
						// Consistency OK.
						//
					} else if (Integer.valueOf(lowerMultiplicity) > Integer.valueOf(upperMultiplicity)) {
						//
						// Upper multiplicity is not strictly greater than lower multiplicity. Create a failure.
						//
						return ctx.createFailureStatus(NLS.bind(Messages.paramConfMultiplicity_isNotConsistency,
								AutosarURIFactory.getAbsoluteQualifiedName(paramConfMultiplicity)));

					}
				} else {
					//
					// Lower multiplicity is not correct. Do nothing more.
					//
				}
			} else {
				//
				// Lower multiplicity or upper multiplicity is null (or both are null). Do nothing more.
				//
			}
		} catch (NumberFormatException nfe) {
			//
			// Catch the exception and does nothing more.
			//
		}

		return ctx.createSuccessStatus();
	}

}
