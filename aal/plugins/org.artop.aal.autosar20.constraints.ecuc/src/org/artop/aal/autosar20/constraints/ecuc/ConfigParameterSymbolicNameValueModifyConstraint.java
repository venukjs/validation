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
package org.artop.aal.autosar20.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGConfigParameterSymbolicNameValueModifyConstraint;

import autosar20.ecucparameterdef.ConfigParameter;

public class ConfigParameterSymbolicNameValueModifyConstraint extends AbstractGConfigParameterSymbolicNameValueModifyConstraint {

	@Override
	protected boolean isSetSymbolicNameValue(GConfigParameter configParameter) {
		if (configParameter instanceof ConfigParameter) {
			return ((ConfigParameter) configParameter).isSetSymbolicNameValue();
		}
		return false;
	}

}
