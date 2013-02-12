/**
 * <copyright>
 * 
 * Copyright (c) Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Continental Engineering Services - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar40.constraints.ecuc.tests.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.artop.aal.autosar40.constraints.ecuc.tests.AddInfoValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.BooleanValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.ContainerSubContainerMultiplicityConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.EcucUtilTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.EnumerationValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.FloatValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.FunctionNameDefConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.FunctionNameValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.InstanceReferenceValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.IntegerValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.LinkerSymbolDefConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.LinkerSymbolValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.ModuleConfigurationConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.ModuleConfigurationSubContainerMultiplicityConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.MultilineStringValueConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.StringParamDefConstraintTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.StringValueConstraintTest;

public class AalAutosar40ConstraintsEcucTestSuite {
	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Autosar 4.0 ecuc validation constraints"); //$NON-NLS-1$

		// basic attributes and definition
		suite.addTestSuite(IntegerValueConstraintTest.class);
		suite.addTestSuite(StringValueConstraintTest.class);
		suite.addTestSuite(AddInfoValueConstraintTest.class);
		suite.addTestSuite(MultilineStringValueConstraintTest.class);
		suite.addTestSuite(BooleanValueConstraintTest.class);
		suite.addTestSuite(FloatValueConstraintTest.class);
		suite.addTestSuite(EnumerationValueConstraintTest.class);
		// suite.addTestSuite(ReferenceValueConstraintTests.class);
		suite.addTestSuite(InstanceReferenceValueConstraintTest.class);
		suite.addTestSuite(ModuleConfigurationConstraintTest.class);
		// suite.addTestSuite(ContainerConstraintTests.class);
		suite.addTestSuite(LinkerSymbolValueConstraintTest.class);
		suite.addTestSuite(FunctionNameValueConstraintTest.class);
		// suite.addTestSuite(ParamConfMultiplicityBasicConstraintTests.class);
		// suite.addTestSuite(ChoiceReferenceParamDefBasicConstraintTests.class);
		// suite.addTestSuite(ReferenceParamDefBasicConstraintTests.class);
		suite.addTestSuite(StringParamDefConstraintTest.class);
		suite.addTestSuite(FunctionNameDefConstraintTest.class);
		suite.addTestSuite(LinkerSymbolDefConstraintTest.class);
		//
		// // structural integrity
		// suite.addTestSuite(ConfigReferenceValueStructuralIntegrityConstraintTests.class);
		// suite.addTestSuite(ParameterValueStructuralIntegrityConstraintTests.class);
		// suite.addTestSuite(ContainerStructuralIntegrityConstraintTests.class);
		suite.addTestSuite(ContainerSubContainerMultiplicityConstraintTests.class);
		// suite.addTestSuite(ContainerParameterValueMultiplicityConstraintTests.class);
		// suite.addTestSuite(ContainerReferenceValueMultiplicityConstraintTests.class);
		suite.addTestSuite(ModuleConfigurationSubContainerMultiplicityConstraintTest.class);
		suite.addTestSuite(EcucUtilTest.class);

		return suite;
	}
}
