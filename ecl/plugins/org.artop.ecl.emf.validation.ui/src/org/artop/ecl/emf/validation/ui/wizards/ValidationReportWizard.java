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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.artop.ecl.emf.util.EcorePlatformUtil;
import org.artop.ecl.emf.validation.diagnostic.ExtendedDiagnostician;
import org.artop.ecl.emf.validation.report.CSVReport;
import org.artop.ecl.emf.validation.report.CSVReportFile;
import org.artop.ecl.emf.validation.report.CSVReportProject;
import org.artop.ecl.emf.validation.ui.Activator;
import org.artop.ecl.emf.validation.ui.util.Messages;
import org.artop.ecl.platform.util.ExtendedPlatform;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.EMFEditUIPlugin;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.Wizard;

public class ValidationReportWizard extends Wizard implements IWizard {

	private ValidationReportWizardMainPage mainPage;

	private List<IResource> selectedIResource;

	// private String reportFileName;
	//
	// private IContainer reportFileContainer;

	/**
	 * Constructor for SampleNewWizard.
	 */
	public ValidationReportWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle(Messages._UI_Wizard_Report_ShellTitle);

	}

	public ValidationReportWizard(List<IResource> selectedIResource) {
		this();
		setSelectedIResource(selectedIResource);

	}

	/**
	 * Adding the page to the wizard.
	 */

	@Override
	public void addPages() {
		mainPage = new ValidationReportWizardMainPage(selectedIResource);
		addPage(mainPage);
	}

	// public String getReportFileName() {
	// return reportFileName;
	// }
	//
	// public void setReportFileName(String reportFileName) {
	// this.reportFileName = reportFileName;
	// }
	//
	// public IContainer getReportFileDirectory() {
	// return reportFileContainer;
	// }
	//
	// public void setReportFileContainer(IContainer reportFileContainer) {
	// this.reportFileContainer = reportFileContainer;
	// }

	public List<IResource> getSelectedIResource() {
		return selectedIResource;
	}

	public void setSelectedIResource(List<IResource> selectedIResource) {
		this.selectedIResource = selectedIResource;
	}

	/**
	 * This method is called when 'Finish' button is pressed in the wizard. We will create an operation and run it using
	 * wizard as execution context.
	 */
	@Override
	public boolean performFinish() {

		final String fileName = mainPage.getFileName();
		final File fileReportContainer = mainPage.getReportFileDirectory();
		final List<IResource> selectedIResource = getSelectedIResource();

		Job createReportJob = new WorkspaceJob("Creating report file...") { //$NON-NLS-1$

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) throws CoreException {
				doRun(selectedIResource, fileReportContainer, fileName, monitor);
				return Status.OK_STATUS;
			}

		};

		createReportJob.setPriority(Job.LONG);
		Activator.getDefault().getWorkbench().getProgressService().showInDialog(null, createReportJob);
		createReportJob.schedule();
		return true;
	}

	/**
	 * The worker method. It will find the container, create the file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */
	private void doRun(List<IResource> selectedIResource, File reportFileDirectory, String fileNamePattern, IProgressMonitor monitor)
			throws CoreException {

		for (IResource currentResource : selectedIResource) {

			// First of all, let's validate
			List<Diagnostic> diagnostics = validate(currentResource, monitor);
			if (diagnostics != null) {
				// On a second hand, let's generate the report itself
				Date date = new Date();
				String theFileName = getFileName(fileNamePattern, currentResource, date);
				String reportFile = reportFileDirectory + File.separator + theFileName;

				CSVReport report = null;
				if (currentResource.getType() == IResource.FILE) {
					report = new CSVReportFile(date, reportFile, currentResource, diagnostics);
				} else if (currentResource.getType() == IResource.PROJECT) {
					report = new CSVReportProject(date, reportFile, currentResource, diagnostics);
				}
				report.create();
			}
		}
	}

	/**
	 * perform a validation on selected Resource
	 * 
	 * @param iResource
	 *            the {@link IResource} to validate
	 * @param progressMonitor
	 * @return the list of connected Diagnostic
	 */
	private List<Diagnostic> validate(IResource iResource, IProgressMonitor progressMonitor) {
		ArrayList<Diagnostic> result = new ArrayList<Diagnostic>();
		List<IFile> files = new ArrayList<IFile>();
		if (iResource instanceof IProject) {
			IProject project = (IProject) iResource;
			files.addAll(ExtendedPlatform.getAllFiles(project, true));
		} else if (iResource instanceof IFile) {
			files.add((IFile) iResource);
		}

		ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		progressMonitor.beginTask(Messages._UI_progressBar_InitialMsg, files.size() * 100);

		for (IFile file : files) {
			EObject modelRoot = EcorePlatformUtil.getModelRoot(file);
			if (modelRoot != null) {
				progressMonitor.setTaskName(EMFEditUIPlugin.INSTANCE.getString("_UI_Validating_message", new Object[] { "\"" + file.getName() //$NON-NLS-1$//$NON-NLS-2$
						+ "\"" })); //$NON-NLS-1$

				int count = 1;
				for (Iterator<?> i = modelRoot.eAllContents(); i.hasNext(); i.next()) {
					++count;
				}
				IProgressMonitor sub = new SubProgressMonitor(progressMonitor, 100);
				sub.beginTask("", count); //$NON-NLS-1$
				diagnostician.setProgressMonitor(sub);
				result.add(diagnostician.validate(modelRoot));
				sub.done();
			} else {
				progressMonitor.worked(100);
			}
			if (progressMonitor.isCanceled()) {
				return null;
			}
		}

		diagnostician.setProgressMonitor(null);
		progressMonitor.done();
		return result;
	}

	/**
	 * Translate a fileNamePattern to fileName
	 * 
	 * @param fileNamePattern
	 * @param current
	 *            an {@link IResource}
	 * @param d
	 *            a java {@link Date} object
	 * @return fileName
	 */

	private String getFileName(String fileNamePattern, IResource current, Date d) {
		Assert.isNotNull(fileNamePattern);
		Assert.isNotNull(current);
		Assert.isNotNull(d);

		if (!fileNamePattern.contains("%")) { //$NON-NLS-1$
			return fileNamePattern;
		}

		String result = ""; //$NON-NLS-1$
		int i = 0;
		int ln = fileNamePattern.length();
		while (i < ln) {
			if (fileNamePattern.charAt(i) != '%') {
				result += fileNamePattern.charAt(i++);
			} else if (fileNamePattern.charAt(i + 1) == 'r') {
				String f = current.getName();
				if (f.contains(".")) { //$NON-NLS-1$
					result += f.substring(0, f.indexOf('.'));
				} else {
					result += f;
				}
				i += 2;
			} else if (fileNamePattern.charAt(i + 1) == 'd') {
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmm"); //$NON-NLS-1$
				df.format(d);
				result += df.format(d);
				i += 2;
			}
		}

		return result;
	}

}