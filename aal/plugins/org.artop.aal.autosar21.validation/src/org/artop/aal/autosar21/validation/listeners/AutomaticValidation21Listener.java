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
package org.artop.aal.autosar21.validation.listeners;

import java.util.List;

import org.artop.aal.validation.listeners.AbstractAutomaticValidationListener;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;

import autosar21.genericstructure.infrastructure.identifiable.IdentifiablePackage;
import autosar21.util.Autosar21ResourceImpl;

public class AutomaticValidation21Listener extends AbstractAutomaticValidationListener {

	@Override
	protected boolean isTargetResource(Resource resource) {
		if (resource == null) {
			return false;
		}

		return resource instanceof Autosar21ResourceImpl ? true : false;
	}

	@Override
	protected boolean isShortNameChange(ResourceSetChangeEvent event) {
		List<?> notifications = event.getNotifications();

		if (notifications.size() == 1) {
			Notification notification = (Notification) notifications.get(0);
			if (notification instanceof ENotificationImpl && notification.getNotifier() instanceof EObject) {
				EClassifier ownerType = IdentifiablePackage.eINSTANCE.getIdentifiable();
				return ownerType.isInstance(notification.getNotifier())
						&& notification.getFeatureID(ownerType.getInstanceClass()) == IdentifiablePackage.IDENTIFIABLE__SHORT_NAME;
			}
		}

		return false;
	}

}
