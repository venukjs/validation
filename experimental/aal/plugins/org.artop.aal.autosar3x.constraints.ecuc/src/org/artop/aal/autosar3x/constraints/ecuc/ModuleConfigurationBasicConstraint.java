package org.artop.aal.autosar3x.constraints.ecuc;

import gautosar.gecucdescription.GModuleConfiguration;

import java.util.List;

import org.artop.aal.autosar3x.constraints.ecuc.internal.Messages;
import org.artop.aal.gautosar.constraints.ecuc.AbstractGModuleConfigurationBasicConstraint;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.ModuleConfiguration;
import autosar3x.ecucparameterdef.ConfigurationVariant;
import autosar3x.ecucparameterdef.ModuleDef;

public class ModuleConfigurationBasicConstraint extends AbstractGModuleConfigurationBasicConstraint {

	@Override
	protected IStatus validateImplementationConfigVariant(IValidationContext ctx, GModuleConfiguration gModuleConfiguration) {

		final IStatus status;
		ModuleConfiguration moduleConfiguration = (ModuleConfiguration) gModuleConfiguration;

		if (false == moduleConfiguration.isSetImplementationConfigVariant()) {
			status = ctx.createFailureStatus(Messages.moduleConfig_ImplConfigVariantNotSet);

		} else {
			ConfigurationVariant configVariant = moduleConfiguration.getImplementationConfigVariant();
			ModuleDef moduleDef = moduleConfiguration.getDefinition();
			List<ConfigurationVariant> supportedConfigVariants = moduleDef.getSupportedConfigVariants();
			if (supportedConfigVariants.contains(configVariant)) {
				status = ctx.createSuccessStatus();
			} else {
				status = ctx.createFailureStatus(Messages.moduleConfig_ImplConfigVariantNotSupported);
			}
		}
		return status;
	}
}
