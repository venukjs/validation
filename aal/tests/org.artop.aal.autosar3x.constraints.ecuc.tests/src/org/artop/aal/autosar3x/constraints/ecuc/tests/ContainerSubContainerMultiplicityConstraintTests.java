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
package org.artop.aal.autosar3x.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("nls")
public class ContainerSubContainerMultiplicityConstraintTests extends AbstractAutosar3xValidationTestCase {
	public ContainerSubContainerMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ContainerSubContainerMultiplicityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidContainer_choiceContainerMoreThanOneChoiceSelected() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/choiceContainerMoreThanOneChoiceSelected.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(Messages.multiplicity_subContainersExpected, "choice container"));
	}

	public void testInvalidContainer_choiceContainerMoreThanOneChoiceSelected_splitted() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/choiceContainerMoreThanOneChoiceSelected_splitted.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(Messages.multiplicity_subContainersExpected, "choice container"));
	}

	public void testInvalidContainer_choiceContainerNoChoiceSelected() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/choiceContainerNoChoiceSelected.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(Messages.multiplicity_subContainersExpected, "choice container"));
	}

	public void testInvalidContainer_lowerMultiplicityOfSubContainerViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/lowerMultiplicityOfSubContainerViolated.arxml");
		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.ERROR,
				NLS.bind(Messages.multiplicity_minElementsExpected, new String[] { "2", "subcontainers",
						"/AUTOSAR/Os/ParamConfContainerDef/SubContainerDef", "1" }));
	}

	public void testInvalidContainer_mandatorySubContainerMissing() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/mandatorySubContainerMissing.arxml");
		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.ERROR,
				NLS.bind(Messages.multiplicity_minElementsExpected, new String[] { "1", "subcontainers",
						"/AUTOSAR/Os/ParamConfContainerDef/SubContainerDef", "0" }));
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
	public void testValidContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidContainer_splitted() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid_splitted.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidContainer_splitted_big() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid_splitted_100containers.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidContainer_MultipleConfigurationContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/upperMultiplicityOfMultipleConfigSubContainerValid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
