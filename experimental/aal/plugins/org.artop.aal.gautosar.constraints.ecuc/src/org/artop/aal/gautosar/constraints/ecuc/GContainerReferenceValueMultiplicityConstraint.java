package org.artop.aal.gautosar.constraints.ecuc;

import gautosar.gecucdescription.GConfigReferenceValue;
import gautosar.gecucdescription.GContainer;
import gautosar.gecucparameterdef.GConfigReference;
import gautosar.gecucparameterdef.GContainerDef;
import gautosar.gecucparameterdef.GParamConfContainerDef;

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
 * Superclass for the constraints implementations on the multiplicity of reference values of a container.
 * 
 */
public class GContainerReferenceValueMultiplicityConstraint extends AbstractModelConstraintWithPrecondition
{
	@Override
	protected boolean isApplicable(IValidationContext ctx)
	{
		boolean isApplicable = false;
		if (ctx.getTarget() instanceof GContainer)
		{
			GContainer gContainer = (GContainer) ctx.getTarget();
			GContainerDef gContainerDef = gContainer.gGetDefinition();
			isApplicable = null != gContainerDef && false == gContainerDef.eIsProxy() && gContainerDef instanceof GParamConfContainerDef;
		}
		return isApplicable;
	}

	@Override
	public IStatus doValidate(IValidationContext ctx)
	{

		GContainer gContainer = (GContainer) ctx.getTarget();
		GParamConfContainerDef gParamConfContainerDef = (GParamConfContainerDef) gContainer.gGetDefinition();

		MultiStatus multiStatus = new MultiStatus(Activator.PLUGIN_ID, 0, this.getClass().getName(), null);

		List<GConfigReferenceValue> allGConfigReferenceValues = EcucUtil.getAllReferenceValuesOf(gContainer);
		List<GConfigReference> gConfigReferences = gParamConfContainerDef.gGetReferences();
		for (GConfigReference currentGConfigReference : gConfigReferences) 
		{
			int numberOfConfigReferenceValues = EcucUtil.filterConfigReferenceValuesByDefinition(allGConfigReferenceValues, currentGConfigReference)
					.size();
			
			if (!EcucUtil.isValidLowerMultiplicity(numberOfConfigReferenceValues, currentGConfigReference))
			{
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_minElementsExpected, new Object[]{EcucUtil.getLowerMultiplicity(currentGConfigReference),"config reference values",AutosarURIFactory.getAbsoluteQualifiedName(currentGConfigReference),numberOfConfigReferenceValues})
));
			}
			if (!EcucUtil.isValidUpperMultiplicity(numberOfConfigReferenceValues, currentGConfigReference)) 
			{
				multiStatus.add(ctx.createFailureStatus(NLS.bind(Messages.multiplicity_maxElementsExpected, new Object[]{EcucUtil.getUpperMultiplicity(currentGConfigReference),"config reference values",AutosarURIFactory.getAbsoluteQualifiedName(currentGConfigReference),numberOfConfigReferenceValues})
));
			}
		}
		return multiStatus;
	}

}
