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
package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.autosar40.gautosar40.ecucdescription.GEcucNumericalParamValue40XAdapter;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGParameterValueBasicConstraint;

import autosar40.ecucdescription.EcucAddInfoParamValue;
import autosar40.ecucdescription.EcucNumericalParamValue;
import autosar40.ecucdescription.EcucTextualParamValue;

public class EcucParameterValueBasicConstraint extends AbstractGParameterValueBasicConstraint {

	@Override
	protected boolean isSetValue(GParameterValue parameterValue) {
		if (parameterValue instanceof EcucNumericalParamValue) {
			return new GEcucNumericalParamValue40XAdapter((EcucNumericalParamValue) parameterValue).getValue() != null;
		} else if (parameterValue instanceof EcucTextualParamValue) {
			return ((EcucTextualParamValue) parameterValue).isSetValue();
		} else if (parameterValue instanceof EcucAddInfoParamValue) {
			return ((EcucAddInfoParamValue) parameterValue).getValue() != null;
		}
		return true;
	}

}
