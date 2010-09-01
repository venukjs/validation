package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GParamConfContainerDef;
import gautosar.gecucparameterdef.GReferenceDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public class GReferenceDefBasicConstraint extends
		AbstractGParameterValueConstraint
{

	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return ctx.getTarget() instanceof GReferenceDef;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{
		GReferenceDef gReferenceDef = (GReferenceDef) ctx.getTarget();
		GParamConfContainerDef destination = gReferenceDef.gGetDestination();

		if (destination != null)
		{
			if (destination.eIsProxy())
			{
				return ctx
						.createFailureStatus(NLS.bind(Messages.reference_destinationNotResolved, AutosarURIFactory
										.getAbsoluteQualifiedName(destination)));
			}
				return ctx.createSuccessStatus();
			
		} 
		
		return ctx.createFailureStatus(Messages.reference_destinationNotSet);		
	}

	@Override
	protected boolean isValueSet(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		return true;
	}
}
