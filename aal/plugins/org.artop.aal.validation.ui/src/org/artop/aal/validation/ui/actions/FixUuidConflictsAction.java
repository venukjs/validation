/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT, Geensys and others.
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
package org.artop.aal.validation.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.artop.aal.common.metamodel.AutosarReleaseDescriptor;
import org.artop.aal.validation.ui.internal.messages.Messages;
import org.artop.ecl.emf.metamodel.IMetaModelDescriptor;
import org.artop.ecl.emf.metamodel.MetaModelDescriptorRegistry;
import org.artop.ecl.emf.util.EObjectUtil;
import org.artop.ecl.emf.util.EcorePlatformUtil;
import org.artop.ecl.emf.util.WorkspaceEditingDomainUtil;
import org.artop.ecl.emf.util.WorkspaceTransactionUtil;
import org.artop.ecl.emf.validation.ui.Activator;
import org.artop.ecl.platform.ui.util.ExtendedPlatformUI;
import org.artop.ecl.platform.util.ExtendedPlatform;
import org.artop.ecl.platform.util.PlatformLogUtil;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * Action that is responsible for computing conflicts of UUID between all Identifiable objects and then fixing theses
 * conflicts by generating new UUIDs and setting them on concerned Identifiable objects.
 */
public class FixUuidConflictsAction extends BaseSelectionListenerAction {

	/**
	 * The name of the UUID feature given by AUTOSAR meta-models.
	 */
	private static final String FEATURE_NAME_UUID = "uuid"; //$NON-NLS-1$

	/**
	 * Constructor.
	 */
	public FixUuidConflictsAction() {
		super(Messages._UI_FixUuidConflicts_item);
		setDescription(Messages._UI_FixUuidConflicts_desc);
	}

	/*
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		final int[] resolvedConflicts = new int[1];
		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {
			public void run(final IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
				// FIXME Why needs this to be executed in the UI (asyncExec)? If so, shouldn't it rather be a syncExec?
				shell.getDisplay().asyncExec(new Runnable() {
					public void run() {
						resolvedConflicts[0] = fixUuidConflicts(progressMonitor);
					}
				});
			}
		};

		// FIXME Is that needed?
		// if (eclipseResourcesUtil != null) { runnableWithProgress =
		// eclipseResourcesUtil.getWorkspaceModifyOperation(runnableWithProgress); }

		try {
			// This runs the operation, and shows progress.
			// (It appears to be a bad thing to fork this onto another thread.)
			//
			new ProgressMonitorDialog(shell).run(true, true, runnableWithProgress);
		} catch (Exception exception) {
			PlatformLogUtil.logAsError(Activator.getDefault(), exception);
		}

		String title = Messages._UI_FixUuidConflicts_item;
		String message = NLS.bind(Messages._UI_FixUuidConflicts_result, resolvedConflicts[0]);
		MessageDialog.openInformation(ExtendedPlatformUI.getDisplay().getActiveShell(), title, message);
	}

	/**
	 * Fixes UUID conflicts.
	 * <p>
	 * First, retrieves objects concerned by such a conflict. Then re-generate a new UUID for these objects.
	 * 
	 * @param monitor
	 *            The progress monitor to used in order to display the progress.
	 * @return The number of conflicts that have been resolved.
	 */
	private int fixUuidConflicts(final IProgressMonitor monitor) {
		// Number of conflicts that would have been resolved
		int resolvedConflicts = 0;

		// The label of the write transaction
		String label = Messages._UI_FixUuidConflicts_item;
		// The default transaction options
		Map<String, Object> options = WorkspaceTransactionUtil.getDefaultTransactionOptions();

		// Retrieve UUID conflicts
		HashSet<EObject> conflicts = getUuidConflicts();

		monitor.beginTask(Messages._UI_FixUuidConflicts_command, conflicts.size());

		for (final EObject eObject : conflicts) {
			TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(eObject);
			if (editingDomain != null) {
				Runnable runnable = new Runnable() {
					public void run() {
						setNewUUID(eObject);
						monitor.worked(1);
					}
				};
				IOperationHistory history = WorkspaceTransactionUtil.getOperationHistory(editingDomain);
				try {
					WorkspaceTransactionUtil.executeInWriteTransaction(editingDomain, runnable, label, history, options, monitor);
				} catch (OperationCanceledException ex) {
					break;
				} catch (ExecutionException ex) {
					PlatformLogUtil.logAsError(Activator.getDefault(), ex);
				}
				resolvedConflicts++;
			}
		}

		monitor.done();

		return resolvedConflicts;
	}

	/**
	 * @return The list objects that have an UUID conflicting with the UUID of another object.
	 */
	private HashSet<EObject> getUuidConflicts() {

		// Object whose UUID conflicts with another one.
		HashSet<EObject> conflicts = new HashSet<EObject>();

		// Map used as a cache to store existing UUIDs.
		HashMap<String, EObject> map = new HashMap<String, EObject>();

		List<EObject> selectedObjects = getSelectedModelObjects();

		for (EObject selectedObject : selectedObjects) {
			for (TreeIterator<EObject> content = selectedObject.eAllContents(); content.hasNext();) {
				EObject eObj = content.next();

				// UUID of the current object. If no found, just goes on.
				String uuid = getUUID(eObj);
				if (uuid == null) {
					continue;
				}
				// If UUID is missing, keep object.
				if (uuid.length() == 0) {
					conflicts.add(eObj);
				}

				if (map.containsKey(uuid) && !eObj.equals(map.get(uuid))) {
					conflicts.add(eObj);
				} else {
					map.put(uuid, eObj);
				}
			}
		}
		return conflicts;
	}

	/**
	 * @param eObject
	 *            The Identifiable whose UUID must be returned.
	 * @return The UUID of the given Identifiable; or <code>null</code> if not an Identifiable.
	 */
	private String getUUID(EObject eObject) {
		String uuid = null;
		EStructuralFeature structuralFeature = EObjectUtil.getEStructuralFeature(eObject, FEATURE_NAME_UUID);
		if (structuralFeature != null) {
			Object value = eObject.eGet(structuralFeature);
			uuid = value != null ? value.toString() : ""; //$NON-NLS-1$
		}
		return uuid;
	}

	/**
	 * @param eObject
	 *            The Identifiable object whose UUID must be re-generated.
	 */
	private void setNewUUID(EObject eObject) {
		EStructuralFeature structuralFeature = EObjectUtil.getEStructuralFeature(eObject, FEATURE_NAME_UUID);
		if (structuralFeature != null) {
			eObject.eSet(structuralFeature, generateUUID());
		}
	}

	/**
	 * @return A new generated UUID.
	 */
	private String generateUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	/**
	 * Due to performance overhead, its just called before running the action to init the list of selected model objects
	 * 
	 * @param selection
	 *            the current selection
	 */
	protected List<EObject> getSelectedModelObjects() {
		IStructuredSelection selection = getStructuredSelection();
		List<EObject> result = new ArrayList<EObject>();
		List<IFile> files = new ArrayList<IFile>();
		for (Object selectedObject : selection.toList()) {
			if (selectedObject instanceof IProject) {
				IProject project = (IProject) selectedObject;
				if (project.isAccessible()) {
					files.addAll(ExtendedPlatform.getAllFiles((IProject) selectedObject, true));
				}
			} else if (selectedObject instanceof IFolder) {
				IFolder folder = (IFolder) selectedObject;
				if (folder.isAccessible()) {
					files.addAll(ExtendedPlatform.getAllFiles((IFolder) selectedObject));
				}
			} else if (selectedObject instanceof IFile) {
				IFile file = (IFile) selectedObject;
				if (file.isAccessible()) {
					files.add((IFile) selectedObject);
				}
			} else if (selectedObject instanceof EObject) {
				result.add((EObject) selectedObject);
			} else if (selectedObject instanceof IWrapperItemProvider) {
				Object object = AdapterFactoryEditingDomain.unwrap(selectedObject);
				if (object instanceof EObject) {
					result.add((EObject) object);
				}
			}
		}
		if (!files.isEmpty()) {
			// If selected object is a file, get the mapped model root
			for (IFile file : files) {
				// Get model from workspace file
				EObject modelRoot = EcorePlatformUtil.getModelRoot(file);
				if (modelRoot != null) {
					result.add(modelRoot);
				}
			}
		}
		return result;
	}

	/**
	 * Updates this action in response to the given selection.
	 * <p>
	 * Computes a custom enablement in order to deactivate action when a {@linkplain IFolder folder} containing no
	 * AUTOSAR resource is selected.
	 * 
	 * @param selection
	 *            The new selection.
	 * @return <ul>
	 *         <li><tt><b>true</b>&nbsp;&nbsp;</tt> if this action is enabled for the given selection;</li> <li><tt>
	 *         <b>false</b>&nbsp;</tt> otherwise.</li>
	 *         </ul>
	 * @see org.eclipse.ui.actions.BaseSelectionListenerAction#updateSelection(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	protected boolean updateSelection(IStructuredSelection selection) {
		return isActionEnabled(selection);
	}

	private boolean isActionEnabled(IStructuredSelection selection) {
		for (Object selectedObject : selection.toList()) {
			boolean enabled = isActionEnabled(selectedObject);
			if (enabled) {
				// When true, return and stop iteration on multi-selection
				return enabled;
			} else {
				// May be enabled for other objects in the multi-selection
				continue;
			}
		}
		return false;
	}

	/**
	 * Only care about case where selection is a {@linkplain IFolder folder} by verifying if that folder contains
	 * AUTOSAR resources or not.
	 * 
	 * @param selectedObject
	 *            One selection element.
	 * @return <ul>
	 *         <li><tt><b>false</b>&nbsp;</tt> if the given selected object is a {@linkplain IFolder folder} containing
	 *         no AUTOSAR resource;</li>
	 *         <li><tt><b>true</b>&nbsp;&nbsp;</tt> otherwise.</li>
	 *         </ul>
	 */
	private boolean isActionEnabled(Object selectedObject) {
		if (selectedObject instanceof IFolder) {
			IFolder folder = (IFolder) selectedObject;
			Collection<TransactionalEditingDomain> editingDomains = WorkspaceEditingDomainUtil.getEditingDomains(folder);
			return isEnabled(ExtendedPlatform.getAllFiles(folder), editingDomains);
		}
		return true;
	}

	/**
	 * @param files
	 *            The list of {@linkplain IFile file}s of container may contain.
	 * @param editingDomains
	 *            The list of {@linkplain TransactionalEditingDomain editing domain}s that can be retrieved from a given
	 *            {@linkplain IContainer container} (the selected one).
	 * @return <ul>
	 *         <li><tt><b>true</b>&nbsp;&nbsp;</tt> if the given selected object is a {@linkplain IFolder folder}
	 *         containing at least one AUTOSAR resource;</li>
	 *         <li><tt><b>false</b>&nbsp;</tt> otherwise.</li>
	 *         </ul>
	 */
	private boolean isEnabled(Collection<IFile> files, Collection<TransactionalEditingDomain> editingDomains) {
		for (IFile file : files) {
			IMetaModelDescriptor descriptor = MetaModelDescriptorRegistry.INSTANCE.getDescriptor(file);
			if (descriptor instanceof AutosarReleaseDescriptor) {
				for (TransactionalEditingDomain editingDomain : editingDomains) {
					EObject object = EcorePlatformUtil.getModelRoot(editingDomain, file);
					if (object != null) {
						return true;
					}
				}
			}
		}
		return false;
	}
}
