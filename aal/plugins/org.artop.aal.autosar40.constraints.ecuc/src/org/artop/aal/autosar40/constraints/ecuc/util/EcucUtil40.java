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
package org.artop.aal.autosar40.constraints.ecuc.util;

import java.math.BigInteger;
import java.util.List;

import org.artop.aal.autosar40.gautosar40.ecucparameterdef.GEcucFloatParamDef40XAdapter;
import org.artop.aal.autosar40.gautosar40.ecucparameterdef.GEcucIntegerParamDef40XAdapter;
import org.eclipse.emf.ecore.EObject;

import autosar40.ecucparameterdef.EcucBooleanParamDef;
import autosar40.ecucparameterdef.EcucEnumerationParamDef;
import autosar40.ecucparameterdef.EcucFloatParamDef;
import autosar40.ecucparameterdef.EcucFunctionNameDef;
import autosar40.ecucparameterdef.EcucFunctionNameDefConditional;
import autosar40.ecucparameterdef.EcucIntegerParamDef;
import autosar40.ecucparameterdef.EcucLinkerSymbolDef;
import autosar40.ecucparameterdef.EcucLinkerSymbolDefConditional;
import autosar40.ecucparameterdef.EcucMultilineStringParamDef;
import autosar40.ecucparameterdef.EcucMultilineStringParamDefConditional;
import autosar40.ecucparameterdef.EcucStringParamDef;
import autosar40.ecucparameterdef.EcucStringParamDefConditional;
import autosar40.genericstructure.formulalanguage.FormulaExpression;

public class EcucUtil40 {

	static final String DEFAULT_VALUE = "defaultValue"; //$NON-NLS-1$
	static final String MIN_VALUE = "min"; //$NON-NLS-1$
	static final String MAX_VALUE = "max"; //$NON-NLS-1$

	/** special value to be supported by Min Boundary of Float parameter definition and by Float parameter value */
	static final String POSITIVE_INFINITY_AS_STRING = "INF"; //$NON-NLS-1$

	/** special value to be supported by Max Boundary of Float parameter definition and by Float parameter value */
	static final String NEGATIVE_INFINITY_AS_STRING = "-INF"; //$NON-NLS-1$

	/**
	 * Get default value of Ecuc Parameter Definition. Return value of defaultValue feature by default
	 * 
	 * @param eObject
	 *            The EObject contain defaultValue to be get
	 * @return defaultValue
	 */
	public static String getDefaultValue(EObject eObject) {
		// Parameter is Boolean type
		if (eObject instanceof EcucBooleanParamDef) {
			EcucBooleanParamDef pd = (EcucBooleanParamDef) eObject;
			if (pd.getDefaultValue() != null) {
				return pd.getDefaultValue().getMixedText();
			}
		}
		// Parameter is Enumeration type
		else if (eObject instanceof EcucEnumerationParamDef) {
			return ((EcucEnumerationParamDef) eObject).getDefaultValue();

		}
		// Parameter is Float type
		else if (eObject instanceof EcucFloatParamDef) {
			EcucFloatParamDef pd = (EcucFloatParamDef) eObject;
			FormulaExpression defaultValue = new GEcucFloatParamDef40XAdapter(pd).getDefaultValue();
			if (defaultValue != null) {
				return defaultValue.getMixedText();
			}

		}
		// Parameter is FunctionName type
		else if (eObject instanceof EcucFunctionNameDef) {
			List<EcucFunctionNameDefConditional> variants = ((EcucFunctionNameDef) eObject).getEcucFunctionNameDefVariants();
			if (variants.size() > 0) {
				return variants.get(0).getDefaultValue();
			}

		}
		// Parameter is Integer type
		else if (eObject instanceof EcucIntegerParamDef) {
			GEcucIntegerParamDef40XAdapter pd = new GEcucIntegerParamDef40XAdapter((EcucIntegerParamDef) eObject);
			if (pd.getDefaultValue() != null) {
				return pd.getDefaultValue().getMixedText();
			}

		}
		// Parameter is Linker type
		else if (eObject instanceof EcucLinkerSymbolDef) {
			List<EcucLinkerSymbolDefConditional> variants = ((EcucLinkerSymbolDef) eObject).getEcucLinkerSymbolDefVariants();
			if (variants.size() > 0) {
				return variants.get(0).getDefaultValue();
			}

		}
		// Parameter is MultilineString type
		else if (eObject instanceof EcucMultilineStringParamDef) {
			List<EcucMultilineStringParamDefConditional> variants = ((EcucMultilineStringParamDef) eObject).getEcucMultilineStringParamDefVariants();
			if (variants.size() > 0) {
				return variants.get(0).getDefaultValue();
			}

		}
		// Parameter is String type
		else if (eObject instanceof EcucStringParamDef) {
			List<EcucStringParamDefConditional> variants = ((EcucStringParamDef) eObject).getEcucStringParamDefVariants();
			if (variants.size() > 0) {
				return variants.get(0).getDefaultValue();
			}

		}
		return org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil.getFeatureValue(eObject, DEFAULT_VALUE);
	}

	/**
	 * Get Ecuc Parameter Defintion Min's value
	 * 
	 * @param eObject
	 *            The EObject contain Min value to be get
	 * @return value of Min
	 */
	public static Object getMin(EObject eObject) {
		// Get EcucFloatParamDef's Min value
		if (eObject instanceof EcucFloatParamDef) {
			FormulaExpression minVarPoint = new GEcucFloatParamDef40XAdapter((EcucFloatParamDef) eObject).getMin();
			if (minVarPoint != null) {
				String mixed = minVarPoint.getMixedText();
				if (mixed != null) {
					try {
						Double min = convertStringToDouble(mixed);
						return min;
					} catch (NumberFormatException ex) {
						// Fail silent
					}
				}
			}
		}
		// Get EcucIntegerParamDef's Min value
		else if (eObject instanceof EcucIntegerParamDef) {
			FormulaExpression minVarPoint = new GEcucIntegerParamDef40XAdapter((EcucIntegerParamDef) eObject).getMin();
			if (minVarPoint != null) {
				String mixed = minVarPoint.getMixedText();
				if (mixed != null) {
					try {
						BigInteger min = new BigInteger(mixed);
						return min;
					} catch (NumberFormatException ex) {
						// Fail silent
					}
				}
			}
		}
		return org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil.getFeatureValue(eObject, MIN_VALUE);
	}

	/**
	 * Get Ecuc Parameter Defintion Max's value
	 * 
	 * @param eObject
	 *            The EObject contain Max value to be get
	 * @return value of Max
	 */
	public static Object getMax(EObject eObject) {
		// Get EcucFloatParamDef's Max value
		if (eObject instanceof EcucFloatParamDef) {
			FormulaExpression maxVarPoint = new GEcucFloatParamDef40XAdapter((EcucFloatParamDef) eObject).getMax();
			if (maxVarPoint != null) {
				String mixed = maxVarPoint.getMixedText();
				if (mixed != null) {
					try {
						Double max = convertStringToDouble(mixed);
						return max;
					} catch (NumberFormatException ex) {
						// Fail silent
					}
				}
			}

		}
		// Get EcucFloatParamDef's Max value
		else if (eObject instanceof EcucIntegerParamDef) {
			FormulaExpression maxVarPoint = new GEcucIntegerParamDef40XAdapter((EcucIntegerParamDef) eObject).getMax();
			if (maxVarPoint != null) {
				String mixed = maxVarPoint.getMixedText();
				if (mixed != null) {
					try {
						BigInteger max = new BigInteger(mixed);
						return max;
					} catch (NumberFormatException ex) {
						// Fail silent
					}
				}
			}
		}
		return org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil.getFeatureValue(eObject, MAX_VALUE);
	}

	/**
	 * Returns a {@link Double} corresponding to the given string representation. The only difference from calling
	 * <code> new Double(string)</code>, is that special values: <li><b>-INF</b></li> and <li><b>INF</b></li> <br>
	 * are also accepted in order to obtain {@link Double#NEGATIVE_INFINITY}, respective
	 * {@link Double#POSITIVE_INFINITY}
	 * 
	 * @param string
	 *            string representation to be converted to a {@link Double}
	 * @throws NumberFormatException
	 *             exception in case conversion fails
	 * @return a {@link Double}
	 */

	public static Double convertStringToDouble(String string) throws NumberFormatException {
		if (string == null || string.equals("")) //$NON-NLS-1$
		{
			return null;
		} else if (string.equalsIgnoreCase(NEGATIVE_INFINITY_AS_STRING)) {
			return Double.NEGATIVE_INFINITY;
		} else if (string.equalsIgnoreCase(POSITIVE_INFINITY_AS_STRING)) {
			return Double.POSITIVE_INFINITY;
		} else {
			return new Double(string);
		}
	}

	/**
	 * Returns a string representation of the given Double <code>value</code>. The only difference towards the
	 * {@link Double#toString()} implementation, is that for the special values: {@link Double#NEGATIVE_INFINITY} <br>
	 * {@link Double#POSITIVE_INFINITY}, <b>-INF</b>, respectively <b>INF</b> is returned.
	 * 
	 * @param value
	 *            Double value
	 * @return a string representation of the given Double
	 */
	public static String convertDoubleToString(Double value) {
		if (value == Double.NEGATIVE_INFINITY) {
			return NEGATIVE_INFINITY_AS_STRING;
		} else if (value == Double.POSITIVE_INFINITY) {
			return POSITIVE_INFINITY_AS_STRING;
		} else {
			return String.valueOf(value);
		}
	}

}
