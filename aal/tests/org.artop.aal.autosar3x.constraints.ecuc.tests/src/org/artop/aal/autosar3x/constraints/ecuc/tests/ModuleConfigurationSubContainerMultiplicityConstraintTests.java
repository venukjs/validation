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

public class ModuleConfigurationSubContainerMultiplicityConstraintTests extends AbstractAutosar3xValidationTestCase
{
	public ModuleConfigurationSubContainerMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ModuleConfigurationSubContainerMultiplicityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidModuleConfiguration_upperMultiplicityOfSubContainerViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/upperMultiplicityOfSubContainerViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,NLS.bind(Messages.multiplicity_maxElementsExpected, new String[]{"1","subcontainers","/AUTOSAR/Os/OsAlarm","2"}));
	}

	public void testInvalidModuleConfiguration_lowerMultiplicityOfSubContainerViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/lowerMultiplicityOfSubContainerViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,NLS.bind(Messages.multiplicity_minElementsExpected, new String[]{"2","subcontainers","/AUTOSAR/Os/OsAlarm1","1"}));
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
	
	public void testValidSubContainer_MultipleConfigurationContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/upperMultiplicityOfMultipleConfigSubContainerValid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
