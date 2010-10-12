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
package org.artop.ecl.emf.validation.ui.wizards;

import java.io.File;
import java.util.List;

import org.artop.ecl.emf.validation.ui.util.Messages;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ValidationReportWizardMainPage extends WizardPage {

	final private String CSV_EXTENSION = "csv"; //$NON-NLS-1$

	private Text containerLocationText;

	private Text reportFileNameText;

	private List<IResource> selectedIResources;

	private String filePattern;

	private String outputDir = null;

	/**
	 * Constructor
	 * 
	 * @param the
	 *            {@link ISelection}
	 */
	public ValidationReportWizardMainPage(List<IResource> selectedIResources) {
		super(Messages._UI_Wizard_Report_MainPage_Title);
		setTitle(Messages._UI_Wizard_Report_Title);
		setDescription(Messages._UI_Wizard_Report_Description);
		this.selectedIResources = selectedIResources;
	}

	/**
	 * initialize the wizard value
	 */

	private void initialize() {

		if (selectedIResources != null && !selectedIResources.isEmpty()) {
			IResource r = selectedIResources.get(0);
			if (r instanceof IContainer) {
				outputDir = ((IContainer) r).getLocation().toString();
			} else { // IFile
				outputDir = r.getProject().getLocation().toString();

			}
		} else {
			outputDir = ""; //$NON-NLS-1$
		}

		containerLocationText.setText(outputDir);

		// Reporting file pattern
		if (filePattern == null || filePattern.equals("")) { //$NON-NLS-1$
			filePattern = Messages._UI_Wizard_Report_Default_File_Pattern;
		}
		reportFileNameText.setText(filePattern);
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for the container field.
	 */

	private void handleBrowse() {

		DirectoryDialog dd = new DirectoryDialog(getShell(), SWT.OPEN);

		dd.setText(Messages._UI_Wizard_Report_Browse_Popup_Title);
		dd.setFilterPath(containerLocationText.getText());

		String dirName = dd.open();

		if (dirName != null) {
			containerLocationText.setText(dirName);
			// Update also the outputDir, so when containerText is disposed outputDir will be accessible
			outputDir = dirName;
		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {

		File dir = new File(getContainerLocation());
		String fileName = getFileName();

		if (getContainerLocation().length() == 0) {
			updateStatus(Messages._UI_Wizard_Report_Err_Target_Directory_Empty);
			return;
		}

		if (!dir.isDirectory()) {
			updateStatus(Messages._UI_Wizard_Report_Err_Target_Directory_Must_Exist);
			return;
		}

		if (fileName.length() == 0) {
			updateStatus(Messages._UI_Wizard_Report_Err_File_Empty);
			return;
		}

		for (int i = 0; i < fileName.length(); i++) {
			if (fileName.charAt(i) == '%') {
				if (i < fileName.length() - 2 && !(fileName.charAt(i + 1) == 'r' || fileName.charAt(i + 1) == 'd')) {
					updateStatus(NLS.bind(Messages._UI_Wizard_Report_Err_FileName_Not_Compliant, fileName.subSequence(i, i + 2)));
					return;
				}
			}
		}

		int dotLoc = fileName.lastIndexOf('.');
		if (dotLoc != -1) {
			String ext = fileName.substring(dotLoc + 1);
			if (ext.equalsIgnoreCase(CSV_EXTENSION) == false) {
				updateStatus(NLS.bind(Messages._UI_Wizard_Report_Err_File_Extension_Not_Compliant, CSV_EXTENSION));
				return;
			}
		} else {
			updateStatus(NLS.bind(Messages._UI_Wizard_Report_Err_File_Extension_Not_Compliant, CSV_EXTENSION));
			return;
		}

		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	protected File getReportFileDirectory() {
		if (!containerLocationText.isDisposed()) {
			String location = containerLocationText.getText();
			File dir = new File(location);
			if (dir.isDirectory()) {
				return dir;
			}
		}
		return new File(outputDir);
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText(Messages._UI_Wizard_Report_TargetDirectory);

		containerLocationText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerLocationText.setLayoutData(gd);
		containerLocationText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText(Messages._UI_Wizard_Report_Browse);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		label = new Label(container, SWT.NULL);
		label.setText(Messages._UI_Wizard_Report_ReportFilePattern);

		reportFileNameText = new Text(container, SWT.BORDER | SWT.SINGLE);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		reportFileNameText.setLayoutData(gd);
		reportFileNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Label label2 = new Label(container, SWT.NULL);
		label2.setText(""); //$NON-NLS-1$

		Label label3 = new Label(container, SWT.NULL);

		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		gd2.horizontalSpan = 2;

		label3.setLayoutData(gd2);
		label3.setText(Messages._UI_FilePatternHelp);

		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * Set default pattern
	 * 
	 * @param filePattern
	 */
	public void setFilePattern(String pattern) {
		filePattern = pattern;
	}

	public String getContainerLocation() {
		if (!containerLocationText.isDisposed()) {
			return containerLocationText.getText();
		}
		return outputDir;
	}

	public String getFileName() {
		return reportFileNameText.getText();
	}

	public String getOutputDir() {
		return outputDir;
	}
}