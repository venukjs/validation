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

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
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
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_definitionReferenceNotSet);
	}

	// consistency

	public void testInvalidContainer_containerNotAllowedInParamConfContainer() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/containerNotAllowedInParamConfContainer.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.structuralIntegrity_containmentProblem, "container", "Alternative1"));
	}

	public void testInvalidContainer_containerNotAllowedInChoiceContainer() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/containerNotAllowedInChoiceContainer.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.structuralIntegrity_containmentProblem, "container", "ParamConfContainerDef1"));
	}

	public void testInvalidContainer_containerNotAllowedInModuleConfiguration() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/containerNotAllowedInModuleConfiguration.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.structuralIntegrity_containmentProblem, "container", "ParamConfContainerDef1"));
	}

	// correctness

	// valid
	public void testValidContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
