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
 *     Continental AG - Mark class as Splitable aware.
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Abstract superclass for the constraints implementations on a parameter value.
 */
public abstract class AbstractGParameterValueConstraint extends AbstractSplitModelConstraintWithPrecondition {

	/**
	 * Performs the validation on the definition of the given <code>gParameterValue</code>.
	 *
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gParameterValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateDefinitionRef(IValidationContext ctx, GParameterValue gParameterValue) {
		// check if definition is set and available
		final IStatus status;
		// if (false ==
		// gParameterValue.eIsSet(EcucdescriptionPackage.eINSTANCE.getParameterValue_Definition()))
		if (gParameterValue.gGetDefinition() == null) {
			status = ctx.createFailureStatus(EcucConstraintMessages.generic_definitionReferenceNotSet);
		} else if (gParameterValue.gGetDefinition().eIsProxy()) {
			status = ctx.createFailureStatus(EcucConstraintMessages.generic_definitionReferenceNotResolved);

		} else {
			status = validateContainmentStructure(ctx, gParameterValue);
		}
		return status;
	}

	/**
	 * Performs the validation on the structure of the given <code>gParameterValue</code>.
	 *
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gParameterValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	private IStatus validateContainmentStructure(IValidationContext ctx, GParameterValue gParameterValue) {
		final IStatus status;

		EObject parent = gParameterValue.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus(EcucConstraintMessages.generic_noParent);
		} else {
			GConfigParameter configParameter = gParameterValue.gGetDefinition();
			if (parent instanceof GContainer) {
				// the current Container is contained in another Container
				GContainer parentContainer = (GContainer) parent;
				GContainerDef parentContainerDef = parentContainer.gGetDefinition();

				if (parentContainerDef instanceof GParamConfContainerDef) {
					// the parent containers definition is a
					// ParamConfContainerDef
					GParamConfContainerDef parentParamConfContainerDef = (GParamConfContainerDef) parentContainerDef;
					if (parentParamConfContainerDef.gGetParameters().contains(configParameter)) {
						status = ctx.createSuccessStatus(); // parameter is
						// valid
					} else {
						status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.structuralIntegrity_containmentProblem,
								"parameter value", configParameter //$NON-NLS-1$
										.gGetShortName()));
					}
				} else if (parentContainerDef instanceof GChoiceContainerDef) {
					// TODO: create testcase
					status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.structuralIntegrity_NotAllowedInChoiceContainer,
							"parameter value")); //$NON-NLS-1$
				} else {
					status = ctx.createSuccessStatus();
				}
			} else {
				// we only expect an object of type Container
				status = ctx.createSuccessStatus();
			}

		}
		return status;
	}

	/**
	 * Returns whether the value of the given <code>gParameterValue</code> is set.
	 *
	 * @param ctx
	 * @param gParameterValue
	 * @return
	 */
	protected abstract boolean isValueSet(IValidationContext ctx, GParameterValue gParameterValue);

	/**
	 * Performs the validation on the value of the given <code>gParameterValue</code>.
	 *
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gParameterValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateValueSet(IValidationContext ctx, GParameterValue gParameterValue, Object value) {
		if (!isValueSet(ctx, gParameterValue)) {
			return ctx.createFailureStatus(EcucConstraintMessages.generic_valueNotSet);
		}

		if (null == value || value.equals("")) { //$NON-NLS-1$
			return ctx.createFailureStatus(EcucConstraintMessages.generic_valueNotSet);
		}

		return ctx.createSuccessStatus();

	}
}
