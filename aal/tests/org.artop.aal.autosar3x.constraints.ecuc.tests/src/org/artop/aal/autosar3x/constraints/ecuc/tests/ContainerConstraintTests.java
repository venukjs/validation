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
public class ContainerConstraintTests extends AbstractAutosar3xValidationTestCase {
	public ContainerConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ContainerBasicConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidContainer_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.generic_definitionReferenceNotSet);
	}

	// consistency
	public void testInvalidContainer_noDefInParentDef() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/containerDefinitionNotInParentDef.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(Messages.structuralIntegrity_containmentProblem, "container", "ChoiceContainerDef"));
	}

	public void testInvalidContainer_containerDefinitionNotInParentDef() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/defNotFoundInParent.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(Messages.structuralIntegrity_containmentProblem, "container", "ParamConfContainerDef"));
	}

	// correctness

	// valid
	public void testValidContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
