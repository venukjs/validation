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
