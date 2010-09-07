package org.artop.ecl.emf.validation.constraint;

import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.model.IModelConstraint;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.xml.XmlConstraintProvider;
import org.osgi.framework.Bundle;

public class XmlChainedConstraintProvider extends XmlConstraintProvider {

	@Override
	protected IModelConstraint createModelConstraint(IConstraintDescriptor descriptor) {
		IModelConstraint constraint = super.createModelConstraint(descriptor);
		if (isChainedConstraintDescriptor(descriptor)) {
			constraint = new ChainedConstraint(constraint, getConstraintClass((IParameterizedConstraintDescriptor) descriptor));
			appendConstraintCallHandlers((ChainedConstraint) constraint);
		}
		return constraint;
	}

	private void appendConstraintCallHandlers(ChainedConstraint chainedConstraint) {
		for (IConstraintCallHandler handler : getConstraintCallHandlers()) {
			chainedConstraint.append(handler);
		}
	}

	private boolean isChainedConstraintDescriptor(IConstraintDescriptor descriptor) {
		if (!(descriptor instanceof IParameterizedConstraintDescriptor)) {
			return false;
		}
		Class<? extends AbstractModelConstraint> constraintClass = getConstraintClass((IParameterizedConstraintDescriptor) descriptor);
		if (constraintClass == null) {
			return false;
		}
		return ChainableConstraint.class.isAssignableFrom(constraintClass);
	}

	@SuppressWarnings("unchecked")
	private Class<? extends AbstractModelConstraint> getConstraintClass(IParameterizedConstraintDescriptor descriptor) {
		String className = descriptor.getParameterValue(IParameterizedConstraintDescriptor.CLASS_PARAMETER);
		String bundleName = descriptor.getParameterValue(IParameterizedConstraintDescriptor.BUNDLE_PARAMETER);
		Bundle bundle = Platform.getBundle(bundleName);
		try {
			return bundle.loadClass(className);
		} catch (ClassNotFoundException ex) {
			return null;
		}
	}

	private Iterable<IConstraintCallHandler> getConstraintCallHandlers() {
		return new ConstraintCallHandlerExtensionsReader().getContributedConstraintCallHandlers();
	}

}
