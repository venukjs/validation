package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GContainer;
import gautosar.gecucdescription.GModuleConfiguration;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GModuleDef;

import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.gautosar.constraints.ecuc.internal.Activator;
import org.artop.aal.gautosar.constraints.ecuc.util.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.osgi.util.NLS;

/**
 * 
 * Superclass for the constraints implementations on the multiplicity of subcontainers of a module configuration.
 * 
 */
public class GModuleConfigurationSubContainerMultiplicityConstraint extends AbstractModelConstraintWithPrecondition 
{
	@Override
	protected boolean isApplicable(IValidationContext ctx) 
	{
		boolean isApplicable = false;
		if (ctx.getTarget() instanceof GModuleConfiguration)
		{
			GModuleConfiguration gModuleConfiguration = (GModuleConfiguration) ctx.getTarget();
			GModuleDef gModuleDef = gModuleConfiguration.gGetDefinition();
			isApplicable = null != gModuleDef && false == gModuleDef.eIsProxy();
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx) {
		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		GModuleConfiguration gModuleConfiguration = (GModuleConfiguration) ctx.getTarget();
		GModuleDef gModuleDef = gModuleConfiguration.gGetDefinition();

		List<GContainer> allSubContainers = EcucUtil.getAllContainersOf(gModuleConfiguration);
		List<GContainerDef> subGContainerDefs = gModuleDef.gGetContainers();

		for (GContainerDef currentSubGContainerDef : subGContainerDefs)
		{
			int numberOfSubContainers = EcucUtil.getNumberOfUniqueContainersByDefinition(allSubContainers, currentSubGContainerDef);
			if (!EcucUtil.isValidLowerMultiplicity(numberOfSubContainers, currentSubGContainerDef))
			{
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_minElementsExpected, new Object[]{EcucUtil.getLowerMultiplicity(currentSubGContainerDef),"subcontainers",AutosarURIFactory.getAbsoluteQualifiedName(currentSubGContainerDef),numberOfSubContainers}))
);
			}
			else if (!EcucUtil.isValidUpperMultiplicity(numberOfSubContainers, currentSubGContainerDef))
			{
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_maxElementsExpected, new Object[]{EcucUtil.getUpperMultiplicity(currentSubGContainerDef),"subcontainers",AutosarURIFactory.getAbsoluteQualifiedName(currentSubGContainerDef),numberOfSubContainers}))
);
			}
			else
			{
				multiStatus.add(ctx.createSuccessStatus());
			}
		}

		return multiStatus;
	}
}
