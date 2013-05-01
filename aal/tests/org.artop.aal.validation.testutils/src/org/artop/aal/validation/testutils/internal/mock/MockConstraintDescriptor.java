/**
 * <copyright>
 * 
 * Copyright (c) BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     BMW Car IT - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.validation.testutils.internal.mock;

import java.util.Set;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.model.Category;
import org.eclipse.emf.validation.model.ConstraintSeverity;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * A metamodel descriptor mock which can used as a descriptor for an arbitrary metamodel.
 */
public class MockConstraintDescriptor implements IConstraintDescriptor {

	private String fId;
	private String fPluginId;

	public MockConstraintDescriptor(String id, String pluginId) {
		fId = id;
		fPluginId = pluginId;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getId() {
		return fId;
	}

	public String getPluginId() {
		return fPluginId;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public ConstraintSeverity getSeverity() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStatusCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public EvaluationMode<?> getEvaluationMode() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean targetsTypeOf(EObject eObject) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean targetsEvent(Notification notification) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isBatch() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isLive() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isError() {
		// TODO Auto-generated method stub
		return false;
	}

	public Throwable getException() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub

	}

	public void setError(Throwable exception) {
		// TODO Auto-generated method stub

	}

	public Set<Category> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addCategory(Category category) {
		// TODO Auto-generated method stub

	}

	public void removeCategory(Category category) {
		// TODO Auto-generated method stub

	}

	public String getMessagePattern() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getBody() {
		// TODO Auto-generated method stub
		return null;
	}

}
