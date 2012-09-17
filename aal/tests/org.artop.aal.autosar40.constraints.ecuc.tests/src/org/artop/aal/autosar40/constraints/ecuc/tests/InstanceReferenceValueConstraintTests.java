/**
 * <copyright>
 * 
 * Copyright (c) Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Continental Engineering Services - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

@SuppressWarnings("nls")
public class InstanceReferenceValueConstraintTests extends AbstractAutosar40ValidationTestCase {

	public InstanceReferenceValueConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.InstanceReferenceValueBasicConstraint_40";//$NON-NLS-1$
	}

	// completeness
	public void testInvalidInstanceReferenceValue_noValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.generic_valueNotSet);
	}

	// correctness
	public void testInvalidInstanceReferenceValue_noDestinationContext() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.instanceref_valueDestContextNotSet);
	}

	public void testInvalidInstanceReferenceValue_noDestinationType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/noDestinationType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, EcucConstraintMessages.reference_targetDestinationTypeNotAvailable);
	}

	public void testInvalidInstanceReferenceValue_invalidContextDestinationType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/invalidDestinationContext.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.reference_invalidContext, new String[] { "P-PORT-PROTOTYPE123" }));
	}

	public void testInvalidInstanceReferenceValue_wrongContextOrder() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/wrongContextOrder.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, NLS.bind(
				EcucConstraintMessages.instanceref_valueNotMatchDestContext, new String[] { "(P-PORT-PROTOTYPE )(SW-COMPONENT-PROTOTYPE )" }));
	}

	public void testInvalidInstanceReferenceValue_contextTypeNoContextValue() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/contextTypeNoContextValue.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR, NLS.bind(
				EcucConstraintMessages.instanceref_valueNotMatchDestContext,
				new String[] { "(SW-COMPONENT-PROTOTYPE )*(P-PORT-PROTOTYPE )(ROOT-SW-COMPOSITION-PROTOTYPE )" }));
	}

	public void testInvalidInstanceReferenceValue_contextTypeNoContextValueWithMultiplicity() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/contextTypeNoContextValueWithMultiplicity.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.OK, null);
	}

	public void testInvalidInstanceReferenceValue_doubleDashContextType() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/InstanceReferenceValue/doubleDashContextType.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.OK, null);
	}

}
