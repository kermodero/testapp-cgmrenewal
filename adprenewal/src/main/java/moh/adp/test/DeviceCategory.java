package moh.adp.test;

import moh.adp.common.DeviceCategoryConst;

/**
 *  SHOULD NOT BE REDEFINING THIS!
 * 
 */
public enum DeviceCategory {
	CA, 
	DSA,
	DSC,
	HD ,
	LP ,	
	MD ,	
	ME ,
	MI ,
	OP ,
	OR ,
	OXF,
	OXR,
	PM ,
	RE ,
	VA ;

	public static Long getId(String deviceCategory) {
		DeviceCategory dc = valueOf(deviceCategory);
		switch (dc) {
		case CA:
			return DeviceCategoryConst.CATEGORY_COMMUNICATION_AID_CA_ID;
		case DSA:
			break;
		case DSC:
			break;
		case HD:
			break;
		case LP:
			break;
		case MD:
			return DeviceCategoryConst.CATEGORY_MOBILITY_MD_ID;
		case ME:
			return DeviceCategoryConst.CATEGORY_MAX_EXTRAORAL_ME_ID;
		case MI:
			return DeviceCategoryConst.CATEGORY_MAX_INTRAORAL_MI_ID;
		case OP:
			break;
		case OR:
			break;
		case OXF:
			break;
		case OXR:
			break;
		case PM:
			break;
		case RE:
			break;
		case VA:
			return DeviceCategoryConst.CATEGORY_VISUAL_AID_VA_ID;
		default:
			break;		
		}
		return null;
	}
	
}
