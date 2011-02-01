package org.artop.aal.validation.constraints.swc.tests.portinterface;

import static org.artop.aal.gautosar.services.predicates.swc.portinterface.HasUniqueErrorCodes.DuplicateErrorCodes.MultiplyAssignedErrorCodeValue.multiplyAssignedErrorCodeValue;
import static org.artop.aal.validation.constraints.swc.tests.portinterface.mock.MockCSInterfaceExplainPredicates.nonUniqueErrorCodes;
import static org.artop.aal.validation.constraints.swc.tests.portinterface.mock.MockCSInterfaceExplainPredicates.uniqueErrorCodes;

import java.util.Arrays;

import junit.framework.TestCase;

import org.artop.aal.validation.constraints.swc.portinterface.UniqueApplicationErrorCodesConstraint;
import org.artop.aal.validation.constraints.swc.tests.portinterface.mock.MockConstraintDescriptor;
import org.artop.aal.validation.constraints.swc.tests.portinterface.mock.MockSwcPredicatesServiceProvider;
import org.artop.aal.validation.constraints.swc.tests.portinterface.mock.MockValidationContext;
import org.artop.aal.validation.constraints.swc.tests.portinterface.mock.MockValidationContext.FailureStatus;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;

/**
 * Tests if the failure messages returned by the <code>UniqueApplicationErrorCodesConstraint</code> are correct.
 */
@SuppressWarnings("nls")
public class UniqueApplicationErrorCodesConstraintTest extends TestCase {

	private static final String CONSTRAINT_DESCRIPTOR_ID = "ConstraintDescriptor";

	private static final String PLUGIN_ID = "org.artop.aal.validation.constraints.swc.tests";

	private static final IConstraintDescriptor CONSTRAINT_DESCRIPTOR = new MockConstraintDescriptor(CONSTRAINT_DESCRIPTOR_ID, PLUGIN_ID);

	private IValidationContext EMPTY_CONTEXT = new MockValidationContext(CONSTRAINT_DESCRIPTOR_ID);

	private MockSwcPredicatesServiceProvider fServiceProvider;
	private AbstractModelConstraint fConstraintUT;

	@Override
	protected void setUp() throws Exception {
		ConstraintRegistry.getInstance().register(CONSTRAINT_DESCRIPTOR);
		fServiceProvider = new MockSwcPredicatesServiceProvider();
		fConstraintUT = new UniqueApplicationErrorCodesConstraint(fServiceProvider);
	}

	@Override
	protected void tearDown() throws Exception {
		ConstraintRegistry.getInstance().unregister(CONSTRAINT_DESCRIPTOR);
	}

	public void testShouldPassWithUniqueErrorCodes() {
		fServiceProvider.setHasUniqueErrorCodesPredicate(uniqueErrorCodes());
		assertEquals(Status.OK_STATUS, fConstraintUT.validate(EMPTY_CONTEXT));
	}

	public void testShouldCreateOneFailureMessageWithTwoErrorCodes() {
		fServiceProvider.setHasUniqueErrorCodesPredicate(nonUniqueErrorCodes(multiplyAssignedErrorCodeValue(0, "IDLE", "INIT")));
		String expectedMessage = "More than one ApplicationErrorCode has the value \"0\": IDLE, INIT";
		assertFailureStatus(fConstraintUT.validate(EMPTY_CONTEXT), expectedMessage);
	}

	public void testShouldCreateOneFailureMessageWithThreeErrorCodes() {
		fServiceProvider.setHasUniqueErrorCodesPredicate(nonUniqueErrorCodes(multiplyAssignedErrorCodeValue(0, "IDLE", "INIT", "RUNNING")));
		String expectedMessage = "More than one ApplicationErrorCode has the value \"0\": IDLE, INIT, RUNNING";
		assertFailureStatus(fConstraintUT.validate(EMPTY_CONTEXT), expectedMessage);
	}

	public void testShouldCreateTwoFailureMessages() {
		fServiceProvider.setHasUniqueErrorCodesPredicate(nonUniqueErrorCodes(multiplyAssignedErrorCodeValue(0, "IDLE", "INIT"),
				multiplyAssignedErrorCodeValue(1, "RUNNING", "WAITING")));
		String expectedMessage1 = "More than one ApplicationErrorCode has the value \"0\": IDLE, INIT";
		String expectedMessage2 = "More than one ApplicationErrorCode has the value \"1\": RUNNING, WAITING";
		assertFailureStatus(fConstraintUT.validate(EMPTY_CONTEXT), expectedMessage1, expectedMessage2);
	}

	private void assertFailureStatus(IStatus failureStatus, String... expectedMessages) {
		IStatus[] failureStatuses = extractAllFailureStatuses(failureStatus);
		assertEquals(expectedMessages.length, failureStatuses.length);
		for (IStatus status : failureStatuses) {
			assertIsFailure(status);
			assertFailureMessage(status, expectedMessages);
		}
	}

	private void assertIsFailure(IStatus failureStatus) {
		assertEquals("The status returned by the constraint is not a failure status.", FailureStatus.class, failureStatus.getClass());
	}

	private IStatus[] extractAllFailureStatuses(IStatus failureStatus) {
		if (failureStatus.isMultiStatus()) {
			return failureStatus.getChildren();
		}
		return new IStatus[] { failureStatus };
	}

	private void assertFailureMessage(IStatus failureStatus, String... expectedMessages) {
		String failureMsg = failureStatus.getMessage();
		assertTrue("The returned failure message is not correct: \"" + failureMsg + "\"", Arrays.asList(expectedMessages).contains(failureMsg));
	}

}
