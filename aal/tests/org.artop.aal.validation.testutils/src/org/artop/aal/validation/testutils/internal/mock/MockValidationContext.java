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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

/**
 * A validation context mock which can be used to be passed to a <code>AbstractModelConstraint</code> when testing it.
 */
public class MockValidationContext implements IValidationContext {

	private String fConstraintDescriptorId;

	public MockValidationContext(String constraintDescriptorId) {
		fConstraintDescriptorId = constraintDescriptorId;
	}

	public String getCurrentConstraintId() {
		return fConstraintDescriptorId;
	}

	public EObject getTarget() {
		return null;
	}

	public EMFEventType getEventType() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Notification> getAllEvents() {
		// TODO Auto-generated method stub
		return null;
	}

	public EStructuralFeature getFeature() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getFeatureNewValue() {
		// TODO Auto-generated method stub
		return null;
	}

	public void skipCurrentConstraintFor(EObject eObject) {
		// TODO Auto-generated method stub

	}

	public void skipCurrentConstraintForAll(Collection<?> eObjects) {
		// TODO Auto-generated method stub

	}

	public void disableCurrentConstraint(Throwable exception) {
		// TODO Auto-generated method stub

	}

	public Object getCurrentConstraintData() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object putCurrentConstraintData(Object newData) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<EObject> getResultLocus() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addResult(EObject eObject) {
		// TODO Auto-generated method stub

	}

	public void addResults(Collection<? extends EObject> eObjects) {
		// TODO Auto-generated method stub

	}

	public IStatus createSuccessStatus() {
		return Status.OK_STATUS;
	}

	@SuppressWarnings("nls")
	public IStatus createFailureStatus(Object... messageArgument) {
		Assert.assertTrue("No message argument was provided.", messageArgument.length > 0);
		Assert.assertEquals("More than one message argument was provided.", 1, messageArgument.length);
		return new FailureStatus((String) messageArgument[0]);
	}

	public static class FailureStatus implements IStatus {

		private String fMessage;

		public FailureStatus(String message) {
			fMessage = message;
		}

		public IStatus[] getChildren() {
			// TODO Auto-generated method stub
			return null;
		}

		public int getCode() {
			// TODO Auto-generated method stub
			return 0;
		}

		public Throwable getException() {
			// TODO Auto-generated method stub
			return null;
		}

		public String getMessage() {
			return fMessage;
		}

		public String getPlugin() {
			// TODO Auto-generated method stub
			return null;
		}

		public int getSeverity() {
			// TODO Auto-generated method stub
			return 0;
		}

		public boolean isMultiStatus() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isOK() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean matches(int severityMask) {
			// TODO Auto-generated method stub
			return false;
		}

	}

}
