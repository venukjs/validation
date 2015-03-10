package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

public class EcucRefinementMultiplicityTest extends AbstractAutosar40ValidationTestCase {

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.EcucContainerDefLowerMultiplicityConstraint_40";//$NON-NLS-1$;
	}

	public void testDefLowerMultiplicityConstraintNeg() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Refinement/lowerMultiplicityInvalid.arxml");

		ValidationTestUtil.validateModel(invalidModel, validator, IStatus.ERROR,
				NLS.bind(EcucConstraintMessages.containerDef_lowerMultiplicityMismatching, new Object[] {
				/* "/TS_T2D13M4I0R57/Os/OsAlarm/OsAlarmAutostart", */"/AUTOSAR/EcucDefs/Os/OsAlarm/OsAlarmAutostart" }));

	}

	public void testDefLowerMultiplicityConstraintPos() throws Exception {
		EObject validModel = loadInputFile("ecuc/Refinement/lowerMultiplicityValid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);
	}

}
