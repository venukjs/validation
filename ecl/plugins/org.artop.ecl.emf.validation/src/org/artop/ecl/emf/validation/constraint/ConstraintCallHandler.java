package org.artop.ecl.emf.validation.constraint;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

public abstract class ConstraintCallHandler implements IConstraintCallHandler {

	protected static final IStatus UNDECIDED = new UndecidedStatus();

	private IConstraintCallHandler fSuccessor;

	protected abstract IStatus validate(IValidationContext ctx);

	public IStatus handleValidationCall(IValidationContext ctx) {
		IStatus result = validate(ctx);
		if (result != UNDECIDED) {
			return result;
		}
		if (fSuccessor != null) {
			return fSuccessor.handleValidationCall(ctx);
		}
		return ctx.createSuccessStatus();
	}

	public void append(IConstraintCallHandler successor) {
		if (fSuccessor == null) {
			fSuccessor = successor;
		} else {
			fSuccessor.append(successor);
		}
	}

}
