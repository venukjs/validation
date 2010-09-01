package org.artop.aal.autosar21.constraints.ecuc;

import gautosar.gecucdescription.GInstanceReferenceValue;
import gautosar.ggenericstructure.ginfrastructure.GIdentifiable;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGInstanceReferenceValueBasicConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar21.ecucdescription.InstanceReferenceValue;
import autosar21.ecucdescription.instanceref.InstanceReferenceValueValue;



public class InstanceReferenceValueBasicConstraint extends AbstractGInstanceReferenceValueBasicConstraint
{

	@Override
	protected IStatus doValidateValueSet(IValidationContext ctx,
			GInstanceReferenceValue gInstanceReferenceValue)
	{
		InstanceReferenceValue instanceReferenceValue = (InstanceReferenceValue) gInstanceReferenceValue;
		InstanceReferenceValueValue value = instanceReferenceValue.getValue();

		return validateValueSet(ctx, instanceReferenceValue, value);
	}

	@Override
	protected EObject getTargetDestination(
			GInstanceReferenceValue gInstanceReferenceValue)
	{
		InstanceReferenceValue instanceReferenceValue = (InstanceReferenceValue) gInstanceReferenceValue;
		InstanceReferenceValueValue value = instanceReferenceValue.getValue();
		
		return value.getValue();
	}

	@Override
	protected EList<? extends GIdentifiable> getTargetContexts(
			GInstanceReferenceValue gInstanceReferenceValue)
	{
		InstanceReferenceValue instanceReferenceValue = (InstanceReferenceValue) gInstanceReferenceValue;
		InstanceReferenceValueValue value = instanceReferenceValue.getValue();
		
		return value.getContexts();
	}

}
