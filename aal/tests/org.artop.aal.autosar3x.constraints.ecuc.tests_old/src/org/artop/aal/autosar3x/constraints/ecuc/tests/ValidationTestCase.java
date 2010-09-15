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
package org.artop.aal.autosar3x.constraints.ecuc.tests;

import junit.framework.TestCase;

import org.artop.aal.autosar3x.constraints.ecuc.tests.internal.Activator;
import org.artop.aal.common.resource.impl.AutosarResourceSetImpl;
import org.artop.ecl.testutils.TestFileAccessor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IConstraintFilter;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

import autosar3x.genericstructure.infrastructure.autosar.AUTOSAR;
import autosar3x.util.Autosar3xPackage;
import autosar3x.util.Autosar3xResourceFactoryImpl;

/**
 * Abstract test class for all validation test cases. The EMF Validator is created here.
 */
public abstract class ValidationTestCase extends TestCase {

	protected IValidator<EObject> validator;
	protected TestFileAccessor fFileAccessor;

	public ValidationTestCase() {
		super();
		fFileAccessor = new TestFileAccessor(Activator.getPlugin());

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
	 * Gets the constraint id.
	 * 
	 * @return the constraint id
	 */
	abstract protected String getConstraintID();

	protected AUTOSAR loadInputFile(String fileName) throws Exception {
		ResourceSet resourceSet = new AutosarResourceSetImpl();

		// Register the package
		resourceSet.getPackageRegistry().put(Autosar3xPackage.eINSTANCE.getNsURI(), Autosar3xPackage.eINSTANCE);

		java.net.URI uri = fFileAccessor.getInputFileURI(fileName);
		URI fileURI = fFileAccessor.convertToEMFURI(uri);
		XMLResource resource = (XMLResource) new Autosar3xResourceFactoryImpl().createResource(fileURI);

		resource.load(null);
		resourceSet.getResources().add(resource);

		return (AUTOSAR) resource.getContents().get(0);
	}

}
