/**
 * <copyright>
 * 
 * Copyright (c) See4sys and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.eclipse.sphinx.emf.validation.report;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;

/**
 * Utility class for validation reporting
 */
public class ReportUtil {

	/**
	 * the constraint registry, useful
	 */
	private static ConstraintRegistry constraintRegistry = ConstraintRegistry.getInstance();

	/**
	 * return the state of rules which verifies the {@link IConstraintFilter}.
	 * 
	 * @param icf
	 *            a constraint filter
	 * @return an Map with the id of the constraint as key and its activation state as value
	 * @see IConstraintDescriptor
	 */
	static public HashMap<String, Boolean> getRulesState(IConstraintFilter icf) {

		HashMap<String, Boolean> result = new HashMap<String, Boolean>();

		for (Object object : constraintRegistry.getAllDescriptors()) {
			IConstraintDescriptor constraint = (IConstraintDescriptor) object;
			if (icf != null) {
				if (icf.accept(constraint, null)) {
					result.put(constraint.getId(), constraint.isEnabled() && !constraint.isError());
				}
			} else {
				result.put(constraint.getId(), constraint.isEnabled() && !constraint.isError());
			}
		}

		return result;
	}

	/**
	 * return the rules in error e.g. which could not be applied
	 * 
	 * @param ruleIds
	 *            a Set of constraint Id
	 * @return a Set of constraint Id in error, if no, an empty Set
	 */
	static public Set<String> getRulesWithException(Set<String> ruleIds) {

		Set<String> result = new HashSet<String>();

		if (ruleIds == null || ruleIds.isEmpty()) {
			return result;
		}

		for (String id : ruleIds) {
			IConstraintDescriptor icd = constraintRegistry.getDescriptor(id);
			if (icd != null && icd.getException() != null) {
				result.add(id);
			}
		}

		return result;
	}

	/**
	 * return for a given {@link Diagnostic} numbers of error, warning and info found
	 * 
	 * @param diagnostic
	 * @return a tab of integer
	 */
	static public int[] getErrWarInf(Diagnostic diagnostic) {

		int[] result = { 0, 0, 0 }; // Error, Warning, Info

		if (diagnostic == null) {
			return result;
		}

		final int ERRIdx = 0;
		final int WARNIdx = 1;
		final int INFOIdx = 2;

		for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
			switch (childDiagnostic.getSeverity()) {
			case Diagnostic.ERROR:
				result[ERRIdx]++;
				break;
			case Diagnostic.WARNING:
				result[WARNIdx]++;
				break;
			case Diagnostic.INFO:
				result[INFOIdx]++;
				break;
			default: // do nothing
			}
		}

		return result;
	}

	/**
	 * Return the label of the rule "id" or the id itself if the rules does not exist
	 * 
	 * @param id
	 * @return see the method descrition.
	 */
	public static String getRuleLabel(String id) {

		IConstraintDescriptor icd = constraintRegistry.getDescriptor(id);

		return icd == null ? id : icd.getName();
	}

}
