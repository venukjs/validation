/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy,  Continental Engineering Services  and others.
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
package org.artop.aal.autosar3x.constraints.ecuc.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.artop.aal.autosar3x.constraints.ecuc.internal.Messages"; //$NON-NLS-1$
	public static String moduleConfig_ImplConfigVariantNotSet;
	public static String moduleConfig_ImplConfigVariantNotSupported;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
