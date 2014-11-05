package org.artop.aal.autosar40.constraints.ecuc.tests;

import org.artop.aal.gautosar.constraints.ecuc.AbstractGConfigParameterImplConfigClassConstraint;
import org.artop.aal.gautosar.constraints.ecuc.messages.EcucConstraintMessages;
import org.artop.aal.gautosar.constraints.ecuc.tests.util.ValidationTestUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.osgi.util.NLS;

/**
 * Test for {@link AbstractGConfigParameterImplConfigClassConstraint}.
 */
public class EcucParameterDefImplConfigClassConstraintTest extends AbstractAutosar40ValidationTestCase {

	@Override
	protected String getConstraintID() {
		return "org.artop.aal.autosar40.constraints.ecuc.EcucParameterDefImplConfigClassConstraint_40"; //$NON-NLS-1$
	}

	/**
	 * Tests that the implementationConfigurationClass is the same in a Module Def vs the refined Module Def.
	 * 
	 * @throws Exception
	 */
	public void testSameConfigClass() throws Exception {

		EObject validModel = loadInputFile("ecuc/ParameterDef/configClassSame.arxml"); //$NON-NLS-1$
		ValidationTestUtil.validateModel(validModel, validator, IStatus.OK);

	}

	/**
	 * Tests that the implementationConfigurationClass is different in a Module Def vs the refined Module Def.
	 * 
	 * @throws Exception
	 */
	public void testDifferentConfigClass() throws Exception {

		EObject invalidModel = loadInputFile("ecuc/ParameterDef/configClassDifferent.arxml"); //$NON-NLS-1$

		ValidationTestUtil.validateModel(
				invalidModel,
				validator,
				IStatus.WARNING,
				NLS.bind(EcucConstraintMessages.configParameter_implConfigClassChanged, new Object[] {
						"/ARPackage4/MemMap/MemMapAddressingModeSet/MemMapSupportedAddressingMethodOption", "/ARPackage3/MemMap" })); //$NON-NLS-1$ //$NON-NLS-2$

	}
}
