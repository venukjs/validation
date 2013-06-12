/**
 * <copyright>
 * 
 * Copyright (c) See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.validation.internal;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.osgi.framework.adaptor.BundleClassLoader;
import org.eclipse.sphinx.emf.validation.evalidator.adapter.EValidatorRegistering;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import autosar3x.genericstructure.infrastructure.autosar.AUTOSAR;

/**
 * The activator class controls the plug-in life cycle
 */
@SuppressWarnings("restriction")
public class Activator extends Plugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.artop.aal.autosar3x.validation"; //$NON-NLS-1$

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

		String autosarValidationBundleId = null;
		ClassLoader classLoader = AUTOSAR.class.getClassLoader();
		if (classLoader instanceof BundleClassLoader) {
			Bundle bundle = ((BundleClassLoader) classLoader).getBundle();
			autosarValidationBundleId = bundle.getSymbolicName().concat(".validation"); //$NON-NLS-1$
		}

		// Let's registering EValidator for each contribution to org.eclipse.sphinx.emf.validation.registration.
		if (autosarValidationBundleId != null && Platform.getBundle(autosarValidationBundleId) != null) {
			EValidatorRegistering.getSingleton().eValidatorSetAllContributions(autosarValidationBundleId);
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