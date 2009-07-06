/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on Released
 * AUTOSAR Material (ASLR) which accompanies this distribution, and is available
 * at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.emf.validation.bridge.util;

import java.util.HashMap;

import org.artop.ecl.emf.validation.bridge.extensions.RulesExtCache;
import org.artop.ecl.emf.validation.bridge.extensions.RulesExtInternal;
import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;

public class ConstraintUtil {

	/**
	 * Return the rules filter used for the meta-model of the EObject class names name
	 * 
	 * @param eObject
	 *            the target {@link EObject}
	 * @return the filter, "" whether the connected meta-model has not been connected
	 */
	public static String getModelFilter(EObject eObject) {
		Assert.isNotNull(eObject);

		return getModelFilter(eObject.eClass().getInstanceClassName());
	}

	/**
	 * return the rules filter used for the meta-model of the class names name
	 * 
	 * @param name
	 *            the full InstanceClassName
	 * @return the filter, "" whether the connected meta-model has not been connected
	 */
	public static String getModelFilter(String name) {
		Assert.isNotNull(name);

		HashMap<String, RulesExtInternal> registry = RulesExtCache.getSingleton().getRulesExtInternals();

		String suffix = name.substring(0, name.indexOf('.'));
		final RulesExtInternal rei = registry.get(suffix);

		if (rei != null) {
			return rei.getFilter();
		} else {
			return ""; //$NON-NLS-1$
		}
	}

}
