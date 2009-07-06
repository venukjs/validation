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
package org.artop.aal.examples.validation.ui.actions;

import org.artop.aal.examples.validation.ui.internal.messages.Messages;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * @since 1.1.2
 */
public class ValidateAutosarConstraintsAction extends BaseSelectionListenerAction {

	/**
	 * Constructor.
	 */
	public ValidateAutosarConstraintsAction() {
		super(Messages.action_validateAutosarConstraints_label);
	}

	@Override
	public void run() {
		super.run();
	}
}
