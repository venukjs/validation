/**
 * <copyright>
 * 
 * Copyright (c) See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGConfigParameterImplConfigClassConstraint;
import org.eclipse.emf.common.util.EList;

import autosar3x.ecucparameterdef.ConfigParameter;
import autosar3x.ecucparameterdef.ConfigurationClass;
import autosar3x.ecucparameterdef.ConfigurationVariant;
import autosar3x.ecucparameterdef.ImplementationConfigClass;

/**
 * 
 */
public class ConfigParameterImplConfigClassConstraint extends AbstractGConfigParameterImplConfigClassConstraint {

	@Override
	protected boolean compareImplConfigurationClass(GConfigParameter cfParam1, GConfigParameter cfParam2) {
		boolean valid = true;
		ConfigParameter paramdef1 = (ConfigParameter) cfParam1;
		ConfigParameter paramdef2 = (ConfigParameter) cfParam2;

		/*
		 * List of ImplConfigClass of the Parameter Definition 1
		 */
		EList<ImplementationConfigClass> configClassList1 = paramdef1.getImplementationConfigClass();

		/*
		 * List of ImplConfigClass of the Parameter Definition 2
		 */
		EList<ImplementationConfigClass> configClassList2 = paramdef2.getImplementationConfigClass();

		/*
		 * A warning is raised if list of ImplConfigClass of ParamDef1 is different with ParamDef2's
		 */
		if (configClassList1 == null) {
			valid = configClassList2 == null;
		} else {
			valid = configClassList1.size() == configClassList2.size();
			if (valid) {
				valid = compareEcucImplementationConfigClass(configClassList1, configClassList2);
			}
		}

		return valid;
	}

	private boolean compareEcucImplementationConfigClass(EList<ImplementationConfigClass> configClassList1,
			EList<ImplementationConfigClass> configClassList2) {
		boolean elementIsValid = false;
		for (int i = 0; i < configClassList1.size(); i++) {
			ImplementationConfigClass cfClass1 = configClassList1.get(i);
			ImplementationConfigClass cfClass2 = configClassList2.get(i);
			if (cfClass1 == null) {
				elementIsValid = cfClass2 == null;
			} else {
				ConfigurationClass configClass1 = cfClass1.getConfigClass();
				ConfigurationClass configClass2 = cfClass2.getConfigClass();
				if (configClass1 != null && configClass2 != null) {
					elementIsValid = configClass1.equals(configClass2);
					if (elementIsValid) {
						ConfigurationVariant configVariant1 = cfClass1.getConfigVariant();
						ConfigurationVariant configVariant2 = cfClass1.getConfigVariant();
						if (configVariant1 != null && configVariant2 != null) {
							elementIsValid = configVariant1.equals(configVariant2);
						}
					}
				}

			}
			if (elementIsValid == false) {
				break;
			}
		}
		return elementIsValid;
	}
}
