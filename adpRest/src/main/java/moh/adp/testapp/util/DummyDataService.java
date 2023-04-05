package moh.adp.testapp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.inject.Singleton;

/*import moh.adp.db.model.ClaimRecordFull;
import moh.adp.db.model.Section;
import moh.adp.db.model.Section3;
import moh.adp.db.model.Test;*/

@Singleton
public class DummyDataService {

	public Object someTests() {
		// TODO Auto-generated method stub
		return null;
	}
/*	private Map<Integer, Test> data;
	
	@PostConstruct
	public void init(){
		data = new HashMap<>();
		data.put(1, new Test(1, "One", "the first"));
		data.put(2, new Test(2, "Two", "the second"));
		data.put(3, new Test(3, "Three", "the third"));
		data.put(4, new Test(4, "Four", "the fourth"));
		data.put(5, new Test(5, "Five", "the fifth"));
	}
	
	public List<Test> someTests(){
		return new ArrayList<>(data.values());		
	}
	
	public Test aTest(int id){
		return data.get(id);		
	}
	
	public void update(int id){

	}
	
	public void create(int id){

	}
	
	public ClaimRecordFull getDummy(Test test) {
		ClaimRecordFull cr = new ClaimRecordFull();
		cr.setDeviceCategory("GM");
		cr.setVersionNumber("202203");
		cr.getSection1().setHealthNumber("1143371332");
		cr.getSection1().setConfirmationOfBenefitQ1YN("yes");
		cr.getSection1().setConfirmationOfBenefitQ1IfYes("yes");
		cr.getSection1().setConfirmationOfBenefitQ2YN("no");
		cr.getSection1().setConfirmationOfBenefitQ3YN("no");
		cr.getSection1().addField("streetName", "Main");

		getDummySection2(cr, test);
		
		cr.getSection3().setPerson(Section3.Person.agent);
		cr.getSection3().setClaimSignatureId(123456l);
		cr.getSection3().setClaimSignatureCode(515001l);
		cr.getSection3().setContactId(53236l);
		
		cr.getSection3().addField("applicantFirstname", "Rubble");

		cr.getSection4().setPhysicianId(1234l);
		cr.getSection4().setAuthorizerId(1234l);
		cr.getSection4().setAdpClinicNo("12345");
		cr.getSection4().setVendorId(1234l);
		
		return cr;
	}

	private void getDummySection2(ClaimRecordFull cr, Test test) {
		switch(cr.getDeviceCategory()) {
		case "GM" :
			getDummyGMSection2(cr, test);
			return;
		default:
			throw new RuntimeException("No dummy data for device category " + cr.getDeviceCategory());
		}	
	}

	private void getDummyGMSection2(ClaimRecordFull cr, Test test) {
		cr.getSection2().addField("q1", Section.Y);
		cr.getSection2().addField("q2a", Section.N);
		cr.getSection2().addField("q2bi", Section.NA);
		cr.getSection2().addField("q2bii", Section.N);
		cr.getSection2().addField("q2biii", Section.Y);
		cr.getSection2().addField("q3a", Section.Y);
		cr.getSection2().addField("q3b", Section.Y);
		cr.getSection2().addField("q3c", Section.N);
	}*/

	public Object aTest(int parseInt) {
		// TODO Auto-generated method stub
		return null;
	}

}
