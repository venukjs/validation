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
package org.artop.aal.examples.autosar444.check;

import autosar40.autosartoplevelstructure.AUTOSAR
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage
import autosar40.genericstructure.generaltemplateclasses.identifiable.Identifiable
import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import org.eclipse.emf.ecore.EObject
import org.eclipse.sphinx.emf.check.Check
import org.eclipse.xtext.xbase.lib.Functions.Function1

class SWACheckValidator extends AbstractAutosarCheckValidator {

	/**
	 * Helper function to group elements by the results of a passed function. This will return a multimap with the
	 * function results as key and the function Arguments as values Can be used to detect duplicates of any kind
	 * (shortName, msgId, etc.) in a collection
	 *
	 * @param objects
	 *            - input list
	 * @param function
	 * @return
	 */
	def static <T, X> Multimap<T, X> groupBy(Iterable<X> objects, Function1<X, T> function) {
		var Multimap<T, X> multiMap = HashMultimap.<T, X> create

		for (X object : objects) {
			multiMap.put(function.apply(object), object)
		}
		multiMap
	}

	/**
	 * Check that all elements of a contained (owned) feature of any Identifiable are unique wrt shortName. Uses EMF
	 * mechanism to iterate over the model.
	 *
	 * @param c
	 */
	@Check(constraint = "NAMENOTUNIQUE",categories="Basic")
	def void checkDuplicateName(Identifiable identifiable) {

		// Use EMF to find out about all contained features
		//
		val Iterable<Identifiable> iterable = identifiable.eContents.filter(typeof(Identifiable))

		// Now find the duplicates. Note that we are passing a lambda expression to groupBy
		//
		val Multimap<String, Identifiable> r = groupBy(iterable, [Identifiable x | x.shortName])

		// Again filter all those keys (shortNames) that have more than one entry.
		//
		val Iterable<String> problems = r.keySet.filter[r.get(it).size > 1]

		// Add the errors to the model
		//
		for(String name : problems) {
			for(Identifiable object : r.get(name)) {
				issue(object, null, name, object.shortName, identifiable.shortName)
			}
		}
	}

	@Check(constraint = "AUTOSARDUMMY")
	protected def void dummy(AUTOSAR ar) {
	}

	@Check(constraint = "AUTOSARDUMMY")
	protected def void dummy(ARPackage arPackage) {
	}

	@Check(constraint = "AUTOSARDUMMY")
	protected def void dummy(EObject eObject) {
	}
}
