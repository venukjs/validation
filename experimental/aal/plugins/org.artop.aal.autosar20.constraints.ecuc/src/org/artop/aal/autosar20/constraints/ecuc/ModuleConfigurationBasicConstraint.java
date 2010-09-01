package org.artop.aal.autosar20.constraints.ecuc;


import gautosar.gecucdescription.GModuleConfiguration;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGModuleConfigurationBasicConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

public class ModuleConfigurationBasicConstraint extends AbstractGModuleConfigurationBasicConstraint
{

	@Override
	protected IStatus validateImplementationConfigVariant
	(
			IValidationContext ctx,
			GModuleConfiguration gModuleConfiguration)
	{
		return ctx.createSuccessStatus();
	}
	

}
