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
package org.artop.aal.autosar321.validation.internal;

import org.artop.aal.autosar321.validation.listeners.AutomaticValidation3xListener;
import org.artop.aal.autosar321.validation.listeners.ProblemMarkerIntegrity3xListener;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.sphinx.emf.domain.factory.ITransactionalEditingDomainFactoryListener;

/**
 * @since 1.2
 */
public class ValidationEditingDomainFactoryListener implements ITransactionalEditingDomainFactoryListener {

	protected AutomaticValidation3xListener automaticValidationListener = new AutomaticValidation3xListener();
	protected ProblemMarkerIntegrity3xListener problemMarkerListener = new ProblemMarkerIntegrity3xListener();

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
