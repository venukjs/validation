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
package org.artop.aal.examples.validation.ui.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * 
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.artop.aal.examples.validation.ui.internal.messages.messages"; //$NON-NLS-1$

	public static String action_validateAutosarConstraints_label;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
