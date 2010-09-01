package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GChoiceReferenceDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Superclass for the constraints implementations on reference def.
 * 
 */
public class GChoiceReferenceDefBasicConstraint extends
		AbstractGParameterValueConstraint
{
	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return ctx.getTarget() instanceof GChoiceReferenceDef;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this
				.getClass().getName(), null);
		GChoiceReferenceDef gChoiceReferenceDef = (GChoiceReferenceDef) ctx
				.getTarget();

		if (gChoiceReferenceDef.gGetDestinations().isEmpty())
		{
			multiStatus
					.add(ctx.createFailureStatus(Messages.choiceref_emptyDestination));
		} else
		{
			for (GParamConfContainerDef choiceParamConfContainerDef : gChoiceReferenceDef
					.gGetDestinations())
			{
				if (choiceParamConfContainerDef.eIsProxy())
				{
					multiStatus
							.add(ctx.createFailureStatus(NLS.bind(Messages.reference_destinationNotResolved,AutosarURIFactory
											.getAbsoluteQualifiedName(choiceParamConfContainerDef))));
				}
			}
		}
		return multiStatus;

	}

	@Override
	protected boolean isValueSet(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		// TODO Auto-generated method stub
		return true;
	}
}
