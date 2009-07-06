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
package org.artop.ecl.emf.validation.diagnostic.filters.util;

import java.util.HashSet;
import java.util.Set;

import org.artop.ecl.emf.validation.util.ConstraintExtensionUtil;
import org.eclipse.core.runtime.Assert;

/**
 * This utility class allow
 */
public class ConstraintFilteringUtil {

	/**
	 * the parameter name (check constraint extension point definition)
	 */
	static public final String FILTER_NOT_VALIDATE_ON_VALUE = "DISABLE_CONSTRAINT_ON"; //$NON-NLS-1$

	/**
	 * return a {@link Set} of filtering value from the extension point definition of the constraint ruleId
	 * 
	 * @param ruleID
	 * @return
	 */
	static public Set<String> getFilteringParameter(String ruleId) {
		Assert.isNotNull(ruleId);

		return ConstraintExtensionUtil.getParamOfType(ruleId, FILTER_NOT_VALIDATE_ON_VALUE);

	}

	/**
	 * return a {@link Set} of filtering value from the extension point definition of the constraint ruleId
	 * 
	 * @param ruleID
	 * @return
	 */
	static public Set<ConstraintFilterValue> getFilteringParameterEnum(String ruleId) {
		Assert.isNotNull(ruleId);

		Set<ConstraintFilterValue> filters = new HashSet<ConstraintFilterValue>();

		Set<String> params = getFilteringParameter(ruleId);

		for (String current : params) {
			ConstraintFilterValue cfv = ConstraintFilterValue.convert(current);
			if (cfv != null) {
				filters.add(cfv);
			}
		}

		return filters;

	}
}
