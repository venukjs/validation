package org.artop.ecl.emf.validation.constraint;

public interface IConstraintCallHandlerProvider {

	Iterable<? extends IConstraintCallHandler> getConstraintCallHandlers();

}
