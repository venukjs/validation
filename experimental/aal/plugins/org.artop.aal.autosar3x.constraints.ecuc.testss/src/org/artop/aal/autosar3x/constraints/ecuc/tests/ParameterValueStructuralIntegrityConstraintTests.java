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
package org.artop.aal.autosar3x.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

public class ParameterValueStructuralIntegrityConstraintTests extends AbstractAutosar3xValidationTestCase
{
	public ParameterValueStructuralIntegrityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ParameterValueStructuralIntegrityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidBooleanValue_notContainedInDefinitionOfParentContainer() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ParameterValue/notContainedInDefinitionOfParentContainer.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,NLS.bind(Messages.structuralIntegrity_containmentProblem, "parameter value","OsAppErrorHook2"));
	}
	
	public void testInvalidBooleanValue_notAllowedInChoiceContainers() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ParameterValue/notAllowedInChoiceContainers.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,NLS.bind(Messages.structuralIntegrity_NotAllowedInChoiceContainer, "reference values"));
	}

	public void testValidBooleanValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/ParameterValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
