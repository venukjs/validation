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
package org.artop.aal.autosar3x.validation.ecuc.tests;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("nls")
public class BooleanValueTests extends ValidationTestCase {

	public BooleanValueTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.validation.ecuc.BooleanValueConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidBooleanValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/BooleanValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidBooleanValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/BooleanValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidBooleanValue_invalidValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/BooleanValue/invalidValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// consistency
	public void testInvalidBooleanValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/BooleanValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidBooleanValue_notContainedInDefinitionOfParentContaine() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/BooleanValue/notContainedInDefinitionOfParentContainer.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// correctness

	// valid
	public void testValidBooleanValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/BooleanValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
