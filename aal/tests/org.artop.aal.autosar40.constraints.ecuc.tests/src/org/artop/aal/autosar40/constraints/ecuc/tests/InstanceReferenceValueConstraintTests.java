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

import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

public class InstanceReferenceValueConstraintTests extends AbstractAutosar40ValidationTestCase
{

	public InstanceReferenceValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.InstanceReferenceValueBasicConstraint_40";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidInstanceReferenceValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.generic_valueNotSet);
	}
	
	// correctness
	public void testInvalidInstanceReferenceValue_noDestinationContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.instanceref_valueDestContextNotSet);
	}

	public void testInvalidInstanceReferenceValue_noDestinationType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDestinationType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.reference_targetDestinationTypeNotAvailable);
	}
}
