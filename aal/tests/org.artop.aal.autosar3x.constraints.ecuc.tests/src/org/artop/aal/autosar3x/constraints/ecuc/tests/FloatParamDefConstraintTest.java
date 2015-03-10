/**
 * <copyright>
 *
 * Copyright (c) Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 *
 * Contributors:
 *     Continental Engineering Services - Initial API and implementation
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
public class FloatParamDefConstraintTest extends AbstractAutosar3xValidationTestCase {

	public FloatParamDefConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.FloatParamDefDefaultValueConstraint_3x";//$NON-NLS-1$
	}

	public void testValidFloatParamDef_noDefValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatParamDef/defValueNotSet.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testInvalidFloatParamDef_invalidDefValueOutOfRange() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatParamDef/defValueOutOfRange.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.floatParamDef_defaultValueIsOutOfRange, new Object[] { /*
																										 * "/ARRoot/ModuleDef/Container/FloatDef"
																										 * ,
																										 */"5.0", "10.0", "15.0" }));
	}

	public void testInvalidFloatParamDef_invalidDefValueOutOfRangeMinUnset() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatParamDef/defValueOutOfRangeMinUnset.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.floatParamDef_defaultValueIsOutOfRange, new Object[] {/*
																									 * "/ARRoot/ModuleDef/Container/FloatDef"
																									 * ,
																									 */
				"10.0", "~", "5.0" }));
	}

	public void testInvalidFloatParamDef_invalidDefValueOutOfRangeMaxUnset() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatParamDef/defValueOutOfRangeMaxUnset.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.floatParamDef_defaultValueIsOutOfRange, new Object[] {/*
																									 * "/ARRoot/ModuleDef/Container/FloatDef"
																									 * ,
																									 */"1.0", "5.0", "~" }));
	}

	// valid
	public void testValidFloatParamDef_validDefValueInRangeUnset() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatParamDef/defValueInRangeUnset.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidFloatParamDef_validDefValueInRange() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatParamDef/defValueInRange.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidFloatParamDef_validDefValueInRangeMinUnset() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatParamDef/defValueInRangeMinUnset.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidFloatParamDef_validDefValueInRangeMaxUnset() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatParamDef/defValueInRangeMaxUnset.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
