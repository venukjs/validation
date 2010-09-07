package org.artop.ecl.emf.validation.constraint;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

public interface IConstraintCallHandler {

	public IStatus handleValidationCall(IValidationContext ctx);

	public void append(IConstraintCallHandler successor);

}
