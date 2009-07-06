/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on Released
 * AUTOSAR Material (ASLR) which accompanies this distribution, and is available
 * at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.emf.validation.eobject.adapter;

public interface IEObjectValidationDataCache {

	/**
	 * Checks if the severity value stored into the adapter is up-to-date.
	 * 
	 * @return <tt>true</tt> if the stored serverity value is up-to-dat, otherwise <tt>false</tt>.
	 */
	public boolean isSeverityOk();

	/**
	 * Returns the severity value stored for the adapted object.
	 * 
	 * @return The stored severity value.
	 * @see org.artop.ecl.emf.validation.markers.ValidationStatusCode
	 */
	public int getSeverity();

}
