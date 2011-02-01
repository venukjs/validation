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
package org.artop.aal.validation.constraints.swc.portinterface;

import static org.artop.ecl.emf.metamodel.providers.EObjectMetaModelDescriptorProvider.createMetaModelDescriptorProviderFor;
import gautosar.gswcomponents.gportinterface.GClientServerInterface;

import java.util.ArrayList;

import org.artop.aal.gautosar.services.DefaultMetaModelServiceProvider;
import org.artop.aal.gautosar.services.IMetaModelServiceProvider;
import org.artop.aal.gautosar.services.predicates.ExplainablePredicate;
import org.artop.aal.gautosar.services.predicates.swc.ISwcPredicatesService;
import org.artop.aal.gautosar.services.predicates.swc.portinterface.HasUniqueErrorCodes.DuplicateErrorCodes;
import org.artop.aal.gautosar.services.predicates.swc.portinterface.HasUniqueErrorCodes.DuplicateErrorCodes.MultiplyAssignedErrorCodeValue;
import org.artop.aal.validation.constraints.swc.internal.portinterface.messages.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;
import org.eclipse.osgi.util.NLS;

/**
 * A Constraint which validates that all defined possible errors within a client server interface have unique values.
 * The constraint will create a failure message for each error code which is not unique.
 */
public class UniqueApplicationErrorCodesConstraint extends AbstractModelConstraint {

	private static final String MSG_NAME = "uniqueApplicationErrorCodes_Msg"; //$NON-NLS-1$

	private static final IMetaModelServiceProvider SERVICE_PROVIDER = new DefaultMetaModelServiceProvider();

	private IMetaModelServiceProvider fServiceProvider;

	/**
	 * Default constructor for creating an <code>UniqueApplicationErrorCodesConstraint</code> instance.
	 */
	public UniqueApplicationErrorCodesConstraint() {
		this(SERVICE_PROVIDER);
	}

	/**
	 * Constructor for creating an <code>UniqueApplicationErrorCodesConstraint</code> instance. The constraint will
	 * retrieve its {@link com.google.common.base.Predicate}s from the <code>IMetaModelServiceProvider</code> passed to
	 * the constructor.
	 * 
	 * @param serviceProvider
	 *            the <code>IMetaModelServiceProvider</code> from which the constraint will get its
	 *            <code>Predicate</code>s
	 */
	public UniqueApplicationErrorCodesConstraint(IMetaModelServiceProvider serviceProvider) {
		fServiceProvider = serviceProvider;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IStatus validate(IValidationContext ctx) {
		GClientServerInterface csInterface = (GClientServerInterface) ctx.getTarget();
		ExplainablePredicate<GClientServerInterface> areErrorCodesUnique = getAreErrorCodesUniquePredicate(csInterface);
		if (!areErrorCodesUnique.apply(csInterface)) {
			return createFailures(ctx, (DuplicateErrorCodes) areErrorCodesUnique.explain());
		}
		return ctx.createSuccessStatus();
	}

	private ExplainablePredicate<GClientServerInterface> getAreErrorCodesUniquePredicate(EObject contextEObject) {
		return getSwcPredicatesService(contextEObject).hasUniqueErrorCodes();
	}

	private ISwcPredicatesService getSwcPredicatesService(EObject contextEObject) {
		return fServiceProvider.getService(createMetaModelDescriptorProviderFor(contextEObject), ISwcPredicatesService.class);
	}

	private IStatus createFailures(IValidationContext ctx, DuplicateErrorCodes reason) {
		Iterable<MultiplyAssignedErrorCodeValue> duplicateErrors = reason.getDuplicateErrorCodes();
		ArrayList<IStatus> failureStatuses = new ArrayList<IStatus>();
		for (MultiplyAssignedErrorCodeValue nonUniqueCode : duplicateErrors) {
			failureStatuses.add(ctx.createFailureStatus(getMessage(nonUniqueCode)));
		}
		return ConstraintStatus.createMultiStatus(ctx, failureStatuses);
	}

	private String getMessage(MultiplyAssignedErrorCodeValue nonUniqueCode) {
		try {
			return NLS.bind((String) Messages.class.getField(MSG_NAME).get(null), nonUniqueCode.getValue(), nonUniqueCode.labelsToString());
		} catch (Exception e) {
			return "The following error code is not unique: " + nonUniqueCode.toString(); //$NON-NLS-1$
		}
	}

}
