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

public class EnumerationValueConstraintTests extends AbstractAutosar40ValidationTestCase
{

	public EnumerationValueConstraintTests()
	{
		super();
	}
	@Override
	protected String getConstraintID()
	{
		return "org.artop.aal.autosar40.constraints.ecuc.EcucTextualParamValueBasicConstraint_40";//$NON-NLS-1$
	}
	// test correctness
	public void testInvalidEnumerationValue_valueNotDefinedInEnumerationLiterals() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/EnumerationValue/valueNotDefinedInEnumerationLiterals.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.enumeration_valueNotInLiterals);
	}

	// test valid
	public void testValidEnumerationValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/EnumerationValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
