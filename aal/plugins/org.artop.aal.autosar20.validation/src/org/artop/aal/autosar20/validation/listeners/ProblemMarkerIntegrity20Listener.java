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
package org.artop.aal.autosar20.validation.listeners;

import org.artop.aal.validation.listeners.AbstractProblemMarkerIntegrityListener;
import org.eclipse.emf.transaction.NotificationFilter;

import autosar20.genericstructure.infrastructure.InfrastructurePackage;

/**
 * Listener on the <em>Short Name</em> feature for AUTOSAR 2.0. Updates URI field on related problem markers according
 * to short name change.
 */
public class ProblemMarkerIntegrity20Listener extends AbstractProblemMarkerIntegrityListener {

	/**
	 * The notification filter to apply.
	 * <p>
	 * This listener should be notified only in case of Identifiable <em>short name</em> modification or modification on
	 * the resource.
	 */
	private final static NotificationFilter filter = NotificationFilter.createFeatureFilter(InfrastructurePackage.eINSTANCE.getIdentifiable(),
			InfrastructurePackage.IDENTIFIABLE__SHORT_NAME);

	/**
	 * Constructor.
	 */
	public ProblemMarkerIntegrity20Listener() {
		super(filter);
	}

	/**
	 * Constructor
	 * 
	 * @param filter
	 *            filter on event
	 */
	protected ProblemMarkerIntegrity20Listener(NotificationFilter filter) {
		super(filter);
	}

}
