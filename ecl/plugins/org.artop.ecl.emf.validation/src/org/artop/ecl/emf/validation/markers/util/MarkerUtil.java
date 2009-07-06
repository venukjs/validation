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
package org.artop.ecl.emf.validation.markers.util;

import java.util.Set;

import org.artop.ecl.emf.util.EcoreResourceUtil;
import org.artop.ecl.emf.util.WorkspaceEditingDomainUtil;
import org.artop.ecl.emf.validation.Activator;
import org.artop.ecl.emf.validation.markers.IValidationMarker;
import org.artop.ecl.platform.util.PlatformLogUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.edit.domain.EditingDomain;

/**
 * Utility class for markers
 */
public class MarkerUtil {

	/**
	 * return the severity associated to marker of (sub)type {@link IMarker#PROBLEM}, -1 otherwise
	 * 
	 * @param marker
	 * @return the severity error of this marker, -1 if not found.
	 */
	public static int getSeverity(IMarker marker) {
		Assert.isNotNull(marker);

		return marker.getAttribute(IMarker.SEVERITY, -1);
	}

	/**
	 * return the problem message associated to marker of (sub)type {@link IMarker#PROBLEM}.
	 * 
	 * @param marker
	 * @return the message error of this marker.
	 */
	public static String getMessage(IMarker marker) {
		Assert.isNotNull(marker);

		return marker.getAttribute(IMarker.MESSAGE, "---"); //$NON-NLS-1$
	}

	/**
	 * return a set containing the names of the target features associated to marker of (sub)type
	 * {@link IValidationMarker#ECL_VALIDATION_PROBLEM}.
	 * 
	 * @param marker
	 * @return Set
	 * @see #getFeatures(IMarker)
	 */
	public static Set<String> getFeatures(IMarker marker) {
		Assert.isNotNull(marker);

		Set<String> result = null;

		try {
			result = FeatureAttUtil.unpackFeatures(marker);
		} catch (CoreException ex) {
			PlatformLogUtil.logAsInfo(Activator.getDefault(), ex);
		}

		return result;

	}

	/**
	 * Returns the eObject connected to a specified IMarker, if this first one exists, null otherwise
	 * 
	 * @param marker
	 *            The IMarker from which to retrieve the EObject.
	 * @return The EObject connected to the specified <code>marker</code>.
	 */
	public static EObject getConnectedEObjectFromMarker(IMarker marker) {
		if (marker != null) {
			IResource resource = marker.getResource();
			if (resource instanceof IFile) {
				EditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(resource);
				String uriAttribute = marker.getAttribute(EValidator.URI_ATTRIBUTE, null);
				if (editingDomain != null && uriAttribute != null) {
					URI uri = URI.createURI(uriAttribute);
					if (EcoreResourceUtil.exists(uri)) {
						return editingDomain.getResourceSet().getEObject(uri, true);
					}
				}
			}
		}
		return null;
	}
}
