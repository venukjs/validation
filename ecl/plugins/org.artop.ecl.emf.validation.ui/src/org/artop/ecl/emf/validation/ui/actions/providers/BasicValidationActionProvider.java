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

import org.artop.ecl.emf.validation.ui.IValidationMenuConstants;
import org.artop.ecl.emf.validation.ui.actions.BasicCleanProblemMarkersAction;
import org.artop.ecl.emf.validation.ui.actions.BasicGenerateValidationReportAction;
import org.artop.ecl.emf.validation.ui.actions.BasicValidateAction;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.eclipse.ui.views.navigator.ResourceSelectionUtil;

/**
 * A basic implementation of {@linkplain AbstractValidationActionProvider action provider} dedicated to actions that are
 * validation related. It provides three actions:
 * <ul>
 * <li>a {@linkplain BasicValidationAction validate action} that is responsible for launching a batch validation of the
 * selected model;</li>
 * <li>a {@linkplain BasicCleanProblemMarkersAction clean problem markers action} that has the capability for cleaning
 * all the markers that may exist for the elements on the selected model;</li>
 * <li>a {@linkplain BasicGenerateValidationReportAction generate validation report action} that generates a report once
 * a validation of the selected model has been performed.</li>
 * </ul>
 * <p>
 * Actions are populated to contextual menu under item "<b>{@linkplain IValidationMenuConstants#MENU_VALIDATION_LABEL
 * Validation}</b>".
 * <p>
 * <table>
 * <tr valign=top>
 * <td><b>Note</b>&nbsp;&nbsp;</td>
 * <td>This action provider is not contributed by the hosting plug-in in order to avoid duplicate entries of actions. If
 * any client application is really interested in this action provider, it should contribute it by itself.</td>
 * </tr>
 * </table>
 */
public class BasicValidationActionProvider extends AbstractValidationActionProvider {

	/**
	 * Action responsible for the batch validation of a selected model.
	 */
	private BasicValidateAction validateAction;

	/**
	 * Action responsible for cleaning existing problem markers on model's elements.
	 */
	private BasicCleanProblemMarkersAction cleanProblemMarkersAction;

	/**
	 * Action responsible for the generation of a report once batch validation performed.
	 */
	private BasicGenerateValidationReportAction generateValidationReportAction;

	/*
	 * @see org.artop.ecl.emf.ui.actions.providers.BasicActionProvider#doInit()
	 */
	@Override
	public void doInit() {
		validateAction = createValidateAction();
		cleanProblemMarkersAction = createCleanProblemMarkersAction();
		generateValidationReportAction = createGenerateValidationReportAction();
	}

	protected BasicValidateAction createValidateAction() {
		return new BasicValidateAction();
	}

	protected BasicCleanProblemMarkersAction createCleanProblemMarkersAction() {
		return new BasicCleanProblemMarkersAction();
	}

	protected BasicGenerateValidationReportAction createGenerateValidationReportAction() {
		return new BasicGenerateValidationReportAction();
	}

	/*
	 * @see
	 * org.artop.ecl.emf.validation.ui.actions.providers.AbstractValidationActionProvider#addSubMenu(org.eclipse.jface
	 * .action.IMenuManager)
	 */
	@Override
	protected IMenuManager addSubMenu(IMenuManager contextMenuManager) {
		IMenuManager subMenuManager = contextMenuManager.findMenuUsingPath(IValidationMenuConstants.MENU_VALIDATION_ID);
		if (subMenuManager == null) {
			subMenuManager = new MenuManager(IValidationMenuConstants.MENU_VALIDATION_LABEL, IValidationMenuConstants.MENU_VALIDATION_ID);
			contextMenuManager.appendToGroup(ICommonMenuConstants.GROUP_ADDITIONS, subMenuManager);
		}
		return subMenuManager;
	}

	/*
	 * @see
	 * org.artop.ecl.emf.validation.ui.actions.providers.AbstractValidationActionProvider#populateActions(org.eclipse
	 * .jface.action.IMenuManager, org.eclipse.jface.viewers.IStructuredSelection, boolean)
	 */
	@Override
	protected void populateActions(IMenuManager menu, IStructuredSelection selection, boolean enabled) {
		// Optimization:
		// 1] Enablement is computed only once by AbstractValidationActionProvider;
		// 2] Selection is given to each action so that getStructuredSelection() can be used (inside action).

		if (validateAction != null) {
			validateAction.selectionChanged(selection);
			validateAction.setEnabled(enabled);
			menu.add(new ActionContributionItem(validateAction));
		}

		if (cleanProblemMarkersAction != null) {
			cleanProblemMarkersAction.selectionChanged(selection);
			cleanProblemMarkersAction.setEnabled(enabled);
			menu.add(new ActionContributionItem(cleanProblemMarkersAction));
		}

		if (generateValidationReportAction != null) {
			generateValidationReportAction.selectionChanged(selection);
			generateValidationReportAction.setEnabled(enabled && selection.size() == 1
					&& ResourceSelectionUtil.allResourcesAreOfType(selection, IResource.PROJECT | IResource.FILE));
			menu.add(new ActionContributionItem(generateValidationReportAction));
		}
	}
}
