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
public class ContainerParameterValueMultiplicityConstraintTests extends ValidationTestCase {

	public ContainerParameterValueMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ContainerParameterValueMultiplicityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidContainer_mandatoryParameterValueMissing() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/mandatoryParameterValueMissing.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidContainer_parameterValueLowerMultiplicityViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/parameterValueLowerMultiplicityViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidContainer_parameterValueUpperMultiplicityViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/parameterValueUpperMultiplicityViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// valid
	public void testValidContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
