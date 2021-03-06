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

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Superclass for the constraints implementations on the multiplicity of reference values of a container.
 */
public class GContainerReferenceValueMultiplicityConstraint extends AbstractSplitModelConstraintWithPreconditionAndIndex {
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

		if (gParamConfContainerDef != null) {
			// if the container has the multiplicity 0 then the references shall not be validated anymore
			if (gParamConfContainerDef.gGetUpperMultiplicityAsString() != null && "0".equals(gParamConfContainerDef.gGetUpperMultiplicityAsString())) //$NON-NLS-1$
			{
				multiStatus.add(ctx.createSuccessStatus());
				return multiStatus;
			}
		}

		List<GConfigReferenceValue> allGConfigReferenceValues = getEcucValidationIndex(ctx).getAllReferenceValuesOf(gContainer);
		List<GConfigReference> gConfigReferences = gParamConfContainerDef.gGetReferences();
		for (GConfigReference currentGConfigReference : gConfigReferences) {
			int numberOfConfigReferenceValues = EcucUtil.filterConfigReferenceValuesByDefinition(allGConfigReferenceValues, currentGConfigReference)
					.size();

			if (!EcucUtil.isValidLowerMultiplicity(numberOfConfigReferenceValues, currentGConfigReference)) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.multiplicity_minElementsExpected,
						new Object[] { EcucUtil.getLowerMultiplicity(currentGConfigReference), "config reference values", //$NON-NLS-1$
								AutosarURIFactory.getAbsoluteQualifiedName(currentGConfigReference), numberOfConfigReferenceValues })));
			}
			if (!EcucUtil.isValidUpperMultiplicity(numberOfConfigReferenceValues, currentGConfigReference)) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.multiplicity_maxElementsExpected,
						new Object[] { EcucUtil.getUpperMultiplicity(currentGConfigReference), "config reference values", //$NON-NLS-1$
								AutosarURIFactory.getAbsoluteQualifiedName(currentGConfigReference), numberOfConfigReferenceValues })));
			}
		}
		return multiStatus;
	}

}
