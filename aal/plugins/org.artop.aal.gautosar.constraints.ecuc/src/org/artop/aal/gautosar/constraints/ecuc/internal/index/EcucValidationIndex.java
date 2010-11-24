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
package org.artop.aal.gautosar.constraints.ecuc.internal.index;

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucdescription.GParameterValue;
import gautosar.ggenericstructure.ginfrastructure.GARObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An index that allows fast access to structural information of ECUC descriptions that are split up over several
 * resources.
 */
public class EcucValidationIndex {

	Map<Class<?>, EquivalentInstancesIndex<?>> classToEquivalentInstancesCache = new HashMap<Class<?>, EquivalentInstancesIndex<?>>();

	public EcucValidationIndex() {
		super();
	}

	/**
	 * Gets all instances within the full model that have the same short name path and type.
	 * 
	 * @param <T>
	 * @param instance
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> getAllEquivalentInstancesOf(GARObject instance, Class<T> type) {

		EquivalentInstancesIndex<T> cache = (EquivalentInstancesIndex<T>) classToEquivalentInstancesCache.get(type);
		if (null == cache) {
			cache = new EquivalentInstancesIndex<T>(instance, type);
			classToEquivalentInstancesCache.put(type, cache);
		}

		return cache.getEquivalentInstances(instance);
	}

	/**
	 * Finds all GModuleConfigurations in the model that have the same short name path as the given gModuleConfiguration
	 * and collects all directly nested GContainers.
	 * 
	 * @param gModuleConfiguration
	 * @return list of directly nested GContainers of all GModuleConfigurations in the model that have the same short
	 *         name path as the give gModuleConfiguration
	 */
	public List<GContainer> getAllContainersOf(GModuleConfiguration gModuleConfiguration) {
		List<GContainer> allContainers = new ArrayList<GContainer>();
		List<GModuleConfiguration> equivalentModuleConfigurations = getAllEquivalentInstancesOf(gModuleConfiguration, GModuleConfiguration.class);
		for (GModuleConfiguration equivalentModuleConguration : equivalentModuleConfigurations) {
			allContainers.addAll(equivalentModuleConguration.gGetContainers());
		}
		return allContainers;
	}

	/**
	 * Finds all GContainers in the model that have the same short name path as the given gContainer and collects all
	 * directly nested GParameterValues.
	 * 
	 * @param gContainer
	 * @return list of directly nested GParameterValues of all GContainers in the model that have the same short name
	 *         path as the give gContainer
	 */
	public List<GParameterValue> getAllParameterValuesOf(GContainer gContainer) {
		List<GParameterValue> allParameterValues = new ArrayList<GParameterValue>();
		List<GContainer> equivalentContainers = getAllEquivalentInstancesOf(gContainer, GContainer.class);
		for (GContainer equivalentContainer : equivalentContainers) {
			allParameterValues.addAll(equivalentContainer.gGetParameterValues());
		}
		return allParameterValues;
	}

	/**
	 * Finds all GContainers in the model that have the same short name path as the given gContainer and collects all
	 * directly nested GConfigReferenceValues.
	 * 
	 * @param gContainer
	 * @return list of directly nested GConfigReferenceValues of all GContainers in the model that have the same short
	 *         name path as the give gContainer
	 */
	public List<GConfigReferenceValue> getAllReferenceValuesOf(GContainer gContainer) {
		List<GConfigReferenceValue> allReferenceValues = new ArrayList<GConfigReferenceValue>();
		List<GContainer> equivalentContainers = getAllEquivalentInstancesOf(gContainer, GContainer.class);
		for (GContainer equivalentContainer : equivalentContainers) {
			allReferenceValues.addAll(equivalentContainer.gGetReferenceValues());
		}
		return allReferenceValues;
	}

	/**
	 * Finds all GContainers in the model that have the same short name path as the given gContainer and collects all
	 * directly nested GContainers.
	 * 
	 * @param gContainer
	 * @return list of directly nested GContainers of all GContainers in the model that have the same short name path as
	 *         the give gContainer
	 */
	public List<GContainer> getAllSubContainersOf(GContainer gContainer) {
		List<GContainer> allContainers = new ArrayList<GContainer>();
		List<GContainer> equivalentContainers = getAllEquivalentInstancesOf(gContainer, GContainer.class);
		for (GContainer equivalentContainer : equivalentContainers) {
			allContainers.addAll(equivalentContainer.gGetSubContainers());
		}
		return allContainers;
	}

}
