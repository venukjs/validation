/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on Released
 * AUTOSAR Material (ASLR) which accompanies this distribution, and is available
 * at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar3x.validation.listeners;

import org.artop.aal.validation.listeners.AbstractProblemMarkerIntegrityListener;
import org.eclipse.emf.transaction.NotificationFilter;

import autosar3x.genericstructure.infrastructure.identifiable.IdentifiablePackage;

/**
 * Listener on the <em>Short Name</em> feature for AUTOSAR 3.x. Updates URI field on related problem markers according
 * to short name change.
 */
public class ProblemMarkerIntegrity3xListener extends AbstractProblemMarkerIntegrityListener {

	/**
	 * The notification filter to apply.
	 * <p>
	 * This listener should be notified only in case of Identifiable <em>short name</em> modification or modification on
	 * the resource.
	 */
	private final static NotificationFilter filter = NotificationFilter.createFeatureFilter(IdentifiablePackage.eINSTANCE.getIdentifiable(),
			IdentifiablePackage.IDENTIFIABLE__SHORT_NAME);

	/**
	 * Constructor.
	 */
	public ProblemMarkerIntegrity3xListener() {
		super(filter);
	}

	/**
	 * Constructor
	 * 
	 * @param filter
	 *            filter on event
	 */
	protected ProblemMarkerIntegrity3xListener(NotificationFilter filter) {
		super(filter);
	}

}
