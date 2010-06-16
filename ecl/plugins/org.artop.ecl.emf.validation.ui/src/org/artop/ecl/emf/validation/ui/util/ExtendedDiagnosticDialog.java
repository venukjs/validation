/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.emf.validation.ui.util;

import java.util.List;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * Extension of the DiagnosticDialog class in order to treat multi diagnostic
 */
public class ExtendedDiagnosticDialog extends IconAndMessageDialog {
	/**
	 * Opens a diagnostic dialog to display the given diagnostic. Use this method if the diagnostic object being
	 * displayed does not contain child items, or if you wish to display all such items without filtering.
	 * 
	 * @param parent
	 *            the parent shell of the dialog, or <code>null</code> if none
	 * @param dialogTitle
	 *            the title to use for this dialog, or <code>null</code> to indicate that the default title should be
	 *            used
	 * @param message
	 *            the message to show in this dialog, or <code>null</code> to indicate that the diagnostic's message
	 *            should be shown as the primary message
	 * @param diagnostic
	 *            the diagnostic to show to the user
	 * @return the code of the button that was pressed that resulted in this dialog closing. This will be
	 *         <code>Dialog.OK</code> if the OK button was pressed, or <code>Dialog.CANCEL</code> if this dialog's close
	 *         window decoration or the ESC key was used.
	 */
	public static int open(Shell parent, String dialogTitle, String message, List<Diagnostic> diagnostics) {
		return open(parent, dialogTitle, message, diagnostics, Diagnostic.OK | Diagnostic.INFO | Diagnostic.WARNING | Diagnostic.ERROR);
	}

	/**
	 * Opens a diagnostic dialog to display the given diagnostic. Use this method if the diagnostic object being
	 * displayed is reporting either an error or a warning. Only children reporting one of this severities are
	 * presented.
	 * 
	 * @param parent
	 *            the parent shell of the dialog, or <code>null</code> if none
	 * @param dialogTitle
	 *            the title to use for this dialog, or <code>null</code> to indicate that the default title should be
	 *            used
	 * @param message
	 *            the message to show in this dialog, or <code>null</code> to indicate that the diagnostic's message
	 *            should be shown as the primary message
	 * @param diagnostic
	 *            the diagnostic to show to the user
	 * @return the code of the button that was pressed that resulted in this dialog closing. This will be
	 *         <code>Dialog.OK</code> if the OK button was pressed, or <code>Dialog.CANCEL</code> if this dialog's close
	 *         window decoration or the ESC key was used.
	 */
	public static int openProblem(Shell parent, String dialogTitle, String message, List<Diagnostic> diagnostics) {
		return open(parent, dialogTitle, message, diagnostics, ExtendedDiagnosticComposite.ERROR_WARNING_MASK);
	}

	/**
	 * Opens an diagnostic dialog to display the given diagnostic. Use this method if the diagnostic object being
	 * displayed contains child items <it>and </it> you wish to specify a mask which will be used to filter the
	 * displaying of these children. The diagnostic dialog will only be displayed if there is at least one child
	 * diagnostic matching the mask.
	 * 
	 * @param parentShell
	 *            the parent shell of the dialog, or <code>null</code> if none
	 * @param title
	 *            the title to use for this dialog, or <code>null</code> to indicate that the default title should be
	 *            used
	 * @param message
	 *            the message to show in this dialog, or <code>null</code> to indicate that the diagnostic's message
	 *            should be shown as the primary message
	 * @param diagnostic
	 *            the diagnostic to show to the user
	 * @param displayMask
	 *            the mask to use to filter the displaying of child items, as per
	 *            <code>ExtendedDiagnosticComposite.severityMatches(List<Diagnostic>, int)</code>
	 * @return the code of the button that was pressed that resulted in this dialog closing. This will be
	 *         <code>Dialog.OK</code> if the OK button was pressed, or <code>Dialog.CANCEL</code> if this dialog's close
	 *         window decoration or the ESC key was used.
	 * @see ExtendedDiagnosticComposite#severityMatches(List<Diagnostic>, int)
	 */
	public static int open(Shell parentShell, String title, String message, List<Diagnostic> diagnostics, int displayMask) {
		ExtendedDiagnosticDialog dialog = new ExtendedDiagnosticDialog(parentShell, title, message, diagnostics, displayMask);
		return dialog.open();
	}

	/**
	 * Returns whether the given diagnostic object should be displayed.
	 * 
	 * @param diagnostic
	 *            a diagnostic object
	 * @param mask
	 *            a mask as per <code>ExtendedDiagnosticComposite.severityMatches(List<Diagnostic>, int)</code>
	 * @return <code>true</code> if the given diagnostic should be displayed, and <code>false</code> otherwise
	 * @see ExtendedDiagnosticComposite#severityMatches(List<Diagnostic>, int)
	 */
	protected static boolean shouldDisplay(List<Diagnostic> diagnostics, int mask) {
		// TODO
		// Collection<Diagnostic> children = diagnostic.getChildren();
		// if (children.isEmpty()) {
		// return ExtendedDiagnosticComposite.severityMatches(diagnostic, mask);
		// }
		//
		// for (Diagnostic child : children) {
		// if (ExtendedDiagnosticComposite.severityMatches(child, mask)) {
		// return true;
		// }
		// }
		return false;
	}

	/**
	 * The Details button.
	 */
	private Button detailsButton;

	/**
	 * The title of the dialog.
	 */
	private String title;

	/**
	 * The diagnostic composite that displays the diagnostic details.
	 */
	private ExtendedDiagnosticComposite diagnosticComposite;
	private ExtendedDiagnosticComposite.TextProvider textProvider;

	/**
	 * Filter mask for determining which diagnostic items to display.
	 */
	private int severityMask = 0xFFFF;

	/**
	 * The diagnostics object to display.
	 */
	private List<Diagnostic> diagnostics;

	private boolean shouldIncludeTopLevelDiagnostic = false;

	/**
	 * Creates an diagnostic dialog. Note that the dialog will have no visual representation (no widgets) until it is
	 * told to open.
	 * <p>
	 * Normally one should use <code>open</code> to create and open one of these. This constructor is useful only if the
	 * diagnostic object being displayed contains child items <it>and </it> you need to specify a mask which will be
	 * used to filter the displaying of these children. The diagnostic dialog will only be displayed if there is at
	 * least one child diagnostic matching the mask.
	 * </p>
	 * 
	 * @param parentShell
	 *            the shell under which to create this dialog
	 * @param dialogTitle
	 *            the title to use for this dialog, or <code>null</code> to indicate that the default title should be
	 *            used
	 * @param message
	 *            the message to show in this dialog, or <code>null</code> to indicate that the diagnostic's message
	 *            should be shown as the primary message
	 * @param diagnostic
	 *            the diagnostic to show to the user
	 * @param severityMask
	 *            the mask to use to filter the displaying of child items, as per
	 *            <code> ExtendedDiagnosticComposite.severityMatches(List<Diagnostic>, int)</code>
	 * @see ExtendedDiagnosticComposite#severityMatches(List<Diagnostic>, int)
	 */
	public ExtendedDiagnosticDialog(Shell parentShell, String dialogTitle, String message, List<Diagnostic> diagnostics, int severityMask) {
		super(parentShell);
		title = dialogTitle == null ? JFaceResources.getString("Problem_Occurred") : dialogTitle; //$NON-NLS-1$
		this.message = message == null ? "At least one constraint have not been respected" : message;
		this.diagnostics = diagnostics;
		this.severityMask = severityMask;
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	public void setTextProvider(ExtendedDiagnosticComposite.TextProvider textProvider) {
		this.textProvider = textProvider;
		if (diagnosticComposite != null) {
			diagnosticComposite.setTextProvider(getTextProvider());
		}
	}

	public ExtendedDiagnosticComposite.TextProvider getTextProvider() {
		return textProvider;
	}

	/**
	 * Handles the pressing of the OK or Details button in this dialog. If the OK button was pressed then close this
	 * dialog. If the Details button was pressed then toggle the displaying of the diagnostic details area. Note that
	 * the Details button will only be visible if the diagnostic being displayed specifies child details.
	 */
	@Override
	protected void buttonPressed(int id) {
		if (id == IDialogConstants.DETAILS_ID) {
			// was the details button pressed?
			toggleDetailsArea();
		} else {
			super.buttonPressed(id);
		}
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(title);
	}

	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// create OK and Details buttons
		createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		createDetailsButton(parent);
	}

	/**
	 * Create the details button if it should be included.
	 * 
	 * @param parent
	 *            the parent composite
	 */
	protected void createDetailsButton(Composite parent) {
		if (shouldShowDetailsButton()) {
			detailsButton = createButton(parent, IDialogConstants.DETAILS_ID, IDialogConstants.SHOW_DETAILS_LABEL, false);
		}
	}

	/**
	 * This implementation of the <code>Dialog</code> framework method creates and lays out a composite. Subclasses that
	 * require a different dialog area may either override this method, or call the <code>super</code> implementation
	 * and add controls to the created composite.
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		createMessageArea(parent);
		// create a composite with standard margins and spacing
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.marginHeight = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_MARGIN);
		layout.marginWidth = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_MARGIN);
		layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
		layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
		layout.numColumns = 2;
		composite.setLayout(layout);
		GridData childData = new GridData(GridData.FILL_HORIZONTAL);
		childData.horizontalSpan = 2;
		composite.setLayoutData(childData);
		composite.setFont(parent.getFont());
		return composite;
	}

	@Override
	protected void createDialogAndButtonArea(Composite parent) {
		super.createDialogAndButtonArea(parent);
		if (dialogArea instanceof Composite) {
			// Create a label if there are no children to force a smaller layout
			Composite dialogComposite = (Composite) dialogArea;
			if (dialogComposite.getChildren().length == 0) {
				new Label(dialogComposite, SWT.NULL);
			}
		}
	}

	@Override
	protected Image getImage() {

		// TODO
		// if (diagnostic != null) {
		// if (diagnostic.getSeverity() == Diagnostic.WARNING) {
		// return getWarningImage();
		// }
		// if (diagnostic.getSeverity() == Diagnostic.INFO) {
		// return getInfoImage();
		// }
		// }
		// If it was not a warning or an diagnostic then return the diagnostic image
		return getErrorImage();
	}

	/**
	 * Create the diagnostic composite.
	 * 
	 * @param parent
	 *            the parent composite
	 * @return the diagnostic composite
	 */
	protected ExtendedDiagnosticComposite createExtendedDiagnosticComposite(Composite parent) {
		ExtendedDiagnosticComposite diagnosticComposite = new ExtendedDiagnosticComposite(parent, SWT.NONE);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL | GridData.VERTICAL_ALIGN_FILL
				| GridData.GRAB_VERTICAL);
		data.horizontalSpan = 2;
		data.heightHint = 200;
		diagnosticComposite.setLayoutData(data);
		if (getTextProvider() != null) {
			diagnosticComposite.setTextProvider(getTextProvider());
		}
		diagnosticComposite.initialize(null);

		populate(diagnosticComposite, diagnostics, shouldIncludeTopLevelDiagnostic);
		return diagnosticComposite;
	}

	/**
	 * Extends <code>Window.open()</code>. Opens an diagnostic dialog to display the diagnostic. If you specified a mask
	 * to filter the displaying of these children, the diagnostic dialog will only be displayed if there is at least one
	 * child diagnostic matching the mask.
	 */
	@Override
	public int open() {
		// TODO
		if (1 == 1) {
			return super.open();
		}
		if (shouldDisplay(diagnostics, severityMask)) {
			return super.open();
		}
		setReturnCode(OK);
		return OK;
	}

	/**
	 * Populates the diagnostic composite with the given diagnostic.
	 * 
	 * @param diagnosticComposite
	 *            the diagnostic composite to populate
	 * @param diagnostic
	 *            the diagnostic being displayed
	 * @param includeDiagnostic
	 *            whether to include the specified diagnostic in the display or just its children
	 */
	private void populate(ExtendedDiagnosticComposite diagnosticComposite, List<Diagnostic> diagnostics, boolean includeDiagnostic) {
		// TODO
		// if (ExtendedDiagnosticComposite.severityMatches(diagnostics, severityMask)) {
		diagnosticComposite.setShowRootDiagnostic(includeDiagnostic);
		diagnosticComposite.setSeverityMask(severityMask);
		diagnosticComposite.setDiagnostics(diagnostics);
		// }
	}

	/**
	 * Toggles the unfolding of the details area. This is triggered by the user pressing the details button.
	 */
	private void toggleDetailsArea() {
		Point windowSize = getShell().getSize();
		if (diagnosticComposite != null) {
			// Closing the detail area
			diagnosticComposite.dispose();
			diagnosticComposite = null;
			detailsButton.setText(IDialogConstants.SHOW_DETAILS_LABEL);
		} else {
			// Opening the detail area
			diagnosticComposite = createExtendedDiagnosticComposite((Composite) getContents());
			detailsButton.setText(IDialogConstants.HIDE_DETAILS_LABEL);
		}
		Point newSize = getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT);
		getShell().setSize(new Point(windowSize.x, newSize.y));
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#close()
	 */
	@Override
	public boolean close() {
		diagnostics = null;
		diagnosticComposite = null;
		return super.close();
	}

	/**
	 * Return whether the Details button should be included. This method is invoked once when the dialog is built. By
	 * default, the Details button is only included if the diagnostic used when creating the dialog was a
	 * multi-diagnostic or if the diagnostic contains an exception. Subclasses may override.
	 * 
	 * @return whether the Details button should be included
	 */
	protected boolean shouldShowDetailsButton() {
		return diagnostics.size() > 0;
	}

	// /**
	// * Set the diagnostic displayed by this diagnostic dialog to the given diagnostic. This only affects the
	// diagnostic
	// * displayed by the diagnostic composite. The message, image and title should be updated by the subclass, if
	// * desired.
	// *
	// * @param diagnostic
	// * the diagnostic to be displayed in the diagnostic composite
	// */
	// protected final void setDiagnostic(Diagnostic diagnostic) {
	// if (this.diagnostic != diagnostic) {
	// this.diagnostic = diagnostic;
	// }
	// shouldIncludeTopLevelDiagnostic = true;
	// if (diagnosticComposite != null && !diagnosticComposite.isDisposed()) {
	// populate(diagnosticComposite, diagnostic, shouldIncludeTopLevelDiagnostic);
	// }
	// }
}
