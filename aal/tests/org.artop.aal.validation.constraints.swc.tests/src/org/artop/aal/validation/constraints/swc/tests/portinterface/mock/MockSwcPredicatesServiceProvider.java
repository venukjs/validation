package org.artop.aal.validation.constraints.swc.tests.portinterface.mock;

import gautosar.gswcomponents.gportinterface.GClientServerInterface;
import junit.framework.Assert;

import org.artop.aal.gautosar.services.IMetaModelService;
import org.artop.aal.gautosar.services.IMetaModelServiceProvider;
import org.artop.aal.gautosar.services.predicates.ExplainablePredicate;
import org.artop.aal.gautosar.services.predicates.swc.ISwcPredicatesService;
import org.artop.ecl.emf.metamodel.IMetaModelDescriptor;
import org.artop.ecl.emf.metamodel.providers.IMetaModelDescriptorProvider;

/**
 * A <code>IMetaModelServiceProvider</code> which provides a
 * {@link org.artop.aal.validation.constraints.swc.tests.portinterface.mock.MockSwcPredicatesServiceProvider.MockSwcPredicatesService}
 * .
 */
public class MockSwcPredicatesServiceProvider implements IMetaModelServiceProvider {

	private final MockSwcPredicatesService fSwcPredicatesService = new MockSwcPredicatesService();

	@SuppressWarnings("unchecked")
	public <T extends IMetaModelService> T getService(IMetaModelDescriptor descriptor, Class<T> serviceType) {
		Assert.assertEquals("Unexpected service was requested!", ISwcPredicatesService.class, serviceType); //$NON-NLS-1$
		return (T) fSwcPredicatesService;
	}

	public <T extends IMetaModelService> T getService(IMetaModelDescriptorProvider provider, Class<T> serviceType) {
		return getService(provider.getMetaModelDescriptor(), serviceType);
	}

	public void setHasUniqueErrorCodesPredicate(ExplainablePredicate<GClientServerInterface> predicate) {
		fSwcPredicatesService.setHasUniqueErrorCodesPredicate(predicate);
	}

	/**
	 * The <code>Predicates</code> of this <code>ISwcPredicateService</code> which it will provide to clients can be set
	 * from the outside. Can be used for providing MockPredicates to classes which are to be tested.
	 */
	private static class MockSwcPredicatesService implements ISwcPredicatesService {

		private ExplainablePredicate<GClientServerInterface> fHasUniqueErrorCodesPredicate;

		public ExplainablePredicate<GClientServerInterface> hasUniqueErrorCodes() {
			return fHasUniqueErrorCodesPredicate;
		}

		public void setHasUniqueErrorCodesPredicate(ExplainablePredicate<GClientServerInterface> predicate) {
			fHasUniqueErrorCodesPredicate = predicate;
		}

	}
}
