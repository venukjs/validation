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

import org.artop.ecl.emf.validation.Activator;
import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

public class ValidationPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IEclipsePreferences defaultNode = new DefaultScope().getNode(Activator.getDefault().getBundle().getSymbolicName());
		defaultNode.putBoolean(IValidationPreferences.PREF_ENABLE_AUTOMATIC_VALIDATION,
				IValidationPreferences.PREF_ENABLE_AUTOMATIC_VALIDATION_DEFAULT);
		defaultNode.putBoolean(IValidationPreferences.PREF_ENABLE_EMF_DEFAULT_RULES, IValidationPreferences.PREF_ENABLE_EMF_DEFAULT_RULES_DEFAULT);
		defaultNode.putBoolean(IValidationPreferences.PREF_STRICT_EDITING, IValidationPreferences.PREF_STRICT_EDITING_DEFAULT);
		defaultNode.putBoolean(IValidationPreferences.PREF_APPLY_MAX_PERFORMANCE_SETTINGS,
				IValidationPreferences.PREF_APPLY_MAX_PERFORMANCE_SETTINGS_DEFAULT);
		defaultNode.putInt(IValidationPreferences.PREF_MAX_NUMBER_OF_ERRORS, IValidationPreferences.PREF_MAX_NUMBER_OF_ERRORS_DEFAULT);
	}
}
