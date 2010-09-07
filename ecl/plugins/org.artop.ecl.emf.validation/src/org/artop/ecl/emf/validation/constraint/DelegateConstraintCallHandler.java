package org.artop.ecl.emf.validation.constraint;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.IModelConstraint;

public class DelegateConstraintCallHandler implements IConstraintCallHandler {

	private IModelConstraint fDelegate;

	public DelegateConstraintCallHandler(IModelConstraint constraint) {
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
