/**
 * <copyright>
 *
 * Copyright (c) Continental AG and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 *
 * Contributors:
 *     Continental AG - initial implementation
 *
 * </copyright>
 */
package org.artop.aal.validation.ui.actions;

import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.services.splitting.AmbiguityHandler;
import org.artop.aal.gautosar.services.splitting.LoggingAmbiguityHandler;
import org.artop.aal.gautosar.services.splitting.Splitable;
import org.artop.aal.gautosar.services.splitting.SplitableEObjectsProvider;
import org.artop.aal.gautosar.services.splitting.SplitableElementServiceProvider;
import org.artop.aal.gautosar.services.splitting.handler.StubHelper;
import org.artop.aal.validation.ui.internal.messages.Messages;
import org.artop.aal.workspace.util.AutosarPlatformUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sphinx.emf.util.EcorePlatformUtil;
import org.eclipse.sphinx.emf.validation.stats.ValidationPerformanceStats;
import org.eclipse.sphinx.emf.validation.ui.actions.BasicValidateAction;
import org.eclipse.sphinx.platform.util.ExtendedPlatform;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;

/**
 * Action that is responsible for the validation of the merged AUTOSAR model.
 */
public class MergedAutosarValidationAction extends BasicValidateAction {

	private boolean displayBriefReport = false;

	/**
	 * Used to compute the merged model.
	 */
	private SplitableEObjectsProvider splitableEObjectsProvider;

	/**
	 * Used to determine if an EObject is splitable.
	 */
	private SplitableElementServiceProvider splitableElementServiceProvider;

	/**
	 * This is supplied as an alternative to the existing StubHelper. It does not attempt to alter the model.
	 */
	@Singleton
	private static class CustomStubHelper extends StubHelper {

		/*
		 * (non-Javadoc)
		 * @see org.artop.aal.gautosar.services.splitting.handler.StubHelper#removeStub(java.util.HashMap,
		 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject)
		 */
		@Override
		public EObject removeStub(HashMap<EStructuralFeature, ArrayList<EObject>> additionalMergeFeatures, EObject

		eObject1, EObject eObject2) {
			return super.removeStub(null, eObject1, eObject2);
		}

		/*
		 * (non-Javadoc)
		 * @see org.artop.aal.gautosar.services.splitting.handler.StubHelper#removeStub(java.util.HashMap,
		 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature)
		 */
		@Override
		protected EObject removeStub(HashMap<EStructuralFeature, ArrayList<EObject>> additionalMergeFeatures, EObject

		eObject1, EObject eObject2, EStructuralFeature feature) {
			return super.removeStub(null, eObject1, eObject2, feature);
		}

		/*
		 * (non-Javadoc)
		 * @see org.artop.aal.gautosar.services.splitting.handler.StubHelper#removeStub(java.util.HashMap,
		 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference)
		 */
		@Override
		protected EObject removeStub(HashMap<EStructuralFeature, ArrayList<EObject>> additionalMergeFeatures, EObject

		eObject1, EObject eObject2, EReference reference) {
			return super.removeStub(null, eObject1, eObject2, reference);
		}

		/*
		 * (non-Javadoc)
		 * @see org.artop.aal.gautosar.services.splitting.handler.StubHelper#removeStub(java.util.HashMap,
		 * org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EAttribute)
		 */
		@Override
		protected EObject removeStub(HashMap<EStructuralFeature, ArrayList<EObject>> additionalMergeFeatures, EObject

		eObject1, EObject eObject2, EAttribute attribute) {
			return super.removeStub(null, eObject1, eObject2, attribute);
		}

		/*
		 * (non-Javadoc)
		 * @see org.artop.aal.gautosar.services.splitting.handler.StubHelper#removeStub(java.util.HashMap,
		 * org.eclipse.emf.ecore.util.FeatureMap, org.eclipse.emf.ecore.util.FeatureMap)
		 */
		@Override
		protected FeatureMap removeStub(HashMap<EStructuralFeature, ArrayList<EObject>> additionalMergeFeatures,

		FeatureMap featureMap1, FeatureMap featureMap2) {
			return super.removeStub(null, featureMap1, featureMap2);
		}
	}

	/**
	 * Constructor.
	 */
	public MergedAutosarValidationAction() {
		super();
		// Set the menu item text for the merged validation
		super.setText(Messages._UI_MergedAutosarValidation_item);
		setDescription(Messages._UI_MergedAutosarValidation_desc);
		Injector injector = Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				bind(AmbiguityHandler.class).to(LoggingAmbiguityHandler.class);
				bind(StubHelper.class).to(CustomStubHelper.class);
			}
		});
		// needed to provide the merged context for validation
		splitableEObjectsProvider = injector.getInstance(SplitableEObjectsProvider.class);
		splitableElementServiceProvider = injector.getInstance(SplitableElementServiceProvider.class);
	}

	/**
	 * Due to performance overhead, it's just called before running the action to init the list of selected model
	 * objects. (Borrowed from Sphinx, because the Sphinx one is private.)
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
				Resource resource = EcorePlatformUtil.getResource(file);
				if (resource != null) {
					EList<EObject> contents = resource.getContents();
					if (!contents.isEmpty()) {
						EObject modelRoot = contents.get(0);
						if (modelRoot != null) {
							result.add(modelRoot);
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * Compute the merged model from {@link objects}.
	 *
	 * @param objects
	 *            for which to compute the merged model
	 * @return the merged model
	 */
	private List<EObject> mergedModel(final List<EObject> objects) {
		// fail fast for empty input cases
		if (objects.isEmpty()) {
			return Collections.emptyList();
		}
		final Collection<EObject> splitablesForObjects = splitableEObjectsProvider.splitablesFor(objects);
		if (splitablesForObjects.isEmpty()) {
			return Collections.emptyList();
		}

		// Compute the "top-level" objects for the input
		final int splitablesSize = splitablesForObjects.size();
		final List<EObject> uniqueObjects = new ArrayList<EObject>(splitablesSize);
		final SetMultimap<String, EClass> uniqueNameEClassPairs = LinkedHashMultimap.create(splitablesSize, 1);
		for (EObject obj : splitablesForObjects) {
			if (obj instanceof GAUTOSAR) {
				// GAUTOSAR encountered, merged model of the entire project must
				// be created, so simply return this instance of the root.
				return Lists.newArrayList(obj);
			} else if (isSplitableObject(obj)) {
				// otherwise, if the current object is a Splitable or a splitable EObject,
				final String qName = AutosarURIFactory.getAbsoluteQualifiedName(obj);
				final EClass eClass = obj.eClass();
				// decide whether to add it based on the uniqueness of its <AQN, EClass> pair.
				if (!uniqueNameEClassPairs.containsEntry(qName, eClass)) {
					uniqueNameEClassPairs.put(qName, eClass);
					uniqueObjects.add(obj);
				}
			}
		}

		return uniqueObjects.isEmpty() ? Collections.<EObject> emptyList() : uniqueObjects;
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

	/**
	 * Action enablement is decided as a logical OR of the respective enablements of the selected objects.
	 *
	 * @param selection
	 *            the selected objects
	 * @return indicates whether the action is enabled or not.
	 */
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
	 * Decides if the action should be enabled for this {@link EObject}.
	 *
	 * @param obj
	 *            the {@link EObject}
	 * @return true <b>iff</b> {@link obj} is an instance of {@link Splitable} or it is a splitable {@link EObject}.
	 */
	private boolean isSplitableObject(EObject obj) {
		if (obj instanceof Splitable) {
			return true;
		}
		return splitableElementServiceProvider.get(obj).isSplitable(obj.eClass());
	}

	/**
	 * Action becomes enabled if {@link selectedObject} is an AUTOSAR project, a {@link Splitable} object (from the
	 * AUTOSAR Browser), or a splitable EObject. For convenience it is enabled also on files and folders, but the effect
	 * is the same as whole project validation, because the root EClass (GAUTOSAR) is certainly present.
	 *
	 * @param selectedObject
	 *            One selection element.
	 * @return <ul>
	 *         <li><tt><b>true</b>&nbsp;</tt> if the given selected object is an AUTOSAR {@linkplain IProject project},
	 *         a {@linkplain Splitable splitable} object from the AUTOSAR Browser, or a splitable {@link EObject}.
	 *         <li><tt><b>false</b>&nbsp;</tt> otherwise.</li>
	 *         </ul>
	 */
	private boolean isActionEnabled(Object selectedObject) {
		if (selectedObject instanceof IProject) {
			return AutosarPlatformUtil.hasAutosarNature((IProject) selectedObject);
		} else if (selectedObject instanceof EObject) {
			return isSplitableObject((EObject) selectedObject);
		}
		return false;
	}

	/**
	 * This is almost a duplicate of the corresponding method in Sphinx, {@link BasicValidateAction#run()}, with the
	 * difference that it computes and passes the "merged model" for validation.
	 */
	@Override
	public void run() {
		final List<EObject> selectedModelObjects = getSelectedModelObjects();

		final Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		IRunnableWithProgress runnableWithProgress = new IRunnableWithProgress() {

			public void run(final IProgressMonitor progressMonitor) throws InvocationTargetException, InterruptedException {
				try {
					ValidationPerformanceStats.INSTANCE.openContext("Merged validation of " + selectedModelObjects.get(0)); //$NON-NLS-1$

					// the change from the default Sphinx behavior is here: validation is run on the merged model.
					final List<EObject> mergedModel = mergedModel(selectedModelObjects);

					final List<Diagnostic> diagnostics = validateMulti(mergedModel, progressMonitor);

					shell.getDisplay().asyncExec(new Runnable() {
						public void run() {
							if (progressMonitor.isCanceled()) {
								handleDiagnostic(mergedModel, Diagnostic.CANCEL_INSTANCE);
							} else if (diagnostics != null) {
								handleDiagnosticMulti(mergedModel, diagnostics, displayBriefReport);
							}

							try {
								PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
										.showView("org.eclipse.sphinx.examples.validation.ui.views.validation"); //$NON-NLS-1$
							} catch (PartInitException ex) {
								// Fail silent
							}
						}
					});

					ValidationPerformanceStats.INSTANCE.closeAndLogCurrentContext();
				} finally {
					progressMonitor.done();
				}
			}
		};

		try {
			// This runs the operation, and shows progress.
			// (It appears to be a bad thing to fork this onto another thread.)
			new ProgressMonitorDialog(shell).run(true, true, new WorkspaceModifyDelegatingOperation(runnableWithProgress));
		} catch (Exception exception) {
			EMFEditUIPlugin.INSTANCE.log(exception);
		}
	}
}
