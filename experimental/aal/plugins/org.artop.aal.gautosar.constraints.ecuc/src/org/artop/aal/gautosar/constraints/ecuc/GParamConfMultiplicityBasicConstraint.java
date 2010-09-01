package org.artop.aal.gautosar.constraints.ecuc;


import gautosar.gecucparameterdef.GParamConfMultiplicity;

import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Superclass for the constraints implementations on the lower and upper multiplicity of a param conf multiplicity object.
 * 
 */
public class GParamConfMultiplicityBasicConstraint extends AbstractModelConstraintWithPrecondition 
{

	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return ctx.getTarget() instanceof GParamConfMultiplicity;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) 
	{
		GParamConfMultiplicity gParamConfMultiplicity = (GParamConfMultiplicity) ctx.getTarget();

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		// validate lower multiplicity
		String lowerMultiplicityString = gParamConfMultiplicity.gGetLowerMultiplicityAsString();
		if (lowerMultiplicityString != null)
		{
			try 
			{
				int lowerMultiplicity = Integer.parseInt(lowerMultiplicityString);
				if (lowerMultiplicity < 0) 
				{
					multiStatus.add(ctx.createFailureStatus(Messages.multiplicity_lowerMultNegative));
				}
			} 
			catch (NumberFormatException nfe) {
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_lowerMultException,nfe.getMessage())));
			}
		}

		// validate upper multiplicity
		String upperMultiplicityString = gParamConfMultiplicity.gGetUpperMultiplicityAsString();
		if (upperMultiplicityString != null)
		{
			if ("*".equals(upperMultiplicityString))
			{
				multiStatus.add(ctx.createSuccessStatus());
			} 
			else
			{
				try
				{
					int upperMultiplicity = Integer.parseInt(upperMultiplicityString);
					if (upperMultiplicity < 0)
					{
						multiStatus.add(ctx.createFailureStatus(Messages.multiplicity_upperMultNegative));
					}
				} 
				catch (NumberFormatException nfe)
				{
					multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_upperMultException, nfe.getMessage())));
				}
			}
		}
		
		if(multiStatus.getChildren().length == 0)
		{
			return ctx.createSuccessStatus();
		}

		return multiStatus;
	}
}
