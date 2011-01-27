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

import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * The class validate the constraints implementations on an <em>GParamConfContainerDef</em>'s lowerMultiplicity and
 * upperMultiplicity
 */
public class GParamConfContainerDefInChoiceContainerDefMultiplicityConstraint extends AbstractModelConstraintWithPrecondition {

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

		if (lowerMultiplicity != null && !lowerMultiplicity.isEmpty() && !lowerMultiplicity.equals(MULTIPLICITY_LOWERBOUND)
				|| upperMultiplicity != null && !upperMultiplicity.isEmpty() && !upperMultiplicity.equals(MULTIPLICITY_UPPERBOUND)) {
			return ctx.createFailureStatus(NLS.bind(Messages.paramConfContainerDef_InChoiceContainerDefMultiplicity, new String[] {
					paramConfContainerDef.gGetShortName(), ((GIdentifiable) paramConfContainerDef.eContainer()).gGetShortName(),
					MULTIPLICITY_LOWERBOUND, MULTIPLICITY_UPPERBOUND }));
		}

		return ctx.createSuccessStatus();
	}

}
