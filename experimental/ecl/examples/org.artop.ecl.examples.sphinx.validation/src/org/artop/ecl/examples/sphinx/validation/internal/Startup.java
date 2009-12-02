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
package org.artop.ecl.examples.sphinx.validation.internal;

import org.artop.ecl.emf.validation.evalidator.adapter.EValidatorAdapter;
import org.artop.ecl.examples.sphinx.SphinxPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.ui.IStartup;

public class Startup implements IStartup {

	public void earlyStartup() {
		// We register the EValidatorAdapter defined in org.artop.ecl.emf.validation.evalidator.adapter
		// to associate it with Sphinx meta-model.
		// The EValidatorAdapter is necessary for retrieving user defined constraints and applying them.
		EValidator.Registry.INSTANCE.put(SphinxPackage.eINSTANCE, new EValidatorAdapter());
	}

}
