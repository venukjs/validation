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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.ModuleConfiguration;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ModuleDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public class ContainerStructuralIntegrityConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof Container;

		final IStatus status;

		Container container = (Container) ctx.getTarget();
		EObject parent = container.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus("element has no parent");
		} else {
			status = validateStructuralIntegrity(ctx, container, parent);
		}

		return status;
	}

	private IStatus validateStructuralIntegrity(IValidationContext ctx, Container container, EObject parent) {
		final IStatus status;

		ContainerDef containerDef = container.getDefinition();

		if (null == containerDef || containerDef.eIsProxy()) {
			// error in the definitions are REPORTED in other constraints
			status = ctx.createSuccessStatus();
		} else {
			final List<ContainerDef> containerDefList = new ArrayList<ContainerDef>();
			if (parent instanceof ModuleConfiguration) {
				// the current Container is directly contained in a ModuleConfiguration
				ModuleConfiguration parentModule = (ModuleConfiguration) parent;
				ModuleDef parentModuleDef = parentModule.getDefinition();
				containerDefList.addAll(EcucUtil.getAllContainersOf(parentModuleDef));

			} else if (parent instanceof Container) {
				// the current Container is contained in another Container
				Container parentContainer = (Container) parent;
				ContainerDef parentContainerDef = parentContainer.getDefinition();

				if (parentContainerDef instanceof ParamConfContainerDef) {
					// the parent containers definition is a ParamConfContainerDef
					ParamConfContainerDef parentParamConfContainerDef = (ParamConfContainerDef) parentContainerDef;
					containerDefList.addAll(EcucUtil.getAllSubContainersOf(parentParamConfContainerDef));
				} else if (parentContainerDef instanceof ChoiceContainerDef) {
					// the parent containers definition is a ChoiceContainerDef
					ChoiceContainerDef parentChoiceContainerDef = (ChoiceContainerDef) parentContainerDef;
					containerDefList.addAll(EcucUtil.getAllChoicesOf(parentChoiceContainerDef));
				}
			}

			if (0 == containerDefList.size()) {
				status = ctx.createFailureStatus("containement problem: no container definitions found in parent definition"); //$NON-NLS-1$
			} else if (!containerDefList.contains(containerDef)) {
				status = ctx.createFailureStatus("containement problem: container definition of this container not found in parent definition"); //$NON-NLS-1$
			} else {
				status = ctx.createSuccessStatus();
			}
		}
		return status;

	}

}
