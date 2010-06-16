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
package org.artop.ecl.emf.validation.bridge.util;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$

	public static String errMissingAttributeOnExtensionPoint;
	public static String errOnExtensionIntro;
	public static String errNsURIRootObject;
	public static String errNsURIRootPackageObject;
	public static String errOnExtensionModelNotRegistered;
	public static String errOnConxtextDef;
	public static String errWrongClassifier;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

}
