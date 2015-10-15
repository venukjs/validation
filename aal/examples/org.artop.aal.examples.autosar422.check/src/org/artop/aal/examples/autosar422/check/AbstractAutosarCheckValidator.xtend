/**
 * <copyright>
 *
 * Copyright (c) itemis and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 *
 * Contributors:
 *     itemis - Initial API and implementation
 *
 * </copyright>
 */
package org.artop.aal.examples.autosar422.check

import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sphinx.emf.check.AbstractCheckValidator;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.Functions.Function2;

abstract class AbstractAutosarCheckValidator extends AbstractCheckValidator {

	protected static Set<String> filter = null

	protected def void rangeIssue(EObject eObject, EStructuralFeature feature, int min, int max) {
		val Object value = eObject.eGet(feature)
		if (value instanceof Integer) {
			if (value < min || value > max)
				issue(eObject, feature, 0, #[value])
		}
	}

	protected def void stringValuesIssue(EObject eObject, EStructuralFeature feature, Set<String> stringValues) {
		val Object value = eObject.eGet(feature)
		if (value instanceof String) {
			if (!stringValues.contains(value))
				issue(eObject, feature, 0, #[value])
		}
	}

	protected def void issueIter(Iterable<EObject> objects, List<EObject> indexList, EStructuralFeature feature, Object... arguments) {
		objects.forEach[issue(it, feature, indexList.indexOf(it), arguments)]
	}

	/**
	 * Issue an error for objects in a list.
	 *
	 * @param objects
	 * @param indexList
	 * @param feature
	 * @param function
	 */
	protected def void issueIter(Iterable<EObject> objects, List<EObject> indexList, EStructuralFeature feature, Function1<EObject, Object> function) {
		objects.forEach[issue(it, feature, indexList.indexOf(it), #[function.apply(it)])]
	}

	/**
	 * Throw issue if predicate holds true
	 *
	 * @param indexList
	 *            - List of elements to check
	 * @param predicate
	 *            - predicate. Throw issue if true
	 * @param feature
	 *            - feature to pass to issue
	 * @param function
	 *            - map object to output object
	 */
	protected def <T> void issuePred(List<T> indexList, Function1<T, Boolean> predicate, EStructuralFeature feature, Function1<T, Object> function) {
		indexList.forEach[if (predicate.apply(it)) issue(it, feature, indexList.indexOf(it), #[function.apply(it)])]
	}

	/**
	 * Throw issue if predicate holds true
	 *
	 * @param indexList
	 *            - List of elements to check
	 * @param predicate
	 *            - predicate. Throw issue if true
	 * @param feature
	 *            - feature to pass to issue
	 */
	protected def <T> void issuePred(List<T> indexList, Function1<T, Boolean> predicate, EStructuralFeature feature) {
		indexList.forEach[if (predicate.apply(it)) issue(it, feature, indexList.indexOf(it), null)]
	}

	protected def <O, T> T reduce(Iterable<O> input, T initial, Function2<O, T, T> function) {
		var T result = initial
		for (O object : input) {
			result = function.apply(object, result)
		}
		result
	}

	protected def void issueRange(EObject eObject, EStructuralFeature feature, int lower, int upper) {
		val Object value = eObject.eGet(feature)
		if (value instanceof Integer) {
			if (value < lower || value > upper)
				issue(eObject, feature, null)
		}
	}
}