package org.artop.ecl.emf.validation.constraint;

public interface IConstraintCallHandlerRegistry {

	<T extends IConstraintCallHandler> Iterable<T> getConstraintCallHandlers(Class<T> handlerType);

}
