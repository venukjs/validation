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

import junit.framework.TestCase;

import org.artop.aal.examples.validation.constraints.tests.mock.MockSwcPredicatesService.MockPredicate;
import org.artop.aal.gautosar.services.DefaultMetaModelServiceProvider;
import org.artop.aal.gautosar.services.predicates.swc.ISwcPredicatesService;
import org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationErrorCodesConstraint;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.sphinx.emf.metamodel.providers.EObjectMetaModelDescriptorProvider;
import org.eclipse.sphinx.emf.metamodel.providers.IMetaModelDescriptorProvider;

import autosar20.util.Autosar20Factory;
import autosar21.util.Autosar21Factory;
import autosar3x.util.Autosar3xFactory;
import autosar40.util.Autosar40Factory;

/**
 * This is an integration test which verifies that the EMF Validation extension points are configured correctly for the
 * {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}. Responsible for the
 * configuration is the <code>org.artop.aal.examples.validation.constraints</code> plugin.
 */
@SuppressWarnings("nls")
public class UniqueApplicationErrorCodesConstraintIntegrationTest extends TestCase {

	private static final String CLASS = "class";
	private IValidator<EObject> fValidator;
	private DefaultMetaModelServiceProvider fServiceProvider;

	public UniqueApplicationErrorCodesConstraintIntegrationTest() {
		this(null);
	}

	public UniqueApplicationErrorCodesConstraintIntegrationTest(String name) {
		super(name);
		ModelValidationService.getInstance().loadXmlConstraintDeclarations();
		fValidator = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
		fServiceProvider = new DefaultMetaModelServiceProvider();
	}

	/**
	 * Verifies that the <code>UniqueApplicationErrorCodesConstraint</code> has been registered with the EMF Validation
	 * Framework at least once.
	 */
	public void testShouldBeRegistered() {
		assertConstraintRegistration(UniqueApplicationErrorCodesConstraint.class);
	}

	/**
	 * Verifies that the {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}
	 * is applied to an AUTOSAR 2.0 {@link autosar20.swcomponent.portinterface.ClientServerInterface}.
	 */
	public void testShouldApplyConstraintToCSInterface20() {
		invokeShouldValidateConstraint(Autosar20Factory.eINSTANCE.createClientServerInterface());
	}

	/**
	 * Verifies that the {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}
	 * is <b>not</b> applied to an AUTOSAR 2.0 {@link autosar20.swcomponent.portinterface.SenderReceiverInterface}.
	 */
	public void testShouldIgnoreConstraintForSRInterface() {
		invokeShouldIgnoreConstraint(Autosar20Factory.eINSTANCE.createSenderReceiverInterface());
	}

	/**
	 * Verifies that the {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}
	 * is applied to an AUTOSAR 2.1 {@link autosar21.swcomponent.portinterface.ClientServerInterface}.
	 */

	public void testShouldApplyConstraintToCSInterface21() {
		invokeShouldValidateConstraint(Autosar21Factory.eINSTANCE.createClientServerInterface());
	}

	/**
	 * Verifies that the {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}
	 * is applied to an AUTOSAR 3.X {@link autosar3x.swcomponent.portinterface.ClientServerInterface}.
	 */
	public void testShouldApplyConstraintToCSInterface3x() {
		invokeShouldValidateConstraint(Autosar3xFactory.eINSTANCE.createClientServerInterface());
	}

	/**
	 * Verifies that the {@link org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationCodesConstraint}
	 * is applied to an AUTOSAR 4.0 {@link autosar40.swcomponent.portinterface.ClientServerInterface}.
	 */
	public void testShouldApplyConstraintToCSInterface40() {
		invokeShouldValidateConstraint(Autosar40Factory.eINSTANCE.createClientServerInterface());
	}

	@SuppressWarnings("unchecked")
	private <T extends EObject> void invokeShouldValidateConstraint(T eObject) {
		validate(eObject);
		getMockPredicate(eObject).assertWasInvokedOn(eObject);
		assertFalse("The constraint was disabled due to an internal error!", getDescriptorByClass(UniqueApplicationErrorCodesConstraint.class)
				.isError());
	}

	private <T extends EObject> void invokeShouldIgnoreConstraint(T eObject) {
		validate(eObject);
		getMockPredicate(eObject).assertWasNotInvoked();
		assertFalse("The constraint was disabled due to an internal error!", getDescriptorByClass(UniqueApplicationErrorCodesConstraint.class)
				.isError());
	}

	private <T extends EObject> void validate(T eObject) {
		getMockPredicate(eObject).clear();
		fValidator.validate(eObject);
	}

	@SuppressWarnings("unchecked")
	private <T extends EObject> MockPredicate<T> getMockPredicate(T eObject) {
		IMetaModelDescriptorProvider mmDescProvider = EObjectMetaModelDescriptorProvider.createMetaModelDescriptorProviderFor(eObject);
		ISwcPredicatesService service = fServiceProvider.getService(mmDescProvider, ISwcPredicatesService.class);
		return (MockPredicate<T>) service.hasUniqueErrorCodes();
	}

	private void assertConstraintRegistration(Class<? extends AbstractModelConstraint> constraintClass) {
		assertNotNull("Constraint \"" + constraintClass.getSimpleName() + "\" is not registered.",
				getDescriptorByClass(UniqueApplicationErrorCodesConstraint.class));

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

}
