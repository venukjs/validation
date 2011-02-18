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

import gautosar.ggenericstructure.ginfrastructure.GARObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.eclipse.sphinx.emf.util.EObjectUtil;

/**
 * Index that allows fast access to all instances that have the short name path and type. This is useful for e.g.
 * implementing validation of ecuc configurations that are split up over several resources and are not yet merged.
 * 
 * @param <T>
 *            the type of the equvalent instances to be indexed by this class
 */
public class EquivalentInstancesIndex<T> {

	private Map<String, List<T>> shortNamePathToEquivalentInstancesMap = new HashMap<String, List<T>>();
	private Map<T, List<T>> instanceToEquivalentInstancesMap = new HashMap<T, List<T>>();

	@SuppressWarnings("unused")
	private EquivalentInstancesIndex() {
		// not intended to be created using default constructor
	}

	/**
	 * Initializes the index for the given type. The index is created for the full model scope. The index only contains
	 * instances that have exactly the same type.
	 * 
	 * @param instance
	 *            - An element in the model
	 * @param type
	 *            - The type to create this index for.
	 */
	public EquivalentInstancesIndex(GARObject instance, Class<T> type) {
		List<T> allInstances = EObjectUtil.getAllInstancesOf(instance, type, false);
		for (T element : allInstances) {
			register(element);
		}
	}

	/**
	 * gets the list of instances that have the same short name path and type as the given instance.
	 * 
	 * @param instance
	 * @return list of instances that have the same short name path and type as the given instance
	 */
	public List<T> getEquivalentInstances(GARObject instance) {
		return instanceToEquivalentInstancesMap.get(instance);
	}

	private void register(T element) {
		String shortNamePath = AutosarURIFactory.getAbsoluteQualifiedName(element);
		List<T> equivalentElements = shortNamePathToEquivalentInstancesMap.get(shortNamePath);
		if (null == equivalentElements) {
			equivalentElements = new ArrayList<T>();
			shortNamePathToEquivalentInstancesMap.put(shortNamePath, equivalentElements);
		}
		equivalentElements.add(element);
		instanceToEquivalentInstancesMap.put(element, equivalentElements);
	}

}
