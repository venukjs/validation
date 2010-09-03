package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GFloatValue;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GFloatParamDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Superclass for the constraints implementations on a float value.
 * 
 */
public abstract class GFloatValueBasicConstraint extends
		AbstractGParameterValueConstraint
{

	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return ctx.getTarget() instanceof GFloatValue;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx)
	{
		GFloatValue gFloatValue = (GFloatValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, gFloatValue);
		if (status.isOK())
		{
			// the validation of the value requires valid access to the
			// IntegerParamDef
			status = validateValue(ctx, gFloatValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx,
			GParameterValue gParameterValue)
	{
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gParameterValue);
		if (status.isOK())
		{
			if (!(gParameterValue.gGetDefinition() instanceof GFloatParamDef))
			{
				status = ctx.createFailureStatus(NLS
						.bind(Messages.generic_definitionNotOfType,
								"float param def"));
			}
		}
		return status;
	}

	/**
	 * Performs the validation on the value of the given
	 * <code>gFloatValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gFloatValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */

	protected IStatus validateValue(IValidationContext ctx,
			GFloatValue gFloatValue)
	{
		IStatus status = validateValueSet(ctx, gFloatValue, gFloatValue.gGetValue());
		if(status.isOK())
		{
			return validateBoundary(ctx, gFloatValue);
		}
		return status;
	}

	/**
	 * Performs the validation on the boundaries of the definition of the given
	 * <code>gFloatValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gFloatValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected abstract IStatus validateBoundary(IValidationContext ctx,
			GFloatValue gFloatValue);
}
