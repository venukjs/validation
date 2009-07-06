/**
 * <copyright>
 * 
 * Copyright (c) {contributing company name} and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Artop Software License 
 * Based on Released AUTOSAR Material (ASLR) which accompanies this 
 * distribution, and is available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     {contributing company name} - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.examples.validation.ui.actions;

import org.artop.ecl.examples.validation.ui.internal.messages.Messages;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * @since 1.1.2
 */
public class BasicValidateSelectedCategoriesAction extends BaseSelectionListenerAction {

	/**
	 * Constructor.
	 */
	public BasicValidateSelectedCategoriesAction() {
		super(Messages.action_validateSelectedCategories_label);
	}

	@Override
	public void run() {
		// TODO Add action implementation
	}
}
