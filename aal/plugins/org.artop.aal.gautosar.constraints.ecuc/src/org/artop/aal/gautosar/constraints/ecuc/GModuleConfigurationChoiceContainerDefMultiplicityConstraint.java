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

import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Class for the constraint implementation on an <em>GModuleConfiguration</em> ChoiceContainerDef multiplicity
 */
public class GModuleConfigurationChoiceContainerDefMultiplicityConstraint extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GModuleConfiguration;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		IStatus status = ctx.createSuccessStatus();
		GModuleConfiguration moduleConfiguration = (GModuleConfiguration) ctx.getTarget();

		GModuleDef moduleDef = null;
		moduleDef = moduleConfiguration.gGetDefinition();

		// Check if we have a definition linked to this configuration
		if (moduleDef == null) { // Nothing to do
			return status;
		}

		// Let's obtain all the ChoiceContainerDef
		Map<GChoiceContainerDef, Integer> mchCDefs = new HashMap<GChoiceContainerDef, Integer>();

		for (GContainerDef current : moduleDef.gGetContainers()) {
			if (current instanceof GChoiceContainerDef) {
				mchCDefs.put((GChoiceContainerDef) current, Integer.valueOf(0));
			}
		}

		if (mchCDefs.keySet().size() < 1) { // Nothing to check
			return status;
		}

		GParamConfContainerDef p = null;
		for (GChoiceContainerDef currentCCDef : mchCDefs.keySet()) {
			int i = 0;
			for (GContainer container : moduleConfiguration.gGetContainers()) {
				p = (GParamConfContainerDef) container.gGetDefinition();
				if (p != null && currentCCDef.gGetChoices().contains(p)) {
					i = mchCDefs.get(currentCCDef).intValue();
					mchCDefs.put(currentCCDef, ++i);
				}
			}
		}

		// Let's iterate on all choiceContainer found to check if
		// the multiplicity is respected.
		List<String> ccPb = new ArrayList<String>();
		for (GChoiceContainerDef currentCCDef : mchCDefs.keySet()) {
			try {
				if (mchCDefs.get(currentCCDef).compareTo(Integer.valueOf(currentCCDef.gGetLowerMultiplicityAsString())) < 0
						|| mchCDefs.get(currentCCDef).compareTo(Integer.valueOf(currentCCDef.gGetUpperMultiplicityAsString())) > 0) {
					ccPb.add(currentCCDef.gGetShortName());
				}
			} catch (NumberFormatException e) {
				// ignore this choice container def
			}
		}

		switch (ccPb.size()) {
		case 0: // No error
			break;
		case 1:
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.choiceContainerDef_multiplicityNotRespected, new Object[] { ccPb.get(0),
					" is" })); //$NON-NLS-1$
		default: // size>1
			String l = new String();
			for (String str : ccPb) {
				l = l + str + ", "; //$NON-NLS-1$
			}
			return ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.choiceContainerDef_multiplicityNotRespected, new Object[] { ccPb.get(0),
					" are" })); //$NON-NLS-1$
		}

		return status;
	}

}
