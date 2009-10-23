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
package org.artop.aal.examples.validation.constraints;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.genericstructure.infrastructure.autosar.ARPackage;

public class ARPackageSpecificNamingConvention3xConstraint extends AbstractModelConstraint {

	public static final String ARPACKAGE_PREFIX = "arp"; //$NON-NLS-1$

	public ARPackageSpecificNamingConvention3xConstraint() {
	}

	@Override
	public IStatus validate(IValidationContext ctx) {
		// Retrieve target object and see if we have to do anything with it
		EObject targetObject = ctx.getTarget();
		if (isApplicable(targetObject)) {
			ARPackage targetARPackage = (ARPackage) targetObject;
			if (!isOK(targetARPackage)) {
				return ctx.createFailureStatus(new Object[] { targetARPackage.getShortName() });
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
		// The EObject object must be an ARPackage
		if (!(eObject instanceof ARPackage)) {
			return false;
		}

		// Ignore ARPackages without short name
		String shortName = ((ARPackage) eObject).getShortName();
		if (shortName == null || shortName.length() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * The constraint itself.
	 * 
	 * @param arPackage
	 *            The target {@link ARPackage}.
	 * @return true if given {@link ARPackage} satisfies this constraint, false otherwise.
	 */
	private boolean isOK(ARPackage arPackage) {
		Assert.isNotNull(arPackage);

		return arPackage.getShortName().startsWith(ARPACKAGE_PREFIX);
	}
}
