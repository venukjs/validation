/**
 * <copyright>
 *
 * Copyright (c) itemis and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Artop Software License Based on AUTOSAR
 * Released Material (ASLR) which accompanies this distribution, and is
 * available at http://www.artop.org/aslr.html
 *
 * Contributors:
 *     itemis - Initial API and implementation
 *
 * </copyright>
 */
package org.artop.aal.examples.autosar421.check;

import autosar40.autosartoplevelstructure.AUTOSAR
import autosar40.commonstructure.constants.NumericalValueSpecification
import autosar40.commonstructure.constants.TextValueSpecification
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage
import autosar40.genericstructure.generaltemplateclasses.primitivetypes.ByteOrderEnum
import autosar40.system.RootSwCompositionPrototype
import autosar40.system.System
import autosar40.system.SystemPackage
import autosar40.system.datamapping.ClientServerToSignalMapping
import autosar40.system.datamapping.DatamappingPackage
import autosar40.system.datamapping.TriggerToSignalMapping
import autosar40.system.diagnosticconnection.DiagnosticConnection
import autosar40.system.diagnosticconnection.DiagnosticconnectionPackage
import autosar40.system.diagnosticconnection.TpConnectionIdent
import autosar40.system.ecuresourcemapping.CommunicationControllerMapping
import autosar40.system.ecuresourcemapping.ECUMapping
import autosar40.system.ecuresourcemapping.EcuresourcemappingPackage
import autosar40.system.ecuresourcemapping.HwPortMapping
import autosar40.system.fibex.fibex4can.cancommunication.CanFrame
import autosar40.system.fibex.fibex4can.cantopology.AbstractCanCommunicationControllerAttributes
import autosar40.system.fibex.fibex4can.cantopology.CanCluster
import autosar40.system.fibex.fibex4can.cantopology.CanControllerFdConfiguration
import autosar40.system.fibex.fibex4can.cantopology.CanControllerFdConfigurationRequirements
import autosar40.system.fibex.fibex4can.cantopology.CantopologyPackage
import autosar40.system.fibex.fibex4ethernet.ethernetcommunication.EthernetFrame
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernettopologyPackage
import autosar40.system.fibex.fibex4ethernet.ethernettopology.TpPort
import autosar40.system.fibex.fibex4flexray.flexraycommunication.FlexrayFrame
import autosar40.system.fibex.fibex4flexray.flexraytopology.FlexrayClusterConditional
import autosar40.system.fibex.fibex4lin.lincommunication.LinFrame
import autosar40.system.fibex.fibex4multiplatform.Fibex4multiplatformPackage
import autosar40.system.fibex.fibex4multiplatform.ISignalMapping
import autosar40.system.fibex.fibexcore.corecommunication.ContainerIPdu
import autosar40.system.fibex.fibexcore.corecommunication.CorecommunicationPackage
import autosar40.system.fibex.fibexcore.corecommunication.DcmIPdu
import autosar40.system.fibex.fibexcore.corecommunication.Frame
import autosar40.system.fibex.fibexcore.corecommunication.IPdu
import autosar40.system.fibex.fibexcore.corecommunication.ISignal
import autosar40.system.fibex.fibexcore.corecommunication.ISignalIPdu
import autosar40.system.fibex.fibexcore.corecommunication.ISignalIPduGroup
import autosar40.system.fibex.fibexcore.corecommunication.ISignalToIPduMapping
import autosar40.system.fibex.fibexcore.corecommunication.PduToFrameMapping
import autosar40.system.fibex.fibexcore.corecommunication.PduTriggering
import autosar40.system.fibex.fibexcore.coretopology.CommunicationConnector
import autosar40.system.fibex.fibexcore.coretopology.CoretopologyPackage
import autosar40.system.fibex.fibexcore.coretopology.EcuInstance
import autosar40.system.fibex.fibexcore.coretopology.PhysicalChannel
import autosar40.system.globaltime.GlobalTimeGateway
import autosar40.system.globaltime.GlobaltimePackage
import autosar40.system.networkmanagement.CanNmCluster
import autosar40.system.networkmanagement.NetworkmanagementPackage
import autosar40.system.swmapping.EcuResourceEstimation
import autosar40.system.swmapping.SwcToEcuMapping
import autosar40.system.swmapping.SwmappingPackage
import autosar40.system.transformer.SOMEIPTransformationDescription
import autosar40.system.transformer.TransformerPackage
import autosar40.system.transportprotocols.CanTpConnection
import autosar40.system.transportprotocols.FlexrayArTpConnection
import autosar40.system.transportprotocols.FlexrayTpConnection
import com.google.common.collect.HashMultimap
import com.google.common.collect.Multimap
import java.text.MessageFormat
import java.util.BitSet
import java.util.List
import java.util.Map
import org.eclipse.emf.common.notify.Adapter
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter
import org.eclipse.sphinx.emf.check.Check
import org.eclipse.xtext.xbase.lib.Functions.Function1
import org.eclipse.sphinx.emf.check.CheckValidatorRegistry

class SystemTemplateValidator extends AbstractAutosarCheckValidator { 

	// Override to create nicer message:
	protected def override void issue(EObject eObject, EStructuralFeature feature, int index, Object... arguments) {
		val current = getState().get()
		val constraint = current.constraint
		val catalog = CheckValidatorRegistry.INSTANCE.getCheckCatalog(this)
		if (catalog == null) 
			return

		val severityMessage = MessageFormat.format(constraint + " - " + catalog.getMessage(constraint), arguments)
		val severity = catalog.getSeverity(constraint)
		switch (severity) {
			case ERROR: error(severityMessage, eObject, feature, index)
			case WARNING: warning(severityMessage, eObject, feature, index)
			case INFO: info(severityMessage, eObject, feature, index)
			default: throw new IllegalArgumentException("Unknow severity " + severity) //$NON-NLS-1$
		}
	}

	protected def <T> Multimap<T, T> overlap(List<T> input, Function1<T, Integer> s, Function1<T, Integer> e) {
		var BitSet all = new BitSet()

		var Map<BitSet, T> others = newHashMap()
		var Multimap<T, T> multimap = HashMultimap.<T, T> create()
		for (T m : input) {
			var BitSet b = new BitSet()
			b.set(s.apply(m), e.apply(m))

			if (all.intersects(b)) {
				for (BitSet ob : others.keySet()) {
					if (ob.intersects(b)) {
						multimap.put(m, others.get(ob))
						multimap.put(others.get(ob), m)
					}
				}
			}
			all.or(b)
			others.put(b, m)
		}
		multimap
	}

	protected def ECrossReferenceAdapter getCrossReferenceAdapter(EObject eObject) {
		for (Adapter adapter : eObject.eResource().getResourceSet().eAdapters()) {
			if (adapter instanceof ECrossReferenceAdapter)
				return adapter
		}

		val ECrossReferenceAdapter adapter = new ECrossReferenceAdapter()
		eObject.eResource().getResourceSet().eAdapters().add(adapter)
		return adapter
	}

	/**
	 *
	 * 
	 * constr_1198
	 *
	 * 
	 * TriggerToSignalMapping.TriggerToSignalMapping.systemSignals eligible for a 
	 * TriggerToSignalMapping
	 *
	 * 
	 * In the context of a TriggerToSignalMapping, it is only possible to refer to a 
	 * TriggerToSignalMapping.TriggerToSignalMapping.systemSignal that in turn is 
	 * referenced by an ISignal with attribute ISignal.length set to 0.
	 */
	@Check(constraint="constr_1198", categories=#["SystemTemplate"])
	def void constr_1198(TriggerToSignalMapping triggerToSignalMapping) {
		val systemSignal = triggerToSignalMapping.getSystemSignal()
		if (systemSignal != null) {
			val references = getCrossReferenceAdapter(systemSignal).getInverseReferences(systemSignal)
			val filteredReferences = references.filter[it.getEObject() instanceof ISignal && (it.getEObject() as ISignal).getLength() == 0]
			if (!filteredReferences.iterator().hasNext()) 
				issue(triggerToSignalMapping, DatamappingPackage.Literals.TRIGGER_TO_SIGNAL_MAPPING__SYSTEM_SIGNAL)
		}
	}

	/**
	 *
	 * 
	 * constr_1199
	 *
	 * 
	 * ISignals relating to TriggerToSignalMapping.systemSignals eligible for a 
	 * TriggerToSignalMapping
	 *
	 * 
	 * An ISignal used to reference a TriggerToSignalMapping.systemSignal that in turn 
	 * is referenced by a TriggerToSignalMapping shall also be referenced by an 
	 * ISignalToIPduMapping where the attribute 
	 * ISignalToIPduMapping.updateIndicationBitPosition is defined.
	 */
	//	@Check(constraint="constr_1199",categories=#["SystemTemplate"])
	// def void constr_1199( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1207
	 *
	 * 
	 * Existence of the attribute DataMapping.DataMapping.communicationDirection in the
	 *  context of a SenderReceiverInterface or TriggerInterface
	 *
	 * 
	 * The following condition shall be fulfilled regarding the existence and values of
	 *  the attribute DataMapping.DataMapping.communicationDirection that refers to a 
	 * PortPrototype typed by a SenderReceiverInterface or TriggerInterface as the 
	 * context PortPrototype:
	 */
	//	@Check(constraint="constr_1207",categories=#["SystemTemplate"])
	// def void constr_1207( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1265
	 *
	 * 
	 * DoIpGidSynchronizationNeeds can only exist once per ECU_EXTRACT
	 *
	 * 
	 * Within the context of one System of Identifiable.categoryECU_EXTRACT, there can 
	 * only be at most one DoIpGidSynchronizationNeeds.
	 */
	//	@Check(constraint="constr_1265",categories=#["SystemTemplate"])
	// def void constr_1265( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1266
	 *
	 * 
	 * DoIpGidNeeds can only exist once per ECU_EXTRACT
	 *
	 * 
	 * Within the context of one System of Identifiable.categoryECU_EXTRACT, there can 
	 * only be at most one DoIpGidNeeds.
	 */
	//	@Check(constraint="constr_1266",categories=#["SystemTemplate"])
	// def void constr_1266( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1267
	 *
	 * 
	 * DoIpActivationLineNeeds can only exist once per ECU_EXTRACT
	 *
	 * 
	 * Within the context of one System of Identifiable.categoryECU_EXTRACT, there can 
	 * only be at most one DoIpActivationLineNeeds.
	 */
	//	@Check(constraint="constr_1267",categories=#["SystemTemplate"])
	// def void constr_1267( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1367
	 *
	 * 
	 * DiagnosticConnection.periodicResponseUudt.DiagnosticConnection.periodicResponseU
	 * udt shall only refer to a DcmIPdu
	 *
	 * 
	 * If the role DiagnosticConnection.periodicResponseUudt exists then every 
	 * PduTriggering referenced in the role DiagnosticConnection.periodicResponseUudt 
	 * shall only refer to a DcmIPdu.
	 */
	@Check(constraint="constr_1367", categories=#["SystemTemplate"])
	def void constr_1367(DiagnosticConnection diagnostic) {
		issuePred(diagnostic.getPeriodicResponseUudts(), [PduTriggering x|!(x instanceof DcmIPdu)],
			DiagnosticconnectionPackage.Literals.DIAGNOSTIC_CONNECTION__PERIODIC_RESPONSE_UUDTS)
	}

	/**
	 *
	 * 
	 * constr_1368
	 *
	 * 
	 * Limitation of the target of references from DiagnosticConnection
	 *
	 * 
	 * DiagnosticConnection shall only reference (via the indirection created by 
	 * TpConnectionIdent) the following sub-classes of the meta-class TpConnection:
	 */
	@Check(constraint="constr_1368", categories=#["SystemTemplate"])
	def void constr_1368(DiagnosticConnection diagnostic) {
		val tpConnectionIdents = diagnostic.eCrossReferences().filter(typeof(TpConnectionIdent))
		tpConnectionIdents.forEach[
			if (!(it instanceof CanTpConnection || it instanceof FlexrayTpConnection ||
				it instanceof FlexrayArTpConnection)) {
				issue(diagnostic, null)
			}
		]
	}

	/**
	 *
	 * 
	 * constr_1369
	 *
	 * 
	 * CommunicationConnectors shall be attached to the same CommunicationCluster
	 *
	 * 
	 * All CommunicationConnectors referenced from GlobalTimeMaster and 
	 * GlobalTimeSlaves aggregated in one GlobalTimeDomain shall be referenced in the 
	 * role PhysicalChannel.commConnector by the same PhysicalChannel aggregated by the
	 *  same CommunicationCluster.
	 */
	//	@Check(constraint="constr_1369",categories=#["SystemTemplate"])
	// def void constr_1369( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1370
	 *
	 * 
	 * Consistency of GlobalTimeDomain
	 *
	 * 
	 * The GlobalTimeSlave referenced in the role 
	 * GlobalTimeGateway.GlobalTimeGateway.slave and the GlobalTimeMaster referenced in
	 *  the role GlobalTimeGateway.GlobalTimeGateway.master shall not be aggregated by 
	 * the same GlobalTimeDomain.
	 */
	//	@Check(constraint="constr_1370",categories=#["SystemTemplate"])
	// def void constr_1370( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1371
	 *
	 * 
	 * Consistency of attribute GlobalTimeGateway.host
	 *
	 * 
	 * Within the context of an aggregating GlobalTimeDomain, the 
	 * CommunicationConnectors referenced in the role 
	 * GlobalTimeGateway.GlobalTimeGateway.master.GlobalTimeSlave.communicationConnecto
	 * r and 
	 * GlobalTimeGateway.GlobalTimeGateway.slave.GlobalTimeMaster.communicationConnecto
	 * r shall be aggregated by the same EcuInstance that is referenced in the role 
	 * GlobalTimeGateway.GlobalTimeGateway.host.
	 */
	//	@Check(constraint="constr_1371",categories=#["SystemTemplate"])
	// def void constr_1371( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1372
	 *
	 * 
	 * Consistency of attribute GlobalTimeDomain.globalTimePdu
	 *
	 * 
	 * Within the context of an aggregating GlobalTimeDomain, the 
	 * GlobalTimeDomain.globalTimePdu shall be referenced by PduTriggerings owned by a 
	 * single PhysicalChannel that is also referencing the CommunicationConnectors 
	 * referenced in the roles GlobalTimeSlave.GlobalTimeSlave.communicationConnector 
	 * and  GlobalTimeMaster.GlobalTimeMaster.communicationConnector.
	 */
	//	@Check(constraint="constr_1372",categories=#["SystemTemplate"])
	// def void constr_1372( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_1373
	 *
	 * 
	 * GlobalTimeMaster with attribute GlobalTimeMaster.isSystemWideGlobalTimeMaster 
	 * set to TRUE
	 *
	 * 
	 * GlobalTimeMaster with attribute GlobalTimeMaster.isSystemWideGlobalTimeMaster 
	 * set to TRUE shall not be referenced in the role 
	 * GlobalTimeGateway.GlobalTimeGateway.master.
	 */
	@Check(constraint="constr_1373", categories=#["SystemTemplate"])
	def void constr_1373(GlobalTimeGateway gateway) {
		if (gateway.getMaster().isSetIsSystemWideGlobalTimeMaster() == true)
			issue(gateway, GlobaltimePackage.Literals.GLOBAL_TIME_MASTER__IS_SYSTEM_WIDE_GLOBAL_TIME_MASTER)
	}

	/**
	 *
	 * 
	 * constr_1374
	 *
	 * 
	 * Only fan-out possible for GlobalTimeGateway
	 *
	 * 
	 * For all GlobalTimeGateways that refer to the same EcuInstance the condition 
	 * applies that no two GlobalTimeGateways shall refer to the same GlobalTimeSlave.
	 */
	//	@Check(constraint="constr_1374",categories=#["SystemTemplate"])
	// def void constr_1374( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_2025
	 *
	 * 
	 * Uniqueness of RunnableEntity.symbol attributes
	 *
	 * 
	 * In the context of a single EcuInstance, the values of the 
	 * RunnableEntity.RunnableEntity.symbol in combination with the attribute 
	 * AtomicSwComponentType.AtomicSwComponentType.symbol of all deployed 
	 * RunnableEntitys shall be unique such that no two (or more ) combinations of 
	 * RunnableEntity.RunnableEntity.symbol and 
	 * AtomicSwComponentType.AtomicSwComponentType.symbol share the same value.
	 */
	//	@Check(constraint="constr_2025",categories=#["SystemTemplate"])
	// def void constr_2025( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3000
	 *
	 * 
	 * valid SenderRecCompositeTypeMappings
	 *
	 * 
	 * SenderReceiverToSignalGroupMapping.SenderReceiverToSignalGroupMapping.signalGrou
	 * p.SystemSignalGroup.systemSignal shall point to each
	 SystemSignal being mapped 
	 * within the context of SenderReceiverToSignalGroupMapping.
	 */
	//	@Check(constraint="constr_3000",categories=#["SystemTemplate"])
	// def void constr_3000( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3002
	 *
	 * 
	 * valid swcToImplMapping
	 *
	 * 
	 */
	//	@Check(constraint="constr_3002",categories=#["SystemTemplate"])
	// def void constr_3002( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3003
	 *
	 * 
	 * Number of CAN channels
	 *
	 * 
	 */
	@Check(constraint="constr_3003", categories="SystemTemplate")
	def void constr_3003(CanCluster canCluster) {
		val canClusterConditionals = canCluster.getCanClusterVariants().filter[it.getPhysicalChannels().size() > 1]
		canClusterConditionals.forEach[issue(it, null)]
	}

	/**
	 *
	 * 
	 * constr_3004
	 *
	 * 
	 * Clustering and separation must be exclusive
	 *
	 * 
	 */
	//	@Check(constraint="constr_3004",categories=#["SystemTemplate"])
	// def void constr_3004( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3005
	 *
	 * 
	 * valid EcuResourceEstimation
	 *
	 * 
	 */
	@Check(constraint="constr_3005", categories=#["SystemTemplate"])
	def void constr_3005( EcuResourceEstimation ecuResourceEst ) {
		issuePred(ecuResourceEst.getSwCompToEcuMappings(),
				[SwcToEcuMapping x | {return x.getEcuInstance() != ecuResourceEst.getEcuInstance()}],
				SwmappingPackage.Literals.ECU_RESOURCE_ESTIMATION__SW_COMP_TO_ECU_MAPPINGS)
	}

	/**
	 *
	 * 
	 * constr_3006
	 *
	 * 
	 * valid EcuMapping
	 *
	 * 
	 */
	@Check(constraint="constr_3006", categories=#["SystemTemplate"])
	def void constr_3006(ECUMapping ecuMapping) {
		issuePred(ecuMapping.getCommControllerMappings(),
			[CommunicationControllerMapping x|!ecuMapping.getEcu().getNestedElements().contains(x.getCommunicationController())],
			EcuresourcemappingPackage.Literals.ECU_MAPPING__COMM_CONTROLLER_MAPPINGS)

		issuePred(ecuMapping.getHwPortMappings(),
			[HwPortMapping x|!ecuMapping.getEcu().getNestedElements().contains(x.getHwCommunicationPort())],
			EcuresourcemappingPackage.Literals.ECU_MAPPING__HW_PORT_MAPPINGS)
	}

	/**
	 *
	 * 
	 * constr_3007
	 *
	 * 
	 * DynamicPartAlternative.selectorFieldCodes for dynamic part alternatives
	 *
	 * 
	 */
	//	@Check(constraint="constr_3007",categories=#["SystemTemplate"])
	// def void constr_3007( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3008
	 *
	 * 
	 * EcuInstance subelements
	 *
	 * 
	 */
	@Check(constraint="constr_3008", categories=#["SystemTemplate"])
	def void constr_3008(EcuInstance instance) {
		val comConnectors = instance.getConnectors().filter[CommunicationConnector x|x.getCommController().getEcuInstance() != instance]
		comConnectors.forEach[issue(it, CoretopologyPackage.Literals.ECU_INSTANCE__CONNECTORS, instance.getConnectors().indexOf(it), null)]
	}

	/**
	 *
	 * 
	 * constr_3009
	 *
	 * 
	 * Overlapping of ISignals is prohibited
	 *
	 * 
	 * ISignals mapped to an ISignalIPdu shall not overlap.
	 */
	@Check(constraint="constr_3009", categories=#["SystemTemplate"])
	def void constr_3009(ISignalIPdu signalIPdu) {
		val overlap = overlap(signalIPdu.getISignalToPduMappings(),
			[ISignalToIPduMapping m|m.getStartPosition()],
			[ISignalToIPduMapping m|m.getStartPosition() + m.getISignal().getLength() - 1])
		
		overlap.keys().forEach[issue(signalIPdu, CorecommunicationPackage.Literals.ISIGNAL_IPDU__ISIGNAL_TO_PDU_MAPPINGS,
				signalIPdu.getISignalToPduMappings().indexOf(it), null)]
	}

	/**
	 *
	 * 
	 * constr_3010
	 *
	 * 
	 * ISignalIPdu length shall not be exceeded
	 *
	 * 
	 * The combined length of all ISignals and 
	 * ISignalToIPduMapping.updateIndicationBitPositions that are mapped into an 
	 * ISignalIPdu shall not exceed the defined PduPdu.length.
	 */
	@Check(constraint="constr_3010", categories=#["SystemTemplate"])
	def void constr_3010(ISignalIPdu signalIPdu) {
		val Integer combined = reduce(signalIPdu.getISignalToPduMappings(), 0,
			[ISignalToIPduMapping x, y|x.getISignal().getLength() + y])
			
		if (8 * signalIPdu.getLength() < combined) 
			issue(signalIPdu, CorecommunicationPackage.Literals.PDU__LENGTH)

		issuePred(signalIPdu.getISignalToPduMappings(),
			[ISignalToIPduMapping x|x.getUpdateIndicationBitPosition() > signalIPdu.getLength()],
			CorecommunicationPackage.Literals.PDU__LENGTH)
	}

	/**
	 *
	 * 
	 * constr_3011
	 *
	 * 
	 * Overlapping of updateIndicationBits of ISignals is prohibited
	 *
	 * 
	 * The ISignalToIPduMapping.updateIndicationBitPosition for an ISignal in an 
	 * ISignalIPdu shall not overlap with other 
	 * ISignalToIPduMapping.updateIndicationBitPositions or ISignal locations.
	 */
	//	@Check(constraint="constr_3011",categories=#["SystemTemplate"])
	// def void constr_3011( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3012
	 *
	 * 
	 * Overlapping of Pdus is prohibited
	 *
	 * 
	 * Pdus mapped to a Frame shall NOT overlap.
	 */
	@Check(constraint="constr_3012", categories=#["SystemTemplate"])
	def void constr_3012(Frame frame) {
		val overlap = overlap(frame.getPduToFrameMappings(),
			[PduToFrameMapping m|m.getStartPosition()],
			[PduToFrameMapping m|m.getStartPosition() + m.getPdu().getLength() - 1])
			
		overlap.keys().forEach[issue(frame, CorecommunicationPackage.Literals.FRAME__PDU_TO_FRAME_MAPPINGS,
				frame.getPduToFrameMappings().indexOf(it), null)]
	}

	/**
	 *
	 * 
	 * constr_3013
	 *
	 * 
	 * Frame length shall not be exceeded
	 *
	 * 
	 * The combined length of all Pdus that are mapped into a Frame shall not exceed 
	 * the defined Frame length.
	 */
	@Check(constraint="constr_3013", categories=#["SystemTemplate"])
	def void constr_3013(Frame frame) {
		val Integer combined = reduce(frame.getPduToFrameMappings(), 0, [PduToFrameMapping x, y|x.getPdu().getLength() + y])
		if (frame.getFrameLength() < combined) 
			issue(frame, CorecommunicationPackage.Literals.FRAME__FRAME_LENGTH);

		issuePred(frame.getPduToFrameMappings(),
			[PduToFrameMapping x|x.getUpdateIndicationBitPosition() > frame.getFrameLength()],
			CorecommunicationPackage.Literals.FRAME__FRAME_LENGTH)
	}

	/**
	 *
	 * 
	 * constr_3014
	 *
	 * 
	 * Overlapping of updateIndicationBits for Pdus is prohibited
	 *
	 * 
	 * The PduToFrameMapping.updateIndicationBitPosition for a Pdu in a Frame shall NOT
	 *  overlap with other PduToFrameMapping.updateIndicationBitPositions and Pdu 
	 * locations.
	 */
	//	@Check(constraint="constr_3014",categories=#["SystemTemplate"])
	// def void constr_3014( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3015
	 *
	 * 
	 * Number of LIN channels
	 *
	 * 
	 */
	//	@Check(constraint="constr_3015",categories=#["SystemTemplate"])
	// def void constr_3015( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3018
	 *
	 * 
	 * Number of FlexRay channels
	 *
	 * 
	 */
	@Check(constraint="constr_3018", categories=#["SystemTemplate"])
	def void constr_3018(FlexrayClusterConditional o) {
		if (o.getPhysicalChannels().size() > 2)
			issue(o, CoretopologyPackage.Literals.COMMUNICATION_CLUSTER_CONTENT__PHYSICAL_CHANNELS);

		issuePred(o.getPhysicalChannels(),
			[PhysicalChannel x|!(x.getShortName().equals("channelA") || x.getShortName().equals("channelB"))],
			CoretopologyPackage.Literals.COMMUNICATION_CLUSTER_CONTENT__PHYSICAL_CHANNELS);

		if (o.getPhysicalChannels().size() == 2 &&
			o.getPhysicalChannels().get(0).getShortName().equals(o.getPhysicalChannels().get(1).getShortName()))
			issue(o, CoretopologyPackage.Literals.COMMUNICATION_CLUSTER_CONTENT__PHYSICAL_CHANNELS)
	}

	/**
	 *
	 * 
	 * constr_3019
	 *
	 * 
	 * In the flat ECU extract each required interface must be satisfied by connected 
	 * provided interfaces
	 *
	 * 
	 */
	//	@Check(constraint="constr_3019",categories=#["SystemTemplate"])
	// def void constr_3019( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3020
	 *
	 * 
	 * ISignalIPduGroup.communicationDirection of ISignalIPduGroup.containedIPduGroups
	 *
	 * 
	 */
	@Check(constraint="constr_3020", categories=#["SystemTemplate"])
	def void constr_3020(ISignalIPduGroup signalIPduGroup) {
		issuePred(signalIPduGroup.getContainedISignalIPduGroups(),
			[ISignalIPduGroup m|m.getCommunicationDirection() == signalIPduGroup.getCommunicationDirection()],
			CorecommunicationPackage.Literals.ISIGNAL_IPDU_GROUP__COMMUNICATION_DIRECTION)
	}

	/**
	 *
	 * 
	 * constr_3021
	 *
	 * 
	 * Mapping of SensorActuatorSwComponents to SensorActuator HwElements
	 *
	 * 
	 */
	//	@Check(constraint="constr_3021",categories=#["SystemTemplate"])
	// def void constr_3021( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3024
	 *
	 * 
	 * Usage of TransferPropertyEnum.triggeredWithoutRepetition and 
	 * TransferPropertyEnum.triggeredOnChangeWithoutRepetition is not allowed for 
	 * signal groups and group signals.
	 *
	 * 
	 * The values TransferPropertyEnum.triggeredWithoutRepetition and 
	 * TransferPropertyEnum.triggeredOnChangeWithoutRepetition shall not be used if the
	 *  ISignalToIPduMapping refers to an ISignalGroup or an ISignal which is part of 
	 * an ISignalGroup (group signal).
	 */
	//	@Check(constraint="constr_3024",categories=#["SystemTemplate"])
	// def void constr_3024( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3025
	 *
	 * 
	 * Usage of NPdus in TpConnections
	 *
	 * 
	 */
	//	@Check(constraint="constr_3025",categories=#["SystemTemplate"])
	// def void constr_3025( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3027
	 *
	 * 
	 * Existence of ecuExtractVersion
	 *
	 * 
	 */
	@Check(constraint="constr_3027", categories=#["SystemTemplate"])
	def void constr_3027(System system) {
		if ((system.getCategory().equals("SYSTEM_EXTRACT") || system.getCategory().equals("ECU_EXTRACT")) &&
			!system.isSetEcuExtractVersion()) {
			issue(system, SystemPackage.Literals.SYSTEM__ECU_EXTRACT_VERSION)
		}
	}

	/**
	 *
	 * 
	 * constr_3028
	 *
	 * 
	 * FibexElements
	 *
	 * 
	 * Each FibexElement that is used in the System Description shall be referenced by 
	 * the System element in the role FibexElement.
	 */
	//	@Check(constraint="constr_3028",categories=#["SystemTemplate"])
	// def void constr_3028( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3029
	 *
	 * 
	 * Assign-Frame command usage
	 *
	 * 
	 * For the LIN 2.0 Assign-Frame command the LinConfigurableFrame list shall be 
	 * used. For the LIN 2.1 Assign-Frame-PID-Range command the 
	 * LinOrderedConfigurableFrame list shall be used.
	 */
	//	@Check(constraint="constr_3029",categories=#["SystemTemplate"])
	// def void constr_3029( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3030
	 *
	 * 
	 * valid relationship between ECUMapping and EcuInstance
	 *
	 * 
	 * If an EcuInstance is assigned to a HwElement the EcuInstance shall belong to the
	 *  same System as the ECUMapping.
	 */
	//	@Check(constraint="constr_3030",categories=#["SystemTemplate"])
	// def void constr_3030( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3031
	 *
	 * 
	 * Complete System Description does not have ports
	 *
	 * 
	 * In a complete System with Identifiable.categoryABSTRACT_SYSTEM_DESCRIPTION or 
	 * System with Identifiable.categorySYSTEM_DESCRIPTION this outermost 
	 * CompositionSwComponentType has the unique feature that it doesn't have any 
	 * outside ports, but all the SWC contained in it are connected to each other and 
	 * fully specified by their SwComponentTypes, PortPrototypes, PortInterfaces, 
	 * VariableDataPrototypes, InternalBehavior etc.
	 */
	@Check(constraint="constr_3031", categories=#["SystemTemplate"])
	def void constr_3031(System system) {
		if (system.getCategory().equals("ABSTRACT_SYSTEM_DESCRIPTION") || system.getCategory().equals("SYSTEM_DESCRIPTION")) {
			issuePred(system.getRootSoftwareCompositions(),
				[RootSwCompositionPrototype x|!x.getSoftwareComposition().getPorts().isEmpty()],
				SystemPackage.Literals.SYSTEM__ROOT_SOFTWARE_COMPOSITIONS)
		}
	}

	/**
	 *
	 * 
	 * constr_3032
	 *
	 * 
	 * Combinations of SwcToEcuMapping targets
	 *
	 * 
	 * For each combination of EcuInstance and the optional HwElement.processingUnit 
	 * and the optional HwElement.partition and the optional 
	 * HwElement.controlledHwElement one SwcToEcuMapping shall be used.
	 */
	//	@Check(constraint="constr_3032",categories=#["SystemTemplate"])
	// def void constr_3032( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3034
	 *
	 * 
	 * Values of LinSlaveConfig and LinSlave attributes
	 *
	 * 
	 * The values of attributes of LinSlaveConfig and LinSlave shall be identical for 
	 * each LinSlaveConfig that points to a LinSlave.
	 */
	//	@Check(constraint="constr_3034",categories=#["SystemTemplate"])
	// def void constr_3034( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3035
	 *
	 * 
	 * CanNm user data configuration in case NID/CBV are enabled
	 *
	 * 
	 * If NID/CBV are enabled (CanNmCluster.nmCbvPosition and 
	 * CanNmCluster.nmNidPosition are configured), there shall not be any user data 
	 * configured at the position of the respective NID/CBV bytes.
	 */
	//	@Check(constraint="constr_3035",categories=#["SystemTemplate"])
	// def void constr_3035( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3036
	 *
	 * 
	 * Pdus in CAN and LIN Frames
	 *
	 * 
	 * CAN Frames and LIN Frames shall only contain one Pdu.
	 */
	@Check(constraint="constr_3036", categories=#["SystemTemplate"])
	def void constr_3036(CanFrame canFrame) {
		if (canFrame.getPduToFrameMappings().size() > 1) 
			issue(canFrame, CorecommunicationPackage.Literals.FRAME__PDU_TO_FRAME_MAPPINGS)
	}

	@Check(constraint="constr_3036", categories=#["SystemTemplate"])
	def void constr_3036b(LinFrame linFrame) {
		if (linFrame.getPduToFrameMappings().size() > 1) 
			issue(linFrame, CorecommunicationPackage.Literals.FRAME__PDU_TO_FRAME_MAPPINGS)
	}

	/**
	 *
	 * 
	 * constr_3037
	 *
	 * 
	 * maximum FrameFrame.frameLength for CAN and LIN
	 *
	 * 
	 * For CAN and LIN the maximum Frame.frameLength is 8 bytes and 64 bytes in case of
	 *  CAN FD.
	 */
	@Check(constraint="constr_3037", categories=#["SystemTemplate"])
	def void constr_3037(CanFrame canFrame) {
		if (canFrame.getFrameLength() > 8) 
			issue(canFrame, CorecommunicationPackage.Literals.FRAME__FRAME_LENGTH)
	}

	/**
	 *
	 * 
	 * constr_3038
	 *
	 * 
	 * maximum FrameFrame.frameLength for FlexRay
	 *
	 * 
	 * For FlexRay the maximum Frame.frameLength is 254 bytes.
	 */
	@Check(constraint="constr_3038", categories=#["SystemTemplate"])
	def void constr_3038(FlexrayFrame flexrayFrame) {
		if (flexrayFrame.getFrameLength() > 254) 
			issue(flexrayFrame, CorecommunicationPackage.Literals.FRAME__FRAME_LENGTH)
	}

	/**
	 *
	 * 
	 * constr_3039
	 *
	 * 
	 * PncMapping.pncIdentifier range
	 *
	 * 
	 * The PncMapping.pncIdentifier value shall be in the range of 8..63.
	 */
	//	@Check(constraint="constr_3039",categories=#["SystemTemplate"])
	// def void constr_3039( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3040
	 *
	 * 
	 * Restriction of PncMapping.pncIdentifier values
	 *
	 * 
	 * The PncMapping.pncIdentifier value shall be within the range described by 
	 * System.pncVectorOffset and System.pncVectorLength.
	 */
	//	@Check(constraint="constr_3040",categories=#["SystemTemplate"])
	// def void constr_3040( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3041
	 *
	 * 
	 * System.pncVectorOffset range
	 *
	 * 
	 * The System.pncVectorOffset value shall be in the range of 1..7.
	 */
	//	@Check(constraint="constr_3041",categories=#["SystemTemplate"])
	// def void constr_3041( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3042
	 *
	 * 
	 * System.pncVectorLength range
	 *
	 * 
	 * The System.pncVectorLength value shall be in the range of 1..6.
	 */
	//	@Check(constraint="constr_3042",categories=#["SystemTemplate"])
	// def void constr_3042( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3043
	 *
	 * 
	 * pncVector configuration in AUTOSAR Com
	 *
	 * 
	 * The pncVector shall be configured as UINT8_N signal in AUTOSAR Com.
	 */
	//	@Check(constraint="constr_3043",categories=#["SystemTemplate"])
	// def void constr_3043( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3044
	 *
	 * 
	 * CBV configuration in case partial network is used
	 *
	 * 
	 * In case a partial network is used the control bit vector (CBV) shall be defined 
	 * in Byte 0 of the NmPdu (CanNmCluster.nmCbvPosition = 0).
	 */
	//	@Check(constraint="constr_3044",categories=#["SystemTemplate"])
	// def void constr_3044( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3045
	 *
	 * 
	 * Signal content evaluation vs. Mode evaluation
	 *
	 * 
	 * The mode evaluation and the signal content evaluation shall not be used in the 
	 * same IPdu. A mix of these two types is not allowed.
	 */
	//	@Check(constraint="constr_3045",categories=#["SystemTemplate"])
	// def void constr_3045( IPdu o ) {
	//		if( o.get )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3046
	 *
	 * 
	 * Consistency of TransmissionModeCondition.TransmissionModeCondition.iSignalInIPdu
	 *
	 * 
	 * The ISignalToIPduMapping referenced by the TransmissionModeCondition in the role
	 *  TransmissionModeCondition.iSignalInIPdu shall belong to the same ISignalIPdu as
	 *  the TransmissionModeCondition.
	 */
	//	@Check(constraint="constr_3046",categories=#["SystemTemplate"])
	// def void constr_3046( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3047
	 *
	 * 
	 * Uniqueness of MacMulticastGroup.macMulticastAddresses
	 *
	 * 
	 * A MacMulticastGroup.macMulticastAddress shall be unique in a particular 
	 * EthernetCluster.
	 */
	//	@Check(constraint="constr_3047",categories=#["SystemTemplate"])
	// def void constr_3047( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3048
	 *
	 * 
	 * Range of VlanConfig.vlanIdentifier
	 *
	 * 
	 * The allowed values of VlanConfig.vlanIdentifier range from 0 to 4095.
	 */
	//	@Check(constraint="constr_3048",categories=#["SystemTemplate"])
	// def void constr_3048( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3049
	 *
	 * 
	 * Role of SystemSignal in inter-ECU client server communication with clients 
	 * located on different ECUs
	 *
	 * 
	 */
	//	@Check(constraint="constr_3049",categories=#["SystemTemplate"])
	// def void constr_3049( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3050
	 *
	 * 
	 * J1939Cluster uses exactly one CanPhysicalChannel
	 *
	 * 
	 * A J1939Cluster shall aggregate exactly one CanPhysicalChannel.
	 */
	//	@Check(constraint="constr_3050",categories=#["SystemTemplate"])
	// def void constr_3050( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3051
	 *
	 * 
	 * Restriction of ISignalMapping references
	 *
	 * 
	 * If the ISignalMapping.sourceSignal references an ISignal then the  
	 * ISignalMapping.targetSignal shall also reference an ISignal.
	 */
	@Check(constraint="constr_3051", categories=#["SystemTemplate"])
	def void constr_3051(ISignalMapping signalMapping) {
		if (signalMapping.getSourceSignal().getISignal() != null && signalMapping.getTargetSignal().getISignal() == null) 
			issue(signalMapping, Fibex4multiplatformPackage.Literals.ISIGNAL_MAPPING__TARGET_SIGNAL)
	}

	/**
	 *
	 * 
	 * constr_3052
	 *
	 * 
	 * Complete ISignalMapping of ISignalGroup signals
	 *
	 * 
	 * If an ISignalMapping to an ISignal that is a member of a ISignalGroup exists 
	 * then an ISignalMapping to the enclosing ISignalGroup shall exist as well.
	 */
	//	@Check(constraint="constr_3052",categories=#["SystemTemplate"])
	// def void constr_3052( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3053
	 *
	 * 
	 * Complete ISignalMapping of target ISignalGroup
	 *
	 * 
	 * If an ISignalGroup is referenced by a ISignalMapping.targetSignal there shall 
	 * exist either an explicit or an implicit mapping (see TPS_SYST_01120 for each 
	 * contained ISignal of that ISignalGroup.
	 */
	//	@Check(constraint="constr_3053",categories=#["SystemTemplate"])
	// def void constr_3053( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3055
	 *
	 * 
	 * SystemSignalGroup in a complete System Description
	 *
	 * 
	 * For each SystemSignalGroup in a complete System with 
	 * Identifiable.categorySYSTEM_DESCRIPTION exactly one DataMapping shall be defined
	 *  (PPortPrototype or RPortPrototype). Preference: PPortPrototype
	 */
	//	@Check(constraint="constr_3055",categories=#["SystemTemplate"])
	// def void constr_3055( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3057
	 *
	 * 
	 * Maximal one BusspecificNmEcu per NmEcu and bus system is allowed to be defined
	 *
	 * 
	 * For each NmEcu at most one BusspecificNmEcu per bus system 
	 * (FlexRay/Can/Udp/J1939) is allowed to be defined.
	 */
	//	@Check(constraint="constr_3057",categories=#["SystemTemplate"])
	// def void constr_3057( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3058
	 *
	 * 
	 * References from SenderRecArrayElementMapping and from 
	 * SenderRecRecordElementMapping to SystemSignals are not allowed within a 
	 * SenderReceiverCompositeElementToSignalMapping
	 *
	 * 
	 * The reference from SenderRecArrayElementMapping to SystemSignal and from 
	 * SenderRecRecordElementMapping to SystemSignal shall not exist if the enclosing 
	 * SenderRecCompositeTypeMapping is owned by a 
	 * SenderReceiverCompositeElementToSignalMapping.
	 */
	//	@Check(constraint="constr_3058",categories=#["SystemTemplate"])
	// def void constr_3058( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3059
	 *
	 * 
	 * Mandatory DataMapping on the receiver side for elements of a composite data type
	 *
	 * 
	 * On the receiver side, it is required that for every 
	 * ApplicationCompositeElementDataPrototype of a ApplicationCompositeDataType  
	 * (ApplicationCompositeDataType.ApplicationCompositeElementDataPrototype.element) 
	 * that types a SenderReceiverInterface.dataElement in a RPortPrototype or 
	 * PRPortPrototype in its receiver role a DataMapping exists.
	 */
	//	@Check(constraint="constr_3059",categories=#["SystemTemplate"])
	// def void constr_3059( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3060
	 *
	 * 
	 * Usage of ISignal.networkRepresentationProps and SystemSignal.physicalProps
	 *
	 * 
	 * Usage of ISignal.networkRepresentationProps and SystemSignal.physicalProps shall
	 *  follow the restrictions given in table~table:SwDataDefPropsForSignals.
	 */
	//	@Check(constraint="constr_3060",categories=#["SystemTemplate"])
	// def void constr_3060( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3061
	 *
	 * 
	 * CompuMethod specification in ISignal.networkRepresentationProps
	 *
	 * 
	 * A CompuMethod that is defined in the ISignal.networkRepresentationProps for the 
	 * ISignal shall be compatible to the CompuMethod that is defined in the 
	 * SystemSignal.physicalProps for the SystemSignal that is referenced by the 
	 * ISignal.
	 */
	//	@Check(constraint="constr_3061",categories=#["SystemTemplate"])
	// def void constr_3061( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3062
	 *
	 * 
	 * The EcuInstance that is referenced from a specific CouplingElement shall be 
	 * connected to the same EthernetCluster as the specific CouplingElement
	 *
	 * 
	 * The EcuInstance referenced from a specific CouplingElement in the role 
	 * CouplingElement.ecuInstance shall be connected via the CommunicationConnector 
	 * and a EthernetPhysicalChannel that refers the CommunicationConnector to the 
	 * EthernetCluster referenced by the specific CouplingElement in the role 
	 * CouplingElement.communicationCluster.
	 */
	//	@Check(constraint="constr_3062",categories=#["SystemTemplate"])
	// def void constr_3062( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3063
	 *
	 * 
	 * Usage of TpPort.portNumber and TpPort.dynamicallyAssigned with value ``true'' is
	 *  mutually exclusive
	 *
	 * 
	 * Usage of TpPort.portNumber and TpPort.dynamicallyAssigned with value ``true'' is
	 *  mutually exclusive.
	 */
	@Check(constraint="constr_3063", categories=#["SystemTemplate"])
	def void constr_3063(TpPort tpPort) {
		if (tpPort.isSetPortNumber() && tpPort.isSetDynamicallyAssigned()) 
			issue(tpPort, EthernettopologyPackage.Literals.TP_PORT__DYNAMICALLY_ASSIGNED)
	}

	/**
	 *
	 * 
	 * constr_3064
	 *
	 * 
	 * Usage of DataMapping.serviceInstance, DataMapping.eventHandler and 
	 * DataMapping.eventGroup references
	 *
	 * 
	 * The DataMapping.serviceInstance, DataMapping.eventHandler and 
	 * DataMapping.eventGroup references shall only be used to describe a service based
	 *  communication over the Internet Protocol. More details are described in chapter
	 *  sec:EthernetCommunication.
	 */
	//	@Check(constraint="constr_3064",categories=#["SystemTemplate"])
	// def void constr_3064( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3065
	 *
	 * 
	 * Mapping of queued Triggers to SystemSignals is prohibited
	 *
	 * 
	 * A TriggerToSignalMapping of a Trigger with Trigger.swImplPolicy set to 
	 * SwImplPolicyEnum.queued is prohibited.
	 */
	//	@Check(constraint="constr_3065",categories=#["SystemTemplate"])
	// def void constr_3065(TriggerToSignalMapping o ) {
	//		if( o.getTrigger(). )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3067
	 *
	 * 
	 * ISignal.initValue defined in the context of ISignal
	 *
	 * 
	 * The definition of an ISignal.initValue in the context of an ISignal can only be 
	 * a primitive NumericalValueSpecification or TextValueSpecification.
	 */
	@Check(constraint="constr_3067", categories=#["SystemTemplate"])
	def void constr_3067(ISignal signal) {
		if (signal.getInitValue() != null && !(signal.getInitValue() instanceof NumericalValueSpecification ||
			signal.getInitValue() instanceof TextValueSpecification)) 
			issue(signal, CorecommunicationPackage.Literals.ISIGNAL__INIT_VALUE)
	}

	/**
	 *
	 * 
	 * constr_3068
	 *
	 * 
	 * DoIpPowerModeStatusNeeds in the Identifiable.categoryECU_EXTRACT
	 *
	 * 
	 * If and only if DoIP (i.e. any of the subclasses of DoIpServiceNeeds are present)
	 *  is used on an Ecu then the DoIpPowerModeStatusNeeds shall exist exactly once in
	 *  a System of Identifiable.categoryECU_EXTRACT.
	 */
	//	@Check(constraint="constr_3068",categories=#["SystemTemplate"])
	// def void constr_3068( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3069
	 *
	 * 
	 * Allowed CanNmCluster.CanNmCluster.nmNidPosition values
	 *
	 * 
	 * The value of CanNmCluster.CanNmCluster.nmNidPosition shall only be set to either
	 *  bit 0 (byte 0) or bit 8 (byte 1).
	 */
	//	@Check(constraint="constr_3069",categories=#["SystemTemplate"])
	// def void constr_3069( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3070
	 *
	 * 
	 * Allowed CanNmCluster.CanNmCluster.nmCbvPosition values
	 *
	 * 
	 * The value of CanNmCluster.CanNmCluster.nmCbvPosition shall only be set to either
	 *  bit 0 (byte 0) or bit 8 (byte 1).
	 */
	//	@Check(constraint="constr_3070",categories=#["SystemTemplate"])
	// def void constr_3070( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3071
	 *
	 * 
	 * CanNmCluster.CanNmCluster.nmCbvPosition and 
	 * CanNmCluster.CanNmCluster.nmNidPosition shall never have the same value
	 *
	 * 
	 * CanNmCluster.CanNmCluster.nmCbvPosition and 
	 * CanNmCluster.CanNmCluster.nmNidPosition shall never have the same value.
	 */
	@Check(constraint="constr_3071", categories=#["SystemTemplate"])
	def void constr_3071(CanNmCluster canNmCluster) {
		if (canNmCluster.getNmCbvPosition() == canNmCluster.getNmNidPosition()) 
			issue(canNmCluster, NetworkmanagementPackage.Literals.CAN_NM_CLUSTER__NM_CBV_POSITION)
	}

	/**
	 *
	 * 
	 * constr_3073
	 *
	 * 
	 * NmPdu.nmVoteInformation only valid for FrNm
	 *
	 * 
	 * The NmPdu.nmVoteInformation attribute is only valid for FrNm.
	 */
	//	@Check(constraint="constr_3073",categories=#["SystemTemplate"])
	// def void constr_3073( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3074
	 *
	 * 
	 * No TransmissionAcknowledgementRequest for multiple senders
	 *
	 * 
	 * If more than one SenderComSpec exist (in different PortPrototypes on atomic 
	 * level) that refer to data elements effectively mapped to the same SystemSignal 
	 * it is not allowed that any SenderComSpec aggregates 
	 * SenderComSpec.transmissionAcknowledge.
	 */
	//	@Check(constraint="constr_3074",categories=#["SystemTemplate"])
	// def void constr_3074( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3078
	 *
	 * 
	 * Allowed UdpNmCluster.UdpNmCluster.nmNidPosition values
	 *
	 * 
	 * The value of UdpNmCluster.UdpNmCluster.nmNidPosition shall only be set to either
	 *  bit 0 (byte 0) or bit 8 (byte 1).
	 */
	//	@Check(constraint="constr_3078",categories=#["SystemTemplate"])
	// def void constr_3078( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3079
	 *
	 * 
	 * Allowed UdpNmCluster.UdpNmCluster.nmCbvPosition values
	 *
	 * 
	 * The value of UdpNmCluster.UdpNmCluster.nmCbvPosition shall only be set to either
	 *  bit 0 (byte 0) or bit 8 (byte 1).
	 */
	//	@Check(constraint="constr_3079",categories=#["SystemTemplate"])
	// def void constr_3079( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3080
	 *
	 * 
	 * UdpNmCluster.UdpNmCluster.nmCbvPosition and 
	 * UdpNmCluster.UdpNmCluster.nmNidPosition shall never have the same value
	 *
	 * 
	 * UdpNmCluster.UdpNmCluster.nmCbvPosition and 
	 * UdpNmCluster.UdpNmCluster.nmNidPosition shall never have the same value.
	 */
	//	@Check(constraint="constr_3080",categories=#["SystemTemplate"])
	// def void constr_3080( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3081
	 *
	 * 
	 * Value of category in GeneralPurposePdu
	 *
	 * 
	 * The attribute Identifiable.category of GeneralPurposePdu can have the following 
	 * values:
	 */
	//	@Check(constraint="constr_3081",categories=#["SystemTemplate"])
	// def void constr_3081( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3082
	 *
	 * 
	 * Value of category in GeneralPurposeIPdu
	 *
	 * 
	 * The attribute Identifiable.category of GeneralPurposeIPdu can have the following
	 *  values:
	 */
	//	@Check(constraint="constr_3082",categories=#["SystemTemplate"])
	// def void constr_3082( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3083
	 *
	 * 
	 * Exactly one AtomicSwComponentType on an EcuInstance may use 
	 * GeneralCallbackEventDataChanged / GeneralCallbackEventStatusChange
	 *
	 * 
	 * The Dem only supports exactly one AtomicSwComponentType using 
	 * GeneralCallbackEventDataChanged / GeneralCallbackEventStatusChange on one 
	 * EcuInstance.
	 */
	//	@Check(constraint="constr_3083",categories=#["SystemTemplate"])
	// def void constr_3083( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3084
	 *
	 * 
	 * Service port in the role PowerTakeOff
	 *
	 * 
	 * Within the context of one EcuInstance, there can only be one service port that 
	 * uses the role PowerTakeOff in the 
	 * RoleBasedPortAssignment.RoleBasedPortAssignment.role.
	 */
	//	@Check(constraint="constr_3084",categories=#["SystemTemplate"])
	// def void constr_3084( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3085
	 *
	 * 
	 * Service port in the role CallbackDCMRequestServices
	 *
	 * 
	 * Within the context of one EcuInstance, there can only be one service port that 
	 * uses the role CallbackDCMRequestServices in the 
	 * RoleBasedPortAssignment.RoleBasedPortAssignment.role.
	 */
	//	@Check(constraint="constr_3085",categories=#["SystemTemplate"])
	// def void constr_3085( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3086
	 *
	 * 
	 * Role of SystemSignal in n:1 sender-receiver communication
	 *
	 * 
	 */
	//	@Check(constraint="constr_3086",categories=#["SystemTemplate"])
	// def void constr_3086( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3087
	 *
	 * 
	 * DataMapping to PRPortPrototype
	 *
	 * 
	 * For inter-ECU communication between SwComponentPrototypes which involves 
	 * PRPortPrototypes for each DataPrototype there shall be one SystemSignal and at 
	 * most two DataMappings, one for each direction.
	 */
	//	@Check(constraint="constr_3087",categories=#["SystemTemplate"])
	// def void constr_3087( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3088
	 *
	 * 
	 * SystemSignal that is not part of a SystemSignalGroup in a complete System 
	 * Description
	 *
	 * 
	 * For each SystemSignal that is not part of a SystemSignalGroup in a complete 
	 * System with Identifiable.categorySYSTEM_DESCRIPTION exactly one DataMapping per 
	 * DataMapping.communicationDirection shall be defined (PPortPrototype, 
	 * RPortPrototype, PRPortPrototype). Preference: AbstractProvidedPortPrototype
	 */
	//	@Check(constraint="constr_3088",categories=#["SystemTemplate"])
	// def void constr_3088( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3089
	 *
	 * 
	 * SystemSignal that is part of exactly one SystemSignalGroup and is not 
	 * transmitted additionally as standalone SystemSignal in a complete System 
	 * Description
	 *
	 * 
	 * For each SystemSignal that is part of exactly one SystemSignalGroup and is not 
	 * transmitted additionally as standalone SystemSignal in a complete System with 
	 * Identifiable.categorySYSTEM_DESCRIPTION exactly one DataMapping per 
	 * DataMapping.communicationDirection shall be defined (PPortPrototype, 
	 * RPortPrototype, PRPortPrototype). Preference: AbstractProvidedPortPrototype
	 */
	//	@Check(constraint="constr_3089",categories=#["SystemTemplate"])
	// def void constr_3089( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3090
	 *
	 * 
	 * TpSdu transmission on a PhysicalChannel
	 *
	 * 
	 * The IPdu that is referenced by a TpConnection in the role tpSdu shall be 
	 * referenced by exactly one PduTriggering aggregated on the PhysicalChannel of the
	 *  TpConnection.
	 */
	//	@Check(constraint="constr_3090",categories=#["SystemTemplate"])
	// def void constr_3090( TpConnection o ) {
	//		if( o.getIdent().get )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3094
	 *
	 * 
	 * Consistent ISignalPort.CommConnectorPort.communicationDirection for 
	 * ISignalTriggerings of ISignalGroups and contained ISignals
	 *
	 * 
	 * In case the ISignals contained in an ISignalGroup are referenced by an 
	 * ISignalTriggering, the CommConnectorPort.communicationDirection of the  
	 * ISignalPort referenced by the ISignal's ISignalTriggering shall be identical to 
	 * the CommConnectorPort.communicationDirection of the ISignalPort referenced by 
	 * the containing ISignalGroup's ISignalTriggering.
	 */
	//	@Check(constraint="constr_3094",categories=#["SystemTemplate"])
	// def void constr_3094( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3095
	 *
	 * 
	 * AbstractCanCommunicationControllerAttributes.canControllerFdAttributes and 
	 * AbstractCanCommunicationControllerAttributes.canControllerFdRequirements are 
	 * mutually exclusive.
	 *
	 * 
	 * AbstractCanCommunicationControllerAttributes.canControllerFdAttributes and 
	 * AbstractCanCommunicationControllerAttributes.canControllerFdRequirements are 
	 * mutually exclusive.
	 */
	@Check(constraint="constr_3095", categories=#["SystemTemplate"])
	def void constr_3095(AbstractCanCommunicationControllerAttributes attributes) {
		if (attributes.getCanControllerFdAttributes() != null && attributes.getCanControllerFdRequirements() != null)
			issue(attributes, CantopologyPackage.Literals.ABSTRACT_CAN_COMMUNICATION_CONTROLLER_ATTRIBUTES__CAN_CONTROLLER_FD_ATTRIBUTES)
	}

	/**
	 *
	 * 
	 * constr_3096
	 *
	 * 
	 * Allowed values for J1939DcmIPdu.diagnosticMessageType
	 *
	 * 
	 * The allowed values of J1939DcmIPdu.diagnosticMessageType range from 1..57.
	 */
	//	@Check(constraint="constr_3096",categories=#["SystemTemplate"])
	// def void constr_3096( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3097
	 *
	 * 
	 * Overlapping of segments of one MultiplexedIPdu is not allowed
	 *
	 * 
	 * The segments defined by the SegmentPosition elements of one and the same 
	 * MultiplexedIPdu - aggregated via StaticPart and DynamicPart - shall not overlap.
	 */
	//	@Check(constraint="constr_3097",categories=#["SystemTemplate"])
	// def void constr_3097( MultiplexedIPdu o ) {
	//		
	//	}
	/**
	 *
	 * 
	 * constr_3098
	 *
	 * 
	 * Defined segments of one MultiplexedIPdu shall not exceed the length of the 
	 * MultiplexedIPdu
	 *
	 * 
	 * The segments defined by the SegmentPosition elements of one and the same 
	 * MultiplexedIPdu - aggregated via StaticPart and DynamicPart - shall not exceed 
	 * the length of the MultiplexedIPdu.
	 */
	//	@Check(constraint="constr_3098",categories=#["SystemTemplate"])
	// def void constr_3098(MultiplexedIPdu o ) {
	//		Iterable<MultiplexedPart> concat = Iterables.concat(o.getDynamicParts(),o.getStaticParts());
	//		Integer length = reduce(concat,0,(MultiplexedPart x,Integer y)->(Integer)(x.getLength()+y));
	//		if(length > o.getLength())
	//			issue(o,CorecommunicationPackage.Literals.PDU__LENGTH);
	//	}
	/**
	 *
	 * 
	 * constr_3099
	 *
	 * 
	 * Defined segments in a DynamicPart shall not exceed the length of any 
	 * DynamicPartAlternative.DynamicPartAlternative.iPdu
	 *
	 * 
	 * The segments defined by the SegmentPosition elements aggregated in the 
	 * DynamicPart of a MultiplexedIPdu shall not exceed the length of any 
	 * DynamicPartAlternative.DynamicPartAlternative.iPdu.
	 */
	//	@Check(constraint="constr_3099",categories=#["SystemTemplate"])
	// def void constr_3099( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3100
	 *
	 * 
	 * Defined segments in a StaticPart shall not exceed the length of the 
	 * StaticPart.StaticPart.iPdu
	 *
	 * 
	 * The segments defined by the SegmentPosition elements aggregated in the 
	 * StaticPart of a MultiplexedIPdu shall not exceed the length of the 
	 * StaticPart.StaticPart.iPdu
	 */
	//	@Check(constraint="constr_3100",categories=#["SystemTemplate"])
	// def void constr_3100( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3101
	 *
	 * 
	 * Signal representation of selector field for DynamicPartAlternative
	 *
	 * 
	 * Every ISignalIPdu that is referenced by the DynamicPartAlternative shall contain
	 *  an ISignal that represents the selector field. The selector field signal shall 
	 * be located at the position that is described by the 
	 * MultiplexedIPdu.selectorFieldLength and 
	 * MultiplexedIPdu.selectorFieldStartPosition.
	 */
	//	@Check(constraint="constr_3101",categories=#["SystemTemplate"])
	// def void constr_3101( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3102
	 *
	 * 
	 * Restriction on usage of J1939NodeName attributes
	 *
	 * 
	 * A J1939NmCluster shall not aggregate two J1939NmNodes with identical 
	 * J1939NodeName attributes.
	 */
	//	@Check(constraint="constr_3102",categories=#["SystemTemplate"])
	// def void constr_3102( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3103
	 *
	 * 
	 * Range of J1939NodeName.ecuInstance
	 *
	 * 
	 * The allowed values of J1939NodeName.ecuInstance range from 0 to 7.
	 */
	//	@Check(constraint="constr_3103",categories=#["SystemTemplate"])
	// def void constr_3103( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3104
	 *
	 * 
	 * Range of J1939NodeName.function
	 *
	 * 
	 * The allowed values of J1939NodeName.function range from 0 to 255.
	 */
	//	@Check(constraint="constr_3104",categories=#["SystemTemplate"])
	// def void constr_3104( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3105
	 *
	 * 
	 * Range of J1939NodeName.functionInstance
	 *
	 * 
	 * The allowed values of J1939NodeName.functionInstance range from 0 to 31.
	 */
	//	@Check(constraint="constr_3105",categories=#["SystemTemplate"])
	// def void constr_3105( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3106
	 *
	 * 
	 * Range of J1939NodeName.identitiyNumber
	 *
	 * 
	 * The allowed values of J1939NodeName.identitiyNumber range from 0 to 2097151.
	 */
	//	@Check(constraint="constr_3106",categories=#["SystemTemplate"])
	// def void constr_3106( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3107
	 *
	 * 
	 * Range of J1939NodeName.industryGroup
	 *
	 * 
	 * The allowed values of J1939NodeName.industryGroup range from 0 to 7.
	 */
	//	@Check(constraint="constr_3107",categories=#["SystemTemplate"])
	// def void constr_3107( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3108
	 *
	 * 
	 * Range of J1939NodeName.manufacturerCode
	 *
	 * 
	 * The allowed values of J1939NodeName.manufacturerCode range from 0 to 2047.
	 */
	//	@Check(constraint="constr_3108",categories=#["SystemTemplate"])
	// def void constr_3108( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3109
	 *
	 * 
	 * Range of J1939NodeName.vehicleSystem
	 *
	 * 
	 * The allowed values of J1939NodeName.vehicleSystem range from 0 to 127.
	 */
	//	@Check(constraint="constr_3109",categories=#["SystemTemplate"])
	// def void constr_3109( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3110
	 *
	 * 
	 * Range of J1939NodeName.vehicleSystemInstance
	 *
	 * 
	 * The allowed values of J1939NodeName.vehicleSystemInstance range from 0 to 15.
	 */
	//	@Check(constraint="constr_3110",categories=#["SystemTemplate"])
	// def void constr_3110( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3111
	 *
	 * 
	 * ClientServerToSignalMapping.returnSignal in ClientServerToSignalMapping is 
	 * mandatory
	 *
	 * 
	 * A ClientServerToSignalMapping shall always have a 
	 * ClientServerToSignalMapping.returnSignal defined.
	 */
	@Check(constraint="constr_3111", categories=#["SystemTemplate"])
	def void constr_3111(ClientServerToSignalMapping mapping) {
		if (mapping.getReturnSignal() == null) 
			issue(mapping, DatamappingPackage.Literals.CLIENT_SERVER_TO_SIGNAL_MAPPING__RETURN_SIGNAL)
	}

	/**
	 *
	 * 
	 * constr_3112
	 *
	 * 
	 * Invalidation support for partial mapping of a data element typed by composite 
	 * data type
	 *
	 * 
	 * If a VariableDataPrototype with a composite data type in a PPortPrototype is 
	 * mapped to a SystemSignalGroup and only a subset of elements of the composite 
	 * data type that are primitives is mapped to separate SystemSignals of the 
	 * SystemSignalGroup then at least one mapped primitive shall have an 
	 * SwDataDefProps.invalidValue defined.
	 */
	//	@Check(constraint="constr_3112",categories=#["SystemTemplate"])
	// def void constr_3112( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3113
	 *
	 * 
	 * EthernetFrame shall not have a PduToFrameMapping
	 *
	 * 
	 * It is not allowed to map Pdus into EthernetFrames.
	 */
	@Check(constraint="constr_3113", categories=#["SystemTemplate"])
	def void constr_3113(EthernetFrame ethernetFrame) {
		if (!ethernetFrame.getPduToFrameMappings().isEmpty()) 
			issue(ethernetFrame, CorecommunicationPackage.Literals.FRAME__PDU_TO_FRAME_MAPPINGS)
	}

	/**
	 *
	 * 
	 * constr_3114
	 *
	 * 
	 * FlatInstanceDescriptors pointing to the same ParameterDataPrototype shall have 
	 * different VariationPoint.postBuildVariantConditions
	 *
	 * 
	 * FlatInstanceDescriptors that are pointing as an atpTarget to the same 
	 * ParameterDataPrototype instance shall have different 
	 * VariationPoint.postBuildVariantConditions.
	 */
	//	@Check(constraint="constr_3114",categories=#["SystemTemplate"])
	// def void constr_3114( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3115
	 *
	 * 
	 * FlatInstanceDescriptors pointing to the same ParameterDataPrototype instance
	 *
	 * 
	 * When several FlatInstanceDescriptors point to the same ParameterDataPrototype in
	 * stance as an atpTarget in the context of a ParameterInterface the different Flat
	 * InstanceDescriptors shall point to the PPortPrototype of the owning ParameterSwC
	 * omponentType. 
	 In this case the PPortPrototype typed by the ParameterInterface 
	 * is part of the context of the according AnyInstanceRef.
	 */
	//	@Check(constraint="constr_3115",categories=#["SystemTemplate"])
	// def void constr_3115( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3116
	 *
	 * 
	 * Overlap of ClientIdRanges in the context of the enclosing System
	 *
	 * 
	 * The ClientIdRange defined for an EcuInstance shall not overlap with the 
	 * ClientIdRange of any other EcuInstance in the context of the enclosing System.
	 */
	//	@Check(constraint="constr_3116",categories=#["SystemTemplate"])
	// def void constr_3116( System o ) {
	//	
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3117
	 *
	 * 
	 * Allowed value of attribute ClientIdDefinition.clientId
	 *
	 * 
	 * Within the context of one ClientIdDefinition, the value of attribute 
	 * ClientIdDefinition.clientId shall be in the range of 
	 * ClientIdRange.ClientIdRange.lowerLimit and 
	 * ClientIdRange.ClientIdRange.upperLimit for the ClientIdRange that is aggregated 
	 * by the EcuInstance onto which the SwComponentPrototypes included in the 
	 * ClientIdDefinition.ClientIdDefinition.clientServerOperation are mapped.
	 */
	//	@Check(constraint="constr_3117",categories=#["SystemTemplate"])
	// def void constr_3117( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3118
	 *
	 * 
	 * Valid reference target for 
	 * ClientIdDefinition.ClientIdDefinition.clientServerOperation.OperationInSystemIns
	 * tanceRef.contextPort
	 *
	 * 
	 * In the context of the definition of a ClientIdDefinition, the reference 
	 * ClientIdDefinition.clientServerOperation.OperationInSystemInstanceRef.contextPor
	 * t shall only refer to an RPortPrototype.
	 */
	//	@Check(constraint="constr_3118",categories=#["SystemTemplate"])
	// def void constr_3118( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3121
	 *
	 * 
	 * The length of transformer chains is limited to 255 transformers
	 *
	 * 
	 * The maximum number of DataTransformation.transformer aggregations in 
	 * DataTransformation to TransformationTechnologys shall be limited to 255.
	 */
	//	@Check(constraint="constr_3121",categories=#["SystemTemplate"])
	// def void constr_3121( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3122
	 *
	 * 
	 * At most one transformer of each transformer class inside a transformer chain
	 *
	 * 
	 * If the value of a TransformationTechnology.transformerClass of a 
	 * TransformationTechnology referenced by a DataTransformation does not equal 
	 * TransformerClassEnum.custom, it shall be different from all other 
	 * TransformationTechnology.transformerClass values of TransformationTechnologys 
	 * referenced by the same DataTransformation.
	 */
	//	@Check(constraint="constr_3122",categories=#["SystemTemplate"])
	// def void constr_3122( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3123
	 *
	 * 
	 * Serializer transformer shall be the first in a chain
	 *
	 * 
	 * A serializer transformer (TransformationTechnology with attribute Transformation
	 * Technology.transformerClass set to
	 TransformerClassEnum.serializer shall be the 
	 * first transformer in a transformer chain.
	 */
	//	@Check(constraint="constr_3123",categories=#["SystemTemplate"])
	// def void constr_3123( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3124
	 *
	 * 
	 * Applicability of TransformationTechnology.needsOriginalData
	 *
	 * 
	 * The attribute TransformationTechnology.needsOriginalData of a 
	 * TransformationTechnology shall only be used for the non-first transformers in 
	 * the transformer chain.
	 */
	//	@Check(constraint="constr_3124",categories=#["SystemTemplate"])
	// def void constr_3124( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3125
	 *
	 * 
	 * Value of attribute BufferProperties.inPlace for the first transformer in a chain
	 *
	 * 
	 * The attribute BufferProperties.inPlace shall be set to |false| if the 
	 * TransformationTechnology of the BufferProperties is referenced as first 
	 * reference in the ordered list of references DataTransformation.transformer from 
	 * a DataTransformation.
	 */
	//	@Check(constraint="constr_3125",categories=#["SystemTemplate"])
	// def void constr_3125( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3126
	 *
	 * 
	 * BufferProperties.headerLength shall be less or equal output buffer size
	 *
	 * 
	 * The BufferProperties.headerLength shall be less or equal of the worst case 
	 * output buffer size which is specified in BufferProperties.bufferComputation in 
	 * BufferProperties.
	 */
	//	@Check(constraint="constr_3126",categories=#["SystemTemplate"])
	// def void constr_3126( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3127
	 *
	 * 
	 * Certain ISignals always need a reference to DataTransformation
	 *
	 * 
	 * An ISignal which references a SystemSignal which is referenced by a 
	 * SystemSignalGroup in the role SystemSignalGroup.transformingSystemSignal shall 
	 * always reference a DataTransformation.
	 */
	//	@Check(constraint="constr_3127",categories=#["SystemTemplate"])
	// def void constr_3127( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3128
	 *
	 * 
	 * SOME/IP transformer configuration
	 *
	 * 
	 * For each TransformationDescription variant that is a 
	 * SOMEIPTransformationDescription
	 */
	//	@Check(constraint="constr_3128",categories=#["SystemTemplate"])
	// def void constr_3128( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3129
	 *
	 * 
	 * Byte Order of SOME/IP transformer
	 *
	 * 
	 * The attribute SOMEIPTransformationDescription.byteOrder of 
	 * SOMEIPTransformationDescription shall be different from |opaque|.
	 */
	@Check(constraint="constr_3129", categories=#["SystemTemplate"])
	def void constr_3129(SOMEIPTransformationDescription description) {
		if (description.getByteOrder() == ByteOrderEnum.OPAQUE) 
			issue(description, TransformerPackage.Literals.SOMEIP_TRANSFORMATION_DESCRIPTION__BYTE_ORDER)
	}

	/**
	 *
	 * 
	 * constr_3130
	 *
	 * 
	 * Range of Interface Version
	 *
	 * 
	 * The value of the attribute SOMEIPTransformationDescription.interfaceVersion 
	 * shall be in the range [0;255]
	 */
	//	@Check(constraint="constr_3130",categories=#["SystemTemplate"])
	// def void constr_3130( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3131
	 *
	 * 
	 * Required first data transformation for 
	 * ISignalGroup.comBasedSignalGroupTransformation
	 *
	 * 
	 * If a ISignalGroup has a reference to the DataTransformation element in the role 
	 * ISignalGroup.comBasedSignalGroupTransformation then this DataTransformation 
	 * shall be the first DataTransformation in the DataTransformationSet.
	 */
	//	@Check(constraint="constr_3131",categories=#["SystemTemplate"])
	// def void constr_3131( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3132
	 *
	 * 
	 * Required COM Based Transformation for 
	 * ISignalGroup.comBasedSignalGroupTransformation
	 *
	 * 
	 * If a ISignalGroup has a reference to the DataTransformation element in the role 
	 * ISignalGroup.comBasedSignalGroupTransformation then this DataTransformation 
	 * shall be the handled by the COM Based Transformer~SWS-COMBasedTransformer.
	 */
	//	@Check(constraint="constr_3132",categories=#["SystemTemplate"])
	// def void constr_3132( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3133
	 *
	 * 
	 * CouplingPort.physicalLayerType of connected CouplingPorts
	 *
	 * 
	 * The CouplingPort.physicalLayerType of two CouplingPorts which are connected via 
	 * a CouplingPortConnection shall be equal.
	 */
	//	@Check(constraint="constr_3133",categories=#["SystemTemplate"])
	// def void constr_3133( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3134
	 *
	 * 
	 * The connection of two CouplingPorts with 
	 * CouplingPort.connectionNegotiationBehavior set to 
	 * EthernetConnectionNegotiationEnum.master is forbidden
	 *
	 * 
	 * The CouplingPort.connectionNegotiationBehavior of two CouplingPorts which are 
	 * connected via a CouplingPortConnection shall not be both set to 
	 * EthernetConnectionNegotiationEnum.master.
	 */
	//	@Check(constraint="constr_3134",categories=#["SystemTemplate"])
	// def void constr_3134( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3135
	 *
	 * 
	 * The connection of two CouplingPorts with 
	 * CouplingPort.connectionNegotiationBehavior set to 
	 * EthernetConnectionNegotiationEnum.slave is forbidden
	 *
	 * 
	 * The CouplingPort.connectionNegotiationBehavior of two CouplingPorts which are 
	 * connected via a CouplingPortConnection shall not be both set to 
	 * EthernetConnectionNegotiationEnum.slave.
	 */
	//	@Check(constraint="constr_3135",categories=#["SystemTemplate"])
	// def void constr_3135( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3136
	 *
	 * 
	 * Allowed payload of SecuredIPdus
	 *
	 * 
	 * SecuredIPdus are allowed to reference PduTriggerings of ISignalIPdus, 
	 * ContainerIPdus, MultiplexedIPdus and UserDefinedIPdus.
	 */
	//	@Check(constraint="constr_3136",categories=#["SystemTemplate"])
	// def void constr_3136( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3137
	 *
	 * 
	 * IPduPort.IPduPort.rxSecurityVerification is configurable on the receiver side
	 *
	 * 
	 * The IPduPort.IPduPort.rxSecurityVerification attribute shall only be used in 
	 * IPduPorts with the CommConnectorPort.communicationDirection = in.
	 */
	//	@Check(constraint="constr_3137",categories=#["SystemTemplate"])
	// def void constr_3137( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3138
	 *
	 * 
	 * IPduPort.IPduPort.rxSecurityVerification validness
	 *
	 * 
	 * The IPduPort.IPduPort.rxSecurityVerification information is only valid for 
	 * SecuredIPdus.
	 */
	//	@Check(constraint="constr_3138",categories=#["SystemTemplate"])
	// def void constr_3138( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3139
	 *
	 * 
	 * Usage of IPduPort.IPduPort.rxSecurityVerification
	 *
	 * 
	 * The IPduPort.IPduPort.rxSecurityVerification is allowed to be set to false only 
	 * for SecuredIPdus with a static and fixed payload layout. For SecuredIPdus that 
	 * contain dynamic length IPdus this attribute shall be always set to true.
	 */
	//	@Check(constraint="constr_3139",categories=#["SystemTemplate"])
	// def void constr_3139( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3140
	 *
	 * 
	 * No ByteOrderEnum.ByteOrderEnum.opaque allowed for 
	 * System.System.containerIPduHeaderByteOrder
	 *
	 * 
	 * The values of System.System.containerIPduHeaderByteOrder are restricted to 
	 * ByteOrderEnum.ByteOrderEnum.mostSignificantByteFirst and 
	 * ByteOrderEnum.ByteOrderEnum.mostSignificantByteLast. I.e. the value 
	 * ByteOrderEnum.ByteOrderEnum.opaque is not allowed.
	 */
	//	@Check(constraint="constr_3140",categories=#["SystemTemplate"])
	// def void constr_3140( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3141
	 *
	 * 
	 * Only IPdus shall be part of a ContainerIPdu
	 *
	 * 
	 * The PduTriggering which is referenced in the role 
	 * ContainerIPdu.ContainerIPdu.containedPduTriggering shall refer to a subclass of 
	 * an IPdu in the role PduTriggering.PduTriggering.iPdu.
	 */
	@Check(constraint="constr_3141", categories=#["SystemTemplate"])
	def void constr_3141(ContainerIPdu containerIPdu) {
		issuePred(containerIPdu.getContainedPduTriggerings(), [PduTriggering x|!(x.getIPdu() instanceof IPdu)],
			CorecommunicationPackage.Literals.PDU_TRIGGERING__IPDU)
	}

	/**
	 *
	 * 
	 * constr_3142
	 *
	 * 
	 * Mandatory ContainedIPduProps.headerIdLongHeader for 
	 * ContainerIPduHeaderTypeEnum.longHeader
	 *
	 * 
	 * For each IPdu which is assigned to a ContainerIPdu in the role 
	 * ContainerIPdu.ContainerIPdu.containedPduTriggering with 
	 * ContainerIPdu.ContainerIPdu.headerType = ContainerIPduHeaderTypeEnum.longHeader 
	 * the IPdu.IPdu.containedIPduProps.ContainedIPduProps.headerIdLongHeader shall be 
	 * defined.
	 */
	//	@Check(constraint="constr_3142",categories=#["SystemTemplate"])
	// def void constr_3142( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3143
	 *
	 * 
	 * Mandatory ContainedIPduProps.headerIdShortHeader for 
	 * ContainerIPduHeaderTypeEnum.shortHeader
	 *
	 * 
	 * For each IPdu which is assigned to a ContainerIPdu in the role 
	 * ContainerIPdu.ContainerIPdu.containedPduTriggering with 
	 * ContainerIPdu.ContainerIPdu.headerType = ContainerIPduHeaderTypeEnum.shortHeader
	 *  the IPdu.IPdu.containedIPduProps.ContainedIPduProps.headerIdShortHeader shall 
	 * be defined.
	 */
	//	@Check(constraint="constr_3143",categories=#["SystemTemplate"])
	// def void constr_3143( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3144
	 *
	 * 
	 * Mandatory IPdu.IPdu.containedIPduProps for contained IPdus
	 *
	 * 
	 * For each IPdu which is assigned to a ContainerIPdu in the role 
	 * ContainerIPdu.ContainerIPdu.containedPduTriggering the 
	 * IPdu.IPdu.containedIPduProps shall be defined.
	 */
	@Check(constraint="constr_3144", categories=#["SystemTemplate"])
	def void constr_3144(ContainerIPdu containerIPdu) {
		issuePred(containerIPdu.getContainedPduTriggerings(),
			[PduTriggering x|(x.getIPdu() instanceof IPdu && ((x.getIPdu() as IPdu).getContainedIPduProps() == null))],
			CorecommunicationPackage.Literals.CONTAINER_IPDU__CONTAINED_PDU_TRIGGERINGS)
	}

	/**
	 *
	 * 
	 * constr_3146
	 *
	 * 
	 * Partial Networking timing constraint
	 *
	 * 
	 * For Partial Networking the following timing constraints shall be ensured:
	 */
	//	@Check(constraint="constr_3146",categories=#["SystemTemplate"])
	// def void constr_3146( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3148
	 *
	 * 
	 * DataTransformation.executeDespiteDataUnavailability setting in case an E2E 
	 * Transformer is used
	 *
	 * 
	 * A transformer chain using E2E shall be configured with 
	 * DataTransformation.DataTransformation.executeDespiteDataUnavailability = TRUE.
	 */
	//	@Check(constraint="constr_3148",categories=#["SystemTemplate"])
	// def void constr_3148( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3149
	 *
	 * 
	 * TransformationTechnology settings for E2E Transformer
	 *
	 * 
	 * The E2E transformer shall be configured with the following values:
	 */
	//	@Check(constraint="constr_3149",categories=#["SystemTemplate"])
	// def void constr_3149( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3150
	 *
	 * 
	 * Effect of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.upperHeaderB
	 * itsToShift value in PROFILE_01 in case it is 0
	 *
	 * 
	 * If in PROFILE_01 the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.upperHeaderB
	 * itsToShift is equal 0 the E2E transformer used in a transformer chain with a 
	 * SOME/IP transformer shall be configured with the following values:
	 */
	//	@Check(constraint="constr_3150",categories=#["SystemTemplate"])
	// def void constr_3150( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3151
	 *
	 * 
	 * BufferProperties.BufferProperties.headerLength settings for an E2E transformer 
	 * used in combination with a SOME/IP transformer
	 *
	 * 
	 * The BufferProperties.BufferProperties.headerLength for an E2E transformer locate
	 * d in a transformer chain with a SOME/IP transformer shall be configured with the
	 *  following values depending on the value of the
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute:
	 */
	//	@Check(constraint="constr_3151",categories=#["SystemTemplate"])
	// def void constr_3151( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3152
	 *
	 * 
	 * BufferProperties.BufferProperties.headerLength settings for an E2E transformer 
	 * used in combination with a COM Based transformer
	 *
	 * 
	 * An E2E transformer used in a transformer chain with a COM Based transformer 
	 * shall be configured with the following values:
	 */
	//	@Check(constraint="constr_3152",categories=#["SystemTemplate"])
	// def void constr_3152( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3153
	 *
	 * 
	 * E2E header field reservation required by COM Based transformer
	 *
	 * 
	 * A COM Based transformer that is used in a transformer chain with an E2E 
	 * transformer requires that the following amount of space is allocated for the E2E
	 *  header fields using a proper ISignalGroup layout according to TPS_SYST_02068:
	 */
	//	@Check(constraint="constr_3153",categories=#["SystemTemplate"])
	// def void constr_3153( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3154
	 *
	 * 
	 * BufferProperties.BufferProperties.bufferComputation setting for an E2E 
	 * transformer
	 *
	 * 
	 * If the TransformationTechnology.TransformationTechnology.protocol attribute has 
	 * a value of E2E then 
	 the multiplicity of 
	 * BufferProperties.BufferProperties.bufferComputation element shall be 0.
	 */
	//	@Check(constraint="constr_3154",categories=#["SystemTemplate"])
	// def void constr_3154( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3155
	 *
	 * 
	 * Allowed values for 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.upperHeaderB
	 * itsToShift
	 *
	 * 
	 * The value of of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.upperHeaderB
	 * itsToShift attribute depends on the used serializing transformer:
	 */
	//	@Check(constraint="constr_3155",categories=#["SystemTemplate"])
	// def void constr_3155( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3156
	 *
	 * 
	 * Allowed values for 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId in 
	 * PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_01 then the value of the 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId 
	 * attribute shall be in the range of 0-65535.
	 */
	//	@Check(constraint="constr_3156",categories=#["SystemTemplate"])
	// def void constr_3156( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3157
	 *
	 * 
	 * Allowed values for 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId in 
	 * PROFILE_01 in case EndToEndTransformationDescription.dataIdMode is set to 
	 * DataIdModeEnum.lower12Bit
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_01 and the value of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIDMode 
	 * attribute has a value of DataIdModeEnum.lower12Bit then the value of the 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId 
	 * attribute shall be in the range of 256-65535.
	 */
	//	@Check(constraint="constr_3157",categories=#["SystemTemplate"])
	// def void constr_3157( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3158
	 *
	 * 
	 * Allowed values for 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.maxDeltaCoun
	 * ter in PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_01 then the attribute 
	 * EndToEndTransformationDescription.maxDeltaCounter shall be in the range 1-14.
	 */
	//	@Check(constraint="constr_3158",categories=#["SystemTemplate"])
	// def void constr_3158( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3159
	 *
	 * 
	 * Allowed values for 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.maxDeltaCoun
	 * ter in PROFILE_04
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_04 the value of 
	 * EndToEndTransformationDescription.maxDeltaCounter attribute shall be in the 
	 * range 1-65535.
	 */
	//	@Check(constraint="constr_3159",categories=#["SystemTemplate"])
	// def void constr_3159( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3160
	 *
	 * 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId in 
	 * PROFILE_02
	 *
	 * 
	 * If the EndToEndTransformationDescription.EndToEndTransformationDescription.profi
	 * leName attribute has a value of PROFILE_02 then the multiplicity of the
	 * EndToEndTransformationISignalProps.dataId attribute shall be 16 and the value of
	 *  each instance shall be in the range 0..255.
	 */
	//	@Check(constraint="constr_3160",categories=#["SystemTemplate"])
	// def void constr_3160( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3161
	 *
	 * 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataLength
	 *  in PROFILE_01, PROFILE_02, PROFILE_05
	 *
	 * 
	 * If the EndToEndTransformationDescription.EndToEndTransformationDescription.profi
	 * leName attribute has a value of PROFILE_01, PROFILE_02, or PROFILE_05
	 then the 
	 * multiplicity of the 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataLength
	 *  attribute shall be 1.
	 */
	//	@Check(constraint="constr_3161",categories=#["SystemTemplate"])
	// def void constr_3161( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3162
	 *
	 * 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.minDataLen
	 * gth and 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.maxDataLen
	 * gth in PROFILE_01, PROFILE_02, PROFILE_05
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_01, PROFILE_02, or PROFILE_05 then the 
	 * multiplicity of the attributes 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.minDataLen
	 * gth and 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.maxDataLen
	 * gth shall be 0.
	 */
	//	@Check(constraint="constr_3162",categories=#["SystemTemplate"])
	// def void constr_3162( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3163
	 *
	 * 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.minDataLen
	 * gth and 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.maxDataLen
	 * gth in PROFILE_04 and PROFILE_06
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_04 or PROFILE_06 then the multiplicity of the 
	 * attributes 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.minDataLen
	 * gth and 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.maxDataLen
	 * gth shall be 1.
	 */
	//	@Check(constraint="constr_3163",categories=#["SystemTemplate"])
	// def void constr_3163( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3164
	 *
	 * 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataLength
	 *  in PROFILE_04 and PROFILE_06
	 *
	 * 
	 * If the EndToEndTransformationDescription.EndToEndTransformationDescription.profi
	 * leName attribute has a value of PROFILE_04 or PROFILE_06 then the
	 multiplicity 
	 * of the attribute 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataLength
	 *  shall be 0.
	 */
	//	@Check(constraint="constr_3164",categories=#["SystemTemplate"])
	// def void constr_3164( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3165
	 *
	 * 
	 * Effect of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.upperHeaderB
	 * itsToShift value in PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_01 then:
	 */
	//	@Check(constraint="constr_3165",categories=#["SystemTemplate"])
	// def void constr_3165( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3166
	 *
	 * 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.upperHeaderB
	 * itsToShift in PROFILE_02
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_02 then the value of the 
	 * EndToEndTransformationDescription.upperHeaderBitsToShift attribute shall be 0.
	 */
	//	@Check(constraint="constr_3166",categories=#["SystemTemplate"])
	// def void constr_3166( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3167
	 *
	 * 
	 * Effect of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.upperHeaderB
	 * itsToShift value in PROFILE_04, PROFILE_05 and PROFILE_06
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_04, PROFILE_05, or PROFILE_06 the value of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.offset 
	 * attribute shall be equal to the value of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.upperHeaderB
	 * itsToShift attribute.
	 */
	//	@Check(constraint="constr_3167",categories=#["SystemTemplate"])
	// def void constr_3167( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3169
	 *
	 * 
	 * Attribute multiplicities and values in PROFILE_02
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_02 then:
	 */
	//	@Check(constraint="constr_3169",categories=#["SystemTemplate"])
	// def void constr_3169( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3171
	 *
	 * 
	 * Value of 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId 
	 * shall be unique in PROFILE_04, PROFILE_05 and PROFILE_06
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_04, PROFILE_05, or PROFILE_06 then the value of
	 *  the 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId 
	 * attribute shall be unique within the scope of the System.
	 */
	//	@Check(constraint="constr_3171",categories=#["SystemTemplate"])
	// def void constr_3171( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3172
	 *
	 * 
	 * Effect of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileBehav
	 * ior value in PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_01 and the value of the 
	 * EndToEndTransformationDescription.profileBehavior attribute is 
	 * EndToEndProfileBehaviorEnum.R4_2 then:
	 */
	//	@Check(constraint="constr_3172",categories=#["SystemTemplate"])
	// def void constr_3172( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3173
	 *
	 * 
	 * Effect of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileBehav
	 * ior value in PROFILE_02
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_02 and the value of the 
	 * EndToEndTransformationDescription.profileBehavior attribute is 
	 * EndToEndProfileBehaviorEnum.R4_2 then:
	 */
	//	@Check(constraint="constr_3173",categories=#["SystemTemplate"])
	// def void constr_3173( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3174
	 *
	 * 
	 * EndToEndTransformationDescription settings not allowed in PROFILE_04, PROFILE_05
	 *  and PROFILE_06
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_04, PROFILE_05 or PROFILE_06 then:
	 */
	//	@Check(constraint="constr_3174",categories=#["SystemTemplate"])
	// def void constr_3174( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3176
	 *
	 * 
	 * Value range of EndToEndTransformationDescription.windowSize
	 *
	 * 
	 * The value of the EndToEndTransformationDescription.windowSize attribute shall be
	 *  greater or equal to 1.
	 */
	//	@Check(constraint="constr_3176",categories=#["SystemTemplate"])
	// def void constr_3176( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3177
	 *
	 * 
	 * Dependency between EndToEndTransformationDescription.maxErrorStateValid, EndToEn
	 * dTransformationDescription.maxErrorStateInit and
	 * EndToEndTransformationDescription.maxErrorStateInvalid
	 *
	 * 
	 * The following restriction shall be respected: 
	 * EndToEndTransformationDescription.maxErrorStateValid >= 
	 * EndToEndTransformationDescription.maxErrorStateInit >= 
	 * EndToEndTransformationDescription.maxErrorStateInvalid >= 0
	 */
	//	@Check(constraint="constr_3177",categories=#["SystemTemplate"])
	// def void constr_3177( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3178
	 *
	 * 
	 * Dependency between EndToEndTransformationDescription.minOkStateValid, EndToEndTr
	 * ansformationDescription.minOkStateInit and
	 * EndToEndTransformationDescription.minOkStateInvalid
	 *
	 * 
	 * The following restriction shall be respected: 
	 1 <= 
	 * EndToEndTransformationDescription.minOkStateValid <= 
	 * EndToEndTransformationDescription.minOkStateInit <= 
	 * EndToEndTransformationDescription.minOkStateInvalid
	 */
	//	@Check(constraint="constr_3178",categories=#["SystemTemplate"])
	// def void constr_3178( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3179
	 *
	 * 
	 * Dependency between EndToEndTransformationDescription.minOkStateInit, 
	 * EndToEndTransformationDescription.maxErrorStateInit and 
	 * EndToEndTransformationDescription.windowSizeStateInit
	 *
	 * 
	 * The following restriction shall be respected: 
	 * EndToEndTransformationDescription.minOkStateInit + 
	 * EndToEndTransformationDescription.maxErrorStateInit <= 
	 * EndToEndTransformationDescription.windowSizeStateInit
	 */
	//	@Check(constraint="constr_3179",categories=#["SystemTemplate"])
	// def void constr_3179( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3180
	 *
	 * 
	 * Dependency between EndToEndTransformationDescription.minOkStateValid, 
	 * EndToEndTransformationDescription.maxErrorStateValid and 
	 * EndToEndTransformationDescription.windowSizeStateValid
	 *
	 * 
	 * The following restriction shall be respected: 
	 * EndToEndTransformationDescription.minOkStateValid + 
	 * EndToEndTransformationDescription.maxErrorStateValid <= 
	 * EndToEndTransformationDescription.windowSizeStateValid
	 */
	//	@Check(constraint="constr_3180",categories=#["SystemTemplate"])
	// def void constr_3180( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3181
	 *
	 * 
	 * Dependency between EndToEndTransformationDescription.minOkStateInvalid, 
	 * EndToEndTransformationDescription.maxErrorStateInvalid and 
	 * EndToEndTransformationDescription.windowSizeStateInvalid
	 *
	 * 
	 * The following restriction shall be respected:
	 * EndToEndTransformationDescription.minOkStateInvalid + 
	 * EndToEndTransformationDescription.maxErrorStateInvalid <= 
	 * EndToEndTransformationDescription.windowSizeStateInvalid
	 */
	//	@Check(constraint="constr_3181",categories=#["SystemTemplate"])
	// def void constr_3181( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3182
	 *
	 * 
	 * Restriction on 
	 * TransformationTechnology.TransformationTechnology.transformationDescriptionVaria
	 * tionPoint
	 *
	 * 
	 * The EndToEndTransformationDescription.EndToEndTransformationDescription.profileN
	 * ame attribute shall not be subject to variability for a given ISignal /
	 * ISignalGroup, i.e., the value of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute shall be the same in all different variants.
	 */
	//	@Check(constraint="constr_3182",categories=#["SystemTemplate"])
	// def void constr_3182( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3183
	 *
	 * 
	 * ISignalGroup with ISignalGroup.transformationISignalProps
	 *
	 * 
	 * An ISignalGroup that aggregates ISignalGroup.transformationISignalProps shall 
	 * reference the DataTransformation in the role 
	 * DataTransformation.comBasedSignalGroupTransformation.
	 */
	//	@Check(constraint="constr_3183",categories=#["SystemTemplate"])
	// def void constr_3183( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3184
	 *
	 * 
	 * Only one 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId 
	 * element in PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_01 then the multiplicity of the 
	 * EndToEndTransformationISignalProps.EndToEndTransformationISignalProps.dataId 
	 * attribute shall be 1.
	 */
	//	@Check(constraint="constr_3184",categories=#["SystemTemplate"])
	// def void constr_3184( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3185
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdMode 
	 * in PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to PROFILE_01 then the multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdMode 
	 * attribute shall be 1.
	 */
	//	@Check(constraint="constr_3185",categories=#["SystemTemplate"])
	// def void constr_3185( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3186
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdMode 
	 * in PROFILE_02, PROFILE_04, PROFILE_05 and PROFILE_06
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to a value of PROFILE_02, PROFILE_04, PROFILE_05 or PROFILE_06 
	 * then the multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdMode 
	 * attribute shall be 0.
	 */
	//	@Check(constraint="constr_3186",categories=#["SystemTemplate"])
	// def void constr_3186( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3187
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.counterOffse
	 * t in PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to PROFILE_01 then the multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.counterOffse
	 * t attribute shall be 1.
	 */
	//	@Check(constraint="constr_3187",categories=#["SystemTemplate"])
	// def void constr_3187( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3188
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.counterOffse
	 * t in PROFILE_02, PROFILE_04, PROFILE_05 and PROFILE_06
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to a value of PROFILE_02, PROFILE_04, PROFILE_05 or PROFILE_06 
	 * then the multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.counterOffse
	 * t attribute shall be 0.
	 */
	//	@Check(constraint="constr_3188",categories=#["SystemTemplate"])
	// def void constr_3188( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3189
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.crcOffset in
	 *  PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to PROFILE_01 then the multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.crcOffset 
	 * attribute shall be 1.
	 */
	//	@Check(constraint="constr_3189",categories=#["SystemTemplate"])
	// def void constr_3189( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3190
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.crcOffset in
	 *  PROFILE_02, PROFILE_04, PROFILE_05 and PROFILE_06
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to a value of PROFILE_02, PROFILE_04, PROFILE_05 or PROFILE_06 
	 * then the multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.crcOffset 
	 * attribute shall be 0.
	 */
	//	@Check(constraint="constr_3190",categories=#["SystemTemplate"])
	// def void constr_3190( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3191
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdNibble
	 * Offset in PROFILE_01 and EndToEndTransformationDescription.dataIdMode equal to 
	 * DataIdModeEnum.lower12Bit
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to PROFILE_01 and the value of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdMode 
	 * attribute is set to DataIdModeEnum.lower12Bit then the multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdNibble
	 * Offset attribute shall be 1.
	 */
	//	@Check(constraint="constr_3191",categories=#["SystemTemplate"])
	// def void constr_3191( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3192
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdNibble
	 * Offset in PROFILE_02, PROFILE_04, PROFILE_05 and PROFILE_06 or 
	 * EndToEndTransformationDescription.dataIdMode different from 
	 * DataIdModeEnum.lower12Bit
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to a value of PROFILE_02, PROFILE_04, PROFILE_05 or PROFILE_06 
	 * or the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdMode 
	 * attribute is set to value different from DataIdModeEnum.lower12Bit then the 
	 * multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.dataIdNibble
	 * Offset attribute shall be 0.
	 */
	//	@Check(constraint="constr_3192",categories=#["SystemTemplate"])
	// def void constr_3192( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3193
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.offset in 
	 * PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to PROFILE_01 then the multiplicity of the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.offset 
	 * attribute shall be 0.
	 */
	//	@Check(constraint="constr_3193",categories=#["SystemTemplate"])
	// def void constr_3193( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3194
	 *
	 * 
	 * Multiplicity of 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.offset in 
	 * Profiles different from PROFILE_01
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute is set to a value different from PROFILE_01 then the multiplicity of 
	 * the EndToEndTransformationDescription.EndToEndTransformationDescription.offset 
	 * attribute shall be 1.
	 */
	//	@Check(constraint="constr_3194",categories=#["SystemTemplate"])
	// def void constr_3194( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3195
	 *
	 * 
	 * Allowed values for 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.maxDeltaCoun
	 * ter in PROFILE_02
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_02 then the attribute 
	 * EndToEndTransformationDescription.maxDeltaCounter shall be in the range 1-15.
	 */
	//	@Check(constraint="constr_3195",categories=#["SystemTemplate"])
	// def void constr_3195( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3196
	 *
	 * 
	 * Allowed values for 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.maxDeltaCoun
	 * ter in PROFILE_05
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_05 then the attribute 
	 * EndToEndTransformationDescription.maxDeltaCounter shall be in the range 1-255.
	 */
	//	@Check(constraint="constr_3196",categories=#["SystemTemplate"])
	// def void constr_3196( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3197
	 *
	 * 
	 * Allowed values for 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.maxDeltaCoun
	 * ter in PROFILE_06
	 *
	 * 
	 * If the 
	 * EndToEndTransformationDescription.EndToEndTransformationDescription.profileName 
	 * attribute has a value of PROFILE_06 then the attribute 
	 * EndToEndTransformationDescription.maxDeltaCounter shall be in the range 1-255.
	 */
	//	@Check(constraint="constr_3197",categories=#["SystemTemplate"])
	// def void constr_3197( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3198
	 *
	 * 
	 * Uniqueness of PncMapping.PncMapping.shortLabel
	 *
	 * 
	 * If the optional PncMapping.shortLabel attribute is used it shall be unique in 
	 * the System scope.
	 */
	//	@Check(constraint="constr_3198",categories=#["SystemTemplate"])
	// def void constr_3198( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3199
	 *
	 * 
	 * ISignal that has ISignal.dataTypePolicy set to 
	 * DataTypePolicyEnum.transformingISignal shall reference a DataTransformation
	 *
	 * 
	 * In a complete model every ISignal that has ISignal.dataTypePolicy set to 
	 * DataTypePolicyEnum.transformingISignal shall reference a DataTransformation.
	 */
	//	@Check(constraint="constr_3199",categories=#["SystemTemplate"])
	// def void constr_3199( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3501
	 *
	 * 
	 * Role of SystemSignal in 1:n communication
	 *
	 * 
	 */
	//	@Check(constraint="constr_3501",categories=#["SystemTemplate"])
	// def void constr_3501( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3505
	 *
	 * 
	 * Criteria for primitive Data Mapping
	 *
	 * 
	 * The VariableDataPrototype referenced by 
	 * SenderReceiverToSignalMapping.dataElement shall be typed by one of
	 */
	//	@Check(constraint="constr_3505",categories=#["SystemTemplate"])
	// def void constr_3505( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3506
	 *
	 * 
	 * Mapping of composite data type to SystemSignals in SystemSignalGroup
	 *
	 * 
	 * The elements of a composite data type shall be mapped to single SystemSignals 
	 * which shall be members of one SystemSignalGroup if no data transformation 
	 * (except COM Based Transformer) is used.
	 */
	//	@Check(constraint="constr_3506",categories=#["SystemTemplate"])
	// def void constr_3506( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3508
	 *
	 * 
	 * Value of FlexRayCommunicationConnector.nmReadySleepTime
	 *
	 * 
	 * The FlexRayCommunicationConnector.nmReadySleepTime value shall be a multiple of 
	 * FlexrayCluster.cycle * FlexrayCluster.nmRepetitionCycle.
	 */
	//	@Check(constraint="constr_3508",categories=#["SystemTemplate"])
	// def void constr_3508( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3514
	 *
	 * 
	 * No two ISignalToIPduMappings shall reference the identical ISignal
	 *
	 * 
	 * No two ISignalToIPduMappings shall reference the identical ISignal in the role 
	 * ISignalToIPduMapping.iSignal.
	 */
	@Check(constraint="constr_3514", categories=#["SystemTemplate"])
	def void constr_3514(ISignal signal) {
		val references = getCrossReferenceAdapter(signal).getInverseReferences(signal)
		val filteredReferences = references.filter[it.getEStructuralFeature() == CorecommunicationPackage.Literals.ISIGNAL_TO_IPDU_MAPPING__ISIGNAL]
		filteredReferences.forEach[issue(it.getEObject(), CorecommunicationPackage.Literals.ISIGNAL_TO_IPDU_MAPPING__ISIGNAL, null)]
	}

	/**
	 *
	 * 
	 * constr_3515
	 *
	 * 
	 * Fully filled EthernetPriorityRegeneration table
	 *
	 * 
	 * In case the CouplingPortDetails.CouplingPortDetails.ethernetPriorityRegeneration
	 *  is defined it shall contain exactly 8 elements of EthernetPriorityRegeneration,
	 *  one for each value of EthernetPriorityRegeneration.ingressPriority (0-7).
	 */
	//	@Check(constraint="constr_3515",categories=#["SystemTemplate"])
	// def void constr_3515( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3516
	 *
	 * 
	 * limitation of Pdu.Pdu.length for CAN L-PDUs
	 *
	 * 
	 * The Pdu.Pdu.length of CAN PDUs shall be restricted to 0..8 for classic CAN 
	 * L-PDUs and 0..8, 12, 16, 20, 24, 32, 48, 64 for CAN FD L-PDUs.
	 */
	//	@Check(constraint="constr_3516",categories=#["SystemTemplate"])
	// def void constr_3516( o ) {
	//		if( )  {
	//			issue(o,);
	//		}
	//	}
	/**
	 *
	 * 
	 * constr_3517
	 *
	 * 
	 * Consistent setting of ContainedIPduProps.ContainedIPduProps.collectionSemantics 
	 * in the context of one ContainerIPdu
	 *
	 * 
	 * The value of the attribute 
	 * ContainedIPduProps.ContainedIPduProps.collectionSemantics shall be identical for
	 *  all contained IPdus within the context of a given ContainerIPdu.
	 */
	//	@Check(constraint="constr_3517",categories=#["SystemTemplate"])
	// def void constr_3517( ContainerIPdu o ) {
	//		o.getContainedIPduProps().getCollectionSemantics()
	//	}
	/**
	 *
	 * 
	 * constr_3518
	 *
	 * 
	 * Range of CanControllerFdConfiguration.CanControllerFdConfiguration.paddingValue 
	 * and 
	 * CanControllerFdConfigurationRequirements.CanControllerFdConfigurationRequirement
	 * s.paddingValue
	 *
	 * 
	 * Range of CanControllerFdConfiguration.CanControllerFdConfiguration.paddingValue 
	 * and 
	 * CanControllerFdConfigurationRequirements.CanControllerFdConfigurationRequirement
	 * s.paddingValue
	 */
	@Check(constraint="constr_3518", categories=#["SystemTemplate"])
	def void constr_3518(CanControllerFdConfiguration config) {
		if (config.getPaddingValue() < 0 || config.getPaddingValue() > 255) 
			issue(config, CantopologyPackage.Literals.CAN_CONTROLLER_FD_CONFIGURATION__PADDING_VALUE)
	}

	@Check(constraint="constr_3518", categories=#["SystemTemplate"])
	def void constr_3518(CanControllerFdConfigurationRequirements requirements) {
		if (requirements.getPaddingValue() < 0 || requirements.getPaddingValue() > 255) 
			issue(requirements, CantopologyPackage.Literals.CAN_CONTROLLER_FD_CONFIGURATION_REQUIREMENTS__PADDING_VALUE)
	}

	@Check(constraint="AUTOSARDUMMY")
	protected def void dummy(AUTOSAR ar) {
	}

	@Check(constraint="AUTOSARDUMMY")
	protected def void dummy(ARPackage arPackage) {
	}

	@Check(constraint="AUTOSARDUMMY")
	protected def void dummy(EObject eObject) {
	}
}
