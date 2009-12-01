/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on Released
 * AUTOSAR Material (ASLR) which accompanies this distribution, and is available
 * at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.examples.sphinx.validation.constraints;

import org.artop.ecl.examples.sphinx.SInterface;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class SInterfaceNamingConventionConstraint extends AbstractModelConstraint {

	public static final String SINTERFACE_PREFIX = "if"; //$NON-NLS-1$

	public SInterfaceNamingConventionConstraint() {
	}

	@Override
	public IStatus validate(IValidationContext ctx) {
		// Retrieve target object and see if we have to do anything with it
		EObject targetObject = ctx.getTarget();
		if (isApplicable(targetObject)) {
			SInterface targetSInterface = (SInterface) targetObject;
			if (!isOK(targetSInterface)) {
				return ctx.createFailureStatus(new Object[] { targetSInterface.getName() });
			}
		}

		return ctx.createSuccessStatus();
	}

	/**
	 * Tests if given {@link EObject} is applicable to this constraint.
	 * 
	 * @param eObject
	 *            The target {@link EObject}.
	 * @return true if given {@link EObject} is applicable to this constraint, false otherwise.
	 */
	private boolean isApplicable(EObject eObject) {
		// The EObject object must be an SInterface
		if (!(eObject instanceof SInterface)) {
			return false;
		}

		// Ignore ARPackages without short name
		String name = ((SInterface) eObject).getName();
		if (name == null || name.length() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * The constraint itself.
	 * 
	 * @param sInterface
	 *            The target {@link SInterface}.
	 * @return true if given {@link SInterface} satisfies this constraint, false otherwise.
	 */
	private boolean isOK(SInterface sInterface) {
		Assert.isNotNull(sInterface);

		return sInterface.getName().startsWith(SINTERFACE_PREFIX);
	}
}
