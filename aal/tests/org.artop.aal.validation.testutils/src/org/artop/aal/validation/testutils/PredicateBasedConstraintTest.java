/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     BMW Car IT - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.validation.testutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.artop.aal.gautosar.services.IMetaModelServiceProvider;
import org.artop.aal.validation.constraints.PredicateBasedConstraint;
import org.artop.aal.validation.testutils.internal.mock.MockConstraintDescriptor;
import org.artop.aal.validation.testutils.internal.mock.MockValidationContext;
import org.artop.aal.validation.testutils.internal.mock.MockValidationContext.FailureStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.junit.After;
import org.junit.Before;

/**
 * Base implementation for tests testing <code>PredicateBasedConstraint</code>s.
 */
public abstract class PredicateBasedConstraintTest {

	private static final String PLUGIN_ID = "org.artop.aal.validation.constraints.swc.tests"; //$NON-NLS-1$
	private static final String CONSTRAINT_DESCRIPTOR_ID = "ConstraintDescriptor"; //$NON-NLS-1$	
	private static final IConstraintDescriptor CONSTRAINT_DESCRIPTOR = new MockConstraintDescriptor(CONSTRAINT_DESCRIPTOR_ID, PLUGIN_ID);

	private IValidationContext DUMMY_CONTEXT = new MockValidationContext(CONSTRAINT_DESCRIPTOR_ID);

	protected IMetaModelServiceProvider fServiceProvider;
	protected PredicateBasedConstraint fConstraintUT;

	@Before
	public void setUp() throws Exception {
		ConstraintRegistry.getInstance().register(CONSTRAINT_DESCRIPTOR);
		fServiceProvider = createMockPredicatesServiceProvider();
		fConstraintUT = createConstraintUnderTest();
		fConstraintUT.setServiceProvider(fServiceProvider);
	}

	protected abstract IMetaModelServiceProvider createMockPredicatesServiceProvider();

	@After
	public void tearDown() throws Exception {
		ConstraintRegistry.getInstance().unregister(CONSTRAINT_DESCRIPTOR);
	}

	protected abstract PredicateBasedConstraint createConstraintUnderTest();

	protected void assertFailedValidation(String... expectedMessages) {
		IStatus failureStatus = fConstraintUT.validate(DUMMY_CONTEXT);
		IStatus[] failureStatuses = extractAllFailureStatuses(failureStatus);
		assertEquals(expectedMessages.length, failureStatuses.length);
		for (IStatus status : failureStatuses) {
			assertIsFailure(status);
			assertFailureMessage(status, expectedMessages);
		}
	}

	protected void assertSuccessfulValidation() {
		assertEquals(Status.OK_STATUS, fConstraintUT.validate(DUMMY_CONTEXT));
	}

	private void assertIsFailure(IStatus failureStatus) {
		assertEquals("The status returned by the constraint is not a failure status.", FailureStatus.class, failureStatus.getClass()); //$NON-NLS-1$
	}

	private IStatus[] extractAllFailureStatuses(IStatus failureStatus) {
		if (failureStatus.isMultiStatus()) {
			return failureStatus.getChildren();
		}
		return new IStatus[] { failureStatus };
	}

	private void assertFailureMessage(IStatus failureStatus, String... expectedMessages) {
		String failureMsg = failureStatus.getMessage();
		assertTrue("The returned failure message is not correct: \"" + failureMsg + "\"", Arrays.asList(expectedMessages).contains(failureMsg)); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
