/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services and others.
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

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("nls")
public class LinkerSymbolValueConstraintTest extends AbstractAutosar3xValidationTestCase {
	public LinkerSymbolValueConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.LinkerSymbolValueBasicConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidLinkerSymbolValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_definitionReferenceNotSet);
	}

	public void testInvalidLinkerSymbolValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_valueNotSet);
	}

	// consistency
	public void testInvalidLinkerSymbolValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.generic_definitionNotOfType, "linker symbol param def"));
	}

	// correctness
	public void testInvalidLinkerSymbolValue_emptyValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/emptyValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_valueNotSet);
	}

	public void testInvalidLinkerSymbolValue_valueNoIdentifier() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/valueNoIdentifier.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.string_valueNoIdentifier);
	}

	public void testInvalidLinkerSymbolValue_valueTooLong() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/LinkerSymbolValue/valueTooLong.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.string_valueTooBig);
	}

	public void testValidLinkerSymbolValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/LinkerSymbolValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
