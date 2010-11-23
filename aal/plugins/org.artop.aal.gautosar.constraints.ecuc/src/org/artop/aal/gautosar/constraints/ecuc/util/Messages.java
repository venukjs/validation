/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc.util;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.artop.aal.gautosar.constraints.ecuc.util.Messages"; //$NON-NLS-1$
	public static String generic_nullParametersException;
	public static String generic_validationOf;
	public static String generic_emptyValue;
	public static String generic_definitionReferenceNotSet;
	public static String generic_definitionReferenceNotResolved;
	public static String generic_definitionNotOfType;
	public static String generic_valueNotSet;
	public static String generic_validationNotPossible;
	public static String generic_noParent;

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

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
