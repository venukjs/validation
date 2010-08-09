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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.artop.aal.validation.util.Messages;
import org.artop.ecl.emf.util.EObjectUtil;
import org.artop.ecl.emf.util.EcorePlatformUtil;
import org.artop.ecl.emf.validation.diagnostic.ExtendedDiagnostician;
import org.artop.ecl.emf.validation.markers.ValidationMarkerManager;
import org.artop.ecl.emf.validation.preferences.IValidationPreferences;
import org.artop.ecl.platform.IExtendedPlatformConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceRuleFactory;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.IJobManager;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.MultiRule;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.osgi.util.NLS;

public abstract class AbstractAutomaticValidationListener extends ResourceSetListenerImpl {

	/**
	 * The notification filter to apply.
	 * <p>
	 * This listener should be notified only in case of Identifiable <em>short name</em> modification or modification on
	 * the resource.
	 */
	protected final static org.eclipse.emf.transaction.NotificationFilter filter = org.eclipse.emf.transaction.NotificationFilter.NOT_TOUCH;

	protected Set<EObject> rootObjectsToValidate = new HashSet<EObject>();

	private class EOD {
		private EObject eObject;
		private int depth;

		EOD(EObject eObject, int depth) {
			this.eObject = eObject;
			this.depth = depth;
		}

		/**
		 * Accessor
		 */
		public EObject getEObject() {
			return eObject;
		}

		/**
		 * Accessor
		 */
		public int getDepth() {
			return depth;
		}
	}

	/**
	 * Filter in order to only keep live constraints
	 */
	protected final static IConstraintFilter rulesFilter = new IConstraintFilter() {
		public boolean accept(IConstraintDescriptor constraint, EObject target) {
			return constraint.isLive();
		}

	};

	protected AbstractAutomaticValidationListener() {
		this(filter);
	}

	protected AbstractAutomaticValidationListener(NotificationFilter filter) {
		super(filter);
	}

	/**
	 * This method allow user to define the scoped resource to listen.
	 * 
	 * @param resource
	 *            an {@link Resource}
	 * @return value of the test
	 */
	abstract protected boolean isTargetResource(Resource resource);

	/**
	 * This method test if the change caught is on shortName only.
	 * 
	 * @param event
	 * @return
	 */
	abstract protected boolean isShortNameChange(ResourceSetChangeEvent event);

	@Override
	public void resourceSetChanged(ResourceSetChangeEvent event) {

		// Let's check if the automatic validation is enabled
		if (!Platform.getPreferencesService().getBoolean(org.artop.ecl.emf.validation.Activator.PLUGIN_ID,
				IValidationPreferences.PREF_ENABLE_AUTOMATIC_VALIDATION, false, null)) {
			return;
		}

		List<?> notifications = event.getNotifications();

		if (isShortNameChange(event)) {
			// Everything must be been tested into the isShorNameChange method.
			EObject eo = (EObject) ((Notification) notifications.get(0)).getNotifier();
			if (eo.eContainer() != null) {
				launchValidation(eo.eContainer(), EObjectUtil.DEPTH_ONE);
			}
			return;
		}

		Set<EObject> objectsToValidate = new HashSet<EObject>();
		Set<EObject> eObjectRootToValidate = new HashSet<EObject>();
		for (Object o : notifications) {
			if (o instanceof Notification) {
				Notification notification = (Notification) o;

				if (notification instanceof ENotificationImpl && notification.getNotifier() instanceof EObject) {
					EObject eObject = (EObject) notification.getNotifier();
					if (eObject != null) {
						if (eObject.eResource() != null && isTargetResource(eObject.eResource())) {
							objectsToValidate.add(eObject);
						}
					}
				} else if (notification.getNotifier() instanceof Resource && isTargetResource((Resource) notification.getNotifier())
						&& notification.getFeatureID(Resource.class) == Resource.RESOURCE__IS_LOADED && !notification.getOldBooleanValue()
						&& notification.getNewBooleanValue()

				) {
					Resource resource = (Resource) notification.getNotifier();
					if (resource.getContents() != null && !resource.getContents().isEmpty() && resource.getContents().get(0) != null) {
						IFile file = EcorePlatformUtil.getFile(resource);
						if (file != null && !URI.createURI(file.getLocationURI().toString(), true).isPlatformPlugin()) {
							eObjectRootToValidate.add(resource.getContents().get(0));
						}
					}
				}
			}
		}

		// Let's organize the tgtObjects set to avoid double validation.
		HashSet<EObject> objectToRemove = new HashSet<EObject>();
		HashSet<EObject> objectToDeeplyValidate = new HashSet<EObject>();

		HashSet<EOD> targets = new HashSet<EOD>();

		for (EObject current : objectsToValidate) {
			if (current.eIsProxy()) {
				objectToRemove.add(current);
				continue;
			}

			if (current.eContainer() != null && objectsToValidate.contains(current.eContainer())) {
				objectToDeeplyValidate.add(current);
				objectToRemove.add(current);
				targets.add(new EOD(current, EObjectUtil.DEPTH_INFINITE));
			}

			if (current.eContainer() != null && !objectsToValidate.contains(current.eContainer())
					&& !objectToDeeplyValidate.contains(current.eContainer())) {
				targets.add(new EOD(current.eContainer(), EObjectUtil.DEPTH_ZERO));
			}

		}
		if (objectToRemove.size() > 0) {
			objectsToValidate.removeAll(objectToRemove);
		}
		for (EObject current : objectsToValidate) {
			targets.add(new EOD(current, EObjectUtil.DEPTH_ONE));
		}

		if (eObjectRootToValidate.size() > 0) {
			validateRootObjects(eObjectRootToValidate);
		}
		if (eObjectRootToValidate == null || eObjectRootToValidate.size() < 1 && !targets.isEmpty()) {
			validateObjects(targets);
		}

	}

	/**
	 * Group all object to validate into a single job which does that and perform an {@link EObjectUtil#DEPTH_INFINITE}
	 * vaildation on its.
	 * 
	 * @param tgtResourceObjects
	 *            list of target {@link EObject}.
	 */
	private void validateRootObjects(Set<EObject> objectstToValidate) {
		rootObjectsToValidate.addAll(objectstToValidate);
		final IJobManager jobManager = Job.getJobManager();

		// Only validate when there is only one workspace job left
		int modelLoadingJobCount = jobManager.find(IExtendedPlatformConstants.FAMILY_MODEL_LOADING).length;
		if (modelLoadingJobCount > 1) {
			return;
		}
		final Set<EObject> objects = new HashSet<EObject>(rootObjectsToValidate);
		clearObjectsRoot();
		WorkspaceJob job = new WorkspaceJob(Messages._Job_Automatic_Validation) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				int totalWork = objects.size();
				/*
				 * Setup progress monitor : - Waiting for jobs to finish (10) - Automatic validation (90)
				 */
				monitor.beginTask("Validating...", totalWork); //$NON-NLS-1$
				try {

					for (EObject current : objects) {
						String uriFragment = null;
						if (current.eContainer() == null) {
							if (current.eResource() != null) {
								uriFragment = current.eResource().getURI().toPlatformString(true);
							}
						} else {
							if (current.eResource() != null) {
								uriFragment = current.eResource().getURIFragment(current);
							}
						}
						if (uriFragment != null) {
							if (monitor.isCanceled()) {
								return Status.CANCEL_STATUS;
							}
							monitor.setTaskName(NLS.bind(Messages._Job_Automatic_Validation_Monitor_Info, uriFragment));
						}
						ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
						Diagnostic diagnostic = diagnostician.validate(current, rulesFilter, EObjectUtil.DEPTH_INFINITE);
						monitor.worked(1);
						ValidationMarkerManager.getInstance().handleDiagnostic(diagnostic, EObjectUtil.DEPTH_INFINITE);
					}
				} catch (Exception ex) {
					// TODO
				} finally {
					clearObjectsRoot();
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};

		ArrayList<ISchedulingRule> myRules = new ArrayList<ISchedulingRule>();
		for (EObject eObject : objects) {
			IResource resource = EcorePlatformUtil.getFile(eObject);
			if (resource != null) {
				IResourceRuleFactory ruleFactory = resource.getWorkspace().getRuleFactory();
				myRules.add(ruleFactory.refreshRule(resource));
			}
		}

		job.setRule(new MultiRule(myRules.toArray(new ISchedulingRule[myRules.size()])));
		job.setPriority(Job.DECORATE);
		job.schedule();

	}

	protected void clearObjectsRoot() {
		rootObjectsToValidate.clear();
	}

	/**
	 * Perform validation on {@link EObject} stored after the resourceSetChange listener ops.
	 * 
	 * @param target
	 *            the objects that should be validated
	 * @see EOD
	 */
	protected void validateObjects(final HashSet<EOD> targets) {
		final IJobManager jobManager = Job.getJobManager();
		boolean wait = false;
		if (jobManager.find(IExtendedPlatformConstants.FAMILY_MODEL_LOADING).length > 0) {
			wait = true; // If there are load jobs running, we wait for!
		}
		final boolean waitInJob = wait;
		WorkspaceJob job = new WorkspaceJob("Automatic validation processing") { //$NON-NLS-1$
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				int max_err = Platform.getPreferencesService().getInt(org.artop.ecl.emf.validation.Activator.PLUGIN_ID,
						IValidationPreferences.PREF_MAX_NUMBER_OF_ERRORS, IValidationPreferences.PREF_MAX_NUMBER_OF_ERRORS_DEFAULT, null);
				int nb_err = 0;
				/*
				 * Setup progress monitor : - Waiting for jobs to finish (6) - Automatic validation (94)
				 */
				monitor.beginTask("Automatic validation processing", 6 + targets.size()); //$NON-NLS-1$
				try {
					if (waitInJob) {
						try {
							jobManager.join(IExtendedPlatformConstants.FAMILY_MODEL_LOADING, new SubProgressMonitor(monitor, 3));
						} catch (InterruptedException iEx) {
							// TODO
						}
					} else {
						monitor.worked(6);
					}
					if (!monitor.isCanceled()) {
						for (EOD current : targets) {
							if (monitor.isCanceled()) {
								break;
							}
							ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
							Diagnostic diagnostic = diagnostician.validate(current.getEObject(), rulesFilter, current.getDepth());
							ValidationMarkerManager.getInstance().handleDiagnostic(diagnostic, current.getDepth());
							monitor.worked(1);
							for (Diagnostic d : diagnostic.getChildren()) {
								if (d.getSeverity() != Diagnostic.OK) {
									++nb_err;
								}
							}
							if (nb_err > max_err) {
								monitor.setCanceled(true);
							}
						}
					}
				} catch (Exception ex) {
					// TODO
				} finally {
					monitor.done();
				}
				return Status.OK_STATUS;
			}
		};

		ArrayList<ISchedulingRule> myRules = new ArrayList<ISchedulingRule>();
		ArrayList<IResource> irs = new ArrayList<IResource>();
		for (EOD current : targets) {
			IResource r = EcorePlatformUtil.getFile(current.getEObject());
			if (r != null && !irs.contains(r)) {
				irs.add(r);
				IResourceRuleFactory ruleFactory = r.getWorkspace().getRuleFactory();
				myRules.add(ruleFactory.modifyRule(r));
				myRules.add(ruleFactory.createRule(r));
			}
		}

		job.setRule(new MultiRule(myRules.toArray(new ISchedulingRule[myRules.size()])));
		job.setPriority(Job.BUILD);
		job.schedule();
	}

	/**
	 * Perform validation of a {@link EObject}.
	 * 
	 * @param eObject
	 *            the object that should be validated
	 * @param the
	 *            validation depth
	 */
	protected void launchValidation(final EObject eObject, final int depth) {

		WorkspaceJob job = new WorkspaceJob(Messages._Job_Automatic_Validation) {
			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
				Diagnostic diagnostic = diagnostician.validate(eObject, rulesFilter, depth);

				ValidationMarkerManager.getInstance().handleDiagnostic(diagnostic, depth);

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

		if (!isTargetResource(eObject.eResource())) {
			return;
		}

		IResourceRuleFactory ruleFactory = iResource.getWorkspace().getRuleFactory();
		job.setRule(new MultiRule(new ISchedulingRule[] { ruleFactory.modifyRule(iResource), ruleFactory.createRule(iResource) }));
		job.setPriority(Job.BUILD);
		job.schedule();
	}

	@Override
	public boolean isPostcommitOnly() {
		return true;
	}

}
