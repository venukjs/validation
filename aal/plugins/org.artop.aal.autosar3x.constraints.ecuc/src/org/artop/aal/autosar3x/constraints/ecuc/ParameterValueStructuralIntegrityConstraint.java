package org.artop.aal.autosar3x.constraints.ecuc;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucdescription.Container;
import autosar3x.ecucdescription.ParameterValue;
import autosar3x.ecucparameterdef.ChoiceContainerDef;
import autosar3x.ecucparameterdef.ConfigParameter;
import autosar3x.ecucparameterdef.ContainerDef;
import autosar3x.ecucparameterdef.ParamConfContainerDef;

public class ParameterValueStructuralIntegrityConstraint extends AbstractModelConstraint {
	@Override
	public IStatus validate(IValidationContext ctx) {
		assert ctx.getTarget() instanceof ParameterValue;

		final IStatus status;
		ParameterValue parameterValue = (ParameterValue) ctx.getTarget();

		EObject parent = parameterValue.eContainer();

		if (null == parent) {
			status = ctx.createFailureStatus("element has no parent");
		} else {
			assert parent instanceof Container;
			Container parentContainer = (Container) parent;
			status = validateStructuralIntegrity(ctx, parameterValue, parentContainer);
		}
		return status;

	}

	private IStatus validateStructuralIntegrity(IValidationContext ctx, ParameterValue parameterValue, Container parentContainer) {
		final IStatus status;

		ContainerDef parentContainerDef = parentContainer.getDefinition();
		ConfigParameter configParameter = parameterValue.getDefinition();

		if (null == configParameter || configParameter.eIsProxy() || null == parentContainerDef || parentContainerDef.eIsProxy()) {
			// error in the definitions are REPORTED in other constraints
			status = ctx.createSuccessStatus();
		} else if (parentContainerDef instanceof ChoiceContainerDef) {
			// TODO: create testcase
			status = ctx.createFailureStatus("ReferenceValue not allowed in choice containers");
		} else if (parentContainerDef instanceof ParamConfContainerDef) {
			// the parent containers definition is a ParamConfContainerDef
			ParamConfContainerDef parentParamConfContainerDef = (ParamConfContainerDef) parentContainerDef;
			if (EcucUtil.getAllParametersOf(parentParamConfContainerDef).contains(configParameter)) {
				status = ctx.createSuccessStatus(); // reference is valid
			} else {
				status = ctx.createFailureStatus("containement problem: parameter with definition " + configParameter.getShortName()
						+ " not allowed here");
			}

		} else {
			// in the current metamodel we only find expect ParamConfContainerDef and ChoiceContainerDef
			// The assert will warn in case of metamodel extensions
			assert false;
			status = ctx.createSuccessStatus();
		}
		return status;

	}

}
