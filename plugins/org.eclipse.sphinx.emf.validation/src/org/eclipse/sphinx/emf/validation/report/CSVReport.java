/**
 * <copyright>
 * 
 * Copyright (c) See4sys and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *     See4sys - Initial API and implementation
 * 
 * </copyright>
 */
package org.eclipse.sphinx.emf.validation.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.IProduct;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sphinx.emf.validation.bridge.util.ConstraintUtil;
import org.eclipse.sphinx.emf.validation.diagnostic.ExtendedDiagnostic;
import org.eclipse.sphinx.emf.validation.util.Messages;
import org.eclipse.sphinx.emf.validation.util.ValidationUtil;

public abstract class CSVReport {

	protected CSVWriter csvWriter = null;

	protected char delimiter = ';';

	protected IResource iresource;

	protected List<Diagnostic> diagnostics = null;

	protected Date date;

	public CSVReport(Date date, String fileName, IResource iresource, List<Diagnostic> diagnostics) {
		if (fileName == null) {
			throw new IllegalArgumentException(Messages.CSV_Report_Null_FileName);
		}

		if (iresource == null) {
			throw new IllegalArgumentException(Messages.CSV_Report_Null_Resource);
		}

		if (diagnostics == null || diagnostics.isEmpty()) {
			throw new IllegalArgumentException(Messages.CSV_Report_Null_diagnostic);
		}

		csvWriter = new CSVWriter(fileName);
		csvWriter.setDelimiter(delimiter);
		this.date = date;
		this.iresource = iresource;
		this.diagnostics = diagnostics;
	}

	public void create() {
		try {

			writeHeader();

			writeEmptyLine(2);

			writeResume();

			writeEmptyLine(2);

			writeActiveRulesPart();

			writeEmptyLine(2);

			writeCore();

			csvWriter.close();

		} catch (IOException ex) {

		}

	}

	/**
	 * write header of report
	 * 
	 * @throws IOException
	 */
	protected abstract void writeHeader() throws IOException;

	/**
	 * write core of report
	 * 
	 * @throws IOException
	 */
	protected abstract void writeCore() throws IOException;

	/**
	 * Add date to report
	 * 
	 * @throws IOException
	 */
	protected void writeDate() throws IOException {

		String msg = NLS.bind(ReportMessages._Header_Date, date);

		csvWriter.write(msg);
		csvWriter.endRecord();
	}

	/**
	 * Add Product Info to report
	 * 
	 * @throws IOException
	 */
	protected void writeProductInfo() throws IOException {

		IProduct product = Platform.getProduct();

		String msg = NLS.bind(ReportMessages._Header_Product, product.getName());

		csvWriter.write(msg);
		csvWriter.endRecord();
	}

	/**
	 * Write empty line(s) to the report
	 * 
	 * @param nb
	 *            number of empty lines
	 * @throws IOException
	 */
	protected void writeEmptyLine(int nb) throws IOException {
		for (int i = 0; i < nb; i++) {
			csvWriter.endRecord();
		}
	}

	/**
	 * generate the error analysis part for a given {@link Diagnostic} to the report
	 * 
	 * @param diagnostic
	 *            a {@link Diagnostic}
	 * @throws IOException
	 */
	protected void writeSimpleDiagnosticAnalysis(Diagnostic diagnostic) throws IOException {
		Assert.isNotNull(diagnostic);

		if (diagnostic.getSeverity() == Diagnostic.OK) {
			csvWriter.write(ReportMessages._Diagnostic_No_Error);
			csvWriter.endRecord();
			return;
		}

		int[] errQuickReport = ReportUtil.getErrWarInf(diagnostic);
		csvWriter.writeRecord(new String[] { null, NLS.bind(ReportMessages._Diagnostic_Number_Of_Error, String.valueOf(errQuickReport[0])) });
		csvWriter.writeRecord(new String[] { null, NLS.bind(ReportMessages._Diagnostic_Number_Of_Warning, String.valueOf(errQuickReport[1])) });
		csvWriter.writeRecord(new String[] { null, NLS.bind(ReportMessages._Diagnostic_Number_Of_Info, String.valueOf(errQuickReport[2])) });

		writeEmptyLine(1);

		final int idx_EOName = 1;
		final int idx_EOURI = 2;
		final int idx_DiagSev = 3;
		final int idx_DiagMsg = 5;
		final int idx_DiagRuleId = 4;

		final int nb_col = 6;

		String header[] = new String[nb_col];
		header[idx_EOName] = ReportMessages._Diagnostic_Column_Header_EOName;
		header[idx_EOURI] = ReportMessages._Diagnostic_Column_Header_EOURI;
		header[idx_DiagSev] = ReportMessages._Diagnostic_Column_Header_DiagSev;
		header[idx_DiagMsg] = ReportMessages._Diagnostic_Column_Header_DiagMsg;
		header[idx_DiagRuleId] = ReportMessages._Diagnostic_Column_Header_DiagRuleId;

		csvWriter.writeRecord(header);

		for (Diagnostic childDiagnostic : diagnostic.getChildren()) {
			if (!childDiagnostic.getData().isEmpty()) {

				String ruleId = childDiagnostic instanceof ExtendedDiagnostic ? ((ExtendedDiagnostic) childDiagnostic).getConstraintId()
						: ReportMessages._Diagnostic_EMFRuleId;

				EObject tgtObject = (EObject) childDiagnostic.getData().get(0);
				String uri = EcoreUtil.getURI(tgtObject).toString();

				String res[] = new String[nb_col];
				res[idx_EOName] = ValidationUtil.getObjectId(uri);
				res[idx_EOURI] = ValidationUtil.getSegment(uri);
				res[idx_DiagSev] = getErrLevelAsString(childDiagnostic.getSeverity());
				res[idx_DiagRuleId] = ReportUtil.getRuleLabel(ruleId);
				res[idx_DiagMsg] = childDiagnostic.getMessage();

				csvWriter.writeRecord(res);

			}
		}
	}

	/**
	 * write to the report the list of Activated Rules
	 * 
	 * @throws IOException
	 */
	protected void writeActiveRulesPart() throws IOException {

		EObject eo = (EObject) diagnostics.get(0).getData().get(0);

		IConstraintFilter icf = null;
		final String filter = ConstraintUtil.getModelFilter(eo.getClass().getName());

		if (filter != null || !filter.equals("")) { //$NON-NLS-1$
			icf = new IConstraintFilter() {
				public boolean accept(IConstraintDescriptor constraint, EObject target) {
					return constraint.getId().contains(filter);
				}
			};
		}

		HashMap<String, Boolean> rules = ReportUtil.getRulesState(icf);

		Set<String> rulesInException = ReportUtil.getRulesWithException(rules.keySet());

		csvWriter.writeRecord(new String[] { ReportMessages._Rule_State_Title });
		writeEmptyLine(1);

		int not_applied = 0;
		int applied = 0;
		for (Boolean state : rules.values()) {
			if (state.booleanValue()) {
				applied++;
			} else {
				not_applied++;
			}

		}

		csvWriter.writeRecord(new String[] { NLS.bind(ReportMessages._Rule_State_NumberOfConstraint, String.valueOf(rules.keySet().size())) });
		csvWriter.writeRecord(new String[] { NLS.bind(ReportMessages._Rule_State_NumberOfConstraint_Applied, String.valueOf(applied)) });

		if (rulesInException != null && !rulesInException.isEmpty()) {
			csvWriter.writeRecord(new String[] { NLS.bind(ReportMessages._Rule_State_NumberOfConstraint_In_Error, String.valueOf(rulesInException
					.size())) });
			csvWriter.writeRecord(new String[] { ReportMessages._Rule_State_Constraint_In_Error_Msg });

		}

		writeEmptyLine(1);

		csvWriter.writeRecord(new String[] { CSVWriter.EMPTY_STRING, ReportMessages._Rule_State_Tab_Header_RuleId,
				ReportMessages._Rule_State_Tab_Header_RuleState });

		for (String id : rules.keySet()) {
			csvWriter.writeRecord(new String[] { CSVWriter.EMPTY_STRING, ReportUtil.getRuleLabel(id),
					rules.get(id).booleanValue() ? ReportMessages._Rule_Enabled : ReportMessages._Rule_Disabled });
		}

		writeEmptyLine(1);

		if (rulesInException != null && !rulesInException.isEmpty()) {
			csvWriter.writeRecord(new String[] { CSVWriter.EMPTY_STRING, ReportMessages._Rule_In_Error_State_Title });
			for (String id : rules.keySet()) {
				csvWriter.writeRecord(new String[] { CSVWriter.EMPTY_STRING, id,
						rules.get(id).booleanValue() ? ReportMessages._Rule_Enabled : ReportMessages._Rule_Disabled });
			}
			writeEmptyLine(1);
		}
	}

	/**
	 * Translate a {@link Diagnostic} error level to an understandable string
	 * 
	 * @param error
	 *            the diagnostic error level
	 * @return an understandable string
	 */
	protected String getErrLevelAsString(int error) {
		switch (error) {
		case Diagnostic.OK:
			return ReportMessages._Diagnostic_OK_Level;
		case Diagnostic.INFO:
			return ReportMessages._Diagnostic_Info_Level;
		case Diagnostic.WARNING:
			return ReportMessages._Diagnostic_Warning_Level;
		case Diagnostic.ERROR:
			return ReportMessages._Diagnostic_Error_Level;
		default:
			return ReportMessages._Diagnostic_Unkown_Level;
		}
	}

	/**
	 * write resume about the report
	 * 
	 * @throws IOException
	 */
	protected void writeResume() throws IOException {

		EObject eo = (EObject) diagnostics.get(0).getData().get(0);

		IConstraintFilter icf = null;
		final String filter = ConstraintUtil.getModelFilter(eo.getClass().getName());

		if (filter != null || !filter.equals("")) { //$NON-NLS-1$
			icf = new IConstraintFilter() {
				public boolean accept(IConstraintDescriptor constraint, EObject target) {
					return constraint.getId().contains(filter);
				}
			};
		}

		HashMap<String, Boolean> rules = ReportUtil.getRulesState(icf);

		Set<String> rulesInException = ReportUtil.getRulesWithException(rules.keySet());

		int not_applied = 0;
		int applied = 0;
		for (Boolean state : rules.values()) {
			if (state.booleanValue()) {
				applied++;
			} else {
				not_applied++;
			}

		}

		int[] iErrWarInfo = { 0, 0, 0 };
		for (Diagnostic diagnostic : diagnostics) {
			int[] r = ReportUtil.getErrWarInf(diagnostic);
			for (int i = 0; i < 3; i++) {
				iErrWarInfo[i] += r[i];
			}
		}

		csvWriter.writeRecord(new String[] { NLS.bind(ReportMessages._Resume_Number_Of_Constraint_Applied, new Object[] { applied, rules.size() }) });
		csvWriter.writeRecord(new String[] { NLS.bind(ReportMessages._Resume_Constraint_On_Error, new Object[] { rulesInException.size() }) });
		csvWriter.writeRecord(new String[] { NLS.bind(ReportMessages._Resume_Number_Of_Error, new Object[] { iErrWarInfo[0], iErrWarInfo[1],
				iErrWarInfo[2] }) });

	}

}
