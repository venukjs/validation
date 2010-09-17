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

import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

public class BooleanValueConstraintTests extends AbstractAutosar21ValidationTestCase
{

	public BooleanValueConstraintTests()
	{
		super();
	}
	
	@Override
	protected String getConstraintID()
	{
		return "org.artop.aal.autosar21.constraints.ecuc.BooleanValueBasicConstraint_21";//$NON-NLS-1$

	}
	public void testInvalidBooleanValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/BooleanValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,Messages.generic_valueNotSet);
	}

}
