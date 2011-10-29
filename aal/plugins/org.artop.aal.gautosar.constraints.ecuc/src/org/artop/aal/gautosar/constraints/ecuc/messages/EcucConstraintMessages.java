/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services, See4sys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 *     Continental Engineering Services - migration to gautosar
 *     See4sys -  
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc.messages;

import org.eclipse.osgi.util.NLS;

public final class EcucConstraintMessages extends NLS {

	/**
	 * Private constructor
	 */
	private EcucConstraintMessages() {
	}

	private static final String BUNDLE_NAME = "org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages"; //$NON-NLS-1$

	public static String generic_nullParametersException;
	public static String generic_validationOf;
	public static String generic_emptyValue;
	public static String generic_definitionReferenceNotSet;
	public static String generic_definitionReferenceNotResolved;
	public static String generic_definitionNotOfType;
	public static String generic_valueNotSet;
	public static String generic_defaultValueNotSet;
	public static String generic_validationNotPossible;
	public static String generic_noParent;
	public static String generic_notValidFormat;

	public static String structuralIntegrity_containmentProblem;
	public static String structuralIntegrity_NotAllowedInChoiceContainer;

	public static String enumeration_valueNotInLiterals;
	public static String string_valueTooBig;
	public static String string_valueNoIdentifier;

	public static String boundary_valueUnderMin;
	public static String boundary_valueAboveMax;
	public static String boundary_MinValueException;
	public static String boundary_MaxValueException;

	public static String multiplicity_lowerMultException;
	public static String multiplicity_upperMultException;
	public static String multiplicity_lowerMultNegative;
	public static String multiplicity_upperMultNegative;

	public static String multiplicity_minElementsExpected;
	public static String multiplicity_maxElementsExpected;
	public static String multiplicity_subContainersExpected;

	public static String instanceref_targetNotSet;
	public static String instanceref_targetNotResolved;
	public static String instanceref_valueNotMatchDestContext;
	public static String instanceref_valueDestContextNotSet;

	public static String choiceref_emptyDestination;
	public static String choiceref_containerNotInTheDest;

	public static String reference_destinationNotResolved;
	public static String reference_destinationNotSet;
	public static String reference_targetDestinationTypeNotAvailable;
	public static String reference_valueNotInstanceOfDestType;
	public static String reference_valueNotOfType;
	public static String reference_valueDefinitionNotSet;
	public static String reference_differentDefAndDestination;

	public static String containerDef_containerDefMissing;
	public static String containerDef_upperMultiplicityChanged;
	public static String containerDef_lowerMultiplicityMismatching;
	public static String containerDef_postBuildChangeableModified;

	public static String configParameter_ancestorEmptylist;
	public static String configParameter_defaultValueChanged;
	public static String configParameter_implConfigClassChanged;
	public static String configParameter_lowerMultiplicityChanged;
	public static String configParameter_upperMultiplicityChanged;
	public static String configParameter_symbolicNameValueModified;

	public static String configReference_lowerMultiplicityChanged;
	public static String configReference_upperMultiplicityChanged;

	public static String paramConfigContainerDef_configParameterMissing;
	public static String paramConfigContainerDef_configReferenceMissing;
	public static String paramConfigContainerDef_multipleConfigurationModified;

	public static String moduleDef_containerDefMissing;
	public static String moduleDef_selfReference;

	public static String enumerationParamDef_enumLiteralChanged;
	public static String enumerationParamDef_defaultValueUndeclaredInLiterals;

	public static String configParameter_symbolicNameValueIsMultiDeclared;
	public static String configParameter_configurationVariantRespectAsPreCompile;
	public static String configParameter_configurationVariantRespectAsPreCompileOrPublished;
	public static String configParameter_configurationVariantRespectAsPreCompileOrLink;
	public static String configParameter_configurationVariantRespectAsPreCompilePublishedOrLink;

	public static String paramDef_defaultValueNoIdentifier;
	public static String parameterValue_valueNotSet;

	public static String paramConfContainerDef_InChoiceContainerDefMultiplicity;
	public static String paramConfMultiplicity_isNotConsistency;

	public static String integerParamDef_upperLimitChanged;
	public static String integerParamDef_LowerLimitChangedInVendorSpecificModuleDefinition;
	public static String integerParamDef_defaultValueIsOutOfRange;
	public static String integerParamDef_defaultValueIsNotInteger;

	public static String floatParamDef_upperLimitChanged;
	public static String floatParamDef_LowerLimitChangedInVendorSpecificModuleDefinition;
	public static String floatParamDef_defaultValueIsOutOfRange;
	public static String floatParamDef_defaultValueIsNotFloat;

	public static String configReferenceValue_valueNotSet;

	public static String choiceContainerDef_multiplicityNotRespected;

	public static String modulesConfiguration_moduleDefTooMuch;
	public static String modulesConfiguration_moduleDefMissing;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, EcucConstraintMessages.class);
	}

}
