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
public class InstanceReferenceValueConstraintTest extends AbstractAutosar3xValidationTestCase {

	public InstanceReferenceValueConstraintTest() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.InstanceReferenceValueBasicConstraint_3x";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidInstanceReferenceValue_noDefinition() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDefinition.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_definitionReferenceNotSet);
	}

	public void testInvalidInstanceReferenceValue_noValueValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noValueValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.instanceref_targetNotSet);
	}

	public void testInvalidInstanceReferenceValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_valueNotSet);
	}

	// consistency
	public void testInvalidInstanceReferenceValue_defTypeInvalid() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/defTypeInvalid.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.generic_definitionNotOfType, "instance reference def"));
	}

	// correctness
	public void testInvalidInstanceReferenceValue_noDestinationContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.instanceref_valueDestContextNotSet);
	}

	public void testInvalidInstanceReferenceValue_valueContextNoDestinationContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/valueContextNoDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.instanceref_valueDestContextNotSet);
	}

	public void testInvalidInstanceReferenceValue_destinationContextNoValueContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/destinationContextNoValueContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.instanceref_valueNotMatchDestContext, "(PPortPrototype )"));
	}

	public void testInvalidInstanceReferenceValue_contextNotMatchWithDestinationContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/contextNotMatchWithDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.instanceref_valueNotMatchDestContext, "(ComponentPrototype )"));
	}

	public void testInvalidInstanceReferenceValue_noDestinationType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDestinationType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.reference_targetDestinationTypeNotAvailable);
	}

	public void testInvalidInstanceReferenceValue_valueNotMatchWithDestinationType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/valueNotMatchWithDestinationType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.reference_valueNotInstanceOfDestType, "PPortPrototype"));
	}

	// valid
	public void testValidStringValue() throws Exception {
		EObject validModel = loadInputFile("ecuc/InstanceReferenceValue/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}
}
