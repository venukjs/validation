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
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GInstanceReferenceValue;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GInstanceReferenceDef;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * Abstract superclass for the constraints implementations on an instance reference value.
 */
public abstract class AbstractGInstanceReferenceValueBasicConstraint extends AbstractGConfigReferenceValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) {
		return ctx.getTarget() instanceof GInstanceReferenceValue;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		GInstanceReferenceValue gInstanceReferenceValue = (GInstanceReferenceValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, gInstanceReferenceValue);
		if (status.isOK()) {
			// the validation of the value requires valid access to the
			// IntegerParamDef
			status = validateValue(ctx, gInstanceReferenceValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, GConfigReferenceValue gConfigReferenceValue) {
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gConfigReferenceValue);
		if (status.isOK()) {
			GConfigReference configReferenceDef = gConfigReferenceValue.gGetDefinition();
			if (!(configReferenceDef instanceof GInstanceReferenceDef)) {
				status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.generic_definitionNotOfType, "instance reference def")); //$NON-NLS-1$
			}
		}
		return status;
	}

	/**
	 * Performs the validation on the value of the given <code>gInstanceReferenceValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gInstanceReferenceValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected abstract IStatus doValidateValueSet(IValidationContext ctx, GInstanceReferenceValue gInstanceReferenceValue);

	protected IStatus validateValue(IValidationContext ctx, GInstanceReferenceValue gInstanceReferenceValue) {
		// default
		final IStatus status;

		status = doValidateValueSet(ctx, gInstanceReferenceValue);
		if (!status.isOK()) {
			return status;
		}

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0,
				NLS.bind(EcucConstraintMessages.generic_validationOf, "instance reference"), null); //$NON-NLS-1$
		multiStatus.add(validateInstanceReferenceTargetDestination(ctx, gInstanceReferenceValue));
		multiStatus.add(validateInstanceReferenceContextDestination(ctx, gInstanceReferenceValue));

		return multiStatus;
	}

	/**
	 * Performs the validation on the target destination of the given <code>gInstanceReferenceValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gInstanceReferenceValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateInstanceReferenceTargetDestination(IValidationContext ctx, GInstanceReferenceValue gInstanceReferenceValue) {
		final IStatus status;
		GInstanceReferenceDef referenceDef = (GInstanceReferenceDef) gInstanceReferenceValue.gGetDefinition();

		String destinationTypeName = referenceDef.gGetDestinationType();

		EObject valueObject = getTargetDestination(gInstanceReferenceValue);
		if (null == valueObject) {
			status = ctx.createFailureStatus(EcucConstraintMessages.instanceref_targetNotSet);
		} else if (valueObject.eIsProxy()) {
			status = ctx.createFailureStatus(EcucConstraintMessages.instanceref_targetNotResolved);
		} else if (null == destinationTypeName || destinationTypeName.length() == 0) {
			status = ctx.createFailureStatus(EcucConstraintMessages.reference_targetDestinationTypeNotAvailable);
		} else if (!isInstanceOfDestinationType(valueObject, destinationTypeName)) {
			status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_valueNotInstanceOfDestType, destinationTypeName));
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;

	}

	/**
	 * Performs the validation on the context destination of the given <code>gInstanceReferenceValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gInstanceReferenceValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateInstanceReferenceContextDestination(IValidationContext ctx, GInstanceReferenceValue gInstanceReferenceValue) {
		// TODO: CAUTION this algorithm only works in case no inheritance is
		// used

		IStatus status = ctx.createSuccessStatus();
		GInstanceReferenceDef referenceDef = (GInstanceReferenceDef) gInstanceReferenceValue.gGetDefinition();

		// CHECK if context of InstanceReferenceValue_value is compatible to
		// destinationContext
		EList<? extends GIdentifiable> contextList = getTargetContexts(gInstanceReferenceValue);
		String destinationContext = referenceDef.gGetDestinationContext();
		// CHECK if destinationContext available
		if (null != destinationContext && 0 != destinationContext.length()) {

			StringBuffer contextBuffer = new StringBuffer();
			StringBuffer destContextBuffer = new StringBuffer();

			// CHECK if value context available
			if (contextList.size() > 0) {

				String[] splitContext = destinationContext.split(" "); //$NON-NLS-1$
				String ctxClassName;

				for (int i = 0; i < contextList.size(); i++) {

					String eClassName = contextList.get(i).eClass().getName();
					// convert value context to a String, each item separated by a
					// space
					contextBuffer.append(eClassName);
					contextBuffer.append(" "); //$NON-NLS-1$

					for (String context : splitContext) {
						ctxClassName = getEClassName(ctx.getTarget(), context, false);
						if (ctxClassName != null) {
							if (destContextBuffer.lastIndexOf(ctxClassName) < 0) {
								destContextBuffer.append(ctxClassName);
								destContextBuffer.append(" "); //$NON-NLS-1$
							}

							// CHECK is a certain context value is available for the context type
							if (context.endsWith("*")) { //$NON-NLS-1$
								context = context.substring(0, context.length() - 1);
								if (ctxClassName.equals(eClassName) && getEClassName(ctx.getTarget(), context, true) == null) {
									status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_contextNotAvailable, context));
								}
							} else

							if (ctxClassName.equals(eClassName) && !isInstanceOfDestinationType(contextList.get(i), context)) {
								status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_contextNotAvailable, context));
							}
						} else {
							status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.reference_invalidContext, context));
							return status;
						}

					}
				}
			}

			// CHECK if value context String matches the definitionContext
			// regular expression
			String destinationContentRegex = getDestinationContextRegex(destContextBuffer.toString());
			if (!contextBuffer.toString().matches(destinationContentRegex)) {
				status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.instanceref_valueNotMatchDestContext, destinationContentRegex));
			} else {
				status = ctx.createSuccessStatus();
			}

		} else if (0 != contextList.size()) {
			status = ctx.createFailureStatus(EcucConstraintMessages.instanceref_valueDestContextNotSet);
		} else {
			status = ctx.createSuccessStatus();
		}

		return status;
	}

	/**
	 * Returns the target destination of the given <code>gInstanceReferenceValue</code>.
	 * 
	 * @param gInstanceReferenceValue
	 * @return
	 */
	protected abstract EObject getTargetDestination(GInstanceReferenceValue gInstanceReferenceValue);

	/**
	 * Returns the list with the target contexts of the given <code>gInstanceReferenceValue</code>.
	 * 
	 * @param gInstanceReferenceValue
	 * @return
	 */
	protected abstract EList<? extends GIdentifiable> getTargetContexts(GInstanceReferenceValue gInstanceReferenceValue);

	protected String getDestinationContextRegex(String destinationContext) {
		String destinationContentRegex = new String();

		String[] contextList = destinationContext.split("\\s+"); //$NON-NLS-1$

		for (String element : contextList) {
			String item = element;
			if (item.endsWith("*")) { //$NON-NLS-1$
				item = "(".concat(item); //$NON-NLS-1$
				item = item.substring(0, item.length() - 1);
				item = item.concat(" )*"); //$NON-NLS-1$
			} else {
				item = "(".concat(item); //$NON-NLS-1$
				item = item.concat(" )"); //$NON-NLS-1$
			}

			destinationContentRegex = destinationContentRegex + item;
		}

		return destinationContentRegex;
	}

}
