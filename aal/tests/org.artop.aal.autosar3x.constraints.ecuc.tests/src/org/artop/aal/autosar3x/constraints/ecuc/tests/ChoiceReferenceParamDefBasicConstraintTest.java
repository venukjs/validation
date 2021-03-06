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
package org.artop.aal.autosar3x.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("nls")
public class ChoiceReferenceParamDefBasicConstraintTest extends AbstractAutosar3xValidationTestCase {
	public ChoiceReferenceParamDefBasicConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ChoiceReferenceParamDefBasicConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidChoiceReferenceParamDef_noDestination() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ChoiceReferenceParamDef/noDestination.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.choiceref_emptyDestination);
	}

	public void testInvalidChoiceReferenceParamDef_unresolvedDestination() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/ChoiceReferenceParamDef/unresolvedDestination.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.reference_destinationNotResolved, "/AUTOSAR/Os/unresolved"));
	}

	// valid
	public void testValidChoiceReferenceParamDef() throws Exception {
		EObject validModel = loadInputFile("ecuc/ChoiceReferenceParamDef/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
