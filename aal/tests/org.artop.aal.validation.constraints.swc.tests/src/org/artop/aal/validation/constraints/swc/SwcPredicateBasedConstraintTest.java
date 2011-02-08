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
