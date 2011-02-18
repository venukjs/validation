/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT, See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar20.validation.internal;

import org.artop.aal.autosar20.validation.listeners.AutomaticValidation20Listener;
import org.artop.aal.autosar20.validation.listeners.ProblemMarkerIntegrity20Listener;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sphinx.emf.domain.factory.ITransactionalEditingDomainFactoryListener;

/**
 * @since 1.2
 */
public class ValidationEditingDomainFactoryListener implements ITransactionalEditingDomainFactoryListener {

	protected AutomaticValidation20Listener automaticValidationListener = new AutomaticValidation20Listener();
	protected ProblemMarkerIntegrity20Listener problemMarkerListener = new ProblemMarkerIntegrity20Listener();

	/**
	 * {@inheritDoc}
	 */
	public void postCreateEditingDomain(TransactionalEditingDomain editingDomain) {
		Assert.isNotNull(editingDomain);
		editingDomain.addResourceSetListener(automaticValidationListener);
		editingDomain.addResourceSetListener(problemMarkerListener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void preDisposeEditingDomain(TransactionalEditingDomain editingDomain) {
		Assert.isNotNull(editingDomain);
		editingDomain.removeResourceSetListener(automaticValidationListener);
		editingDomain.removeResourceSetListener(problemMarkerListener);
	}
}
