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
package org.artop.ecl.emf.validation.report;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

public class CSVReportProject extends CSVReport {

	/**
	 * constructor
	 * 
	 * @param date
	 *            the date
	 * @param fileName
	 *            the target fileName
	 * @param iresource
	 *            the root resource
	 */
	public CSVReportProject(Date date, String fileName, IResource iresource, List<Diagnostic> diagnostics) {
		super(date, fileName, iresource, diagnostics);
	}

	@Override
	protected void writeHeader() throws IOException {

		// TODO Build header project title from diagnostics rather than iresource in order to get rid of it
		String msg = NLS.bind(ReportMessages._Header_Main_Title, new Object[] { ReportMessages._Header_Project_Title, iresource.getName() });

		csvWriter.write(msg);
		csvWriter.endRecord();

		writeEmptyLine(1);

		writeDate();
		writeProductInfo();

		writeEmptyLine(2);

		listFile();
	}

	protected void listFile() throws IOException {

		csvWriter.write(ReportMessages._Header_FileList);
		csvWriter.endRecord();
		writeEmptyLine(1);
		for (Diagnostic current : diagnostics) {
			EObject eObject = (EObject) current.getData().get(0);
			csvWriter.writeRecord(new String[] { CSVWriter.EMPTY_STRING, eObject.eResource().getURI().toString() });
		}

	}

	@Override
	protected void writeCore() throws IOException {

		for (Diagnostic diagnostic : diagnostics) {

			EObject eObject = (EObject) diagnostic.getData().get(0);
			csvWriter.writeRecord(new String[] { NLS.bind(ReportMessages._File_Line, eObject.eResource().getURI().toString()) });
			csvWriter.endRecord();

			writeSimpleDiagnosticAnalysis(diagnostic);
			csvWriter.endRecord();
		}

	}

}
