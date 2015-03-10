package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

public class EcucRefinementMissingParamTest extends AbstractAutosar40ValidationTestCase {

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.EcucParamConfContainerDefConfigParameterMissingConstraint_40";//$NON-NLS-1$;
	}

	public void testMissingConfigurationParam() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Refinement/missingConfigParamInvalid.arxml");

		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.paramConfigContainerDef_configParameterMissing, new Object[] { " ", "OsTaskSchedule", " is",
				/* "/TS_T2D13M4I0R57/Os/OsTask" */}));

	}

}
