/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     BMW Car IT - Initial API and implementation
 *     Continental AG - Minor refactoring.
 * </copyright>
 */
package org.artop.aal.validation.constraints.swc.portinterface;

import gautosar.gswcomponents.gportinterface.GClientServerInterface;
import gautosar.gswcomponents.gportinterface.GPortInterface;

import java.util.ArrayList;

import org.artop.aal.gautosar.services.predicates.ExplainablePredicate;
import org.artop.aal.gautosar.services.predicates.PortInterfacePredicates;
import org.artop.aal.gautosar.services.predicates.swc.portinterface.HasUniqueErrorCodes.DuplicateErrorCodes;
import org.artop.aal.gautosar.services.predicates.swc.portinterface.HasUniqueErrorCodes.DuplicateErrorCodes.MultiplyAssignedErrorCodeValue;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.artop.aal.validation.constraints.swc.internal.portinterface.messages.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.osgi.util.NLS;

/**
 * A Constraint which validates that all defined possible errors within a client server interface have unique values.
 * The constraint will create a failure message for each error code which is not unique.
 */
public class UniqueApplicationErrorCodesConstraint extends AbstractSplitModelConstraintWithPrecondition {

	private static final String MSG_NAME = "uniqueApplicationErrorCodes_Msg"; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GClientServerInterface;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GClientServerInterface csInterface = (GClientServerInterface) ctx.getTarget();
		ExplainablePredicate<GPortInterface> hasUniqueErrorCodes = PortInterfacePredicates.hasUniqueErrorCodes();
		if (!hasUniqueErrorCodes.apply(csInterface)) {
			return createFailures(ctx, (DuplicateErrorCodes) hasUniqueErrorCodes.explain());
		}
		return ctx.createSuccessStatus();
	}

	private IStatus createFailures(IValidationContext ctx, DuplicateErrorCodes reason) {
		Iterable<MultiplyAssignedErrorCodeValue> duplicateErrors = reason.getDuplicateErrorCodes();
		ArrayList<IStatus> failureStatuses = new ArrayList<IStatus>();
		for (MultiplyAssignedErrorCodeValue nonUniqueCode : duplicateErrors) {
			failureStatuses.add(ctx.createFailureStatus(getMessage(nonUniqueCode)));
		}
		return ConstraintStatus.createMultiStatus(ctx, failureStatuses);
	}

	private String getMessage(MultiplyAssignedErrorCodeValue nonUniqueCode) {
		try {
			return NLS.bind((String) Messages.class.getField(MSG_NAME).get(null), nonUniqueCode.getValue(), nonUniqueCode.labelsToString());
		} catch (Exception e) {
			return "The following error code is not unique: " + nonUniqueCode.toString(); //$NON-NLS-1$
		}
	}

}
