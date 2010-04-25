/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.constraints.ecuc;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucparameterdef.ContainerDef;

public class ContainerConstraint extends AbstractModelConstraintWithPrecondition {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof Container;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		return validateDefinitionRef(ctx, (Container) ctx.getTarget());
	}

	private IStatus validateDefinitionRef(IValidationContext ctx, Container container) {
		// check if definition is set and available
		final IStatus status;
		if (false == container.eIsSet(EcucdescriptionPackage.eINSTANCE.getContainer_Definition())) {
			status = ctx.createFailureStatus("definition reference not set");
		} else {
			ContainerDef containerDef = container.getDefinition();
			if (null == containerDef) {
				status = ctx.createFailureStatus("definition reference not set");
			} else if (containerDef.eIsProxy()) {
				status = ctx.createFailureStatus("reference to definition could not be resolved");
			} else {
				status = ctx.createSuccessStatus();
			}
		}
		return status;
	}

}
