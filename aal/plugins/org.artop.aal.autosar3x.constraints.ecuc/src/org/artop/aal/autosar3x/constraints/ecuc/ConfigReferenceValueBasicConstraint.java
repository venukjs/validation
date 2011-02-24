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

import gautosar.gecucdescription.GConfigReferenceValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGConfigReferenceValueBasicConstraint;

import autosar3x.ecucdescription.InstanceReferenceValue;
import autosar3x.ecucdescription.ReferenceValue;

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
