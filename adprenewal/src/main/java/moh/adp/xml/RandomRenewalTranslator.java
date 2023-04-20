package moh.adp.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXB;

import moh.adp.common.DeviceCategory;
import moh.adp.common.exception.AdpException;
import moh.adp.db.common.TestDBException;
import moh.adp.db.jpa.RenewalRecord;
import moh.adp.model.claim.ClientAgent;
import moh.adp.model.client.Client;
import moh.adp.model.party.vendor.Vendor;
import moh.adp.model.shared.Address;
import moh.adp.service.client.ClientService;
import moh.adp.service.client.ClientServiceImpl;
import moh.adp.service.invoice.InvoiceService;
import moh.adp.service.invoice.InvoiceServiceImpl;
import moh.adp.service.vendor.VendorService;
import moh.adp.service.vendor.VendorServiceImpl;
import moh.adp.xml.model.renewal.gm.v202311.Form1;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section1;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section2;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section3;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section3.Contact;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section4;

public abstract class RandomRenewalTranslator<U> {
	private static final String FORM_TYPE="RENEWAL";
	protected final String OUTPUT_DIR = "c:/test/renewals/";		
	protected SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
	public abstract DeviceCategory getDeviceCategory(); 
	
	public String translate(RenewalRecord r) {
		Form1 f = new Form1();
		populate(f, r);
		return marshall(f, r);
	}

	protected void populate(Form1 f1, RenewalRecord r) {
		
		//SEE AdpRuleDataAccess.java
		
		
		Form f = getForm(f1);
		f.setDeviceCategory(getDeviceCategory().getCategoryShortName());
		f.setFormType(FORM_TYPE);
		f.setVersionNumber("202311");
		populateSection1(f, r);
		populateSection2(f, r);
		populateSection3(f, r);
		populateSection4(f, r);
	}

	protected abstract void populateSection2(Form f, RenewalRecord r);

	private String marshall(Form1 f, RenewalRecord r) {
		File file = getFile(r);
		JAXB.marshal(f, file);
		try {
			String content = new String(Files.readAllBytes(file.toPath()));
			saveFile(r.getFileName(), content);
			return content;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "yaddayadda";
	}    
    
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
		ClientAgent ca = getClientAgent(r);
		Contact c = new Contact();
		section3.setContact(c);
		if (ca == null)
			return;
		setClientRelationship(c, ca);
		c.setAgentFirstname(ca.getFirstName());
		c.setAgentLastname(ca.getLastName());
		c.setAgentMiddleinitial(ca.getMiddleName());
		Address a = ca.getMailingAddress();
		if (a==null)
			return;
		c.setAgentUnitNo(a.getUnitNum());
		c.setAgentStreetNo(a.getStreetNum());
		c.setAgentStreetName(a.getStreetName());
		c.setAgentRRoute(a.getLotRrAddress());
		c.setAgentCity(a.getCityTown());
		c.setAgentProvince(a.getProvinceShortName());
		c.setAgentPostalCode(a.getPostalCode());
		//TODO: verify phone display method (also exists 'formatted display' method)
		c.setAgentHomePhone(ca.getHomePhone().getPhoneDisplay());
		c.setAgentBusPhone(ca.getBusinessPhone().getPhoneDisplay());
		c.setAgentPhoneExtension(ca.getBusinessPhone().getExtension());		
	}

	protected void populateSection4(Form f, RenewalRecord r) {
		f.setSection4(new Form1.Form.Section4());
		Section4 section4 = f.getSection4();		
		moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section4.Vendor v = 
				new moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section4.Vendor();
		section4.setVendor(v);
		Vendor vendor = getVendor(r);
		v.setAdpVendorRegNo(vendor.getVendorNum());
		v.setVendorBusName(vendor.getLegalBusinessName());
		if (vendor.getContact() != null) {
			v.setVendorFirstname(vendor.getContact().getFirstName());
			v.setVendorLastname(vendor.getContact().getLastName());
			v.setVendorPositionTitle(vendor.getContact().getPositionTitle());
		}
		v.setVendorBusPhone(vendor.getBusinessPhone().getPhoneDisplay());
		v.setVendorPhoneExtension(vendor.getBusinessPhone().getExtension());
		Address a = vendor.getAddress();
		if (a==null)
			return;
		v.setVendorUnitNo(a.getUnitNum());
		v.setVendorStreetNo(a.getStreetNum());
		v.setVendorStreetName(a.getStreetName());
		v.setVendorCity(a.getCityTown());
		v.setVendorProvince(a.getProvinceShortName());
		v.setVendorPostalCode(a.getPostalCode());
//		v.setVendorSignature(??);
//		v.setVendorSignDate(??);
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

	private Vendor getVendor(RenewalRecord r) {
		if (r.getAdamVendorId() == null)
			return null;
		VendorService vs = new VendorServiceImpl();		
		try {
			return vs.getVendor(r.getAdamVendorId());
		} catch (AdpException e) {
			e.printStackTrace();
			throw new TestDBException("error getting vendor " + e.getMessage(), e);
		}
	}
	
	private ClientAgent getClientAgent(RenewalRecord r) {
		if (r.getClientAgentId() == null)
			return null;
		try {
			 InvoiceService service = new InvoiceServiceImpl();
	         return service.getClientAgent(r.getClientAgentId());			
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestDBException("error getting client agent " + e.getMessage(), e);
		}
	}

	private void setClientRelationship(Contact contact, ClientAgent ca) {
	    if (ca.isRelationSpouse() )
	    	contact.setRelationship(ContactRelationship.SPOUSE.getText());
	    else if (ca.isRelationParent() )
	    	contact.setRelationship(ContactRelationship.PARENT.getText());
	    else if (ca.isRelationGuardian() )
	    	contact.setRelationship(ContactRelationship.GUARDIAN.getText());
	    else if (ca.isRelationTrustee() )
	    	contact.setRelationship(ContactRelationship.TRUSTEE.getText());
	    else if (ca.isRelationPowerAttorney())
	    	contact.setRelationship(ContactRelationship.ATTORNEY.getText());		
	}

	private Form getForm(Form1 f1) {
		f1.setForm(new Form1.Form());
		return f1.getForm();
	}
	
	protected File getFile(RenewalRecord r) {
		return Paths.get(r.getFileName()).toFile();
	}
	
	protected void saveFile(String name, String doc) {
		System.out.println("DOC: " + doc);
		try {
			Files.write(Paths.get(OUTPUT_DIR + name), doc.getBytes(), new OpenOption[]{});
		} catch (IOException e) {
			throw new TestDBException("couldn't save file", e);
		}
	}	
	
}
