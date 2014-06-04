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
package org.artop.aal.gautosar.constraints.ecuc;

import org.artop.aal.validation.constraints.AbstractModelConstraintWithPrecondition;

/**
 * Customization of {@link AbstractModelConstraintWithPrecondition} that uses a cache for splitable ECUC configurations
 * and additionally takes into account splitable objects (see <code>isSplitAware()</code>).
 */
abstract public class AbstractSplitModelConstraintWithPreconditionAndIndex extends AbstractModelConstraintWithPreconditionAndIndex {

	@Override
	protected boolean isSplitAware() {
		return true;
	}

}
