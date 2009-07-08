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
package org.artop.ecl.examples.validation.ui.internal.messages;

import org.eclipse.osgi.util.NLS;

/**
 * 
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.artop.ecl.examples.validation.ui.internal.messages.messages"; //$NON-NLS-1$

	public static String action_validateSelectedCategories_label;

	public static String menu_validation_label;

	public static String dialog_SelectConstraintCategories_title;
	public static String dialog_SelectConstraintCategories_description;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
