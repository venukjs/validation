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
public class ModuleConfigurationSubContainerMultiplicityConstraintTests extends ValidationTestCase {

	public ModuleConfigurationSubContainerMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ModuleConfigurationSubContainerMultiplicityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidModuleConfiguration_upperMultiplicityOfSubContainerViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/upperMultiplicityOfSubContainerViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidModuleConfiguration_lowerMultiplicityOfSubContainerViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/lowerMultiplicityOfSubContainerViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// valid
	public void testValidModuleConfiguration() throws Exception {
		EObject validModel = loadInputFile("ecuc/ModuleConfiguration/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidModuleConfiguration_splitted() throws Exception {
		EObject validModel = loadInputFile("ecuc/ModuleConfiguration/valid_splitted.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
