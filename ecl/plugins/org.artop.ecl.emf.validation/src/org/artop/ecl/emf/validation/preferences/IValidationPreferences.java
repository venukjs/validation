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
package org.artop.ecl.emf.validation.preferences;

/**
 * Validation framework preferences.
 */
public interface IValidationPreferences {

	public final String PREF_ENABLE_AUTOMATIC_VALIDATION = "automatic_validation_enablement"; //$NON-NLS-1$
	public final boolean PREF_ENABLE_AUTOMATIC_VALIDATION_DEFAULT = Boolean.FALSE;

	public final String PREF_ENABLE_EMF_DEFAULT_RULES = "emf_rule_enablement"; //$NON-NLS-1$
	public final boolean PREF_ENABLE_EMF_DEFAULT_RULES_DEFAULT = Boolean.FALSE;

	// Whether or not to limit problems
	public final String PREF_LIMIT_PROBLEMS = "limit_problems"; //$NON-NLS-1$

	public final String PREF_MAX_NUMBER_OF_ERRORS = "pref_max_number_of_errors"; //$NON-NLS-1$
	public final int PREF_MAX_NUMBER_OF_ERRORS_DEFAULT = 10000;

	// Problem limits
	public final String PREF_PROBLEMS_LIMIT = "problems_limit"; //$NON-NLS-1$

	// The list of defined problems filters
	public final String PREF_PROBLEMS_FILTERS = "problems_filters"; //$NON-NLS-1$

	// Strict Editing Group
	public final String PREF_STRICT_EDITING = "strict_editing_mode"; //$NON-NLS-1$
	public final boolean PREF_STRICT_EDITING_DEFAULT = Boolean.TRUE;

	// Max Performance Group
	public final String PREF_APPLY_MAX_PERFORMANCE_SETTINGS = "apply_max_performance_settings"; //$NON-NLS-1$
	public final boolean PREF_APPLY_MAX_PERFORMANCE_SETTINGS_DEFAULT = Boolean.TRUE;
}
