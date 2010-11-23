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

import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import java.util.ArrayList;
import java.util.List;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Superclass for the constraints implementations on container.
 */

public class GContainerBasicConstraint extends AbstractModelConstraintWithPrecondition {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GContainer;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		return validateDefinitionRef(ctx, (GContainer) ctx.getTarget());
	}

	/**
	 * Performs the validation on the definition of the given <code>gContainer</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gContainer
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	private IStatus validateDefinitionRef(IValidationContext ctx, GContainer gContainer) {
		// check if definition is set and available
		final IStatus status;

		GContainerDef containerDef = gContainer.gGetDefinition();
		if (null == containerDef) {
			status = ctx.createFailureStatus(Messages.generic_definitionReferenceNotSet);
		} else if (containerDef.eIsProxy()) {
			status = ctx.createFailureStatus(Messages.generic_definitionReferenceNotResolved);
		} else {
			status = validateContainmentStructure(ctx, gContainer);
		}

		return status;
	}

	private IStatus validateContainmentStructure(IValidationContext ctx, GContainer gContainer) {
		assert null != gContainer;
		assert null != gContainer.gGetDefinition();
		assert false == gContainer.gGetDefinition().eIsProxy();

		final IStatus status;

		EObject parent = gContainer.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus(Messages.generic_noParent);
		} else {
			GContainerDef gContainerDef = gContainer.gGetDefinition();

			if (parent instanceof GModuleConfiguration) {
				// the current GContainer is directly contained in a GModuleConfiguration
				GModuleConfiguration parentGModuleConfiguration = (GModuleConfiguration) parent;
				GModuleDef parentGModuleDef = parentGModuleConfiguration.gGetDefinition();

				if (null == parentGModuleDef) {
					// this error will be reported when this constraint is applied to the parent object
					status = ctx.createSuccessStatus();
				} else {
					if (parentGModuleDef.gGetContainers().contains(gContainerDef)) {
						status = ctx.createSuccessStatus();
					} else {
						status = ctx.createFailureStatus(NLS.bind(Messages.structuralIntegrity_containmentProblem, "container",
								gContainerDef.gGetShortName()));
					}
				}

			} else if (parent instanceof GContainer) {
				// the current GContainer is contained in another GContainer
				GContainer parentContainer = (GContainer) parent;
				GContainerDef parentContainerDef = parentContainer.gGetDefinition();

				if (null == parentContainerDef) {
					// this error will be reported when this constraint is applied to the parent object
					status = ctx.createSuccessStatus();
				} else {
					final List<? extends GContainerDef> validContainerDefs;
					if (parentContainerDef instanceof GParamConfContainerDef) {
						GParamConfContainerDef parentParamConfContainerDef = (GParamConfContainerDef) parentContainerDef;
						validContainerDefs = parentParamConfContainerDef.gGetSubContainers();
					} else if (parentContainerDef instanceof GChoiceContainerDef) {
						GChoiceContainerDef parentChoiceContainerDef = (GChoiceContainerDef) parentContainerDef;
						validContainerDefs = parentChoiceContainerDef.gGetChoices();
					} else {
						assert false; // this assertion will help detecting future metamodel extensions
						validContainerDefs = new ArrayList<GContainerDef>();
					}
					if (validContainerDefs.contains(gContainerDef)) {
						status = ctx.createSuccessStatus();
					} else {
						status = ctx.createFailureStatus(NLS.bind(Messages.structuralIntegrity_containmentProblem, "container",
								gContainerDef.gGetShortName()));
					}
				}
			} else {
				assert false; // this assertion will help detecting future metamodel extensions
				status = ctx.createSuccessStatus();
			}
		}

		return status;

	}

}
