package moh.adp.xml;

import moh.adp.common.CodeValueConsts;
import moh.adp.model.shared.Address;
import moh.adp.server.shared.AddressHelper;

public class AddressUtil {

	public static Address getAddress(Long stakeholderId, Long stakeholderTypeCd) {
		return AddressHelper.getAddressByStakeholderId(stakeholderId, stakeholderTypeCd, CodeValueConsts.ADDRESS_TYPE_ONTARIO_CD);
	}

}
