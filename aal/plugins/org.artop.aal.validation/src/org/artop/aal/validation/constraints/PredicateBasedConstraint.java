/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     BMW Car IT - Initial API and implementation
 * 
 * </copyright>
 */
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
