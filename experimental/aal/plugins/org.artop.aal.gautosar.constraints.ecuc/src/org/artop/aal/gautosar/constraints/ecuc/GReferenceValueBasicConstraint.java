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
import gautosar.gecucdescription.GReferenceValue;
import gautosar.gecucparameterdef.GChoiceReferenceDef;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GForeignReferenceDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;
import gautosar.gecucparameterdef.GReferenceDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Superclass for the constraints implementations on a reference value.
 * 
 */
public class GReferenceValueBasicConstraint extends
		AbstractGConfigReferenceValueConstraint
{

	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return ctx.getTarget() instanceof GReferenceValue;

	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{
		GReferenceValue gReferenceValue = (GReferenceValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, gReferenceValue);
		if (status.isOK())
		{
			status = validateValue(ctx, gReferenceValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx,
			GConfigReferenceValue gConfigReferenceValue)
	{
		// check if definition is set and available
		IStatus status = super
				.validateDefinitionRef(ctx, gConfigReferenceValue);
		if (status.isOK())
		{
			GConfigReference configReferenceDef = gConfigReferenceValue
					.gGetDefinition();
			if (!(configReferenceDef instanceof GReferenceDef
					|| configReferenceDef instanceof GChoiceReferenceDef || configReferenceDef instanceof GForeignReferenceDef))
			{
				status = ctx
						.createFailureStatus(NLS
								.bind(Messages.generic_definitionNotOfType,
										"GReferenceDef/GChoiceReferenceDef/GForeignReferenceDef"));
			}
		}
		return status;
	}

	/**
	 * Performs the validation on the value of the given
	 * <code>gReferenceValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gReferenceValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateValue(IValidationContext ctx,
			GReferenceValue gReferenceValue)
	{

		IStatus status;

		status = validateValueSet(ctx, gReferenceValue,
				gReferenceValue.gGetValue());
		if (!status.isOK())
		{
			return status;
		}

		GConfigReference configReference = gReferenceValue.gGetDefinition();
		if (configReference instanceof GChoiceReferenceDef)
		{
			status = validateReferenceValue_ChoiceReference(ctx,
					gReferenceValue);
		} else if (configReference instanceof GReferenceDef)
		{
			status = validateReferenceValue_Reference(ctx, gReferenceValue);
		} else if (configReference instanceof GForeignReferenceDef)
		{
			status = validateReferenceValue_ForeignReference(ctx,
					gReferenceValue);
		} else
		{
			status = ctx.createSuccessStatus();
		}
		return status;
	}

	private IStatus validateReferenceValue_Reference(IValidationContext ctx,
			GReferenceValue gReferenceValue)
	{
		EObject valueObject = gReferenceValue.gGetValue();
		GReferenceDef referenceDef = (GReferenceDef) gReferenceValue
				.gGetDefinition();
		assert null != valueObject;
		assert null != referenceDef;

		final IStatus status;

		// CHECK if value is a GContainer
		if (!(valueObject instanceof GContainer))
		{
			status = ctx.createFailureStatus(NLS.bind(
					Messages.reference_valueNotOfType, "container"));
		} else
		{

			// CHECK if GParamConfContainerDef is available for destination
			GParamConfContainerDef containerDefFromDestination = referenceDef
					.gGetDestination();
			if (null == containerDefFromDestination
					|| containerDefFromDestination.eIsProxy())
			{
				status = ctx
						.createFailureStatus(NLS
								.bind(Messages.generic_validationNotPossible,
										AutosarURIFactory
												.getAbsoluteQualifiedName(containerDefFromDestination)));
			} else
			{
				GContainer gContainer = (GContainer) valueObject;
				GParamConfContainerDef containerDefFromDefinition = (GParamConfContainerDef) gContainer
						.gGetDefinition();

				if (null == containerDefFromDefinition
						|| containerDefFromDefinition.eIsProxy())
				{
					// CHECK if GParamConfContainerDef is available for
					// GContainer
					status = ctx.createFailureStatus(NLS.bind(
							Messages.reference_valueDefinitionNotSet,
							"param conf container def", gContainer));
				} else if (!containerDefFromDestination
						.equals(containerDefFromDefinition))
				{
					// CHECK if GParamConfContainerDef is the same for
					// GContainer and destination
					status = ctx.createFailureStatus(NLS.bind(
							Messages.reference_differentDefAndDestination,
							containerDefFromDefinition.gGetShortName(),
							containerDefFromDestination.gGetShortName()));
				} else
				{
					status = ctx.createSuccessStatus();
				}
			}
		}

		return status;
	}

	private IStatus validateReferenceValue_ChoiceReference(
			IValidationContext ctx, GReferenceValue GReferenceValue)
	{
		// assert that the value is available
		EObject valueObject = GReferenceValue.gGetValue();
		GChoiceReferenceDef choiceReferenceDef = (GChoiceReferenceDef) GReferenceValue
				.gGetDefinition();
		assert null != valueObject;
		assert null != choiceReferenceDef;

		IStatus status = ctx.createSuccessStatus();

		// CHECK if value is a GContainer
		if (!(valueObject instanceof GContainer))
		{
			status = ctx.createFailureStatus(NLS.bind(
					Messages.reference_valueNotOfType, "container")); //$NON-NLS-1$
		} else
		{
			// CHECK if destination available
			EList<GParamConfContainerDef> containerDefList = choiceReferenceDef
					.gGetDestinations();
			if (null == containerDefList || 0 == containerDefList.size())
			{
				status = ctx.createFailureStatus(NLS.bind(
						Messages.generic_validationNotPossible,
						AutosarURIFactory
								.getAbsoluteQualifiedName(choiceReferenceDef)));
			} else
			{
				// CHECK if the value GContainer is included in the destination
				// of the reference parameter
				GContainer gContainer = (GContainer) valueObject;
				GParamConfContainerDef containerDefFromDefinition = (GParamConfContainerDef) gContainer
						.gGetDefinition();
				String tmpStr = ""; //$NON-NLS-1$
				for (int i = 0; i < containerDefList.size(); i++)
				{
					tmpStr = tmpStr + containerDefList.get(i).gGetShortName()
							+ ","; //$NON-NLS-1$
				}
				if (containerDefFromDefinition != null)
				{
					if (false == containerDefList
							.contains(containerDefFromDefinition))
					{

						status = ctx.createFailureStatus(NLS.bind(
								Messages.choiceref_containerNotInTheDest,
								containerDefFromDefinition.gGetShortName(),
								tmpStr)); //$NON-NLS-1$
					}
				} else
				{
					status = ctx.createFailureStatus(NLS.bind(
							Messages.choiceref_containerNotInTheDest,
							"", tmpStr)); //$NON-NLS-1$
				}
			}
		}
		return status;
	}

	private IStatus validateReferenceValue_ForeignReference(
			IValidationContext ctx, GReferenceValue gReferenceValue)
	{
		EObject valueObject = gReferenceValue.gGetValue();
		GForeignReferenceDef foreignReferenceDef = (GForeignReferenceDef) gReferenceValue
				.gGetDefinition();
		assert null != valueObject;
		assert null != foreignReferenceDef;

		final IStatus status;

		if (foreignReferenceDef != null)
		{
			// CHECK if reference element is of correct type
			String destinationTypeName = foreignReferenceDef
					.gGetDestinationType();
			if (null == destinationTypeName
					|| destinationTypeName.length() == 0)
			{
				status = ctx
						.createFailureStatus(Messages.reference_targetDestinationTypeNotAvailable); //$NON-NLS-1$
			} else if (!isInstanceOfDestinationType(valueObject,
					destinationTypeName))
			{
				status = ctx.createFailureStatus(NLS.bind(
						Messages.reference_valueNotInstanceOfDestType,
						destinationTypeName));
			} else
			{
				status = ctx.createSuccessStatus();
			}
		}
		else
		{
			status = ctx
			.createFailureStatus(Messages.reference_targetDestinationTypeNotAvailable); //$NON-NLS-1$
		}

		return status;
	}

}
