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
package org.artop.aal.examples.autosar444.check;

import java.util.HashSet;

import org.artop.aal.autosar40.services.predicates.compatibility.PortInterfaceCompatibility;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sphinx.emf.check.Check;

import autosar40.autosartoplevelstructure.AUTOSAR;
import autosar40.commonstructure.basetypes.SwBaseType;
import autosar40.commonstructure.datadefproperties.SwDataDefPropsConditional;
import autosar40.commonstructure.implementationdatatypes.ImplementationDataType;
import autosar40.commonstructure.implementationdatatypes.ImplementationdatatypesPackage;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import autosar40.swcomponent.components.ComponentsPackage;
import autosar40.swcomponent.components.PPortPrototype;
import autosar40.swcomponent.components.PortPrototype;
import autosar40.swcomponent.components.RPortPrototype;
import autosar40.swcomponent.composition.AssemblySwConnector;
import autosar40.swcomponent.composition.CompositionPackage;
import autosar40.swcomponent.composition.CompositionSwComponentType;
import autosar40.swcomponent.datatype.datatypes.ApplicationPrimitiveDataType;
import autosar40.swcomponent.endtoendprotection.EndToEndDescription;
import autosar40.swcomponent.endtoendprotection.EndtoendprotectionPackage;
import autosar40.swcomponent.portinterface.ApplicationError;
import autosar40.swcomponent.portinterface.ModeSwitchInterface;
import autosar40.swcomponent.portinterface.NvDataInterface;
import autosar40.swcomponent.portinterface.PortinterfacePackage;
import autosar40.swcomponent.portinterface.TriggerInterface;

import com.google.common.collect.Sets;

class SoftwareComponentTemplateValidator extends AbstractAutosarCheckValidator {

	private static final String PROFILE_01 = "PROFILE_01" //$NON-NLS-1$

	private static final String PROFILE_02 = "PROFILE_02" //$NON-NLS-1$

	private static final String NONE = "NONE" //$NON-NLS-1$

	private static final String E_OK = "E_OK" //$NON-NLS-1$

	private HashSet<String> constr_1110 = Sets.newHashSet(NONE, PROFILE_01, PROFILE_02)

	/**
	 * constr_1000 End-to-end protection is limited to sender/receive communication
	 */
	// @Check(constraint="constr_1000")
	// def void constr_1000( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1001 Value of EndToEndDescription.dataId shall be unique The value of the EndToEndDescription.dataId shall
	 * be unique within the scope of the System.
	 */
	// @Check(constraint="constr_1001")
	// def void constr_1001( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1002 End-to-end protection does not support n:1 communication As the n:1 communication scenario implies
	 * that probably not all senders use the same EndToEndDescription.dataId this scenario is explicitly not supported.
	 */
	// @Check(constraint="constr_1002")
	// def void constr_1002( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1004 Mapping of ApplicationDataTypes The same ApplicationDataTypes may be mapped to different
	 * ImplementationDataTypes even in the scope of a single ECU (more exactly speaking, a single RTE), but not in the
	 * scope of a single atomic software component.
	 */
	// @Check(constraint="constr_1004")
	// def void constr_1004( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1005 Compatibility of ImplementationDataTypes mapped to the same ApplicationDataType It is required that
	 * ImplementationDataTypes which are taken for connecting corresponding elements of PortInterfaces and thus refer to
	 * compatible ApplicationDataTypes are also compatible among each other (so that RTE is able to cope with possible
	 * connections by converting the data accordingly).
	 */
	// @Check(constraint="constr_1005")
	// def void constr_1005( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1006 applicable data categories Table~table:CategoriesOverview defines the applicable
	 * Identifiable.categorys depending on specific model elements related to data definition properties.67107
	 */
	// @Check(constraint="constr_1006")
	// def void constr_1006( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1007 Allowed attributes of SwDataDefProps for ApplicationDataTypes The allowed attributes of
	 * SwDataDefProps for ApplicationDataTypes and their allowed multiplicities are listed as an overview in
	 * table~table:CategoriesAppl. 67107
	 */
	// @Check(constraint="constr_1007")
	// def void constr_1007( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1008 Applicability of Identifiable.categorys ARTechTerm_STRUCTURE and ARTechTerm_ARRAY The categories
	 * ARTechTerm_STRUCTURE and ARTechTerm_ARRAY correspond to ApplicationCompositeDataTypes whereas all other
	 * Identifiable.categorys can be applied only for ApplicationPrimitiveDataTypes.
	 */
	// @Check(constraint="constr_1008")
	// def void constr_1008( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1009 SwDataDefProps applicable to ImplementationDataTypes A complete list of the SwDataDefProps and other
	 * attributes and their multiplicities which are allowed for a given Identifiable.category is shown in
	 * table~table:CategoriesImpl.
	 */
	// @Check(constraint="constr_1009")
	// def void constr_1009( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1010 If BaseTypeDirectDefinition.nativeDeclaration does not exist If
	 * BaseTypeDirectDefinition.nativeDeclaration does not exist in the SwBaseType it is required that the
	 * Identifiable.shortName (e.g. ``uint8'') of the corresponding ImplementationDataType is equal to a name of one of
	 * the Platform or Standard Types predefined in AUTOSAR code.
	 */
	// @Check(constraint="constr_1010")
	// def void constr_1010( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1011 Identifiable.category of SwBaseType For Identifiable.category only the values FIXED_LENGTH and
	 * VARIABLE_LENGTH are supported.
	 */
	@Check(constraint = "constr_1011")
	def void constr_1011(SwBaseType swBaseType) {
		if (swBaseType.getCategory() !== null && !(swBaseType.getCategory().equals("FIXED_LENGTH") || swBaseType.getCategory().equals("VARIABLE_LENGTH")))
			issue(swBaseType, null)
	}

	/**
	 * constr_1012 Value of Identifiable.category is FIXED_LENGTH If the value of the attribute Identifiable.category of
	 * SwBaseType is set to FIXED_LENGTH the attribute BaseTypeDirectDefinition.baseTypeSize shall be filled with
	 * content and attribute BaseTypeDirectDefinition.maxBaseTypeSize shall not exist.
	 */
	// @Check(constraint="constr_1012")
	// def void constr_1012( SwBaseType o ) {
	// if(o.getCategory() !== null && o.getCategory().equals("FIXED_LENGTH") ) {
	//
	// }
	// }

	/**
	 * constr_1013 Value of Identifiable.category is VARIABLE_LENGTH If the value of the attribute Identifiable.category
	 * of SwBaseType is set to VARIABLE_LENGTH the attribute BaseTypeDirectDefinition.maxBaseTypeSize shall be filled
	 * with content and attribute BaseTypeDirectDefinition.baseTypeSize shall not exist.
	 */
	// @Check(constraint="constr_1013")
	// def void constr_1013( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1014 Supported value encodings for SwBaseType 1C: One's complement
	 */
	// @Check(constraint="constr_1014")
	// def void constr_1014( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1015 Prioritization of SwDataDefProps The prioritization and usage of attributes of meta-class
	 * SwDataDefProps shall follow the restrictions given in table~table:DataDefPropsUsageDetails.
	 */
	// @Check(constraint="constr_1015")
	// def void constr_1015( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1016 Restriction of SwDataDefProps.invalidValue for ImplementationDataType and
	 * ImplementationDataTypeElement SwDataDefProps.invalidValue for ImplementationDataType and
	 * ImplementationDataTypeElement is restricted to to be either a compatible NumericalValueSpecification,
	 * TextValueSpecification (caution, constr_1284 applies) or a ConstantReference that in turn points to a compatible
	 * ValueSpecification.
	 */
	// @Check(constraint="constr_1016")
	// def void constr_1016( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1017 Supported combinations of SwDataDefProps.swImplPolicy and SwDataDefProps.swCalibrationAccess The
	 * table~tab:Supported combinations of SwImplPolicy and SwCalibrationAccess defines the supported combinations of
	 * SwDataDefProps.swImplPolicy and SwDataDefProps.swCalibrationAccess attribute setting.
	 */
	// @Check(constraint="constr_1017")
	// def void constr_1017( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1018 SwImplPolicyEnum.measurementPoint shall not be referenced by a VariableAccess aggregated by
	 * RunnableEntity in the role RunnableEntity.dataReadAccess Due to the nature of SenderReceiverInterface.data
	 * elements characterized by setting the SwDataDefProps.swImplPolicy to SwImplPolicyEnum.measurementPoint, such
	 * SenderReceiverInterface.data elements shall not be referenced by a VariableAccess aggregated by RunnableEntity in
	 * the role RunnableEntity.dataReadAccess.
	 */
	// @Check(constraint="constr_1018")
	// def void constr_1018( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1019 Compatibility of input value and axis The SwDataDefProps the input variable shall be compatible to
	 * the SwAxisIndividual.datatype resp. SwAxisIndividual.compuMethod resp. SwAxisIndividual.unit of the
	 * SwAxisIndividual.
	 */
	// @Check(constraint="constr_1019")
	// def void constr_1019( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1020 ParameterDataPrototype needs to be of compatible data type as referenced in
	 * SwAxisGrouped.sharedAxisType Finally, the ParameterDataPrototype assigned in SwAxisGrouped.swCalprmRef shall be
	 * typed by data type compatible to SwAxisGrouped.sharedAxisType.
	 */
	// @Check(constraint="constr_1020")
	// def void constr_1020( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1021 A CompuMethod shall specify instructions for both directions The forward and inverse direction shall
	 * always be clearly determined either by
	 */
	// @Check(constraint="constr_1021")
	// def void constr_1021( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1022 Limits shall be defined for each direction of CompuMethod In case that both domains are specified in
	 * the CompuMethod both shall have explicitly defined limits.
	 */
	// @Check(constraint="constr_1022")
	// def void constr_1022( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1024 Stepwise definition of CompuMethods Within AUTOSAR only the stepwise definition (CompuScales) is
	 * used.
	 */
	// @Check(constraint="constr_1024")
	// def void constr_1024( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1025 Avoid division by zero in rational formula The rational formula shall not yield any division by zero.
	 */
	// @Check(constraint="constr_1025")
	// def void constr_1025( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1026 Compatibility of Units For data types or prototypes, units should be referenced from within the
	 * associated CompuMethod. But if it is referenced from within SwDataDefProps and/or PhysConstrs (for exceptional
	 * use cases) it shall be compatible (for more details please refer to constr_1052) to the ones referenced from the
	 * referred CompuMethod.
	 */
	// @Check(constraint="constr_1026")
	// def void constr_1026( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1027 Types for record layouts Because ParameterDataPrototypes have a isOfType-relation to
	 * ApplicationDataTypes or ImplementationDataTypes the related data types shall properly match to the details as
	 * specified in AutosarDataType.swDataDefProps.
	 */
	// @Check(constraint="constr_1027")
	// def void constr_1027( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1029 ConstantSpecificationMapping and ConstantSpecification It is required that one ConstantSpecification
	 * referenced from a ConstantSpecificationMapping needs to be defined in the application domain
	 * (ConstantSpecificationMapping.applConstant) and the other referenced ConstantSpecification needs to be defined in
	 * the implementation domain (ConstantSpecificationMapping.implConstant).
	 */
	// @Check(constraint="constr_1029")
	// def void constr_1029( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1030 ParameterSwComponentType references ConstantSpecificationMappingSet ParameterSwComponentType: here
	 * the ConstantSpecificationMappingSet is directly associated by the ParameterSwComponentType.
	 */
	// @Check(constraint="constr_1030")
	// def void constr_1030( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1031 NvBlockSwComponentType references ConstantSpecificationMappingSet NvBlockSwComponentType: in this
	 * case the ConstantSpecificationMappingSet is associated with the aggregated NvBlockDescriptor.
	 */
	// @Check(constraint="constr_1031")
	// def void constr_1031( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1032 DelegationSwConnector can only connect PortPrototypes of the same kind A DelegationSwConnector can
	 * only connect PortPrototypes of the same kind, i.e. PPortPrototype to PPortPrototype and RPortPrototype to
	 * RPortPrototype.
	 */
	// @Check(constraint="constr_1032")
	// def void constr_1032( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1033 Communication scenarios for sender/receiver communication For sender/receiver communication, it is
	 * not allowed to create a communication scenario where n sender are connected to m receivers where m and n are both
	 * greater than 1.
	 */
	// @Check(constraint="constr_1033")
	// def void constr_1033( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1035 Recursive definition of CompositionSwComponentType The recursive definition of a
	 * CompositionSwComponentType that eventually contains a SwComponentPrototype typed by the same
	 * CompositionSwComponentType shall not be feasible.
	 */
	// @Check(constraint="constr_1035")
	// def void constr_1035( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1036 Connect kinds of PortInterfaces It shall not be possible to connect PortPrototypes typed by
	 * PortInterfaces of different kinds. Subclasses of DataInterface make an exception from this rule and can be used
	 * for creating connections to each other.
	 */
	// @Check(constraint="constr_1036")
	// def void constr_1036( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1037 Client shall not be connected to multiple servers A client shall not be connected to multiple servers
	 * such that an operation call would be handled by more than one server.
	 */
	// @Check(constraint="constr_1037")
	// def void constr_1037( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1038 Reference to ApplicationError A ClientServerOperation.possibleError referenced by a
	 * ClientServerOperation shall be owned by the ClientServerInterface that also owns the ClientServerOperation.
	 */
	// @Check(constraint="constr_1038")
	// def void constr_1038( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1039 Relevance of SwDataDefProps.swImplPolicy It is not possible to define a mapping between an element
	 * where the SwDataDefProps.swImplPolicy is set to SwImplPolicyEnum.queued and an other element where the
	 * SwDataDefProps.swImplPolicy is set differently.
	 */
	// @Check(constraint="constr_1039")
	// def void constr_1039( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1040 Conversion of SenderReceiverInterfaces Either the AutosarDataTypes of the referred DataPrototypes are
	 * compatible as described in chapter~chap:Compatibility_of_Data_Types or a conversion of the data as described in
	 * chapter ~chap:Data Conversion is available.
	 */
	// @Check(constraint="constr_1040")
	// def void constr_1040( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1041 Conversion of ClientServerInterfaces Either the AutosarDataTypes of the referred
	 * ArgumentDataPrototypes are compatible as described in chapter~chap:Compatibility_of_Data_Types or a conversion of
	 * the data as described in chapter~chap:Data Conversion is available.
	 */
	// @Check(constraint="constr_1041")
	// def void constr_1041( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1043 PortInterface vs. ComSpec The allowed combinations of a specific kind of PortInterface and a kind of
	 * ComSpec are documented in Table~table:Port_Interface_vs_Com_Spec.
	 */
	// @Check(constraint="constr_1043")
	// def void constr_1043( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1044 Applicability of DataFilter According to the origin of DataFilter, i.e. OSEK COM 3.0.3
	 * specification~OSEK-COM, DataFilters can only be applied to values with an integer base type.
	 */
	// @Check(constraint="constr_1044")
	// def void constr_1044( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1045 Supported value encodings for SwBaseType in the context of PortInterfaces The supported value
	 * encodings for the usage within a PortInterface are:
	 */
	// @Check(constraint="constr_1045")
	// def void constr_1045( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1046 Applicability of constr_1045 constr_1045 applies only if the value of the attribute
	 * PortInterface.isService is set to false.
	 */
	// @Check(constraint="constr_1046")
	// def void constr_1046( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1047 Compatibility of ApplicationPrimitiveDataTypes Instances of ApplicationPrimitiveDataType are
	 * compatible if and only if one of the following conditions applies:
	 */
	// @Check(constraint="constr_1047")
	// def void constr_1047( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1048 Compatibility of ApplicationRecordDataTypes Instances of ApplicationRecordDataTypes are compatible if
	 * and only if one of the following conditions applies:
	 */
	// @Check(constraint="constr_1048")
	// def void constr_1048( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1049 Compatibility of ApplicationArrayDataTypes Instances of ApplicationArrayDataType are compatible if
	 * and only if one of the following conditions applies:
	 */
	// @Check(constraint="constr_1049")
	// def void constr_1049( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1050 Compatibility of ImplementationDataTypes Instances of ImplementationDataType are compatible if and
	 * only if after all type-references are resolved one of the following rules apply:
	 */
	// @Check(constraint="constr_1050")
	// def void constr_1050( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1051 Compatibility of SwDataDefProps SwDataDefProps are compatible if and only if:
	 */
	// @Check(constraint="constr_1051")
	// def void constr_1051( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1052 Compatibility of Units Two Unit definitions are compatible if and only if:
	 */
	// @Check(constraint="constr_1052")
	// def void constr_1052( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1053 Compatibility of PhysicalDimensions Two PhysicalDimension definitions are compatible if and only if
	 * the values of
	 */
	// @Check(constraint="constr_1053")
	// def void constr_1053( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1054 No DataConstr available at the provider If the provider defines no constraints it is only compatible
	 * with a receiver which also defines no constraints at all.
	 */
	// @Check(constraint="constr_1054")
	// def void constr_1054( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1055 ImplementationDataType has Identifiable.categoryARTechTerm_VALUE The attributes
	 * SwDataDefProps.baseType shall refer to a compatible SwBaseType
	 */
	// @Check(constraint="constr_1055")
	// def void constr_1055( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1056 ImplementationDataType has Identifiable.categoryARTechTerm_TYPE_REFERENCE The ImplementationDataTypes
	 * referenced by the attributes SwDataDefProps.SwDataDefProps.implementationDataType shall be compatible .
	 */
	// @Check(constraint="constr_1056")
	// def void constr_1056( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1057 ImplementationDataType has Identifiable.categoryARTechTerm_DATA_REFERENCE The attributes
	 * SwDataDefProps.SwDataDefProps.swPointerTargetProps shall have identical SwPointerTargetProps.targetCategory and
	 * shall refer to SwDataDefProps where all attributes are identical
	 */
	// @Check(constraint="constr_1057")
	// def void constr_1057( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1058 ImplementationDataType has Identifiable.categoryARTechTerm_FUNCTION_REFERENCE The attributes
	 * SwDataDefProps.SwDataDefProps.swPointerTargetProps.SwPointerTargetProps.function PointerSignature shall refer to
	 * BswModuleEntrys which each resolve to the same function signature.
	 */
	// @Check(constraint="constr_1058")
	// def void constr_1058( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1059 Compatibility of data types with Identifiable.categoryARTechTerm_VALUE An ApplicationDataType of
	 * Identifiable.categoryARTechTerm_VALUE can only be mapped/connected to an ImplementationDataType which also has
	 * Identifiable.categoryARTechTerm_VALUE.
	 */
	// @Check(constraint="constr_1059")
	// def void constr_1059( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1060 Compatibility of data types with Identifiable.categoryARTechTerm_ARRAY, ARTechTerm_VAL_BLK An
	 * ApplicationDataType of Identifiable.categoryARTechTerm_ARRAY, ARTechTerm_VAL_BLK can only be mapped/connected to
	 * an ImplementationDataType of Identifiable.categoryARTechTerm_ARRAY.
	 */
	// @Check(constraint="constr_1060")
	// def void constr_1060( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1061 Compatibility of data types with Identifiable.categoryARTechTerm_STRUCTURE An ApplicationDataType of
	 * Identifiable.categoryARTechTerm_STRUCTURE can only be mapped/connected to an ImplementationDataType of
	 * Identifiable.categoryARTechTerm_STRUCTURE.
	 */
	// @Check(constraint="constr_1061")
	// def void constr_1061( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1063 Compatibility of data types with Identifiable.categoryARTechTerm_BOOLEAN An ApplicationDataType of
	 * Identifiable.categoryARTechTerm_BOOLEAN can only be mapped/connected to an ImplementationDataType of
	 * Identifiable.categoryARTechTerm_VALUE.
	 */
	// @Check(constraint="constr_1063")
	// def void constr_1063( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1064 Compatibility of data types with Identifiable.categoryARTechTerm_COM_AXIS, ARTechTerm_RES_AXIS,
	 * ARTechTerm_CURVE, ARTechTerm_MAP, ARTechTerm_CUBOID, ARTechTerm_CUBE_4, or ARTechTerm_CUBE_5 An
	 * ApplicationDataType of Identifiable.categoryARTechTerm_COM_AXIS, ARTechTerm_RES_AXIS, ARTechTerm_CURVE,
	 * ARTechTerm_MAP, ARTechTerm_CUBOID, ARTechTerm_CUBE_4, or ARTechTerm_CUBE_5 can only be mapped/connected to an
	 * ImplementationDataType of Identifiable.categoryARTechTerm_STRUCTURE or ARTechTerm_ARRAY.
	 */
	// @Check(constraint="constr_1064")
	// def void constr_1064( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1066 Forbidden mappings to ImplementationDataType An ApplicationDataType shall never be mapped to an
	 * ImplementationDataType of of Identifiable.categoryARTechTerm_UNION, ARTechTerm_DATA_REFERENCE, or
	 * ARTechTerm_FUNCTION_REFERENCE.
	 */
	// @Check(constraint="constr_1066")
	// def void constr_1066( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1068 Compatibility of VariableDataPrototypes or ParameterDataPrototypes typed by primitive data types Two
	 * VariableDataPrototypes or ParameterDataPrototypes of ApplicationPrimitiveDataTypes or ImplementationDataTypes of
	 * Identifiable.categoryARTechTerm_VALUE, ARTechTerm_BOOLEAN, or ARTechTerm_STRING are compatible if and only if one
	 * of the following conditions applies:
	 */
	// @Check(constraint="constr_1068")
	// def void constr_1068( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1069 Compatibility of PortPrototypes of different DataInterfaces in the context of AssemblySwConnectors
	 * PortPrototypes of different DataInterfaces are compatible if and only if
	 */

	@Check(constraint = "constr_1069")
	def void constr_1069(AssemblySwConnector assemblySwConnector) {
		if (assemblySwConnector.getProvider().getTargetPPort() instanceof PPortPrototype) {
			val PPortPrototype pPortPrototype = assemblySwConnector.getProvider().getTargetPPort() as PPortPrototype
			val PortInterfaceCompatibility compatibility = new org.artop.aal.autosar40.services.predicates.compatibility.PortInterfaceCompatibility(pPortPrototype.getProvidedInterface())
			if (!compatibility.apply((assemblySwConnector.getRequester().getTargetRPort() as RPortPrototype).getRequiredInterface()))
				issue(assemblySwConnector, CompositionPackage.Literals.ASSEMBLY_SW_CONNECTOR__PROVIDER)
		}
	}

	/**
	 * constr_1070 Compatibility of PortPrototypes of different DataInterfaces in the context of DelegationSwConnectors
	 * PortPrototypes of different DataInterfaces are compatible if and only if
	 */
	// @Check(constraint="constr_1070")
	// def void constr_1070( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1071 compatibility of ParameterDataPrototype and VariableDataPrototype Combinations of
	 * ParameterDataPrototype and VariableDataPrototype used in PortPrototypes typed by various kinds of PortInterfaces
	 * shall only be allowed where Table tab:Overview of compatibility of ParameterDataPrototype and
	 * VariableDataPrototype contains the value ``yes''.
	 */
	// @Check(constraint="constr_1071")
	// def void constr_1071( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1072 Compatibility of ModeSwitchInterfaces in the context of an AssemblySwConnector PortPrototypes of
	 * different ModeSwitchInterfaces are compatible if and only if
	 */
	// @Check(constraint="constr_1072")
	// def void constr_1072( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1073 Compatibility of ModeSwitchInterfaces in the context of an DelegationSwConnector PortPrototypes of
	 * different ModeSwitchInterfaces are compatible if and only if
	 */
	// @Check(constraint="constr_1073")
	// def void constr_1073( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1074 Compatibility of ModeDeclarationGroupPrototypes ModeDeclarationGroupPrototypes are compatible if and
	 * only if one of the following conditions applies:
	 */
	// @Check(constraint="constr_1074")
	// def void constr_1074( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1075 Compatibility of ModeDeclarationGroups ModeDeclarationGroups are compatible if and only if one of the
	 * following conditions applies:
	 */
	// @Check(constraint="constr_1075")
	// def void constr_1075( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1076 Compatibility of ArgumentDataPrototypes Two ArgumentDataPrototypes are compatible if and only if
	 */
	// @Check(constraint="constr_1076")
	// def void constr_1076( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1077 Compatibility of ApplicationErrors Two ApplicationErrors are compatible if and only if one of the
	 * following conditions applies:
	 */
	// @Check(constraint="constr_1077")
	// def void constr_1077( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1078 Compatibility of ClientServerOperations Two ClientServerOperations are compatible if their signatures
	 * match. In particular, they are compatible if and only if
	 */
	// @Check(constraint="constr_1078")
	// def void constr_1078( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1079 Compatibility of ClientServerInterfaces in the context of an AssemblySwConnector
	 * ClientServerInterfaces are compatible if and only if
	 */
	// @Check(constraint="constr_1079")
	// def void constr_1079( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1080 Compatibility of ClientServerInterfaces in the context of an DelegationSwConnector
	 * ClientServerInterfaces are compatible if and only if
	 */
	// @Check(constraint="constr_1080")
	// def void constr_1080( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1081 Compatibility of TriggerInterfaces in the context of an AssemblySwConnector TriggerInterfaces are
	 * compatible if and only if
	 */
	// @Check(constraint="constr_1081")
	// def void constr_1081( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1082 Compatibility of TriggerInterfaces in the context of an DelegationSwConnector TriggerInterfaces are
	 * compatible if and only if all of the following conditions apply:
	 */
	// @Check(constraint="constr_1082")
	// def void constr_1082( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1083 Compatibility of Triggers Triggers are compatible if they have an identical Referrable.shortName.
	 */
	// @Check(constraint="constr_1083")
	// def void constr_1083( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1084 delegation of a provided outer PortPrototype The delegation of a provided outer PortPrototype is
	 * properly defined if the following criteria are fulfilled:
	 */
	// @Check(constraint="constr_1084")
	// def void constr_1084( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1085 Compatibility in the case of a flat ECU extract PortPrototypes of different SenderReceiverInterfaces,
	 * NvDataInterfaces, and Para meterInterfaces are compatible if and only if for at least one VariableDataProto type
	 * or ParameterDataPrototype defined in the context of the SenderReceiverInte rface, NvDataInterface, or
	 * ParameterInterface of the RPortPrototype a compatible VariableDataPrototype or ParameterDataPrototype exists in
	 * the SenderReceiverInt erface, NvDataInterface, or ParameterInterface of the provided PortPrototype. The
	 * compatibility of PortInterface elements depends on the kind of PortInterface and the SwDataDefProps.swImplPolicy
	 * attributes of the PortInterface elements.
	 */
	// @Check(constraint="constr_1085")
	// def void constr_1085( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1086 SwConnector between two specific PortPrototypes Each pair of PortPrototypes can only be connected by
	 * one and only one SwConnector.
	 */
	// @Check(constraint="constr_1086")
	// def void constr_1086( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1087 AssemblySwConnector inside CompositionSwComponentType An AssemblySwConnector can only connect
	 * PortPrototypes of SwComponentPrototypes that are owned by the same CompositionSwComponentType
	 */
	@Check(constraint = "constr_1087")
	def void constr_1087(AssemblySwConnector assemblySwConnector) {
		if (assemblySwConnector.getCompositionSwComponentType() != assemblySwConnector.getProvider().getContextComponent().getCompositionSwComponentType())
			issue(assemblySwConnector, CompositionPackage.Literals.ASSEMBLY_SW_CONNECTOR__PROVIDER)

		if (assemblySwConnector.getCompositionSwComponentType() != assemblySwConnector.getRequester().getContextComponent().getCompositionSwComponentType())
			issue(assemblySwConnector, CompositionPackage.Literals.ASSEMBLY_SW_CONNECTOR__REQUESTER)
	}

	/**
	 * constr_1088 DelegationSwConnector inside CompositionSwComponentType A DelegationSwConnector can only connect a
	 * PortPrototype of a SwComponentPrototype that is owned by the same CompositionSwComponentType that also owns the
	 * connected delegation PortPrototype.
	 */
	// @Check(constraint="constr_1088")
	// def void constr_1088( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1090 WaitPoint and RunnableEntity A single RunnableEntity can actually wait only at a single WaitPoint
	 * provided that the RunnableEntity can only be scheduled a single timeThis constraint is valid at least in the OSEK
	 * standard where an extended task (that can have wait points) can only exist a single time in the context of the
	 * scheduler..
	 */
	// @Check(constraint="constr_1090")
	// def void constr_1090( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1091 RTEEvents that can unblock a WaitPoint The only RTEEvents that are qualified for unblocking a
	 * WaitPoint are:
	 */
	// @Check(constraint="constr_1091")
	// def void constr_1091( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1092 ParameterSwComponentType A ParameterSwComponentType shall never aggregate a SwcInternalBehavior and
	 * also owns exclusively PPortPrototypes of type ParameterInterface.
	 */
	// @Check(constraint="constr_1092")
	// def void constr_1092( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1093 Definition of textual strings An ApplicationPrimitiveDataType of
	 * Identifiable.categoryARTechTerm_STRING shall have a SwDataDefProps.swTextProps which determines the
	 * ImplementationDataTypeElement.arraySizeSemantics and SwTextProps.swMaxTextSize.
	 */
	@Check(constraint = "constr_1093")
	def void constr_1093(ApplicationPrimitiveDataType applicationPrimitiveDataType) {
		if (applicationPrimitiveDataType.getCategory().equals("STRING")) {
			issuePred(applicationPrimitiveDataType.getSwDataDefProps().getSwDataDefPropsVariants(),
				[SwDataDefPropsConditional x | x.getSwTextProps() === null || x.getSwTextProps().getArraySizeSemantics() === null
					|| x.getSwTextProps().getSwMaxTextSize() === null
				], null)
		}
	}

	/**
	 * constr_1095 Values of NvBlockNeeds.nDataSets vs. NvBlockNeeds.reliability If the value of NvBlockNeeds.nDataSets
	 * is greater than 0 the value of NvBlockNeeds.reliability shall not be set to
	 * NvBlockNeedsReliabilityEnum.errorCorrection.
	 */
	// @Check(constraint="constr_1095")
	// def void constr_1095( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1096 SwcModeSwitchEvent and WaitPoint A RunnableEntity that has a WaitPoint shall not be referenced by a
	 * SwcModeSwitchEvent.
	 */
	// @Check(constraint="constr_1096")
	// def void constr_1096( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1097 RunnableEntity that has a WaitPoint A RunnableEntity that has a WaitPoint shall not be referenced by
	 * a RTEEvent that has a reference in the role RTEEvent.disabledMode.
	 */
	// @Check(constraint="constr_1097")
	// def void constr_1097( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1098 Mode switch and mode disabling A SwcModeSwitchEvent shall not simultaneously reference to the same
	 * ModeDeclaration in both the roles SwcModeSwitchEvent.mode and RTEEvent.disabledMode.
	 */
	// @Check(constraint="constr_1098")
	// def void constr_1098( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1100 Unconnected RPortPrototype typed by a DataInterface For any element in an unconnected RPortPrototype
	 * typed by a DataInterface there shall be a RPortPrototype.requiredComSpec that defines an
	 * NonqueuedReceiverComSpec.initValue.
	 */
	// @Check(constraint="constr_1100")
	// def void constr_1100( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1101 Mode-related communication An RPortPrototype typed by ModeSwitchInterface shall not be referenced by
	 * more than one SwConnector.
	 */
	// @Check(constraint="constr_1101")
	// def void constr_1101( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1102 ApplicationError in the scope of one SwComponentType A SwComponentType may have PortPrototypes typed
	 * by different PortInterfaces with equal Referrable.shortName but conflicting ApplicationErrors.
	 */
	// @Check(constraint="constr_1102")
	// def void constr_1102( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1103 NonqueuedReceiverComSpec and NonqueuedReceiverComSpec.enableUpdate A NonqueuedReceiverComSpec that
	 * has attribute NonqueuedReceiverComSpec.enableUpdate set to true may not reference a
	 * SenderReceiverInterface.dataElement that in turn is referenced by a VariableAccess in the role
	 * RunnableEntity.dataReadAccess.
	 */
	// @Check(constraint="constr_1103")
	// def void constr_1103( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1104 Trigger sink and trigger source An RPortPrototype typed by a TriggerInterface shall not be referenced
	 * by more than one SwConnectors that are in turn referencing PPortPrototypes typed by TriggerInterfaces that
	 * contain Triggers with the same Referrable.shortName.
	 */
	// @Check(constraint="constr_1104")
	// def void constr_1104( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1105 Value of ImplementationDataTypeElement.arraySize The value of the attribute
	 * ImplementationDataTypeElement.arraySize of an ImplementationDataTypeElement owned by an ImplementationDataType or
	 * ImplementationDataTypeElement of Identifiable.categoryARTechTerm_ARRAY shall be greater than 0.
	 */
	// @Check(constraint="constr_1105")
	// def void constr_1105( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1106 Structure shall have at least one element An ImplementationDataType or ImplementationDataTypeElement
	 * of Identifiable.categoryARTechTerm_STRUCTURE shall own at least one ImplementationDataTypeElement.
	 */
	// @Check(constraint="constr_1106")
	// def void constr_1106( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1107 Union shall have at least one element An ImplementationDataType or ImplementationDataTypeElement of
	 * Identifiable.categoryARTechTerm_UNION shall own at least one ImplementationDataTypeElement.
	 */
	@Check(constraint = "constr_1107")
	def void constr_1107(ImplementationDataType implDataType) {
		if (implDataType.getCategory().equals("UNION") && implDataType.getSubElements().isEmpty())
			issue(implDataType, ImplementationdatatypesPackage.Literals.IMPLEMENTATION_DATA_TYPE__SUB_ELEMENTS, 0, null)
	}

	/**
	 * constr_1108 Value of ApplicationError.ApplicationError.errorCode The value of
	 * ApplicationError.ApplicationError.errorCode shall not exceed the closed interval 1 .. 63. The following exception
	 * applies: only in case ClientServerInterface.possibleError is supposed to represent E_OK the value 0 shall be be
	 * allowed.
	 */
	@Check(constraint = "constr_1108")
	def void constr_1108(ApplicationError applicationError) {
		if (applicationError.getShortName().equals(E_OK))
			rangeIssue(applicationError, PortinterfacePackage.Literals.APPLICATION_ERROR__ERROR_CODE, 0, 63)
		else
			rangeIssue(applicationError, PortinterfacePackage.Literals.APPLICATION_ERROR__ERROR_CODE, 1, 63)
	}

	/**
	 * constr_1109 Mapping of SwComponentPrototypes typed by a SensorActuatorSwComponentType A SwComponentPrototype
	 * typed by a SensorActuatorSwComponentType needs to be mapped and run on exactly that ECU that contains the
	 * HwElement corresponding to the HwType that its SensorActuatorSwComponentType refers to in case it accesses the
	 * hardware via the I/O hardware abstraction layer.
	 */
	// @Check(constraint="constr_1109")
	// def void constr_1109( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1110 Value of Identifiable.category in EndToEndDescription The attribute Identifiable.category of
	 * EndToEndDescription can have the following values:
	 */
	@Check(constraint = "constr_1110")
	def void constr_1110(EndToEndDescription description) {
		stringValuesIssue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__CATEGORY, constr_1110)
	}

	/**
	 * constr_1111 Constraints of EndToEndDescription.dataId in PROFILE_01 In PROFILE_01, there shall be only one
	 * element in the set and the applicable range of values is [0~..~65535].
	 */
	@Check(constraint = "constr_1111")
	def void constr_1111(EndToEndDescription description) {
		if (description.getCategory().equals(PROFILE_01)) {
			if (description.getDataIds().size() > 1)
				issue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__DATA_IDS, null)

			if (description.getDataIds().size() == 1 && (description.getDataIds().get(0) < 0 || description.getDataIds().get(0) > 65535))
				issue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__DATA_IDS, 0, #[description.getDataIds().get(0)])
		}
	}

	/**
	 * constr_1112 Constraints of EndToEndDescription.dataIdMode in PROFILE_01 In PROFILE_01, the applicable range of
	 * values for dataIdMode is [0~..~3].
	 */
	@Check(constraint = "constr_1112")
	def void constr_1112(EndToEndDescription description) {
		if (description.getCategory().equals(PROFILE_01))
			rangeIssue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__DATA_ID_MODE, 0, 3)
	}

	/**
	 * constr_1113 Existence of attributes in PROFILE_01 In PROFILE_01, the following attributes shall exist:
	 */
	@Check(constraint = "constr_1113")
	def void constr_1113(EndToEndDescription description) {
		if (description.getCategory().equals(PROFILE_01)) {
			if (description.getDataLength() === null || description.getDataIds().isEmpty())
				issue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__DATA_IDS, null)
		}
	}

	/**
	 * constr_1114 Constraints of EndToEndDescription.crcOffset in PROFILE_01 In PROFILE_01, the applicable range of
	 * values for EndToEndDescription.crcOffset is [0~..~65535]. For the value of this attribute the constraint
	 * value~mod~4~=~0 applies.
	 */
	@Check(constraint = "constr_1114")
	def void constr_1114(EndToEndDescription description) {
		if (description.getCategory().equals(PROFILE_01)) {
			rangeIssue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__CRC_OFFSET, 0, 65535)
			if (description.getCrcOffset() % 4 != 0)
				issue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__CRC_OFFSET)
		}
	}

	/**
	 * constr_1115 Constraints of EndToEndDescription.counterOffset in PROFILE_01 In PROFILE_01, the applicable range of
	 * values for EndToEndDescription.counterOffset is [0~..~65535]. For the value of this attribute the constraint
	 * value~mod~4~=~0 applies.
	 */
	@Check(constraint = "constr_1115")
	def void constr_1115(EndToEndDescription description) {
		if (description.getCategory().equals(PROFILE_01)) {
			rangeIssue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__COUNTER_OFFSET, 0, 65535)
			if (description.getCounterOffset() % 4 != 0)
				issue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__COUNTER_OFFSET)
		}
	}

	/**
	 * constr_1116 Constraints of EndToEndDescription.dataLength in PROFILE_01 In PROFILE_01, the applicable range of
	 * values for EndToEndDescription.dataLength is [0~..~240]. For the value of this attribute the constraint
	 * value~mod~8~=~0 applies.
	 */
	// @Check(constraint="constr_1116")
	// def void constr_1116( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1117 Constraints of EndToEndDescription.maxDeltaCounterInit in PROFILE_01 In PROFILE_01, the applicable
	 * range of values for EndToEndDescription.EndToEndDescription.maxDeltaCounterInit and
	 * ReceiverComSpec.ReceiverComSpec.maxDeltaCounterInit is [0~..~14].
	 */
	// @Check(constraint="constr_1117")
	// def void constr_1117( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1118 Existence of attributes in PROFILE_02 In PROFILE_02, only the following attributes shall exist:
	 */
	// @Check(constraint="constr_1118")
	// def void constr_1118( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1119 Constraints of EndToEndDescription.dataLength in PROFILE_02 In PROFILE_02, the applicable range of
	 * values for EndToEndDescription.dataLength is [0~..~65535]. For the value of this attribute the constraint
	 * value~mod~8~=~0 applies.
	 */
	// @Check(constraint="constr_1119")
	// def void constr_1119( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1120 Constraints of EndToEndDescription.dataId in PROFILE_02 In PROFILE_02, there shall be exactly ordered
	 * 16 elements in the set and the applicable range of values is [0~..~255].
	 */
	@Check(constraint = "constr_1120")
	def void constr_1120(EndToEndDescription description) {
		if (description.getCategory().equals(PROFILE_02)) {
			if (description.getDataIds().size() != 16)
				issue(description, EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__DATA_IDS, null)

			issuePred(description.getDataIds(), [Long x | x < 0 || x > 255], EndtoendprotectionPackage.Literals.END_TO_END_DESCRIPTION__DATA_IDS)
		}
	}

	/**
	 * constr_1121 Constraints of EndToEndDescription.maxDeltaCounterInit in PROFILE_02 In PROFILE_02, the applicable
	 * range of values for EndToEndDescription.EndToEndDescription.maxDeltaCounterInit and
	 * ReceiverComSpec.ReceiverComSpec.maxDeltaCounterInit is [0~..~15].
	 */
	// @Check(constraint="constr_1121")
	// def void constr_1121( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1126 Compatibility of DataConstrs The DataConstr (e.g. the limits) defined by the type of the providing
	 * data element shall be within the constraints defined by the type of the requiring data element.
	 */
	// @Check(constraint="constr_1126")
	// def void constr_1126( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1128 Queue length of ClientServerOperations associated with the same RunnableEntity If two or more
	 * OperationInvokedEvents reference a single RunnableEntity the value of the ServerComSpec attribute
	 * ServerComSpec.queueLength shall be identical for all ServerComSpecs owned by PPortPrototypes of the enclosing
	 * SwComponentType that reference one of the ClientServerOperations that are also referenced by the
	 * OperationInvokedEvents.
	 */
	// @Check(constraint="constr_1128")
	// def void constr_1128( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1129 SwDataDefProps.swImplPolicy and NonqueuedReceiverComSpec The attribute SwDataDefProps.swImplPolicy of
	 * a SenderReceiverInterface.dataElement referenced by a NonqueuedReceiverComSpecshall not be set to the value
	 * SwImplPolicyEnum.queued.
	 */
	// @Check(constraint="constr_1129")
	// def void constr_1129( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1130 SwDataDefProps.swImplPolicy and QueuedReceiverComSpec The attribute SwDataDefProps.swImplPolicy of a
	 * SenderReceiverInterface.dataElement referenced by a QueuedReceiverComSpecshall be set to the value
	 * SwImplPolicyEnum.queued.
	 */
	// @Check(constraint="constr_1130")
	// def void constr_1130( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1131 SwDataDefProps.swImplPolicy and NonqueuedSenderComSpec The attribute SwDataDefProps.swImplPolicy of a
	 * SenderReceiverInterface.dataElement referenced by a NonqueuedSenderComSpecshall not be set to the value
	 * SwImplPolicyEnum.queued.
	 */
	// @Check(constraint="constr_1131")
	// def void constr_1131( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1132 SwDataDefProps.swImplPolicy and QueuedSenderComSpec The attribute SwDataDefProps.swImplPolicy of a
	 * SenderReceiverInterface.dataElement referenced by a QueuedSenderComSpecshall be set to the value
	 * SwImplPolicyEnum.queued.
	 */
	// @Check(constraint="constr_1132")
	// def void constr_1132( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1133 Identical CompuScale Symbolic Names shall have the same range In a CompuMethod that is subject to
	 * constr_1146, allCompuScales that yield identical CompuScale Symbolic Names shall have the same range defined by
	 * CompuScale.CompuScale.lowerLimit and CompuScale.CompuScale.upperLimit.
	 */
	// @Check(constraint="constr_1133")
	// def void constr_1133( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1134 Allowed structure of ARTechTerm_TEXTTABLE CompuMethod.physConstr is not allowed.
	 * CompuMethod.compuInternalToPhys shall exist with CompuScales.compuScales consisting of CompuScale.upperLimit and
	 * CompuScale.lowerLimit.
	 */
	// @Check(constraint="constr_1134")
	// def void constr_1134( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1135 Limit of CompuConstTextContent.vt in ARTechTerm_BITFIELD_TEXTTABLE The separator is ``|'' and is
	 * forbidden in CompuConstTextContent.vt therefore.
	 */
	// @Check(constraint="constr_1135")
	// def void constr_1135( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1137 Applicability of ParameterInterface A PPortPrototype typed by a ParameterInterface can only be owned
	 * by a ParameterSwComponentType.
	 */
	// @Check(constraint="constr_1137")
	// def void constr_1137( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1138 RoleBasedPortAssignment.assignedPort and DiagEventDebounceMonitorInternal The existence of an
	 * RoleBasedPortAssignment.assignedPort in combination with a DiagEventDebounceAlgorithm shall only be respected for
	 * the concrete subclass DiagEventDebounceMonitorInternal.
	 */
	// @Check(constraint="constr_1138")
	// def void constr_1138( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1139 RoleBasedPortAssignment.assignedPort of DiagEventDebounceMonitorInternal shall refer to an
	 * RPortPrototype Concerning the debouncing, the software-component acts as a client and thus the
	 * RoleBasedPortAssignment.assignedPort defined with respect to a DiagEventDebounceMonitorInternal may only refer to
	 * an RPortPrototype. The standardized value of the RoleBasedPortAssignment.role identifier of the
	 * RoleBasedPortAssignment.assignedPort shall be DiagFaultDetectionCounterPort.
	 */
	// @Check(constraint="constr_1139")
	// def void constr_1139( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1140 Combination of SwDataDefProps.invalidValue with the attribute InvalidationPolicy.handleInvalid The
	 * combination of setting the attribute InvalidationPolicy.handleInvalid of the meta-class InvalidationPolicy owned
	 * by SenderReceiverInterface to value replaceand of setting the value of the attribute
	 * NonqueuedReceiverComSpec.initValue owned by a corresponding NonqueuedReceiverComSpec effectively to the value of
	 * the SwDataDefProps.invalidValue (owned by a corresponding SwDataDefProps) is not supported.
	 */
	// @Check(constraint="constr_1140")
	// def void constr_1140( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1141 Applicability of the VariableAccess.scope attribute The attribute VariableAccess.scope of meta-class
	 * VariableAccess shall only be applied with respect to the aggregation of VariableAccess in the following roles:
	 */
	// @Check(constraint="constr_1141")
	// def void constr_1141( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1142 Identifiable.category of CompuMethod shall not be extended In contrast to the general rule that
	 * Identifiable.category can be extended by user-specific values it is not allowed to extend the meaning of the
	 * attribute Identifiable.category of meta-class CompuMethod
	 */
	// @Check(constraint="constr_1142")
	// def void constr_1142( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1143 Identifiable.category of AutosarDataType shall not be extended In contrast to the general rule that
	 * Identifiable.category can be extended by user-specific values it is not allowed to extend the meaning of the
	 * attribute Identifiable.category of meta-class AutosarDataType
	 */
	// @Check(constraint="constr_1143")
	// def void constr_1143( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1144 SensorActuatorSwComponentType, EcuAbstractionSwComponentType, and ComplexDeviceDriverSwComponentType
	 * may only reference a HwType The attribute SensorActuatorSwComponentType.sensorActuator of
	 * SensorActuatorSwComponentType, the attribute EcuAbstractionSwComponentType.hardwareElement of
	 * EcuAbstractionSwComponentType, and the attribute ComplexDeviceDriverSwComponentType.hardwareElement of
	 * ComplexDeviceDriverSwComponentType may only reference a HwType. References to other subclasses of
	 * HwDescriptionEntity are not allowed.
	 */
	// @Check(constraint="constr_1144")
	// def void constr_1144( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1146 Applicability of a symbol for a CompuScale in C code The CompuScale.symbol attribute shall only be
	 * provided for CompuScales where the Identifiable.category of the enclosing CompuMethod is one of the following:
	 */
	// @Check(constraint="constr_1146")
	// def void constr_1146( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1147 Standardized values for the attribute Identifiable.category of meta-class PortGroup The following
	 * values of the attribute Identifiable.category of meta-class PortGroup are reserved by the AUTOSAR standard:
	 */
	// @Check(constraint="constr_1147")
	// def void constr_1147( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1148 PortInterfaces of PortPrototypes used to connect to NvBlockSwComponentTypes PortInterfaces of
	 * PortPrototypes used to connect to NvBlockSwComponentTypes as well as the PortInterfaces used in the context of
	 * NvBlockSwComponentTypes shall always set the value of the attribute PortInterface.isService to false.
	 */
	// @Check(constraint="constr_1148")
	// def void constr_1148( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1149 PortPrototypes used for NV data management A PortPrototype typed by a ClientServerInterface used for
	 * NV data management, i.e. the interaction of ApplicationSwComponentTypes with NvBlockSwComponentTypes, shall be
	 * typed by ClientServerInterfaces that are compatible to the particular ClientServerInterfaces derived from
	 * MOD_GeneralBlueprints~MOD-GeneralBlueprints. constr_1148 applies.
	 */
	// @Check(constraint="constr_1149")
	// def void constr_1149( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1150 Usage of PortDefinedArgumentValue.valueType for PortDefinedArgumentValue The
	 * PortDefinedArgumentValue.valueType (typically this boils down to integer values used to specify an ``id'')
	 * associated with PortDefinedArgumentValue shall be of Identifiable.categoryVALUE or TYPE_REFERENCE. The latter
	 * case is only supported if the value of Identifiable.category of the target data type is set to VALUE.
	 */
	// @Check(constraint="constr_1150")
	// def void constr_1150( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1151 Applicability of PortInterfaceMapping A PortInterfaceMapping is only applicable and valid for a
	 * SwConnector if the two PortPrototypes which are referenced by the SwConnector are typed by the same two
	 * PortInterfaces which are mapped by the PortInterfaceMapping.
	 */
	// @Check(constraint="constr_1151")
	// def void constr_1151( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1152 Identifiable.category of ApplicationArrayElement and AutosarDataType referenced in the role
	 * DataPrototype.type shall be kept in sync The value of Identifiable.category of an ApplicationArrayElement shall
	 * always be identical to the value of Identifiable.category of the AutosarDataType referenced by the
	 * ApplicationArrayElement.
	 */
	// @Check(constraint="constr_1152")
	// def void constr_1152( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1153 Applicability of compatibility requirements for CompuScales Compatibility requirements for
	 * CompuScales shall only apply for CompuScales where the Identifiable.category of the enclosing CompuMethod is one
	 * of the following:
	 */
	// @Check(constraint="constr_1153")
	// def void constr_1153( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1154 Compatibility of CompuScales for sender-receiver communication and similar use cases For
	 * sender-receiver communication and similar use cases, it is required that the set of CompuScales defined in the
	 * CompuMethod of the provider of the communication (i.e. on the side of the PPortPrototype) shall be a subset of
	 * the set of CompuScales defined in the CompuMethod on the required side (i.e. on the side of the RPortPrototype).
	 */

	// @Check(constraint="constr_1154")
	// def void constr_1154( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1155 Compatibility of CompuScales for client-server communication For client-server communication, the
	 * following rules apply:
	 */

	// @Check(constraint="constr_1155")
	// def void constr_1155( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1156 Relevance of ``names'' of CompuScales CompuScales which contribute to tabular conversion by having a
	 * CompuScaleConstan tContents.compuConst are compatible if and only if the ``names'' of the CompuSca
	 * les.compuScales, (namely CompuScale.shortLabel, CompuScaleConstantContents.compu Const and CompuScale.symbol) are
	 * equal. If the scale has no CompuScaleConstantContents.compuConst, ``names'' of CompuScales are not relevant for
	 * compatibility.
	 */

	// @Check(constraint="constr_1156")
	// def void constr_1156( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1157 Applicability of constraints of CompuScales The constraints constr_1154, constr_1155, and constr_1156
	 * shall only apply in the absence of a TextTableMapping which shall take precedence regarding the compatibility if
	 * it exists.
	 */

	// @Check(constraint="constr_1157")
	// def void constr_1157( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1158 Applicable Identifiable.categorys for attribute
	 * ImplementationDataType.ImplementationDataType.swDataDefProps.SwDataDefProps.comp uMethod The definition of the
	 * reference ImplementationDataType.ImplementationDataType.swDataDefProps.SwDataDefProps.comp uMethod is restricted
	 * to a CompuMethod of either Identifiable.categoryARTechTerm_BITFIELD_TEXTTABLE or
	 * Identifiable.categoryARTechTerm_TEXTTABLE (these might be seen as implementation specific in certain cases).
	 */

	// @Check(constraint="constr_1158")
	// def void constr_1158( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1159 Consistency of VariableAndParameterInterfaceMapping with respect to the referenced DataInterfaces
	 * Within one VariableAndParameterInterfaceMapping all VariableAndParameterInterfaceMapping.firstDataPrototypes
	 * shall belong to one and only one DataInterface and all VariableAndParameterInterfaceMapping.secondDataPrototypes
	 * shall belong to one other and only one other DataInterface.
	 */

	// @Check(constraint="constr_1159")
	// def void constr_1159( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1160 Size of Compound Primitive Data Type is variant For Compound Primitive Data Types (see
	 * TPS_SWCT_01179) where the size is subject to variation the size of the specified initValues shall match the range
	 * of the involved SwSystemconst.
	 */

	// @Check(constraint="constr_1160")
	// def void constr_1160( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1161 Applicability of the Ref.index attribute of Ref The Ref.index attribute of Ref is limited to a given
	 * set if use cases as there are:
	 */

	// @Check(constraint="constr_1161")
	// def void constr_1161( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1162 Compatibility of SwRecordLayouts Two SwRecordLayout definitions are compatible if and only if all
	 * attributes except
	 */

	// @Check(constraint="constr_1162")
	// def void constr_1162( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1163 Compatibility of CompuMethods Two CompuMethod definitions are compatible if and only if all
	 * attributes except
	 */

	// @Check(constraint="constr_1163")
	// def void constr_1163( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1164 Number of ClientServerOperation.arguments owned by a RunnableEntity The number of owned
	 * RunnableEntityArguments in the role RunnableEntity.argument of a given RunnableEntity shall be identical to the
	 * number of applicable PortAPIOption.portArgValues of the PortAPIOption that references the PortPrototype that in
	 * turn is referenced by the OperationInvokedEvent that references the RunnableEntityplus the number of
	 * ArgumentDataPrototypes aggregated in the role RunnableEntity.argument by the ClientServerOperation referenced by
	 * said OperationInvokedEvent.
	 */

	// @Check(constraint="constr_1164")
	// def void constr_1164( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1165 Applicability of RunnableEntityArgument The existence of a RunnableEntityArgument is limited to
	 * RunnableEntitys triggered by a ClientServerOperation.
	 */

	// @Check(constraint="constr_1165")
	// def void constr_1165( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1166 Restrictions of ModeRequestTypeMap For every ModeDeclarationGroup referenced by a
	 * ModeDeclarationGroupPrototype used in a PortPrototype typed by a ModeSwitchInterface a ModeRequestTypeMap shall
	 * exist that points to the ModeDeclarationGroup and also to an eligible ImplementationDataType.
	 */

	// @Check(constraint="constr_1166")
	// def void constr_1166( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1167 ImplementationDataTypes used as ModeRequestTypeMap.ModeRequestTypeMap.implementationDataType The
	 * ImplementationDataType referenced by a ModeRequestTypeMap shall either be of
	 * Identifiable.categoryARTechTerm_VALUEor of Identifiable.categoryARTechTerm_TYPE_REFERENCE that in turn references
	 * an ImplementationDataType of Identifiable.categoryARTechTerm_VALUE.
	 */

	// @Check(constraint="constr_1167")
	// def void constr_1167( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1168 Compatibility of ImplementationDataTypes used used in the ModeRequestTypeMap Both
	 * ImplementationDataTypes shall fulfill constr_1167.
	 */

	// @Check(constraint="constr_1168")
	// def void constr_1168( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1169 Allowed values for Trigger.Trigger.swImplPolicy The only allowed values for the attribute
	 * Trigger.Trigger.swImplPolicy are either STANDARD (in which case the Trigger processing does not use a queue) or
	 * QUEUED (in which case the processing of Triggers positively uses a queue).
	 */

	// @Check(constraint="constr_1169")
	// def void constr_1169( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1170 Interpretation of attribute EndToEndDescription.maxDeltaCounterInit owned by EndToEndDescription If
	 * EndToEndProtection.EndToEndProtection.endToEndProtectionVariablePrototype.EndToE
	 * ndProtectionVariablePrototype.receiver is identical to the
	 * RPortPrototype.AbstractRequiredPortPrototype.requiredComSpec.ReceiverComSpec.dat
	 * aElementandRPortPrototype.AbstractRequiredPortPrototype.requiredComSpec.Receiver ComSpec.maxDeltaCounterInit is
	 * defined then the value of RPortPrototype.AbstractRequiredPortPrototype.requiredComSpec.ReceiverComSpec.max
	 * DeltaCounterInitshall be preferred over the value of
	 * EndToEndProtection.EndToEndProtection.endToEndProfile.EndToEndDescription.maxDel taCounterInit.
	 */

	// @Check(constraint="constr_1170")
	// def void constr_1170( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1171 Interpretation of attribute EndToEndDescription.maxDeltaCounterInit of EndToEndDescription If
	 * EndToEndProtection.EndToEndProtection.endToEndProtectionVariablePrototype.EndToE
	 * ndProtectionVariablePrototype.receiver is identical to the
	 * RPortPrototype.AbstractRequiredPortPrototype.requiredComSpec.ReceiverComSpec.dat
	 * aElementandRPortPrototype.AbstractRequiredPortPrototype.requiredComSpec.Receiver ComSpec.maxDeltaCounterInit is
	 * defined then the value of RPortPrototype.AbstractRequiredPortPrototype.requiredComSpec.EndToEndDescription
	 * .maxDeltaCounterInitshall be preferred over the value of
	 * EndToEndProtection.EndToEndProtection.endToEndProfile.EndToEndDescription.maxDel taCounterInit.
	 */

	// @Check(constraint="constr_1171")
	// def void constr_1171( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1172 Allowed values of SwCalibrationAccessEnum for ModeDeclarationGroupPrototype The only allowed values
	 * of ModeDeclarationGroupPrototype.swCalibrationAccess aggregated by ModeDeclarationGroupPrototype are
	 * SwCalibrationAccessEnum.notAccessible and SwCalibrationAccessEnum.readOnly.
	 */

	// @Check(constraint="constr_1172")
	// def void constr_1172( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1173 Applicability of AutosarParameterRef referencing a VariableDataPrototype A reference from
	 * AutosarParameterRef to VariableDataPrototype is only applicable if the AutosarParameterRef is used in the context
	 * of SwAxisGrouped.
	 */

	// @Check(constraint="constr_1173")
	// def void constr_1173( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1174 PortInterfaces used in the context of CompositionSwComponentTypes cannot refer to AUTOSAR services
	 * CompositionSwComponentTypes shall not own PortPrototypes typed by PortInterfaces where the attribute
	 * PortInterface.isService is set to true.
	 */

	// @Check(constraint="constr_1174")
	// def void constr_1174( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1175 Depending on its Identifiable.category, CompuMethod shall refer to a CompuMethod.unit As a
	 * CompuMethod specifies the conversion between the physical world and the numerical values they shall refer to a
	 * CompuMethod.unit unless the CompuMethod's Identifiable.category is one of ARTechTerm_TEXTTABLE,
	 * ARTechTerm_BITFIELD_TEXTTABLE, or ARTechTerm_IDENTICAL.
	 */

	// @Check(constraint="constr_1175")
	// def void constr_1175( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1176 Compatibility of CompuScales of Identifiable.categoryARTechTerm_LINEAR and ARTechTerm_RAT_FUNC
	 * CompuScales of Identifiable.categoryARTechTerm_LINEAR and ARTechTerm_RAT_FUNC are considered compatible if they
	 * yield the same conversion.
	 */

	// @Check(constraint="constr_1176")
	// def void constr_1176( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1177 Allowed SwPointerTargetProps.targetCategory for SwPointerTargetProps The value of
	 * SwPointerTargetProps.targetCategory for SwPointerTargetProps can on ly be one of ARTechTerm_TYPE_REFERENCE or
	 * ARTechTerm_FUNCTION_REFERENCE. The only exception from this rule applies if the
	 * SwPointerTargetProps.swDataDefProps owned by the SwPointerTargetProps refers to a SwBaseType with native type
	 * declaration void, in this case the value ARTechTerm_VALUE is also permitted.
	 */

	// @Check(constraint="constr_1177")
	// def void constr_1177( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1178 Existence of attributes of SwDataDefProps in the context of ImplementationDataType For the sake of
	 * removing possible sources of ambiguity, SwDataDefProps used in the context of ImplementationDataType can only
	 * have one of
	 */

	// @Check(constraint="constr_1178")
	// def void constr_1178( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1181 Numerical values used in ModeDeclaration.ModeDeclaration.value and
	 * ModeDeclarationGroup.ModeDeclarationGroup.onTransitionValue The numerical values used to define the
	 * ModeDeclaration.value attributes and the ModeDeclarationGroup.onTransitionValue attribute of a
	 * ModeDeclarationGroup shall not overlap.
	 */

	// @Check(constraint="constr_1181")
	// def void constr_1181( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1182 Allowed values for InternalTriggeringPoint.SwDataDefProps.swImplPolicy The only allowed values for
	 * the attribute SwDataDefProps.swImplPolicy of meta-class InternalTriggeringPoint are either STANDARD (in which
	 * case the processing of the internal triggering does not use a queue) or QUEUED (in which case the processing of
	 * internal triggering positively uses a queue).
	 */

	// @Check(constraint="constr_1182")
	// def void constr_1182( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1183 EndToEndProtectionVariablePrototypes aggregated by EndToEndProtection All
	 * EndToEndProtectionVariablePrototypes aggregated by the same EndToEndProtection shall refer to the identical
	 * EndToEndProtectionVariablePrototype.sender.
	 */

	// @Check(constraint="constr_1183")
	// def void constr_1183( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1184 Consistency of ApplicationCompositeElementInPortInterfaceInstanceRef.rootDataPrototype and base in
	 * the context of ApplicationCompositeElementInPortInterfaceInstanceRef The
	 * ApplicationCompositeElementInPortInterfaceInstanceRef.rootDataPrototype referenced by
	 * ApplicationCompositeElementInPortInterfaceInstanceRef shall be owned by the applicable subclass of DataInterface
	 * referenced in the role ApplicationCompositeElementInPortInterfaceInstanceRef.base. This implies that the
	 * ApplicationCompositeElementInPortInterfaceInstanceRef.rootDataPrototype shall be a ParameterDataPrototype if the
	 * ApplicationCompositeElementInPortInterfaceInstanceRef.base is a ParameterInterface. Otherwise the
	 * ApplicationCompositeElementInPortInterfaceInstanceRef.rootDataPrototype shall be a VariableDataPrototype.
	 */

	// @Check(constraint="constr_1184")
	// def void constr_1184( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1185 Consistency of data types in the context of ApplicationCompositeElementInPortInterfaceInstanceRef The
	 * definition of attributes ApplicationCompositeElementInPortInterfaceInstanceRef.contextDataPrototype and
	 * ApplicationCompositeElementInPortInterfaceInstanceRef.targetDataPrototype shall (via the type-prototype pattern)
	 * be enclosed in the context of the definition of the data type used to type
	 * ApplicationCompositeElementInPortInterfaceInstanceRef.rootDataPrototype.
	 */

	// @Check(constraint="constr_1185")
	// def void constr_1185( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1186 Consistency of data types in the context of ArVariableInImplementationDataInstanceRef The definition
	 * of attributes ArVariableInImplementationDataInstanceRef.contextDataPrototype and
	 * ArVariableInImplementationDataInstanceRef.targetDataPrototype shall be enclosed in the context of the definition
	 * of the data type used to type ArVariableInImplementationDataInstanceRef.rootDataPrototype.
	 */

	// @Check(constraint="constr_1186")
	// def void constr_1186( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1187 Compatibility of VariableDataPrototypes or ParameterDataPrototypes typed by composite data types
	 * DataPrototypes of ApplicationCompositeDataTypes or ImplementationDataTypes of
	 * Identifiable.categoryARTechTerm_STRUCTURE or ARTechTerm_ARRAY are compatible if one of the following conditions
	 * evaluates to true:
	 */

	// @Check(constraint="constr_1187")
	// def void constr_1187( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1188 Existence of ReceiverComSpec.ReceiverComSpec.replaceWith The aggregation of VariableAccess in the
	 * role ReceiverComSpec.ReceiverComSpec.replaceWith shall exist if and only if at least one of the following
	 * conditions is fulfilled:
	 */

	// @Check(constraint="constr_1188")
	// def void constr_1188( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1190 Only one mapping for composite to primitive use case In the case described by TPS_SWCT_01195 only one
	 * DataPrototypeMapping.subElementMapping shall exist at the enclosing DataPrototypeMapping.
	 */

	// @Check(constraint="constr_1190")
	// def void constr_1190( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1191 Value of Limit shall yield a numerical value After all variability is bound, the content obtained
	 * from a limit shall yield a numerical value.
	 */

	// @Check(constraint="constr_1191")
	// def void constr_1191( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1192 Compatibility of ``ARTechTerm_IDENTICAL'' to ``ARTechTerm_RAT_FUNC'' or ``ARTechTerm_LINEAR'' Similar
	 * to constr_1176, a CompuScale where the Identifiable.category of the enclosing CompuMethod is set to
	 * ARTechTerm_IDENTICAL is considered compatible to a CompuScale where the Identifiable.category of the enclosing
	 * CompuMethod is set to ARTechTerm_RAT_FUNC or ARTechTerm_LINEAR if the following rule applies:
	 */

	// @Check(constraint="constr_1192")
	// def void constr_1192( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1193 ModeDeclaration shall be referenced by at least one ModeTransition in the role
	 * ModeTransition.enteredMode For each ModeDeclaration at least one ModeTransition shall reference the
	 * ModeDeclaration in the role ModeTransition.enteredMode. This constraint shall apply only if there is at least one
	 * ModeTransition defined in the context of the enclosing ModeDeclarationGroup and it shall not apply to the
	 * ModeDeclarationGroup.initialMode.
	 */

	// @Check(constraint="constr_1193")
	// def void constr_1193( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1194 Identical ModeTransitions Two ModeDeclarationGroups contain identical
	 * ModeDeclarationGroup.modeTransitions if and only if
	 */

	// @Check(constraint="constr_1194")
	// def void constr_1194( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1195 SwcModeSwitchEvent and the definition of ModeTransition For each pair of ModeDeclarations referenced
	 * by a SwcModeSwitchEvent with attribute SwcModeSwitchEvent.activation set to ModeActivationKind.onTransition a
	 * ModeTransition shall be defined in the corresponding direction (i.e. from ModeTransition.exitedMode to
	 * ModeTransition.enteredMode). This constraint shall only apply if the respective ModeDeclarationGroup defines at
	 * least one ModeTransition.modeTransition.
	 */

	// @Check(constraint="constr_1195")
	// def void constr_1195( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1196 Existence of ReceiverComSpec.networkRepresentation vs. ReceiverComSpec.compositeNetworkRepresentation
	 * If a ReceiverComSpec or SenderComSpec aggregates ReceiverComSpec.networkRepresentation it shall not aggregate
	 * ReceiverComSpec.compositeNetworkRepresentation at the same time (and vice versa).
	 */

	// @Check(constraint="constr_1196")
	// def void constr_1196( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1197 Existence of ReceiverComSpec.compositeNetworkRepresentation shall be comprehensive If at least one
	 * ReceiverComSpec.compositeNetworkRepresentation exists then for each leaf ApplicationCompositeElementDataPrototype
	 * of the affected ApplicationCompositeDataType exactly one ReceiverComSpec.compositeNetworkRepresentation shall be
	 * defined.
	 */

	// @Check(constraint="constr_1197")
	// def void constr_1197( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1200 Queued communication is not applicable for SenderReceiverInterface.dataElements owned by
	 * PRPortPrototype The SwDataDefProps.swImplPolicy shall not be set to SwImplPolicyEnum.queued for any
	 * SenderReceiverInterface.dataElement owned by a PRPortPrototype.
	 */

	// @Check(constraint="constr_1200")
	// def void constr_1200( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1201 NonqueuedReceiverComSpec.initValue shall exist in an RPortPrototype The optional attribute
	 * NonqueuedReceiverComSpec.initValueshall exist if the enclosing NonqueuedReceiverComSpec is owned by an
	 * RPortPrototype.
	 */

	// @Check(constraint="constr_1201")
	// def void constr_1201( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1202 Supported connections by AssemblySwConnector for PortPrototypes typed by a SenderReceiverInterface or
	 * NvDataInterface For the modeling of AssemblySwConnectors between PortPrototypes typed by a
	 * SenderReceiverInterface or NvDataInterface, only the connections documented in
	 * Table~table:supportedAssSRNVConnections are supported by AUTOSAR.
	 */

	// @Check(constraint="constr_1202")
	// def void constr_1202( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1203 Supported connections by DelegationSwConnector for PortPrototypes typed by a SenderReceiverInterface
	 * or NvDataInterface For the modeling of DelegationSwConnectors between PortPrototypes typed by a
	 * SenderReceiverInterface or NvDataInterface, only the connections documented in
	 * Table~table:supportedDelSRNVConnections are supported by AUTOSAR.
	 */

	// @Check(constraint="constr_1203")
	// def void constr_1203( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1204 Supported connections by AssemblySwConnector for PortPrototypes typed by a ClientServerInterface,
	 * ModeSwitchInterface, or TriggerInterface For the modeling of AssemblySwConnectors between PortPrototypes typed by
	 * a ClientServerInterface, ModeSwitchInterface, or TriggerInterface, only the connections documented in
	 * Table~table:supportedAssCSMTConnections are supported by AUTOSAR.
	 */

	// @Check(constraint="constr_1204")
	// def void constr_1204( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1205 Supported connections by DelegationSwConnector for PortPrototypes typed by a ClientServerInterface,
	 * ModeSwitchInterface, or TriggerInterface For the modeling of DelegationSwConnectors between PortPrototypes typed
	 * by a ClientServerInterface, ModeSwitchInterface, or TriggerInterface, only the connections documented in
	 * Table~table:supportedDelCSMTConnections are supported by AUTOSAR.
	 */

	// @Check(constraint="constr_1205")
	// def void constr_1205( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1209 Mapping of ModeDeclarations of mode user to ModeDeclaration of mode manager A configuration that maps
	 * severalModeDeclarations representing modes of a mode user to oneModeDeclaration representing a mode of a mode
	 * manager shall be rejected.
	 */

	// @Check(constraint="constr_1209")
	// def void constr_1209( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1210 Mapping of ModeDeclarations of mode user to allModeDeclarations of mode manager If a
	 * ModeDeclarationMapping exists that references a ModeDeclaration representing a mode of the mode manager then
	 * ModeDeclarationMappings shall exist that map all modes of the mode manager to modes of the mode user.
	 */

	// @Check(constraint="constr_1210")
	// def void constr_1210( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1211 Constraints of EndToEndDescription.maxNoNewOrRepeatedData in PROFILE_01 In PROFILE_01, the applicable
	 * range of values for EndToEndDescription.EndToEndDe scription.maxNoNewOrRepeatedData and
	 * ReceiverComSpec.ReceiverComSpec.maxNoNewOrR epeatedData is [0~..~14].
	 */

	// @Check(constraint="constr_1211")
	// def void constr_1211( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1212 Constraints of EndToEndDescription.syncCounterInit in PROFILE_01 In PROFILE_01, the applicable range
	 * of values for EndToEndDescription.EndToEndDe scription.syncCounterInit and
	 * ReceiverComSpec.ReceiverComSpec.syncCounterInit is [0~..~14].
	 */

	// @Check(constraint="constr_1212")
	// def void constr_1212( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1213 Constraints of EndToEndDescription.maxNoNewOrRepeatedData in PROFILE_02 In PROFILE_02, the applicable
	 * range of values for EndToEndDescription.EndToEndDe scription.maxNoNewOrRepeatedData and
	 * ReceiverComSpec.ReceiverComSpec.maxNoNewOrR epeatedData is [0~..~15].
	 */

	// @Check(constraint="constr_1213")
	// def void constr_1213( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1214 Constraints of EndToEndDescription.syncCounterInit in PROFILE_02 In PROFILE_02, the applicable range
	 * of values for EndToEndDescription.EndToEndDe scription.syncCounterInit and
	 * ReceiverComSpec.ReceiverComSpec.syncCounterInit is [0~..~15].
	 */

	// @Check(constraint="constr_1214")
	// def void constr_1214( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1215 Interpretation of attribute EndToEndDescription.maxNoNewOrRepeatedData owned by EndToEndDescription
	 * in PROFILE_01 If EndToEndProtection.EndToEndProtection.endToEndProtectionVariablePrototype.EndToE
	 * ndProtectionVariablePrototype.receiver is identical to the
	 * RPortPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.dataElementandRPor
	 * tPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.maxNoNewOrRepeatedData is defined then the value of
	 * RPortPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.maxNoNewOrRepeated Datashall be preferred over the
	 * value of EndToEndProtection.EndToEndProtection.endToEndProfile.EndToEndDescription.maxNoN ewOrRepeatedData.
	 */

	// @Check(constraint="constr_1215")
	// def void constr_1215( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1216 Interpretation of attribute EndToEndDescription.syncCounterInit owned by EndToEndDescription in
	 * PROFILE_01 If EndToEndProtection.EndToEndProtection.endToEndProtectionVariablePrototype.EndToE
	 * ndProtectionVariablePrototype.receiver is identical to the
	 * RPortPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.dataElementandRPor
	 * tPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.syncCounterInit is defined then the value of
	 * RPortPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.syncCounterInitsha ll be preferred over the value
	 * of EndToEndProtection.EndToEndProtection.endToEndProfile.EndToEndDescription.syncCo unterInit.
	 */

	// @Check(constraint="constr_1216")
	// def void constr_1216( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1217 Interpretation of attribute EndToEndDescription.maxNoNewOrRepeatedData owned by EndToEndDescription
	 * in PROFILE_02 If EndToEndProtection.EndToEndProtection.endToEndProtectionVariablePrototype.EndToE
	 * ndProtectionVariablePrototype.receiver is identical to the
	 * RPortPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.dataElementandRPor
	 * tPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.maxNoNewOrRepeatedData is defined then the value of
	 * RPortPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.maxNoNewOrRepeated Datashall be preferred over the
	 * value of EndToEndProtection.EndToEndProtection.endToEndProfile.EndToEndDescription.maxNoN ewOrRepeatedData.
	 */

	// @Check(constraint="constr_1217")
	// def void constr_1217( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1218 Interpretation of attribute EndToEndDescription.syncCounterInit owned by EndToEndDescription in
	 * PROFILE_02 If EndToEndProtection.EndToEndProtection.endToEndProtectionVariablePrototype.EndToE
	 * ndProtectionVariablePrototype.receiver is identical to the
	 * RPortPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.dataElementandRPor
	 * tPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.syncCounterInit is defined then the value of
	 * RPortPrototype.RPortPrototype.requiredComSpec.ReceiverComSpec.syncCounterInitsha ll be preferred over the value
	 * of EndToEndProtection.EndToEndProtection.endToEndProfile.EndToEndDescription.syncCo unterInit.
	 */

	// @Check(constraint="constr_1218")
	// def void constr_1218( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1219 Invalidation depends on the value of SwDataDefProps.swImplPolicy Invalidation of
	 * SenderReceiverInterface.dataElements is only supported for SenderReceiverInterface.dataElements where the value
	 * of SwDataDefProps.swImplPolicy is not set to SwImplPolicyEnum.queued.
	 */

	// @Check(constraint="constr_1219")
	// def void constr_1219( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1220 Compatibility of SwBaseType Two SwBaseTypes are compatible if and only if attributes
	 * SwBaseType.baseTypeSize respectively SwBaseType.maxBaseTypeSize, BaseTypeDirectDefinition.byteOrder,
	 * BaseTypeDirectDefinition.memAlignment, BaseTypeDirectDefinition.baseTypeEncoding, and
	 * BaseTypeDirectDefinition.nativeDeclaration have identical values.
	 */

	// @Check(constraint="constr_1220")
	// def void constr_1220( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1221 DataPrototype is typed by an ApplicationPrimitiveDataType If a DataPrototype is typed by an
	 * ApplicationPrimitiveDataType its SwDataDefProps.initValue shall be provided by an ApplicationValueSpecification.
	 * If the underlying ApplicationPrimitiveDataType represents an enumeration, the value provided shall match to one
	 * of the applicable text values (CompuConstTextContent.vt, CompuScale.shortLabel, CompuScale.symbol) defined by the
	 * applicable CompuScales.
	 */

	// @Check(constraint="constr_1221")
	// def void constr_1221( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1222 Identifiable.category of an AutosarDataType used to type a DataPrototype is set to STRING If the
	 * Identifiable.category of an AutosarDataType used to type a DataPrototype is set to STRING the
	 * ApplicationValueSpecification used to initialize the DataPrototype shall be of
	 * Identifiable.categoryARTechTerm_STRING.
	 */

	// @Check(constraint="constr_1222")
	// def void constr_1222( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1223 DataPrototype is typed by an ApplicationRecordDataType If a DataPrototype is typed by an
	 * ApplicationRecordDataType the corresponding SwDataDefProps.initValue shall be provided by a
	 * RecordValueSpecification.
	 */

	// @Check(constraint="constr_1223")
	// def void constr_1223( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1224 DataPrototype is typed by an ApplicationArrayDataType If a DataPrototype is typed by an
	 * ApplicationArrayDataType the corresponding SwDataDefProps.initValue shall be provided by an
	 * ArrayValueSpecification or ApplicationRuleBasedValueSpecification.
	 */

	// @Check(constraint="constr_1224")
	// def void constr_1224( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1225 DataPrototype is typed by an ImplementationDataType that references a CompuMethod of category
	 * ARTechTerm_TEXTTABLE or ARTechTerm_BITFIELD_TEXTTABLE If a DataPrototype is typed by an ImplementationDataType
	 * that references a CompuMethod of category ARTechTerm_TEXTTABLE or ARTechTerm_BITFIELD_TEXTTABLE the applicable
	 * ValueSpecification shall be a TextValueSpecification. In this case the value provided shall match to one of the
	 * applicable text values (CompuConstTextContent.vt, CompuScale.shortLabel, CompuScale.symbol) defined by the
	 * applicable CompuScales.
	 */

	// @Check(constraint="constr_1225")
	// def void constr_1225( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1226 Applicable range for ExecutableEntityActivationReason.ExecutableEntityActivationReason.bitPosition
	 * The value of attribute ExecutableEntityActivationReason.ExecutableEntityActivationReason.bitPosition shall be in
	 * the range of 0~..~31.
	 */

	// @Check(constraint="constr_1226")
	// def void constr_1226( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1227 Value of attribute ExecutableEntityActivationReason.ExecutableEntityActivationReason.bitPosition
	 * shall be unique The value of attributes
	 * ExecutableEntityActivationReason.ExecutableEntityActivationReason.bitPosition and
	 * ExecutableEntityActivationReason.ImplementationProps.symbol shall be unique in the context of the enclosing
	 * RunnableEntity.
	 */

	// @Check(constraint="constr_1227")
	// def void constr_1227( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1228 RTEEvent that is referenced by a WaitPoint in the role WaitPoint.trigger shall not reference
	 * ExecutableEntityActivationReason An RTEEvent that is referenced by a WaitPoint in the role WaitPoint.trigger
	 * shall not reference ExecutableEntityActivationReason in the role AbstractEvent.activationReasonRepresentation.
	 */

	// @Check(constraint="constr_1228")
	// def void constr_1228( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1229 Identifiable.category of ImplementationDataType boils down to ARTechTerm_VALUE An
	 * ImplementationDataType qualifies as an ARTechTerm_Integral Primitive Type if and only if either
	 */

	// @Check(constraint="constr_1229")
	// def void constr_1229( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1230 ApplicationDataType that qualifies for ARTechTerm_Integral Primitive Type An ApplicationDataType
	 * qualifies as an ARTechTerm_Integral Primitive Type if and only if all of the following conditions apply:
	 */

	// @Check(constraint="constr_1230")
	// def void constr_1230( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1231 ConsistencyNeeds aggregated by CompositionSwComponentType If ConsistencyNeeds are aggregated by a
	 * CompositionSwComponentType the associations stereotyped instanceRef may only refer to context and target elements
	 * within the context of this CompositionSwComponentType.
	 */

	// @Check(constraint="constr_1231")
	// def void constr_1231( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1232 ConsistencyNeeds aggregated by AtomicSwComponentType If ConsistencyNeeds are aggregated by a
	 * AtomicSwComponentType the associations stereotyped instanceRef may only refer to context and target elements
	 * within the context of this AtomicSwComponentType.
	 */

	// @Check(constraint="constr_1232")
	// def void constr_1232( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1233 InstantiationTimingEventProps shall only reference TimingEvent An InstantiationTimingEventProps shall
	 * only reference TimingEvent in the role InstantiationRTEEventProps.refinedEvent. A reference to other kinds of
	 * RTEEvents is not supported.
	 */

	// @Check(constraint="constr_1233")
	// def void constr_1233( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1234 Value of RunnableEntity.RunnableEntity.symbol The possible value of
	 * RunnableEntity.RunnableEntity.symbol owned by an NvBlockSwComponentType shall only be taken from the set of API
	 * names associated with the NvM.
	 */

	// @Check(constraint="constr_1234")
	// def void constr_1234( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1237 Scope of mapped ClientServerOperations in the context of a ClientServerOperationMapping All
	 * ClientServerOperations referenced by a ClientServerOperationMapping in the role
	 * ClientServerOperationMapping.firstOperation shall belong to exactly one ClientServerInterface.
	 */

	// @Check(constraint="constr_1237")
	// def void constr_1237( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1238 Scope of mapped ApplicationErrors in the context of a ClientServerOperationMapping All
	 * ApplicationErrors referenced by a ClientServerApplicationErrorMapping in the role
	 * ClientServerApplicationErrorMapping.firstApplicationError shall belong to exactly one ClientServerInterface.
	 */

	// @Check(constraint="constr_1238")
	// def void constr_1238( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1240 Consistency of ArgumentDataPrototypes within the context of a ClientServerOperationMapping For each
	 * ClientServerOperation.argument owned by a ClientServerOperationMapping.
	 * ClientServerOperationMapping.firstOperation and
	 * ClientServerOperationMapping.ClientServerOperationMapping.secondOperation a reference in the role
	 * ClientServerOperationMapping.ClientServerOperationMapping.argumentMapping.DataPr
	 * ototypeMapping.firstDataPrototype or
	 * ClientServerOperationMapping.ClientServerOperationMapping.argumentMapping.DataPr
	 * ototypeMapping.secondDataPrototype shall exist originated by one of the
	 * ClientServerOperationMapping.ClientServerOperationMapping.argumentMappings owned by the mentioned
	 * ClientServerOperationMapping.
	 */

	// @Check(constraint="constr_1240")
	// def void constr_1240( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1241 ARTechTerm_Compound Primitive Data Types and SwDataDefProps.invalidValue ARTechTerm_Compound
	 * Primitive Data Types that have set the value of of Identifiable.category other than ARTechTerm_STRING shall not
	 * define SwDataDefProps.invalidValue.
	 */

	// @Check(constraint="constr_1241")
	// def void constr_1241( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1242 Restriction of SwDataDefProps.invalidValue for ApplicationPrimitiveDataType of
	 * Identifiable.categoryARTechTerm_STRING SwDataDefProps.invalidValue for ApplicationPrimitiveDataType of
	 * Identifiable.categoryARTechTerm_STRING (constr_1241 applies) is restricted to be either a compatible
	 * ApplicationValueSpecification or a ConstantReference that in turn points to a compatible
	 * ApplicationValueSpecification.
	 */

	// @Check(constraint="constr_1242")
	// def void constr_1242( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1243 NumericalOrText shall either define NumericalOrText.vf or NumericalOrText.vt Within the context of
	 * one NumericalOrText, either the attribute NumericalOrText.vfor the attribute NumericalOrText.vt shall be defined.
	 * The existence of both attributes at the same time is not permitted.
	 */

	// @Check(constraint="constr_1243")
	// def void constr_1243( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1244 DataPrototypes used in application software shall not be typed by C enums A DataPrototype that is
	 * used in an AtomicSwComponentType shall not set
	 * DataPrototype.swDataDefProps.SwDataDefProps.additionalNativeTypeQualifier to enum.
	 */

	// @Check(constraint="constr_1244")
	// def void constr_1244( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1245 Consideration of ModeTransitions for the compatibility of ModeDeclarationGroups One of the following
	 * conditions for the consideration of ModeTransitions for the compatibility of ModeDeclarationGroups shall apply:
	 */

	// @Check(constraint="constr_1245")
	// def void constr_1245( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1246 Consistency of ModeDeclarationMapping.firstMode and ModeDeclarationMapping.secondMode in the scope of
	 * one ModeDeclarationMappingSet Within the scope of one ModeDeclarationMappingSet, all
	 * ModeDeclarationMapping.firstModes shall belong to one and only one ModeDeclarationGroup and all
	 * ModeDeclarationMapping.secondModes shall belong to one and only one otherModeDeclarationGroup
	 */

	// @Check(constraint="constr_1246")
	// def void constr_1246( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1247 Consistency of ModeDeclarationMappingSet with respect to the referenced
	 * ModeDeclarationGroupPrototypeMapping.firstModeGroup and ModeDeclarationGroupPrototypeMapping.secondModeGroup If a
	 * ModeDeclarationGroupPrototypeMapping.ModeDeclarationGroupPrototypeMapping.m odeDeclarationMappingSet exists, the
	 * ModeDeclarationGroup owning the ModeDeclara tionGroup.modeDeclarations referenced in the role
	 * ModeDeclarationMapping.firstMo de shall be the ModeDeclarationGroupPrototype.type of the ModeDeclarationGroupPr
	 * ototypeMapping.ModeDeclarationGroupPrototypeMapping.firstModeGroup and the ModeDeclarationGroup owning the
	 * ModeDeclarationGroup.modeDeclarations referenced in the role ModeDeclarationMapping.secondMode shall be the
	 * ModeDeclarationGroupPrototype.type of the
	 * ModeDeclarationGroupPrototypeMapping.ModeDeclarationGroupPrototypeMapping.second ModeGroup.
	 */

	// @Check(constraint="constr_1247")
	// def void constr_1247( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1248 Compatibility of PortPrototypes of different DataInterfaces in the context of a
	 * PassThroughSwConnector PortPrototypes of different DataInterfaces are considered compatible if and only if
	 */

	// @Check(constraint="constr_1248")
	// def void constr_1248( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1249 Compatibility of ModeSwitchInterfaces in the context of a PassThroughSwConnector PortPrototypes of
	 * different ModeSwitchInterfaces are considered compatible if and only if
	 */

	// @Check(constraint="constr_1249")
	// def void constr_1249( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1250 Compatibility of ClientServerInterfaces in the context of a PassThroughSwConnector PortPrototypes of
	 * different ClientServerInterfaces are considered compatible if and only if
	 */

	// @Check(constraint="constr_1250")
	// def void constr_1250( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1251 Compatibility of PortPrototypes of TriggerInterfaces in the context of a PassThroughSwConnector
	 * PortPrototypes of different TriggerInterfaces are considered compatible if and only if
	 */

	// @Check(constraint="constr_1251")
	// def void constr_1251( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1252 Creation of a loop involving a PassThroughSwConnector is not allowed A PassThroughSwConnector is not
	 * allowed if the required outer PortPrototype is directly or indirectly connected to the provided outer
	 * PortPrototype without the placement of a SwComponentPrototype typed by an AtomicSwComponentType in the chain of
	 * SwConnectors.
	 */

	// @Check(constraint="constr_1252")
	// def void constr_1252( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1253 Supported usage of VariationPointProxy The allowed multiplicities for attributes of
	 * VariationPointProxy depending on the applicable binding time and the value of
	 * VariationPointProxy.Identifiable.category are documented in Table~tab:SupportedUsageOfVariationPointProxy.
	 */

	// @Check(constraint="constr_1253")
	// def void constr_1253( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1254 Definition of a pointer to a pointer AUTOSAR does not support the definition of a pointer to a
	 * pointer by defining an ImplementationDataType of Identifiable.categoryARTechTerm_DATA_REFERENCE that aggregates
	 * SwDataDefProps in the role ImplementationDataType.swDataDefProps that in turn aggregate SwPointerTargetProps in
	 * the role SwDataDefProps.swPointerTargetProps with attribute SwPointerTargetProps.targetCategory set to
	 * ARTechTerm_DATA_REFERENCE that in turn aggregates SwDataDefProps in the role SwPointerTargetProps.swDataDefProps
	 * that aggregates SwPointerTargetProps in the role SwDataDefProps.swPointerTargetProps that references an
	 * ImplementationDataType of Identifiable.category e.g. ARTechTerm_VALUE.
	 */

	// @Check(constraint="constr_1254")
	// def void constr_1254( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1255 ApplicationPrimitiveDataTypes of Identifiable.categoryARTechTerm_BOOLEAN and STRING If a Unit is
	 * referenced from within SwDataDefProps and/or PhysConstrs owned by an ApplicationPrimitiveDataTypes of
	 * Identifiable.categoryARTechTerm_BOOLEAN and ARTechTerm_STRING it is required that this Unit represents a
	 * meaningless unit, i.e. the referenced Unit.physicalDimension shall not define any exponent value other than 0.
	 */

	// @Check(constraint="constr_1255")
	// def void constr_1255( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1256 Acknowledgement feedback in n:1 writer case Within the scope of one SwcInternalBehavior, it is not
	 * allowed that two or more aggregated RunnableEntitys own either RunnableEntity.dataSendPoints or
	 * RunnableEntity.dataWriteAccesss that in turn point to the identical
	 * VariableAccess.accessedVariable.AutosarVariableRef.autosarVariable.VariableInAto
	 * micSWCTypeInstanceRef.targetDataPrototypeif the attribute SenderComSpec.transmissionAcknowledge exists in the
	 * context of the SenderComSpec owned by the
	 * RunnableEntity.dataSendPoint.VariableAccess.accessedVariable.AutosarVariableRef.
	 * autosarVariable.VariableInAtomicSWCTypeInstanceRef.portPrototype (or the respective construct for
	 * RunnableEntity.dataWriteAccess) that also refers to said SenderReceiverInterface.dataElement.
	 */

	// @Check(constraint="constr_1256")
	// def void constr_1256( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1257 No WaitPoints allowed A RunnableEntity referenced by an InitEvent in the role RTEEvent.startOnEvent
	 * shall not aggregate a WaitPoint.
	 */

	// @Check(constraint="constr_1257")
	// def void constr_1257( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1258 Value of ExecutableEntity.minimumStartInterval for RunnableEntitys triggered by an InitEvent The
	 * value of the attribute ExecutableEntity.ExecutableEntity.minimumStartInterval for a RunnableEntitys that is
	 * triggered by an InitEvent shall always be set to 0.
	 */

	// @Check(constraint="constr_1258")
	// def void constr_1258( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1259 Aggregation of AsynchronousServerCallPointandAsynchronousServerCallResultPoint A RunnableEntity
	 * referenced by an InitEvent in the role RTEEvent.startOnEvent may aggregate an AsynchronousServerCallPoint but it
	 * shall not aggregate an AsynchronousServerCallResultPoint.
	 */

	// @Check(constraint="constr_1259")
	// def void constr_1259( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1260 No mode disabling for InitEvents An InitEvent shall not have a reference to a ModeDeclaration in the
	 * role RTEEvent.disabledMode.
	 */

	// @Check(constraint="constr_1260")
	// def void constr_1260( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1261 Applicability for EndToEndDescription.EndToEndDescription.dataIdNibbleOffset
	 * EndToEndDescription.EndToEndDescription.dataIdNibbleOffset shall be used only if
	 * EndToEndDescription.EndToEndDescription.dataIdMode is set to the value 3 and at the same time
	 * EndToEndDescription.Identifiable.category is set to PROFILE_01.
	 */

	// @Check(constraint="constr_1261")
	// def void constr_1261( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1263 Existence of ModeErrorBehavior.ModeErrorBehavior.defaultMode The optional attribute
	 * ModeErrorBehavior.ModeErrorBehavior.defaultModeshall exist if the value of the attribute
	 * ModeErrorBehavior.ModeErrorBehavior.errorReactionPolicy is set to ModeErrorReactionPolicyEnum.defaultMode.
	 */

	// @Check(constraint="constr_1263")
	// def void constr_1263( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1264 Iteration along output axis is only supported for VALUE and VAL_BLK
	 * SwRecordLayoutV.swRecordLayoutVIndex in SwRecordLayoutV cannot be 0 for any data Identifiable.category other than
	 * VALUE and VAL_BLK.
	 */

	// @Check(constraint="constr_1264")
	// def void constr_1264( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1268 ArgumentDataPrototype.ArgumentDataPrototype.direction shall be preserved in a
	 * ClientServerOperationMapping Within the context of a ClientServerOperationMapping, the value of the argument
	 * ArgumentDataPrototype.ArgumentDataPrototype.direction of two mapped ArgumentDataPrototype shall be identical.
	 */

	// @Check(constraint="constr_1268")
	// def void constr_1268( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1269 Number of ClientServerOperation.arguments shall be preserved in a ClientServerOperationMapping Within
	 * the context of a ClientServerOperationMapping, the number of ClientServerOperation.arguments of
	 * ClientServerOperationMapping.firstOperation and ClientServerOperationMapping.secondOperation shall be identical.
	 */

	// @Check(constraint="constr_1269")
	// def void constr_1269( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1270 ArgumentDataPrototype shall be mapped only once in a ClientServerOperationMapping Within the context
	 * of a ClientServerOperationMapping, each ClientServerOperation.argument shall only be referenced once in the role
	 * DataPrototypeMapping.firstDataPrototype or DataPrototypeMapping.secondDataPrototype.
	 */

	// @Check(constraint="constr_1270")
	// def void constr_1270( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1271 RecordValueSpecification.RecordValueSpecification.elements shall be identical to the number of
	 * ApplicationRecordDataType.ApplicationRecordDataType.element The initialization of an DataPrototype typed by an
	 * ApplicationRecordDataType by means of a RecordValueSpecification shall exactly match the structure of the
	 * ApplicationRecordDataType.
	 */

	// @Check(constraint="constr_1271")
	// def void constr_1271( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1272 RecordValueSpecification.RecordValueSpecification.elements shall be identical to the number of
	 * ImplementationDataType.subElements of ImplementationDataType of Identifiable.categorySTRUCTURE The initialization
	 * of an DataPrototype typed by an ImplementationDataType of Identifiable.categoryARTechTerm_STRUCTURE by means of a
	 * RecordValueSpecification shall exactly match the structure of the ImplementationDataType of
	 * Identifiable.categorySTRUCTURE.
	 */

	// @Check(constraint="constr_1272")
	// def void constr_1272( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1273 ArrayValueSpecification.ArrayValueSpecification.elements shall be identical to the value of
	 * ApplicationArrayDataType.ApplicationArrayDataType.element.ApplicationArrayElemen t.maxNumberOfElements The
	 * initialization of DataPrototype typed by an ApplicationArrayDataType by means of an ArrayValueSpecification shall
	 * exactly match the structure of the ApplicationArrayDataType regardless of the setting of the attribute
	 * ApplicationArrayDataType.ApplicationArrayDataType.element.ApplicationArrayElemen t.arraySizeSemantics.
	 */

	// @Check(constraint="constr_1273")
	// def void constr_1273( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1274 ArrayValueSpecification.ArrayValueSpecification.elements shall be identical to the value of
	 * ImplementationDataType.ImplementationDataType.subElement.ImplementationDataTypeE lement.arraySize of
	 * Identifiable.categoryARTechTerm_ARRAY The initialization of a DataPrototype typed by an ImplementationDataType of
	 * Identifiable.categoryARRAY by means of an ArrayValueSpecification shall exactly match the structure of the
	 * ImplementationDataType regardless of the setting of the attribute
	 * ImplementationDataType.ImplementationDataType.subElement.ImplementationDataTypeE lement.arraySizeSemantics.
	 */

	// @Check(constraint="constr_1274")
	// def void constr_1274( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1277 SwDataDefProps.SwDataDefProps.swImplPolicy of a VariableDataPrototype referenced by a VariableAccess
	 * aggregated in the role RunnableEntity.dataReceivePointByValue The SwDataDefProps.SwDataDefProps.swImplPolicy of a
	 * VariableDataPrototype referenced by a VariableAccess aggregated in the role
	 * RunnableEntity.dataReceivePointByValue shall not be set to SwImplPolicyEnum.queued.
	 */

	// @Check(constraint="constr_1277")
	// def void constr_1277( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1278 PhysConstrs references a Unit DataConstrs are only compatible if the
	 * DataConstr.DataConstr.dataConstrRule.DataConstrRule.physConstrs.PhysConstrs.unit are compatible or neither
	 * DataConstr.DataConstr.dataConstrRule.DataConstrRule.physConstrs.PhysConstrs.unit exist.
	 */

	// @Check(constraint="constr_1278")
	// def void constr_1278( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1279 Unmapped elements of ApplicationCompositeDataTypes or ImplementationDataTypes and the attribute
	 * SwDataDefProps.swImplPolicy If the attribute SwDataDefProps.swImplPolicy is set to SwImplPolicyEnum.queued it is
	 * not allowed to have unmapped elements of ApplicationCompositeDataTypes or ImplementationDataTypes of
	 * Identifiable.categoryARTechTerm_STRUCTURE or ARTechTerm_ARRAY on the receiver side.
	 */

	// @Check(constraint="constr_1279")
	// def void constr_1279( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1280 Unmapped SenderReceiverInterface.dataElement on the receiver side shall have an initValue If elements
	 * of ApplicationCompositeDataTypes or ImplementationDataTypes of Identifiable.categoryARTechTerm_STRUCTURE or
	 * ARTechTerm_ARRAY are not considered in a SubElementMapping then the enclosing SenderReceiverInterface.dataElement
	 * shall have an NonqueuedReceiverComSpec.initValueif the NonqueuedReceiverComSpec is aggregated by an
	 * AbstractRequiredPortPrototype.
	 */

	// @Check(constraint="constr_1280")
	// def void constr_1280( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1281 SwDataDefProps.invalidValue is inside the scope of the SwDataDefProps.compuMethod If the value of the
	 * SwDataDefProps.invalidValue of an ApplicationPrimitiveDataType of Identifiable.categoryVALUE is supposed to be
	 * inside the scope of the applicable CompuMethod an ApplicationValueSpecification is used to describe the
	 * SwDataDefProps.invalidValue of the ApplicationPrimitiveDataType.
	 */

	// @Check(constraint="constr_1281")
	// def void constr_1281( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1282 Restriction concerning the usage of RuleBasedValueSpecification or a ReferenceValueSpecification for
	 * the specification of an SwDataDefProps.invalidValue The aggregation of a RuleBasedValueSpecification or a
	 * ReferenceValueSpecification for the definition of a
	 * ApplicationPrimitiveDataType.ApplicationPrimitiveDataType.swDataDefProps.SwDataD efProps.invalidValue is not
	 * supported.
	 */

	// @Check(constraint="constr_1282")
	// def void constr_1282( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1283 SwDataDefProps.invalidValue is outside the scope of the SwDataDefProps.compuMethod If the value of
	 * the SwDataDefProps.invalidValue of an ApplicationPrimitiveDataType of Identifiable.categoryARTechTerm_VALUE is
	 * supposed to be outside the scope of the applicable CompuMethod a NumericalValueSpecification shall be used to
	 * describe the SwDataDefProps.invalidValue of the ApplicationPrimitiveDataType.
	 */

	// @Check(constraint="constr_1283")
	// def void constr_1283( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1284 Limitation of the use of TextValueSpecification TextValueSpecification shall only be used in the
	 * context of an AutosarDataType that references a CompuMethod in the role
	 * ImplementationDataType.AutosarDataType.swDataDefPropos.SwDataDefProps.compuMetho d of
	 * Identifiable.categoryARTechTerm_TEXTTABLE, ARTechTerm_BITFIELD_TEXTTABLE, ARTechTerm_SCALE_LINEAR_AND_TEXTTABLE,
	 * and ARTechTerm_SCALE_RATIONAL_AND_TEXTTABLE.
	 */

	// @Check(constraint="constr_1284")
	// def void constr_1284( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1285 Applicability of roles vs. PortPrototypes The aggregation of AutosarVariableRef aggregated by
	 * NvBlockDataMapping in the roles NvBlockDataMapping.writtenNvData, NvBlockDataMapping.writtenReadNvData, or
	 * NvBlockDataMapping.readNvData is subject to limitation depending on the applicable subclass of PortPrototype:
	 */

	// @Check(constraint="constr_1285")
	// def void constr_1285( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1286 ArgumentDataPrototype.serverArgumentImplPolicy and ArgumentDataPrototype typed by primitive data
	 * types The value of the attribute ArgumentDataPrototype.ArgumentDataPrototype.serverArgumentImplPolicy shall not
	 * be set to ServerArgumentImplPolicyEnum.useVoid for an ArgumentDataPrototype of
	 * ArgumentDataPrototype.directionArgumentDirectionEnum.in that is typed by an AutosarDataType that boils down to a
	 * primitive C data type (see TPS_SWCT_01565).
	 */

	// @Check(constraint="constr_1286")
	// def void constr_1286( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1287 Compatibility of SenderReceiverInterfaces with respect to SenderReceiverInterface.invalidationPolicy
	 * VariableDataPrototypes defined in the context of the SenderReceiverInterface are only compatible if the
	 * SenderReceiverInterface.invalidationPolicys have the same value.
	 */

	// @Check(constraint="constr_1287")
	// def void constr_1287( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1288 Allowed Attributes vs. Identifiable.category for DataPrototypes typed by ImplementationDataTypes The
	 * allowed values per Identifiable.category for DataPrototypes typed by ImplementationDataTypes are documented in
	 * table~table:CategoriesImpl4DataProt.
	 */

	// @Check(constraint="constr_1288")
	// def void constr_1288( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1289 Allowed Attributes vs. Identifiable.category for DataPrototypes typed by ApplicationDataTypes The
	 * allowed values of Attributes per Identifiable.category for DataPrototypes typed by ApplicationDataTypes are
	 * documented in table~table:CategoriesAppl4DataProt.
	 */

	// @Check(constraint="constr_1289")
	// def void constr_1289( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1290 Limitation on the number of PPortComSpecs in the context of one PPortPrototype Within the context of
	 * one PPortPrototype there can only be onePPortComSpec that references a given SenderReceiverInterface.dataElement
	 * or ClientServerInterface.clientServerOperation.
	 */

	// @Check(constraint="constr_1290")
	// def void constr_1290( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1291 Limitation on the number of RPortComSpecs in the context of one PPortPrototype Within the context of
	 * one RPortPrototype, there can only be oneRPortComSpec that references a given SenderReceiverInterface.dataElement
	 * or ClientServerInterface.clientServerOperation.
	 */

	// @Check(constraint="constr_1291")
	// def void constr_1291( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1292 Limitation on the number of RPortComSpecs/PPortComSpecs in the context of one PRPortPrototype Within
	 * the context of one PRPortPrototype, there can only be oneRPortComSpec and onePPortComSpec that references a given
	 * SenderReceiverInterface.dataElement or ClientServerInterface.clientServerOperation.
	 */

	// @Check(constraint="constr_1292")
	// def void constr_1292( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1295 PortInterfaces and Identifiable.categoryARTechTerm_DATA_REFERENCE A DataPrototype defined in the
	 * context of a PortInterface used by an ApplicationSwComponentType or SensorActuatorSwComponentType that is (after
	 * potential indirections via ARTechTerm_TYPE_REFERENCE are resolved) either typed by or mapped to an
	 * ImplementationDataType of Identifiable.categoryARTechTerm_DATA_REFERENCE shall only be used if either the
	 * provider or the requester of the information represents a ServiceSwComponentType, a
	 * ComplexDeviceDriverSwComponentType, a ParameterSwComponentType, or an NvBlockSwComponentType, or the
	 * EcuAbstractionSwComponentType.
	 */

	// @Check(constraint="constr_1295")
	// def void constr_1295( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1296 DataPrototypes used as SwcInternalBehavior.explicitInterRunnableVariable or
	 * SwcInternalBehavior.implicitInterRunnableVariable and Identifiable.categoryDATA_REFERENCE A VariableDataPrototype
	 * shall not be aggregated by SwcInternalBehavior in either the role
	 * SwcInternalBehavior.explicitInterRunnableVariable or SwcInternalBehavior.implicitInterRunnableVariable if the
	 * VariableDataPrototype (after potential indirections via TYPE_REFERENCE are resolved) is either typed by or mapped
	 * to an ImplementationDataType of Identifiable.categoryDATA_REFERENCE.
	 */

	// @Check(constraint="constr_1296")
	// def void constr_1296( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1297 Applicability of ArgumentDataPrototype.serverArgumentImplPolicy set to
	 * ServerArgumentImplPolicyEnum.useArrayBaseType The value of the attribute
	 * ArgumentDataPrototype.ArgumentDataPrototype.serverArg umentImplPolicy shall only be set to
	 * ServerArgumentImplPolicyEnum.useArrayBaseType for an ArgumentDataPrototype that is typed by an AutosarDataType
	 * that is (after all ARTechTerm_TYPE_REFERENCEs are resolved) either an ImplementationDataType of
	 * Identifiable.categoryARTechTerm_ARRAY or an ApplicationDataType mapped to (after all ARTechTerm_TYPE_REFERENCEs
	 * are resolved) an ImplementationDataType of Identifiable.categoryARTechTerm_ARRAY.
	 */

	// @Check(constraint="constr_1297")
	// def void constr_1297( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1298 Existence of attributes if Identifiable.category of a ModeDeclarationGroup is set to EXPLICIT_ORDER
	 * The attributes ModeDeclarationGroup.ModeDeclarationGroup.onTransitionValue and
	 * ModeDeclaration.ModeDeclaration.value (for each ModeDeclaration) shall be set if the Identifiable.category of a
	 * ModeDeclarationGroup is set to EXPLICIT_ORDER.
	 */

	// @Check(constraint="constr_1298")
	// def void constr_1298( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1299 Existence of attributes if Identifiable.category of a ModeDeclarationGroup is set to other than
	 * EXPLICIT_ORDER The attributes ModeDeclarationGroup.ModeDeclarationGroup.onTransitionValue or
	 * ModeDeclaration.ModeDeclaration.value (for any ModeDeclaration) shall not be set if the Identifiable.category of
	 * a ModeDeclarationGroup is set to any value other thanEXPLICIT_ORDER.
	 */

	// @Check(constraint="constr_1299")
	// def void constr_1299( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1300 Primitive DataPrototype on the provider side shall not be mapped to element of a composite data type
	 * on the requester side The usage of DataPrototypeMapping resp. SubElementMapping does not support the following
	 * configuration:
	 */

	// @Check(constraint="constr_1300")
	// def void constr_1300( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1301 Existence of RoleBasedDataTypeAssignment.RoleBasedDataTypeAssignment.role vs.
	 * RoleBasedDataAssignment.RoleBasedDataAssignment.role The usage of a RoleBasedDataTypeAssignment with attribute
	 * RoleBasedDataTypeAssignment.role set to the value temporaryRamBlock is only allowed if noRoleBasedDataAssignment
	 * defined with attribute RoleBasedDataAssignment.role set to value defaultValue exists in the owning
	 * SwcServiceDependency.
	 */

	// @Check(constraint="constr_1301")
	// def void constr_1301( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1302 Restriction of data invalidation Data invalidation is only applicable for one of the following cases
	 * applicable on the receiving side:
	 */

	// @Check(constraint="constr_1302")
	// def void constr_1302( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1303 Applicability of TextTableMapping depending on the value of CompuMethod.Identifiable.category If a
	 * DataPrototypeMapping aggregates a TextTableMapping then only certain combinations of the value of the applicable
	 * CompuMethod.Identifiable.category are supported:
	 */

	// @Check(constraint="constr_1303")
	// def void constr_1303( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1304 Existence of attribute TextTableMapping.bitfieldTextTableMaskFirst The attribute
	 * TextTableMapping.bitfieldTextTableMaskFirst shall be defined only if the DataPrototypeMapping.firstDataPrototype
	 * of a DataPrototypeMapping refers to a CompuMethod that has the value of Identifiable.category set to
	 * ARTechTerm_BITFIELD_TEXTTABLE.
	 */

	// @Check(constraint="constr_1304")
	// def void constr_1304( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1305 Existence of attribute TextTableMapping.bitfieldTextTableMaskSecond The attribute
	 * TextTableMapping.bitfieldTextTableMaskSecond shall be defined only if the
	 * DataPrototypeMapping.secondDataPrototype of a DataPrototypeMapping refers to a CompuMethod that has the value of
	 * Identifiable.category set to ARTechTerm_BITFIELD_TEXTTABLE.
	 */

	// @Check(constraint="constr_1305")
	// def void constr_1305( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1306 Limitation of TextTableMapping for CompuMethods that have the value of Identifiable.category set to
	 * ARTechTerm_BITFIELD_TEXTTABLE For any TextTableMapping where both DataPrototypeMapping.firstDataPrototype and
	 * DataPrototypeMapping.secondDataPrototype refer to CompuMethods that have the value of Identifiable.category set
	 * to ARTechTerm_BITFIELD_TEXTTABLEand where the attribute TextTableMapping.TextTableMapping.valuePair exists the
	 * value of attribute TextTableMapping.TextTableMapping.identicalMapping shall be set to false.
	 */

	// @Check(constraint="constr_1306")
	// def void constr_1306( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1307 Consistency of values and masks in TextTableMapping If a TextTableMapping element defines bit masks
	 * as TextTableMapping.bitfieldTextTableMaskFirst or TextTableMapping.bitfieldTextTableMaskSecond then all contained
	 * TextTableMapping.TextTableMapping.valuePair.TextTableValuePair.firstValues as well as all
	 * TextTableMapping.TextTableMapping.valuePair.TextTableValuePair.secondValues shall not specify a value that would
	 * be ruled out when - depending on the given value of TextTableMapping.TextTableMapping.mappingDirection - the
	 * relevant bit mask is applied.
	 */

	// @Check(constraint="constr_1307")
	// def void constr_1307( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1308 Existence of NvBlockNeeds.NvBlockNeeds.cyclicWritingPeriod The attribute
	 * NvBlockNeeds.NvBlockNeeds.cyclicWritingPeriod shall exist if and only if the attribute
	 * NvBlockNeeds.NvBlockNeeds.storeCyclic exists and its value is set to true.
	 */

	// @Check(constraint="constr_1308")
	// def void constr_1308( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1309 Existence of NvBlockDescriptor.NvBlockDescriptor.timingEvent The attribute
	 * NvBlockDescriptor.NvBlockDescriptor.timingEvent shall exist if and only if the
	 * NvBlockDescriptor.NvBlockDescriptor.nvBlockNeeds.NvBlockNeeds.storeCyclic exists and is set to the value true.
	 */

	// @Check(constraint="constr_1309")
	// def void constr_1309( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1310 Existence of attributes of meta-class NvBlockNeeds If in the context of an ApplicationSwComponentType
	 * the attribute SwcServiceDependency.SwcServiceDependency.serviceNeeds is implemented by an NvBlockNeeds then the
	 * following attributes
	 */

	// @Check(constraint="constr_1310")
	// def void constr_1310( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1311 Appearance of safety-related possible values of MemorySection.MemorySection.option or
	 * SwAddrMethod.SwAddrMethod.option according to TPS_SWCT_01456 Any given list of values stored in the attributes
	 * MemorySection.MemorySection.option or SwAddrMethod.SwAddrMethod.option shall at most include a single value out
	 * of the following list:
	 */

	// @Check(constraint="constr_1311")
	// def void constr_1311( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1312 PortPrototypes typed by a ParameterInterface PortPrototypes typed by a ParameterInterface can either
	 * be PPortPrototypes or RPortPrototypes. The usage of PRPortPrototypes that are typed by a ParameterInterface is
	 * not supported.
	 */

	// @Check(constraint="constr_1312")
	// def void constr_1312( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1313 Completeness of TextTableMapping for the values of a given bit mask on the sender side If a
	 * DataPrototypeMapping contains one or more TextTableMapping(s) where the DataPrototype on the sender side refers
	 * to a CompuMethod of Identifiable.categoryARTechTerm_BITFIELD_TEXTTABLE then all
	 * DataPrototypeMapping.DataPrototypeMapping.textTableMapping shall aggregate a collection of
	 * TextTableMapping.TextTableMapping.valuePair where each possible value of the sender bit maskDepending on the
	 * applicable case this means either TextTableMapping.bitfieldTextTableMaskFirst (applies if TPS_SWCT_01163 is in
	 * place) or TextTableMapping.bitfieldTextTableMaskSecond for the case of TPS_SWCT_01164. is represented by exactly
	 * one TextTableValuePair.TextTableValuePair.firstValue (TPS_SWCT_01163) resp.
	 * TextTableValuePair.TextTableValuePair.secondValue (TPS_SWCT_01164).
	 */

	// @Check(constraint="constr_1313")
	// def void constr_1313( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1314 Profile ARTechTerm_VSA_LINEAR for ApplicationArrayDataType If the
	 * ApplicationArrayDataType.dynamicArraySizeProfile of ApplicationArrayDataType is set to ARTechTerm_VSA_LINEAR, the
	 * contained ApplicationArrayElement shall fulfill all of the following conditions:
	 */

	// @Check(constraint="constr_1314")
	// def void constr_1314( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1315 Profile ARTechTerm_VSA_SQUARE for ApplicationArrayDataType If the
	 * ApplicationArrayDataType.dynamicArraySizeProfile of ApplicationArrayDataType is set to ARTechTerm_VSA_SQUARE, the
	 * contained ApplicationArrayElement shall fulfill all of the following conditions:
	 */

	// @Check(constraint="constr_1315")
	// def void constr_1315( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1316 Profile ARTechTerm_VSA_RECTANGULAR for ApplicationArrayDataType If the
	 * ApplicationArrayDataType.dynamicArraySizeProfile of ApplicationArrayDataType is set to ARTechTerm_VSA_RECTANGULAR
	 * the contained ApplicationArrayElement shall fulfill all of the following conditions:
	 */

	// @Check(constraint="constr_1316")
	// def void constr_1316( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1317 Profile ARTechTerm_VSA_FULLY_FLEXIBLE for ApplicationArrayDataType If the
	 * ApplicationArrayDataType.dynamicArraySizeProfile of ApplicationArrayDataType is set to
	 * ARTechTerm_VSA_FULLY_FLEXIBLE, the contained ApplicationArrayElement shall fulfill all of the following
	 * conditions:
	 */

	// @Check(constraint="constr_1317")
	// def void constr_1317( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1318 Profile ARTechTerm_VSA_LINEAR for ImplementationDataType If the
	 * ImplementationDataType.dynamicArraySizeProfile of ImplementationDataType is set to ARTechTerm_VSA_LINEAR, the
	 * contained ImplementationDataTypeElement shall fulfill all of the following conditions:
	 */

	// @Check(constraint="constr_1318")
	// def void constr_1318( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1319 Profile ARTechTerm_VSA_SQUARE for ImplementationDataType If the
	 * ImplementationDataType.dynamicArraySizeProfile of ImplementationDataType is set to ARTechTerm_VSA_SQUARE, the
	 * contained ImplementationDataTypeElement shall fulfill all of the following conditions:
	 */

	// @Check(constraint="constr_1319")
	// def void constr_1319( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1320 Profile ARTechTerm_VSA_RECTANGULAR for ImplementationDataType If the
	 * ImplementationDataType.dynamicArraySizeProfile of ImplementationDataType is set to ARTechTerm_VSA_RECTANGULAR,
	 * the contained ImplementationDataTypeElement shall have an ImplementationDataTypeElement that fulfills all of the
	 * following conditions:
	 */

	// @Check(constraint="constr_1320")
	// def void constr_1320( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1321 Profile ARTechTerm_VSA_FULLY_FLEXIBLE for ImplementationDataType If the
	 * ImplementationDataType.dynamicArraySizeProfile of ImplementationDataType is set to ARTechTerm_VSA_FULLY_FLEXIBLE,
	 * the contained ImplementationDataTypeElement shall have an ImplementationDataTypeElement that fulfills all of the
	 * following conditions:
	 */

	// @Check(constraint="constr_1321")
	// def void constr_1321( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1322 ARTechTerm_Size Indicator for undefined ApplicationDataType.dynamicArraySizeProfile If the
	 * ImplementationDataType is mapped to an ApplicationArrayDataType where the attribute
	 * ApplicationArrayDataType.ApplicationArrayDataType.dynamicArraySizeProfile exists, the ImplementationDataType
	 * shall have the Identifiable.categoryARTechTerm_STRUCTURE.
	 */

	// @Check(constraint="constr_1322")
	// def void constr_1322( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1323 Applicability of attribute ReceiverComSpec.ReceiverComSpec.usesEndToEndProtection The attribute
	 * ReceiverComSpec.ReceiverComSpec.usesEndToEndProtection shall be set to false for all ReceiverComSpec that
	 * aggregate EndToEndTransformationDescription in the role ReceiverComSpec.transformationComSpecProps.
	 */

	// @Check(constraint="constr_1323")
	// def void constr_1323( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1363 Existence of attributes of DiagnosticValueNeeds if DiagnosticValueNeeds is aggregated by a
	 * SwcServiceDependency in the role SwcServiceDependency.serviceNeeds then the attributes
	 */

	// @Check(constraint="constr_1363")
	// def void constr_1363( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1364 Existence of attributes of DiagnosticIoControlNeeds if DiagnosticIoControlNeeds is aggregated by a
	 * SwcServiceDependency in the role SwcServiceDependency.serviceNeeds then the attributes
	 */

	// @Check(constraint="constr_1364")
	// def void constr_1364( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1375 Existence of attributes of CompuMethod and related meta-classes The existence of attributes of
	 * CompuMethod and related meta-classes depending on the value of the Identifiable.category shall follow the
	 * restrictions documented in Table~table:CategoriesCompuMethod.
	 */

	// @Check(constraint="constr_1375")
	// def void constr_1375( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1381 Appearance of core-related possible values of MemorySection.MemorySection.option or
	 * SwAddrMethod.SwAddrMethod.option according to TPS_SWCT_01456 Any given list of values stored in the attributes
	 * MemorySection.MemorySection.option or SwAddrMethod.SwAddrMethod.option shall at most include a single value out
	 * of the following list:
	 */

	// @Check(constraint="constr_1381")
	// def void constr_1381( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1382 Mutually exclusive existence of attributes SwVariableRefProxy.SwVariableRefProxy.autosarVariable vs.
	 * SwVariableRefProxy.SwVariableRefProxy.mcDataInstanceVar In any given AUTOSAR model, the aggregations
	 * SwVariableRefProxy.SwVariableRefProxy.autosarVariable and SwVariableRefProxy.SwVariableRefProxy.mcDataInstanceVar
	 * shall never exist at the same time.
	 */

	// @Check(constraint="constr_1382")
	// def void constr_1382( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_1383 Existence of CompuMethod and DataConstr for ImplementationDataTypes of
	 * Identifiable.categoryARTechTerm_TYPE_REFERENCE The existence of
	 * ImplementationDataType.ImplementationDataType.swDataDefProps.SwDataDefProps.comp uMethod and
	 * ImplementationDataType.ImplementationDataType.swDataDefProps.SwDataDefProps.data Constr for
	 * ImplementationDataTypes of Identifiable.categoryARTechTerm_TYPE_REFERENCE is only allowed if the respective
	 * ImplementationDataType, after all type references are resolved, ends up in an ImplementationDataType of
	 * Identifiable.categoryARTechTerm_VALUE.
	 */

	// @Check(constraint="constr_1383")
	// def void constr_1383( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2000 Compatibility of ClientServerOperations triggering the same RunnableEntity The ClientServerOperations
	 * are considered compatible if the number of arguments (which can be ArgumentDataPrototypes or related
	 * PortDefinedArgumentValues) is equal and the corresponding arguments (i.e. first argument on both sides, second
	 * argument on both sides, etc.) are compatible.
	 */

	// @Check(constraint="constr_2000")
	// def void constr_2000( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2002 Referenced VariableDataPrototype from AutosarVariableRef of VariableAccess in role
	 * RunnableEntity.dataReadAccess A VariableAccess in the role RunnableEntity.dataReadAccess shall refer to an
	 * RPortPrototype or PRPortPrototype that is typed by either a SenderReceiverInterface or a NvDataInterface.
	 */

	// @Check(constraint="constr_2002")
	// def void constr_2002( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2003 Referenced VariableDataPrototype from AutosarVariableRef of VariableAccess in role
	 * RunnableEntity.dataWriteAccess A VariableAccess in the role RunnableEntity.dataWriteAccess shall refer to a
	 * PPortPrototype or PRPortPrototype that is typed by either a SenderReceiverInterface or a NvDataInterface.
	 */

	// @Check(constraint="constr_2003")
	// def void constr_2003( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2004 Referenced VariableDataPrototype from AutosarVariableRef of VariableAccess in role
	 * RunnableEntity.dataSendPoint A VariableAccess in the role RunnableEntity.dataSendPoint shall refer to a
	 * PPortPrototype or PRPortPrototype that is typed by either a SenderReceiverInterface or a NvDataInterface.
	 */

	// @Check(constraint="constr_2004")
	// def void constr_2004( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2005 Referenced VariableDataPrototype from AutosarVariableRef of VariableAccess in role
	 * RunnableEntity.dataReceivePointByValue or RunnableEntity.dataReceivePointByArgument A VariableAccess in the role
	 * RunnableEntity.dataReceivePointByValue or RunnableEntity.dataReceivePointByArgument shall refer to an
	 * RPortPrototype or PRPortPrototype that is typed by either a SenderReceiverInterface or an NvDataInterface.
	 */

	// @Check(constraint="constr_2005")
	// def void constr_2005( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2006 Number of AsynchronousServerCallResultPoint referencing to one AsynchronousServerCallPoint The
	 * AsynchronousServerCallPoint has to be referenced by exactly one AsynchronousServerCallResultPoint. This means
	 * that only the RunnableEntity with this AsynchronousServerCallResultPoint can fetch the result of the asynchronous
	 * server invocation of this particular AsynchronousServerCallPoint.
	 */

	// @Check(constraint="constr_2006")
	// def void constr_2006( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2007 Consistency of PerInstanceMemory.typeDefinition attribute All PerInstanceMemorys of the same
	 * SwcInternalBehavior with identical PerInstanceMemory.type attribute shall define an identical
	 * PerInstanceMemory.typeDefinition attribute as well.
	 */

	// @Check(constraint="constr_2007")
	// def void constr_2007( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2009 Supported kinds of PortPrototypes of a NvBlockSwComponentType With respect to external communication,
	 * NvBlockSwComponentType is limited to the definition of the following kinds of PortPrototype:
	 */

	// @Check(constraint="constr_2009")
	// def void constr_2009( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2010 Connections between SwComponentPrototypes of type NvBlockSwComponentType The existence of
	 * SwConnectors that refer to PortPrototypes belonging to SwComponentPrototypes where both are typed by
	 * NvBlockSwComponentType is not permitted.
	 */

	// @Check(constraint="constr_2010")
	// def void constr_2010( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2011 Connections between SwComponentPrototypes typed by NvBlockSwComponentType and SwComponentPrototypes
	 * typed by other AtomicSwComponentTypes The nv dataPortPrototypes of the SwComponentPrototype typed by an
	 * NvBlockSwComponentType are either connected with PortPrototypes typed by NvDataInterfaces or
	 * SenderReceiverInterfaces of other AtomicSwComponentType.
	 */

	// @Check(constraint="constr_2011")
	// def void constr_2011( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2012 Compatibility of ImplementationDataTypes used for NvBlockDescriptor.ramBlock and
	 * NvBlockDescriptor.romBlock The NvBlockDescriptor.ramBlock and the NvBlockDescriptor.romBlock shall have
	 * compatible ImplementationDataTypes to ensure, that the NVRAM Block default values in the ROM Block can be copied
	 * into the RAM Block.
	 */

	// @Check(constraint="constr_2012")
	// def void constr_2012( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2013 Compatibility of ImplementationDataTypes for NvBlockDataMapping The NvBlockDataMapping is only valid
	 * if the ImplementationDataType of the referenced VariableDataPrototype or ImplementationDataTypeElement in the
	 * role NvBlockDataMapping.nvRamBlockElement is compatible to the ImplementationDataType used to type the
	 * VariableDataPrototype aggregated by NvBlockDataMapping in the role NvBlockDataMapping.writtenNvData,
	 * NvBlockDataMapping.writtenReadNvData, or NvBlockDataMapping.readNvData.
	 */

	// @Check(constraint="constr_2013")
	// def void constr_2013( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2014 Limitation of RoleBasedPortAssignment.RoleBasedPortAssignment.role in NvBlockDescriptors The
	 * RoleBasedPortAssignment.role has to be set to a valid name of the Standardized AUTOSAR Interface used for the
	 * NVRAM Manager e.g. NvMNotifyJobFinished or NvMNotifyInitBlock.
	 */

	// @Check(constraint="constr_2014")
	// def void constr_2014( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2015 Limitation of SwcInternalBehavior of a NvBlockSwComponentType The SwcInternalBehavior of a
	 * NvBlockSwComponentType is only permitted to define
	 */

	// @Check(constraint="constr_2015")
	// def void constr_2015( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2016 Connections between SwComponentPrototypes of type ServiceProxySwComponentType A connection between
	 * PortPrototypes belonging to SwComponentPrototypes where both are typed by ServiceProxySwComponentType is not
	 * permitted.
	 */

	// @Check(constraint="constr_2016")
	// def void constr_2016( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2017 Ports of ServiceProxySwComponentTypes ServiceProxySwComponentType is only permitted to define
	 */

	// @Check(constraint="constr_2017")
	// def void constr_2017( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2018 Supported remote communication of a ServiceProxySwComponentType For remote communication,
	 * ServiceProxySwComponentType can have only RPortPrototypes typed by SenderReceiverInterfaces in a 1:n
	 * communication scenario.
	 */

	// @Check(constraint="constr_2018")
	// def void constr_2018( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2019 ServiceSwComponentType shall have service ports only In the case of ServiceSwComponentType, all
	 * aggregated PortPrototypes need to have an isOfType relationship to a PortInterface which has its
	 * PortInterface.isService attribute set to true. The exceptions described in TPS_SWCT_01572, TPS_SWCT_01579 and
	 * TPS_SWCT_01580 apply.
	 */

	// @Check(constraint="constr_2019")
	// def void constr_2019( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2020 RunnableEntity.dataReadAccess can not be used for queued communication The
	 * SwDataDefProps.swImplPolicy of the VariableDataPrototype referenced by a VariableAccess in role
	 * RunnableEntity.dataReadAccess shall not be set to SwImplPolicyEnum.queued.
	 */

	// @Check(constraint="constr_2020")
	// def void constr_2020( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2021 WaitPoint referencing a DataReceivedEvent can not be used for non-queued communication A WaitPoint
	 * referencing a DataReceivedEvent is permitted if and only if the SwDataDefProps.swImplPolicy of the
	 * VariableDataPrototype referenced by this DataReceivedEvent is set to SwImplPolicyEnum.queued.
	 */

	// @Check(constraint="constr_2021")
	// def void constr_2021( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2022 Mutually exclusive use of SynchronousServerCallPoints and AsynchronousServerCallPoints A
	 * ClientServerOperation of a particular RPortPrototype shall be mutually exclusive referenced by either a
	 * SynchronousServerCallPoints or an AsynchronousServerCallPoints.
	 */

	// @Check(constraint="constr_2022")
	// def void constr_2022( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2023 Consistency of ServerCallPoint.timeout values The ServerCallPoint.timeout values of all
	 * ServerCallPoints referencing the same instance of ClientServerOperation in a RPortPrototype shall be identical.
	 */

	// @Check(constraint="constr_2023")
	// def void constr_2023( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2024 PortAPIOption.enableTakeAddress is restricted to single instantiation The definition of a
	 * PortAPIOption with PortAPIOption.enableTakeAddress set to true is only permitted for software-components where
	 * the attribute SwcInternalBehavior.SwcInternalBehavior.supportsMultipleInstantiation is set to false.
	 */

	// @Check(constraint="constr_2024")
	// def void constr_2024( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2026 Referenced VariableDataPrototype from AutosarVariableRef of VariableAccess in role
	 * RunnableEntity.writtenLocalVariable and RunnableEntity.readLocalVariable A VariableDataPrototype in the
	 * AutosarVariableRef.localVariable reference needs to be owned by the same SwcInternalBehavior as this
	 * RunnableEntity belongs to, and the referenced VariableDataPrototype has to be defined in the role
	 * SwcInternalBehavior.implicitInterRunnableVariable or SwcInternalBehavior.explicitInterRunnableVariable.
	 */

	// @Check(constraint="constr_2026")
	// def void constr_2026( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2027 SwcServiceDependency shall be defined for service ports only A PortPrototype that is referenced by a
	 * SwcServiceDependency via RoleBasedPortAssignment.assignedPort shall be typed by a PortInterface that has
	 * PortInterface.isService set to true.
	 */

	// @Check(constraint="constr_2027")
	// def void constr_2027( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2028 InternalBehavior.staticMemory is restricted to single instantiation The InternalBehavior.staticMemory
	 * is only supported if the attribute SwcInternalBehavior.supportsMultipleInstantiation of the owning
	 * SwcInternalBehavior is set to false
	 */

	// @Check(constraint="constr_2028")
	// def void constr_2028( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2029 Referrable.shortName of InternalBehavior.constantMemory and InternalBehavior.staticMemory The
	 * Referrable.shortName of a VariableDataPrototype in role InternalBehavior.staticMemory or a ParameterDataPrototype
	 * in role InternalBehavior.constantMemory has to be equal with the 'C' identifier of the described variable resp.
	 * constant.
	 */

	// @Check(constraint="constr_2029")
	// def void constr_2029( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2030 AsynchronousServerCallResultPoint combined with WaitPoint shall belong to the same RunnableEntity The
	 * WaitPoint which references a AsynchronousServerCallReturnsEvent and the AsynchronousServerCallResultPoint which
	 * is referenced by this AsynchronousServerCallReturnsEvent shall be aggregated by the same RunnableEntity.
	 */

	// @Check(constraint="constr_2030")
	// def void constr_2030( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2031 Period of TimingEvent shall be greater than 0 The value of the attribute period of TimingEvent shall
	 * be greater than 0.
	 */

	// @Check(constraint="constr_2031")
	// def void constr_2031( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2033 Timeout of DataSendCompletedEvent The WaitPoint.timeout value of a WaitPoint associated with a
	 * DataSendCompletedEvent shall have the same value as the corresponding value of
	 * TransmissionAcknowledgementRequest.TransmissionAcknowledgementRequest.timeout.
	 */

	// @Check(constraint="constr_2033")
	// def void constr_2033( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2034 SwAddrMethod referenced by RunnableEntitys or BswSchedulableEntitys RunnableEntitys and
	 * BswSchedulableEntitys shall not reference a SwAddrMethod which attribute
	 * SwAddrMethod.memoryAllocationKeywordPolicy is set to
	 * MemoryAllocationKeywordPolicyType.addrMethodShortNameAndAlignment.
	 */

	// @Check(constraint="constr_2034")
	// def void constr_2034( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2035 SwDataDefProps.swImplPolicy for VariableDataPrototype in SenderReceiverInterface The overriding
	 * SwDataDefProps.swImplPolicy attribute value of a VariableDataPrototype in SenderReceiverInterface shall be
	 * SwImplPolicyEnum.standard, SwImplPolicyEnum.queued or SwImplPolicyEnum.measurementPoint.
	 */

	// @Check(constraint="constr_2035")
	// def void constr_2035( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2036 SwDataDefProps.swImplPolicy for VariableDataPrototype in NvDataInterface The overriding
	 * SwDataDefProps.swImplPolicy attribute value of a VariableDataPrototype in NvDataInterface shall be
	 * SwImplPolicyEnum.standard.
	 */

	// @Check(constraint="constr_2036")
	// def void constr_2036( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2037 SwDataDefProps.swImplPolicy for VariableDataPrototype in the role NvBlockDescriptor.ramBlock The
	 * overriding SwDataDefProps.swImplPolicy attribute value of a VariableDataProt otype in the role
	 * NvBlockDescriptor.ramBlock shall be SwImplPolicyEnum.standard.
	 */

	// @Check(constraint="constr_2037")
	// def void constr_2037( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2038 SwDataDefProps.swImplPolicy for VariableDataPrototype in the role
	 * SwcInternalBehavior.implicitInterRunnableVariable The overriding SwDataDefProps.swImplPolicy attribute value of a
	 * VariableDataProt otype in the role SwcInternalBehavior.implicitInterRunnableVariable shall be
	 * SwImplPolicyEnum.standard.
	 */

	// @Check(constraint="constr_2038")
	// def void constr_2038( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2039 SwDataDefProps.swImplPolicy for VariableDataPrototype in the role
	 * SwcInternalBehavior.explicitInterRunnableVariable The overriding SwDataDefProps.swImplPolicy attribute value of a
	 * VariableDataProt otype in the role SwcInternalBehavior.explicitInterRunnableVariable shall be
	 * SwImplPolicyEnum.standard.
	 */

	// @Check(constraint="constr_2039")
	// def void constr_2039( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2040 SwDataDefProps.swImplPolicy for VariableDataPrototype in the role
	 * SwcInternalBehavior.arTypedPerInstanceMemory The overriding SwDataDefProps.swImplPolicy attribute value of a
	 * VariableDataProt otype in the role SwcInternalBehavior.arTypedPerInstanceMemory shall be
	 * SwImplPolicyEnum.standard or SwImplPolicyEnum.measurementPoint.
	 */

	// @Check(constraint="constr_2040")
	// def void constr_2040( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2041 SwDataDefProps.swImplPolicy for VariableDataPrototype in the role SwcInternalBehavior.staticMemory
	 * The overriding SwDataDefProps.swImplPolicy attribute value of a VariableDataProt otype in the role
	 * SwcInternalBehavior.staticMemory shall be SwImplPolicyEnum.standard, SwImplPolicyEnum.measurementPoint or
	 * SwImplPolicyEnum.message.
	 */

	// @Check(constraint="constr_2041")
	// def void constr_2041( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2042 SwDataDefProps.swImplPolicy for ParameterDataPrototype in ParameterInterface The overriding
	 * SwDataDefProps.swImplPolicy attribute value of a ParameterDataPrototype in ParameterInterface shall be
	 * SwImplPolicyEnum.standard, SwImplPolicyEnum.const or SwImplPolicyEnum.fixed.
	 */

	// @Check(constraint="constr_2042")
	// def void constr_2042( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2043 SwDataDefProps.swImplPolicy for ParameterDataPrototype in the role SwcInternalBehavior.staticMemory
	 * The overriding SwDataDefProps.swImplPolicy attribute value of a ParameterDataPro totype in the role
	 * NvBlockDescriptor.romBlock shall be SwImplPolicyEnum.standard.
	 */

	// @Check(constraint="constr_2043")
	// def void constr_2043( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2044 SwDataDefProps.swImplPolicy for ParameterDataPrototype in the role
	 * SwcInternalBehavior.sharedParameter The overriding SwDataDefProps.swImplPolicy attribute value of a
	 * ParameterDataPro totype in the role SwcInternalBehavior.sharedParameter shall be SwImplPolicyEnum.standard.
	 */

	// @Check(constraint="constr_2044")
	// def void constr_2044( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2045 SwDataDefProps.swImplPolicy for ParameterDataPrototype in the role
	 * SwcInternalBehavior.perInstanceParameter The overriding SwDataDefProps.swImplPolicy attribute value of a
	 * ParameterDataPro totype in the role SwcInternalBehavior.sharedParameter shall be SwImplPolicyEnum.standard.
	 */

	// @Check(constraint="constr_2045")
	// def void constr_2045( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2046 SwDataDefProps.swImplPolicy for ParameterDataPrototype in the role SwcInternalBehavior.constantMemory
	 * The overriding SwDataDefProps.swImplPolicy attribute value of a ParameterDataPro totype in the role
	 * SwcInternalBehavior.sharedParameter shall be SwImplPolicyEnum.standard, SwImplPolicyEnum.const or
	 * SwImplPolicyEnum.fixed.
	 */

	// @Check(constraint="constr_2046")
	// def void constr_2046( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2047 SwDataDefProps.swImplPolicy for ArgumentDataPrototype The overriding SwDataDefProps.swImplPolicy
	 * attribute value of a ArgumentDataPrototype shall be SwImplPolicyEnum.standard.
	 */

	// @Check(constraint="constr_2047")
	// def void constr_2047( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2048 SwDataDefProps.swImplPolicy for SwServiceArg The overriding SwDataDefProps.swImplPolicy attribute
	 * value of a SwServiceArg shall be SwImplPolicyEnum.standard or SwImplPolicyEnum.const.
	 */

	// @Check(constraint="constr_2048")
	// def void constr_2048( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2049 Different ModeDeclarationGroups shall have different Referrable.shortNames. A software component is
	 * not allowed to type multiple PortPrototypes with ModeSwitchInterfaces where the contained
	 * ModeDeclarationGroupPrototypes are referencing ModeDeclarationGroups with identical Referrable.shortNames but
	 * different ModeDeclarations.
	 */

	// @Check(constraint="constr_2049")
	// def void constr_2049( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2050 Mandatory information of a SwAxisCont If the attribute ApplicationValueSpecification.swAxisCont is
	 * defined for an ApplicationValueSpecification the SwAxisCont shall define one SwAxisCont.swAxisIndex value and one
	 * SwAxisCont.swArraysize value per dimension, even in the case when the owning ApplicationValueSpecification
	 * defines only the content of a single dimensional object like a CURVE.
	 */

	// @Check(constraint="constr_2050")
	// def void constr_2050( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2051 Mandatory information of a SwValueCont If the attribute ApplicationValueSpecification.swValueCont is
	 * defined for an ApplicationValueSpecification the SwValueCont shall always define the attribute
	 * SwValueCont.swArraysize if the ApplicationValueSpecification is of Identifiable.categoryARTechTerm_CURVE,
	 * ARTechTerm_MAP, ARTechTerm_CUBOID, ARTechTerm_CUBE_4, ARTechTerm_CUBE_5, ARTechTerm_COM_AXIS,
	 * ARTechTerm_RES_AXIS, ARTechTerm_CURVE_AXIS, or ARTechTerm_VAL_BLK.
	 */

	// @Check(constraint="constr_2051")
	// def void constr_2051( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2052 Values of SwValueCont.swArraySize and the number of values provided by SwAxisCont.swValuesPhys shall
	 * be consistent. SwAxisCont.swValuesPhys shall define as many numbers of values as the SwValueCont.swArraysize
	 * defines. In other words, in the bound model the number of descendants (SwValues.v, or SwValues.vf, or
	 * SwValues.vt, or SwValues.vtf) shall be identical to the number of elements of the related DataPrototype typed by
	 * an ApplicationPrimitiveDataType.
	 */

	// @Check(constraint="constr_2052")
	// def void constr_2052( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2053 Consistency between RoleBasedPortAssignment.roleIUMPRNumerator and
	 * ObdRatioServiceNeeds.ObdRatioServiceNeeds.connectionType If a SwcServiceDependency with a ObdRatioServiceNeeds is
	 * defined and the attribute ObdRatioServiceNeeds.connectionType of the contained ObdRatioServiceNeeds is set to
	 * ObdRatioConnectionKindEnum.ObdRatioConnectionKindEnum.apiUse a RoleBasedPortAssignment with the
	 * RoleBasedPortAssignment.role value IUMPRNumerator shall be defined.
	 */

	// @Check(constraint="constr_2053")
	// def void constr_2053( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2054 Valid targets of RapidPrototypingScenario.rptSystem The System referenced in the role
	 * RapidPrototypingScenario.rptSystem shall be of Identifiable.categoryRPT_SYSTEM.
	 */

	// @Check(constraint="constr_2054")
	// def void constr_2054( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2055 Valid targets of RptContainer.byPassPoint and RptContainer.rptHook reference Depending on the
	 * Identifiable.category value the targets of RptContainer.byPassPoint and RptContainer.rptHook references are
	 * restricted according table table:Category_of_RptContainers.
	 */

	// @Check(constraint="constr_2055")
	// def void constr_2055( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2056 Consistency of RapidPrototypingScenario with respect to RapidPrototypingScenario.rptSystem and
	 * RptHook.rptArHook references Within one RapidPrototypingScenario all RapidPrototypingScenario.rptSystem refer
	 * ences shall point to instances in one and only one System and if existent all RptHook.rptArHook shall point to
	 * instances in one other and only one other System.
	 */

	// @Check(constraint="constr_2056")
	// def void constr_2056( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2057 Mandatory information of a RuleBasedAxisCont If the attribute
	 * ApplicationRuleBasedValueSpecification.swAxisCont is defined for an ApplicationRuleBasedValueSpecification the
	 * RuleBasedAxisCont shall define one RuleBasedAxisCont.swAxisIndex value and one RuleBasedAxisCont.swArraysize
	 * value per dimension, even in the case when the owning ApplicationRuleBasedValueSpecification defines only the
	 * content of a single dimensional object like a ARTechTerm_CURVE.
	 */

	// @Check(constraint="constr_2057")
	// def void constr_2057( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2058 Mandatory information of a RuleBasedValueCont If the attribute
	 * ApplicationRuleBasedValueSpecification.swValueCont is defined for an ApplicationRuleBasedValueSpecification the
	 * RuleBasedValueCont shall define always the attribute RuleBasedValueCont.swArraysize if the
	 * ApplicationRuleBasedValueSpecification is of Identifiable.categoryARTechTerm_CURVE, ARTechTerm_MAP,
	 * ARTechTerm_CUBOID, ARTechTerm_CUBE_4, ARTechTerm_CUBE_5, ARTechTerm_COM_AXIS, ARTechTerm_RES_AXIS,
	 * ARTechTerm_CURVE_AXIS, ARTechTerm_VAL_BLK or ARTechTerm_ARRAY.
	 */

	// @Check(constraint="constr_2058")
	// def void constr_2058( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2535 Target of an AutosarParameterRef.autosarParameter in AutosarParameterRef shall refer to a parameter
	 * Except for the specifically described cases where constr_1173 applies the target of
	 * AutosarParameterRef.autosarParameter (which in fact is an instance ref) in AutosarParameterRef shall either be or
	 * be nested in ParameterDataPrototype. This means that the target shall either be a ParameterDataPrototype or an
	 * ApplicationCompositeElementDataPrototype that in turn is owned by a ParameterDataPrototype.
	 */

	// @Check(constraint="constr_2535")
	// def void constr_2535( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2536 Target of an AutosarVariableRef.autosarVariable in AutosarVariableRef shall refer to a variable The
	 * target of AutosarVariableRef.autosarVariable (which in fact is an instance ref) in AutosarVariableRef shall
	 * either be or be nested in VariableDataPrototype. This means that the target shall either be a
	 * VariableDataPrototype or an ApplicationCompositeElementDataPrototype that in turn is owned by a
	 * VariableDataPrototype.
	 */

	// @Check(constraint="constr_2536")
	// def void constr_2536( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2544 Limits need to be consistent The limits of ApplicationDataType shall be inside of the definition
	 * range of the CompuMethod
	 */

	// @Check(constraint="constr_2544")
	// def void constr_2544( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2545 SwDataDefProps.invalidValue shall fit in the specified ranges The SwDataDefProps.invalidValue shall
	 * be in the range of the ImplementationDataType.
	 */

	// @Check(constraint="constr_2545")
	// def void constr_2545( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2548 Data constraint of value axis shall match The values compliant to
	 * SwDataDefProps.SwDataDefProps.dataConstr shall be also b e compliant to
	 * SwDataDefProps.SwDataDefProps.valueAxisDataType.AutosarDataType.swDataDefProps.S wDataDefProps.dataConstr.
	 */

	// @Check(constraint="constr_2548")
	// def void constr_2548( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2549 Units of input axis shall be consistent
	 */

	// @Check(constraint="constr_2549")
	// def void constr_2549( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2550 Units of value axis shall be consistent
	 */

	// @Check(constraint="constr_2550")
	// def void constr_2550( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2561 Application of DataConstrRule.DataConstrRule.constrLevel DataConstrRule.DataConstrRule.constrLevel is
	 * limited to
	 */

	// @Check(constraint="constr_2561")
	// def void constr_2561( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4000 Local communication of mode switches Ports with ModeSwitchInterfaces cannot be connected across ECU
	 * boundaries.
	 */

	// @Check(constraint="constr_4000")
	// def void constr_4000( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4002 Unambiguous mapping of modes to data types Within one DataTypeMappingSet, a ModeDeclarationGroup
	 * shall not be mapped to different ImplementationDataTypes.
	 */

	// @Check(constraint="constr_4002")
	// def void constr_4002( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4003 Semantics of SwcModeSwitchEvent If the value of SwcModeSwitchEvent.SwcModeSwitchEvent.activation is
	 * ModeActivationKind.onTransition then SwcModeSwitchEvent shall refer to two different ModeDeclarations belonging
	 * to the same instance of ModeDeclarationGroup.
	 */

	// @Check(constraint="constr_4003")
	// def void constr_4003( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4004 Context of SenderReceiverAnnotation A SenderReceiverAnnotation shall only be aggregated by a
	 * PortPrototype typed by a SenderReceiverInterface.
	 */

	// @Check(constraint="constr_4004")
	// def void constr_4004( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4005 Context of ClientServerAnnotation A ClientServerAnnotation shall only be aggregated by a
	 * PortPrototype typed by a ClientServerInterface.
	 */

	// @Check(constraint="constr_4005")
	// def void constr_4005( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4006 Context of ParameterPortAnnotation A ParameterPortAnnotation shall only be aggregated by a
	 * PPortPrototype owned by a ParameterSwComponentType.
	 */

	// @Check(constraint="constr_4006")
	// def void constr_4006( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4007 Context of ModePortAnnotation A ModePortAnnotation shall only be aggregated by a PortPrototype typed
	 * by a ModeSwitchInterface.
	 */

	@Check(constraint = "constr_4007")
	def void constr_4007(PPortPrototype pPortPrototype) {
		if (!pPortPrototype.getModePortAnnotations().isEmpty() && !(pPortPrototype.getProvidedInterface() instanceof ModeSwitchInterface))
			issue(pPortPrototype, ComponentsPackage.Literals.PORT_PROTOTYPE__NV_DATA_PORT_ANNOTATIONS)
	}

	@Check(constraint = "constr_4007")
	def void constr_4007r(RPortPrototype rPortPrototype) {
		if (!rPortPrototype.getModePortAnnotations().isEmpty() && !(rPortPrototype.getRequiredInterface() instanceof ModeSwitchInterface))
			issue(rPortPrototype, ComponentsPackage.Literals.PORT_PROTOTYPE__NV_DATA_PORT_ANNOTATIONS)
	}

	/**
	 * constr_4008 Context of TriggerPortAnnotation A TriggerPortAnnotation shall only be aggregated by a PortPrototype
	 * typed by a TriggerInterface.
	 */

	@Check(constraint = "constr_4008")
	def void constr_4008(PPortPrototype pPortPrototype) {
		if (!pPortPrototype.getTriggerPortAnnotations().isEmpty() && !(pPortPrototype.getProvidedInterface() instanceof TriggerInterface))
			issue(pPortPrototype, ComponentsPackage.Literals.PORT_PROTOTYPE__NV_DATA_PORT_ANNOTATIONS)
	}

	@Check(constraint = "constr_4008")
	def void constr_4008r(RPortPrototype rPortPrototype) {
		if (!rPortPrototype.getTriggerPortAnnotations().isEmpty() && !(rPortPrototype.getRequiredInterface() instanceof TriggerInterface))
			issue(rPortPrototype, ComponentsPackage.Literals.PORT_PROTOTYPE__NV_DATA_PORT_ANNOTATIONS)
	}

	/**
	 * constr_4009 Context of NvDataPortAnnotation An NvDataPortAnnotation shall only be aggregated by a PortPrototype
	 * typed by an NvDataInterface.
	 */

	@Check(constraint = "constr_4009")
	def void constr_4009(PPortPrototype pPortPrototype) {
		if (!pPortPrototype.getNvDataPortAnnotations().isEmpty() && !(pPortPrototype.getProvidedInterface() instanceof NvDataInterface))
			issue(pPortPrototype, ComponentsPackage.Literals.PORT_PROTOTYPE__NV_DATA_PORT_ANNOTATIONS)
	}

	@Check(constraint = "constr_4009")
	def void constr_4009r(RPortPrototype rPortPrototype) {
		if (!rPortPrototype.getNvDataPortAnnotations().isEmpty() && !(rPortPrototype.getRequiredInterface() instanceof NvDataInterface))
			issue(rPortPrototype, ComponentsPackage.Literals.PORT_PROTOTYPE__NV_DATA_PORT_ANNOTATIONS)
	}

	/**
	 * constr_4010 Context of DelegatedPortAnnotation A DelegatedPortAnnotation shall only be aggregated by a
	 * PortPrototype aggregated by a CompositionSwComponentType.
	 */

	@Check(constraint = "constr_4010")
	def void constr_4010(PortPrototype portPrototype) {
		if (portPrototype.getDelegatedPortAnnotation() !== null && !(portPrototype.getSwComponentType() instanceof CompositionSwComponentType))
			issue(portPrototype, ComponentsPackage.Literals.PORT_PROTOTYPE__DELEGATED_PORT_ANNOTATION)
	}

	/**
	 * constr_4012 Timeout of ModeSwitchedAckEvent The timeout value of a WaitPoint associated with a
	 * ModeSwitchedAckEvent shall be equal to the corresponding ModeSwitchedAckRequest.ModeSwitchedAckRequest.timeout.
	 */

	// @Check(constraint="constr_4012")
	// def void constr_4012( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4035 ValueSpecification shall fit into data type An instance of ValueSpecification which is used to assign
	 * a value to a software object typed by an AutosarDataType shall fit into this AutosarDataType without losing
	 * information.
	 */

	// @Check(constraint="constr_4035")
	// def void constr_4035( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4082 RunnableEntity.ExecutableEntity.reentrancyLevel shall not be set. The optional attribute
	 * ExecutableEntity.reentrancyLevel shall not be set for a RunnableEntity. This attribute would define more specific
	 * reentrancy features than the mandatory attribute RunnableEntity.canBeInvokedConcurrently. These features are
	 * currently only supported for Basic Software.
	 */

	// @Check(constraint="constr_4082")
	// def void constr_4082( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	@Check(constraint = "AUTOSARDUMMY")
	protected def void dummy(AUTOSAR ar) {
	}

	@Check(constraint = "AUTOSARDUMMY")
	protected def void dummy(ARPackage arPackage) {
	}

	@Check(constraint = "AUTOSARDUMMY")
	protected def void dummy(EObject eObject) {
	}
}
