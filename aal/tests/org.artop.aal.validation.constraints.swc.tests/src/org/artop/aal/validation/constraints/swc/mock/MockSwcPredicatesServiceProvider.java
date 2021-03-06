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
package org.artop.aal.validation.constraints.swc.mock;

import gautosar.gswcomponents.gportinterface.GPortInterface;
import junit.framework.Assert;

import org.artop.aal.gautosar.services.IMetaModelService;
import org.artop.aal.gautosar.services.IMetaModelServiceProvider;
import org.artop.aal.gautosar.services.predicates.ExplainablePredicate;
import org.artop.aal.gautosar.services.predicates.swc.ISwcPredicatesService;
import org.eclipse.sphinx.emf.metamodel.IMetaModelDescriptor;
import org.eclipse.sphinx.emf.metamodel.providers.IMetaModelDescriptorProvider;

/**
 * A <code>IMetaModelServiceProvider</code> which provides a
 * {@link org.artop.aal.validation.testutils.internal.mock.MockSwcPredicatesServiceProvider.MockSwcPredicatesService} .
 */
public class MockSwcPredicatesServiceProvider implements IMetaModelServiceProvider {

	private final MockSwcPredicatesService fSwcPredicatesService = new MockSwcPredicatesService();

	@SuppressWarnings("unchecked")
	public <T extends IMetaModelService> T getService(IMetaModelDescriptor descriptor, Class<T> serviceType) {
		Assert.assertEquals("Unexpected service was requested!", ISwcPredicatesService.class, serviceType); //$NON-NLS-1$
		return (T) fSwcPredicatesService;
	}

	public <T extends IMetaModelService> T getService(IMetaModelDescriptorProvider provider, Class<T> serviceType) {
		return getService(provider.getMetaModelDescriptor(), serviceType);
	}

	public void setHasUniqueErrorCodesPredicate(ExplainablePredicate<GPortInterface> predicate) {
		fSwcPredicatesService.setHasUniqueErrorCodesPredicate(predicate);
	}

	/**
	 * The <code>Predicates</code> of this <code>ISwcPredicateService</code> which it will provide to clients can be set
	 * from the outside. Can be used for providing MockPredicates to classes which are to be tested.
	 */
	private static class MockSwcPredicatesService implements ISwcPredicatesService {

		private ExplainablePredicate<GPortInterface> fHasUniqueErrorCodesPredicate;

		public ExplainablePredicate<GPortInterface> hasUniqueErrorCodes() {
			return fHasUniqueErrorCodesPredicate;
		}

		public void setHasUniqueErrorCodesPredicate(ExplainablePredicate<GPortInterface> predicate) {
			fHasUniqueErrorCodesPredicate = predicate;
		}

		public ExplainablePredicate<GPortInterface> isDcmInterface() {
			return null;
		}

		public ExplainablePredicate<GPortInterface> isEcuMInterface() {
			return null;
		}

	}
}
