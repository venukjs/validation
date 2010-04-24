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
public class FunctionNameValueConstraintTests extends ValidationTestCase {

	public FunctionNameValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.FunctionNameValueConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidFunctionNameValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidFunctionNameValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// consistency
	public void testInvalidFunctionNameValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, "[ecuc sws 3005]");
	}

	// correctness
	public void testInvalidFunctionNameValue_emptyValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/emptyValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// should be reported by LinkerSymbolConstraint and not reported again
	public void testInvalidFunctionNameValue_valueNoIdentifier() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/valueNoIdentifier.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// should be reported by LinkerSymbolConstraint and not reported again
	public void testInvalidFunctionNameValue_valueTooLong() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/valueTooLong.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testValidFunctionNameValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/FunctionNameValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
