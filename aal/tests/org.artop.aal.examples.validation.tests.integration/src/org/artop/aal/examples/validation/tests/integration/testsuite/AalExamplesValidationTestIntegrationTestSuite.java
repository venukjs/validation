/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on Released
 * AUTOSAR Material (ASLR) which accompanies this distribution, and is available
 * at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.examples.validation.tests.integration.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AalExamplesValidationTestIntegrationTestSuite {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for org.artop.ecl.emf.tests.testsuite");
		// $JUnit-BEGIN$
		suite.addTestSuite(org.artop.aal.examples.validation.tests.integration.constraints.ExampleValidationConstraintsTest.class);
		// $JUnit-END$
		return suite;
	}

}
