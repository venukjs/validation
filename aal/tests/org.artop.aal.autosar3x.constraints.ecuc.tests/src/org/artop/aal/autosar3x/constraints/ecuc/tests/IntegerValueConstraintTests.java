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
public class IntegerValueConstraintTests extends ValidationTestCase {

	public IntegerValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.validation.ecuc.IntegerValueConstraint_3x";//$NON-NLS-1$
	}

	// test completeness
	public void testInvalidIntegerValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidIntegerValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidIntegerValue_invalidValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/invalidValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test consistency
	public void testInvalidIntegerValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test correctness
	public void testInvalidIntegerValue_valueLowerThanMin() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/valueLowerThanMin.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidIntegerValue_valueBiggerThanMax() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/valueBiggerThanMax.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testValidIntegerValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/IntegerValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
