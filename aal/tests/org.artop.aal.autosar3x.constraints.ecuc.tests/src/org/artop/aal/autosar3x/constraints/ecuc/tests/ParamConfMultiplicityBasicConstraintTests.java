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
public class ParamConfMultiplicityBasicConstraintTests extends AbstractAutosar3xValidationTestCase {
	public ParamConfMultiplicityBasicConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ParamConfMultiplicityBasicConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidParamConfMultiplicity_upperMultiplicityNotANumber() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ParamConfMultiplicity/upperMultiplicityNotANumber.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.multiplicity_upperMultException, "For input string: \"UNLIMITED\""));
	}

	public void testInvalidParamConfMultiplicity_upperMultiplicityNegativeNumber() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ParamConfMultiplicity/upperMultiplicityNegativeNumber.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.multiplicity_upperMultNegative);
	}

	public void testInvalidParamConfMultiplicity_lowerMultiplicityNotANumber() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ParamConfMultiplicity/lowerMultiplicityNotANumber.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.multiplicity_lowerMultException, "For input string: \"*\""));
	}

	public void testInvalidParamConfMultiplicity_lowerMultiplicityNegativeNumber() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ParamConfMultiplicity/lowerMultiplicityNegativeNumber.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.multiplicity_lowerMultNegative);
	}

	// valid
	public void testValidParamConfMultiplicity() throws Exception {
		EObject validModel = loadInputFile("ecuc/ParamConfMultiplicity/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidParamConfMultiplicity_upperMultiplicityUnlimited() throws Exception {
		EObject validModel = loadInputFile("ecuc/ParamConfMultiplicity/valid_upperMultiplicityUnlimited.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

	public void testValidParamConfMultiplicity_multiplicitiesUnset() throws Exception {
		EObject validModel = loadInputFile("ecuc/ParamConfMultiplicity/valid_multiplicitiesUnset.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
