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

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfMultiplicity;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;

public class EcucUtil {

	public static int getNumberOfUniqueShortNames(List<GIdentifiable> identifiables) {
		Set<String> shortNames = new HashSet<String>();
		for (GIdentifiable gIdentifiable : identifiables) {
			shortNames.add(gIdentifiable.gGetShortName());
		}
		return shortNames.size();
	}

	public static int getNumberOfUniqueContainersByDefinition(List<GContainer> containers, GContainerDef gContainerDef)
			throws IllegalArgumentException {
		final int numberOfUniqueContainersByDefinition;

		if (null == containers || null == gContainerDef) {
			throw new IllegalArgumentException(Messages.generic_nullParametersException);
		} else {
			Set<String> uniqueContainers = new HashSet<String>();
			for (GContainer currentContainer : containers) {
				if (gContainerDef.equals(currentContainer.gGetDefinition())) {
					uniqueContainers.add(currentContainer.gGetShortName());
				}
			}
			numberOfUniqueContainersByDefinition = uniqueContainers.size();
		}
		return numberOfUniqueContainersByDefinition;
	}

	public static List<GParameterValue> filterParameterValuesByDefinition(List<GParameterValue> gParameterValues, GConfigParameter gConfigParameter)
			throws IllegalArgumentException {
		List<GParameterValue> filteredParameterValues = new ArrayList<GParameterValue>();

		if (null == gParameterValues || null == gConfigParameter) {
			throw new IllegalArgumentException(Messages.generic_nullParametersException);
		} else {
			for (GParameterValue currentParameterValue : gParameterValues) {
				if (gConfigParameter.equals(currentParameterValue.gGetDefinition())) {
					filteredParameterValues.add(currentParameterValue);
				}
			}
		}
		return filteredParameterValues;
	}

	public static List<GConfigReferenceValue> filterConfigReferenceValuesByDefinition(List<GConfigReferenceValue> gConfigReferenceValues,
			GConfigReference gConfigReference) throws IllegalArgumentException {
		List<GConfigReferenceValue> filteredConfigReferenceValues = new ArrayList<GConfigReferenceValue>();

		if (null == gConfigReferenceValues || null == gConfigReference) {
			throw new IllegalArgumentException(Messages.generic_nullParametersException);
		} else {
			for (GConfigReferenceValue currentConfigReferenceValue : gConfigReferenceValues) {
				if (gConfigReference.equals(currentConfigReferenceValue.gGetDefinition())) {
					filteredConfigReferenceValues.add(currentConfigReferenceValue);
				}
			}
		}
		return filteredConfigReferenceValues;
	}

	public static boolean isValidLowerMultiplicity(int numberOfObjects, GParamConfMultiplicity gParamConfMultiplicity) {

		// by default the multiplicity is 1
		int lowerMultiplicity = 1;
		try {
			if (gParamConfMultiplicity.gGetLowerMultiplicityAsString() != null) {
				lowerMultiplicity = Integer.parseInt(gParamConfMultiplicity.gGetLowerMultiplicityAsString());
			}
		} catch (NumberFormatException nfe) {
			// GContainerDef is currupt. that problem need to be reported by another constraint
		}

		return lowerMultiplicity <= numberOfObjects;
	}

	public static String getLowerMultiplicity(GParamConfMultiplicity gParamConfMultiplicity) {
		final String lowerMultiplicity;
		if (gParamConfMultiplicity.gGetLowerMultiplicityAsString() != null) {
			lowerMultiplicity = gParamConfMultiplicity.gGetLowerMultiplicityAsString();
		} else {
			lowerMultiplicity = "1";
		}
		return lowerMultiplicity;
	}

	public static String getUpperMultiplicity(GParamConfMultiplicity gParamConfMultiplicity) {
		final String upperMultiplicity;
		if (gParamConfMultiplicity.gGetUpperMultiplicityAsString() != null) {
			upperMultiplicity = gParamConfMultiplicity.gGetUpperMultiplicityAsString();
		} else {
			upperMultiplicity = "1";
		}
		return upperMultiplicity;
	}

	public static boolean isValidUpperMultiplicity(int numberOfObjects, GParamConfMultiplicity gParamConfMultiplicity) {
		final boolean isValidUpperMultiplicity;
		if ("*".equals(gParamConfMultiplicity.gGetUpperMultiplicityAsString())) {
			isValidUpperMultiplicity = true;
		} else {
			int upperMultiplicity = 1;
			try {
				if (gParamConfMultiplicity.gGetUpperMultiplicityAsString() != null) {
					upperMultiplicity = Integer.parseInt(gParamConfMultiplicity.gGetUpperMultiplicityAsString());
				}
			} catch (NumberFormatException nfe) {
				// GContainerDef is currupt. that problem need to be reported by another constraint
			}

			isValidUpperMultiplicity = numberOfObjects <= upperMultiplicity;
		}
		return isValidUpperMultiplicity;
	}
}
