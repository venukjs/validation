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
package org.artop.aal.validation.ui.internal.messages;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	public static String _UI_FixUuidConflicts_item;
	public static String _UI_FixUuidConflicts_desc;
	public static String _UI_FixUuidConflicts_command;
	public static String _UI_FixUuidConflicts_commandDesc;
	public static String _UI_FixUuidConflicts_result;

	private static final String BUNDLE_NAME = "org.artop.aal.validation.ui.internal.messages.Messages"; //$NON-NLS-1$

	static {
		// Initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
