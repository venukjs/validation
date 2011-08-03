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
package org.artop.aal.validation.constraints.swc.portinterface;

import static org.artop.aal.gautosar.services.builder.swc.portinterface.GClientServerInterfaceBuilder.clientServerInterface;
import gautosar.gswcomponents.gportinterface.GClientServerInterface;

import org.artop.aal.gautosar.services.builder.GMaker;
import org.artop.aal.gautosar.services.builder.swc.portinterface.GClientServerInterfaceBuilder;
import org.artop.aal.validation.testutils.PredicateBasedConstraintTest;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.junit.Test;

import autosar40.util.Autosar40ReleaseDescriptor;

/**
 * Tests if the failure messages returned by the <code>UniqueApplicationErrorCodesConstraint</code> are correct. This
 * test shall not test the logic of the Constraint as the logic is implemented in the HasUniqueErrorCodesPredicate. The
 * UniqueApplicationErrorCodesConstraint only wraps the Predicate and provides the messages.
 */
@SuppressWarnings("nls")
public class UniqueApplicationErrorCodesConstraintTest extends PredicateBasedConstraintTest {

	@Override
	protected AbstractModelConstraint createConstraintUnderTest() {
		return new UniqueApplicationErrorCodesConstraint();
	}

	@Test
	public void shouldPassWithUniqueErrorCodes() {
		assertSuccessfulValidation(make(clientServerInterface("CSIfc")));
	}

	@Test
	public void shouldCreateOneFailureMessageWithTwoErrorCodes() {
		GClientServerInterfaceBuilder csIfc = clientServerInterface("csIfc").possibleError("WRONG_STATE", 0).possibleError("NOT_INITIALIZED", 0);
		String expectedMessage = "More than one ApplicationErrorCode has the value \"0\": WRONG_STATE, NOT_INITIALIZED";
		assertFailedValidation(make(csIfc), expectedMessage);
	}

	@Test
	public void shouldCreateOneFailureMessageWithThreeErrorCodes() {
		GClientServerInterfaceBuilder csIfc = clientServerInterface("csIfc").possibleError("WRONG_STATE", 0).possibleError("NOT_INITIALIZED", 0)
				.possibleError("NEGATIVE_VALUE", 0);
		String expectedMessage = "More than one ApplicationErrorCode has the value \"0\": WRONG_STATE, NOT_INITIALIZED, NEGATIVE_VALUE";
		assertFailedValidation(make(csIfc), expectedMessage);
	}

	@Test
	public void shouldCreateTwoFailureMessages() {
		GClientServerInterfaceBuilder csIfc = clientServerInterface("csIfc").possibleError("WRONG_STATE", 0).possibleError("NOT_INITIALIZED", 0)
				.possibleError("NEGATIVE_VALUE", 1).possibleError("DIVISION_BY_ZERO", 1);
		String expectedMessage1 = "More than one ApplicationErrorCode has the value \"0\": WRONG_STATE, NOT_INITIALIZED";
		String expectedMessage2 = "More than one ApplicationErrorCode has the value \"1\": NEGATIVE_VALUE, DIVISION_BY_ZERO";
		assertFailedValidation(make(csIfc), expectedMessage1, expectedMessage2);
	}

	private GClientServerInterface make(GClientServerInterfaceBuilder builder) {
		return GMaker.make(Autosar40ReleaseDescriptor.INSTANCE).from(builder);
	}

}
