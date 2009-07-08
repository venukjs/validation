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
package org.artop.ecl.examples.validation.ui.actions.providers;

import org.artop.ecl.emf.validation.ui.actions.providers.AbstractValidationActionProvider;
import org.artop.ecl.examples.common.ui.IArtopExampleMenuConstants;
import org.artop.ecl.examples.validation.ui.IValidationExampleMenuConstants;
import org.artop.ecl.examples.validation.ui.actions.BasicValidateSelectedCategoriesAction;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * @since 1.1.2
 */
public class BasicValidationExampleActionProvider extends AbstractValidationActionProvider {

	/**
	 * 
	 */
	private BasicValidateSelectedCategoriesAction validateCategoriesAction;

	/*
	 * @see org.artop.ecl.emf.ui.actions.providers.BasicActionProvider#doInit()
	 */
	@Override
	public void doInit() {
		validateCategoriesAction = createValidateCategoriesAction();
	}

	protected BasicValidateSelectedCategoriesAction createValidateCategoriesAction() {
		return new BasicValidateSelectedCategoriesAction();
	}

	/*
	 * @see
	 * org.artop.ecl.emf.validation.ui.actions.providers.AbstractValidationActionProvider#addSubMenu(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	@Override
	protected IMenuManager addSubMenu(IMenuManager contextMenuManager) {
		IMenuManager examplesMenuManager = contextMenuManager.findMenuUsingPath(IArtopExampleMenuConstants.MENU_ARTOP_EXAMPLES_ID);
		if (examplesMenuManager == null) {
			examplesMenuManager = new MenuManager(IArtopExampleMenuConstants.MENU_ARTOP_EXAMPLES_LABEL,
					IArtopExampleMenuConstants.MENU_ARTOP_EXAMPLES_ID);
			contextMenuManager.appendToGroup(ICommonMenuConstants.GROUP_ADDITIONS, examplesMenuManager);

			examplesMenuManager.add(new Separator(IArtopExampleMenuConstants.GROUP_ARTOP_EXAMPLES));
		}

		IMenuManager validationMenuManager = examplesMenuManager.findMenuUsingPath(IValidationExampleMenuConstants.MENU_VALIDATION_ID);
		if (validationMenuManager == null) {
			validationMenuManager = new MenuManager(IValidationExampleMenuConstants.MENU_VALIDATION_LABEL,
					IValidationExampleMenuConstants.MENU_VALIDATION_ID);
			examplesMenuManager.appendToGroup(IArtopExampleMenuConstants.GROUP_ARTOP_EXAMPLES, validationMenuManager);
		}
		return validationMenuManager;
	}

	/*
	 * @see
	 * org.artop.ecl.emf.validation.ui.actions.providers.AbstractValidationActionProvider#populateActions(org.eclipse
	 * .jface.action.IMenuManager, org.eclipse.jface.viewers.IStructuredSelection, boolean)
	 */
	@Override
	protected void populateActions(IMenuManager menu, IStructuredSelection selection, boolean enabled) {
		validateCategoriesAction.selectionChanged(selection);
		validateCategoriesAction.setEnabled(enabled);
		menu.add(new ActionContributionItem(validateCategoriesAction));
	}
}
