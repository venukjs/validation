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
package org.artop.aal.examples.validation.ui.actions;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.artop.aal.examples.validation.IAutosarValidationExamplesConstants;
import org.artop.aal.examples.validation.ui.internal.Activator;
import org.artop.aal.examples.validation.ui.internal.messages.Messages;
import org.artop.ecl.emf.validation.diagnostic.filters.ConstraintCategoryFilter;
import org.artop.ecl.emf.validation.util.ValidationUtil;
import org.artop.ecl.platform.ui.util.ExtendedPlatformUI;
import org.artop.ecl.platform.util.PlatformLogUtil;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;

/**
 * @since 1.1.2
 */
public class ValidateAutosarConstraintsAction extends BaseSelectionListenerAction {

	/**
	 * The constraint category filters used for validation.
	 */
	private List<IConstraintFilter> filters = new ArrayList<IConstraintFilter>();

	/**
	 * Constructor.
	 */
	public ValidateAutosarConstraintsAction() {
		super(Messages.action_validateAutosarConstraints_label);
		filters.add(new ConstraintCategoryFilter(IAutosarValidationExamplesConstants.AUTOSAR_CONSTRAINTS_CATEGORY_ID));
	}

	@Override
	public void run() {
		try {
			IRunnableWithProgress operation = new WorkspaceModifyDelegatingOperation(new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					List<Object> objects = new ArrayList<Object>();
					for (Iterator<?> it = getStructuredSelection().iterator(); it.hasNext();) {
						objects.add(it.next());
					}
					ValidationUtil.validate(objects, filters, monitor);
				}
			});
			// Run the validation operation, and show progress
			new ProgressMonitorDialog(ExtendedPlatformUI.getActiveShell()).run(true, true, operation);
		} catch (Exception ex) {
			PlatformLogUtil.logAsError(Activator.getDefault(), ex);
		}
	}
}
