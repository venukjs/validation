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
public class EnumerationValueConstraintTest extends AbstractAutosar3xValidationTestCase {

	public EnumerationValueConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.EnumerationValueBasicConstraint_3x";//$NON-NLS-1$
	}

	// test completeness
	public void testInvalidEnumerationValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_definitionReferenceNotSet);
	}

	public void testInvalidEnumerationValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_valueNotSet);
	}

	// test consistency
	public void testInvalidEnumerationValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.generic_definitionNotOfType, "enumeration param def"));
	}

	// test correctness
	public void testInvalidEnumerationValue_valueNotDefinedInEnumerationLiterals() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/valueNotDefinedInEnumerationLiterals.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.enumeration_valueNotInLiterals);
	}

	// test valid
	public void testValidIntegerValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/EnumerationValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
