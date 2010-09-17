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
package org.artop.aal.autosar20.constraints.ecuc.tests;

import org.artop.aal.autosar20.constraints.ecuc.tests.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.tests.AbstractValidationTestCase;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource.Factory;

import autosar20.util.Autosar20Package;
import autosar20.util.Autosar20ResourceFactoryImpl;



public abstract class AbstractAutosar20ValidationTestCase extends AbstractValidationTestCase
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
		return Autosar20Package.eINSTANCE.getNsURI();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.artop.aal.constraints.ecuc.tests.AbstractValidationTestCase#getAutosarPackageInstance()
	 */
	@Override
	protected EPackage getAutosarPackageInstance()
	{
		return Autosar20Package.eINSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.artop.aal.constraints.ecuc.tests.AbstractValidationTestCase#getAutosarResourceFactory()
	 */
	@Override
	protected Factory getAutosarResourceFactory()
	{
		return new Autosar20ResourceFactoryImpl();
	}
}
