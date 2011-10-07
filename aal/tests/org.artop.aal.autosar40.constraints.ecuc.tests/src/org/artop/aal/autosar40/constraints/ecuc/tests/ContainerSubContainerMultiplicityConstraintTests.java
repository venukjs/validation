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
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("nls")
public class ContainerSubContainerMultiplicityConstraintTests extends AbstractAutosar40ValidationTestCase {

	public ContainerSubContainerMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.ContainerSubContainerMultiplicityConstraint_40";//$NON-NLS-1$
	}

	public void testInvalidContainer_upperMultiplicityOfSubContainerViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/upperMultiplicityOfSubContainerViolated.arxml");
		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.ERROR,
				NLS.bind(Messages.multiplicity_maxElementsExpected, new String[] { "2", "subcontainers",
						"/AUTOSAR/Os/ParamConfContainerDef/SubContainerDef", "3" }));
	}

	// valid
	public void testValidContainer_MultipleConfigurationContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/upperMultiplicityOfMultipleConfigSubContainerValid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
