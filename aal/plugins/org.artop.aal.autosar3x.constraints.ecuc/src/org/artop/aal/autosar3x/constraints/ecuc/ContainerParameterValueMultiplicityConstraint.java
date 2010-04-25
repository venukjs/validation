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

import java.util.List;

import org.artop.aal.autosar3x.constraints.ecuc.internal.Activator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.ConfigParameter;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public class ContainerParameterValueMultiplicityConstraint extends AbstractModelConstraintWithPrecondition {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		boolean isApplicable = false;
		if (ctx.getTarget() instanceof Container) {
			Container container = (Container) ctx.getTarget();
			ContainerDef containerDef = container.getDefinition();
			isApplicable = null != containerDef && false == containerDef.eIsProxy() && containerDef instanceof ParamConfContainerDef;
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {

		Container container = (Container) ctx.getTarget();
		ParamConfContainerDef paramConfContainerDef = (ParamConfContainerDef) container.getDefinition();

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		List<ParameterValue> allParameterValues = EcucUtil.getAllParameterValuesOf(container);
		List<ConfigParameter> configParameters = paramConfContainerDef.getParameters();
		for (ConfigParameter currentConfigParameter : configParameters) {

			int numberOfParameters = EcucUtil.filterParameterValuesByDefinition(allParameterValues, currentConfigParameter).size();
			multiStatus.add(EcucUtil.validateLowerMultiplicity(ctx, numberOfParameters, currentConfigParameter));
			multiStatus.add(EcucUtil.validateUpperMultiplicity(ctx, numberOfParameters, currentConfigParameter));
		}
		return multiStatus;
	}
}
