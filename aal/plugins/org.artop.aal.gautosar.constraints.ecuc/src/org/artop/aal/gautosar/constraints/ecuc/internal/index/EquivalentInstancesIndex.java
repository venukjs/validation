package org.artop.aal.gautosar.constraints.ecuc.internal.index;

import gautosar.ggenericstructure.ginfrastructure.GARObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.ecl.emf.util.EObjectUtil;

public class EquivalentInstancesIndex<T> {

	private Map<String, List<T>> shortNamePathToEquivalentInstancesMap = new HashMap<String, List<T>>();
	private Map<T, List<T>> instanceToEquivalentInstancesMap = new HashMap<T, List<T>>();

	@SuppressWarnings("unused")
	private EquivalentInstancesIndex() {
		// not intended to be created using default constructor
	}

	public EquivalentInstancesIndex(GARObject instance, Class<T> type) {
		List<T> allInstances = EObjectUtil.getAllInstancesOf(instance, type, false);
		for (T element : allInstances) {
			register(element);
		}
	}

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
