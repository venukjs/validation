package org.artop.ecl.emf.validation.internal.constraint;

import java.util.ArrayList;
import java.util.Collection;

import org.artop.ecl.emf.validation.constraint.IConstraintCallHandler;
import org.artop.ecl.emf.validation.constraint.IConstraintCallHandlerProvider;
import org.artop.ecl.emf.validation.constraint.IConstraintCallHandlerRegistry;

public class ConstraintCallHandlerRegistry implements IConstraintCallHandlerRegistry {

	private IConstraintCallHandlerProvider fHandlerProvider;
	private Iterable<? extends IConstraintCallHandler> fHandlers;

	public ConstraintCallHandlerRegistry(IConstraintCallHandlerProvider handlerProvider) {
		fHandlerProvider = handlerProvider;
	}

	@SuppressWarnings("unchecked")
	public <T extends IConstraintCallHandler> Iterable<T> getConstraintCallHandlers(Class<T> handlerType) {
		Collection<T> handlers = new ArrayList<T>();
		if (handlerType != null) {
			for (IConstraintCallHandler handler : getHandlers()) {
				if (handlerType.isAssignableFrom(handler.getClass())) {
					handlers.add((T) handler);
				}
			}
		}
		return handlers;
	}

	private Iterable<? extends IConstraintCallHandler> getHandlers() {
		if (fHandlers == null) {
			fHandlers = fHandlerProvider.getConstraintCallHandlers();
		}
		return fHandlers;
	}

}
