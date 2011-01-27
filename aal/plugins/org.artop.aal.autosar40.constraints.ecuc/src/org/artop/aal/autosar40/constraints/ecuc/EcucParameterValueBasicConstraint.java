/**
 * <copyright>
 * 
 * Copyright (c) see4Sys and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Artop Software License 
 * Based on Released AUTOSAR Material (ASLR) which accompanies this 
 * distribution, and is available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     see4Sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGParameterValueBasicConstraint;

import autosar40.ecucdescription.EcucAddInfoParamValue;
import autosar40.ecucdescription.EcucNumericalParamValue;
import autosar40.ecucdescription.EcucTextualParamValue;

public class EcucParameterValueBasicConstraint extends AbstractGParameterValueBasicConstraint {

	@Override
	protected boolean isSetValue(GParameterValue parameterValue) {
		if (parameterValue instanceof EcucNumericalParamValue) {
			return ((EcucNumericalParamValue) parameterValue).getValue() != null;
		} else if (parameterValue instanceof EcucTextualParamValue) {
			return ((EcucTextualParamValue) parameterValue).isSetValue();
		} else if (parameterValue instanceof EcucAddInfoParamValue) {
			return ((EcucAddInfoParamValue) parameterValue).getValue() != null;
		}
		return true;
	}

}
