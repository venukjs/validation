package org.artop.aal.examples.validation.constraints;

import gautosar.gswcomponents.gportinterface.GClientServerInterface;

import org.artop.ecl.emf.validation.constraint.ChainableConstraint;
import org.artop.ecl.emf.validation.constraint.IConstraintCallHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

// EXAMPLE:
// A GAutosar-based Constraint which is extended with a release-specific extension.
// 
// The Constraint validates that the ShortName of all CSInterfaces start with "CS". A
// ConstraintCallHandler is contributed for autosar3x models. The handler causes the 
// Constraint to ignore CSInterfaces called "Did_Services_*" if the model is an autosar3x
// model. Within AUTOSAR 3.X Diagnostic interfaces have to be called "Did_Services_*".   
public class CSInterfaceStartsWithCSConstraint extends ChainableConstraint {

	private static final String CS_PREFIX = "CS"; //$NON-NLS-1$

	// This is the actual implementation of the constraint. It is used when ever there is no
	// ConstraintCallHandler which can handle the validate()-call.
	@Override
	protected AbstractModelConstraint getDelegateConstraint() {
		return new AbstractModelConstraint() {

			// Checks if the shortName starts with "CS". Implementation is based on GAutosar and therefore
			// can handle all models no matter of which AUTOSAR release they are.
			@Override
			public IStatus validate(IValidationContext ctx) {
				GClientServerInterface csInterface = (GClientServerInterface) ctx.getTarget();
				if (!csInterface.gGetShortName().startsWith(CS_PREFIX)) {
					return ctx.createFailureStatus();
				}
				return ctx.createSuccessStatus();
			}
		};
	}

	// The Constraint is intended to be extended by ConstraintCallHandlers therefore it overrides this method.
	@Override
	protected Class<? extends IConstraintCallHandler> getSupportedHandlerClass() {
		return ConstraintCallHandler.class;
	}

	// ConstraintHandlers which shall be taken into account by this Constraint have to implement this interface.
	public static interface ConstraintCallHandler extends IConstraintCallHandler {
	}

}
