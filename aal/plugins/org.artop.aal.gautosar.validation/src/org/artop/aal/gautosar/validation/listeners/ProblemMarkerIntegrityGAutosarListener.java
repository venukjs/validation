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
package org.artop.aal.gautosar.validation.listeners;

import gautosar.ggenericstructure.ginfrastructure.GinfrastructurePackage;

import org.artop.aal.validation.listeners.AbstractProblemMarkerIntegrityListener;
import org.eclipse.emf.transaction.NotificationFilter;

/**
 * Listener on the <em>Short Name</em> feature for GAUTOSAR. Updates URI field on related problem markers according to
 * short name change.
 */
public class ProblemMarkerIntegrityGAutosarListener extends AbstractProblemMarkerIntegrityListener {

	/**
	 * The notification filter to apply.
	 * <p>
	 * This listener should be notified only in case of Identifiable <em>short name</em> modification or modification on
	 * the resource.
	 */
	private final static NotificationFilter filter = NotificationFilter.createFeatureFilter(GinfrastructurePackage.eINSTANCE.getGIdentifiable(),
			GinfrastructurePackage.GREFERRABLE__GSHORT_NAME);

	/**
	 * Constructor.
	 */
	public ProblemMarkerIntegrityGAutosarListener() {
		super(filter);
	}

	/**
	 * Constructor
	 * 
	 * @param filter
	 *            filter on event
	 */
	protected ProblemMarkerIntegrityGAutosarListener(NotificationFilter filter) {
		super(filter);
	}

}
