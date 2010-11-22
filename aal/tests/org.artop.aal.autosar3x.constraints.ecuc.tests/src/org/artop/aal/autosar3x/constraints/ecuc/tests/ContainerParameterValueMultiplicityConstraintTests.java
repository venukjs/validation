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
public class ContainerParameterValueMultiplicityConstraintTests extends AbstractAutosar3xValidationTestCase {
	public ContainerParameterValueMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ContainerParameterValueMultiplicityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidContainer_mandatoryParameterValueMissing() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/mandatoryParameterValueMissing.arxml");
		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.ERROR,
				NLS.bind(Messages.multiplicity_minElementsExpected, new String[] { "1", "parameter values", "/AUTOSAR/Os/OsAlarm/BooleanParameter",
						"0" }));
	}

	public void testInvalidContainer_parameterValueLowerMultiplicityViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/parameterValueLowerMultiplicityViolated.arxml");
		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.ERROR,
				NLS.bind(Messages.multiplicity_minElementsExpected, new String[] { "2", "parameter values", "/AUTOSAR/Os/OsAlarm/BooleanParameter",
						"1" }));
	}

	public void testInvalidContainer_parameterValueUpperMultiplicityViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/parameterValueUpperMultiplicityViolated.arxml");
		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.ERROR,
				NLS.bind(Messages.multiplicity_maxElementsExpected, new String[] { "1", "parameter values", "/AUTOSAR/Os/OsAlarm/BooleanParameter",
						"2" }));
	}

	// valid
	public void testValidContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
