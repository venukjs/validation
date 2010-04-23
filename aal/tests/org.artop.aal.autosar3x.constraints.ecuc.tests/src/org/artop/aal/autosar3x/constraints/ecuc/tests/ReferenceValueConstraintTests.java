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
public class ReferenceValueConstraintTests extends ValidationTestCase {

	public ReferenceValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ReferenceValueConstraint_3x";//$NON-NLS-1$
	}

	// test completeness
	public void testInvalidReferenceValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test consistency
	public void testInvalidReferenceValue_defTypeInvalid() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/defTypeInvalid.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test correctness
	public void testInvalidReferenceValue_choiceReferenceParamDefnotContainer() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/choiceReferenceParamDefNotContainer.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_choiceReferenceParamDefNoDest() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/choiceReferenceParamDefNoDest.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_choiceReferenceParamDefDifferentDef() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/choiceReferenceParamDefDifferentDef.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_referenceParamDefnotContainer() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/referenceParamDefNotContainer.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_referenceParamDefNoDest() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/referenceParamDefNoDest.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_referenceParamDefContainerNotInDest() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/referenceParamDefContainerNotInDest.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_referenceParamDefDifferentDef() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/referenceParamDefDifferentDef.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_noForeignDestinationType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/noForeignDestinationType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidReferenceValue_valueTypeNotMatchWithForeignDestination() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ReferenceValue/valueTypeNotMatchWithForeignDestination.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test valid
	public void testValidReferenceValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/ReferenceValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidReferenceValue_validChoiceReferenceParamDef() throws Exception {
		EObject validModel = loadInputFile("ecuc/ReferenceValue/validChoiceReferenceParamDef.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidReferenceValue_validReferenceParamDef() throws Exception {
		EObject validModel = loadInputFile("ecuc/ReferenceValue/validReferenceParamDef.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidReferenceValue_validForeignReferenceParamDef() throws Exception {
		EObject validModel = loadInputFile("ecuc/ReferenceValue/validForeignReferenceParamDef.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidReferenceValue_validForeignReferenceParamDefValueWithSuperTypes() throws Exception {
		EObject validModel = loadInputFile("ecuc/ReferenceValue/validForeignReferenceParamDefValueWithSuperTypes.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
