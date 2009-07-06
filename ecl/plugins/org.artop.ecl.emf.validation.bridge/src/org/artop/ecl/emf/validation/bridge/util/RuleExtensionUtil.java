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

import org.artop.ecl.emf.validation.bridge.extensions.RulesExtCache;
import org.artop.ecl.emf.validation.bridge.extensions.RulesExtInternal;

/**
 * useful utility methods.
 */
public class RuleExtensionUtil {

	/**
	 * Give the client context binding id for the model with the corresponding marker
	 * 
	 * @param marker
	 * @return the corresponding client context binding id, if it exists, null otherwise
	 */
	public static String getContextBindingId(String marker) {

		RulesExtInternal r = RulesExtCache.getSingleton().getRulesExtInternals().get(marker);

		if (r == null || r.getRootModelClass() == null) {
			return null;
		}

		return r.getMarker() + "." + r.getRootModelClass().getName() + ".context"; //$NON-NLS-1$ //$NON-NLS-2$
	}

}
