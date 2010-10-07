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
public class ContainerReferenceValueMultiplicityConstraintTests extends ValidationTestCase {

	public ContainerReferenceValueMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ContainerReferenceValueMultiplicityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidContainer_mandatoryReferenceValueMissing() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/mandatoryReferenceValueMissing.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidContainer_referenceValueLowerMultiplicityViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/referenceValueLowerMultiplicityViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidContainer_referenceValueUpperMultiplicityViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/referenceValueUpperMultiplicityViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// valid
	public void testValidContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
