/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy,  Continental Engineering Services  and others.
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

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Superclass for the constraints implementations on config reference def.
 * 
 */
public class GConfigReferenceValueStructuralIntegrityConstraint extends
		AbstractModelConstraintWithPrecondition
{
	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		boolean isApplicable = false;

		if (ctx.getTarget() instanceof GConfigReferenceValue)
		{
			// required ECUC description objects
			GConfigReferenceValue gConfigReferenceValue = (GConfigReferenceValue) ctx
					.getTarget();
			GContainer parentGContainer = (GContainer) gConfigReferenceValue
					.eContainer();

			if (null != parentGContainer)
			{
				// required ECUC definition objects
				GContainerDef parentGContainerDef = parentGContainer
						.gGetDefinition();
				GConfigReference gConfigReference = gConfigReferenceValue
						.gGetDefinition();
				isApplicable = null != parentGContainerDef
						&& false == parentGContainerDef.eIsProxy();
				isApplicable &= null != gConfigReference
						&& false == gConfigReference.eIsProxy();
			}
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{

		GConfigReferenceValue gConfigReferenceValue = (GConfigReferenceValue) ctx
				.getTarget();
		GContainer parentContainer = (GContainer) gConfigReferenceValue
				.eContainer();

		return validateStructuralIntegrity(ctx, gConfigReferenceValue,
				parentContainer);

	}

	/**
	 * Performs the validation on the structural integrity of the given
	 * <code>gConfigReferenceValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gConfigReferenceValue
	 *            the element on which the validation is performed.
	 * @param parentContainer
	 *            the parent container
	 * @return a status object describing the result of the validation.
	 */
	private IStatus validateStructuralIntegrity(IValidationContext ctx,
			GConfigReferenceValue gConfigReferenceValue,
			GContainer parentContainer)
	{
		final IStatus status;

		GContainerDef parentGContainerDef = parentContainer.gGetDefinition();
		GConfigReference gConfigReference = gConfigReferenceValue
				.gGetDefinition();

		if (parentGContainerDef instanceof GChoiceContainerDef)
		{
			status = ctx.createFailureStatus(NLS.bind(
					Messages.structuralIntegrity_NotAllowedInChoiceContainer,
					"reference value"));
		} else if (parentGContainerDef instanceof GParamConfContainerDef)
		{
			// the parent containers definition is a GParamConfContainerDef
			GParamConfContainerDef parentGParamConfContainerDef = (GParamConfContainerDef) parentGContainerDef;
			if (EcucUtil.getAllReferencesOf(parentGParamConfContainerDef)
					.contains(gConfigReference))
			{
				status = ctx.createSuccessStatus(); // reference is valid
			} else
			{
				status = ctx.createFailureStatus(NLS.bind(
						Messages.structuralIntegrity_containmentProblem,
						"reference value", gConfigReference.gGetShortName()));
			}

		} else
		{
			// in the current metamodel we only find expect
			// GParamConfContainerDef and GChoiceContainerDef
			// The assert will warn in case of metamodel extensions
			assert false;
			status = ctx.createSuccessStatus();
		}
		return status;

	}

}
