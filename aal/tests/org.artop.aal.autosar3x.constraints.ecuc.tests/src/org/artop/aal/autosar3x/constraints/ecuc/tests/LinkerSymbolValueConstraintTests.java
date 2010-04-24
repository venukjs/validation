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
public class LinkerSymbolValueConstraintTests extends ValidationTestCase {

	public LinkerSymbolValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.LinkerSymbolValueConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidLinkerSymbolValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidLinkerSymbolValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, "[ecuc sws 3034]");
	}

	// consistency
	public void testInvalidLinkerSymbolValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, "[ecuc sws 3041]");
	}

	// correctness
	public void testInvalidLinkerSymbolValue_emptyValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/emptyValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidLinkerSymbolValue_valueNoIdentifier() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/valueNoIdentifier.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidLinkerSymbolValue_valueTooLong() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/valueTooLong.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testValidLinkerSymbolValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/LinkerSymbolValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
