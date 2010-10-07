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
public class ConfigReferenceValueStructuralIntegrityConstraintTests extends ValidationTestCase {

	public ConfigReferenceValueStructuralIntegrityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ConfigReferenceValueStructuralIntegrityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidConfigReferenceValue_notContainedInDefinitionOfParentContainer() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ConfigReferenceValue/notContainedInDefinitionOfParentContainer.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test valid
	public void testValidConfigReferenceValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/ConfigReferenceValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
