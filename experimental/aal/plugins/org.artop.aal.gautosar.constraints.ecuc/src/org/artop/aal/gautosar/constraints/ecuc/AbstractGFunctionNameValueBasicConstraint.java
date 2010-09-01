package org.artop.aal.gautosar.constraints.ecuc;


import gautosar.gecucdescription.GFunctionNameValue;
import gautosar.gecucparameterdef.GFunctionNameDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;


/**
 * 
 * Abstract superclass for the constraints implementations on a function name
 * value (validates definition and value).
 * 
 */
public abstract class AbstractGFunctionNameValueBasicConstraint extends AbstractGLinkerSymbolValueBasicConstraint
{
	@Override
	protected boolean isApplicable(IValidationContext ctx) 
	{
		return ctx.getTarget() instanceof GFunctionNameValue;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{
		GFunctionNameValue gFunctionNameValue = (GFunctionNameValue) ctx.getTarget();
		IStatus status = ctx.createSuccessStatus();
		status = validateDefinition(ctx, gFunctionNameValue);
		if(status.isOK())
		{
			status =  validateValue(ctx, gFunctionNameValue);
		}
		return status;
	}

	/**
	 * Performs the validation on the definition of the given
	 * <code>gFunctionNameValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gFunctionNameValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateDefinition(IValidationContext ctx, GFunctionNameValue gFunctionNameValue) 
	{
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gFunctionNameValue);
		if (status.isOK()) {
			if (!(gFunctionNameValue.gGetDefinition() instanceof GFunctionNameDef))
			{
				status = ctx
						.createFailureStatus(NLS.bind(Messages.generic_definitionNotOfType, "function name param def"));
			}
		}
		return status;
	}
}
