/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc.tests.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.artop.aal.autosar3x.constraints.ecuc.tests.BooleanValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ChoiceReferenceParamDefBasicConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerParameterValueMultiplicityConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerReferenceValueMultiplicityConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerSubContainerMultiplicityConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.EnumerationValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.FloatValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.FunctionNameDefConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.FunctionNameValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.InstanceReferenceValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.IntegerValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.LinkerSymbolDefConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.LinkerSymbolValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ModuleConfigurationConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ModuleConfigurationSubContainerMultiplicityConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ParamConfMultiplicityBasicConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ReferenceParamDefBasicConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ReferenceValueConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.StringParamDefConstraintTests;
import org.artop.aal.autosar3x.constraints.ecuc.tests.StringValueConstraintTests;

public class AalAutosar3xConstraintsEcucTestSuite {
	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for ecuc validation constraints for Autosar 3.x"); //$NON-NLS-1$

		// basic attributes and definition
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
		suite.addTestSuite(ParamConfMultiplicityBasicConstraintTests.class);
		suite.addTestSuite(ChoiceReferenceParamDefBasicConstraintTests.class);
		suite.addTestSuite(ReferenceParamDefBasicConstraintTests.class);
		suite.addTestSuite(StringParamDefConstraintTests.class);
		suite.addTestSuite(FunctionNameDefConstraintTests.class);
		suite.addTestSuite(LinkerSymbolDefConstraintTests.class);

		// structural integrity
		suite.addTestSuite(ContainerSubContainerMultiplicityConstraintTests.class);
		suite.addTestSuite(ContainerParameterValueMultiplicityConstraintTests.class);
		suite.addTestSuite(ContainerReferenceValueMultiplicityConstraintTests.class);
		suite.addTestSuite(ModuleConfigurationSubContainerMultiplicityConstraintTests.class);

		return suite;
	}
}
