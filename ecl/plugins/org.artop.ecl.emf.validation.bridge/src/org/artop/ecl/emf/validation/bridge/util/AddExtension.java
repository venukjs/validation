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

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.artop.ecl.emf.validation.bridge.Activator;
import org.eclipse.core.internal.registry.ExtensionRegistry;
import org.eclipse.core.runtime.ContributorFactoryOSGi;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Tools in order to simplify the add programmaticaly extension
 * 
 * @author NBD
 */
@SuppressWarnings("restriction")
public class AddExtension {

	/**
	 * add the extension described into buffer to the extension registry and associate it to a OSGI contributor for the
	 * plugin org.artop.ecl.emf.validation
	 * 
	 * @param buffer
	 *            the extension contribution (as xml format)
	 */
	public static boolean addExt(String buffer) {
		IContributor cont = ContributorFactoryOSGi.createContributor(Platform.getBundle(Activator.PLUGIN_ID));
		return addExt(buffer, cont);
	}

	/**
	 * add the extension described into buffer to the extension registry and associate it to the contributor cont
	 * 
	 * @param buffer
	 *            the extension contribution (as xml format)
	 * @param cont
	 *            an OSGI contributor
	 */
	public static boolean addExt(String buffer, IContributor cont) {

		IExtensionRegistry registry = Platform.getExtensionRegistry();

		InputStream iss = new ByteArrayInputStream(buffer.getBytes());

		Object key = ((ExtensionRegistry) registry).getTemporaryUserToken();

		try {
			registry.addContribution(iss, cont, false, null, null, key);
		} finally {
			try {
				iss.close();
			} catch (IOException e) {
			}
		}

		return true;
	}
}
