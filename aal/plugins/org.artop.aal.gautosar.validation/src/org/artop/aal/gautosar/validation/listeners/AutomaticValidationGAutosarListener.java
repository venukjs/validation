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
package org.artop.aal.gautosar.validation.listeners;

import gautosar.ggenericstructure.ginfrastructure.GinfrastructurePackage;

import java.util.List;

import org.artop.aal.common.resource.impl.AutosarXMLResourceImpl;
import org.artop.aal.validation.listeners.AbstractAutomaticValidationListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

public class AutomaticValidationGAutosarListener extends AbstractAutomaticValidationListener {

	@Override
	protected boolean isTargetResource(Resource resource) {
		if (resource == null) {
			return false;
		}

		return resource instanceof AutosarXMLResourceImpl ? true : false;
	}

	@Override
	protected boolean isShortNameChange(ResourceSetChangeEvent event) {
		List<?> notifications = event.getNotifications();

		if (notifications.size() == 1) {
			Notification notification = (Notification) notifications.get(0);
			if (notification instanceof ENotificationImpl && notification.getNotifier() instanceof EObject) {
				EClassifier ownerType = GinfrastructurePackage.eINSTANCE.getGIdentifiable();
				return ownerType.isInstance(notification.getNotifier())
						&& notification.getFeatureID(ownerType.getInstanceClass()) == GinfrastructurePackage.GIDENTIFIABLE__GSHORT_NAME;
			}
		}

		return false;
	}

}
