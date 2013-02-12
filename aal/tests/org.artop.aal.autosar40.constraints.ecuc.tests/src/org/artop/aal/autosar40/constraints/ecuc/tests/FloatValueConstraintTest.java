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
package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

@SuppressWarnings("nls")
public class FloatValueConstraintTest extends AbstractAutosar40ValidationTestCase {

	public FloatValueConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.EcucNumericalParamValueBasicConstraint_40";//$NON-NLS-1$
	}

	// test completeness
	public void testInvalidFloatValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/FloatValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_valueNotSet);
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
	public void testValidFloatValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
