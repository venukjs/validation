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
package org.artop.ecl.emf.validation.report;

import org.eclipse.osgi.util.NLS;

public class ReportMessages extends NLS {

	private static final String BUNDLE_NAME = "reportMessages"; //$NON-NLS-1$

	public static String _Header_Main_Title;
	public static String _Header_File_Title;
	public static String _Header_Project_Title;
	public static String _Header_Date;
	public static String _Header_Product;
	public static String _Header_FileList;

	public static String _Resume_Number_Of_Constraint_Applied;
	public static String _Resume_Constraint_On_Error;
	public static String _Resume_Number_Of_Error;
	public static String _Resume_OK;
	public static String _Resume_KO;

	public static String _Diagnostic_Number_Of_Error;
	public static String _Diagnostic_Number_Of_Warning;
	public static String _Diagnostic_Number_Of_Info;

	public static String _Diagnostic_Error_Level;
	public static String _Diagnostic_Warning_Level;
	public static String _Diagnostic_Info_Level;
	public static String _Diagnostic_OK_Level;
	public static String _Diagnostic_Unkown_Level;

	public static String _Diagnostic_No_Error;

	public static String _Diagnostic_EMFRuleId;

	public static String _Diagnostic_Column_Header_EOName;
	public static String _Diagnostic_Column_Header_EOURI;
	public static String _Diagnostic_Column_Header_DiagSev;
	public static String _Diagnostic_Column_Header_DiagMsg;
	public static String _Diagnostic_Column_Header_DiagRuleId;

	public static String _File_Line;

	public static String _Rule_Enabled;
	public static String _Rule_Disabled;

	public static String _Rule_State_NumberOfConstraint;
	public static String _Rule_State_NumberOfConstraint_Applied;
	public static String _Rule_State_NumberOfConstraint_In_Error;
	public static String _Rule_State_Constraint_In_Error_Msg;
	public static String _Rule_In_Error_State_Title;

	public static String _Rule_State_Title;
	public static String _Rule_State_Tab_Header_RuleId;
	public static String _Rule_State_Tab_Header_RuleState;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, ReportMessages.class);
	}

}
