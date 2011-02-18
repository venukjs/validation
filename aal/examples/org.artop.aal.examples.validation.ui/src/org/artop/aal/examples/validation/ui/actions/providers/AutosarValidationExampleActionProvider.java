/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT, See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.examples.validation.ui.actions.providers;

import org.artop.aal.examples.validation.ui.actions.ValidateAutosarConstraintsAction;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * {@link AbstractArtopValidationExampleActionProvider Provider} for AUTOSAR validation example actions.
 * 
 * @since 1.1.2
 */
public class AutosarValidationExampleActionProvider extends AbstractArtopValidationExampleActionProvider {

	/**
	 * Action capable of filtering constraints so that only constraints belonging to an AUTOSAR category would be
	 * validated.
	 */
	private ValidateAutosarConstraintsAction validateAutosarConstraintsAction;

	/*
	 * @see org.eclipse.sphinx.emf.ui.actions.providers.BasicActionProvider#doInit()
	 */
	@Override
	public void doInit() {
		validateAutosarConstraintsAction = createValidateAutosarConstraintsAction();
	}

	protected ValidateAutosarConstraintsAction createValidateAutosarConstraintsAction() {
		return new ValidateAutosarConstraintsAction();
	}

	/*
	 * @see
	 * org.eclipse.sphinx.emf.validation.ui.actions.providers.AbstractValidationActionProvider#populateActions(org.eclipse
	 * .jface.action.IMenuManager, org.eclipse.jface.viewers.IStructuredSelection, boolean)
	 */
	@Override
	protected void populateActions(IMenuManager menu, IStructuredSelection selection, boolean enabled) {
		validateAutosarConstraintsAction.selectionChanged(selection);
		validateAutosarConstraintsAction.setEnabled(enabled);
		menu.add(new ActionContributionItem(validateAutosarConstraintsAction));
	}
}
