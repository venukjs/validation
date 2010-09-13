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
 * 
 * Superclass for the constraints implementations on the structural integrity of a container.
 * 
 */
public class GContainerStructuralIntegrityConstraint extends AbstractModelConstraintWithPrecondition 
{
	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		boolean isApplicable = false;
		if (ctx.getTarget() instanceof GContainer)
		{
			// OK: correct type
			GContainer gContainer = (GContainer) ctx.getTarget();
			EObject parentEObject = gContainer.eContainer();
			if (null != parentEObject)
			{
				// OK: there is a parent
				GContainerDef gContainerDef = gContainer.gGetDefinition();
				if (gContainerDef != null && false == gContainerDef.eIsProxy())
				{
					// OK: there is a definition
					if (parentEObject instanceof GModuleConfiguration) 
					{
						// the parent is a MouleConfiguration an has a definition
						GModuleConfiguration parentGModuleConfiguration = (GModuleConfiguration) parentEObject;
						GModuleDef parentGModuleDef = parentGModuleConfiguration.gGetDefinition();
						isApplicable = null != parentGModuleDef && false == parentGModuleDef.eIsProxy();
					}
					else if (parentEObject instanceof GContainer) 
					{
						// the parent is a GContainer an has a definition
						GContainer parentGContainer = (GContainer) parentEObject;
						GContainerDef parentGContainerDef = parentGContainer.gGetDefinition();
						isApplicable = null != parentGContainerDef && false == parentGContainerDef.eIsProxy();
					}
				}
			}
		}
		return isApplicable;

	}

	@Override
	public IStatus doValidate(IValidationContext ctx) 
	{
		GContainer gContainer = (GContainer) ctx.getTarget();
		EObject parent = gContainer.eContainer();
		return validateStructuralIntegrity(ctx, gContainer, parent);
	}

	private IStatus validateStructuralIntegrity(IValidationContext ctx, GContainer gContainer, EObject parent) 
	{
		final IStatus status;

		GContainerDef gContainerDef = gContainer.gGetDefinition();

		final List<GContainerDef> containerDefList = new ArrayList<GContainerDef>();
		if (parent instanceof GModuleConfiguration) 
		{
			// the current GContainer is directly contained in a GModuleConfiguration
			GModuleConfiguration parentGModuleConfiguration = (GModuleConfiguration) parent;
			GModuleDef parentGModuleDef = parentGModuleConfiguration.gGetDefinition();
			containerDefList.addAll(EcucUtil.getAllContainersOf(parentGModuleDef));
		}
		else if (parent instanceof GContainer) 
		{
			// the current GContainer is contained in another GContainer
			GContainer parentContainer = (GContainer) parent;
			GContainerDef parentContainerDef = parentContainer.gGetDefinition();

			if (parentContainerDef instanceof GParamConfContainerDef)
			{
				// the parent containers definition is a GParamConfContainerDef
				GParamConfContainerDef parentParamConfContainerDef = (GParamConfContainerDef) parentContainerDef;
				containerDefList.addAll(EcucUtil.getAllSubContainersOf(parentParamConfContainerDef));
			}
			else if (parentContainerDef instanceof GChoiceContainerDef)
			{
				// the parent containers definition is a GChoiceContainerDef
				GChoiceContainerDef parentGChoiceContainerDef = (GChoiceContainerDef) parentContainerDef;
				containerDefList.addAll(EcucUtil.getAllChoicesOf(parentGChoiceContainerDef));
			}
		}

		if (0 == containerDefList.size())
		{
			status = ctx.createFailureStatus(NLS.bind(Messages.structuralIntegrity_noDefInParent,"container")); //$NON-NLS-1$
		}
		else if (!containerDefList.contains(gContainerDef)) 
		{
			status = ctx.createFailureStatus(NLS.bind(Messages.structuralIntegrity_DefNotFoundInParent, "container")); //$NON-NLS-1$
		}
		else 
		{
			status = ctx.createSuccessStatus();
		}

		return status;

	}
}
