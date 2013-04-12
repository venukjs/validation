/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     BMW Car IT - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.validation.constraints.swc.internal.portinterface.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Failure messages for the PortInterface constraints.
 */
public class Messages extends NLS {

	private static final String MSG_BUNDLE_NAME = "org.artop.aal.validation.constraints.swc.internal.portinterface.messages.messages"; //$NON-NLS-1$

	public static String uniqueApplicationErrorCodes_Msg;

	static {
		NLS.initializeMessages(MSG_BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}

}
