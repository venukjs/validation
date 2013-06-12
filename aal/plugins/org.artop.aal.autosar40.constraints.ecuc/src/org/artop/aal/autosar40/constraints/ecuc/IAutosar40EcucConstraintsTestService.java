package org.artop.aal.autosar40.constraints.ecuc;

import org.artop.aal.gautosar.services.IMetaModelService;

import autosar40.ecucparameterdef.EcucFloatParamDef;

public interface IAutosar40EcucConstraintsTestService extends IMetaModelService {

	String getMin(EcucFloatParamDef eObject);

}
