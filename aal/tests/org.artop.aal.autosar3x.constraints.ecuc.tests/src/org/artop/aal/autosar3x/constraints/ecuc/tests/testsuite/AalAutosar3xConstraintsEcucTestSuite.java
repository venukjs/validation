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

package org.artop.aal.autosar3x.constraints.ecuc.tests.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.artop.aal.autosar3x.constraints.ecuc.tests.BooleanValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ConfigReferenceValueStructuralIntegrityConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerMultiplicityConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerStructuralIntegrityConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.EnumerationValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.FloatValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.FunctionNameValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.InstanceReferenceValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.IntegerValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.LinkerSymbolValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ModuleConfigurationConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ParameterValueStructuralIntegrityConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ReferenceValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.StringValueConstraintTests;

public class AalAutosar3xConstraintsEcucTestSuite {

	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for org.artop.aal.autosar3x.validation.ecuc"); //$NON-NLS-1$

		// basic attributes an definition
		suite.addTestSuite(IntegerValueConstraintTests.class);
		suite.addTestSuite(StringValueConstraintTests.class);
		suite.addTestSuite(BooleanValueConstraintTests.class);
		suite.addTestSuite(FloatValueConstraintTests.class);
		suite.addTestSuite(EnumerationValueConstraintTests.class);
		suite.addTestSuite(ReferenceValueConstraintTests.class);
		suite.addTestSuite(InstanceReferenceValueConstraintTests.class);
		suite.addTestSuite(ModuleConfigurationConstraintTests.class);
		suite.addTestSuite(ContainerConstraintTests.class);
		suite.addTestSuite(LinkerSymbolValueConstraintTests.class);
		suite.addTestSuite(FunctionNameValueConstraintTests.class);

		// structural integrity
		suite.addTestSuite(ConfigReferenceValueStructuralIntegrityConstraintTests.class);
		suite.addTestSuite(ParameterValueStructuralIntegrityConstraintTests.class);
		suite.addTestSuite(ContainerStructuralIntegrityConstraintTests.class);
		suite.addTestSuite(ContainerMultiplicityConstraintTests.class);

		return suite;
	}
}