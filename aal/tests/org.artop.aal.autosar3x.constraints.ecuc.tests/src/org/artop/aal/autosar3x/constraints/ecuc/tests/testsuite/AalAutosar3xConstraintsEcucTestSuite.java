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

import org.artop.aal.autosar3x.constraints.ecuc.tests.BooleanValueConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ChoiceReferenceParamDefBasicConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerParameterValueMultiplicityConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerReferenceValueMultiplicityConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ContainerSubContainerMultiplicityConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.EnumerationValueConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.FloatValueConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.FunctionNameDefConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.FunctionNameValueConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.InstanceReferenceValueConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.IntegerValueConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.LinkerSymbolDefConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.LinkerSymbolValueConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ModuleConfigurationConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ModuleConfigurationSubContainerMultiplicityConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ParamConfMultiplicityBasicConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ReferenceParamDefBasicConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.ReferenceValueConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.StringParamDefConstraintTest;
import org.artop.aal.autosar3x.constraints.ecuc.tests.StringValueConstraintTest;

public class AalAutosar3xConstraintsEcucTestSuite {
	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for ecuc validation constraints for Autosar 3.x"); //$NON-NLS-1$

		// basic attributes and definition
		suite.addTestSuite(IntegerValueConstraintTest.class);
		suite.addTestSuite(StringValueConstraintTest.class);
		suite.addTestSuite(BooleanValueConstraintTest.class);
		suite.addTestSuite(FloatValueConstraintTest.class);
		suite.addTestSuite(EnumerationValueConstraintTest.class);
		suite.addTestSuite(ReferenceValueConstraintTest.class);
		suite.addTestSuite(InstanceReferenceValueConstraintTest.class);
		suite.addTestSuite(ModuleConfigurationConstraintTest.class);
		suite.addTestSuite(ContainerConstraintTest.class);
		suite.addTestSuite(LinkerSymbolValueConstraintTest.class);
		suite.addTestSuite(FunctionNameValueConstraintTest.class);
		suite.addTestSuite(ParamConfMultiplicityBasicConstraintTest.class);
		suite.addTestSuite(ChoiceReferenceParamDefBasicConstraintTest.class);
		suite.addTestSuite(ReferenceParamDefBasicConstraintTest.class);
		suite.addTestSuite(StringParamDefConstraintTest.class);
		suite.addTestSuite(FunctionNameDefConstraintTest.class);
		suite.addTestSuite(LinkerSymbolDefConstraintTest.class);

		// structural integrity
		suite.addTestSuite(ContainerSubContainerMultiplicityConstraintTest.class);
		suite.addTestSuite(ContainerParameterValueMultiplicityConstraintTest.class);
		suite.addTestSuite(ContainerReferenceValueMultiplicityConstraintTest.class);
		suite.addTestSuite(ModuleConfigurationSubContainerMultiplicityConstraintTest.class);

		return suite;
	}
}
