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

import org.artop.aal.autosar21.constraints.ecuc.tests.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.tests.AbstractValidationTestCase;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource.Factory;

import autosar21.util.Autosar21Package;
import autosar21.util.Autosar21ResourceFactoryImpl;



public abstract class AbstractAutosar21ValidationTestCase extends AbstractValidationTestCase
{

	@Override
	protected Plugin getPlugin()
	{
		return Activator.getPlugin();
	}
	/*
	 * (non-Javadoc)
	 * @see org.artop.aal.constraints.ecuc.tests.AbstractValidationTestCase#getAutosarPackageNsURi()
	 */
	@Override
	protected String getAutosarPackageNsURi()
	{
		return Autosar21Package.eINSTANCE.getNsURI();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.artop.aal.constraints.ecuc.tests.AbstractValidationTestCase#getAutosarPackageInstance()
	 */
	@Override
	protected EPackage getAutosarPackageInstance()
	{
		return Autosar21Package.eINSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.artop.aal.constraints.ecuc.tests.AbstractValidationTestCase#getAutosarResourceFactory()
	 */
	@Override
	protected Factory getAutosarResourceFactory()
	{
		return new Autosar21ResourceFactoryImpl();
	}
}
