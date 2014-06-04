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

import autosar3x.ecucparameterdef.ModuleDef;
import autosar3x.genericstructure.infrastructure.autosar.ARPackage;
import autosar3x.genericstructure.infrastructure.identifiable.PackageableElement;

public class ModuleDefMultiplicityShouldBeTheSame3xConstraint extends AbstractSplitModelConstraintWithPrecondition {

	public ModuleDefMultiplicityShouldBeTheSame3xConstraint() {
	}

	@Override
	protected boolean isApplicable(IValidationContext ctx) {

		EObject eObject = ctx.getTarget();
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

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		// Retrieve target object and see if we have to do anything with it
		ModuleDef targetModuleDef = (ModuleDef) ctx.getTarget();

		if (!isOK(targetModuleDef)) {
			return ctx.createFailureStatus(new Object[] { targetModuleDef.getARPackage().getShortName(), targetModuleDef.getShortName() });

		}

		return ctx.createSuccessStatus();
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