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
package org.artop.aal.examples.autosar442.check;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sphinx.emf.check.Check;

import autosar40.autosartoplevelstructure.AUTOSAR;
import autosar40.genericstructure.generaltemplateclasses.documentation.textmodel.languagedatamodel.LanguageSpecific;
import autosar40.genericstructure.generaltemplateclasses.documentation.textmodel.languagedatamodel.LanguagedatamodelPackage;

class GenericStructureTemplateValidator extends AbstractAutosarCheckValidator {

	/**
	 * constr_2501 Blueprint of blueprints are not supported Note that objects modeled particularly as a ``blueprint''
	 * (e.g. PortPrototypeBlueprint) also live in a package of category BLUEPRINT. Strictly speaking this means that
	 * they can be ``blueprints'' of ``blueprints''. This indirection is not intended and not supported.
	 */
	// @Check(constraint = "constr_2501")
	// def void constr_2501( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2502 Merged model shall be compliant to the meta-model A model merged from atpSplitable elements shall
	 * adhere to the consistency rules of the pure meta model. Note that the required lower multiplicity depend on the
	 * process phase therefore the AUTOSAR schema sets them mainly to 0. This also applies to the bound model.
	 */
	// @Check(constraint="constr_2502")
	// def void constr_2502( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2503 Bound model must be compliant to the pure meta model The completelyCompletely bound includes post
	 * build!Completely bound includes post build!Completely bound includes post build! bound M1 model must adhere to
	 * the pure meta model with respect to consistency rules and semantic constraints defined in the related template
	 * specifications. Especially, the multiplicity's in the bound model must conform to the multiplicity and the
	 * constraints of the pure meta model.
	 */
	// @Check(constraint="constr_2503")
	// def void constr_2503( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2504 Constraint to ConditionByFormula.bindingTime The tag vh.latestBindingTimeconstraints the value of the
	 * attribute ConditionByFormula.bindingTime from TPS_GST_00190. Hence, it defines the latest point in methodology
	 * which is allowed as value for ConditionByFormula.bindingTime of this particular application of atpVariation.
	 */
	// @Check(constraint="constr_2504")
	// def void constr_2504( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2505 Multiplicity after binding.
	 */
	// @Check(constraint="constr_2505")
	// def void constr_2505( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2506 Attributes in property set pattern On M1 level, let C be the set of attributes (or aggregated
	 * elementsThe constraints defined in this section apply to attributes as well as aggregates elements, due to the
	 * close relationship of the two in the AUTOSAR meta model. For simplicity, the rest of this section talks about
	 * ``attributes'' only.) that would have been in the originalIn this context, ``original'' means {PropertySetClass}
	 * without the stereotype atpVariation. In other words, ``original'' means ` `as in the pure meta
	 * model''.{PropertySetClass} object, and C_1, \ldots{}, C_n b e the respective sets of attributes in the
	 * {PropertySetClass}Conditional objects for a given variant. Also, let C' be the set of non-optional attributes,
	 * e.g., those with a lower multiplicity of 1.
	 */
	// @Check(constraint="constr_2506")
	// def void constr_2506( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2507 EvaluatedVariantSet shall not refer to itself An EvaluatedVariantSet shall not refer to itself
	 * directly or via other EvaluatedVariantSet.
	 */
	// @Check(constraint="constr_2507")
	// def void constr_2507( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2508 Name space of Referrable.shortName The content of Referrable.shortName needs to be unique (case
	 * insensitive) within a given Identifiable. Note that the check for uniqueness of Referrable.short Name must be
	 * performed case insensitively. This supports the good practice that names should not differ in upper~/~lower~case
	 * only which would cause a lot of confusion.
	 */
	// @Check(constraint="constr_2508")
	// def void constr_2508( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2509 ReferenceBase needs to be unique in a package The ReferenceBase.shortLabel of a reference base needs
	 * to be unique in (not with in) a package. Note that it is not necessary to be unique within (to say in deeper
	 * levels) of a package.
	 */
	// @Check(constraint="constr_2509")
	// def void constr_2509( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2510 only one default ReferenceBase Only one ReferenceBase per level can be marked as default
	 * (default=''true'').
	 */
	// @Check(constraint="constr_2510")
	// def void constr_2510( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2511 Named reference bases shall be available If there is a relative references, then one of the
	 * containing packages shall ha ve a referenceBase with a ReferenceBase.shortLabel equal to the Ref.base of the
	 * reference.
	 */
	// @Check(constraint="constr_2511")
	// def void constr_2511( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2512 Referrable.shortName uniqueness constraint for variants Referrable.shortName +
	 * VariationPoint.shortLabel of a variant element must be unique within the name space established by the
	 * surrounding Identifiable.
	 */
	// @Check(constraint="constr_2512")
	// def void constr_2512( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2514 VariationPoint.shortLabel in VariationPoint must be unique The combination of Identifiable.shortName
	 * and VariationPoint.shortLabel shall be unique within the next enclosing Identifiable{WholeClass}. In case the
	 * Identifiable.shortName does not exist on the {PartClass} the VariationPoint.shortLabel i s unnecessary. In case
	 * the Identifiable.shortName of the {PartClass} is unique in the context of the {WholeClass} the
	 * VariationPoint.shortLabel is unnecessary.
	 */
	// @Check(constraint="constr_2514")
	// def void constr_2514( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2515 Avoid conflicting package categories Note that it is in the responsibility of the stakeholders to
	 * ensure that no conflicting category occurs.
	 */
	// @Check(constraint="constr_2515")
	// def void constr_2515( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2516 Return type of an AttributeValueVariationPoint When such a formula is evaluated by a software tool,
	 * and the return value of the formula is shall be compatible to the type of the attribute in the pure meta-model.
	 */
	// @Check(constraint="constr_2516")
	// def void constr_2516( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2517 VariationPoint.postbuildVariantCondition only for PostBuild Aggregation of PostBuildVariantCondition
	 * in VariationPoint is only allowed if the annotated model states vh.latestBindingTime to PostBuild.
	 */
	// @Check(constraint="constr_2517")
	// def void constr_2517( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2518 Binding time is constrained Note that this binding time is again constrained by the value of the tag
	 * vh.latestBindingTime.
	 */
	// @Check(constraint="constr_2518")
	// def void constr_2518( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2519 PredefinedVariants need to be consistent If a PredefinedVariant plus its
	 * PredefinedVariant.includedVariants references more than one SwSystemconstantValueSet all SwSystemconstValue.value
	 * attributes in SwSystemconstValues for a particular SwSystemconst must be identical.
	 */
	// @Check(constraint="constr_2519")
	// def void constr_2519( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2520 Nesting of lists shall be limited
	 */
	// @Check(constraint="constr_2520")
	// def void constr_2520( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2521 The AttributeValueVariationPoint.shortLabel in AttributeValueVariationPoint shall be unique The
	 * AttributeValueVariationPoint.shortLabel must be unique within the next enclosing Identifiable, and is used to
	 * individually address variation points in t he variant rich M1 model.
	 */
	// @Check(constraint="constr_2521")
	// def void constr_2521( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2522 Notes should not be nested Note even if it is possible to nest notes it is not recommended to do so,
	 * since it might lead to problems with the rendering of the note icon.
	 */
	// @Check(constraint="constr_2522")
	// def void constr_2522( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2523 Used languages need to be consistent The used languages of an AUTOSAR file are specified in the top
	 * level AUTOSAR.adminData. All other elements shall be provided in the languages specified for the document.
	 */
	@Check(constraint = "constr_2523")
	def void constr_2523(LanguageSpecific ls) {
		val AUTOSAR ar = EcoreUtil.getRootContainer(ls) as AUTOSAR;
		if (!ar.getAdminData().getUsedLanguages().getL10s().contains(ls.getL()))
			issue(ls, LanguagedatamodelPackage.Literals.LANGUAGE_SPECIFIC__L)
	}

	/**
	 * constr_2524 Non splitable elements in one file If the aggregation/attribute is notatpSplitable, then all
	 * aggregated element(s) shall be described in the same physical file as the aggregating element.
	 */
	// @Check(constraint="constr_2524")
	// def void constr_2524( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2525 Non splitable elements shall not be repeated Properties (namely aggregations and attributes) which
	 * are not marked as atpSplit able must be all together in one physical file. They must not be repeated i n the
	 * split files unless they are required for proper merging.
	 */
	// @Check(constraint="constr_2525")
	// def void constr_2525( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2530 InstanceRefs must be consistent The first AtpInstanceRef.atpContextElement in the path must be an
	 * AtpClassifier. atpFeature of the AtpInstanceRef.atpBase. For all subsequent AtpInstanceRef.atpContextElements,
	 * they must be an AtpClassifier.atpFeature of the AtpPrototype.atpType of the previous element (which is an
	 * AtpPrototype).
	 */
	// @Check(constraint="constr_2530")
	// def void constr_2530( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2531 AtpInstanceRef shall be close to the base An AtpInstanceRef shall be aggregated such that its
	 * relationship to the AtpClas sifier referenced in the role AtpInstanceRef.atpBase is unambiguous. This is the case
	 * in one of the following situations:
	 */
	// @Check(constraint="constr_2531")
	// def void constr_2531( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2533 Documentation context is either a feature or an identifiable One particular DocumentationContext
	 * shall be either a feature or an identifiable but not both at the same time. If this is desired, one should create
	 * multiple DocumentationContext.
	 */
	// @Check(constraint="constr_2533")
	// def void constr_2533( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2534 Limits of unlimited Integer Practically UnlimitedInteger shall be limited such that it fits into 64
	 * bit.
	 */
	// @Check(constraint="constr_2534")
	// def void constr_2534( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2537 Variation of PackageableElement is limited to components resp. modules Variation of ARElement in
	 * ARPackage shall be applied only to elements on a kind of component level. In particular this is
	 * BswModuleDescription, Documentation, Implementation, SwComponentType, TimingExtension. This constraint only
	 * applies if the PackageableElement is not a blueprint.
	 */
	// @Check(constraint="constr_2537")
	// def void constr_2537( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2538 Global reference is limited to certain elements The ability to perform a global reference is limited
	 * to Chapter, Topic1, Caption, Traceable, XrefTarget, Std, Xdoc, Xfile
	 */
	// @Check(constraint="constr_2538")
	// def void constr_2538( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2547 Ordered collections cannot be split into partial models
	 */
	// @Check(constraint="constr_2547")
	// def void constr_2547( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2557 No VariationPoints where vh.latestBindingTime set to BlueprintDerivationTime in system configurations
	 * Blueprints are not part of a system configuration. In consequence of this, in a system configuration there shall
	 * be no VariationPoint where vh.latestBindingTime is restricted to BlueprintDerivationTime by the meta model.
	 */
	// @Check(constraint="constr_2557")
	// def void constr_2557( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2558 If vh.latestBindingTime is BlueprintDerivationTime then there shall only be
	 * VariationPoint.blueprintCondition/AttributeValueVariationPoint.blueprintValue VariationPoints with
	 * vh.latestBindingTime restricted to BlueprintDerivation shall not have VariationPoint.swSysCond nor
	 * VariationPoint.postbuildVariantCondition.
	 */
	// @Check(constraint="constr_2558")
	// def void constr_2558( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2559 No nested VariationPoint As VariationPoint.blueprintCondition is a DocumentationBlock it could again
	 * contain VariationPoints and therefore would allow nesting of VariationPoints. This is not intended and shall not
	 * be used.
	 */
	// @Check(constraint="constr_2559")
	// def void constr_2559( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2567 Undefined Value in Attribute Value Blueprints If a AttributeValueVariationPoint.blueprintValue is
	 * specified, then the AttributeValueVariationPoint.value defined by the AttributeValueVariationPoint is not used
	 * and should therefore at least contain one term undefined which is to be refined when deriving objects from this
	 * blueprint.
	 */
	// @Check(constraint="constr_2567")
	// def void constr_2567( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2572 Unique Control of Document Languages The settings for multiple languages are specified in the
	 * top-Level AdminData only
	 */
	// @Check(constraint="constr_2572")
	// def void constr_2572( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2573 ICS shall not reference examples ICS is like a productive Model and therefore shall not reference to
	 * an EXAMPLE. Such a reference would be useless since the target needs to be ignored in the ICS.
	 */
	// @Check(constraint="constr_2573")
	// def void constr_2573( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2574 ReferenceBase.globalInPackage for global elements only ReferenceBase.ReferenceBase.globalInpackage is
	 * allowed only if ReferenceBase.isGlobal is set to true.
	 */
	// @Check(constraint="constr_2574")
	// def void constr_2574( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2575 AttributeValueVariationPoint.blueprintValue in blueprints only
	 * AttributeValueVariationPoint.blueprintValue is only allowed in blueprints and ma y not be present in a system
	 * description.
	 */
	// @Check(constraint="constr_2575")
	// def void constr_2575( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2577 Binding Time in Aggregation Pattern Within VariationPoint, the class ConditionByFormula has an
	 * attribute VariationPo int.bindingTime which defines the latest binding time for this variation point. This
	 * binding time is further constrained by the UML tag vh.latestBindingTime that is attached to the aggregation see
	 * TPS_GST_00190, TPS_GST_00220, TPS_GST_00221):
	 */
	// @Check(constraint="constr_2577")
	// def void constr_2577( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2578 Binding Time in Association Pattern Within VariationPoint, the class ConditionByFormula has an
	 * attribute VariationPo int.bindingTime which defines the latest binding time for this variation point. This
	 * binding time is further constrained by the UML tag vh.latestBindingTime that is attached to the association (see
	 * TPS_GST_00190, TPS_GST_00220,TPS_GST_00221):
	 */
	// @Check(constraint="constr_2578")
	// def void constr_2578( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2579 Binding Time in Attribute Value Pattern The meta class AttributeValueVariationPoint has an attribute
	 * VariationPoint.bind ingTime which defines the latest binding time for this variation point. This binding time is
	 * further constrained by the UML tag vh.latestBindingTime that is attached to the attribute (see TPS_GST_00190,
	 * TPS_GST_00220, TPS_GST_00221):
	 */
	// @Check(constraint="constr_2579")
	// def void constr_2579( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2580 Binding Time in Property Set Pattern The meta class VariationPoint has an attribute
	 * VariationPoint.bindingTime which defines the latest binding time for this variation point. This binding time is
	 * further constrained by the UML tag vh.latestBindingTime that is attached to the meta class which is marked as
	 * atpVariation (see TPS_GST_00190, TPS_GST_00220, TPS_GST_00221):
	 */
	// @Check(constraint="constr_2580")
	// def void constr_2580( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2581 Default life cycle state shall be defined properly LifeCycleInfoSet.defaultLcState in
	 * LifeCycleInfoSet shall reference to a LifeCycleStateDefinitionGroup.lcState defined in the
	 * LifeCycleStateDefinitionGroup referenced by LifeCycleInfoSet.usedLifeCycleStateDefinitionGroup.
	 */
	// @Check(constraint="constr_2581")
	// def void constr_2581( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2583 Used life cycle state shall be defined properly LifeCycleInfoSet.defaultLcState in LifeCycleInfo
	 * shall reference to a LifeCycleStateDefinitionGroup.lcState defined in the LifeCycleStateDefinitionGroup
	 * referenced by LifeCycleInfoSet.usedLifeCycleStateDefinitionGroup of the containing LifeCycleInfoSet.
	 */
	// @Check(constraint="constr_2583")
	// def void constr_2583( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2585 LifeCycleInfo shall be unambiguous Within one particular
	 * LifeCycleInfoSetLifeCycleInfoSet.lifeCycleInfo.LifeCycleInfo.lcObject shall be unique. This ensures that the
	 * association of a LifeCycleState to a Referrable is unambiguous.
	 */
	// @Check(constraint="constr_2585")
	// def void constr_2585( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2586 Constraints on LifeCyclePeriod The attributes LifeCyclePeriod.date, LifeCyclePeriod.arReleaseVersion,
	 * LifeCyclePeriod.productRelease in LifeCyclePeriod are mutually exclusive.
	 */
	// @Check(constraint="constr_2586")
	// def void constr_2586( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_2587 No System in AnyInstanceRef In consequence of constr_2531System shall not be
	 * AnyInstanceRef.contextElement nor AtpInstanceRef.target of an AnyInstanceRef. Otherwise AtpInstanceRef.atpBase
	 * would not be determined.
	 */
	// @Check(constraint="constr_2587")
	// def void constr_2587( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }

	/**
	 * constr_4055 ICS may not contain blueprints Since an Implementation Conformance Statement always describes a set
	 * of one or more fully configured software modules, a package with category ICS it is not allowed to contain
	 * sub-packages at any level which have the category BLUEPRINT.
	 */
	// @Check(constraint="constr_4055")
	// def void constr_4055( o ) {
	// if( ) {
	// issue(o,);
	// }
	// }
}
