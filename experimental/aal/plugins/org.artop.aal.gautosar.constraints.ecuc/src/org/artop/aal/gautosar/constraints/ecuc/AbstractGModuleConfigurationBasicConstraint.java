package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GModuleDef;

import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

/**
 * 
 * Abstract superclass for the constraints implementations on a module
 * configuration.
 * 
 */
public abstract class AbstractGModuleConfigurationBasicConstraint extends
		AbstractModelConstraintWithPrecondition
{

	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		return ctx.getTarget() instanceof GModuleConfiguration;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{
		assert ctx.getTarget() instanceof GModuleConfiguration;

		GModuleConfiguration gModuleConfiguration = (GModuleConfiguration) ctx
				.getTarget();
		IStatus status = validateDefinitionRef(ctx, gModuleConfiguration);
		if (status.isOK())
		{
			status = validateImplementationConfigVariant(ctx,
					gModuleConfiguration);
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
	protected IStatus validateDefinitionRef(IValidationContext ctx,
			GModuleConfiguration gModuleConfiguration)
	{
		// check if definition is set and available
		final IStatus status;

		GModuleDef gModuleDef = gModuleConfiguration.gGetDefinition();
		if (null == gModuleDef)
		{
			status = ctx
					.createFailureStatus(Messages.generic_definitionReferenceNotSet);
		} else if (gModuleDef.eIsProxy())
		{
			status = ctx
					.createFailureStatus(Messages.generic_definitionReferenceNotResolved);
		} else
		{
			status = ctx.createSuccessStatus();
		}
		return status;
	}

	/**
	 * Performs the validation on the <code>implementationConfigVariant</code> of the given
	 * <code>gModuleConfiguration</code>.
	 * 
	 * @param ctx
	 *            the validation context that provides access to the current
	 *            constraint evaluation environment
	 * @param gModuleConfiguration
	 *            the element on which the validation is performed.
	 * @return a status object describing the result of the validation.
	 */
	protected abstract IStatus validateImplementationConfigVariant(
			IValidationContext ctx, GModuleConfiguration gModuleConfiguration);
}
