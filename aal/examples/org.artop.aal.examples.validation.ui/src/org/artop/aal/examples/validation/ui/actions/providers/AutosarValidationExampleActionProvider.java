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
package org.artop.aal.examples.validation.ui.actions.providers;

import org.artop.aal.examples.validation.ui.actions.ValidateAutosarConstraintsAction;
import org.artop.ecl.examples.validation.ui.actions.providers.BasicValidationExampleActionProvider;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * @since 1.1.2
 */
public class AutosarValidationExampleActionProvider extends BasicValidationExampleActionProvider {

	/**
	 * Action capable of filtering constraints so that only constraints belonging to an AUTOSAR category would be
	 * validated.
	 */
	private ValidateAutosarConstraintsAction validateAutosarConstraintsAction;

	/*
	 * @see org.artop.ecl.examples.validation.ui.actions.providers.BasicValidationExampleActionProvider#doInit()
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
	 * org.artop.ecl.examples.validation.ui.actions.providers.BasicValidationExampleActionProvider#populateActions(org
	 * .eclipse.jface.action.IMenuManager, org.eclipse.jface.viewers.IStructuredSelection, boolean)
	 */
	@Override
	protected void populateActions(IMenuManager menu, IStructuredSelection selection, boolean enabled) {
		validateAutosarConstraintsAction.selectionChanged(selection);
		validateAutosarConstraintsAction.setEnabled(enabled);
		menu.add(new ActionContributionItem(validateAutosarConstraintsAction));
	}
}
