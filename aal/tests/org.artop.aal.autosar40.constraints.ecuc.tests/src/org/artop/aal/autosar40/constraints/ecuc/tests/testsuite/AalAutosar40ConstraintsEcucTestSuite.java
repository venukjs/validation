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

import org.artop.aal.autosar40.constraints.ecuc.tests.AddInfoValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.BooleanValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.ContainerSubContainerMultiplicityConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.EcucUtilTest;
import org.artop.aal.autosar40.constraints.ecuc.tests.EnumerationValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.FloatValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.FunctionNameDefConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.FunctionNameValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.InstanceReferenceValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.IntegerValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.LinkerSymbolDefConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.LinkerSymbolValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.ModuleConfigurationConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.ModuleConfigurationSubContainerMultiplicityConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.MultilineStringValueConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.StringParamDefConstraintTests;
import org.artop.aal.autosar40.constraints.ecuc.tests.StringValueConstraintTests;

public class AalAutosar40ConstraintsEcucTestSuite {
	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Autosar 4.0 ecuc validation constraints"); //$NON-NLS-1$

		// basic attributes and definition
		suite.addTestSuite(IntegerValueConstraintTests.class);
		suite.addTestSuite(StringValueConstraintTests.class);
		suite.addTestSuite(AddInfoValueConstraintTests.class);
		suite.addTestSuite(MultilineStringValueConstraintTests.class);
		suite.addTestSuite(BooleanValueConstraintTests.class);
		suite.addTestSuite(FloatValueConstraintTests.class);
		suite.addTestSuite(EnumerationValueConstraintTests.class);
		// suite.addTestSuite(ReferenceValueConstraintTests.class);
		suite.addTestSuite(InstanceReferenceValueConstraintTests.class);
		suite.addTestSuite(ModuleConfigurationConstraintTests.class);
		// suite.addTestSuite(ContainerConstraintTests.class);
		suite.addTestSuite(LinkerSymbolValueConstraintTests.class);
		suite.addTestSuite(FunctionNameValueConstraintTests.class);
		// suite.addTestSuite(ParamConfMultiplicityBasicConstraintTests.class);
		// suite.addTestSuite(ChoiceReferenceParamDefBasicConstraintTests.class);
		// suite.addTestSuite(ReferenceParamDefBasicConstraintTests.class);
		suite.addTestSuite(StringParamDefConstraintTests.class);
		suite.addTestSuite(FunctionNameDefConstraintTests.class);
		suite.addTestSuite(LinkerSymbolDefConstraintTests.class);
		//
		// // structural integrity
		// suite.addTestSuite(ConfigReferenceValueStructuralIntegrityConstraintTests.class);
		// suite.addTestSuite(ParameterValueStructuralIntegrityConstraintTests.class);
		// suite.addTestSuite(ContainerStructuralIntegrityConstraintTests.class);
		suite.addTestSuite(ContainerSubContainerMultiplicityConstraintTests.class);
		// suite.addTestSuite(ContainerParameterValueMultiplicityConstraintTests.class);
		// suite.addTestSuite(ContainerReferenceValueMultiplicityConstraintTests.class);
		suite.addTestSuite(ModuleConfigurationSubContainerMultiplicityConstraintTests.class);
		suite.addTestSuite(EcucUtilTest.class);

		return suite;
	}
}
