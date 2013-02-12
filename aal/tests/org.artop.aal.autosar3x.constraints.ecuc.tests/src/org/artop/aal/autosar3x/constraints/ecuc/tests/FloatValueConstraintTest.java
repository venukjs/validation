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
public class FloatValueConstraintTest extends AbstractAutosar3xValidationTestCase {

	public FloatValueConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.FloatValueBasicConstraint_3x";//$NON-NLS-1$
	}

	// test completeness
	public void testInvalidFloatValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_definitionReferenceNotSet);
	}

	public void testInvalidFloatValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_valueNotSet);
	}

	public void testInvalidFloatValue_invalidValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/invalidValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_valueNotSet);
	}

	// test consistency
	public void testInvalidFloatValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.generic_definitionNotOfType, "float param def"));
	}

	// test correctness
	public void testInvalidFloatValue_valueLowerThanMin() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/valueLowerThanMin.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.boundary_valueUnderMin);
	}

	public void testInvalidFloatValue_valueBiggerThanMax() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/valueBiggerThanMax.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.boundary_valueAboveMax);
	}

	// test valid
	public void testValidIntegerValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
