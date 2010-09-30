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

import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

public class ContainerReferenceValueMultiplicityConstraintTests extends AbstractAutosar3xValidationTestCase
{

	public ContainerReferenceValueMultiplicityConstraintTests() {
		super();
	}

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar3x.constraints.ecuc.ContainerReferenceValueMultiplicityConstraint_3x";//$NON-NLS-1$
	}

	public void testInvalidContainer_mandatoryReferenceValueMissing() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/mandatoryReferenceValueMissing.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,NLS.bind(Messages.multiplicity_minElementsExpected, new String[]{"1","config reference values","/AUTOSAR/Osss/OsAlarmmm/DestPduRef","0"}));
	}

	public void testInvalidContainer_referenceValueLowerMultiplicityViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/referenceValueLowerMultiplicityViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,NLS.bind(Messages.multiplicity_minElementsExpected, new String[]{"2","config reference values","/AUTOSAR/Osss/OsAlarmmm/DestPduRef","1"}));
	}

	public void testInvalidContainer_referenceValueUpperMultiplicityViolated() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Container/referenceValueUpperMultiplicityViolated.arxml");
		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,NLS.bind(Messages.multiplicity_maxElementsExpected, new String[]{"1","config reference values","/AUTOSAR/Osss/OsAlarmmm/DestPduRef","2"}));
	}

	// valid
	public void testValidContainer() throws Exception {
		EObject validModel = loadInputFile("ecuc/Container/valid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
