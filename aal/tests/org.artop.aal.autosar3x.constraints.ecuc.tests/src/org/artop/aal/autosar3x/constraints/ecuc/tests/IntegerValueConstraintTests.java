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

import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("nls")
public class IntegerValueConstraintTests extends AbstractAutosar3xValidationTestCase {

	public IntegerValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.IntegerValueBasicConstraint_3x";//$NON-NLS-1$
	}

	// test completeness
	public void testInvalidIntegerValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.generic_definitionReferenceNotSet);
	}

	public void testInvalidIntegerValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.generic_valueNotSet);
	}

	public void testInvalidIntegerValue_invalidValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/invalidValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.generic_valueNotSet);
	}

	// test consistency
	public void testInvalidIntegerValue_wrongParamDefType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/wrongParamDefType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, NLS.bind(Messages.generic_definitionNotOfType, "integer param def"));
	}

	public void testInvalidIntegerValue_notContainedInDefinitionOfParentContainer() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/notContainedInDefinitionOfParentContainer.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(Messages.structuralIntegrity_containmentProblem, "parameter value", "SomeIntegerDef"));
	}

	public void testInvalidIntegerValue_notAllowedInChoiceContainers() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/notAllowedInChoiceContainers.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(Messages.structuralIntegrity_NotAllowedInChoiceContainer, "parameter value"));
	}

	// test correctness
	public void testInvalidIntegerValue_valueLowerThanMin() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/valueLowerThanMin.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.boundary_valueUnderMin);
	}

	public void testInvalidIntegerValue_valueBiggerThanMax() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/IntegerValue/valueBiggerThanMax.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, Messages.boundary_valueAboveMax);
	}

	public void testValidIntegerValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/IntegerValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
