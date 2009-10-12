/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Artop Software License 
 * Based on Released AUTOSAR Material (ASLR) which accompanies this 
 * distribution, and is available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.ecl.emf.validation.ui;

import org.artop.ecl.emf.validation.ui.internal.messages.Messages;

/**
 * 
 */
public interface IValidationMenuConstants {

	/**
	 * Identifier of the validation contextual menu.
	 * 
	 * @deprecated Use {@link #MENU_VALIDATION_ID} instead.
	 */
	@Deprecated
	public static final String VALIDATION_MENU_ID = "org.artop.ui.menu.validate"; //$NON-NLS-1$

	/**
	 * Label of the validation contextual menu.
	 * 
	 * @deprecated Use {@link #MENU_VALIDATION_LABEL} instead.
	 */
	@Deprecated
	public static final String VALIDATION_MENU_LABEL = Messages.menu_validation_label;

	/**
	 * Identifier of the Validation sub menu.
	 */
	public static final String MENU_VALIDATION_ID = "org.artop.ecl.emf.validation.ui.menus.validation"; //$NON-NLS-1$

	/**
	 * Label of the Validation sub menu.
	 */
	public static final String MENU_VALIDATION_LABEL = Messages.menu_validation_label;
}
