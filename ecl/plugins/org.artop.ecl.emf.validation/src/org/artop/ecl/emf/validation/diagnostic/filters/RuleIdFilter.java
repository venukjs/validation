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
package org.artop.ecl.emf.validation.diagnostic.filters;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;

public class RuleIdFilter implements IConstraintFilter {

	private Set<String> ruleIds = null;

	public RuleIdFilter(String ruleId) {
		ruleIds = new HashSet<String>();
		ruleIds.add(ruleId);
	}

	public RuleIdFilter(Set<String> ruleIds) {
		this.ruleIds = ruleIds;
	}

	public boolean accept(IConstraintDescriptor constraint, EObject target) {
		if (ruleIds == null) {
			return true;
		}
		return ruleIds.contains(constraint.getId()) ? true : false;
	}
}
