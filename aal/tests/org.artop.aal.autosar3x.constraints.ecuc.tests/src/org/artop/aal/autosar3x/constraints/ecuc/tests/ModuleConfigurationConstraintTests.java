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
public class ModuleConfigurationConstraintTests extends ValidationTestCase {

	public ModuleConfigurationConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.validation.ecuc.ModuleConfigurationConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidModuleConfiguration_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidModuleConfiguration_proxyDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/proxyDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// consistency
	public void testInValidModuleConfiguration_implConfigValueWrongValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/implConfigValueWrongValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// correctness
	public void testInValidModuleConfiguration_noSupportedConfigVariant() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/noSupportedConfigVariant.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInValidModuleConfiguration_implConfigVariantNotSupported() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/implConfigVariantNotSupported.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// valid
	public void testValidModuleConfiguration() throws Exception {
		EObject validModel = loadInputFile("ecuc/ModuleConfiguration/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
