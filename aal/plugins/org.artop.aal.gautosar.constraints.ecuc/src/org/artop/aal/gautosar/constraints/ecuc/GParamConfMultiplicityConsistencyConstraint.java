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

import gautosar.gecucparameterdef.GParamConfMultiplicity;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

/**
 * The class validate the constraints implementations on an <em>GParamConfMultiplicity</em>'s lowerMultiplicity and
 * upperMultiplicity
 */
public class GParamConfMultiplicityConsistencyConstraint extends AbstractSplitModelConstraintWithPrecondition {

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
					if (paramConfMultiplicity.gGetUpperMultiplicityInfinite()) {
						//
						// Consistency OK.
						//
					} else if (Integer.valueOf(lowerMultiplicity) > Integer.valueOf(upperMultiplicity)) {
						//
						// Upper multiplicity is not strictly greater than lower multiplicity. Create a failure.
						//
						return ctx.createFailureStatus(/* NLS.bind( */EcucConstraintMessages.paramConfMultiplicity_isNotConsistency/*
																																	 * ,
																																	 * AutosarURIFactory
																																	 * .
																																	 * getAbsoluteQualifiedName
																																	 * (
																																	 * paramConfMultiplicity
																																	 * )
																																	 * )
																																	 */);

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
