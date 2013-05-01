/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GReferenceDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public class GReferenceDefBasicConstraint extends AbstractGParameterValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GReferenceDef;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		GReferenceDef gReferenceDef = (GReferenceDef) ctx.getTarget();
		GContainerDef destination = gReferenceDef.gGetRefDestination();

		if (destination != null) {
			if (destination.eIsProxy()) {
				return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_destinationNotResolved,
						AutosarURIFactory.getAbsoluteQualifiedName(destination)));
			}
			return ctx.createSuccessStatus();

		}

		return ctx.createFailureStatus(EcucConstraintMessages.reference_destinationNotSet);
	}

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		return true;
	}
}
