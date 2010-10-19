package org.artop.aal.examples.validation.autosar3x.constraints;

import org.artop.ecl.emf.validation.constraint.ConstraintCallHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.swcomponent.portinterface.SenderReceiverInterface;

public class ModeSwitchStartsWithMSCCHandler extends ConstraintCallHandler implements
		org.artop.aal.examples.validation.constraints.ModeSwitchInterfaceStartsWithMSConstraint.ConstraintCallHandler {

	private static final String MS_PREFIX = "MS"; //$NON-NLS-1$

	@Override
	protected IStatus validate(IValidationContext ctx) {
		EObject targetEObj = ctx.getTarget();
		if (targetEObj instanceof SenderReceiverInterface) {
			SenderReceiverInterface srIfc = (SenderReceiverInterface) targetEObj;
			if (isModeSwitchInterface(srIfc) && srIfc.getShortName().startsWith(MS_PREFIX)) {
				return ctx.createFailureStatus();
			}
		}
		return UNDECIDED;
	}

	private boolean isModeSwitchInterface(SenderReceiverInterface srIfc) {
		return !srIfc.getModeGroups().isEmpty();
	}

}
