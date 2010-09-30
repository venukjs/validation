package org.artop.ecl.emf.validation.constraint;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class DelegateConstraintCallHandler implements IConstraintCallHandler {

	private AbstractModelConstraint fDelegate;

	public DelegateConstraintCallHandler(AbstractModelConstraint constraint) {
		fDelegate = constraint;
	}

	public void append(IConstraintCallHandler successor) {
		throw new UnsupportedOperationException(
				"The DelegateConstraintCallHandler is intended to be the last handler in a chain. No successor can be appended.");
	}

	public IStatus handleValidationCall(IValidationContext ctx) {
		return fDelegate.validate(ctx);
	}

}
