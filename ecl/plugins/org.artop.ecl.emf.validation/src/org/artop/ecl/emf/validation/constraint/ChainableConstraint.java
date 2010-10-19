package org.artop.ecl.emf.validation.constraint;

import org.artop.ecl.emf.validation.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public abstract class ChainableConstraint extends AbstractModelConstraint implements IChainableConstraint {

	private IConstraintCallHandler fConstraintCallHandler;

	public ChainableConstraint() {
		fConstraintCallHandler = new DelegateConstraintCallHandler(getDelegateConstraint());
		IConstraintCallHandlerRegistry registry = Activator.getConstraintCallHandlerRegistry();
		Iterable<? extends IConstraintCallHandler> handlers = registry.getConstraintCallHandlers(getSupportedHandlerClass());
		for (IConstraintCallHandler handler : handlers) {
			append(handler);
		}
	}

	/**
	 * Returns the actual Constraint instance. Any call to the ChainableConstraint will be delegated to the Constraint
	 * returned by this method unless one of the provided ConstraintCallHandlers already handles the call.
	 * 
	 * @return The Constraint which is to be called by default if none of the appended ConstraintCallHandlers can handle
	 *         the validate()-call.
	 */
	protected AbstractModelConstraint getDelegateConstraint() {
		return new SuccessConstraint();
	}

	/**
	 * Returns the type of ConstraintCallHandlers which this Constraint will forward validate()-calls to.
	 * ConstraintCallHandlers which want to be appended to this Constraint have to be instanceof the returned class.
	 * Constraints which are intended to be extended by ConstraintCallHandlers have to override this method.
	 * 
	 * @return The class of which ConstraintCallHandlers have be instanceof in order to be appended to this Constraint.
	 */
	protected Class<? extends IConstraintCallHandler> getSupportedHandlerClass() {
		return null;
	}

	@Override
	public IStatus validate(IValidationContext ctx) {
		return fConstraintCallHandler.handleValidationCall(ctx);
	}

	public void append(IConstraintCallHandler constraintCallHandler) {
		if (constraintCallHandler != null) {
			constraintCallHandler.append(fConstraintCallHandler);
			fConstraintCallHandler = constraintCallHandler;
		}
	}

	private class SuccessConstraint extends AbstractModelConstraint {

		@Override
		public IStatus validate(IValidationContext ctx) {
			return ctx.createSuccessStatus();
		}

	}

}
