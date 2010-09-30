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
package org.artop.ecl.emf.validation;

import java.util.HashMap;

import org.artop.ecl.emf.validation.bridge.extensions.RulesExtCache;
import org.artop.ecl.emf.validation.bridge.extensions.RulesExtInternal;
import org.artop.ecl.emf.validation.constraint.ConstraintCallHandlerExtensionsReader;
import org.artop.ecl.emf.validation.constraint.IConstraintCallHandlerRegistry;
import org.artop.ecl.emf.validation.evalidator.adapter.EValidatorAdapter;
import org.artop.ecl.emf.validation.internal.constraint.ConstraintCallHandlerRegistry;
import org.artop.ecl.emf.validation.listeners.ResourceURIChangeListener;
import org.artop.ecl.platform.util.PlatformLogUtil;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.emf.ecore.EValidator;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends Plugin {

	/** The plug-in ID */
	public static final String PLUGIN_ID = "org.artop.ecl.emf.validation"; //$NON-NLS-1$

	/** The shared instance */
	private static Activator plugin;

	/** the resource listener on URI changes */
	private static IResourceChangeListener resourceChangeListener = null;

	private static IConstraintCallHandlerRegistry fConstraintCallHandlerRegistry;

	private InstanceScope instanceScope;

	/**
	 * The constructor
	 */
	public Activator() {
		resourceChangeListener = new ResourceURIChangeListener();
	}

	/*
	 * @see org.eclipse.core.runtime.Plugins#start(org.osgi.framework.BundleContext)
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		try {
			super.start(context);
			plugin = this;

			/*
			 * Let's start the RulesExtCache.
			 */
			RulesExtCache.getSingleton().startup();

		} catch (Exception e) {
			PlatformLogUtil.logAsError(plugin, e);
		}

		// Add our listener on the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		workspace.addResourceChangeListener(resourceChangeListener, IResourceChangeEvent.POST_CHANGE | IResourceChangeEvent.POST_BUILD);

	}

	/*
	 * @see org.eclipse.core.runtime.Plugin#stop(org.osgi.framework.BundleContext)
	 */
	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;

		/*
		 * Let's stop the RulesExtCache.
		 */
		RulesExtCache.getSingleton().shutdown();

		super.stop(context);

		// Remove our listener on the workspace
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (workspace != null) {
			workspace.removeResourceChangeListener(resourceChangeListener);
		}
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Add EValidator for each registered model to the EValidator registry.
	 */
	protected void eValidatorSetting() {
		HashMap<String, RulesExtInternal> extRuleCacheMap = RulesExtCache.getSingleton().getRulesExtInternals();

		for (RulesExtInternal r : extRuleCacheMap.values()) {
			EValidator.Registry.INSTANCE.put(r.getRootModelClass().getEPackage(), new EValidatorAdapter());
		}
	}

	public InstanceScope getInstanceScope() {
		if (instanceScope == null) {
			instanceScope = new InstanceScope();
		}
		return instanceScope;
	}

	public static IConstraintCallHandlerRegistry getConstraintCallHandlerRegistry() {
		if (fConstraintCallHandlerRegistry == null) {
			fConstraintCallHandlerRegistry = new ConstraintCallHandlerRegistry(new ConstraintCallHandlerExtensionsReader());
		}
		return fConstraintCallHandlerRegistry;
	}
}
