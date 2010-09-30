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
	 * @return
	 */
	protected abstract AbstractModelConstraint getDelegateConstraint();

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

}
