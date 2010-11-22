package org.artop.aal.gautosar.constraints.ecuc;

import org.artop.aal.gautosar.constraints.ecuc.internal.index.EcucValidationIndex;
import org.eclipse.emf.validation.IValidationContext;

abstract public class AbstractModelConstraintWithPreconditionAndIndex extends AbstractModelConstraintWithPrecondition {

	public EcucValidationIndex getEcucValidationIndex(IValidationContext ctx) {
		Object currentConstraintData = ctx.getCurrentConstraintData();
		EcucValidationIndex ecucValidationIndex;
		if (null == currentConstraintData) {
			// first invocation. create cache.
			ecucValidationIndex = new EcucValidationIndex();
			ctx.putCurrentConstraintData(ecucValidationIndex);
		} else if (currentConstraintData instanceof EcucValidationIndex) {
			ecucValidationIndex = (EcucValidationIndex) currentConstraintData;
		} else {
			// cache not properly used constraint data of type EcucValidationCache expected
			throw new RuntimeException("Internal error: Illegal constraint data");
		}
		return ecucValidationIndex;
	}

}
