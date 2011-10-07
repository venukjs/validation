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
package org.artop.aal.autosar3x.validation.ui.preferences;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.ui.preferences.ConstraintsSelectionBlock;
import org.eclipse.sphinx.emf.validation.bridge.util.ConstraintUtil;
import org.eclipse.sphinx.emf.validation.ui.preferences.ConstraintPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import autosar3x.util.Autosar3xFactory;

public class Constraint3xPreferencePage extends ConstraintPreferencePage {
	@Override
	protected Control createContents(Composite parent) {
		Composite result = new Composite(parent, SWT.NONE);
		FillLayout layout = new FillLayout();
		result.setLayout(layout);

		final String filter = ConstraintUtil.getModelFilter(Autosar3xFactory.eINSTANCE.createARPackage());

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
