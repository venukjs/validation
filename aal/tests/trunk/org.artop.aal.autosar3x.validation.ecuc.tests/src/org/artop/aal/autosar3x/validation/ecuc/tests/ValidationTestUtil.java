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
package org.artop.aal.autosar3x.validation.ecuc.tests;

import junit.framework.Assert;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.service.IValidator;

public class ValidationTestUtil {

	// private final static Logger logger = Logger.getLogger(ValidationTestUtil.class.getSimpleName());

	public static void validateModel(EObject model, IValidator<EObject> validator, int severity) {
		IStatus status = validator.validate(model);
		Assert.assertEquals("inconsistence between expected and returned status: ", severity, status.getSeverity());//$NON-NLS-1$
	}

	public static void validateModel(EObject model, IValidator<EObject> validator, int severity, String expectedMessage) {
		IStatus status;

		status = validator.validate(model);
		Assert.assertEquals("inconsistence between expected and returned status: ", severity, status.getSeverity());//$NON-NLS-1$

		if (IStatus.ERROR == status.getSeverity()) {
			Assert.assertTrue("expected error message '" + expectedMessage + "' not found. ", findErrorMessage(expectedMessage, status));//$NON-NLS-1$//$NON-NLS-2$
		}
	}

	private static boolean findErrorMessage(String errorMessage, IStatus status) {
		assert null != errorMessage;
		assert null != status;

		boolean errorMessageFound = false;

		// we are only interested in error messages
		if (IStatus.ERROR == status.getSeverity()) {
			// try to find the expected message in IStatus directly
			if (errorMessage.contains(status.getMessage())) {
				errorMessageFound = true;
			}

			// find error message in children
			for (IStatus substatus : status.getChildren()) {
				if (false == errorMessageFound) {
					errorMessageFound = findErrorMessage(errorMessage, substatus);
				} else {
					break; // leave the loop. We have found the error message
				}
			}
		}

		return errorMessageFound;
	}
}