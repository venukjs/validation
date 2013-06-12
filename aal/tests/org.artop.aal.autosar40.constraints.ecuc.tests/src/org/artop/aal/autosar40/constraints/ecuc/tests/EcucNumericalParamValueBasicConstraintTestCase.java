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

import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;

public class EcucNumericalParamValueBasicConstraintTestCase extends AbstractAutosar40ValidationTestCase {

	@Override
	protected String getConstraintID() {

		return "org.artop.aal.autosar40.constraints.ecuc.EcucNumericalParamValueBasicConstraint_40"; //$NON-NLS-1$
	}

	/**
	 * Checks the successful validation of a Float parameter value whose min and max boundaries are on [-INF, INF]
	 * 
	 * @throws Exception
	 */
	public void testValidParameter_BoundaryInfinite() throws Exception {
		EObject validModel = loadInputFile("ecuc/FloatValue/infiniteBoundaries.arxml"); //$NON-NLS-1$

		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
