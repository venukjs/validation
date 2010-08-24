/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
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
package org.artop.aal.examples.autosar3x.constraints.tests.integration.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AalExamplesValidationTestIntegrationTestSuite {

	@SuppressWarnings("nls")
	public static Test suite() {
		TestSuite suite = new TestSuite("Test for  org.artop.aal.examples.validation.tests.integration");
		// $JUnit-BEGIN$
		suite.addTestSuite(org.artop.aal.examples.autosar3x.constraints.tests.integration.constraints.ExampleValidationConstraintsTest.class);
		// $JUnit-END$
		return suite;
	}

}
