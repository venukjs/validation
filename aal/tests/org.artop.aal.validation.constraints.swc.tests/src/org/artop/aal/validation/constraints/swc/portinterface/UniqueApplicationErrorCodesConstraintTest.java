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

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests if the failure messages returned by the <code>UniqueApplicationErrorCodesConstraint</code> are correct.
 */
@SuppressWarnings("nls")
public class UniqueApplicationErrorCodesConstraintTest /* extends PredicateBasedConstraintTest */{

	// @Override
	// protected AbstractModelConstraint createConstraintUnderTest() {
	// return new UniqueApplicationErrorCodesConstraint();
	// }

	@Test
	public void disabledTest() {
		Assert.fail("Please visit this class and fix the compilation errors");
	}

	// @Test
	// public void shouldPassWithUniqueErrorCodes() {
	// getSwcServiceProvider().setHasUniqueErrorCodesPredicate(uniqueErrorCodes());
	// assertSuccessfulValidation();
	// }
	//
	// @Test
	// public void shouldCreateOneFailureMessageWithTwoErrorCodes() {
	// getSwcServiceProvider().setHasUniqueErrorCodesPredicate(nonUniqueErrorCodes(multiplyAssignedErrorCodeValue(0,
	// "IDLE", "INIT")));
	// String expectedMessage = "More than one ApplicationErrorCode has the value \"0\": IDLE, INIT";
	// assertFailedValidation(expectedMessage);
	// }
	//
	// @Test
	// public void shouldCreateOneFailureMessageWithThreeErrorCodes() {
	// getSwcServiceProvider().setHasUniqueErrorCodesPredicate(nonUniqueErrorCodes(multiplyAssignedErrorCodeValue(0,
	// "IDLE", "INIT", "RUNNING")));
	// String expectedMessage = "More than one ApplicationErrorCode has the value \"0\": IDLE, INIT, RUNNING";
	// assertFailedValidation(expectedMessage);
	// }
	//
	// @Test
	// public void shouldCreateTwoFailureMessages() {
	// getSwcServiceProvider().setHasUniqueErrorCodesPredicate(
	// nonUniqueErrorCodes(multiplyAssignedErrorCodeValue(0, "IDLE", "INIT"), multiplyAssignedErrorCodeValue(1,
	// "RUNNING", "WAITING")));
	// String expectedMessage1 = "More than one ApplicationErrorCode has the value \"0\": IDLE, INIT";
	// String expectedMessage2 = "More than one ApplicationErrorCode has the value \"1\": RUNNING, WAITING";
	// assertFailedValidation(expectedMessage1, expectedMessage2);
	// }

}
