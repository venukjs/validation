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

import org.artop.aal.autosar40.constraints.ecuc.internal.messages.Messages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings({ "restriction", "nls" })
public class ModuleConfigurationConstraintTest extends AbstractAutosar40ValidationTestCase {
	public ModuleConfigurationConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.ModuleConfigurationBasicConstraint_40";//$NON-NLS-1$
	}

	// consistency
	public void testInValidModuleConfiguration_implConfigValueNotSet() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/implConfigValueNotSet.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.moduleConfig_ImplConfigVariantNotSet);
	}

	// correctness
	public void testInValidModuleConfiguration_implConfigVariantNotSupported() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ModuleConfiguration/implConfigVariantNotSupported.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(Messages.moduleConfig_ImplConfigVariantNotSupported, "VariantPostBuild", "{VariantLinkTime,VariantPreCompile}"));
	}
}
