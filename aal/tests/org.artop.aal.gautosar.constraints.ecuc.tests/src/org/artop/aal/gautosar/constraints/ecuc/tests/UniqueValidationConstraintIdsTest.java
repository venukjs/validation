package org.artop.aal.gautosar.constraints.ecuc.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.validation.service.ConstraintRegistry;
import org.eclipse.emf.validation.service.IConstraintDescriptor;
import org.eclipse.emf.validation.service.IParameterizedConstraintDescriptor;
import org.eclipse.emf.validation.service.ModelValidationService;
import org.junit.Test;

/**
 * This is a test which verifies that each vendor specific constraint has an unique
 * <code>constraintIdParameterName</code>. A constraint is vendor specific if it the validation rule cannot be
 * originated in a written requirement from the standard AUTOSAR documents.
 */
public class UniqueValidationConstraintIdsTest {
	/**
	 * The parameter holding the constraint id. It is read from the the descriptor of registered constraints.
	 */
	private final String constraintIdParameterName = "constrId"; //$NON-NLS-1$
	/**
	 * Substring used to specify a vendor constraint in the constraint id.
	 */
	private final String vendorSpecificConstraintIdPart = "_artop_"; //$NON-NLS-1$
	private List<String> modelFilters = Arrays.asList("_21", "_3x", "_40"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	// ids that represent the same rule and it's ok to have the same id

	private static List<String> exceptionConstraints = new ArrayList<String>();
	static {

		exceptionConstraints.add("org.artop.aal.autosar21.constraints.ecuc.IntegerValueBasicConstraint_21"); //$NON-NLS-1$
		exceptionConstraints.add("org.artop.aal.autosar21.constraints.ecuc.BooleanValueBasicConstraint_21"); //$NON-NLS-1$
		exceptionConstraints.add("org.artop.aal.autosar21.constraints.ecuc.FloatValueBasicConstraint_21"); //$NON-NLS-1$
		exceptionConstraints.add("org.artop.aal.autosar40.constraints.ecuc.EcucNumericalParamValueBasicConstraint_40"); //$NON-NLS-1$
		exceptionConstraints.add("org.artop.aal.autosar3x.constraints.ecuc.BooleanValueBasicConstraint_3x"); //$NON-NLS-1$
		exceptionConstraints.add("org.artop.aal.autosar3x.constraints.ecuc.FloatValueBasicConstraint_3x"); //$NON-NLS-1$
	}

	/**
	 * Map containing the constraint ids for each constraint
	 */
	private Map<String, IConstraintDescriptor> constraintIdsMap = new HashMap<String, IConstraintDescriptor>();

	public UniqueValidationConstraintIdsTest() {
		super();
	}

	@Test
	/**
	 * Test that the constraints have unique id.
	 */
	public void testUniqueConstraints() {
		ModelValidationService.getInstance().loadXmlConstraintDeclarations();
		Map<String, List<IConstraintDescriptor>> duplicateConstraintIdsMap = new HashMap<String, List<IConstraintDescriptor>>();
		ConstraintRegistry registry = ConstraintRegistry.getInstance();
		Collection<IConstraintDescriptor> allDescriptors = registry.getAllDescriptors();
		for (IConstraintDescriptor constraint : allDescriptors) {
			assertTrue(constraint instanceof IParameterizedConstraintDescriptor);
			String constraintIdValue = ((IParameterizedConstraintDescriptor) constraint).getParameterValue(constraintIdParameterName);
			// check for duplicate constraint ids only for vendor specific constraints
			if (constraintIdValue != null && constraintIdValue.contains(vendorSpecificConstraintIdPart)) {
				if (constraintIdsMap.containsKey(constraintIdValue)) {
					// check if the constraint is the same, only for different metamodel. In this case the constraint
					// id can be the same
					IConstraintDescriptor existingConstraintDescriptor = constraintIdsMap.get(constraintIdValue);
					String existingConstraintDescriptorId = existingConstraintDescriptor.getId();
					String existingDescriptorFiltered = clearDescriptorByFilters(existingConstraintDescriptorId, modelFilters);
					String descriptorFiltered = clearDescriptorByFilters(constraint.getId(), modelFilters);
					if (existingDescriptorFiltered.equals(descriptorFiltered)) {
						continue;
					}

					// check for similarities in the name, msg or description
					if (areConstraintsSimilar(existingConstraintDescriptor, constraint)) {
						continue;
					}

					// else put as duplicate
					List<IConstraintDescriptor> duplicateConstraintDescriptorsList = duplicateConstraintIdsMap.get(constraintIdValue);
					if (duplicateConstraintDescriptorsList == null) {
						duplicateConstraintDescriptorsList = new ArrayList<IConstraintDescriptor>();
						duplicateConstraintDescriptorsList.add(existingConstraintDescriptor);
					}
					duplicateConstraintDescriptorsList.add(constraint);
					duplicateConstraintIdsMap.put(constraintIdValue, duplicateConstraintDescriptorsList);
				} else {
					constraintIdsMap.put(constraintIdValue, constraint);
				}
			}
		}
		// there should be more constraint IDs
		assertNotSame(0, constraintIdsMap.size());
		// there should be no duplicate vendor specific constraint Id
		String message = "These constraints have duplicate ids:"; //$NON-NLS-1$
		for (String id : duplicateConstraintIdsMap.keySet()) {
			message = message + " \n constraintID: " + id + " is used for " + duplicateConstraintIdsMap.get(id).size() + " constraints: "; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			List<IConstraintDescriptor> list = duplicateConstraintIdsMap.get(id);
			for (IConstraintDescriptor iConstraintDescriptor : list) {
				message = message + " ," + iConstraintDescriptor.getId(); //$NON-NLS-1$
			}
		}
		assertEquals(message, 0, duplicateConstraintIdsMap.size());
	}

	/**
	 * Check if the constraints have similarities in the name or description or if they are exception constraints that
	 * shall have the same ids in the same model and across models.
	 * 
	 * @param existingConstraintDescriptor
	 * @param constraintDescriptor
	 * @return
	 */
	private boolean areConstraintsSimilar(IConstraintDescriptor existingConstraintDescriptor, IConstraintDescriptor constraintDescriptor) {
		String name1 = existingConstraintDescriptor.getName();
		String name2 = constraintDescriptor.getName();

		if (name1.contains(name2) || name2.contains(name1)) {
			return true;
		}

		String description1 = existingConstraintDescriptor.getDescription();
		String description2 = constraintDescriptor.getDescription();
		if (description1.equals(description2)) {
			return true;
		}

		String id1 = existingConstraintDescriptor.getId();
		String id2 = constraintDescriptor.getId();
		if (exceptionConstraints.contains(id1) && exceptionConstraints.contains(id2)) {
			return true;
		}
		return false;
	}

	/**
	 * @return the <code>descriptor<code> without filtering information and package details
	 */
	private String clearDescriptorByFilters(String descriptor, List<String> filters) {
		String filteredValue = null;
		for (String filter : filters) {
			if (descriptor.endsWith(filter)) {
				int indexOf = descriptor.indexOf(filter);
				if (indexOf > 0) {
					filteredValue = descriptor.substring(0, indexOf);
					break;
				}
			}

		}
		if (filteredValue == null) {
			filteredValue = descriptor;
		}
		// remove also package details from constraint description
		int lastIndexOfSeparator = filteredValue.lastIndexOf("."); //$NON-NLS-1$
		filteredValue = filteredValue.substring(lastIndexOfSeparator + 1, filteredValue.length());
		return filteredValue;
	}
}
