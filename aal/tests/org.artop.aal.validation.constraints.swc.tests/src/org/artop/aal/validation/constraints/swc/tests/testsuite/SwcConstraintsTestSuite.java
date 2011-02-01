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
package org.artop.aal.validation.constraints.swc.tests.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.artop.aal.validation.constraints.swc.tests.portinterface.UniqueApplicationErrorCodesConstraintTest;

/**
 * The test-suite containing all SWC Constraint tests.
 */
public class SwcConstraintsTestSuite {

	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for the SWC constraints"); //$NON-NLS-1$
		suite.addTestSuite(UniqueApplicationErrorCodesConstraintTest.class);
		return suite;
	}
}
