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

import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>GParamConfContainerDef</em>'s lowerMultiplicity and
 * upperMultiplicity
 */
public class GParamConfContainerDefInChoiceContainerDefMultiplicityConstraint extends AbstractSplitModelConstraintWithPrecondition {

	String MULTIPLICITY_LOWERBOUND = "0"; //$NON-NLS-1$
	String MULTIPLICITY_UPPERBOUND = "1"; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GParamConfContainerDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GParamConfContainerDef paramConfContainerDef = (GParamConfContainerDef) ctx.getTarget();

		if (!(paramConfContainerDef.eContainer() instanceof GChoiceContainerDef)) {
			return ctx.createSuccessStatus();
		}

		String lowerMultiplicity = paramConfContainerDef.gGetLowerMultiplicityAsString();
		String upperMultiplicity = paramConfContainerDef.gGetUpperMultiplicityAsString();

		if (lowerMultiplicity != null && lowerMultiplicity.length() > 0 && !lowerMultiplicity.equals(MULTIPLICITY_LOWERBOUND)
				|| upperMultiplicity != null && upperMultiplicity.length() > 0 && !upperMultiplicity.equals(MULTIPLICITY_UPPERBOUND)) {
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.paramConfContainerDef_InChoiceContainerDefMultiplicity, new String[] {
					paramConfContainerDef.gGetShortName(), ((GIdentifiable) paramConfContainerDef.eContainer()).gGetShortName(),
					MULTIPLICITY_LOWERBOUND, MULTIPLICITY_UPPERBOUND }));
		}

		return ctx.createSuccessStatus();
	}

}
