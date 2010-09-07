package org.artop.ecl.emf.validation.constraint;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

public class ChainedConstraint implements IModelConstraint {

	private IConstraintCallHandler fConstraintCallHandler;
	private Class<? extends AbstractModelConstraint> fConstraintClass;
	private IModelConstraint fDelegate;

	protected ChainedConstraint(IModelConstraint constraint, Class<? extends AbstractModelConstraint> constraintClass) {
		fDelegate = constraint;
		fConstraintClass = constraintClass;
		fConstraintCallHandler = new DelegateConstraintCallHandler(constraint);
	}

	public IConstraintDescriptor getDescriptor() {
		return fDelegate.getDescriptor();
	}

	public IStatus validate(IValidationContext ctx) {
		return fConstraintCallHandler.handleValidationCall(ctx);
	}

	public void append(IConstraintCallHandler constraintCallHandler) {
		if (constraintCallHandler != null && isSupportedHandler(constraintCallHandler)) {
			constraintCallHandler.append(fConstraintCallHandler);
			fConstraintCallHandler = constraintCallHandler;
		}
	}

	private boolean isSupportedHandler(IConstraintCallHandler handler) {
		try {
			Class<? extends IConstraintCallHandler> handlerClass = ((ChainableConstraint) fConstraintClass.newInstance()).getSupportedHandlerClass();
			return handlerClass.isAssignableFrom(handler.getClass());
		} catch (InstantiationException ex) {
			// TODO Auto-generated catch block
		} catch (IllegalAccessException ex) {
			// TODO Auto-generated catch block
		}
		return false;
	}
}
