/**
 * <copyright>
 *
 * Copyright (c) See4sys, itemis and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 *
 * Contributors:
 *     See4sys - Initial API and implementation
 *     itemis - [2090] - Usage of BundleClassLoader in bundle activator of org.artop.aal.autosar3x.validation breaks compilation under Luna
 *
 * </copyright>
 */
package org.artop.aal.autosar40.validation.internal;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.sphinx.emf.validation.evalidator.adapter.EValidatorRegistering;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import autosar40.autosartoplevelstructure.AUTOSAR;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.artop.aal.autosar40.validation"; //$NON-NLS-1$

	/** The shared instance */
	private static Activator plugin;

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// Compute id of revision-specific AUTOSAR validation bundle
		Bundle bundle = FrameworkUtil.getBundle(AUTOSAR.class);
		String autosarValidationBundleId = bundle.getSymbolicName().concat(".validation"); //$NON-NLS-1$

		// Let's registering EValidator for each contribution to org.eclipse.sphinx.emf.validation.registration.
		if (autosarValidationBundleId != null && Platform.getBundle(autosarValidationBundleId) != null) {
			EValidatorRegistering.getSingleton().eValidatorSetAllContributions(autosarValidationBundleId);
		}

		// Bug 2122
		else {
			EValidatorRegistering.getSingleton().eValidatorSetAllContributions(Activator.PLUGIN_ID);

		}

	}

	/*
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}