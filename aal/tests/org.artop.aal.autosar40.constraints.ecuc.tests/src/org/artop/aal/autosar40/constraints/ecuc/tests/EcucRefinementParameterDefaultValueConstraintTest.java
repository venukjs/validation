package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

public class EcucRefinementParameterDefaultValueConstraintTest extends AbstractAutosar40ValidationTestCase {

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.EcucParameterDefDefaultValueConstraint_40";
	}

	public void testRefinementParameterDefaultValueConstraintInvalid() throws Exception {
		EObject invalidModel = loadInputFile("ecuc/Refinement/defaultValueInvalid.arxml");

		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.WARNING,
				NLS.bind(EcucConstraintMessages.configParameter_defaultValueChanged, new Object[] { "/TS_T2D13M4I0R57/Os/OsTask/OsTaskPriority",
						"/AUTOSAR/EcucDefs/Os" }));

	}

	public void testRefinementParameterDefaultValueConstraintValid() throws Exception {
		EObject validModel = loadInputFile("ecuc/Refinement/defaultValueInvalid.arxml");
		ValidationTestUtil.validateModel(validModel, validator, IStatus.WARNING);

	}

}
