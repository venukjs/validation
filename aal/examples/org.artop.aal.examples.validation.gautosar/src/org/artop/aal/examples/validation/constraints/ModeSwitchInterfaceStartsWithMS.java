package org.artop.aal.examples.validation.constraints;

import org.artop.ecl.emf.validation.constraint.ChainableConstraint;
import org.artop.ecl.emf.validation.constraint.IConstraintCallHandler;

// EXAMPLE:
// A GAutosar-bound Constraint which only consists of release-specific implementations.
//
// The Constraint shall validate that the ShortNames of all ModeSwitchInterfaces start with "MS". At the time
// when this Constraint was implemented no GAUtosar abstraction for the ModeSwitch part of the AUTOSAR meta-model
// existed. The Constraint therefore will be implemented by ConstraintCallHandlers which provide release-specific
// implementations.
// This Constraint actually only serves as an enty point for the release-specific ConstraintCallHandlers into the 
// EMF Validation Framework.
public class ModeSwitchInterfaceStartsWithMS extends ChainableConstraint {

	// The Constraint is intended to be extended by ConstraintCallHandlers therefore it overrides this method.
	@Override
	protected Class<? extends IConstraintCallHandler> getSupportedHandlerClass() {
		return ConstraintCallHandler.class;
	}

	private static interface ConstraintCallHandler extends IConstraintCallHandler {
	}

}
