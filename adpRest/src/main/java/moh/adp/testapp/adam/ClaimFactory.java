package moh.adp.testapp.adam;

import moh.adp.common.DeviceCategory;
import moh.adp.model.claim.Claim;
import moh.adp.model.claim.form.ClaimCommunicationAid;
import moh.adp.model.claim.form.ClaimGlucoseMonitor;
import moh.adp.model.claim.form.ClaimHearing;
import moh.adp.model.claim.form.ClaimInsulinPump;
import moh.adp.model.claim.form.ClaimMaxExtraoral;
import moh.adp.model.claim.form.ClaimMaxIntraoral;
import moh.adp.model.claim.form.ClaimOcular;
import moh.adp.model.claim.form.ClaimOrthotic;
import moh.adp.model.claim.form.ClaimOxygen;
import moh.adp.model.claim.form.ClaimVentilator;
import moh.adp.model.claim.limb.ClaimLimb;
import moh.adp.model.claim.mobility.ClaimMobility;
import moh.adp.model.claim.pressure.ClaimPressureModification;
import moh.adp.model.claim.respiratory.ClaimRespiratory;
import moh.adp.testapp.util.ADAMTestException;

public class ClaimFactory {
	
	public static Claim createClaim(DeviceCategory deviceCategory) {
		
		switch (deviceCategory) {
		case CA:
			return new ClaimCommunicationAid();
		case DSA:
			return new ClaimInsulinPump(); //TODO
		case DSC:
			return new ClaimInsulinPump(); //TODO
		case GM:
			return new ClaimGlucoseMonitor();
		case HD:
			return new ClaimHearing();
		case LP:
			return new ClaimLimb();
		case MD:
			return new ClaimMobility();
		case ME:
			return new ClaimMaxExtraoral();
		case MI:
			return new ClaimMaxIntraoral();
		case OP:
			return new ClaimOcular(); //TODO - confirm
		case OR:
			return new ClaimOrthotic(); //TODO - confirm
		case OXF:
			return new ClaimOxygen();
		case OXR:
			return new ClaimOxygen();
		case PM:
			return new ClaimPressureModification();
		case RE:
			return new ClaimRespiratory();
		case VA:
			return new ClaimVentilator();
		default:
			return null;
		}
		
	}

	public static DeviceCategory getDeviceCategory(String deviceCategory) {		
		try {
			return DeviceCategory.valueOf(deviceCategory);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ADAMTestException("Error getting device category " + deviceCategory, e);
		}
	}
	

}
