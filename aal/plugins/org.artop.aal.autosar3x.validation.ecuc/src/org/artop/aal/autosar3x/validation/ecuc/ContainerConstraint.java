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
package org.artop.aal.autosar3x.validation.ecuc;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.EcucdescriptionPackage;
import autosar3x.ecucdescription.ModuleConfiguration;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ModuleDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public class ContainerConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof Container;

		Container container = (Container) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, container);
		if (status.isOK()) {
			status = validateContainmentStructure(ctx, container);
		}

		return status;
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

	private IStatus validateContainmentStructure(IValidationContext ctx, Container container) {
		final IStatus status;

		EObject parent = container.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus("element has no parent");
		} else {
			final List<ContainerDef> containerDefList = new ArrayList<ContainerDef>();
			ContainerDef containerDef = container.getDefinition();
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
