package org.artop.ecl.emf.validation.constraint;


public interface ChainableConstraint {

	Class<? extends IConstraintCallHandler> getSupportedHandlerClass();

}
