package org.artop.aal.autosar40.constraints.ecuc;

import gautosar.gecucparameterdef.GConfigParameter;

import org.artop.aal.autosar40.constraints.ecuc.util.EcucUtil40;
import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.GConfigParameterDefaultValueConstraint;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.util.EcucUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

public class EcucConfigParameterDefaultValueConstraint extends
		GConfigParameterDefaultValueConstraint {
	
	
	@Override
	protected IStatus doValidate(IValidationContext ctx) {
		GConfigParameter cfParam = (GConfigParameter) ctx.getTarget();

		/*
		 * The corresponding Configuration Parameter from the Refined Module Definition
		 */
		GConfigParameter configParamInRefinedModuleDef = EcucUtil.getConfigParameterInRefinedModuleDef(cfParam);

		/* If Configuration Parameter can not be found in Refined Module Definition, just return. */
		if (configParamInRefinedModuleDef == null) {
			return ctx.createSuccessStatus();
		}

		/* Flag used to mark the default value as modified or not. */
		boolean valid = true;

		/*
		 * Default value of the Configuration Parameter in the Refined Module Definition.
		 */
		// FIXME default value of ConfigParameter in AR4.0 is something special, need to be reworked
		String defaultValueInRefinedModuleDef = EcucUtil40.getDefaultValue(configParamInRefinedModuleDef); //$NON-NLS-1$

		if (defaultValueInRefinedModuleDef != null) {
			/*
			 * Default value of the Configuration Parameter in the Vendor Specific Module Definition.
			 */
			String defaultValue = EcucUtil40.getDefaultValue(cfParam); //$NON-NLS-1$

			/*
			 * A warning is raised if default value has been modified in the Vendor Specific ModuleDef.
			 */
			valid = defaultValue != null ? defaultValue.equals(defaultValueInRefinedModuleDef) : defaultValueInRefinedModuleDef == null;
		}

		if (!valid) {
			return ctx.createFailureStatus(NLS.bind(
					EcucConstraintMessages.configParameter_defaultValueChanged,
					new Object[] { getConfigParameterType(cfParam), AutosarURIFactory.getAbsoluteQualifiedName(cfParam),
							AutosarURIFactory.getAbsoluteQualifiedName(EcucUtil.getParentModuleDef(configParamInRefinedModuleDef)) }));
		}

		return ctx.createSuccessStatus();
	}
	
	
}
