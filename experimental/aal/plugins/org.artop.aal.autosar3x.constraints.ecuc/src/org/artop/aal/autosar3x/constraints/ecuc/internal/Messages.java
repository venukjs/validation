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
