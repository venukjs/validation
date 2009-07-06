/***********************************************************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others. All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html Contributors: IBM Corporation - initial API and implementation
 **********************************************************************************************************************/

package org.artop.ecl.emf.validation.ui.views;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.swt.graphics.Image;

/**
 * Folder field. Designed to display and compare the names of the eObject that contain IMarker objects.
 */
public class FieldEObject extends AbstractField {

	/**
	 * Create a new instance of the receiver.
	 */
	public FieldEObject() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#getDescription()
	 */
	public String getDescription() {
		return MarkerMessages.description_eObject;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#getDescriptionImage()
	 */
	public Image getDescriptionImage() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#getColumnHeaderText()
	 */
	public String getColumnHeaderText() {
		return MarkerMessages.description_eObject;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#getColumnHeaderImage()
	 */
	public Image getColumnHeaderImage() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#getValue(java.lang.Object)
	 */
	public String getValue(Object obj) {
		if (obj == null || !(obj instanceof ConcreteMarker)) {
			return Util.EMPTY_STRING;
		}
		ConcreteMarker marker = (ConcreteMarker) obj;
		return marker.getEObjectID();
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#getImage(java.lang.Object)
	 */
	public Image getImage(Object obj) {

		if (obj == null || !(obj instanceof ConcreteMarker)) {
			return null;
		}
		ConcreteMarker marker = (ConcreteMarker) obj;

		EObject eo = null;

		// TODO Replace all this by use of Meta-Model Descriptors.

		// FIXME NBD remove these ugly algo and hard-coded strings
		EPackage autosarPackage = EPackage.Registry.INSTANCE.getEPackage("http://autosar.org/2.1.4"); //$NON-NLS-1$
		if (autosarPackage != null) {
			EClassifier eClassifier = autosarPackage.getEClassifier(marker.getEObjectType());
			EClass eClass = null;
			if (eClassifier != null && eClassifier instanceof EClass) {
				eClass = (EClass) eClassifier;
				eo = autosarPackage.getEFactoryInstance().create(eClass);
			}
		}
		if (eo == null) {
			autosarPackage = EPackage.Registry.INSTANCE.getEPackage("http://autosar.org/3.1.1"); //$NON-NLS-1$
			if (autosarPackage != null) {
				EClassifier eClassifier = autosarPackage.getEClassifier(marker.getEObjectType());
				EClass eClass = null;
				if (eClassifier != null && eClassifier instanceof EClass) {
					eClass = (EClass) eClassifier;
					eo = autosarPackage.getEFactoryInstance().create(eClass);
				}
			}
		}
		if (eo == null) {
			autosarPackage = EPackage.Registry.INSTANCE.getEPackage("http://autosar.org/4.0.0"); //$NON-NLS-1$
			if (autosarPackage != null) {
				EClassifier eClassifier = autosarPackage.getEClassifier(marker.getEObjectType());
				EClass eClass = null;
				if (eClassifier != null && eClassifier instanceof EClass) {
					eClass = (EClass) eClassifier;
					eo = autosarPackage.getEFactoryInstance().create(eClass);
				}
			}
		}
		if (eo == null) {
			autosarPackage = EPackage.Registry.INSTANCE.getEPackage("http://autosar.org/2.0.0"); //$NON-NLS-1$
			if (autosarPackage != null) {
				EClassifier eClassifier = autosarPackage.getEClassifier(marker.getEObjectType());
				EClass eClass = null;
				if (eClassifier != null && eClassifier instanceof EClass) {
					eClass = (EClass) eClassifier;
					eo = autosarPackage.getEFactoryInstance().create(eClass);
				}
			}
		}

		// EObject eo = MarkerUtil.getConnectedEObjectFromMarker(marker.getMarker());

		Object o = null;
		if (eo != null) {
			ComposedAdapterFactory factory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
			IItemLabelProvider ilp = (IItemLabelProvider) factory.adapt(eo, IItemLabelProvider.class);
			o = ilp.getImage(eo);
		}
		Image img = ExtendedImageRegistry.getInstance().getImage(o);

		return img;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Object obj1, Object obj2) {
		if (obj1 == null || obj2 == null || !(obj1 instanceof ConcreteMarker) || !(obj2 instanceof ConcreteMarker)) {
			return 0;
		}
		ConcreteMarker marker1 = (ConcreteMarker) obj1;
		ConcreteMarker marker2 = (ConcreteMarker) obj2;

		// construct full address of targetObject
		String targetObj1 = marker1.getFolder() + "/" + marker1.getResourceName() + "/" + marker1.getEObjectID(); //$NON-NLS-1$ //$NON-NLS-2$
		String targetObj2 = marker2.getFolder() + "/" + marker2.getResourceName() + "/" + marker2.getEObjectID(); //$NON-NLS-1$//$NON-NLS-2$
		return targetObj1.compareTo(targetObj2);
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#getDefaultDirection()
	 */
	public int getDefaultDirection() {
		return TableComparator.ASCENDING;
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.views.markers.internal.IField#getPreferredWidth()
	 */
	public int getPreferredWidth() {
		return 150;
	}

}
