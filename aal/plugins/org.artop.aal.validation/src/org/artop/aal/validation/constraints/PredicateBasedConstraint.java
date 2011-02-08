package org.artop.aal.validation.constraints;

import org.artop.aal.gautosar.services.DefaultMetaModelServiceProvider;
import org.artop.aal.gautosar.services.IMetaModelServiceProvider;
import org.eclipse.emf.validation.AbstractModelConstraint;

public abstract class PredicateBasedConstraint extends AbstractModelConstraint {

	private IMetaModelServiceProvider fServiceProvider = new DefaultMetaModelServiceProvider();

	/**
	 * Sets the <code>IMetaModelServiceProvider</code> from which the constraint will get its <code>Predicate</code>s.
	 * 
	 * @param serviceProvider
	 *            the <code>IMetaModelServiceProvider</code>
	 */
	public void setServiceProvider(IMetaModelServiceProvider serviceProvider) {
		fServiceProvider = serviceProvider;
	}

	protected IMetaModelServiceProvider getServiceProvider() {
		return fServiceProvider;
	}

}
