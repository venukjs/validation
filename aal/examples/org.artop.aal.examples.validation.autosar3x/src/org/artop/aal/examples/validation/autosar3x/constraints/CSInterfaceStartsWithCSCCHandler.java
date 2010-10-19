package org.artop.aal.examples.validation.autosar3x.constraints;

import org.artop.ecl.emf.validation.constraint.ConstraintCallHandler;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.swcomponent.portinterface.ClientServerInterface;

public class CSInterfaceStartsWithCSCCHandler extends ConstraintCallHandler implements
		org.artop.aal.examples.validation.constraints.CSInterfaceStartsWithCSConstraint.ConstraintCallHandler {

	private static final String DIAG_IFC_PREFIX = "DidServices_"; //$NON-NLS-1$

	@Override
	protected IStatus validate(IValidationContext ctx) {
		EObject targetEObj = ctx.getTarget();
		if (targetEObj instanceof ClientServerInterface) {
			ClientServerInterface csIfc = (ClientServerInterface) targetEObj;
			if (csIfc.getShortName().startsWith(DIAG_IFC_PREFIX)) {
				return ctx.createSuccessStatus();
			}
		}
		return UNDECIDED;
	}

}
