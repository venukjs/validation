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
package org.artop.aal.examples.validation.constraints.tests;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.artop.aal.gautosar.services.builder.swc.portinterface.GClientServerInterfaceBuilder.clientServerInterface;
import static org.artop.aal.gautosar.services.builder.swc.portinterface.GSenderReceiverInterfaceBuilder.senderReceiverInterface;

import java.util.Arrays;
import java.util.Collection;

import org.artop.aal.common.metamodel.AutosarReleaseDescriptor;
import org.artop.aal.gautosar.services.builder.GBuilder;
import org.artop.aal.gautosar.services.builder.GMaker;
import org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationErrorCodesConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.model.IConstraintStatus;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.emf.validation.util.XmlConfig;
import org.eclipse.emf.validation.xml.IXmlConstraintDescriptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import autosar21.util.Autosar21ReleaseDescriptor;
import autosar3x.util.Autosar3xReleaseDescriptor;
import autosar40.util.Autosar40ReleaseDescriptor;

/**
 * This is an integration test which verifies that the EMF Validation extension points are configured correctly for the
 * {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}. Responsible for the
 * configuration is the <code>org.artop.aal.examples.validation.constraints</code> plugin.
 */
@SuppressWarnings("nls")
@RunWith(Parameterized.class)
public class UniqueApplicationErrorCodesConstraintIntegrationTest {

	private static final Autosar40ReleaseDescriptor AR_40 = Autosar40ReleaseDescriptor.INSTANCE;
	private static final Autosar3xReleaseDescriptor AR_3X = Autosar3xReleaseDescriptor.INSTANCE;
	private static final Autosar21ReleaseDescriptor AR_21 = Autosar21ReleaseDescriptor.INSTANCE;
	private static final Class<UniqueApplicationErrorCodesConstraint> UNIQUE_ERRORS_CONSTRAINT = UniqueApplicationErrorCodesConstraint.class;
	private static final String CLASS = "class";
	private IValidator<EObject> fValidator;
	private AutosarReleaseDescriptor fRelease;

	@Parameters
	public static Collection<Object[]> releases() {
		return Arrays.asList(new Object[][] { { AR_21 }, { AR_3X }, { AR_40 } });
	}

	public UniqueApplicationErrorCodesConstraintIntegrationTest(AutosarReleaseDescriptor release) {
		ModelValidationService.getInstance().loadXmlConstraintDeclarations();
		fValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
		fValidator.setReportSuccesses(true);
		fRelease = release;
	}

	/**
	 * Verifies that the <code>UniqueApplicationErrorCodesConstraint</code> has been registered with the EMF Validation
	 * Framework at least once.
	 */
	@Test
	public void testShouldBeRegistered() {
		assertConstraintRegistration(UNIQUE_ERRORS_CONSTRAINT);
	}

	/**
	 * Verifies that the {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}
	 * is applied to an AUTOSAR 2.0 {@link autosar20.swcomponent.portinterface.ClientServerInterface}.
	 */
	@Test
	public void testShouldApplyConstraintToCSInterface() {
		assertConstraintCalled(clientServerInterface("csIfc"));
	}

	/**
	 * Verifies that the {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}
	 * is <b>not</b> applied to an AUTOSAR 2.0 {@link autosar20.swcomponent.portinterface.SenderReceiverInterface}.
	 */
	@Test
	public void testShouldIgnoreConstraintForSRInterface() {
		assertConstraintNotCalled(senderReceiverInterface("srIfc"));
	}

	private void assertConstraintRegistration(Class<? extends AbstractModelConstraint> constraintClass) {
		assertNotNull("Constraint \"" + constraintClass.getSimpleName() + "\" is not registered.", getDescriptorByClass(UNIQUE_ERRORS_CONSTRAINT));
	}

	private <T extends EObject> void assertConstraintNotCalled(GBuilder<T> builder) {
		IStatus status = validate(builder);
		assertFalse("The constraint was called although not expected! (status was found)", contains(status, UNIQUE_ERRORS_CONSTRAINT));
	}

	private <T extends EObject> void assertConstraintCalled(GBuilder<T> builder) {
		IStatus status = validate(builder);
		assertTrue("The constraint was not called! (no status found)", contains(status, UNIQUE_ERRORS_CONSTRAINT));
	}

	private IStatus validate(GBuilder<? extends EObject> builder) {
		IStatus status = fValidator.validate(make(builder));
		assertFalse("The constraint was disabled due to an internal error!", getDescriptorByClass(UNIQUE_ERRORS_CONSTRAINT).isError());
		return status;
	}

	private boolean contains(IStatus status, Class<? extends AbstractModelConstraint> constraint) {
		if (status instanceof IConstraintStatus) {
			IConstraintDescriptor descriptor = ((IConstraintStatus) status).getConstraint().getDescriptor();
			if (descriptor instanceof IXmlConstraintDescriptor) {
				String className = ((IXmlConstraintDescriptor) descriptor).getConfig().getAttribute(XmlConfig.A_CLASS);
				if (constraint.getName().equals(className)) {
					return true;
				}
			}
		}
		return false;
	}

	private IConstraintDescriptor getDescriptorByClass(Class<UniqueApplicationErrorCodesConstraint> constraintClass) {
		ConstraintRegistry registry = ConstraintRegistry.getInstance();
		for (IConstraintDescriptor constraint : registry.getAllDescriptors()) {
			String className = ((IParameterizedConstraintDescriptor) constraint).getParameterValue(CLASS);
			if (constraintClass.getName().equals(className)) {
				return constraint;
			}
		}
		return null;
	}

	private <T extends Notifier> T make(GBuilder<T> builder) {
		return GMaker.make(fRelease).from(builder);
	}

}
