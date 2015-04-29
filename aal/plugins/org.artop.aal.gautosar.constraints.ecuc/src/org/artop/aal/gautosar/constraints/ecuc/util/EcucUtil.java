/**
 * <copyright>
 *
 * Copyright (c) OpenSynergy, Continental Engineering Services, See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 *
 * Contributors:
 *     OpenSynergy - Initial API and implementation
 *     Continental Engineering Services - migration to gautosar
 *     See4sys -
 *
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc.util;

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GCommonConfigurationAttributes;
import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;
import gautosar.gecucparameterdef.GParamConfMultiplicity;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sphinx.platform.util.PlatformLogUtil;
import org.eclipse.sphinx.platform.util.RadixConverter;

public class EcucUtil {
	/**
	 * The constant used to represent a multiplicity with "0" as value.
	 */
	static final String MULTIPLICITY_ZERO = "0"; //$NON-NLS-1$

	/**
	 * The constant used to represent a multiplicity with "1" as value.
	 */
	public static final String MULTIPLICITY_ONE = "1"; //$NON-NLS-1$

	/**
	 * The constant used to represent an infinite multiplicity with "*" as value.
	 */
	static final String MULTIPLICITY_INFINITY = "*"; //$NON-NLS-1$

	/**
	 * The constant used to represent a "*" inside the upperMultiplicity field
	 */
	public static final BigInteger MULTIPLICITY_STAR_BIG_INTEGER = new BigInteger("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF", 16); //$NON-NLS-1$

	public static String getFeatureValue(EObject eObject, String featureName) {
		EStructuralFeature eFeature = eObject.eClass().getEStructuralFeature(featureName);

		if (eFeature == null) {
			return null;
		}

		Object featureValue = eObject.eGet(eFeature);
		if (featureValue == null) {
			return null;
		}

		return featureValue.toString();
	}

	public static GContainerDef getContainerDefInRefinedModuleDef(GContainerDef vSpecifContainerDef) {
		/*
		 * The ContainerDef corresponding to the given one in the refined ModuleDef. That is the Container Definition to
		 * return.
		 */
		GContainerDef refinedContainerDef = null;

		/*
		 * The Module Definition which is refined by the Module Definition containing the given Container Definition.
		 */
		GModuleDef refinedModuleDef = getParentRefinedModuleDef(vSpecifContainerDef);
		if (refinedModuleDef != null) {

			/* Initializes cache with the Container Definition objects contained in the Refined Module Definition. */
			GContainerDef[] cache = refinedModuleDef.gGetContainers().toArray(new GContainerDef[0]);

			for (GContainerDef vSpecifContDefAncestor : getAncestors(vSpecifContainerDef)) {
				for (GContainerDef refinedContDefAncestor : cache) {
					if (refinedContDefAncestor.gGetShortName().equals(vSpecifContDefAncestor.gGetShortName())) {
						refinedContainerDef = refinedContDefAncestor;
						if (refinedContDefAncestor instanceof GParamConfContainerDef) {
							cache = ((GParamConfContainerDef) refinedContDefAncestor).gGetSubContainers().toArray(new GContainerDef[0]);
						} else if (refinedContDefAncestor instanceof GChoiceContainerDef) {
							cache = ((GChoiceContainerDef) refinedContDefAncestor).gGetChoices().toArray(new GContainerDef[0]);
						} else {
							cache = null;
						}
						break;
					}
				}
				if (cache == null) {
					break;
				}
			}
		}

		return refinedContainerDef;
	}

	public static GConfigParameter getConfigParameterInRefinedModuleDef(GConfigParameter configParameter) {
		/*
		 * The ConfigParameter corresponding to the given one in the refined ModuleDef.
		 */
		GConfigParameter refinedConfigParameter = null;

		/*
		 * The Module Definition which is refined by the Module Definition containing the given Configuration Parameter.
		 */
		GModuleDef refinedModuleDef = getParentRefinedModuleDef(configParameter);

		if (refinedModuleDef != null) {

			GContainerDef refinedContainerDef = null;

			GContainerDef[] tmpRefinedContainers = refinedModuleDef.gGetContainers().toArray(new GContainerDef[0]);

			for (GContainerDef containerDef : getAncestors(configParameter)) {
				for (GContainerDef rcd : tmpRefinedContainers) {
					refinedContainerDef = rcd;
					if (rcd.gGetShortName().equals(containerDef.gGetShortName())) {
						if (rcd instanceof GParamConfContainerDef) {
							GParamConfContainerDef pccd = (GParamConfContainerDef) rcd;
							tmpRefinedContainers = pccd.gGetSubContainers().toArray(new GContainerDef[0]);
							break;
						} else if (rcd instanceof GChoiceContainerDef) {
							GChoiceContainerDef ccd = (GChoiceContainerDef) rcd;
							tmpRefinedContainers = ccd.gGetChoices().toArray(new GContainerDef[0]);
							break;
						}

					}
				}
			}

			if (refinedContainerDef != null && refinedContainerDef instanceof GParamConfContainerDef) {
				for (GConfigParameter refinedConfigParam : ((GParamConfContainerDef) refinedContainerDef).gGetParameters()) {
					if (refinedConfigParam.gGetShortName().equals(configParameter.gGetShortName())) {
						refinedConfigParameter = refinedConfigParam;
						break;
					}
				}
			}
		}

		return refinedConfigParameter;
	}

	public static GModuleDef getParentRefinedModuleDef(GContainerDef containerDef) {
		/*
		 * The Module Definition containing the Container Definition being validated.
		 */
		GModuleDef moduleDef = getParentModuleDefForContainerDef(containerDef);

		/*
		 * The Module Definition which is refined by the current Module Definition.
		 */
		GModuleDef refinedModuleDef = moduleDef.gGetRefinedModuleDef();

		/*
		 * Log a warning if the Refined Module Definition is the Module Definition itself.
		 */
		if (moduleDef.equals(refinedModuleDef)) {
			String m = "ModuleDef \"" + AutosarURIFactory.getAbsoluteQualifiedName(moduleDef) + "\" references itself as 'Refined Module Definition'"; //$NON-NLS-1$ //$NON-NLS-2$
			PlatformLogUtil.logAsWarning(Activator.getDefault(), m);
		}

		return refinedModuleDef;
	}

	public static GModuleDef getParentRefinedModuleDef(GConfigParameter configParameter) {
		/*
		 * The Module Definition containing the Configuration Parameter being validated.
		 */
		GModuleDef moduleDef = getParentModuleDef(configParameter);

		/*
		 * The Module Definition which is refined by the current Module Definition.
		 */
		GModuleDef refinedModuleDef = moduleDef.gGetRefinedModuleDef();

		/*
		 * Log a warning if the Refined Module Definition is the Module Definition itself.
		 */
		if (moduleDef.equals(refinedModuleDef)) {
			PlatformLogUtil.logAsWarning(Activator.getDefault(), NLS.bind(EcucConstraintMessages.moduleDef_selfReference, moduleDef.gGetShortName()));
		}

		return refinedModuleDef;
	}

	public static GModuleDef getParentModuleDef(GConfigParameter configAttr) {

		return getParentModuleDef((GCommonConfigurationAttributes) configAttr);
	}

	public static GModuleDef getParentModuleDef(GConfigReference configAttr) {

		return getParentModuleDef((GCommonConfigurationAttributes) configAttr);
	}

	private static GModuleDef getParentModuleDef(GCommonConfigurationAttributes configAttr) {
		/* Configuration Attribute ancestors hierarchy. */
		ArrayList<GContainerDef> ancestors = getAncestors(configAttr);
		if (ancestors.isEmpty()) {
			throw new RuntimeException(NLS.bind(EcucConstraintMessages.configParameter_ancestorEmptylist, configAttr.gGetShortName()));
		}
		return (GModuleDef) ancestors.get(0).eContainer();
	}

	public static GModuleDef getParentModuleDefForContainerDef(GContainerDef containerDef) {
		EObject parent = containerDef.eContainer();
		while (!(parent instanceof GModuleDef) && parent != null) {
			parent = parent.eContainer();
		}
		if (parent instanceof GModuleDef) {
			return (GModuleDef) parent;
		}
		return null;
	}

	public static ArrayList<GContainerDef> getAncestors(GConfigParameter configAttr) {

		return getAncestors((GCommonConfigurationAttributes) configAttr);
	}

	public static ArrayList<GContainerDef> getAncestors(GConfigReference configAttr) {

		return getAncestors((GCommonConfigurationAttributes) configAttr);
	}

	private static ArrayList<GContainerDef> getAncestors(GCommonConfigurationAttributes configAttr) {
		ArrayList<GContainerDef> ancestors = new ArrayList<GContainerDef>();

		EObject eContainer = configAttr.eContainer();
		while (eContainer != null && eContainer instanceof GContainerDef) {
			ancestors.add(0, (GContainerDef) eContainer);
			eContainer = eContainer.eContainer();
		}
		;

		return ancestors;
	}

	public static ArrayList<GContainerDef> getAncestors(GContainerDef containerDef) {
		ArrayList<GContainerDef> ancestors = new ArrayList<GContainerDef>();

		ancestors.add(containerDef);

		EObject eContainer = containerDef.eContainer();
		while (eContainer != null && eContainer instanceof GContainerDef) {
			ancestors.add(0, (GContainerDef) eContainer);
			eContainer = eContainer.eContainer();
		}
		return ancestors;
	}

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
			throw new IllegalArgumentException(EcucConstraintMessages.generic_nullParametersException);
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

	public static ArrayList<GIdentifiable> getIdentifiableAncestors(GIdentifiable eObject) {
		ArrayList<GIdentifiable> ancestors = new ArrayList<GIdentifiable>();

		ancestors.add(eObject);
		EObject eContainer = eObject.eContainer();
		while (eContainer != null && (eContainer instanceof GCommonConfigurationAttributes || eContainer instanceof GContainerDef)) {
			ancestors.add(0, (GIdentifiable) eContainer);
			eContainer = eContainer.eContainer();
		}
		return ancestors;
	}

	public static GConfigReference getFromRefined(GConfigReference vSpecif) {
		return (GConfigReference) getFromRefined((GCommonConfigurationAttributes) vSpecif);
	}

	public static GConfigParameter getFromRefined(GConfigParameter vSpecif) {
		return (GConfigParameter) getFromRefined((GCommonConfigurationAttributes) vSpecif);
	}

	private static GCommonConfigurationAttributes getFromRefined(GCommonConfigurationAttributes vSpecif) {

		/* The object form the Refined side to return. */
		EObject refined = null;

		/* The parent Vendor Specific Module Definition. */
		GModuleDef vSpecifModuleDef = getParentModuleDef(vSpecif);

		/* The Refined Module Definition. */
		GModuleDef refinedModuleDef = vSpecifModuleDef.gGetRefinedModuleDef();

		if (refinedModuleDef != null) {
			/* Initializes cache with the Container Definition objects contained in the Refined Module Definition. */
			for (GIdentifiable ancestor : getIdentifiableAncestors(vSpecif)) {
				GIdentifiable[] cache = null;
				if (ancestor instanceof GContainerDef) {
					if (refined == null) {
						cache = refinedModuleDef.gGetContainers().toArray(new GContainerDef[0]);
					} else if (refined instanceof GParamConfContainerDef) {
						cache = ((GParamConfContainerDef) refined).gGetSubContainers().toArray(new GContainerDef[0]);
					} else if (refined instanceof GChoiceContainerDef) {
						cache = ((GChoiceContainerDef) refined).gGetChoices().toArray(new GContainerDef[0]);
					} else {
						cache = null;
					}
				} else if (ancestor instanceof GCommonConfigurationAttributes) {
					if (refined instanceof GParamConfContainerDef) {
						if (ancestor instanceof GConfigParameter) {
							cache = ((GParamConfContainerDef) refined).gGetParameters().toArray(new GConfigParameter[0]);
						} else if (ancestor instanceof GConfigReference) {
							cache = ((GParamConfContainerDef) refined).gGetReferences().toArray(new GConfigReference[0]);
						} else {
							cache = null;
						}
					}
				}

				boolean matchFound = false;
				String vSpecifShortName = ancestor.gGetShortName();
				for (EObject retrieved : cache) {
					String refinedShortName = ((GIdentifiable) retrieved).gGetShortName();
					if (refinedShortName.equals(vSpecifShortName)) {
						refined = retrieved;
						matchFound = true;
						break;
					}
				}
				if (!matchFound) {
					refined = null;
					cache = null;
				}
				if (cache == null) {
					break;
				}
			}
		} else {
			//
			// Refined Module Definition is null; the parent Module Definition is a Vendor Specific.
			// Does nothing more but return.
			//
		}
		return (GCommonConfigurationAttributes) refined;

	}

	public static boolean validateLower(GParamConfMultiplicity refinedConfMultiplicity, GParamConfMultiplicity vSpecifConfMultiplicity) {
		/* Flag used to mark the lower multiplicity as valid or not. */
		boolean valid = true;

		/*
		 * Lower multiplicity of the Common Configuration Attribute from the Refined Module Definition side.
		 */
		String refinedLowerMultiplicity = refinedConfMultiplicity.gGetLowerMultiplicityAsString();

		/*
		 * Lower multiplicity of the Common Configuration Attribute from the Vendor Specific Module Definition side.
		 */
		String vSpecifLowerMultiplicity = vSpecifConfMultiplicity.gGetLowerMultiplicityAsString();

		if (refinedLowerMultiplicity != null && refinedLowerMultiplicity.length() > 0 && vSpecifLowerMultiplicity != null
				&& vSpecifLowerMultiplicity.length() > 0) {
			if (!refinedLowerMultiplicity.equals(MULTIPLICITY_INFINITY)) {
				if (vSpecifLowerMultiplicity.equals(MULTIPLICITY_INFINITY)) {
					valid = false;
				} else {
					if (Integer.valueOf(vSpecifLowerMultiplicity) < Integer.valueOf(refinedLowerMultiplicity)) {
						valid = false;
					}
				}
			}
		}
		return valid;
	}

	public static String[] vendorSpecificCommonConfigurationAttributesLowerMultiplicity(GCommonConfigurationAttributes current) {
		/* Parent of the Common Configuration Attribute. */
		GParamConfContainerDef vSpecifParentParamConfContainerDef = null;
		EObject container = current.eContainer();

		if (container instanceof GParamConfContainerDef) {
			vSpecifParentParamConfContainerDef = (GParamConfContainerDef) container;
		}

		if (vSpecifParentParamConfContainerDef == null) {
			return null;
		}

		/* Retrieves the parent Module Definition. */
		GModuleDef vSpecifModuleDef = getParentModuleDefForContainerDef(vSpecifParentParamConfContainerDef);

		/* Try to get the Refined Module Definition. */
		GModuleDef refinedModuleDef = vSpecifModuleDef.gGetRefinedModuleDef();

		/*
		 * If Refined Module Definition is not null, the target is a Common Configuration Attribute from the Vendor
		 * Specific side.
		 */
		if (refinedModuleDef != null) {
			//
			// Retrieves the Container Definition from the Refined side corresponding to the given Container
			// Definition from the Vendor Specific side.
			//
			GContainerDef refinedParentParamConfContainerDef = findContainerDefInModuleDef(refinedModuleDef, vSpecifParentParamConfContainerDef);

			if (refinedParentParamConfContainerDef != null) {
				if (!(refinedParentParamConfContainerDef instanceof GParamConfContainerDef)) {
					return null;
				}

				GParamConfContainerDef def = (GParamConfContainerDef) refinedParentParamConfContainerDef;

				GCommonConfigurationAttributes[] refinedCommonConfigurationAttributes = null;
				if (current instanceof GConfigParameter) {
					refinedCommonConfigurationAttributes = def.gGetParameters().toArray(new GCommonConfigurationAttributes[0]);
				} else if (current instanceof GConfigReference) {
					refinedCommonConfigurationAttributes = def.gGetReferences().toArray(new GCommonConfigurationAttributes[0]);
				}

				if (refinedCommonConfigurationAttributes == null) {
					return null;
				}

				GCommonConfigurationAttributes refinedCommonConfigAtt = (GCommonConfigurationAttributes) find(current.gGetShortName(),
						refinedCommonConfigurationAttributes);

				if (refinedCommonConfigAtt instanceof GParamConfMultiplicity && current instanceof GParamConfMultiplicity) {
					//
					// Perform the comparison between the two lower multiplicity of Common Configuration Attributes.
					//
					if (!validateLower((GParamConfMultiplicity) refinedCommonConfigAtt, (GParamConfMultiplicity) current)) {
						return new String[] { AutosarURIFactory.getAbsoluteQualifiedName(current),
								AutosarURIFactory.getAbsoluteQualifiedName(refinedModuleDef) };
					}
				} else {
					//
					// Refined Common Configuration Attribute not retrieved; does nothing more.
					//
				}
			} else {
				//
				// Refined parent Parameter Configuration Container Definition is null.
				// Does nothing more.
				//
			}
		} else {
			//
			// Refined Module Definition is null; it means the target is not from the Vendor Specific side.
			// Does nothing more.
			//
		}

		return null;
	}

	public static String[] vendorSpecificCommonConfigurationAttributesUpperMultiplicity(GCommonConfigurationAttributes configAttributes) {
		/* Parent of the Common Configuration Attribute. */
		GParamConfContainerDef vSpecifParentParamConfContainerDef = null;
		EObject container = configAttributes.eContainer();

		if (container instanceof GParamConfContainerDef) {
			vSpecifParentParamConfContainerDef = (GParamConfContainerDef) container;
		}

		if (vSpecifParentParamConfContainerDef == null) {
			return null;
		}

		/* Retrieves the parent Module Definition. */
		GModuleDef vSpecifModuleDef = EcucUtil.getParentModuleDefForContainerDef(vSpecifParentParamConfContainerDef);

		/* Try to get the Refined Module Definition. */
		GModuleDef refinedModuleDef = vSpecifModuleDef.gGetRefinedModuleDef();

		/*
		 * If Refined Module Definition is not null, the target is a Common Configuration Attribute from the Vendor
		 * Specific side.
		 */
		if (refinedModuleDef != null) {
			//
			// Retrieves the Container Definition from the Refined side corresponding to the given Container
			// Definition from the Vendor Specific side.
			//
			GContainerDef refinedParentParamConfContainerDef = EcucUtil.findContainerDefInModuleDef(refinedModuleDef,
					vSpecifParentParamConfContainerDef);

			if (refinedParentParamConfContainerDef != null) {
				if (!(refinedParentParamConfContainerDef instanceof GParamConfContainerDef)) {
					return null;
				}

				GParamConfContainerDef def = (GParamConfContainerDef) refinedParentParamConfContainerDef;

				GCommonConfigurationAttributes[] refinedCommonConfigurationAttributes = null;
				if (configAttributes instanceof GConfigParameter) {
					refinedCommonConfigurationAttributes = def.gGetParameters().toArray(new GCommonConfigurationAttributes[0]);
				} else if (configAttributes instanceof GConfigReference) {
					refinedCommonConfigurationAttributes = def.gGetReferences().toArray(new GCommonConfigurationAttributes[0]);
				}

				if (refinedCommonConfigurationAttributes == null) {
					return null;
				}

				GCommonConfigurationAttributes refinedCommonConfigAtt = (GCommonConfigurationAttributes) EcucUtil.find(
						configAttributes.gGetShortName(), refinedCommonConfigurationAttributes);

				if (refinedCommonConfigAtt != null) {
					//
					// Perform the comparison between the two upper multiplicity of Common Configuration Attributes.
					//
					if (!validateUpper((GParamConfMultiplicity) refinedCommonConfigAtt, (GParamConfMultiplicity) configAttributes)) {
						return new String[] { AutosarURIFactory.getAbsoluteQualifiedName(configAttributes),
								AutosarURIFactory.getAbsoluteQualifiedName(refinedModuleDef) };
					}
				}
			} else {
				//
				// Refined parent Parameter Configuration Container Definition is null.
				// Does nothing more.
				//
			}
		} else {
			//
			// Refined Module Definition is null; it means the target is not from the Vendor Specific side.
			// Does nothing more.
			//
		}
		return null;
	}

	/**
	 * Validate the upper multiplicity consistency for the given Parameter Configuration Multiplicity. In the Vendor
	 * Specific side, upper multiplicity must be smaller or equal to upper multiplicity from the Refined side.
	 *
	 * @param refinedConfMultiplicity
	 *            The Refined one.
	 * @param vSpecifConfMultiplicity
	 *            The Vendor Specific one.
	 * @return <ul>
	 *         <li><b><tt>true&nbsp;&nbsp;</tt></b> if upper multiplicity from the Vendor Specific side is not greater
	 *         than one in Refined side;</li>
	 *         <li><b><tt>false&nbsp;</tt></b> otherwise.</li>
	 *         </ul>
	 */
	public static boolean validateUpper(GParamConfMultiplicity refinedConfMultiplicity, GParamConfMultiplicity vSpecifConfMultiplicity) {

		/* Flag used to mark the upper multiplicity as valid or not. */
		boolean valid = true;

		// Upper multiplicity of the Common Configuration Attribute from the Refined Module Definition side.
		String refinedUpperMultiplicity = refinedConfMultiplicity.gGetUpperMultiplicityAsString();

		// Upper multiplicity of the Common Configuration Attribute from the Vendor Specific Module Definition side.
		String vSpecifUpperMultiplicity = vSpecifConfMultiplicity.gGetUpperMultiplicityAsString();

		if (refinedUpperMultiplicity != null && refinedUpperMultiplicity.length() > 0 && vSpecifUpperMultiplicity != null
				&& vSpecifUpperMultiplicity.length() > 0) {
			if (refinedConfMultiplicity.gGetUpperMultiplicityInfinite()) {
				if (vSpecifUpperMultiplicity.equals(MULTIPLICITY_ZERO)) {
					valid = false;
				}
			} else {
				if (vSpecifConfMultiplicity.gGetUpperMultiplicityInfinite()) {
					valid = false;
				} else if (Integer.valueOf(vSpecifUpperMultiplicity) > Integer.valueOf(refinedUpperMultiplicity)) {
					valid = false;
				}
			}
		}
		return valid;
	}

	public static List<GParameterValue> filterParameterValuesByDefinition(List<GParameterValue> gParameterValues, GConfigParameter gConfigParameter)
			throws IllegalArgumentException {
		List<GParameterValue> filteredParameterValues = new ArrayList<GParameterValue>();

		if (null == gParameterValues || null == gConfigParameter) {
			throw new IllegalArgumentException(EcucConstraintMessages.generic_nullParametersException);
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
			throw new IllegalArgumentException(EcucConstraintMessages.generic_nullParametersException);
		} else {
			for (GConfigReferenceValue currentConfigReferenceValue : gConfigReferenceValues) {
				if (gConfigReference.equals(currentConfigReferenceValue.gGetDefinition())) {
					filteredConfigReferenceValues.add(currentConfigReferenceValue);
				}
			}
		}
		return filteredConfigReferenceValues;
	}

	public static List<GContainer> filterChoiceContainersByDefinition(GChoiceContainerDef choiceContainerDef, GModuleConfiguration moduleConfiguration) {
		List<GContainer> choiceContainers = new ArrayList<GContainer>();

		for (GContainer containerConf : moduleConfiguration.gGetContainers()) {
			GContainerDef containerDef = containerConf.gGetDefinition();
			if (containerDef != null) {
				if (containerDef instanceof GChoiceContainerDef) {
					for (GContainer containerChoice : containerConf.gGetSubContainers()) {
						if (choiceContainerDef.gGetChoices().contains(containerChoice.gGetDefinition())) {
							choiceContainers.add(containerChoice);
						}
					}
				} else if (choiceContainerDef.gGetChoices().contains(containerDef)) {
					choiceContainers.add(containerConf);
				}
			}
		}
		return choiceContainers;
	}

	public static ArrayList<GContainerDef> getContainerDefAncestors(GContainerDef containerDef) {
		ArrayList<GContainerDef> ancestors = new ArrayList<GContainerDef>();
		ancestors.add(containerDef);
		EObject eContainer = containerDef.eContainer();
		while (eContainer != null && eContainer instanceof GContainerDef) {
			ancestors.add(0, (GContainerDef) eContainer);
			eContainer = eContainer.eContainer();
		}
		return ancestors;
	}

	public static GContainerDef findContainerDefInModuleDef(GModuleDef moduleDef, GContainerDef containerDef) {
		GContainerDef containerDefRetrieved = null;

		/* Initializes cache with the Container Definition objects contained in the Refined Module Definition. */
		GContainerDef[] cache = moduleDef.gGetContainers().toArray(new GContainerDef[0]);

		for (GContainerDef containerDefAncestor : getContainerDefAncestors(containerDef)) {
			if (cache.length == 0) {
				containerDefRetrieved = null;
				break;
			}
			for (GContainerDef containerDefAncestorRetrieved : cache) {
				String containerDefAncestorShortNameRetrieved = containerDefAncestorRetrieved.gGetShortName();
				String containerDefAncestorShortName = containerDefAncestor.gGetShortName();
				if (containerDefAncestorShortNameRetrieved.equals(containerDefAncestorShortName)) {
					containerDefRetrieved = containerDefAncestorRetrieved;
					if (containerDefAncestorRetrieved instanceof GParamConfContainerDef) {
						cache = ((GParamConfContainerDef) containerDefAncestorRetrieved).gGetSubContainers().toArray(new GContainerDef[0]);
					} else if (containerDefAncestorRetrieved instanceof GChoiceContainerDef) {
						cache = ((GChoiceContainerDef) containerDefAncestorRetrieved).gGetChoices().toArray(new GContainerDef[0]);
					} else {
						cache = null;
					}
					break;
				}
			}
			if (cache == null) {
				break;
			}
		}

		// fix - instead of retrieving the ancestor, retrieve the GContainerDef if there is a match (null otherwise)
		if (containerDefRetrieved != null) {
			if (containerDefRetrieved.gGetShortName().equals(containerDef.gGetShortName())) {
				return containerDefRetrieved;
			} else {
				EList<EObject> tContents = containerDefRetrieved.eContents();
				if (tContents != null) {
					for (EObject eObject : tContents) {
						if (eObject instanceof GIdentifiable && ((GIdentifiable) eObject).gGetShortName().equals(containerDef.gGetShortName())) {
							return (GContainerDef) eObject;
						}
					}
				}
			}
		} else {
			return null;
		}
		return null;
	}

	public static EObject find(String shortName, EObject[] eObjects) {
		Assert.isNotNull(shortName);
		Assert.isNotNull(eObjects);

		for (EObject eObject : eObjects) {
			if (eObject instanceof GIdentifiable && shortName.equals(((GIdentifiable) eObject).gGetShortName())) {
				return eObject;
			}
		}
		return null;
	}

	public static String[] inspectContainersSub(EList<GContainerDef> refinedContainers, EList<GContainerDef> vSpecifContainers) {
		/* List of invalid config parameters */
		List<String> failures = new ArrayList<String>();

		/*
		 * [ecuc_sws_6007] Elements defined in the StMD must be present in the VSMDand must not be omitted...
		 */
		checkRefinedVendorDiffs(refinedContainers, vSpecifContainers, failures);

		for (GContainerDef refinedContainerDef : refinedContainers) {
			/* Retrieves the Container Definition with the specified short name from the Vendor Specific side. */
			EObject vSpecifContainerDef = find(refinedContainerDef.gGetShortName(), vSpecifContainers.toArray(new EObject[0]));

			if (vSpecifContainerDef == null) {
				/*
				 * 'Container Definition' not found in 'Vendor Specific'. If the current 'Refined Container Definition'
				 * has a non-zero lower multiplicity, the current 'Vendor Specific Container Definition' is marked as
				 * missing in 'Vendor Specific Module Definition'.
				 */
				String lowerMultiplicity = refinedContainerDef.gGetLowerMultiplicityAsString();
				if (lowerMultiplicity != null && !"".equals(lowerMultiplicity) && !"0".equals(lowerMultiplicity)) { //$NON-NLS-1$ //$NON-NLS-2$
					String commonConfAttShortName = refinedContainerDef.gGetShortName();
					failures.add(commonConfAttShortName);
				}

			} else {
				// Container Definition has been found in the Vendor Specific side. Just verifies it is really an
				// instance of ContainerDef and continue.
				// apiContainerDef.assertInstanceOfContainerDef(vSpecifContainerDef);
			}
		}

		return failures.toArray(new String[0]);
	}

	/**
	 * @param refinedContainers
	 * @param vSpecifContainers
	 * @param failures
	 */
	private static void checkRefinedVendorDiffs(EList<GContainerDef> refinedContainers, EList<GContainerDef> vSpecifContainers, List<String> failures) {
		if (vSpecifContainers.size() < refinedContainers.size()) {
			List<GContainerDef> deltaList = new ArrayList<GContainerDef>();
			for (GContainerDef rContainerDef : refinedContainers) {
				boolean isInList = false;
				for (GContainerDef vContainerDef : vSpecifContainers) {
					if (rContainerDef.gGetShortName().equals(vContainerDef.gGetShortName())) {
						isInList = true;
					}
				}
				if (!isInList) {
					deltaList.add(rContainerDef);
				}
			}
			if (!deltaList.isEmpty()) {
				for (GContainerDef gContainerDef : deltaList) {
					failures.add(gContainerDef.gGetShortName());
				}
			}
		}
	}

	public static String[] inspectContainersChoice(EList<GParamConfContainerDef> refinedContainers, EList<GParamConfContainerDef> vSpecifContainers) {
		/* List of invalid config parameters */
		List<String> failures = new ArrayList<String>();

		/*
		 * [ecuc_sws_6007] Elements defined in the StMD must be present in the VSMD and must not be omitted, even if the
		 * upperMultiplicity of an element in the VSMD is set to 0 - loosen up the initial implementation
		 */

		for (GContainerDef refinedContainerDef : refinedContainers) {

			/* Retrieves the Container Definition with the specified short name from the Vendor Specific side. */
			EObject vSpecifContainerDef = find(refinedContainerDef.gGetShortName(), vSpecifContainers.toArray(new EObject[0]));

			if (vSpecifContainerDef == null) {

				String commonConfAttShortName = refinedContainerDef.gGetShortName();
				failures.add(commonConfAttShortName);
			}
		}

		return failures.toArray(new String[0]);
	}

	public static String[] inspectCommonConfigurationParameter(EList<GConfigParameter> refinedCommonConfigurationAttributes,
			EList<GConfigParameter> vSpecifCommonConfigurationAttributes) {
		/* List of invalid config parameters */
		List<String> failures = new ArrayList<String>();

		/*
		 * [ecuc_sws_6007] Elements defined in the StMD must be present in the VSMD and must not be omitted, even if the
		 * upperMultiplicity of an element in the VSMD is set to 0 - loosen up the initial implementation
		 */

		for (GConfigParameter refinedCommonConfAtt : refinedCommonConfigurationAttributes) {
			if (GCommonConfigurationAttributes.class.isInstance(refinedCommonConfAtt)) {
				/*
				 * Retrieves the Common Configuration Attributes with the specified short name from the Vendor Specific
				 * side.
				 */
				GConfigParameter vSpecifCommonConfAtt = (GConfigParameter) find(refinedCommonConfAtt.gGetShortName(),
						vSpecifCommonConfigurationAttributes.toArray(new EObject[0]));

				if (vSpecifCommonConfAtt == null) {
					String commonConfAttShortName = refinedCommonConfAtt.gGetShortName();
					failures.add(commonConfAttShortName);
				}
			}
		}
		return failures.toArray(new String[0]);
	}

	public static String[] inspectCommonConfigurationReference(EList<GConfigReference> refinedCommonConfigurationAttributes,
			EList<GConfigReference> vSpecifCommonConfigurationAttributes) {
		/* List of invalid config parameters */
		List<String> failures = new ArrayList<String>();

		/*
		 * [ecuc_sws_6007] Elements defined in the StMD must be present in the VSMD and must not be omitted, even if the
		 * upperMultiplicity of an element in the VSMD is set to 0 - loosen up the initial implementation
		 */
		for (GConfigReference refinedCommonConfAtt : refinedCommonConfigurationAttributes) {
			if (GCommonConfigurationAttributes.class.isInstance(refinedCommonConfAtt)) {
				/*
				 * Retrieves the Common Configuration Attributes with the specified short name from the Vendor Specific
				 * side.
				 */
				GConfigReference vSpecifCommonConfAtt = (GConfigReference) find(refinedCommonConfAtt.gGetShortName(),
						vSpecifCommonConfigurationAttributes.toArray(new EObject[0]));

				if (vSpecifCommonConfAtt == null) {

					String commonConfAttShortName = refinedCommonConfAtt.gGetShortName();
					failures.add(commonConfAttShortName);
				}
			}
		}
		return failures.toArray(new String[0]);
	}

	public static String getLowerMultiplicity(GParamConfMultiplicity gParamConfMultiplicity) {
		final String lowerMultiplicity;
		final String lowerMultiplicityAsString = gParamConfMultiplicity.gGetLowerMultiplicityAsString();

		if (lowerMultiplicityAsString == null || lowerMultiplicityAsString.equals("")) { //$NON-NLS-1$
			lowerMultiplicity = MULTIPLICITY_ONE;
		} else {
			lowerMultiplicity = gParamConfMultiplicity.gGetLowerMultiplicityAsString();
		}
		return lowerMultiplicity;
	}

	public static String getUpperMultiplicity(GParamConfMultiplicity gParamConfMultiplicity) {
		Assert.isNotNull(gParamConfMultiplicity);

		if (gParamConfMultiplicity.gGetUpperMultiplicityInfinite()) {
			return MULTIPLICITY_INFINITY;
		} else {
			BigInteger upperMultiplicity = convertMultiplicityAsBigInteger(gParamConfMultiplicity.gGetUpperMultiplicityAsString(), new BigInteger(
					MULTIPLICITY_ONE, 10));
			return upperMultiplicity.toString(10);
		}
	}

	public static boolean isValidLowerMultiplicity(int numberOfObjects, GParamConfMultiplicity gParamConfMultiplicity) {
		Assert.isNotNull(gParamConfMultiplicity);

		BigInteger lowerMultiplicity = convertMultiplicityAsBigInteger(gParamConfMultiplicity.gGetLowerMultiplicityAsString(), new BigInteger(
				MULTIPLICITY_ONE, 10));
		return numberOfObjects >= lowerMultiplicity.intValue();
	}

	public static boolean isValidUpperMultiplicity(int numberOfObjects, GParamConfMultiplicity gParamConfMultiplicity) {
		Assert.isNotNull(gParamConfMultiplicity);

		if (gParamConfMultiplicity.gGetUpperMultiplicityInfinite()) {
			return true;
		}
		BigInteger upperMultiplicity = convertMultiplicityAsBigInteger(gParamConfMultiplicity.gGetUpperMultiplicityAsString(), new BigInteger(
				MULTIPLICITY_ONE, 10));
		if (upperMultiplicity.compareTo(MULTIPLICITY_STAR_BIG_INTEGER) != 0) {
			return numberOfObjects <= upperMultiplicity.intValue();
		}
		return true;
	}

	/**
	 * Converts from the String representation of a BigInteger into a BigInteger. If <code>multiplicity</code> is
	 * <code>null</code> or cannot be converted, the given <code>defaultValue</code> is returned.
	 *
	 * @param multiplicity
	 *            String representation of a BigInteger
	 * @param defaultValue
	 *            value to be returned in case conversion is not possible
	 * @return converted value
	 */
	public static BigInteger convertMultiplicityAsBigInteger(String multiplicity, BigInteger defaultValue) {
		// multiplicity not set, return default
		//
		if (multiplicity == null) {
			return defaultValue;
		}

		// infinite
		//
		if (MULTIPLICITY_INFINITY.equals(multiplicity)) {
			return MULTIPLICITY_STAR_BIG_INTEGER;
		}

		int radix = RadixConverter.getRadix(multiplicity);
		if (radix != 0) {
			BigInteger converted;
			try {
				// try to convert
				converted = new BigInteger(multiplicity, radix);
			} catch (NumberFormatException e) {
				// invalid representation
				return defaultValue;
			}
			return converted;
		} else {
			return defaultValue;
		}
	}

	public static String enumeratorToString(Collection<? extends Enumerator> enumInstances) {
		Iterator<? extends Enumerator> enumInstancesIterator = enumInstances.iterator();
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("{"); //$NON-NLS-1$
		if (enumInstancesIterator.hasNext()) {
			stringBuilder.append(enumInstancesIterator.next().getName());
			while (enumInstancesIterator.hasNext()) {
				stringBuilder.append(","); //$NON-NLS-1$
				stringBuilder.append(enumInstancesIterator.next().getName());
			}
		}
		stringBuilder.append("}"); //$NON-NLS-1$

		return stringBuilder.toString();
	}

	/**
	 * This utility method return the first container of type className for a given EObject. We do not test the given
	 * EObject itself.
	 *
	 * @param eObject
	 * @param className
	 * @return the corresponding EObject, if it exists, null otherwise
	 */
	public static EObject getFirstContainerOfType(EObject eObject, String className) {
		Assert.isNotNull(eObject);
		Assert.isNotNull(className);

		EObject current = eObject.eContainer();
		while (current != null) {
			if (current.eClass().getName().compareTo(className) == 0) {
				return current;
			}
			current = current.eContainer();
		}
		return null;
	}

	/**
	 * Return the {@link GModuleDef} which aggregate the {@link GConfigParameter} cp
	 *
	 * @param cp
	 *            a GConfigParameter
	 * @return the reached {@link GModuleDef} if this one exists, null otherwise
	 */
	public static GModuleDef getModuleDef(GConfigParameter cp) {
		if (!(cp.eContainer() instanceof GContainerDef)) {
			return null; // NdSam: sometimes a configuration parameter is not owned by a ContainerDef but by an
			// IdentifiableExtensionsMapEntryImpl
		}

		GContainerDef cd = (GContainerDef) cp.eContainer();

		while (cd != null && cd.eContainer() instanceof GContainerDef) {
			cd = (GContainerDef) cd.eContainer();
		}

		return cd.eContainer() != null && cd.eContainer() instanceof GModuleDef ? (GModuleDef) cd.eContainer() : null;

	}
}
