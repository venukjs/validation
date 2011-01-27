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
package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucdescription.GConfigReferenceValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGConfigReferenceValueBasicConstraint;

import autosar21.ecucdescription.InstanceReferenceValue;
import autosar21.ecucdescription.ReferenceValue;

public class ConfigReferenceValueBasicConstraint extends AbstractGConfigReferenceValueBasicConstraint {

	@Override
	protected Object getValue(GConfigReferenceValue configReferenceValue) {
		if (configReferenceValue instanceof ReferenceValue) {
			return ((ReferenceValue) configReferenceValue).getValue();
		} else if (configReferenceValue instanceof InstanceReferenceValue) {
			return ((InstanceReferenceValue) configReferenceValue).getValue();
		}
		return null;
	}

}
