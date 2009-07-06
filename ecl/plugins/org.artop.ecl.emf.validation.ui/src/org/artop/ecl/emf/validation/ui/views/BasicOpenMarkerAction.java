/***********************************************************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors: IBM Corporation - initial API and implementation
 * Sebastian Davids <sdavids@gmx.de> - Fix for Bug 73612 [Markers] "Open All" does not work with multi-select in the
 * bookmarks view
 **********************************************************************************************************************/

package org.artop.ecl.emf.validation.ui.views;

import org.artop.ecl.emf.ui.util.EcoreUIUtil;
import org.artop.ecl.emf.validation.markers.util.MarkerUtil;
import org.artop.ecl.emf.validation.ui.Activator;
import org.artop.ecl.platform.util.PlatformLogUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.ResourceUtil;
import org.eclipse.ui.navigator.CommonNavigator;
import org.eclipse.ui.statushandlers.StatusAdapter;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Action to open an editor on the selected bookmarks.
 */
public class BasicOpenMarkerAction extends MarkerSelectionProviderAction {

	private final String IMAGE_PATH = "elcl16/gotoobj_tsk.gif"; //$NON-NLS-1$

	private final String DISABLED_IMAGE_PATH = "dlcl16/gotoobj_tsk.gif"; //$NON-NLS-1$

	protected IWorkbenchPart part;

	protected IViewPart viewPart;

	protected IEditorInput editorInput;
	protected String editorId;

	/**
	 * Create a new instance of the receiver.
	 * 
	 * @param part
	 * @param provider
	 */
	public BasicOpenMarkerAction(IWorkbenchPart part, ISelectionProvider provider) {
		super(provider, MarkerMessages.openAction_title);
		this.part = part;
		ImageDescriptor imageDescriptor = Activator.getImageDescriptor(IMAGE_PATH);
		// TODO :aakar remove this after verification of image path
		Assert.isNotNull(imageDescriptor);
		setImageDescriptor(imageDescriptor);
		setDisabledImageDescriptor(Activator.getImageDescriptor(DISABLED_IMAGE_PATH));
		setEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {

		if (editorInput != null && editorId != null) {
			IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			IWorkbenchPage[] pages = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getPages();
			IViewReference[] viewReferences = pages[0].getViewReferences();
			for (IViewReference viewReference : viewReferences) {
				if (viewReference.getId().startsWith("org.artop.ab.gce.ui.epc.explorer")) {
					IViewPart viewPart = viewReference.getView(true);
					if (viewPart instanceof CommonNavigator) {
						setViewPart(viewPart);
						showInView();
					}
				}
			}
			try {
				page.openEditor(editorInput, editorId);
			} catch (PartInitException ex) {
				PlatformLogUtil.logAsError(Activator.getDefault(), ex);
			}
		} else {

			IMarker[] markers = getSelectedMarkers();
			for (IMarker marker : markers) {

				// optimization: if the active editor has the same input as the
				// selected marker then
				// RevealMarkerAction would have been run and we only need to
				// activate the editor
				IEditorPart editor = part.getSite().getPage().getActiveEditor();
				if (editor != null) {
					IEditorInput input = editor.getEditorInput();
					IFile file = ResourceUtil.getFile(input);
					if (file != null) {
						if (marker.getResource().equals(file)) {
							part.getSite().getPage().activate(editor);
						}
					}
				}

				if (marker.getResource() instanceof IFile) {
					try {
						IFile file = (IFile) marker.getResource();
						if (file.getLocation() == null || file.getLocationURI() == null) {
							return; // Abort if it cannot be opened
						}
						IDE.openEditor(part.getSite().getPage(), marker, OpenStrategy.activateOnOpen());
					} catch (PartInitException e) {
						// Open an error style dialog for PartInitException by
						// including any extra information from the nested
						// CoreException if present.

						// Check for a nested CoreException
						CoreException nestedException = null;
						IStatus status = e.getStatus();
						if (status != null && status.getException() instanceof CoreException) {
							nestedException = (CoreException) status.getException();
						}

						if (nestedException != null) {
							// Open an error dialog and include the extra
							// status information from the nested CoreException
							reportStatus(nestedException.getStatus());
						} else {
							// Open a regular error dialog since there is no
							// extra information to display
							reportError(e.getLocalizedMessage());
						}
					}
				}
			}
		}
	}

	protected void setViewPart(IViewPart viewPart) {
		this.viewPart = viewPart;
	}

	public IViewPart getViewPart() {
		return viewPart;
	}

	/**
	 * Report an error message
	 * 
	 * @param message
	 */
	private void reportError(String message) {
		IStatus status = new Status(IStatus.ERROR, Activator.getPluginId(), message);
		reportStatus(status);
	}

	/**
	 * Report the status
	 * 
	 * @param status
	 */
	private void reportStatus(IStatus status) {
		StatusAdapter adapter = new StatusAdapter(status);
		adapter.setProperty(StatusAdapter.TITLE_PROPERTY, MarkerMessages.OpenMarker_errorTitle);
		StatusManager.getManager().handle(adapter, StatusManager.SHOW);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.actions.SelectionProviderAction#selectionChanged(org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(Util.allConcreteSelection(selection));

		editorInput = null;
		editorId = null;
		if (selection.size() == 1) {
			Object element = selection.getFirstElement();
			if (element instanceof ProblemMarker) {
				ProblemMarker pm = (ProblemMarker) element;
				EObject eobject = MarkerUtil.getConnectedEObjectFromMarker(pm.getMarker());

				editorInput = EcoreUIUtil.createURIEditorInput(eobject);

				IEditorDescriptor defaultEditor = EcoreUIUtil.getDefaultEditor(eobject);
				if (defaultEditor != null) {
					editorId = defaultEditor.getId();
				}
			}
		}
	}

	// TO DO AAT TO ARTOP
	// public BasicEditingDomainHolder getEditingDomainHolder() {
	// return BasicEditingDomainHolder.INSTANCE;
	// }

	public void showInView() {
		// TO DO AAT TO ARTOP
		// CommonViewer viewer = ((CommonNavigator) viewPart).getCommonViewer();
		// EObject object = getEditingDomainHolder().getEditingDomain().getResourceSet().getEObject(((URIEditorInput)
		// editorInput).getURI(), false);
		// viewer.setExpandedState(object, true);
	}
}
