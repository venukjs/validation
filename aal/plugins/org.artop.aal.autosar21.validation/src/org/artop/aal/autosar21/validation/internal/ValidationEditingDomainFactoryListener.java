/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT, Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar21.validation.internal;

import org.artop.aal.autosar21.validation.listeners.AutomaticValidation21Listener;
import org.artop.aal.autosar21.validation.listeners.ProblemMarkerIntegrity21Listener;
import org.artop.ecl.emf.domain.factory.ITransactionalEditingDomainFactoryListener;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * @since 1.2
 */
public class ValidationEditingDomainFactoryListener implements ITransactionalEditingDomainFactoryListener {

	protected AutomaticValidation21Listener automaticValidationListener = new AutomaticValidation21Listener();
	protected ProblemMarkerIntegrity21Listener problemMarkerListener = new ProblemMarkerIntegrity21Listener();

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
