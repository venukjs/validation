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

import gautosar.gecucdescription.GConfigReferenceValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGConfigReferenceValueBasicConstraint;

import autosar40.ecucdescription.EcucInstanceReferenceValue;
import autosar40.ecucdescription.EcucReferenceValue;

public class EcucAbstractReferenceValueBasicConstraint extends AbstractGConfigReferenceValueBasicConstraint {

	@Override
	protected Object getValue(GConfigReferenceValue configReferenceValue) {
		if (configReferenceValue instanceof EcucReferenceValue) {
			return ((EcucReferenceValue) configReferenceValue).getValue();
		} else if (configReferenceValue instanceof EcucInstanceReferenceValue) {
			return ((EcucInstanceReferenceValue) configReferenceValue).getValue();
		}
		return null;
	}

}
