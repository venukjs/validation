package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GEnumerationValue;
import gautosar.gecucdescription.GParameterValue;
import gautosar.gecucparameterdef.GEnumerationLiteralDef;
import gautosar.gecucparameterdef.GEnumerationParamDef;

import java.util.List;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;


/**
 * 
 * Abstract superclass for the constraints implementations on an enumeration value.
 * value.
 * 
 */
public abstract class AbstractGEnumerationValueBasicConstraint extends AbstractGParameterValueConstraint {

	@Override
	protected boolean isApplicable(IValidationContext ctx) 
	{
		return ctx.getTarget() instanceof GEnumerationValue;
	}

	@Override
	protected IStatus doValidate(IValidationContext ctx) 
	{
		assert ctx.getTarget() instanceof GEnumerationValue;

		GEnumerationValue gEnumerationValue = (GEnumerationValue) ctx.getTarget();
		IStatus status = validateDefinitionRef(ctx, gEnumerationValue);
		if (status.isOK()) 
		{
			// the validation of the value requires valid access to the IntegerParamDef
			status = validateValue(ctx, gEnumerationValue);
		}

		return status;
	}

	@Override
	protected IStatus validateDefinitionRef(IValidationContext ctx, GParameterValue gParameterValue)
	{
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gParameterValue);
		if (status.isOK()) 
		{
			if (!(gParameterValue.gGetDefinition() instanceof GEnumerationParamDef))
			{
				status = ctx.createFailureStatus(NLS.bind(Messages.generic_definitionNotOfType,"enumeration param def"));
			}
		}
		return status;
	}

	/**
	 * Performs the validation on the value of the given
	 * <code>gEnumerationValue</code>. It checks if the value is set and if the value found is among the literals from the definition.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gEnumerationValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected IStatus validateValue(IValidationContext ctx, GEnumerationValue gEnumerationValue)
	{		
		
		GEnumerationParamDef gEnumerationParamDef = (GEnumerationParamDef) gEnumerationValue.gGetDefinition();
		String value = gEnumerationValue.gGetValue();
		
		IStatus status = validateValueSet(ctx, gEnumerationValue,value);
		if(!status.isOK())
		{
			return status;
		}

		if (0 == value.length()) 
		{
			return ctx.createFailureStatus(Messages.generic_emptyValue);
		}
		else
		{
			List<GEnumerationLiteralDef> literalList = gEnumerationParamDef.gGetLiterals();
			boolean valueFound = false;

			for (int i = 0; i < literalList.size(); i++)
			{
				if (literalList.get(i).gGetShortName().equals(value))
				{
					valueFound = true;
					break; // leave the for loop
				}
			}

			if (false == valueFound)
			{
				return ctx.createFailureStatus(Messages.enumeration_valueNotInLiterals);
			}
		}
	
		return ctx.createSuccessStatus();
	}
}