/**
 * <copyright>
 * 
 * Copyright (c) Geensys and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 * 
 * Contributors: 
 *     Geensys - Initial API and implementation
 * 
 * </copyright>
 */
package org.artop.aal.examples.autosar3x.constraints.tests.integration.constraints;

import java.util.List;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.examples.validation.constraints.ARPackageSpecificNamingConvention3xConstraint;
import org.artop.aal.testutils.integration.referenceworkspace.AbstractAutosarIntegrationTestCase;
import org.artop.aal.testutils.integration.referenceworkspace.AutosarTestReferenceWorkspace;
import org.artop.ecl.emf.util.EcorePlatformUtil;
import org.artop.ecl.emf.validation.diagnostic.ExtendedDiagnostic;
import org.artop.ecl.emf.validation.diagnostic.ExtendedDiagnostician;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.ecucparameterdef.ModuleDef;
import autosar3x.ecuresource.ECU;
import autosar3x.genericstructure.infrastructure.ARObject;
import autosar3x.genericstructure.infrastructure.autosar.ARPackage;
import autosar3x.system.fibex.fibexcore.coretopology.EcuInstance;

// Warning ! This test class is based on precise model structure from arFile3x_3xA_5.arxml resource any
// changes in that resource could lead to a test failures.
@SuppressWarnings("nls")
public class ExampleValidationConstraintsTest extends AbstractAutosarIntegrationTestCase {
	private static String ARPACKAGE_SPECIFIQUE_NAMING_CONVENTION_3x_CONSTRAINT_ID = "org.artop.aal.examples.validation.ARPackageSpecificNamingConvention_3x";
	private static int ARPACKAGE_SPECIFIQUE_NAMING_CONVENTION_3x_CONSTRAINT_CODE = 101;

	private static String MODULE_DEF_MULTIPLICITY_SHOULD_BE_THE_SAME_3x_CONSTRAINT_ID = "org.artop.aal.examples.validation.ModuleDefMultiplicityShouldBeTheSame_3x";
	private static int MODULE_DEF_MULTIPLICITY_SHOULD_BE_THE_SAME_3x_CONSTRAINT_CODE = 102;

	private static String IDENTIABLE_ELEMENTS_MUST_HAVE_A_VALID_SHORT_NAME_3x_CONSTRAINT_ID = "org.artop.aal.examples.validation.IdentifiableElementsMustHaveAValidShortName_3x";
	private static int IDENTIABLE_ELEMENTS_MUST_HAVE_A_VALID_SHORT_NAME_3x_CONSTRAINT_CODE = 103;

	private static String SHORTNAME_OF_IDENTIFIABLE_ELEMENTS_MUST_BE_UNIQUE_3x_CONSTRAINT_ID = "org.artop.aal.examples.validation.ShortNameOfIdentifiableElementsMustBeUnique_3x";
	private static int SHORTNAME_OF_IDENTIFIABLE_ELEMENTS_MUST_BE_UNIQUE_3x_CONSTRAINT_CODE = 104;

	@Override
	protected String[] getProjectsToLoad() {

		return new String[] { AutosarTestReferenceWorkspace.AR_PROJECT_NAME_3x_A };
	}

	// TODO move this method to AutosarAbstractTestCase when this classes will be created (i.e after test framework
	// refactoring performed)
	private <T extends ARObject> T getArObject(IPath modelFilePath, String absoluteQualifiedName, Class<T> modelElementType) {
		URI modelFileURI = EcorePlatformUtil.createURI(modelFilePath);
		String uriFragment = AutosarURIFactory.createURIFragment(absoluteQualifiedName, modelElementType.getSimpleName());
		return modelElementType.cast(refWks.editingDomain3x.getResourceSet().getEObject(modelFileURI.appendFragment(uriFragment), false));
	}

	/**
	 * Test method for {@link ARPackageSpecificNamingConvention3xConstraint#validate(IValidationContext)}
	 */
	public void testValidationConstraint_ARPackageSpecificNamingConvention3xConstraint() {
		// we retrieve arFile3x_3xA_4.arxml file from arProject3x_A
		IFile arProject3xAFile3x_4 = refWks.getReferenceFile(AutosarTestReferenceWorkspace.AR_PROJECT_NAME_3x_A,
				AutosarTestReferenceWorkspace.AR_FILE_NAME_3x_3xA_4);
		// ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		// we retrieve ARPackage named badpackage
		ARPackage badPackage = getArObject(arProject3xAFile3x_4.getFullPath(), "/badPackage", ARPackage.class); //$NON-NLS-1$
		assertNotNull(badPackage);
		// we ensure that retrieved Arpackage exist

		Diagnostic diagnostic = diagnostician.validate(badPackage);
		assertNotNull(diagnostic);
		// we check that data point by root diagnostic is previously validated model object
		List<?> data = diagnostic.getData();
		assertNotNull(data);
		assertFalse(data.isEmpty());
		assertEquals(badPackage, data.get(0));
		// We check that expected ERROR severity was correctly set to diagnostic object
		assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		assertEquals(0, diagnostic.getCode());
		List<Diagnostic> diagnosticChildren = diagnostic.getChildren();
		assertEquals(1, diagnosticChildren.size());
		Diagnostic childDiagnostic = diagnosticChildren.get(0);
		assertTrue(childDiagnostic instanceof ExtendedDiagnostic);
		assertNotNull(childDiagnostic);
		assertTrue(childDiagnostic.getChildren().isEmpty());
		// we ensure that child severity is the same as parent and as expected
		assertEquals(Diagnostic.WARNING, childDiagnostic.getSeverity());
		// we ensure that the violated rule is the expected one
		assertEquals(ARPACKAGE_SPECIFIQUE_NAMING_CONVENTION_3x_CONSTRAINT_CODE, childDiagnostic.getCode());
		assertEquals(ARPACKAGE_SPECIFIQUE_NAMING_CONVENTION_3x_CONSTRAINT_ID, ((ExtendedDiagnostic) childDiagnostic).getConstraintId());
		// we ensure that data element stored is the expected element violating a rule
		List<?> childData = childDiagnostic.getData();
		assertNotNull(childData);
		assertEquals(childData.get(0), badPackage);

	}

	public void testValidationConstraint_ModuleDefMultiplicityShouldBeTheSame3xConstraint() {
		// we retrieve arFile3x_3xA_4.arxml file from arProject3x_A
		IFile arProject3xAFile3x_4 = refWks.getReferenceFile(AutosarTestReferenceWorkspace.AR_PROJECT_NAME_3x_A,
				AutosarTestReferenceWorkspace.AR_FILE_NAME_3x_3xA_4);
		// We retrieve ARPackage named arpackage3
		ARPackage arPackage3 = getArObject(arProject3xAFile3x_4.getFullPath(), "/arpackage3", ARPackage.class); //$NON-NLS-1$
		assertNotNull(arPackage3);
		// We create diagnostician instance and perform validation of model object
		ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		Diagnostic diagnostic = diagnostician.validate(arPackage3);
		assertNotNull(diagnostic);

		// we check that data point by root diagnostic is previously validated model object
		List<?> data = diagnostic.getData();
		assertNotNull(data);
		assertEquals(1, data.size());
		assertEquals(arPackage3, data.get(0));

		// We check that expected ERROR severity was correctly set to diagnostic object
		assertEquals(Diagnostic.ERROR, diagnostic.getSeverity());

		// We ensure that ERROR severity is not due to error on arpackage2 object itself
		assertEquals(0, diagnostic.getCode());

		// We retrieve Children Diagnostics and check that expected number have been created
		List<Diagnostic> diagnosticChildren = diagnostic.getChildren();
		assertEquals(3, diagnosticChildren.size());
		// We now check constraints for each diagnostic child
		for (Diagnostic childDiagnostic : diagnosticChildren) {
			if (childDiagnostic instanceof ExtendedDiagnostic) {
				assertNotNull(childDiagnostic);
				assertTrue(childDiagnostic.getChildren().isEmpty());
				// we ensure that child severity is the same as parent and as expected
				assertEquals(Diagnostic.ERROR, childDiagnostic.getSeverity());
				// we ensure that the violated rule is the expected one
				assertEquals(MODULE_DEF_MULTIPLICITY_SHOULD_BE_THE_SAME_3x_CONSTRAINT_CODE, childDiagnostic.getCode());

				assertEquals(MODULE_DEF_MULTIPLICITY_SHOULD_BE_THE_SAME_3x_CONSTRAINT_ID, ((ExtendedDiagnostic) childDiagnostic).getConstraintId());
				// we ensure that data element stored is the expected element violating a rule
				List<?> childData = childDiagnostic.getData();
				assertNotNull(childData);
				assertFalse(childData.isEmpty());
				assertTrue(childData.get(0) instanceof ModuleDef);

			}
		}

	}

	/**
	 * Test method for OCL constraint{@link IdentifiableElementsMustHaveAValidShortName_3x }
	 */
	public void testValidationConstraint_IdentifiableElementsMustHaveAValidShortName_3x() {
		// we retrieve arFile3x_3xA_4.arxml file from arProject3x_A
		IFile arProject3xAFile3x_4 = refWks.getReferenceFile(AutosarTestReferenceWorkspace.AR_PROJECT_NAME_3x_A,
				AutosarTestReferenceWorkspace.AR_FILE_NAME_3x_3xA_4);
		// We retrieve ARPackage named arpackage1
		ARPackage arPackage1 = getArObject(arProject3xAFile3x_4.getFullPath(), "/arpackage1", ARPackage.class); //$NON-NLS-1$
		assertNotNull(arPackage1);
		// This ARPackage contains an identifiable object with a ShortName field violating size limit i.e longer than 32
		// characters
		// We create diagnostician instance and perform validation of model object
		ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		Diagnostic diagnostic = diagnostician.validate(arPackage1);
		assertNotNull(diagnostic);
		// we check that data point by root diagnostic is previously validated model object
		List<?> data = diagnostic.getData();
		assertNotNull(data);
		assertEquals(1, data.size());
		assertEquals(arPackage1, data.get(0));
		// We check that expected ERROR severity was correctly set to diagnostic object
		assertEquals(Diagnostic.ERROR, diagnostic.getSeverity());
		// We ensure that ERROR severity is not due to error on arpackage1 object itself
		assertEquals(0, diagnostic.getCode());
		List<Diagnostic> diagnosticChildren = diagnostic.getChildren();
		assertNotNull(diagnosticChildren);
		assertEquals(2, diagnosticChildren.size());
		for (Diagnostic childDiagnostic : diagnosticChildren) {
			if (childDiagnostic instanceof ExtendedDiagnostic) {
				assertNotNull(childDiagnostic);
				// we ensure that child severity is the same as parent and as expected
				assertEquals(Diagnostic.ERROR, childDiagnostic.getSeverity());
				// we ensure that there is no other elements under this one violating rules
				assertTrue(childDiagnostic.getChildren().isEmpty());
				// we ensure that the violated rule is the expected one
				assertEquals(IDENTIABLE_ELEMENTS_MUST_HAVE_A_VALID_SHORT_NAME_3x_CONSTRAINT_CODE, childDiagnostic.getCode());

				assertEquals(IDENTIABLE_ELEMENTS_MUST_HAVE_A_VALID_SHORT_NAME_3x_CONSTRAINT_ID, ((ExtendedDiagnostic) childDiagnostic)
						.getConstraintId());
				// we ensure that data element stored is the expected element violating a rule
				List<?> childData = childDiagnostic.getData();
				assertNotNull(childData);
				assertFalse(childData.isEmpty());
				assertTrue(childData.get(0) instanceof EcuInstance);
				EcuInstance ecuInstance = (EcuInstance) childData.get(0);
				String shortName = ecuInstance.getShortName();
				assertTrue(shortName.length() > 32);
			}
		}
	}

	/**
	 * Test method for OCL constraint{@link ShortNameOfIdentifiableElementsMustBeUnique_3x }
	 */
	public void testValidationConstraint_ShortNameOfIdentifiableElementsMustBeUnique_3x() {
		// we retrieve arFile3x_3xA_4.arxml file from arProject3x_A
		IFile arProject3xAFile3x_4 = refWks.getReferenceFile(AutosarTestReferenceWorkspace.AR_PROJECT_NAME_3x_A,
				AutosarTestReferenceWorkspace.AR_FILE_NAME_3x_3xA_4);

		// We retrieve ARPackage named arpackage2
		ARPackage arPackage2 = getArObject(arProject3xAFile3x_4.getFullPath(), "/arpackage2", ARPackage.class); //$NON-NLS-1$
		assertNotNull(arPackage2);

		// We create diagnostician instance and perform validation of model object
		ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		Diagnostic diagnostic = diagnostician.validate(arPackage2);
		assertNotNull(diagnostic);

		// we check that data point by root diagnostic is previously validated model object
		List<?> data = diagnostic.getData();
		assertNotNull(data);
		assertEquals(1, data.size());
		assertEquals(arPackage2, data.get(0));

		// We check that expected ERROR severity was correctly set to diagnostic object
		assertEquals(Diagnostic.ERROR, diagnostic.getSeverity());

		// We ensure that ERROR severity is not due to error on arpackage2 object itself
		assertEquals(0, diagnostic.getCode());

		// We retrieve Children Diagnostics and check that expected number have been created
		List<Diagnostic> diagnosticChildren = diagnostic.getChildren();
		assertEquals(3, diagnosticChildren.size());
		// We now check constraints for each diagnostic child
		for (Diagnostic childDiagnostic : diagnosticChildren) {
			if (childDiagnostic instanceof ExtendedDiagnostic) {
				assertNotNull(childDiagnostic);
				assertTrue(childDiagnostic.getChildren().isEmpty());
				// we ensure that child severity is the same as parent and as expected
				assertEquals(Diagnostic.ERROR, childDiagnostic.getSeverity());
				// we ensure that the violated rule is the expected one
				assertEquals(SHORTNAME_OF_IDENTIFIABLE_ELEMENTS_MUST_BE_UNIQUE_3x_CONSTRAINT_CODE, childDiagnostic.getCode());
				assertEquals(SHORTNAME_OF_IDENTIFIABLE_ELEMENTS_MUST_BE_UNIQUE_3x_CONSTRAINT_ID, ((ExtendedDiagnostic) childDiagnostic)
						.getConstraintId());
				// we ensure that data element stored is the expected element violating a rule
				List<?> childData = childDiagnostic.getData();
				assertNotNull(childData);
				assertFalse(childData.isEmpty());
				assertTrue(childData.get(0) instanceof ECU);
			}
		}

	}

}
