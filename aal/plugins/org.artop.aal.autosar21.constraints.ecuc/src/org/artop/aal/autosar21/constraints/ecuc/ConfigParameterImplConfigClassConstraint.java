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
package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGConfigParameterImplConfigClassConstraint;

import autosar21.ecucparameterdef.ConfigParameter;
import autosar21.ecucparameterdef.ConfigurationClass;

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
		ConfigurationClass configClass1 = paramdef1.getImplementationConfigClass();

		/*
		 * List of ImplConfigClass of the Parameter Definition 2
		 */
		ConfigurationClass configClass2 = paramdef2.getImplementationConfigClass();

		/*
		 * A warning is raised if list of ImplConfigClass of ParamDef1 is different with ParamDef2's
		 */
		if (configClass1 == null) {
			valid = configClass2 == null;
		} else {
			if (valid && configClass1 != null && configClass2 != null) {
				valid = configClass1.equals(configClass2);
			}
		}

		return valid;
	}

}
