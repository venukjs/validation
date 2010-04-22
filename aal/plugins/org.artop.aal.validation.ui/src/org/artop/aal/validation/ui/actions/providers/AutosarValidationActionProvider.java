/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT, Geensys and others.
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
package org.artop.aal.validation.ui.actions.providers;

import org.artop.aal.validation.ui.actions.FixUuidConflictsAction;
import org.artop.ecl.emf.validation.ui.actions.providers.BasicValidationActionProvider;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;

/**
 * Implementation of {@linkplain BasicValidationActionProvider validation action provider} dedicated to actions related
 * to validation of AUTOSAR models. It provides actions already provided by the basic implementation of
 * {@linkplain BasicValidationActionProvider validation action provider} and provides one more action:
 * <ul>
 * <li>a {@linkplain FixUuidConflictsAction fix UUID conflicts action} that is responsible for computing conflicts of
 * UUID between all Identifiable objects and then fixing theses conflicts by generating new UUIDs and setting them on
 * concerned Identifiable objects.</li>
 * </ul>
 */
public class AutosarValidationActionProvider extends BasicValidationActionProvider {

	/**
	 * Action responsible for fixing UUID conflicts on Identifiable objects.
	 */
	protected FixUuidConflictsAction fixUuidConflictsAction;

	/*
	 * @see org.artop.ecl.emf.validation.ui.actions.providers.BasicValidationActionProvider#doInit()
	 */
	@Override
	public void doInit() {
		super.doInit();
		fixUuidConflictsAction = createFixUuidConflictsAction();
	}

	/**
	 * @return The fix UUID action to contribute to the "Validate" menu item.
	 */
	protected FixUuidConflictsAction createFixUuidConflictsAction() {
		return new FixUuidConflictsAction();
	}

	/*
	 * @see
	 * org.artop.ecl.emf.validation.ui.actions.providers.BasicValidationActionProvider#populateActions(org.eclipse.jface
	 * .action.IMenuManager, org.eclipse.jface.viewers.IStructuredSelection, boolean)
	 */
	@Override
	protected void populateActions(IMenuManager menu, IStructuredSelection selection, boolean enabled) {
		super.populateActions(menu, selection, enabled);

		if (fixUuidConflictsAction != null) {
			fixUuidConflictsAction.selectionChanged(selection);
			fixUuidConflictsAction.setEnabled(enabled);
			menu.add(new ActionContributionItem(fixUuidConflictsAction));
		}
	}
}
