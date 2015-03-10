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
package org.artop.aal.autosar21.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("nls")
public class IntegerParamDefConstraintTest extends AbstractAutosar21ValidationTestCase {

	public IntegerParamDefConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar21.constraints.ecuc.IntegerParamDefDefaultValueConstraint_21";//$NON-NLS-1$
	}

	public void testValidIntegerParamDef_noDefValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/IntegerParamDef/defValueNotSet.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testInvalidIntegerParamDef_invalidDefValueOutOfRange() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerParamDef/defValueOutOfRange.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.integerParamDef_defaultValueIsOutOfRange, new Object[] { /*
																										 * "/ARRoot/ModuleDef/Container/IntegerDef"
																										 * ,
																										 */
				"5", "10", "15" }));
	}

	public void testInvalidIntegerParamDef_invalidDefValueOutOfRangeMinUnset() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerParamDef/defValueOutOfRangeMinUnset.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.integerParamDef_defaultValueIsOutOfRange, new Object[] {/*
																										 * "/ARRoot/ModuleDef/Container/IntegerDef"
																										 * ,
																										 */
				"10", "-9223372036854775808", "5" }));
	}

	public void testInvalidIntegerParamDef_invalidDefValueOutOfRangeMaxUnset() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerParamDef/defValueOutOfRangeMaxUnset.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.integerParamDef_defaultValueIsOutOfRange, new Object[] {/*
																										 * "/ARRoot/ModuleDef/Container/IntegerDef"
																										 * ,
																										 */
				"1", "5", "18446744073709551615" }));
	}

	// valid
	public void testValidIntegerParamDef_validDefValueInRangeUnset() throws Exception {
		EObject validModel = loadInputFile("ecuc/IntegerParamDef/defValueInRangeUnset.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidIntegerParamDef_validDefValueInRange() throws Exception {
		EObject validModel = loadInputFile("ecuc/IntegerParamDef/defValueInRange.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidIntegerParamDef_validDefValueInRangeMinUnset() throws Exception {
		EObject validModel = loadInputFile("ecuc/IntegerParamDef/defValueInRangeMinUnset.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidIntegerParamDef_validDefValueInRangeMaxUnset() throws Exception {
		EObject validModel = loadInputFile("ecuc/IntegerParamDef/defValueInRangeMaxUnset.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
