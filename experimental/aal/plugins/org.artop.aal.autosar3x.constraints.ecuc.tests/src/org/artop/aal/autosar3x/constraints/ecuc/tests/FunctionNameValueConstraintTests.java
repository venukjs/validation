/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy,  Continental Engineering Services  and others.
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

public class FunctionNameValueConstraintTests extends AbstractAutosar3xValidationTestCase
{

	public FunctionNameValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.FunctionNameValueBasicConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidFunctionNameValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.generic_definitionReferenceNotSet);
	}

	public void testInvalidFunctionNameValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.generic_valueNotSet);
	}

	// consistency
	public void testInvalidFunctionNameValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, NLS.bind(Messages.generic_definitionNotOfType,"function name param def"));
	}

	// correctness
	public void testInvalidFunctionNameValue_emptyValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/emptyValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.generic_valueNotSet);
	}

	// should be reported by LinkerSymbolConstraint and not reported again
	public void testInvalidFunctionNameValue_valueNoIdentifier() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/valueNoIdentifier.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.string_valueNoIdentifier);
	}

	// should be reported by LinkerSymbolConstraint and not reported again
	public void testInvalidFunctionNameValue_valueTooLong() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FunctionNameValue/valueTooLong.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.string_valueTooBig);
	}

	public void testValidFunctionNameValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/FunctionNameValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
