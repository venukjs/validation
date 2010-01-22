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
package org.artop.aal.validation.ui.preferences;

import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.ui.preferences.ConstraintsSelectionBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import org.artop.ecl.emf.validation.bridge.extensions.RulesExtCache;
import org.artop.ecl.emf.validation.bridge.extensions.RulesExtInternal;
import org.artop.ecl.emf.validation.ui.preferences.ConstraintPreferencePage;

public class ConstraintOtherPreferencePage extends ConstraintPreferencePage {

	@Override
	protected Control createContents(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		result.setLayout(layout);

		HashMap<String, RulesExtInternal> registry = RulesExtCache.getSingleton().getRulesExtInternals();

		ArrayList<String> filters = new ArrayList<String>();
		for (RulesExtInternal current : registry.values()) {
			filters.add(current.getFilter());
		}

		final ArrayList<String> filters_ = filters;

		constraintsComposite = new ConstraintsSelectionBlock(new IConstraintFilter() {
			public boolean accept(IConstraintDescriptor constraint, EObject target) {
				for (String current : filters_) {
					if (constraint.getId().contains(current)) {
						return false;
					}
				}

				return true;
			}
		});

		constraintsComposite.createComposite(result);

		applyDialogFont(result);

		return result;
	}
}
