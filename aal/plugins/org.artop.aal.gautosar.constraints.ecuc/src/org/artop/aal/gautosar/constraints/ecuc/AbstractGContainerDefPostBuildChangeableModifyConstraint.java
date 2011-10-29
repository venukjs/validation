/**
 * <copyright>
 * 
 * Copyright (c) See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucparameterdef.GContainerDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractGContainerDefPostBuildChangeableModifyConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GContainerDef;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GContainerDef containerDef = (GContainerDef) ctx.getTarget();

		/*
		 * The corresponding Container Definition from the Refined Module Definition
		 */
		GContainerDef refinedContainerDef = EcucUtil.getContainerDefInRefinedModuleDef(containerDef);

		/* If no Refined Container Definition can be found, just return. */
		if (refinedContainerDef == null) {
			return ctx.createSuccessStatus();
		}

		/* Flag used to mark the 'post-build changeable' as modified or not. */
		boolean valid = true;

		/*
		 * PostBuildChangeable of the Container Definition in the Refined Module Definition.
		 */
		Boolean refinedPostBuildChangeable = refinedContainerDef.gGetPostBuildChangeable();

		/*
		 * PostBuildChangeable of the Container Definition in the Vendor Specific Module Definition.
		 */
		Boolean vSpecifPostBuildChangeable = containerDef.gGetPostBuildChangeable();

		/*
		 * A warning is raised if 'post-build changeable' has been modified in the Vendor Specific ModuleDef.
		 */
		if (!isSetPostBuildChangeable(refinedContainerDef)) {
			valid = !isSetPostBuildChangeable(containerDef);
		} else {
			valid = refinedPostBuildChangeable.equals(vSpecifPostBuildChangeable);
		}

		if (!valid) {
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.containerDef_postBuildChangeableModified,
					AutosarURIFactory.getAbsoluteQualifiedName(containerDef),
					AutosarURIFactory.getAbsoluteQualifiedName(EcucUtil.getParentModuleDefForContainerDef(refinedContainerDef))));
		}

		return ctx.createSuccessStatus();
	}

	protected abstract boolean isSetPostBuildChangeable(GContainerDef containerDef);

}
