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
 *     Continental AG - Mark class as Splitable aware.
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.services.DefaultMetaModelServiceProvider;
import org.artop.aal.gautosar.services.IMetaModelServiceProvider;
import org.artop.aal.gautosar.services.ecuc.IMetaModelUtilityService;
import org.artop.aal.validation.constraints.AbstractSplitModelConstraintWithPrecondition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;
import org.eclipse.sphinx.emf.metamodel.MetaModelDescriptorRegistry;

/**
 * Abstract superclass for the constraints implementations on a config reference value.
 */
public abstract class AbstractGConfigReferenceValueConstraint extends AbstractSplitModelConstraintWithPrecondition {

	/**
	 * Performs the validation on the definition of the given <code>gConfigReferenceValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gConfigReferenceValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateDefinitionRef(IValidationContext ctx, GConfigReferenceValue gConfigReferenceValue) {
		// check if definition is set and available
		final IStatus status;
		if (null == gConfigReferenceValue.gGetDefinition()) {
			status = ctx.createFailureStatus(EcucConstraintMessages.generic_definitionReferenceNotSet);
		} else if (gConfigReferenceValue.gGetDefinition().eIsProxy()) {
			status = ctx.createFailureStatus(EcucConstraintMessages.generic_definitionReferenceNotResolved);
		} else {
			status = validateContainmentStructure(ctx, gConfigReferenceValue);
		}
		return status;
	}

	/**
	 * Performs the validation on the structure of the given <code>gConfigReferenceValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gConfigReferenceValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	private IStatus validateContainmentStructure(IValidationContext ctx, GConfigReferenceValue gConfigReferenceValue) {
		final IStatus status;

		EObject parent = gConfigReferenceValue.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus(EcucConstraintMessages.generic_noParent);
		} else {
			GConfigReference configReference = gConfigReferenceValue.gGetDefinition();
			if (parent instanceof GContainer) {
				// the current Container is contained in another Container
				GContainer parentContainer = (GContainer) parent;
				GContainerDef parentContainerDef = parentContainer.gGetDefinition();

				if (parentContainerDef instanceof GParamConfContainerDef) {
					// the parent containers definition is a
					// ParamConfContainerDef
					GParamConfContainerDef parentParamConfContainerDef = (GParamConfContainerDef) parentContainerDef;
					if (parentParamConfContainerDef.gGetReferences().contains(configReference)) {
						status = ctx.createSuccessStatus(); // reference is
															// valid
					} else {
						status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.structuralIntegrity_containmentProblem, "reference value", //$NON-NLS-1$
								configReference.gGetShortName()));
					}
				} else if (parentContainerDef instanceof GChoiceContainerDef) {
					// TODO: create testcase
					status = ctx.createFailureStatus(NLS.bind(EcucConstraintMessages.structuralIntegrity_NotAllowedInChoiceContainer,
							"reference value")); //$NON-NLS-1$
				} else {
					status = ctx.createSuccessStatus();
				}
			} else {
				// we only expect an object of type Container
				status = ctx.createSuccessStatus();
			}

		}
		return status;
	}

	/**
	 * Retrieves EClass name for a given <code>destinationTypeName</code> or null if the class does not exist.
	 * <code>checkInstance</code> flag indicates whether checking whether <code>instance</code> matching of the
	 * <code>destinationTypeName</code> will be taken into account
	 * 
	 * @param instance
	 * @param destinationTypeName
	 * @param checkInstance
	 * @return
	 */
	protected String getEClassName(EObject instance, String destinationTypeName, boolean checkInstance) {
		IMetaModelServiceProvider provider = new DefaultMetaModelServiceProvider();

		IMetaModelUtilityService service = provider.getService(MetaModelDescriptorRegistry.INSTANCE.getDescriptor(instance),
				IMetaModelUtilityService.class);
		if (service == null) {
			return null;
		}

		String multiplicity = ""; //$NON-NLS-1$
		// get correct EClass classifier
		if (destinationTypeName.endsWith("*")) { //$NON-NLS-1$
			destinationTypeName = destinationTypeName.substring(0, destinationTypeName.length() - 1);
			multiplicity = "*"; //$NON-NLS-1$
		}
		EClass destinationEClass = service.findEClass(destinationTypeName);

		EClass metaClass = instance.eClass();
		String metaClassName = getMetaClassName(metaClass);

		if (checkInstance && !(metaClassName.equals(destinationTypeName) || metaClass.equals(destinationEClass))) {
			return null;
		}

		return destinationEClass != null ? getMetaClassName(destinationEClass) + multiplicity : null;

	}

	/**
	 * Returns the meta class name used for destination type and destination context. AUTOSAR 4.0 uses the XML name of
	 * the meta class unlike AUTOSAR 3x which use directly the EClass name. This method should be override by AUTOSAR 40
	 * constraints to return the XML name from the ExtendedMetaData
	 * 
	 * @param eObject
	 * @return
	 */
	protected String getMetaClassName(EClass eClass) {
		return eClass.getName();
	}

	/**
	 * Checks whether the given <code>instance</code> is an instance of the destination with the given
	 * <code>destinationTypeName</code>.
	 * 
	 * @param instance
	 * @param destinationTypeName
	 * @return
	 */
	protected boolean isInstanceOfDestinationType(EObject instance, String destinationTypeName) {
		boolean isInstanceOfDestinationType = false;
		IMetaModelServiceProvider provider = new DefaultMetaModelServiceProvider();

		IMetaModelUtilityService service = provider.getService(MetaModelDescriptorRegistry.INSTANCE.getDescriptor(instance),
				IMetaModelUtilityService.class);
		if (service == null) {
			return false;
		}

		// get correct EClass classifier
		EClass destinationEClass = service.findEClass(destinationTypeName);

		EClass metaClass = instance.eClass();
		String metaClassName = getMetaClassName(metaClass);

		if (metaClassName.equals(destinationTypeName) || metaClass.equals(destinationEClass)) {
			isInstanceOfDestinationType = true;
		} else {
			// get all super types of the metaClass and check if destination
			// type is a
			// super type
			for (EClass superType : metaClass.getESuperTypes()) {
				// check if destination type is a super type of value class
				if (getMetaClassName(superType).equals(destinationTypeName) || superType.equals(destinationEClass)) {
					isInstanceOfDestinationType = true;
					break;
				}
			}
		}
		return isInstanceOfDestinationType;
	}

	/**
	 * Performs the validation on the value of the given <code>gConfigReferenceValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current constraint evaluation environment
	 * @param gConfigReferenceValue
	 *            the element on which the validation is performed.
	 * @param value
	 *            the value of <code>gConfigReferenceValue</code>
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateValueSet(IValidationContext ctx, GConfigReferenceValue gConfigReferenceValue, Object value) {
		if (null == value || value.equals("")) { //$NON-NLS-1$
			return ctx.createFailureStatus(EcucConstraintMessages.generic_valueNotSet);
		}

		return ctx.createSuccessStatus();

	}
}