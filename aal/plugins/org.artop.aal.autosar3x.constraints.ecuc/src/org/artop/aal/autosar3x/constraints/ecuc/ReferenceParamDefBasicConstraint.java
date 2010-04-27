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

import org.artop.aal.common.resource.AutosarURIFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucparameterdef.EcucparameterdefPackage;
import autosar3x.ecucparameterdef.ParamConfContainerDef;
import autosar3x.ecucparameterdef.ReferenceParamDef;

public class ReferenceParamDefBasicConstraint extends AbstractParameterValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof ReferenceParamDef;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		ReferenceParamDef referenceParamDef = (ReferenceParamDef) ctx.getTarget();

		final IStatus status;
		if (referenceParamDef.eIsSet(EcucparameterdefPackage.eINSTANCE.getReferenceParamDef_Destination())) {
			ParamConfContainerDef paramConfContainerDef = referenceParamDef.getDestination();
			if (paramConfContainerDef.eIsProxy()) {
				status = ctx.createFailureStatus("Could not resolve destination. ("
						+ AutosarURIFactory.getAbsoluteQualifiedName(paramConfContainerDef) + " not found)");
			} else {
				status = ctx.createSuccessStatus();
			}
		} else {
			status = ctx.createFailureStatus("'destination' not set");
		}

		return status;
	}

}
