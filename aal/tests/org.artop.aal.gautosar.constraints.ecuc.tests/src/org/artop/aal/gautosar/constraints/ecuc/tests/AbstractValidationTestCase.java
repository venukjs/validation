/**
 * <copyright>
 * 
 * Copyright (c) OpenSynergy, Continental Engineering Services and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     OpenSynergy - Initial API and implementation for AUTOSAR 3.x
 *     Continental Engineering Services - migration to gautosar
 * 
 * </copyright>
 */
package org.artop.aal.gautosar.constraints.ecuc.tests;

import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;

import java.io.File;
import java.util.Map;

import junit.framework.TestCase;

import org.artop.aal.common.resource.impl.AutosarResourceSetImpl;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.eclipse.sphinx.testutils.TestFileAccessor;

/**
 * Abstract class corresponding to a test case that validates the ecuc validation constraints.
 */
public abstract class AbstractValidationTestCase extends TestCase {

	protected IValidator<EObject> validator;
	protected TestFileAccessor fFileAccessor;

	public AbstractValidationTestCase() {
		fFileAccessor = new TestFileAccessor(getPlugin(), new File("working-dir"));

		validator = ModelValidationService.getInstance().newValidator(EvaluationMode.BATCH);
		validator.setReportSuccesses(true);

		// only this constraint is going to be evaluated.
		validator.addConstraintFilter(new IConstraintFilter() {
			public boolean accept(IConstraintDescriptor constraint, EObject target) {
				return constraint.getId().endsWith(getConstraintID());
			}

		});
	}

	/**
	 * Returns the plugin instance, useful for locating resources.
	 * 
	 * @return the plugin instance
	 */
	protected abstract Plugin getPlugin();

	/**
	 * Gets the constraint id.
	 * 
	 * @return the constraint id
	 */
	abstract protected String getConstraintID();

	/**
	 * Returns the metamodel-specific namespace URI corresponding to the the autosar package.
	 * 
	 * @return
	 */
	protected abstract String getAutosarPackageNsURi();

	/**
	 * Returns the instance of the autosar package.
	 * 
	 * @return
	 */
	protected abstract EPackage getAutosarPackageInstance();

	/**
	 * Returns the instance of the autosar resource factory.
	 * 
	 * @return
	 */
	protected abstract Resource.Factory getAutosarResourceFactory();

	/**
	 * Loads the content of the given file.
	 * 
	 * @param fileName
	 *            the bundle-related path of the file to load.
	 * @return the file's root object.
	 * @throws Exception
	 *             if the loading failed.
	 */
	protected GAUTOSAR loadInputFile(String fileName) throws Exception {
		return loadInputFile(fileName, null);
	}

	/**
	 * Loads the content of the given file.
	 * 
	 * @param fileName
	 *            the bundle-related path of the file to load.
	 * @return the file's root object.
	 * @throws Exception
	 *             if the loading failed.
	 */
	protected GAUTOSAR loadInputFile(String fileName, Map<?, ?> options) throws Exception {
		ResourceSet resourceSet = new AutosarResourceSetImpl();

		// Register the package
		resourceSet.getPackageRegistry().put(getAutosarPackageNsURi(), getAutosarPackageInstance());

		java.net.URI uri = fFileAccessor.getInputFileURI(fileName);
		URI fileURI = fFileAccessor.convertToEMFURI(uri);
		XMLResource resource = (XMLResource) getAutosarResourceFactory().createResource(fileURI);

		resource.load(options);
		resourceSet.getResources().add(resource);

		return (GAUTOSAR) resource.getContents().get(0);
	}
}
