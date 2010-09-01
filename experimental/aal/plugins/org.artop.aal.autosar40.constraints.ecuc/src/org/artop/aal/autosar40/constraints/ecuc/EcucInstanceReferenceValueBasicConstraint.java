package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucdescription.GInstanceReferenceValue;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGInstanceReferenceValueBasicConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar40.ecucdescription.EcucInstanceReferenceValue;
import autosar40.genericstructure.generaltemplateclasses.anyinstanceref.AnyInstanceRef;


public class EcucInstanceReferenceValueBasicConstraint extends AbstractGInstanceReferenceValueBasicConstraint
{

	@Override
	protected IStatus doValidateValueSet(IValidationContext ctx,
			GInstanceReferenceValue gInstanceReferenceValue)
	{
		EcucInstanceReferenceValue instanceReferenceValue = (EcucInstanceReferenceValue) gInstanceReferenceValue;
		AnyInstanceRef value = instanceReferenceValue.getValue();

		return validateValueSet(ctx, instanceReferenceValue, value);
	}

	@Override
	protected EObject getTargetDestination(
			GInstanceReferenceValue gInstanceReferenceValue)
	{
		EcucInstanceReferenceValue instanceReferenceValue = (EcucInstanceReferenceValue) gInstanceReferenceValue;
		AnyInstanceRef value = instanceReferenceValue.getValue();

		
		return value.getTarget();
	}

	@Override
	protected EList<? extends GIdentifiable> getTargetContexts(
			GInstanceReferenceValue gInstanceReferenceValue)
	{
		EcucInstanceReferenceValue instanceReferenceValue = (EcucInstanceReferenceValue) gInstanceReferenceValue;
		AnyInstanceRef value = instanceReferenceValue.getValue();

		
		return value.getContextElements();
	}
}
