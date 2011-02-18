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

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucparameterdef.ModuleDef;
import autosar3x.genericstructure.infrastructure.autosar.ARPackage;
import autosar3x.genericstructure.infrastructure.identifiable.PackageableElement;

public class ModuleDefMultiplicityShouldBeTheSame3xConstraint extends AbstractModelConstraint {

	public ModuleDefMultiplicityShouldBeTheSame3xConstraint() {
	}

	@Override
	public IStatus validate(IValidationContext ctx) {
		// Retrieve target object and see if we have to do anything with it
		EObject targetObject = ctx.getTarget();
		if (isApplicable(targetObject)) {
			ModuleDef targetModuleDef = (ModuleDef) targetObject;
			if (!isOK(targetModuleDef)) {
				return ctx.createFailureStatus(new Object[] { targetModuleDef.getARPackage().getShortName(), targetModuleDef.getShortName() });
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
		Assert.isNotNull(eObject);

		// The EObject must be a ModuleDef
		if (!(eObject instanceof ModuleDef)) {
			return false;
		}

		// The ModuleDef must be contained in an ARPackage
		if (eObject.eContainer() == null || !(eObject.eContainer() instanceof ARPackage)) {
			return false;
		}

		return true;
	}

	/**
	 * The constraint itself.
	 * 
	 * @param moduleDef
	 *            The target {@link ModuleDef}.
	 * @return true if given {@link ModuleDef} satisfies this constraint, false otherwise.
	 */
	private boolean isOK(ModuleDef moduleDef) {
		Assert.isNotNull(moduleDef);

		// Find all other ModuleDefs within same ARPackage
		for (PackageableElement element : moduleDef.getARPackage().getElements()) {
			if (element instanceof ModuleDef && element != moduleDef) {
				ModuleDef otherModuleDef = (ModuleDef) element;

				// Test if multiplicities of other ModuleDef are the same as
				// those of given ModuleDef
				if (moduleDef.getLowerMultiplicity() != null) {
					if (!moduleDef.getLowerMultiplicity().equals(otherModuleDef.getLowerMultiplicity())) {
						return false;
					}
				}
				if (moduleDef.getUpperMultiplicity() != null) {
					if (!moduleDef.getUpperMultiplicity().equals(otherModuleDef.getUpperMultiplicity())) {
						return false;
					}
				}
			}
		}

		return true;
	}
}