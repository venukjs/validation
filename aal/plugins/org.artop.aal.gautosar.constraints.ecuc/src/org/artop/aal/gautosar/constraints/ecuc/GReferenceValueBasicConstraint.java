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

import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GReferenceValue;
import gautosar.gecucparameterdef.GChoiceReferenceDef;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GForeignReferenceDef;
import gautosar.gecucparameterdef.GReferenceDef;

/**
 * Superclass for the constraints implementations on a reference value.
 */
public class GReferenceValueBasicConstraint extends AbstractGConfigReferenceValueConstraint {

	private static final String DELIMITER = ", "; //$NON-NLS-1$

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GReferenceValue;

	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		GReferenceValue gReferenceValue = (GReferenceValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, gReferenceValue);
		if (status.isOK()) {
			status = validateValue(ctx, gReferenceValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, GConfigReferenceValue gConfigReferenceValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gConfigReferenceValue);
		if (status.isOK()) {
			GConfigReference configReferenceDef = gConfigReferenceValue.gGetDefinition();
			if (!(configReferenceDef instanceof GReferenceDef || configReferenceDef instanceof GChoiceReferenceDef
					|| configReferenceDef instanceof GForeignReferenceDef)) {
				status = ctx.createFailureStatus(
						NLS.bind(EcucConstraintMessages.generic_definitionNotOfType, "GReferenceDef/GChoiceReferenceDef/GForeignReferenceDef")); //$NON-NLS-1$
			}
		}
		return status;
	}

	/**
	 * Performs the validation on the value of the given <code>gReferenceValue</code>.
	 *
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gReferenceValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateValue(IValidationContext ctx, GReferenceValue gReferenceValue) {

		IStatus status;

		status = validateValueSet(ctx, gReferenceValue, gReferenceValue.gGetValue());
		if (!status.isOK()) {
			return status;
		}

		GConfigReference configReference = gReferenceValue.gGetDefinition();
		if (configReference instanceof GChoiceReferenceDef) {
			status = validateReferenceValue_ChoiceReference(ctx, gReferenceValue);
		} else if (configReference instanceof GReferenceDef) {
			status = validateReferenceValue_Reference(ctx, gReferenceValue);
		} else if (configReference instanceof GForeignReferenceDef) {
			status = validateReferenceValue_ForeignReference(ctx, gReferenceValue);
		} else {
			status = ctx.createSuccessStatus();
		}
		return status;
	}

	private IStatus validateReferenceValue_Reference(IValidationContext ctx, GReferenceValue gReferenceValue) {
		EObject valueObject = gReferenceValue.gGetValue();
		GReferenceDef referenceDef = (GReferenceDef) gReferenceValue.gGetDefinition();
		assert null != valueObject;
		assert null != referenceDef;

		final IStatus status;

		// CHECK if value is a GContainer
		if (!(valueObject instanceof GContainer)) {
			status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_valueNotOfType, "container")); //$NON-NLS-1$
		} else {

			// CHECK if GParamConfContainerDef is available for destination
			GContainerDef containerDefFromDestination = referenceDef.gGetRefDestination();
			if (null == containerDefFromDestination || containerDefFromDestination.eIsProxy()) {
				status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.generic_validationNotPossible,
						AutosarURIFactory.getAbsoluteQualifiedName(containerDefFromDestination)));
			} else {
				GContainer gContainer = (GContainer) valueObject;
				GContainerDef containerDefFromDefinition = gContainer.gGetDefinition();

				if (null == containerDefFromDefinition || containerDefFromDefinition.eIsProxy()) {
					// CHECK if GParamConfContainerDef is available for
					// GContainer
					String missingContainerURI = ""; //$NON-NLS-1$
					URI eProxyURI = null;
					// the definition is a proxy
					if (containerDefFromDefinition instanceof InternalEObject) {
						eProxyURI = ((InternalEObject) containerDefFromDefinition).eProxyURI();

					}
					// the actual value is a proxy
					else if (gContainer instanceof InternalEObject) {
						eProxyURI = ((InternalEObject) gContainer).eProxyURI();
					}

					if (eProxyURI != null) {
						missingContainerURI = eProxyURI.toString();
					}
					status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_valueDefinitionNotSet, missingContainerURI));
				} else {
					// CHECK if shortname of actual GParamConfContainerDef
					// corresponds to the expected shortname
					// We only compare the shortnames since the DESTINATION-REF of
					// Vendor Specific Module Definitions always points to destination
					// as defined in the Standardized Module Definition [ecu_sws_6015]
					String containerDefFromDefinitionShortName = containerDefFromDefinition.gGetShortName();
					String containerDefFromDestinationShortName = containerDefFromDestination.gGetShortName();
					if (null != containerDefFromDefinitionShortName
							&& !containerDefFromDefinitionShortName.equals(containerDefFromDestinationShortName)) {
						status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_differentDefAndDestination,
								containerDefFromDefinitionShortName, containerDefFromDestinationShortName));
					} else {
						status = ctx.createSuccessStatus();
					}
				}

			}
		}

		return status;
	}

	private IStatus validateReferenceValue_ChoiceReference(IValidationContext ctx, GReferenceValue GReferenceValue) {
		// assert that the value is available
		EObject valueObject = GReferenceValue.gGetValue();
		GChoiceReferenceDef choiceReferenceDef = (GChoiceReferenceDef) GReferenceValue.gGetDefinition();
		assert null != valueObject;
		assert null != choiceReferenceDef;

		IStatus status = ctx.createSuccessStatus();

		// CHECK if value is a GContainer
		if (!(valueObject instanceof GContainer)) {
			status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_valueNotOfType, "container")); //$NON-NLS-1$
		} else {

			if (valueObject.eIsProxy()) {

				// it is not in the scope of this constraint to check for unresolved references
				return status;
			}

			// CHECK if destination available
			EList<? extends GContainerDef> destinations = choiceReferenceDef.gGetDestinations();
			if (null == destinations || 0 == destinations.size()) {
				status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.generic_validationNotPossible,
						AutosarURIFactory.getAbsoluteQualifiedName(choiceReferenceDef)));
			} else {

				GContainer referredContainer = (GContainer) valueObject;
				GContainerDef definitionOfReferredContaier = referredContainer.gGetDefinition();

				if (definitionOfReferredContaier == null) {
					status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.generic_validationNotPossible,
							AutosarURIFactory.getAbsoluteQualifiedName(referredContainer)));
				} else {

					if (definitionOfReferredContaier.eIsProxy()) {
						status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.generic_validationNotPossible,
								AutosarURIFactory.getAbsoluteQualifiedName(referredContainer)));

					} else {

						// definition of referred container resolved, it make sense to validate it against allowed
						// destinations
						boolean destinationAllowed = isDestinationAllowed(definitionOfReferredContaier, choiceReferenceDef);

						if (!destinationAllowed) {
							status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.choiceref_containerNotInTheDest,
									new Object[] { referredContainer.gGetShortName(),
											AutosarURIFactory.getAbsoluteQualifiedName(definitionOfReferredContaier),
											getDestinationsAsString(destinations) }));
						}
					}

				}

			}
		}

		return status;
	}

	/**
	 * Returns whether the given <code>destinationToBeVerified</code> container definition conforms to the destinations
	 * specified in the <code>choiceReferenceDef</code>
	 *
	 * @param destinationToBeVerified
	 * @param choiceReferenceDef
	 * @return
	 */
	private boolean isDestinationAllowed(GContainerDef destinationToBeVerified, GChoiceReferenceDef choiceReferenceDef) {

		EList<? extends GContainerDef> allowedDestinations = choiceReferenceDef.gGetDestinations();

		String qualifiedNameOfDestToBeVerified = AutosarURIFactory.getAbsoluteQualifiedName(destinationToBeVerified);

		for (GContainerDef dest : choiceReferenceDef.gGetDestinations()) {

			String qualifiedName = AutosarURIFactory.getAbsoluteQualifiedName(dest);
			if (qualifiedName.equals(qualifiedNameOfDestToBeVerified)) {
				return true;
			}
		}

		// treat the case when choiceReferenceDef refines a standard reference definition and its possible destinations
		// point towards container definitions from the refined module
		GConfigReference refinedReference = EcucUtil.getFromRefined(choiceReferenceDef);
		if (refinedReference != null) {

			// obtain the corresponding container definition from the standard module
			GContainerDef refinedContainerDef = EcucUtil.getContainerDefInRefinedModuleDef(destinationToBeVerified);

			if (refinedContainerDef != null) {

				for (GContainerDef allowedDest : allowedDestinations) {

					if (allowedDest == refinedContainerDef) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private String getDestinationsAsString(List<? extends GContainerDef> destinations) {

		String qualifiedName;
		StringBuffer stringbuffer = new StringBuffer();

		for (GContainerDef destination : destinations) {

			qualifiedName = AutosarURIFactory.getAbsoluteQualifiedName(destination);
			if (null != qualifiedName) {
				stringbuffer.append(qualifiedName);
				stringbuffer.append(DELIMITER);
			}
		}

		int lastIndexOfComma = stringbuffer.lastIndexOf(DELIMITER);
		if (lastIndexOfComma > 0) {
			stringbuffer.delete(lastIndexOfComma, lastIndexOfComma + DELIMITER.length());
		}

		return stringbuffer.toString();
	}

	private IStatus validateReferenceValue_ForeignReference(IValidationContext ctx, GReferenceValue gReferenceValue) {
		EObject valueObject = gReferenceValue.gGetValue();
		GForeignReferenceDef foreignReferenceDef = (GForeignReferenceDef) gReferenceValue.gGetDefinition();
		assert null != valueObject;
		assert null != foreignReferenceDef;

		final IStatus status;

		if (foreignReferenceDef != null) {
			// CHECK if reference element is of correct type
			String destinationTypeName = foreignReferenceDef.gGetDestinationType();
			if (null == destinationTypeName || destinationTypeName.length() == 0) {
				status = ctx.createFailureStatus(EcucConstraintMessages.reference_targetDestinationTypeNotAvailable);
			} else if (!isInstanceOfDestinationType(valueObject, destinationTypeName)) {
				status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_valueNotInstanceOfDestType, destinationTypeName));
			} else {
				status = ctx.createSuccessStatus();
			}
		} else {
			status = ctx.createFailureStatus(EcucConstraintMessages.reference_targetDestinationTypeNotAvailable);
		}

		return status;
	}

}
