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

import org.artop.aal.autosar3x.constraints.ecuc.internal.Activator;
import org.artop.aal.common.resource.AutosarURIFactory;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucparameterdef.ChoiceReferenceParamDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public class ChoiceReferenceParamDefBasicConstraint extends AbstractParameterValueConstraint {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof ChoiceReferenceParamDef;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		ChoiceReferenceParamDef choiceReferenceParamDef = (ChoiceReferenceParamDef) ctx.getTarget();

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);
		if (choiceReferenceParamDef.getDestinations().isEmpty()) {
			multiStatus.add(ctx.createFailureStatus("A ChoiceReferenceParamDef shall define at least one 'destination'"));
		} else {
			for (ParamConfContainerDef choiceParamConfContainerDef : choiceReferenceParamDef.getDestinations()) {
				if (choiceParamConfContainerDef.eIsProxy()) {
					multiStatus.add(ctx.createFailureStatus("Could not resolve destination "
							+ AutosarURIFactory.getAbsoluteQualifiedName(choiceParamConfContainerDef)));
				}
			}
		}
		return multiStatus;
	}
}
