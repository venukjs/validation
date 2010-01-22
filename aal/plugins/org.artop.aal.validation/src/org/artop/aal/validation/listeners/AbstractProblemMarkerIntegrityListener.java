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
package org.artop.aal.validation.listeners;

import java.util.List;

import org.artop.aal.validation.Activator;
import org.artop.aal.validation.util.Messages;
import org.artop.ecl.emf.util.EcorePlatformUtil;
import org.artop.ecl.emf.validation.markers.ValidationMarkerManager;
import org.artop.ecl.emf.validation.preferences.IValidationPreferences;
import org.artop.ecl.emf.validation.util.ValidationUtil;
import org.artop.ecl.platform.util.PlatformLogUtil;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;

public abstract class AbstractProblemMarkerIntegrityListener extends ResourceSetListenerImpl {

	protected AbstractProblemMarkerIntegrityListener(NotificationFilter filter) {
		super(filter);
	}

	@Override
	public void resourceSetChanged(ResourceSetChangeEvent event) {

		// If automatic validation is activated, we do not need to do anything, this one will simply perform
		// its job, removing old markers and creating new ones.
		if (Platform.getPreferencesService().getBoolean(org.artop.ecl.emf.validation.Activator.PLUGIN_ID,
				IValidationPreferences.PREF_ENABLE_AUTOMATIC_VALIDATION, false, null)) {
			return;
		}

		List<?> notifications = event.getNotifications();
		for (Object o : notifications) {
			if (o instanceof Notification) {
				Notification notification = (Notification) o;
				if (notification.getNotifier() instanceof EObject) {
					final EObject eObject = (EObject) notification.getNotifier();
					final String oldUri = ValidationUtil.computeOldURI(eObject, notification.getOldStringValue());
					final String newUri = EcoreUtil.getURI(eObject).toString();
					if (oldUri == null || oldUri.equals(newUri)) { // finally, we have no job to perform.
						return;
					}

					WorkspaceJob job = new WorkspaceJob(Messages._Job_Automatic_Validation) {
						@Override
						public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
							try {
								ValidationMarkerManager.getInstance().modifyMarkersURI(EcorePlatformUtil.getFile(eObject), oldUri, newUri);
							} catch (CoreException cex) {
								PlatformLogUtil.logAsError(Activator.getDefault(), cex);
							}

							return Status.OK_STATUS;
						}
					};
					IResource iResource = null;
					if (eObject.eResource() != null) {
						iResource = WorkspaceSynchronizer.getFile(eObject.eResource());
					}
					if (iResource == null) {
						iResource = ResourcesPlugin.getWorkspace().getRoot();
					}

					IResourceRuleFactory ruleFactory = iResource.getWorkspace().getRuleFactory();
					job.setRule(new MultiRule(new ISchedulingRule[] { ruleFactory.modifyRule(iResource), ruleFactory.createRule(iResource) }));
					job.setPriority(Job.BUILD);
					job.schedule();

				}
			}
		}
	}

	@Override
	public boolean isPostcommitOnly() {
		return true;
	}

}
