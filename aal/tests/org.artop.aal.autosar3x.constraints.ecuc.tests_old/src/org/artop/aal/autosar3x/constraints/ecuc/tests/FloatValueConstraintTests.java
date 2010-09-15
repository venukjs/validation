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
public class FloatValueConstraintTests extends ValidationTestCase {

	public FloatValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.FloatValueBasicConstraint_3x";
	}

	// test completeness
	public void testInvalidFloatValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidFloatValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidFloatValue_invalidValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/invalidValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test consistency
	public void testInvalidFloatValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test correctness
	public void testInvalidFloatValue_valueLowerThanMin() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/valueLowerThanMin.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidFloatValue_valueBiggerThanMax() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/valueBiggerThanMax.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test valid
	public void testValidIntegerValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
