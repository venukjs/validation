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
package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.autosar40.gautosar40.ecucparameterdef.GEcucDefinitionElement40XAdapter;
import org.artop.aal.autosar40.tests.AbstractAutosar40TestCase;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.emf.common.util.EList;

import autosar40.autosartoplevelstructure.AUTOSAR;
import autosar40.ecucparameterdef.EcucModuleDef;
import autosar40.ecucparameterdef.EcucParamConfContainerDef;
import autosar40.ecucparameterdef.EcucparameterdefFactory;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import autosar40.genericstructure.generaltemplateclasses.arpackage.PackageableElement;
import autosar40.util.Autosar40Factory;

public class EcucUtilTest extends AbstractAutosar40TestCase {

	private static final String TEST_FILE_NAME = "Module.arxml"; //$NON-NLS-1$

	/**
	 * Tests following utility methods from {@link EcucUtil}: <li>getLowerMultiplicity()</li> <li>getUpperMultiplicity()
	 * </li> <li>isValidLowerMultiplicity()</li> <li>isValidUpperMultiplicity()</li>
	 */
	public void testMultiplicity() {

		AUTOSAR autosar = Autosar40Factory.eINSTANCE.createAUTOSAR();

		ARPackage arPackage = Autosar40Factory.eINSTANCE.createARPackage();
		arPackage.setShortName("ARroot"); //$NON-NLS-1$
		autosar.getArPackages().add(arPackage);

		EcucModuleDef moduleDef = EcucparameterdefFactory.eINSTANCE.createEcucModuleDef();
		moduleDef.setShortName("VendorMD"); //$NON-NLS-1$

		new GEcucDefinitionElement40XAdapter(moduleDef).setUpperMultiplicityValue("1"); //$NON-NLS-1$
		arPackage.getElements().add(moduleDef);

		EcucParamConfContainerDef containerDef1 = createEcucParamConfContainerDef("EcucParamConfContainerDef1", "1", "1", true); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		EcucParamConfContainerDef containerDef2 = createEcucParamConfContainerDef("EcucParamConfContainerDef2", "2", "3", null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		EcucParamConfContainerDef containerDef3 = createEcucParamConfContainerDef("EcucParamConfContainerDef3", "1", "1", false); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$		

		moduleDef.getContainers().add(containerDef1);
		moduleDef.getContainers().add(containerDef2);
		moduleDef.getContainers().add(containerDef3);

		try {
			// save
			saveWorkingFile(TEST_FILE_NAME, autosar);
			autosar = (AUTOSAR) loadWorkingFile(TEST_FILE_NAME);
			assertEquals(1, autosar.getArPackages().size());

			arPackage = autosar.getArPackages().get(0);
			EList<PackageableElement> elements = arPackage.getElements();
			assertEquals(1, elements.size());

			EcucModuleDef module = (EcucModuleDef) elements.get(0);
			assertEquals(3, module.getContainers().size());

			EcucParamConfContainerDef containerDef = (EcucParamConfContainerDef) module.getContainers().get(0);
			assertEquals("1", EcucUtil.getLowerMultiplicity(containerDef)); //$NON-NLS-1$
			assertEquals("*", EcucUtil.getUpperMultiplicity(containerDef)); //$NON-NLS-1$
			assertFalse(EcucUtil.isValidLowerMultiplicity(0, containerDef));
			assertTrue(EcucUtil.isValidLowerMultiplicity(2, containerDef));

			containerDef = (EcucParamConfContainerDef) module.getContainers().get(1);
			assertEquals("2", EcucUtil.getLowerMultiplicity(containerDef)); //$NON-NLS-1$
			assertEquals("3", EcucUtil.getUpperMultiplicity(containerDef)); //$NON-NLS-1$
			assertFalse(EcucUtil.isValidLowerMultiplicity(1, containerDef));
			assertTrue(EcucUtil.isValidLowerMultiplicity(2, containerDef));
			assertFalse(EcucUtil.isValidUpperMultiplicity(4, containerDef));

			containerDef = (EcucParamConfContainerDef) module.getContainers().get(2);
			assertEquals("1", EcucUtil.getLowerMultiplicity(containerDef)); //$NON-NLS-1$
			assertEquals("1", EcucUtil.getUpperMultiplicity(containerDef)); //$NON-NLS-1$
			assertTrue(EcucUtil.isValidLowerMultiplicity(1, containerDef));
			assertFalse(EcucUtil.isValidLowerMultiplicity(0, containerDef));
			assertTrue(EcucUtil.isValidUpperMultiplicity(1, containerDef));
			assertFalse(EcucUtil.isValidUpperMultiplicity(2, containerDef));

		} catch (Exception ex) {
			fail(ex.getMessage());
		}

	}

	private EcucParamConfContainerDef createEcucParamConfContainerDef(String shortName, String lower, String upper, Boolean upperInfinite) {

		EcucParamConfContainerDef containerDef1 = EcucparameterdefFactory.eINSTANCE.createEcucParamConfContainerDef();

		containerDef1.setShortName(shortName);

		GEcucDefinitionElement40XAdapter cDef = new GEcucDefinitionElement40XAdapter(containerDef1);
		cDef.setLowerMultiplicityValue(lower);

		cDef.setUpperMultiplicityValue(upper);

		if (upperInfinite != null) {
			cDef.setUpperMultiplicityInfiniteValue(upperInfinite.toString());
		}

		return containerDef1;

	}
}
