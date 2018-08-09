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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.artop.aal.autosar40.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGConfigReferenceValueBasicConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import autosar40.ecucdescription.EcucInstanceReferenceValue;
import autosar40.ecucdescription.EcucReferenceValue;
import gautosar.gecucdescription.GConfigReferenceValue;

public class EcucAbstractReferenceValueBasicConstraint extends AbstractGConfigReferenceValueBasicConstraint {

	@Override
	protected Object getValue(GConfigReferenceValue configReferenceValue) {
		if (configReferenceValue instanceof EcucReferenceValue) {
			try {
				Method method = configReferenceValue.getClass().getMethod("getValue"); //$NON-NLS-1$
				return method.invoke(configReferenceValue);

			} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
				Activator.getDefault().getLog().log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage()));
			}
		} else if (configReferenceValue instanceof EcucInstanceReferenceValue) {
			return ((EcucInstanceReferenceValue) configReferenceValue).getValue();

		}
		return null;
	}

}
