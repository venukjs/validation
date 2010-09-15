/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy,  Continental Engineering Services  and others.
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
package org.artop.aal.autosar3x.constraints.ecuc.tests;

import org.artop.aal.autosar3x.constraints.ecuc.tests.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.tests.AbstractValidationTestCase;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource.Factory;

import autosar3x.util.Autosar3xPackage;
import autosar3x.util.Autosar3xResourceFactoryImpl;

public abstract class AbstractAutosar3xValidationTestCase extends AbstractValidationTestCase
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
		return Autosar3xPackage.eINSTANCE.getNsURI();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.artop.aal.constraints.ecuc.tests.AbstractValidationTestCase#getAutosarPackageInstance()
	 */
	@Override
	protected EPackage getAutosarPackageInstance()
	{
		return Autosar3xPackage.eINSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * @see org.artop.aal.constraints.ecuc.tests.AbstractValidationTestCase#getAutosarResourceFactory()
	 */
	@Override
	protected Factory getAutosarResourceFactory()
	{
		return new Autosar3xResourceFactoryImpl();
	}
}
