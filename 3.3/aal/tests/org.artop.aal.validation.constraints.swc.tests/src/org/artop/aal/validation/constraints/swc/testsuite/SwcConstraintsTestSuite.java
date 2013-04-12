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
package org.artop.aal.validation.constraints.swc.testsuite;

import org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationErrorCodesConstraintTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * The test-suite containing all SWC Constraint tests.
 */
@RunWith(Suite.class)
@SuiteClasses(UniqueApplicationErrorCodesConstraintTest.class)
public class SwcConstraintsTestSuite {

}
