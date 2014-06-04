/**
 * <copyright>
 * 
 * Copyright (c) Continental AG and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Continental AG - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.validation.constraints;


/**
 * Abstract superclass for the constraint implementations that check before validation that a precondition is fulfilled
 * and additionally takes into account splitable objects (see <code>isSplitAware()</code>).
 */
public abstract class AbstractSplitModelConstraintWithPrecondition extends AbstractModelConstraintWithPrecondition {

	@Override
	protected boolean isSplitAware() {
		return true;
	}
}
