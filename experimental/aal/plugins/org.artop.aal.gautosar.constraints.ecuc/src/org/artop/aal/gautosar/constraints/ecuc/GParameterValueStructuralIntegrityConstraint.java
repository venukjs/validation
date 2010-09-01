package org.artop.aal.gautosar.constraints.ecuc;


import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GChoiceContainerDef;
import gautosar.gecucparameterdef.GConfigParameter;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Superclass for the constraints implementations of the structural integrity of a parameter value.
 * 
 */

public class GParameterValueStructuralIntegrityConstraint extends AbstractModelConstraintWithPrecondition
{
	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		boolean isApplicable = false;

		if (ctx.getTarget() instanceof GParameterValue)
		{
			// required ECUC description objects
			GParameterValue gParameterValue = (GParameterValue) ctx.getTarget();
			GContainer parentContainer = (GContainer) gParameterValue.eContainer();

			if (null != parentContainer)
			{
				// required ECUC definition objects
				GContainerDef parentGContainerDef = parentContainer.gGetDefinition();
				GConfigParameter gConfigReference = gParameterValue.gGetDefinition();
				isApplicable = null != parentGContainerDef && false == parentGContainerDef.eIsProxy();
				isApplicable &= null != gConfigReference && false == gConfigReference.eIsProxy();
			}
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{
		GParameterValue gParameterValue = (GParameterValue) ctx.getTarget();
		GContainer parentGContainer = (GContainer) gParameterValue.eContainer();

		return validateStructuralIntegrity(ctx, gParameterValue, parentGContainer);

	}

	private IStatus validateStructuralIntegrity(IValidationContext ctx, GParameterValue GParameterValue, GContainer parentContainer)
	{
		final IStatus status;

		GContainerDef parentGContainerDef = parentContainer.gGetDefinition();
		GConfigParameter gConfigParameter = GParameterValue.gGetDefinition();

		if (parentGContainerDef instanceof GChoiceContainerDef)
		{
			status = ctx.createFailureStatus(NLS.bind(Messages.structuralIntegrity_NotAllowedInChoiceContainer, "reference values"));
		}
		else if (parentGContainerDef instanceof GParamConfContainerDef)
		{
			// the parent containers definition is a GParamConfContainerDef
			GParamConfContainerDef parentGParamConfContainerDef = (GParamConfContainerDef) parentGContainerDef;
			if (EcucUtil.getAllParametersOf(parentGParamConfContainerDef).contains(gConfigParameter))
			{
				status = ctx.createSuccessStatus(); // reference is valid
			}
			else
			{
				status = ctx.createFailureStatus(NLS.bind(Messages.structuralIntegrity_containmentProblem,"parameter value",gConfigParameter.gGetShortName()
						+ " not allowed here"));
			}

		} 
		else
		{
			// in the current metamodel we only find expect GParamConfContainerDef and ChoiceContainerDef
			// The assert will warn in case of metamodel extensions
			assert false;
			status = ctx.createSuccessStatus();
		}
		return status;

	}

}
