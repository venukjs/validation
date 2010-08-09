/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.emf.validation.diagnostic.filters;

import java.util.HashSet;
import java.util.Set;

import org.artop.ecl.emf.validation.diagnostic.filters.util.ConstraintFilterValue;
import org.artop.ecl.emf.validation.diagnostic.filters.util.ConstraintFilteringUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;

public class ExtensionPointFilter implements IConstraintFilter {

	private Set<ConstraintFilterValue> cfvs = null;

	public ExtensionPointFilter(Set<ConstraintFilterValue> cfvs) {
		this.cfvs = cfvs;
	}

	public ExtensionPointFilter(ConstraintFilterValue cfv) {
		cfvs = new HashSet<ConstraintFilterValue>();
		cfvs.add(cfv);
	}

	public boolean accept(IConstraintDescriptor constraint, EObject target) {

		if (cfvs == null) {
			return true;
		}

		for (ConstraintFilterValue current : ConstraintFilteringUtil.getFilteringParameterEnum(constraint.getId())) {
			if (cfvs.contains(current)) {
				return false;
			}
		}

		return true;
	}
}
