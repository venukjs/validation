/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on Released
 * AUTOSAR Material (ASLR) which accompanies this distribution, and is available
 * at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.emf.validation.markers;

import java.util.EventObject;

public class MarkerChangedEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3415310768653818533L;

	public MarkerChangedEvent(Object source) {
		super(source);
	}

}
