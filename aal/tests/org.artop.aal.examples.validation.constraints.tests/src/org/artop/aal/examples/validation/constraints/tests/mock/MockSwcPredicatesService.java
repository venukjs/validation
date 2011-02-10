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
package org.artop.aal.examples.validation.constraints.tests.mock;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import gautosar.gswcomponents.gportinterface.GClientServerInterface;

import java.util.ArrayList;
import java.util.Collection;

import org.artop.aal.gautosar.services.predicates.ExplainablePredicate;
import org.artop.aal.gautosar.services.predicates.Reason;
import org.artop.aal.gautosar.services.predicates.swc.ISwcPredicatesService;
import org.artop.aal.validation.constraints.PredicateBasedConstraint;

/**
 * A mock implementation of the <code>ISwcPredicateService</code>. Instead of providing clients with real SWC
 * <code>Predicates</code> it returns
 * {@link org.artop.aal.examples.validation.constraints.tests.mock.MockSwcPredicateService.MockPredicate}s which can
 * verify that they have been applied. By passing an instance of this class to a {@link PredicateBasedConstraint} it can
 * be verified if a constraint was actually called upon a certain EObject.
 */
public class MockSwcPredicatesService implements ISwcPredicatesService {

	private final MockPredicate<GClientServerInterface> ARE_ERROR_CODES_UNIQUE = new MockPredicate<GClientServerInterface>();

	/**
	 * {@inheritDoc}<br>
	 * <br>
	 * Returns a {@link org.artop.aal.examples.validation.constraints.tests.mock.MockSwcPredicateService.MockPredicate}.
	 */
	public ExplainablePredicate<GClientServerInterface> hasUniqueErrorCodes() {
		return ARE_ERROR_CODES_UNIQUE;
	}

	/**
	 * Asserts that the AreErrorCodesUnique predicate's <code>apply()</code> method has been invoked at least once on
	 * each provided client server interface.
	 * 
	 * @param expectedCSInterfaces
	 *            the client server interfaces to which the predicate should have been applied()
	 */
	public void assertAreErrorCodesUniqueWasAppliedTo(GClientServerInterface... expectedCSInterfaces) {
		ARE_ERROR_CODES_UNIQUE.assertWasInvokedOn(expectedCSInterfaces);
	}

	/**
	 * A <code>Predicate</code> which keeps track to which <code>EObject</code>s it was applied.
	 * <code>MockPredicates</code> can be used to verify that a <code>PredicateBasedConstraint</code> is actually called
	 * for a given <code>EObject</code>.
	 * 
	 * @param <T>
	 */
	public static class MockPredicate<T> implements ExplainablePredicate<T> {

		private Collection<T> fEObjects = new ArrayList<T>();

		public void clear() {
			fEObjects.clear();
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean apply(T eObject) {
			fEObjects.add(eObject);
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		public Reason explain() {
			return Reason.OK;
		}

		/**
		 * Asserts that the <code>MockPredicate</code> was invoked for the given set of expected <code>EObject</code>s.
		 * 
		 * @param expectedEObjects
		 *            the <code>EObject</code>s this <code>MockPredicate</code> is expected to have been called for.
		 */
		public void assertWasInvokedOn(T... expectedEObjects) {
			assertEquals("The number of EObjects to which the Constraint was applied is not correct.", expectedEObjects.length, fEObjects.size()); //$NON-NLS-1$
			for (T expectedEObject : expectedEObjects) {
				assertTrue("The Constraint was not applied to the following EObject although expected: " + expectedEObject, //$NON-NLS-1$
						fEObjects.contains(expectedEObject));
			}
		}

		/**
		 * Asserts that the <code>MockPredicate</code> was not invoked.
		 */
		public void assertWasNotInvoked() {
			assertTrue("Constraint was invoked although it should not have been!", fEObjects.isEmpty()); //$NON-NLS-1$
		}

	}

}
