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
public class EnumerationValueConstraintTests extends ValidationTestCase {

	public EnumerationValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.validation.ecuc.EnumerationValueConstraint_3x";//$NON-NLS-1$
	}

	// test completeness
	public void testInvalidEnumerationValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidEnumerationValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test consistency
	public void testInvalidEnumerationValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test correctness
	public void testInvalidEnumerationValue_valueNotDefinedInEnumerationLiterals() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/valueNotDefinedInEnumerationLiterals.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// test valid
	public void testValidIntegerValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/EnumerationValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
