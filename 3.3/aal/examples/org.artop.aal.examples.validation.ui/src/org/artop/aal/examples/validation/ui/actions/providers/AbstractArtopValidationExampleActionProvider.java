/**
 * <copyright>
 * 
 * Copyright (c) See4sys and others.
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

import org.artop.aal.examples.common.ui.IArtopExampleMenuConstants;
import org.artop.aal.examples.validation.ui.IArtopValidationExampleMenuConstants;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.sphinx.emf.validation.ui.actions.providers.AbstractValidationActionProvider;
import org.eclipse.ui.navigator.ICommonMenuConstants;

/**
 * Basic {@link AbstractValidationActionProvider action provider} implementation for Artop validation examples actions.
 * 
 * @since 1.1.2
 */
public abstract class AbstractArtopValidationExampleActionProvider extends AbstractValidationActionProvider {

	/*
	 * @see
	 * org.eclipse.sphinx.emf.ui.actions.providers.BasicActionProvider#addSubMenu(org.eclipse.jface.action.IMenuManager)
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

		IMenuManager validationMenuManager = examplesMenuManager.findMenuUsingPath(IArtopValidationExampleMenuConstants.MENU_VALIDATION_ID);
		if (validationMenuManager == null) {
			validationMenuManager = new MenuManager(IArtopValidationExampleMenuConstants.MENU_VALIDATION_LABEL,
					IArtopValidationExampleMenuConstants.MENU_VALIDATION_ID);
			examplesMenuManager.appendToGroup(IArtopExampleMenuConstants.GROUP_ARTOP_EXAMPLES, validationMenuManager);
		}
		return validationMenuManager;
	}
}
