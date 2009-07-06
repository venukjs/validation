package org.artop.ecl.emf.validation.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.artop.ecl.emf.util.EObjectUtil;
import org.artop.ecl.emf.util.EcorePlatformUtil;
import org.artop.ecl.emf.util.WorkspaceEditingDomainUtil;
import org.artop.ecl.emf.util.WorkspaceTransactionUtil;
import org.artop.ecl.emf.validation.ui.Activator;
import org.artop.ecl.emf.validation.ui.util.Messages;
import org.artop.ecl.platform.ui.util.ExtendedPlatformUI;
import org.artop.ecl.platform.util.ExtendedPlatform;
import org.artop.ecl.platform.util.PlatformLogUtil;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.AbstractEMFOperation;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * @deprecated Use {@linkplain org.artop.aal.validation.ui.actions.FixUuidConflictsAction} instead.
 */
@Deprecated
public class FixUuidConflictsAction extends BaseSelectionListenerAction {

	private static final String FEATURE_NAME_UUID = "uuid"; //$NON-NLS-1$

	/**
	 * The selected objects from which the action should be launched.
	 */
	// protected List<EObject> selectedObjects;
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
	 * @param progressMonitor
	 *            The progress monitor to used in order to display the progress.
	 */
	private int fixUuidConflicts(final IProgressMonitor progressMonitor) {

		final HashSet<EObject> conflicts = getUuidConflicts();
		progressMonitor.beginTask(Messages._UI_FixUuidConflicts_command, conflicts.size());
		int resolvedConflicts = 0;

		for (final EObject eObject : conflicts) {
			TransactionalEditingDomain editingDomain = WorkspaceEditingDomainUtil.getEditingDomain(eObject);
			if (editingDomain != null) {
				try {
					final String label = Messages._UI_FixUuidConflicts_item;
					Map<String, Object> options = WorkspaceTransactionUtil.getDefaultSaveTransactionOptions();
					IUndoableOperation operation = new AbstractEMFOperation(editingDomain, label, options) {
						@Override
						protected IStatus doExecute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
							try {
								setNewUUID(eObject);
								monitor.worked(1);
								return Status.OK_STATUS;
							} catch (Exception ex) {
								if (ex instanceof OperationCanceledException) {
									throw (OperationCanceledException) ex;
								} else if (ex instanceof ExecutionException) {
									throw (ExecutionException) ex;
								} else {
									throw new ExecutionException(NLS.bind(org.artop.ecl.emf.internal.messages.Messages.problem_transactionFailed,
											label), ex);
								}
							}
						}
					};
					IOperationHistory history = WorkspaceTransactionUtil.getOperationHistory(editingDomain);
					IStatus status = history.execute(operation, progressMonitor, null);
					if (status.getSeverity() == IStatus.CANCEL) {
						break;
					}
					resolvedConflicts++;
				} catch (ExecutionException ex) {
					PlatformLogUtil.logAsError(Activator.getDefault(), ex);
				}
			}
		}

		progressMonitor.done();
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

}
