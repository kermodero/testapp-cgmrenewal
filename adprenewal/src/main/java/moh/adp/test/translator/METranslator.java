package moh.adp.test.translator;

import moh.adp.model.claim.form.ClaimMaxExtraoral;
import moh.adp.test.DCTranslator;
import moh.adp.xml.model.common.Section1;
import moh.adp.xml.model.common.Section3;
import moh.adp.xml.model.common.Section4;
import moh.adp.xml.model.me.v202301.MEForm1;


public class METranslator extends DCTranslator<ClaimMaxExtraoral, MEForm1> {

	@Override
	public void translate(ClaimMaxExtraoral claim, MEForm1 form) {
		initialiseForm(form);
		form.getForm().setDeviceCategory("ME");
		form.getForm().setVersionNumber("123");
		translateCommonSections(claim, form);
		translateSection2(claim, form);
	}

	private void initialiseForm(MEForm1 form) {
		MEForm1.Form f = new MEForm1.Form();
		f.setSection2(new MEForm1.Form.Section2());
		f.setSection1(new Section1());
		f.setSection3(new Section3());
		f.setSection4(new Section4());
		form.setForm(f);
	}

	protected void translateSection2(ClaimMaxExtraoral claim, MEForm1 form) {
		addField(MEForm1.Form.Section2.class, "auricularNewL",       "auricularNew", "leftDeviceType"); //??TODO 
		addField(MEForm1.Form.Section2.class, "auricularNewR",		 "auricularNew", "rightDeviceType");
		addField(MEForm1.Form.Section2.class, "auricularRepeatL",	 "auricularRepeat", "leftDeviceType"); //??TODO
		addField(MEForm1.Form.Section2.class, "auricularRepeatR",	 "auricularRepeat", "rightDeviceType"); //??TODO
		addField(MEForm1.Form.Section2.class, "auricularRepeatL",	 "auricularRepeat", "leftDeviceType"); //??TODO
		addField(MEForm1.Form.Section2.class, "auricularRepeatR",	 "auricularRepeat", "rightDeviceType"); //??TODO
		addField(MEForm1.Form.Section2.class, "orbitalNewL",		 "orbitalNew", "leftDeviceType");
		addField(MEForm1.Form.Section2.class, "orbitalNewR",		 "orbitalNew", "rightDeviceType");
		addField(MEForm1.Form.Section2.class, "orbitalRepeatL",		 "orbitalRepeat", "leftDeviceType");	
		addField(MEForm1.Form.Section2.class, "orbitalRepeatR",		 "orbitalRepeat", "rightDeviceType");
		addField(MEForm1.Form.Section2.class, "orbitomaxillaryNewL",	 "orbitomaxillaryNew", "leftDeviceType");	
		addField(MEForm1.Form.Section2.class, "orbitomaxillaryNewR",	 "orbitomaxillaryNew", "rightDeviceType");
		addField(MEForm1.Form.Section2.class, "orbitomaxillaryRepeatL",	 "orbitomaxillaryRepeat", "leftDeviceType");	
		addField(MEForm1.Form.Section2.class, "orbitomaxillaryRepeatR",	 "orbitomaxillaryRepeat", "rightDeviceType");
		
		addField(MEForm1.Form.Section2.class, "nasalNew",		     "nasalNew"); //?? TODO invoking toString() on ClaimDeviceType - need to be more sophisticated.
		addField(MEForm1.Form.Section2.class, "nasalRepeat",	     "nasalRepeat");
		addField(MEForm1.Form.Section2.class, "nasomaxillaryNew",	 "nasomaxillaryNew");
		addField(MEForm1.Form.Section2.class, "nasomaxillaryRepeat", "nasomaxillaryRepeat");
		//addField(MEForm1.Form.Section2.class, "reason",			 "", "");
		//addField(MEForm1.Form.Section2.class, "replacementChange",	 "", "");
		//addField(MEForm1.Form.Section2.class, "replacementPhysical",	 "", "");
		//addField(MEForm1.Form.Section2.class, "replacementNormal",	 "", "");
		//addField(MEForm1.Form.Section2.class, "confirmation",             "", "");
	}


}
