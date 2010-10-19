package org.artop.aal.examples.validation.constraints;

import gautosar.gswcomponents.gdatatype.gdataprototypes.GAutosarDataPrototype;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

/*
 * EXAMPLE:
 * A GAutosar-based Constraint.
 * 
 * The Constraint validates that the Type is set for all DataPrototypes. The Constraint's implementation 
 * is based on the GAutosar API making it executable against all AUTOSAR models no matter of which release 
 * they are.  
 * The Constraint is not intended to be extended by ConstraintCallHandlers and therefore is not derived 
 * from ChainableConstraint.  
 */
public class DataPrototypeHasTypeConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		GAutosarDataPrototype prototype = (GAutosarDataPrototype) ctx.getTarget();
		if (prototype.gGetType() == null) {
			return ctx.createFailureStatus();
		}
		return ctx.createSuccessStatus();
	}

}
