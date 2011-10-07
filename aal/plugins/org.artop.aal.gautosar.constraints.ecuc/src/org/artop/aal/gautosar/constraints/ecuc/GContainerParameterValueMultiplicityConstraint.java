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
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Superclass for the constraints implementations on the multiplicity of parameter values of a container.
 */
public class GContainerParameterValueMultiplicityConstraint extends AbstractModelConstraintWithPreconditionAndIndex {
	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		boolean isApplicable = false;
		if (ctx.getTarget() instanceof GContainer) {
			GContainer gContainer = (GContainer) ctx.getTarget();
			GContainerDef gContainerDef = gContainer.gGetDefinition();
			isApplicable = null != gContainerDef && false == gContainerDef.eIsProxy() && gContainerDef instanceof GParamConfContainerDef;
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {

		GContainer gContainer = (GContainer) ctx.getTarget();
		GParamConfContainerDef gParamConfContainerDef = (GParamConfContainerDef) gContainer.gGetDefinition();

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		List<GParameterValue> allGParameterValues = getEcucValidationIndex(ctx).getAllParameterValuesOf(gContainer);
		List<GConfigParameter> gConfigParameters = gParamConfContainerDef.gGetParameters();
		for (GConfigParameter currentConfigParameter : gConfigParameters) {

			int numberOfParameters = EcucUtil.filterParameterValuesByDefinition(allGParameterValues, currentConfigParameter).size();
			if (!EcucUtil.isValidLowerMultiplicity(numberOfParameters, currentConfigParameter)) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_minElementsExpected,
						new Object[] { EcucUtil.getLowerMultiplicity(currentConfigParameter), "parameter values", //$NON-NLS-1$
								AutosarURIFactory.getAbsoluteQualifiedName(currentConfigParameter), numberOfParameters })));
			}
			if (!EcucUtil.isValidUpperMultiplicity(numberOfParameters, currentConfigParameter)) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_maxElementsExpected,
						new Object[] { EcucUtil.getUpperMultiplicity(currentConfigParameter), "parameter values", //$NON-NLS-1$
								AutosarURIFactory.getAbsoluteQualifiedName(currentConfigParameter), numberOfParameters })));
			}
		}
		return multiStatus;
	}

}
