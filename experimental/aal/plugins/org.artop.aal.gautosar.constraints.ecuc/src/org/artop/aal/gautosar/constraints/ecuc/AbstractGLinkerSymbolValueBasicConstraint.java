package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GLinkerSymbolValue;
import gautosar.gecucdescription.GecucdescriptionPackage;
import gautosar.gecucparameterdef.GLinkerSymbolDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;


/**
 * 
 * Abstract superclass for the constraints implementations on a linker symbol
 * value.
 * 
 */
public abstract class AbstractGLinkerSymbolValueBasicConstraint extends AbstractGStringValueBasicConstraint 
{
	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return GecucdescriptionPackage.eINSTANCE.getGLinkerSymbolValue().equals(ctx.getTarget().eClass());
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{
		GLinkerSymbolValue gLinkerSymbolValue = (GLinkerSymbolValue) ctx.getTarget();
		IStatus status = ctx.createSuccessStatus();
		
		status =  validateDefinition(ctx, gLinkerSymbolValue);
		if(status.isOK())
		{
			status = validateValue(ctx, gLinkerSymbolValue);
		}
		return status;
	}

	/**
	 * Performs the validation on the definition of the given
	 * <code>gLinkerSymbolValue</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gLinkerSymbolValue
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	private IStatus validateDefinition(IValidationContext ctx, GLinkerSymbolValue gLinkerSymbolValue) 
	{
		// check if definition is set and available
		IStatus status = super.validateDefinitionRef(ctx, gLinkerSymbolValue);
		if (status.isOK()) 
		{
			if (!(gLinkerSymbolValue.gGetDefinition() instanceof GLinkerSymbolDef)) 
			{
				status = ctx
						.createFailureStatus(NLS.bind(Messages.generic_definitionNotOfType,"linker symbol param def"));
			}
		}
		return status;
	}
}
