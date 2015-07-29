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
package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("nls")
public class ContainerParameterMultiplicityConstraintTest extends AbstractAutosar40ValidationTestCase {

	public ContainerParameterMultiplicityConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.ContainerParameterValueMultiplicityConstraint_40";//$NON-NLS-1$
	}

	// valid
	public void testValidParameter_upperMultiplicityStar() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/upperMultiplicityStarOfParameterValid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	/**
	 * Tests that an error message is NOT thrown when a parameter of an already existing container with multiplicity 0
	 * is not set
	 *
	 * @throws Exception
	 */
	public void testValidParameter_containerUpperMultiplicityZero() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/upperMultiplicityZeroOfContainerParamValid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);

	}
}
