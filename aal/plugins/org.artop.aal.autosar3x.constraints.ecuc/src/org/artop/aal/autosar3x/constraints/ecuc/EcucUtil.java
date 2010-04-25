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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.ecl.emf.util.EObjectUtil;
import org.eclipse.emf.ecore.resource.Resource;

import autosar3x.ecucdescription.ConfigReferenceValue;
import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.ModuleConfiguration;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ConfigParameter;
import autosar3x.ecucparameterdef.ConfigReference;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ModuleDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;
import autosar3x.ecucparameterdef.ParamConfMultiplicity;
import autosar3x.genericstructure.infrastructure.ARObject;
import autosar3x.genericstructure.infrastructure.identifiable.Identifiable;

public class EcucUtil {

	public static <T> List<T> getAllEquivalentInstancesOf(ARObject instance, Class<T> type) {
		List<T> allInstances = EObjectUtil.getAllInstancesOf(instance, type, true);
		if (0 == allInstances.size()) {
			List<Resource> resources = instance.eResource().getResourceSet().getResources();
			allInstances = EObjectUtil.getAllInstancesOf(resources, type, true);
		}
		List<T> equivalentInstances = new ArrayList<T>();

		String absoluteQualifiedName = AutosarURIFactory.getAbsoluteQualifiedName(instance);

		for (T compareInstance : allInstances) {
			String compareAbsoluteQualifiedName = AutosarURIFactory.getAbsoluteQualifiedName(compareInstance);
			if (compareAbsoluteQualifiedName.equals(absoluteQualifiedName)) {
				equivalentInstances.add(compareInstance);
			}
		}

		return equivalentInstances;
	}

	public static List<Container> getAllContainersOf(ModuleConfiguration moduleConfiguration) {
		List<Container> allContainers = new ArrayList<Container>();
		List<ModuleConfiguration> equivalentModuleCongurations = getAllEquivalentInstancesOf(moduleConfiguration, ModuleConfiguration.class);
		for (ModuleConfiguration equivalentModuleConguration : equivalentModuleCongurations) {
			allContainers.addAll(equivalentModuleConguration.getContainers());
		}
		return allContainers;
	}

	public static List<ParameterValue> getAllParameterValuesOf(Container container) {
		List<ParameterValue> allParameterValues = new ArrayList<ParameterValue>();
		List<Container> equivalentContainers = getAllEquivalentInstancesOf(container, Container.class);
		for (Container equivalentContainer : equivalentContainers) {
			allParameterValues.addAll(equivalentContainer.getParameterValues());
		}
		return allParameterValues;
	}

	public static List<ConfigReferenceValue> getAllReferenceValuesOf(Container container) {
		List<ConfigReferenceValue> allReferenceValues = new ArrayList<ConfigReferenceValue>();
		List<Container> equivalentContainers = getAllEquivalentInstancesOf(container, Container.class);
		for (Container equivalentContainer : equivalentContainers) {
			allReferenceValues.addAll(equivalentContainer.getReferenceValues());
		}
		return allReferenceValues;
	}

	public static List<ContainerDef> getAllContainersOf(ModuleDef moduleConfiguration) {
		List<ContainerDef> allContainerDefs = new ArrayList<ContainerDef>();
		List<ModuleDef> equivalentModuleCongurations = getAllEquivalentInstancesOf(moduleConfiguration, ModuleDef.class);
		for (ModuleDef equivalentModuleConguration : equivalentModuleCongurations) {
			allContainerDefs.addAll(equivalentModuleConguration.getContainers());
		}
		return allContainerDefs;
	}

	public static List<Container> getAllSubContainersOf(Container container) {
		List<Container> allContainers = new ArrayList<Container>();
		List<Container> equivalentContainers = getAllEquivalentInstancesOf(container, Container.class);
		for (Container equivalentContainer : equivalentContainers) {
			allContainers.addAll(equivalentContainer.getSubContainers());
		}
		return allContainers;
	}

	public static List<ContainerDef> getAllSubContainersOf(ParamConfContainerDef containerDef) {
		List<ContainerDef> allContainerDefs = new ArrayList<ContainerDef>();
		List<ParamConfContainerDef> equivalentContainerDefs = getAllEquivalentInstancesOf(containerDef, ParamConfContainerDef.class);
		for (ParamConfContainerDef equivalentContainerDef : equivalentContainerDefs) {
			allContainerDefs.addAll(equivalentContainerDef.getSubContainers());
		}
		return allContainerDefs;
	}

	public static List<ContainerDef> getAllChoicesOf(ChoiceContainerDef containerDef) {
		List<ContainerDef> allContainerDefs = new ArrayList<ContainerDef>();
		List<ChoiceContainerDef> equivalentContainerDefs = getAllEquivalentInstancesOf(containerDef, ChoiceContainerDef.class);
		for (ChoiceContainerDef equivalentContainerDef : equivalentContainerDefs) {
			allContainerDefs.addAll(equivalentContainerDef.getChoices());
		}
		return allContainerDefs;
	}

	public static List<ConfigReference> getAllReferencesOf(ParamConfContainerDef containerDef) {
		List<ConfigReference> allConfigReferences = new ArrayList<ConfigReference>();
		List<ParamConfContainerDef> equivalentContainerDefs = getAllEquivalentInstancesOf(containerDef, ParamConfContainerDef.class);
		for (ParamConfContainerDef equivalentContainerDef : equivalentContainerDefs) {
			allConfigReferences.addAll(equivalentContainerDef.getReferences());
		}
		return allConfigReferences;
	}

	public static List<ConfigParameter> getAllParametersOf(ParamConfContainerDef containerDef) {
		List<ConfigParameter> allConfigParameters = new ArrayList<ConfigParameter>();
		List<ParamConfContainerDef> equivalentContainerDefs = getAllEquivalentInstancesOf(containerDef, ParamConfContainerDef.class);
		for (ParamConfContainerDef equivalentContainerDef : equivalentContainerDefs) {
			allConfigParameters.addAll(equivalentContainerDef.getParameters());
		}
		return allConfigParameters;
	}

	public static int getNumberOfUniqueShortNames(List<Identifiable> identifiables) {
		Set<String> shortNames = new HashSet<String>();
		for (Identifiable identifiable : identifiables) {
			shortNames.add(identifiable.getShortName());
		}
		return shortNames.size();
	}

	public static int getNumberOfUniqueContainersByDefinition(List<Container> containers, ContainerDef containerDef) throws IllegalArgumentException {
		final int numberOfUniqueContainersByDefinition;

		if (null == containers || null == containerDef) {
			throw new IllegalArgumentException("parameters must not be null");
		} else {
			Set<String> uniqueContainers = new HashSet<String>();
			for (Container currentContainer : containers) {
				if (containerDef.equals(currentContainer.getDefinition())) {
					uniqueContainers.add(currentContainer.getShortName());
				}
			}
			numberOfUniqueContainersByDefinition = uniqueContainers.size();
		}
		return numberOfUniqueContainersByDefinition;
	}

	public static List<ParameterValue> filterParameterValuesByDefinition(List<ParameterValue> parameterValues, ConfigParameter configParameter)
			throws IllegalArgumentException {
		List<ParameterValue> filteredParameterValues = new ArrayList<ParameterValue>();

		if (null == parameterValues || null == configParameter) {
			throw new IllegalArgumentException("parameters must not be null");
		} else {
			for (ParameterValue currentParameterValue : parameterValues) {
				if (configParameter.equals(currentParameterValue.getDefinition())) {
					filteredParameterValues.add(currentParameterValue);
				}
			}
		}
		return filteredParameterValues;
	}

	public static List<ConfigReferenceValue> filterConfigReferenceValuesByDefinition(List<ConfigReferenceValue> configReferenceValues,
			ConfigReference configReference) throws IllegalArgumentException {
		List<ConfigReferenceValue> filteredConfigReferenceValues = new ArrayList<ConfigReferenceValue>();

		if (null == configReferenceValues || null == configReference) {
			throw new IllegalArgumentException("parameters must not be null");
		} else {
			for (ConfigReferenceValue currentConfigReferenceValue : configReferenceValues) {
				if (configReference.equals(currentConfigReferenceValue.getDefinition())) {
					filteredConfigReferenceValues.add(currentConfigReferenceValue);
				}
			}
		}
		return filteredConfigReferenceValues;
	}

	public static boolean isValidLowerMultiplicity(int numberOfObjects, ParamConfMultiplicity paramConfMultiplicity) {

		// by default the multiplicity is 1
		int lowerMultiplicity = 1;
		try {
			if (paramConfMultiplicity.isSetLowerMultiplicity()) {
				lowerMultiplicity = Integer.parseInt(paramConfMultiplicity.getLowerMultiplicity());
			}
		} catch (NumberFormatException nfe) {
			// ContainerDef is currupt. that problem need to be reported by another constraint
		}

		return lowerMultiplicity <= numberOfObjects;
	}

	public static String getLowerMultiplicity(ParamConfMultiplicity paramConfMultiplicity) {
		final String lowerMultiplicity;
		if (paramConfMultiplicity.isSetLowerMultiplicity()) {
			lowerMultiplicity = paramConfMultiplicity.getLowerMultiplicity();
		} else {
			lowerMultiplicity = "1";
		}
		return lowerMultiplicity;
	}

	public static String getUpperMultiplicity(ParamConfMultiplicity paramConfMultiplicity) {
		final String upperMultiplicity;
		if (paramConfMultiplicity.isSetUpperMultiplicity()) {
			upperMultiplicity = paramConfMultiplicity.getUpperMultiplicity();
		} else {
			upperMultiplicity = "1";
		}
		return upperMultiplicity;
	}

	public static boolean isValidUpperMultiplicity(int numberOfObjects, ParamConfMultiplicity paramConfMultiplicity) {
		final boolean isValidUpperMultiplicity;
		if ("*".equals(paramConfMultiplicity.getUpperMultiplicity())) {
			isValidUpperMultiplicity = true;
		} else {
			int upperMultiplicity = 1;
			try {
				if (paramConfMultiplicity.isSetUpperMultiplicity()) {
					upperMultiplicity = Integer.parseInt(paramConfMultiplicity.getUpperMultiplicity());
				}
			} catch (NumberFormatException nfe) {
				// ContainerDef is currupt. that problem need to be reported by another constraint
			}

			isValidUpperMultiplicity = numberOfObjects <= upperMultiplicity;
		}
		return isValidUpperMultiplicity;
	}

}
