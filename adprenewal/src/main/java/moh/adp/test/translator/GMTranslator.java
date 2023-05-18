package moh.adp.test.translator;

import moh.adp.model.claim.form.ClaimGlucoseMonitor;
import moh.adp.test.DCTranslator;
import moh.adp.xml.model.common.Section1;
import moh.adp.xml.model.common.Section3;
import moh.adp.xml.model.common.Section4;
import moh.adp.xml.model.gm.v202301.GMForm1;

public class GMTranslator extends DCTranslator<ClaimGlucoseMonitor, GMForm1> {

	@Override
	public void translate(ClaimGlucoseMonitor claim, GMForm1 form) {
		initialiseForm(form);
		form.getForm().setDeviceCategory("GM");
		form.getForm().setVersionNumber("202311");
		translateCommonSections(claim, form);
		translateSection2(claim, form);
	}

	private void initialiseForm(GMForm1 form) {
		GMForm1.Form f = new GMForm1.Form();
		f.setSection1(new Section1());
		f.setSection2(new GMForm1.Form.Section2());		
		f.setSection3(new Section3());
		f.setSection4(new Section4());
		form.setForm(f);
	}

	protected void translateSection2(ClaimGlucoseMonitor claim, GMForm1 form) {
/*		Class<CAForm1.Form.Section2> clazz = CAForm1.Form.Section2.class;
		addField(clazz, "leaseBuyouts", "leaseBuyoutInd");
		addField(clazz, "modelDescription", "modelDesc");
		addField(clazz, "reason", "claimReasonCd"); 
		addBooleanField(clazz, "replacementChange", "replacement");
		addBooleanField(clazz, "replacementPhysical", "replacementReasonPhysicalGrowth");
		addBooleanField(clazz, "replacementNormal", "replacementReasonNormalWear");
		populateAll(clazz, claim, form.getForm().getSection2());
		*/

		
	}

}
