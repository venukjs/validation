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
package org.artop.ecl.emf.validation.report;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.osgi.util.NLS;

public class CSVReportFile extends CSVReport {

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
	public CSVReportFile(Date date, String fileName, IResource iresource, List<Diagnostic> diagnostics) {
		super(date, fileName, iresource, diagnostics);
	}

	@Override
	protected void writeHeader() throws IOException {

		// TODO Build header file title from diagnostics rather than iresource in order to get rid of it
		String msg = NLS.bind(ReportMessages._Header_Main_Title, new Object[] { ReportMessages._Header_File_Title, iresource.getName() });

		csvWriter.write(msg);
		csvWriter.endRecord();

		writeEmptyLine(1);

		writeDate();
		writeProductInfo();

	}

	@Override
	protected void writeCore() throws IOException {
		writeSimpleDiagnosticAnalysis(diagnostics.get(0));
	}

}
