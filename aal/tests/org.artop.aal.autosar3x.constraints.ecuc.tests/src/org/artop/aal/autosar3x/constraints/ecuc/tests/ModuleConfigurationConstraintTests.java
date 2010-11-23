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

@SuppressWarnings({ "restriction", "nls" })
public class ModuleConfigurationConstraintTests extends AbstractAutosar3xValidationTestCase {

	public ModuleConfigurationConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ModuleConfigurationBasicConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidModuleConfiguration_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.generic_definitionReferenceNotSet);
	}

	public void testInvalidModuleConfiguration_proxyDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/proxyDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.generic_definitionReferenceNotResolved);
	}

	// consistency
	public void testInValidModuleConfiguration_implConfigValueWrongValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/implConfigValueWrongValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, NLS.bind(
				org.artop.aal.autosar3x.constraints.ecuc.internal.Messages.moduleConfig_ImplConfigVariantNotSupported, "VariantPreCompile",
				"{VariantPostBuild}"));
	}

	public void testInValidModuleConfiguration_implConfigValueNotSet() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/implConfigValueNotSet.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				org.artop.aal.autosar3x.constraints.ecuc.internal.Messages.moduleConfig_ImplConfigVariantNotSet);
	}

	// correctness
	public void testInValidModuleConfiguration_noSupportedConfigVariant() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/noSupportedConfigVariant.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, NLS.bind(
				org.artop.aal.autosar3x.constraints.ecuc.internal.Messages.moduleConfig_ImplConfigVariantNotSupported, "VariantPostBuild", "{}"));
	}

	public void testInValidModuleConfiguration_implConfigVariantNotSupported() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/implConfigVariantNotSupported.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, NLS.bind(
				org.artop.aal.autosar3x.constraints.ecuc.internal.Messages.moduleConfig_ImplConfigVariantNotSupported, "VariantPostBuild",
				"{VariantLinkTime,VariantPreCompile}"));
	}

	// valid
	public void testValidModuleConfiguration() throws Exception {
		EObject validModel = loadInputFile("ecuc/ModuleConfiguration/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
