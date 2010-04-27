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
public class ChoiceReferenceParamDefBasicConstraintTests extends ValidationTestCase {

	public ChoiceReferenceParamDefBasicConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ChoiceReferenceParamDefBasicConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidChoiceReferenceParamDef_noDestination() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ChoiceReferenceParamDef/noDestination.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidChoiceReferenceParamDef_unresolvedDestination() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ChoiceReferenceParamDef/unresolvedDestination.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// valid
	public void testValidChoiceReferenceParamDef() throws Exception {
		EObject validModel = loadInputFile("ecuc/ChoiceReferenceParamDef/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
