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
import org.eclipse.osgi.util.ManifestElement;
import org.eclipse.sphinx.emf.validation.evalidator.adapter.EValidatorRegistering;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.artop.aal.autosar321.validation"; //$NON-NLS-1$

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

		// Let's registering EValidator for each contribution to org.eclipse.sphinx.emf.validation.registration.
		for (String requiredBundle : ManifestElement.getArrayFromList(getDefault().getBundle().getHeaders().get("Require-Bundle"))) { //$NON-NLS-1$
			if (requiredBundle.matches("org\\.artop\\.aal\\.autosar\\d\\d\\d\\.validation.*")) { //$NON-NLS-1$
				String validationBundleId = requiredBundle.substring(0, 35);
				if (Platform.getBundle(validationBundleId) != null) {
					EValidatorRegistering.getSingleton().eValidatorSetAllContributions(validationBundleId);
				}
			}
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