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

package org.artop.aal.gautosar.constraints.ecuc.internal.index;

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;
import gautosar.ggenericstructure.ginfrastructure.GARObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EcucValidationIndex {

	Map<Class<?>, EquivalentInstancesIndex<?>> classToEquivalentInstancesCache = new HashMap<Class<?>, EquivalentInstancesIndex<?>>();

	public EcucValidationIndex() {
		super();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getAllEquivalentInstancesOf(GARObject instance, Class<T> type) {

		EquivalentInstancesIndex<T> cache = (EquivalentInstancesIndex<T>) classToEquivalentInstancesCache.get(type);
		if (null == cache) {
			cache = new EquivalentInstancesIndex<T>(instance, type);
			classToEquivalentInstancesCache.put(type, cache);
		}

		return cache.getEquivalentInstances(instance);
	}

	public List<GContainer> getAllContainersOf(GModuleConfiguration gModuleConfiguration) {
		List<GContainer> allContainers = new ArrayList<GContainer>();
		List<GModuleConfiguration> equivalentModuleConfigurations = getAllEquivalentInstancesOf(gModuleConfiguration, GModuleConfiguration.class);
		for (GModuleConfiguration equivalentModuleConguration : equivalentModuleConfigurations) {
			allContainers.addAll(equivalentModuleConguration.gGetContainers());
		}
		return allContainers;
	}

	public List<GParameterValue> getAllParameterValuesOf(GContainer gContainer) {
		List<GParameterValue> allParameterValues = new ArrayList<GParameterValue>();
		List<GContainer> equivalentContainers = getAllEquivalentInstancesOf(gContainer, GContainer.class);
		for (GContainer equivalentContainer : equivalentContainers) {
			allParameterValues.addAll(equivalentContainer.gGetParameterValues());
		}
		return allParameterValues;
	}

	public List<GConfigReferenceValue> getAllReferenceValuesOf(GContainer gContainer) {
		List<GConfigReferenceValue> allReferenceValues = new ArrayList<GConfigReferenceValue>();
		List<GContainer> equivalentContainers = getAllEquivalentInstancesOf(gContainer, GContainer.class);
		for (GContainer equivalentContainer : equivalentContainers) {
			allReferenceValues.addAll(equivalentContainer.gGetReferenceValues());
		}
		return allReferenceValues;
	}

	public List<GContainerDef> getAllContainersOf(GModuleDef gModuleDef) {
		List<GContainerDef> allContainerDefs = new ArrayList<GContainerDef>();
		List<GModuleDef> equivalentModuleDefs = getAllEquivalentInstancesOf(gModuleDef, GModuleDef.class);
		for (GModuleDef equivalentModuleDef : equivalentModuleDefs) {
			allContainerDefs.addAll(equivalentModuleDef.gGetContainers());
		}
		return allContainerDefs;
	}

	public List<GContainer> getAllSubContainersOf(GContainer gContainer) {
		List<GContainer> allContainers = new ArrayList<GContainer>();
		List<GContainer> equivalentContainers = getAllEquivalentInstancesOf(gContainer, GContainer.class);
		for (GContainer equivalentContainer : equivalentContainers) {
			allContainers.addAll(equivalentContainer.gGetSubContainers());
		}
		return allContainers;
	}

	public List<GContainerDef> getAllSubContainersOf(GParamConfContainerDef gContainerDef) {
		List<GContainerDef> allContainerDefs = new ArrayList<GContainerDef>();
		List<GParamConfContainerDef> equivalentContainerDefs = getAllEquivalentInstancesOf(gContainerDef, GParamConfContainerDef.class);
		for (GParamConfContainerDef equivalentContainerDef : equivalentContainerDefs) {
			allContainerDefs.addAll(equivalentContainerDef.gGetSubContainers());
		}
		return allContainerDefs;
	}

	public List<GContainerDef> getAllChoicesOf(GChoiceContainerDef gContainerDef) {
		List<GContainerDef> allContainerDefs = new ArrayList<GContainerDef>();
		List<GChoiceContainerDef> equivalentContainerDefs = getAllEquivalentInstancesOf(gContainerDef, GChoiceContainerDef.class);
		for (GChoiceContainerDef equivalentContainerDef : equivalentContainerDefs) {
			allContainerDefs.addAll(equivalentContainerDef.gGetChoices());
		}
		return allContainerDefs;
	}

	public List<GConfigReference> getAllReferencesOf(GParamConfContainerDef gContainerDef) {
		List<GConfigReference> allConfigReferences = new ArrayList<GConfigReference>();
		List<GParamConfContainerDef> equivalentContainerDefs = getAllEquivalentInstancesOf(gContainerDef, GParamConfContainerDef.class);
		for (GParamConfContainerDef equivalentContainerDef : equivalentContainerDefs) {
			allConfigReferences.addAll(equivalentContainerDef.gGetReferences());
		}
		return allConfigReferences;
	}

	public List<GConfigParameter> getAllParametersOf(GParamConfContainerDef gContainerDef) {
		List<GConfigParameter> allConfigParameters = new ArrayList<GConfigParameter>();
		List<GParamConfContainerDef> equivalentContainerDefs = getAllEquivalentInstancesOf(gContainerDef, GParamConfContainerDef.class);
		for (GParamConfContainerDef equivalentContainerDef : equivalentContainerDefs) {
			allConfigParameters.addAll(equivalentContainerDef.gGetParameters());
		}
		return allConfigParameters;
	}

}
