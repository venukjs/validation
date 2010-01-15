/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
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
package org.artop.aal.autosar40.validation.ui.preferences;

import org.artop.ecl.emf.validation.bridge.util.ConstraintUtil;
import org.artop.ecl.emf.validation.ui.preferences.ConstraintPreferencePage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.ui.preferences.ConstraintsSelectionBlock;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import autosar40.util.Autosar40Factory;

public class Constraint40PreferencePage extends ConstraintPreferencePage {

	@Override
	protected Control createContents(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		result.setLayout(layout);

		final String filter = ConstraintUtil.getModelFilter(Autosar40Factory.eINSTANCE.createARPackage());

		constraintsComposite = new ConstraintsSelectionBlock(new IConstraintFilter() {
			public boolean accept(IConstraintDescriptor constraint, EObject target) {
				return constraint.getId().contains(filter);
			}
		});

		constraintsComposite.createComposite(result);

		applyDialogFont(result);

		return result;
	}
}
