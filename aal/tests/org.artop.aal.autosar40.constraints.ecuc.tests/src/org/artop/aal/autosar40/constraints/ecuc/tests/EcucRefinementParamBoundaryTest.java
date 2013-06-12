package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

public class EcucRefinementParamBoundaryTest extends AbstractAutosar40ValidationTestCase {

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.EcucIntegerParamDefLowerLimitConstraint_40"; //$NON-NLS-1$
	}

	public void testDefLowerLimitBoundaryConstraintInvalid() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Refinement/invalidParamBoundary.arxml");

		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.integerParamDef_LowerLimitSmallerInVendorSpecificModuleDefinition, new Object[] {
						"/TS_T2D13M4I0R57/Os/OsTask/OsTaskActivation", "/AUTOSAR/EcucDefs/Os" }));

	}

}
