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
package org.artop.aal.validation.constraints.swc;

import org.artop.aal.gautosar.services.IMetaModelServiceProvider;
import org.artop.aal.validation.constraints.PredicateBasedConstraint;
import org.artop.aal.validation.constraints.swc.mock.MockSwcPredicatesServiceProvider;
import org.artop.aal.validation.testutils.PredicateBasedConstraintTest;

public class SwcPredicateBasedConstraintTest extends PredicateBasedConstraintTest {

	@Override
	protected PredicateBasedConstraint createConstraintUnderTest() {
		// TODO Auto-generated method stub
		return null;
	}

	protected MockSwcPredicatesServiceProvider getSwcServiceProvider() {
		return (MockSwcPredicatesServiceProvider) fServiceProvider;
	}

	@Override
	protected IMetaModelServiceProvider createMockPredicatesServiceProvider() {
		return new MockSwcPredicatesServiceProvider();
	}

}
