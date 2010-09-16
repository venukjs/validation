/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy,  Continental Engineering Services  and others.
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
package org.artop.aal.gautosar.constraints.ecuc.tests.testsuite;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AalGautosarConstraintsEcucTestSuite {
	/**
	 * Suite.
	 * 
	 * @return the test
	 */
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for ecuc validation constraints for GAutosar"); //$NON-NLS-1$
		return suite;
	}

}
