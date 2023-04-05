package moh.adp.xml;

import java.text.SimpleDateFormat;

import moh.adp.common.DeviceCategory;
import moh.adp.db.common.TestDBException;
import moh.adp.db.jpa.RenewalRecord;
import moh.adp.model.client.Client;
import moh.adp.service.client.ClientService;
import moh.adp.service.client.ClientServiceImpl;
import moh.adp.xml.model.renewal.gm.v202311.Form1;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section1;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section3;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section3.Contact;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section4;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section4.Vendor;

public abstract class RenewalTranslator<U> {
	private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
	public abstract String translate(U u);
	public abstract DeviceCategory getDeviceCategory(); 
	
	protected void populateSection1(Form f, RenewalRecord r) {
		Client c = getClient(r);
		f.setSection1(new Form1.Form.Section1());
		Section1 section1 = f.getSection1();
		section1.setApplicantFirstname(c.getLastName());
		section1.setApplicantLastname(c.getFirstName());
		section1.setApplicantMiddleinitial(c.getMiddleName());
		section1.setVersionNo(c.getVersionCode());
		section1.setDateOfBirth(sdf.format(c.getDateOfBirth()));
		section1.setHealthNo(r.getHealthNumber());
		section1.setVersionNo(r.getFormVersion());
	}
		
	protected void populateSection3(Form f, RenewalRecord r) {
		f.setSection3(new Form1.Form.Section3());
		Section3 section3 = f.getSection3();
		Contact c = new Contact();
		section3.setContact(c);
		c.setAgentFirstname("Betty");
		c.setAgentLastname("Rubble");
	}

	protected void populateSection4(Form f, RenewalRecord r) {
		f.setSection4(new Form1.Form.Section4());
		Section4 section4 = f.getSection4();		
		Vendor v = new Vendor();
		section4.setVendor(v);
		v.setAdpVendorRegNo("1235");
		v.setVendorBusName("Earnies discount Glucose Monitors");
	}

	private Client getClient(RenewalRecord r) {
		try {
			 ClientService service = new ClientServiceImpl();
	         return service.getClientView(r.getHealthNumber()).getAdpClient();			
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestDBException("error getting client", e);
		}
	}
	
}
