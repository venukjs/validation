/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.emf.validation.ui.actions;

import org.artop.ecl.emf.validation.ui.util.Messages;
import org.artop.ecl.emf.validation.ui.wizards.ValidationReportWizard;
import org.artop.ecl.platform.ui.util.ExtendedPlatformUI;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.actions.SelectionListenerAction;

public class BasicGenerateValidationReportAction extends SelectionListenerAction {

	/**
	 * Default constructor
	 */
	public BasicGenerateValidationReportAction() {
		super(Messages._UI_ValidationReport_menu_item);
		setDescription(Messages._UI_ValidationReport_simple_description);
	}

	@Override
	public void run() {

		// TODO Run wizard with selectedObjects rather than IResources
		ValidationReportWizard validationWizard = new ValidationReportWizard(getSelectedResources());

		WizardDialog dilaog = new WizardDialog(ExtendedPlatformUI.getActiveShell(), validationWizard);
		dilaog.setBlockOnOpen(true);
		dilaog.open();

	}
}
