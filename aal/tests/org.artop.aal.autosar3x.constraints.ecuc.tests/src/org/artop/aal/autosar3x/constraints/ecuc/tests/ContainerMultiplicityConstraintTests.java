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
package org.artop.aal.autosar3x.constraints.ecuc.tests;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("nls")
public class ContainerMultiplicityConstraintTests extends ValidationTestCase {

	public ContainerMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ContainerMultiplicityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidContainer_choiceContainerMoreThanOneChoiceSelected() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/choiceContainerMoreThanOneChoiceSelected.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidContainer_choiceContainerNoChoiceSelected() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/choiceContainerNoChoiceSelected.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidContainer_lowerMultiplicityOfSubContainerViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/lowerMultiplicityOfSubContainerViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidContainer_upperMultiplicityOfSubContainerViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/upperMultiplicityOfSubContainerViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// valid
	public void testValidContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
