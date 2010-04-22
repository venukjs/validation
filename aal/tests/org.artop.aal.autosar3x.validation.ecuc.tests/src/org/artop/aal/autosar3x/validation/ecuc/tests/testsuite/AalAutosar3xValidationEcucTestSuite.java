/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 * 
 * </copyright>
 */

package org.artop.aal.autosar3x.validation.ecuc.tests.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.artop.aal.autosar3x.validation.ecuc.tests.BooleanValueTests;
import org.artop.aal.autosar3x.validation.ecuc.tests.EnumerationValueTests;
import org.artop.aal.autosar3x.validation.ecuc.tests.FloatValueTests;
import org.artop.aal.autosar3x.validation.ecuc.tests.InstanceReferenceValueTests;
import org.artop.aal.autosar3x.validation.ecuc.tests.IntegerValueTests;
import org.artop.aal.autosar3x.validation.ecuc.tests.ModuleConfigurationTests;
import org.artop.aal.autosar3x.validation.ecuc.tests.ReferenceValueTests;
import org.artop.aal.autosar3x.validation.ecuc.tests.StringValueTests;

public class AalAutosar3xValidationEcucTestSuite {

	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for org.artop.aal.autosar3x.validation.ecuc"); //$NON-NLS-1$

		// ecuc
		suite.addTestSuite(IntegerValueTests.class);
		suite.addTestSuite(StringValueTests.class);
		suite.addTestSuite(BooleanValueTests.class);
		suite.addTestSuite(FloatValueTests.class);
		suite.addTestSuite(EnumerationValueTests.class);
		suite.addTestSuite(ReferenceValueTests.class);
		suite.addTestSuite(InstanceReferenceValueTests.class);
		suite.addTestSuite(ModuleConfigurationTests.class);

		return suite;
	}
}