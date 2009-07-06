/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT, Geensys and others.
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
package org.artop.ecl.emf.validation.ui.actions.providers;

import java.util.Collection;

import org.artop.ecl.emf.ui.actions.providers.BasicActionProvider;
import org.artop.ecl.emf.util.EcorePlatformUtil;
import org.artop.ecl.emf.util.WorkspaceEditingDomainUtil;
import org.artop.ecl.platform.util.ExtendedPlatform;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.IWrapperItemProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.CommonActionProvider;

/**
 * An abstract base class for {@linkplain BasicActionProvider action provider}s that are dedicated to validation related
 * {@link IAction}s.
 */
public abstract class AbstractValidationActionProvider extends BasicActionProvider {

	/*
	 * @see
	 * org.artop.ecl.emf.ui.actions.providers.BasicActionProvider#fillSubMenu(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	public void fillSubMenu(IMenuManager subMenuManager) {
		// Retrieve selection
		IStructuredSelection selection = (IStructuredSelection) getContext().getSelection();

		// Calculate enablement
		boolean enabled = isEnabled(selection);

		// Performance optimization: Rather than overriding updateSelection() and individually calculating the
		// enablement status on each validation action we calculate it once here and then set it on all validation
		// actions
		populateActions(subMenuManager, selection, enabled);
	}

	/**
	 * Populates specified {@linkplain IMenuManager menu} with the {@linkplain org.eclipse.jface.action.IAction action}s
	 * provided by this {@link AbstractValidationActionProvider action provider} implementation. Initializes
	 * {@linkplain org.eclipse.jface.action.IAction action}s with given {@linkplain IStructuredSelection selection} and
	 * <code>enabled</code> status.
	 * 
	 * @param menu
	 *            The {@linkplain IMenuManager menu} actions should be added to.
	 * @param selection
	 *            The {@linkplain IStructuredSelection selection} actions must act upon.
	 * @param enabled
	 *            Enablement status indicating if actions should be enabled or not.
	 */
	protected abstract void populateActions(IMenuManager menu, IStructuredSelection selection, boolean enabled);

	/**
	 * Computes the enable state of this {@linkplain CommonActionProvider action provider} according to the given
	 * {@link IStructuredSelection selection}.
	 * <p>
	 * <table>
	 * <tr valign=top>
	 * <td><b>Note</b>&nbsp;&nbsp;</td>
	 * <td>This method is not supposed to be overridden by client applications for providing a custom enablement
	 * computation; in that purpose overriding {@linkplain #isEnabled(Object)} is preferable.</td>
	 * </tr>
	 * </table>
	 * 
	 * @param selection
	 *            The {@linkplain IStructuredSelection selection} for which enablement must be computed.
	 * @return <ul>
	 *         <li><tt><b>true</b>&nbsp;&nbsp;</tt> if this {@linkplain action provider} is enabled for the specified
	 *         {@link IStructuredSelection selection};</li> <li><tt><b>false</b>&nbsp;</tt> otherwise.</li>
	 *         </ul>
	 */
	protected final boolean isEnabled(IStructuredSelection selection) {
		for (Object selectedObject : selection.toList()) {
			boolean enabled = isEnabled(selectedObject);
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
	 * Computes the enable state of this {@linkplain CommonActionProvider action provider} according to the given
	 * {@link Object selectedObject}.
	 * 
	 * @param selectedObject
	 *            The object currently selected; object from which enable state for this
	 *            {@linkplain CommonActionProvider action provider} must be computed.
	 * @return <ul>
	 *         <li><tt><b>true</b>&nbsp;&nbsp;</tt> if this {@linkplain action provider} is enabled for the given <code>
	 *         selectedObject</code>
	 *         ;</li>
	 *         <li><tt><b>false</b>&nbsp;</tt> otherwise.</li>
	 *         </ul>
	 */
	protected boolean isEnabled(Object selectedObject) {
		if (selectedObject instanceof IProject) {
			IProject project = (IProject) selectedObject;
			Collection<TransactionalEditingDomain> editingDomains = WorkspaceEditingDomainUtil.getEditingDomains(project);
			return isEnabled(ExtendedPlatform.getAllFiles(project, true), editingDomains);

		} else if (selectedObject instanceof IFolder) {
			IFolder folder = (IFolder) selectedObject;
			Collection<TransactionalEditingDomain> editingDomains = WorkspaceEditingDomainUtil.getEditingDomains(folder);
			return isEnabled(ExtendedPlatform.getAllFiles(folder), editingDomains);

		} else if (selectedObject instanceof IFile) {
			IFile file = (IFile) selectedObject;
			return EcorePlatformUtil.getModelRoot(file) != null;

		} else if (selectedObject instanceof EObject) {
			// FIXME should test if it proxy or eResource not null
			return true;

		} else if (selectedObject instanceof IWrapperItemProvider) {
			return AdapterFactoryEditingDomain.unwrap(selectedObject) instanceof EObject;
		}
		return false;
	}

	private boolean isEnabled(Collection<IFile> files, Collection<TransactionalEditingDomain> editingDomains) {
		for (IFile file : files) {
			for (TransactionalEditingDomain editingDomain : editingDomains) {
				EObject object = EcorePlatformUtil.getModelRoot(editingDomain, file);
				if (object != null) {
					return true;
				}
			}
		}
		return false;
	}
}
