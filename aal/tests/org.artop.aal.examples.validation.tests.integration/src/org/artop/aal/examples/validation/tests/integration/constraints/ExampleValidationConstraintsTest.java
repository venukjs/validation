package org.artop.aal.examples.validation.tests.integration.constraints;

import org.artop.aal.common.resource.AutosarURIFactory;
import org.artop.aal.examples.validation.constraints.ARPackageSpecificNamingConvention3xConstraint;
import org.artop.ecl.emf.util.EcorePlatformUtil;
import org.artop.ecl.testutils.DefaultTestCase;
import org.artop.ecl.testutils.DefaultTestReferenceWorkspace;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.validation.IValidationContext;

import autosar3x.genericstructure.infrastructure.ARObject;

// Warning ! This test class is based on precise model structure from arFile3x_3xA_5.arxml resource any
// changes in that resource could lead to a test failures.
public class ExampleValidationConstraintsTest extends DefaultTestCase {

	private static int ARPACKAGE_SPECIFIQUE_NAMING_CONVENTION_3x_CONSTRAINT_CODE = 101;
	private static int MODULE_DEF_MULTIPLICITY_SHOULD_BE_THE_SAME_3x_CONSTRAINT_CODE = 101;
	private static int SHORTNAME_OF_IDENTIFIABLE_ELEMENTS_MUST_BE_UNIQUE_3x_CONSTRAINT_CODE = 1;
	private static int IDENTIABLE_ELEMENTS_MUST_HAVE_A_VALID_SHORT_NAME_3x_CONSTRAINT_CODE = 1;

	// / ShortNameOfIdentifiableElementsMustBeUnique_3x code 1
	// IdentifiableElementsMustHaveAValidShortName_3x code 1
	@Override
	protected String[] getProjectsToLoad() {

		return new String[] { DefaultTestReferenceWorkspace.AR_PROJECT_NAME_3x_A };
	}

	private <T extends ARObject> T getArObject(IPath modelFilePath, String absoluteQualifiedName, Class<T> modelElementType) {
		URI arProject3xAFile3x_4_Uri = EcorePlatformUtil.createURI(modelFilePath);

		String uriFragment = AutosarURIFactory.createURIFragment(absoluteQualifiedName, modelElementType.getSimpleName());
		return modelElementType.cast(refWks.editingDomain3x.getResourceSet().getEObject(arProject3xAFile3x_4_Uri.appendFragment(uriFragment), false));
	}

	/**
	 * Test method for OCL constraint{@link IdentifiableElementsMustHaveAValidShortName_3x }
	 */
	public void testValidationConstraint_IdentifiableElementsMustHaveAValidShortName_3x() {

	}

	/**
	 * Test method for OCL constraint{@link ShortNameOfIdentifiableElementsMustBeUnique_3x }
	 */
	public void testValidationConstraint_ShortNameOfIdentifiableElementsMustBeUnique_3x() {
		// IFile arProject3xAFile3x_4 = refWks.getReferenceFile(DefaultTestReferenceWorkspace.AR_PROJECT_NAME_3x_A,
		// DefaultTestReferenceWorkspace.AR_FILE_NAME_3x_3xA_4);
		//
		// // We retrieve ARPackage named arpackage2
		//		ARPackage arPackage2 = getArObject(arProject3xAFile3x_4.getFullPath(), "/arpackage2", ARPackage.class); //$NON-NLS-1$
		// assertNotNull(arPackage2);
		//
		// // We create diagnostician instance and perform validation of model object
		// ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		// Diagnostic diagnostic = diagnostician.validate(arPackage2);
		// assertNotNull(diagnostic);
		// // We retrieve Children Diagnostics and check that expected number have been created
		//
		// // we check that data point by root diagnostic is previously validated model object
		// List<?> data = diagnostic.getData();
		// assertNotNull(data);
		// assertEquals(1, data.size());
		// assertEquals(arPackage2, data.get(0));
		//
		// // We check that expected ERROR severity was correctly set to diagnostic object
		// assertEquals(Diagnostic.ERROR, diagnostic.getSeverity());
		//
		// // We ensure that ERROR severity is not due to error on arpackage2 object itself
		// assertEquals(0, diagnostic.getCode());
		//
		// List<Diagnostic> diagnosticChildren = diagnostic.getChildren();
		// assertEquals(3, diagnosticChildren.size());
		// // We now check constraints for each diagnostic child
		// for (Diagnostic childDiagnostic : diagnosticChildren) {
		// if (childDiagnostic instanceof ExtendedDiagnostic) {
		// assertNotNull(childDiagnostic);
		// assertTrue(childDiagnostic.getChildren().isEmpty());
		// assertEquals(Diagnostic.ERROR, childDiagnostic.getSeverity());
		// assertEquals(SHORTNAME_OF_IDENTIFIABLE_ELEMENTS_MUST_BE_UNIQUE_3x_CONSTRAINT_CODE,
		// childDiagnostic.getCode());
		// assertEquals("org.artop.aal.examples.validation.ShortNameOfIdentifiableElementsMustBeUnique_3x",
		// ((ExtendedDiagnostic) childDiagnostic).getConstraintId());
		// List<?> childData = childDiagnostic.getData();
		// assertNotNull(childData);
		// assertFalse(childData.isEmpty());
		// assertTrue(childData.get(0) instanceof ECU);
		// }
		// }

	}

	/**
	 * Test method for {@link ARPackageSpecificNamingConvention3xConstraint#validate(IValidationContext)}
	 */
	public void testValidationConstraint_ARPackageSpecificNamingConvention3xConstraint() {

		// IFile arProject3xAFile3x_4 = refWks.getReferenceFile(DefaultTestReferenceWorkspace.AR_PROJECT_NAME_3x_A,
		// DefaultTestReferenceWorkspace.AR_FILE_NAME_3x_3xA_4);
		// ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		// // we retrieve ARPackage named badpackage
		//		ARPackage badPackage = getArObject(arProject3xAFile3x_4.getFullPath(), "/badPackage", ARPackage.class); //$NON-NLS-1$
		// assertNotNull(badPackage);
		// // we ensure that retrieved Arpackage exist
		//
		// Diagnostic diagnostic = diagnostician.validate(badPackage);
		// assertNotNull(diagnostic);
		// List<?> data = diagnostic.getData();
		// assertNotNull(data);
		// assertFalse(data.isEmpty());
		// assertEquals(badPackage, data.get(0));
		// assertEquals(Diagnostic.WARNING, diagnostic.getSeverity());
		// // assertEquals(ARPACKAGE_SPECIFIQUE_NAMING_CONVENTION_3x_CONSTRAINT_CODE, diagnostic.getCode());
		// // List<Diagnostic> childrenDiagnostics = diagnostic.getChildren();
		// // assertEquals(1, childrenDiagnostics.size());
		// // Diagnostic childDiagnostic = childrenDiagnostics.get(0);
		// // assertNotNull(childDiagnostic);

	}

	public void testValidationConstraint_ModuleDefMultiplicityShouldBeTheSame3xConstraint() {

		// IFile arProject3xAFile3x_4 = refWks.getReferenceFile(DefaultTestReferenceWorkspace.AR_PROJECT_NAME_3x_A,
		// DefaultTestReferenceWorkspace.AR_FILE_NAME_3x_3xA_4);
		// // we retrieve ARPackage named badpackage
		//		AUTOSAR autosarRoot = getArObject(arProject3xAFile3x_4.getFullPath(), "/", AUTOSAR.class); //$NON-NLS-1$
		// assertNotNull(autosarRoot);
		// ExtendedDiagnostician diagnostician = new ExtendedDiagnostician();
		// Diagnostic diagnostic = diagnostician.validate(autosarRoot);
		//		
		// ARPackage arpackage1 = null;
		// ARPackage arpackage2 = null;
		// ARPackage badpackage = null;
		//
		// // we retrieve ARpackages under Autosar root
		// for (ARPackage arpackage : topLevelPackages) {
		// if (arpackage.getShortName().equals("arpackage1")) {
		// arpackage1 = arpackage;
		// } else if (arpackage.getShortName().equals("arpackage2")) {
		// arpackage2 = arpackage;
		// } else if (arpackage.getShortName().equals("badpackage")) {
		// badpackage = arpackage;
		// }
		//
		// }
		// // we ensure that retrieved Arpackages exist
		// assertNotNull(arpackage1);
		// assertNotNull(arpackage2);
		// assertNotNull(badpackage);
	}

}
