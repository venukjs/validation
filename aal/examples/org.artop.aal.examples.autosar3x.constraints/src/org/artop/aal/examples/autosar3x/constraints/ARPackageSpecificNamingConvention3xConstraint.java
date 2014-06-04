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
package org.artop.aal.examples.autosar3x.constraints;

import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.genericstructure.infrastructure.autosar.ARPackage;

public class ARPackageSpecificNamingConvention3xConstraint extends AbstractSplitModelConstraintWithPrecondition {

	public static final String ARPACKAGE_PREFIX = "arp"; //$NON-NLS-1$

	public ARPackageSpecificNamingConvention3xConstraint() {
	}

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		EObject eObject = ctx.getTarget();

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

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		ARPackage targetARPackage = (ARPackage) ctx.getTarget();

		if (!isOK(targetARPackage)) {
			return ctx.createFailureStatus(new Object[] { targetARPackage.getShortName() });
		}

		return ctx.createSuccessStatus();
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
