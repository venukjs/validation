/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc.internal;

import org.artop.ecl.emf.validation.evalidator.adapter.EValidatorRegistering;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;


public class Activator extends Plugin {

	public static final String PLUGIN_ID = "org.artop.aal.gautosar.validation.ecuc"; //$NON-NLS-1$

	private static Activator plugin;

	public Activator() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		// Let's registering EValidator for each contribution to org.artop.ecl.emf.validation.registration.
		EValidatorRegistering.getSingleton().eValidatorSetAllContributions(Activator.PLUGIN_ID);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static Activator getDefault() {
		return plugin;
	}

}
