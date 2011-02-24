/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation
 * 
 * </copyright>
 */
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
			throw new RuntimeException("Internal error: Illegal constraint data"); //$NON-NLS-1$
		}
		return ecucValidationIndex;
	}

}
