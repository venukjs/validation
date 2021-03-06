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

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GChoiceReferenceDef;
import gautosar.gecucparameterdef.GContainerDef;

/**
 * Superclass for the constraints implementations on reference def.
 */
public class GChoiceReferenceDefBasicConstraint extends AbstractGParameterValueConstraint {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GChoiceReferenceDef;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);
		GChoiceReferenceDef gChoiceReferenceDef = (GChoiceReferenceDef) ctx.getTarget();

		if (gChoiceReferenceDef.gGetDestinations().isEmpty()) {
			multiStatus.add(ctx.createFailureStatus(EcucConstraintMessages.choiceref_emptyDestination));
		} else {
			for (GContainerDef choiceParamConfContainerDef : gChoiceReferenceDef.gGetDestinations()) {
				if (choiceParamConfContainerDef.eIsProxy()) {
					multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_destinationNotResolved,
							AutosarURIFactory.getAbsoluteQualifiedName(choiceParamConfContainerDef))));
				}
			}
		}
		return multiStatus;

	}

	@Override
	protected boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue) {
		// TODO Auto-generated method stub
		return true;
	}
}
