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
public class InstanceReferenceValueTests extends ValidationTestCase {

	public InstanceReferenceValueTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.validation.ecuc.InstanceReferenceValueConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidInstanceReferenceValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidInstanceReferenceValue_noValueValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noValueValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidInstanceReferenceValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// consistency
	public void testInvalidInstanceReferenceValue_defTypeInvalid() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/defTypeInvalid.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// correctness
	public void testInvalidInstanceReferenceValue_noDestinationContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidInstanceReferenceValue_valueContextNoDestinationContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/valueContextNoDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidInstanceReferenceValue_destinationContextNoValueContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/destinationContextNoValueContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidInstanceReferenceValue_contextNotMatchWithDestinationContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/contextNotMatchWithDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidInstanceReferenceValue_noDestinationType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDestinationType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	public void testInvalidInstanceReferenceValue_valueNotMatchWithDestinationType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/valueNotMatchWithDestinationType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR);
	}

	// valid
	public void testValidStringValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/InstanceReferenceValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
