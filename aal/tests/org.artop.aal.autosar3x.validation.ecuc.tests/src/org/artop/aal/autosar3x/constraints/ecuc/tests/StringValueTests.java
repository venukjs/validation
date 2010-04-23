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
public class StringValueTests extends ValidationTestCase {

	public StringValueTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.validation.ecuc.StringValueConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidStringValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/StringValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidStringValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/StringValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// consistency
	public void testInvalidStringValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/StringValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// correctness
	public void testInvalidStringValue_emptyValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/StringValue/emptyValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidStringValue_valueNoIdentifier() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/StringValue/valueNoIdentifier.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidStringValue_valueTooLong() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/StringValue/valueTooLong.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testValidStringValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/StringValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
