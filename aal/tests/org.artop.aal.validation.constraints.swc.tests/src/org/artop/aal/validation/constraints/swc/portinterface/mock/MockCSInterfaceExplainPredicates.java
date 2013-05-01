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
package org.artop.aal.validation.constraints.swc.portinterface.mock;

import gautosar.gswcomponents.gportinterface.GPortInterface;

import java.util.Arrays;

import org.artop.aal.gautosar.services.predicates.ExplainablePredicate;
import org.artop.aal.gautosar.services.predicates.Reason;
import org.artop.aal.gautosar.services.predicates.swc.portinterface.HasUniqueErrorCodes.DuplicateErrorCodes;
import org.artop.aal.gautosar.services.predicates.swc.portinterface.HasUniqueErrorCodes.DuplicateErrorCodes.MultiplyAssignedErrorCodeValue;

public class MockCSInterfaceExplainPredicates {

	private static final UniqueErrorCodes UNIQUE_ERRORCODES = new UniqueErrorCodes();

	public static UniqueErrorCodes uniqueErrorCodes() {
		return UNIQUE_ERRORCODES;
	}

	public static NonUniqueErrorCodes nonUniqueErrorCodes(MultiplyAssignedErrorCodeValue... errorCodeSets) {
		return new NonUniqueErrorCodes(errorCodeSets);
	}

	private static class UniqueErrorCodes implements ExplainablePredicate<GPortInterface> {

		public boolean apply(GPortInterface csInterface) {
			return true;
		}

		public Reason explain() {
			return Reason.OK;
		}

	}

	private static class NonUniqueErrorCodes implements ExplainablePredicate<GPortInterface> {

		private MultiplyAssignedErrorCodeValue[] fErrorCodeSets;

		public NonUniqueErrorCodes(MultiplyAssignedErrorCodeValue[] errorCodeSets) {
			fErrorCodeSets = errorCodeSets;
		}

		public boolean apply(GPortInterface csInterface) {
			return false;
		}

		public Reason explain() {
			return MockDuplicateErrorCodes.create(fErrorCodeSets);
		}

		private static class MockDuplicateErrorCodes extends DuplicateErrorCodes {

			public static DuplicateErrorCodes create(MultiplyAssignedErrorCodeValue[] errorCodeSets) {
				return new MockDuplicateErrorCodes(Arrays.asList(errorCodeSets));
			}

			protected MockDuplicateErrorCodes(Iterable<MultiplyAssignedErrorCodeValue> duplicateErrorCodes) {
				super(duplicateErrorCodes);
			}

		}
	}

}
