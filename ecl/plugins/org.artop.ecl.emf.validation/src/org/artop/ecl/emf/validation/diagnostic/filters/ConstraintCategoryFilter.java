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

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;

/**
 * Constraint filter whose criteria is the constraint category. This filter only accepts constraints whose category
 * matches the one given in the constructor.
 */
public class ConstraintCategoryFilter implements IConstraintFilter {

	private String categoryId;

	/**
	 * Constructor.
	 * 
	 * @param categoryId
	 *            The identifier of the expected category.
	 */
	public ConstraintCategoryFilter(String categoryId) {
		Assert.isNotNull(categoryId);
		this.categoryId = categoryId;
	}

	public boolean accept(IConstraintDescriptor constraint, EObject target) {
		if (constraint != null) {
			for (Category category : constraint.getCategories()) {
				// Iterate over category and its ancestors
				while (category != null) {
					if (categoryId.equals(category.getId())) {
						return true;
					}
					// Get the parent category
					category = category.getParent();
				}
			}
		}
		return false;
	}
}
