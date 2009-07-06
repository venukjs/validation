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
package org.artop.ecl.emf.validation.listeners;

import org.artop.ecl.emf.validation.eobject.adapter.EObjectValidationDataCacheAdapterFactory;
import org.eclipse.emf.validation.service.IValidationListener;
import org.eclipse.emf.validation.service.ValidationEvent;

/**
 * A listener listening for {@link org.eclipse.emf.validation.service.ValidationEvent validation events} in order to
 * clean an {@link org.artop.ecl.emf.validation.eobject.adapter.EObjectValidationDataCacheAdapter
 * EObjectValidationDataCacheAdapter}.
 */
public class CachedDataCleaner implements IValidationListener {

	public void validationOccurred(ValidationEvent event) {
		EObjectValidationDataCacheAdapterFactory.initVAdapters();
	}
}
