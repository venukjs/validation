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
package org.artop.ecl.emf.validation.markers;

import java.util.EventObject;

/**
 * Can be used to get notified about validation problem marker changes.
 */
public interface IValidationProblemMarkersChangeListener {

	/**
	 * Called when validation problem markers have changed.
	 */
	void validationProblemMarkersChanged(EventObject event);
}
