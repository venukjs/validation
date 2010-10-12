/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.emf.validation.constraint;

import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * This interface provide an extension of the {@link IConstraintDescriptor} one in order to access simply to features
 * whether the constraint is not described through an xml extension point contribution.
 */
public interface IExtendedConstraintDescriptor extends IConstraintDescriptor {

	/**
	 * @return target features
	 */
	public String[] getFeatures();

}
