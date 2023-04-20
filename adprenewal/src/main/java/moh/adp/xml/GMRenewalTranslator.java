package moh.adp.xml;

import moh.adp.common.DeviceCategory;
import moh.adp.db.jpa.RenewalRecord;
import moh.adp.xml.model.renewal.gm.v202311.Form1;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section2;

public class GMRenewalTranslator extends RenewalTranslator<RenewalRecord> {

	protected void populateSection2(Form f, RenewalRecord r) {
		f.setSection2(new Form1.Form.Section2());
		Section2 section2 = f.getSection2();
		section2.setCertification(r.getCertification());
		section2.setQuestion1(r.getQuestion1());
		section2.setQuestion2(r.getQuestion2());
		section2.setQuestion3(r.getQuestion3());
		section2.setSignature(r.getSignature());
		section2.setSignDate(sdf.format(r.getSignDate()));
		section2.setSignedBy(r.getSignedBy());		
		
		splatFields(r);
		
	}

	//TODO - REMOVE THIS!
	private void splatFields(RenewalRecord r) {
		if (r.getRenewalRecFields() == null) {
			System.out.println("Renewal Rec Fields was null");
			return;
		}
		System.out.println("Renewal Rec fields: ");
		r.getRenewalRecFields().forEach((f -> System.out.println( f.getPropertyName() + " - " + f.getPropertyValue())));
		
	}

	@Override
	public DeviceCategory getDeviceCategory() {
		return DeviceCategory.GM;
	}

}
