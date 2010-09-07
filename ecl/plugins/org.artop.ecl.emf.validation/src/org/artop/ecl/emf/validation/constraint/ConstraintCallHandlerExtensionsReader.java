package org.artop.ecl.emf.validation.constraint;

import java.util.ArrayList;
import java.util.Collection;

import org.artop.ecl.emf.validation.Activator;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.validation.IValidationContext;

public class ConstraintCallHandlerExtensionsReader {

	private static final String EXP_ID_SEPARATOR = "."; //$NON-NLS-1$
	private static final String EXP_ID_CONST_CALL_HANDLERS = "constraintCallHandlers"; //$NON-NLS-1$
	private static final String NODE_NAME_CALL_HANDLER = "constraintCallHandler"; //$NON-NLS-1$
	private static final String ATTR_NAME_CLASS = "class"; //$NON-NLS-1$
	private Collection<IConstraintCallHandler> fConstraintCallHandlers;

	public Iterable<IConstraintCallHandler> getContributedConstraintCallHandlers() {
		if (fConstraintCallHandlers == null) {
			fConstraintCallHandlers = new ArrayList<IConstraintCallHandler>();
			IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
					Activator.getDefault().getBundle().getSymbolicName() + EXP_ID_SEPARATOR + EXP_ID_CONST_CALL_HANDLERS).getExtensions();
			for (IExtension extension : extensions) {
				for (IConfigurationElement configElem : extension.getConfigurationElements()) {
					if (NODE_NAME_CALL_HANDLER.equals(configElem.getName())) {
						fConstraintCallHandlers.add(getCallHandler(configElem));
					}
				}
			}
		}
		return fConstraintCallHandlers;
	}

	private IConstraintCallHandler getCallHandler(IConfigurationElement configElem) {
		try {
			return (IConstraintCallHandler) configElem.createExecutableExtension(ATTR_NAME_CLASS);
		} catch (CoreException ex) {
			// TODO: Log Problem
		}
		return new NullConstraintCallHandler();
	}

	private class NullConstraintCallHandler extends ConstraintCallHandler {

		@Override
		protected IStatus validate(IValidationContext ctx) {
			return UNDECIDED;
		}

	}
}
