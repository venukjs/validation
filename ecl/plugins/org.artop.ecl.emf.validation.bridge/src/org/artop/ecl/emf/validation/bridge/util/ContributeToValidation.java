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
package org.artop.ecl.emf.validation.bridge.util;

import org.artop.ecl.emf.validation.bridge.Activator;
import org.artop.ecl.emf.validation.bridge.extensions.RulesExtInternal;
import org.artop.ecl.platform.util.PlatformLogUtil;

public class ContributeToValidation {

	/**
	 * Main utility function
	 * 
	 * @param r
	 *            describe the org.artop.ecl.emf.validation extension
	 */
	public static void contributeToValidation(RulesExtInternal r) {
		if (r == null) {
			return;
		}

		declareContext(r);

	}

	/**
	 * contribution to the org.eclipse.emf.validation.constraintBindings in order to declare new context
	 * 
	 * @param r
	 *            it describes the org.artop.ecl.emf.validation extension
	 */
	private static void declareContext(RulesExtInternal r) {

		String buffer = "<plugin><extension point=\"org.eclipse.emf.validation.constraintBindings\">" + "<clientContext id=\"" //$NON-NLS-1$//$NON-NLS-2$
				+ RuleExtensionUtil.getContextBindingId(r.getMarker()) + "\">" + "<enablement>" + "<instanceof value=\"" + r.getRootModelObjectName() //$NON-NLS-1$ //$NON-NLS-2$//$NON-NLS-3$
				+ "\"/>" + "</enablement>" + "</clientContext>" + "</extension></plugin>"; //$NON-NLS-1$//$NON-NLS-2$//$NON-NLS-3$//$NON-NLS-4$

		if (!AddExtension.addExt(buffer)) {
			PlatformLogUtil.logAsWarning(Activator.getDefault(), RuleExtensionUtil.getContextBindingId(r.getMarker()));
		}

	}
}
