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

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("nls")
public class ContainerReferenceMultiplicityConstraintTest extends AbstractAutosar40ValidationTestCase {

	public ContainerReferenceMultiplicityConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.ContainerReferenceValueMultiplicityConstraint_40";//$NON-NLS-1$
	}

	// valid
	public void testValidReference_upperMultiplicityStar() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/upperMultiplicityStarOfReferenceValid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	/**
	 * Tests that an error message is NOT thrown when a reference of an already existing container with multiplicity 0
	 * is not set
	 *
	 * @throws Exception
	 */
	public void testValidReference_containerUpperMultiplicityZero() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/upperMultiplicityZeroOfContainerRefValid.arxml");

		IStatus status = validator.validate(validModel);
		assertFalse(status.isOK());

		// this message shall exist as the container is created when its multiplicity is 0
		assertTrue(status.getMessage().contains(
				NLS.bind(EcucConstraintMessages.multiplicity_maxElementsExpected, new String[] { "0", "subcontainers",
						"/ARRoot/EcucModuleDef/TestContainer", "1" })));

		// this message shall not exist when the container of the given reference has the multiplicity 0
		assertTrue(!status.getMessage().contains(
				NLS.bind(EcucConstraintMessages.multiplicity_maxElementsExpected, new String[] { "1", "config reference values",
						"/ARRoot/EcucModuleDef/TestContainer/TestRef", "0" })));
	}

}
