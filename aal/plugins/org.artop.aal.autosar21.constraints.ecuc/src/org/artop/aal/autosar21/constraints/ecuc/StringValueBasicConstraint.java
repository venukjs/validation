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
package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGStringValueBasicConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar21.ecucdescription.StringValue;

public class StringValueBasicConstraint extends AbstractGStringValueBasicConstraint
{

	@Override
	protected boolean isValueSet(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		StringValue value = (StringValue)gParameterValue;
		return value.isSetValue();
	}

}
