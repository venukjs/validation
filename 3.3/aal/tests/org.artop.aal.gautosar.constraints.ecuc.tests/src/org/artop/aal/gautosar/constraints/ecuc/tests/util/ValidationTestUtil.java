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
package org.artop.aal.gautosar.constraints.ecuc.tests.util;

import junit.framework.Assert;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.service.IValidator;

/**
 * 
 * Utilities for the ecuc validation constraints tests.
 * 
 */
public class ValidationTestUtil
{

	/**
	 * Validates the given <code>model</code> with the given
	 * <code>validator</code>, expecting that the severity of the result status
	 * is equal to the given <code>severity</code>.
	 * 
	 * @param model the model to validate
	 * @param validator the validator to use in validating model elements
	 * @param severity the expected severity
	 */
	public static void validateModel(EObject model,
			IValidator<EObject> validator, int severity)
	{
		IStatus status = validator.validate(model);
		Assert.assertEquals(
				"Inconsistence between expected and returned status: ", severity, status.getSeverity());//$NON-NLS-1$
	}

	/**
	 * Validates the given <code>model</code> with the given
	 * <code>validator</code>, expecting that the severity of the result status
	 * is equal to the given <code>severity</code> and the message is the same with <code>expectedMessage</code>.
	 * 
	 * @param model the model to validate
	 * @param validator the validator to use in validating model elements
	 * @param severity the expected severity
	 * @param expectedMessage the expected message from the result status
	 */
	public static void validateModel(EObject model,
			IValidator<EObject> validator, int severity, String expectedMessage)
	{
		IStatus status;

		status = validator.validate(model);
		Assert.assertEquals(
				"Inconsistence between expected and returned status: ", severity, status.getSeverity());//$NON-NLS-1$

		if (IStatus.ERROR == status.getSeverity())
		{
			Assert.assertTrue(
					"Expected error message '" + expectedMessage + "' not found. ", findErrorMessage(expectedMessage, status));//$NON-NLS-1$//$NON-NLS-2$
		}
	}

	private static boolean findErrorMessage(String expectedErrorMessage,
			IStatus status)
	{
		assert null != expectedErrorMessage;
		assert null != status;

		boolean errorMessageFound = false;

		// we are only interested in error messages
		if (IStatus.ERROR == status.getSeverity())
		{
			// try to find the expected message in IStatus directly
			String statusErrorMessage = status.getMessage();
			if (null != statusErrorMessage
					&& -1 < statusErrorMessage.indexOf(expectedErrorMessage))
			{
				errorMessageFound = true;
			} else
			{

				// find error message in children
				for (IStatus substatus : status.getChildren())
				{
					errorMessageFound = findErrorMessage(expectedErrorMessage,
							substatus);
					if (true == errorMessageFound)
					{
						break; // leave the loop. We have found the error
								// message
					}
				}
			}
		}

		return errorMessageFound;
	}
}
