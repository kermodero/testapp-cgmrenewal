package moh.adp.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Random;

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
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section3;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section3.Contact;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section4;

public abstract class RenewalTranslator<U> {
	private static final String FORM_TYPE="RENEWAL";
	protected final String OUTPUT_DIR = "c:/test/renewals/";		
	protected SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
	public abstract DeviceCategory getDeviceCategory(); 
	public Random random = new Random();
	
	public void translate(RenewalRecord r, Map<String, String> results) {
		Form1 f = new Form1();
		populate(f, r);
		String xml = marshal(f, r);
		results.put(getFileName(f, r.getFileName()), xml);
	}
	
	protected void populate(Form1 f1, RenewalRecord r) {
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

	private String marshal(Form1 f, RenewalRecord r) {
		File file = getFile(r);
		JAXB.marshal(f, file);
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
			throw new TestDBException("Exception marshalling file", e);
		}
	}    
    
	protected void populateSection1(Form f, RenewalRecord r) {
		Client c = getClient(r);
		populateSection1(f, r, c);
	}
	
	protected void populateSection1(Form f, RenewalRecord r, Client c) {
		f.setSection1(new Form1.Form.Section1());
		Section1 section1 = f.getSection1();
		section1.setApplicantFirstname(blankIfNull(c.getLastName()));
		section1.setApplicantLastname(blankIfNull(c.getFirstName()));
		section1.setApplicantMiddleinitial(blankIfNull(c.getMiddleName()));
		section1.setVersionNo(blankIfNull(c.getVersionCode()));
		section1.setDateOfBirth(sdf.format(c.getDateOfBirth()));
		section1.setHealthNo(r.getHealthNumber());
		section1.setVersionNo(r.getFormVersion());
		//confirmation of benefit?
	}

	protected void populateSection3(Form f, RenewalRecord r) {
		ClientAgent ca = getClientAgent(r);
		populateSection3(f, r, ca);	
	}

	protected void populateSection3(Form f, RenewalRecord r, ClientAgent ca) {
		f.setSection3(new Form1.Form.Section3());
		Section3 section3 = f.getSection3();
		Contact c = new Contact();
		section3.setContact(c);
		if (ca == null)
			return;
		setClientRelationship(c, ca);
		c.setAgentFirstname(blankIfNull(ca.getFirstName()));
		c.setAgentLastname(blankIfNull(ca.getLastName()));
		c.setAgentMiddleinitial(blankIfNull(ca.getMiddleName()));
		Address a = ca.getMailingAddress();
		if (a==null)
			return;
		c.setAgentUnitNo(blankIfNull(a.getUnitNum()));
		c.setAgentStreetNo(blankIfNull(a.getStreetNum()));
		c.setAgentStreetName(blankIfNull(a.getStreetName()));
		c.setAgentRRoute(blankIfNull(a.getLotRrAddress()));
		c.setAgentCity(blankIfNull(a.getCityTown()));
		c.setAgentProvince(blankIfNull(a.getProvinceShortName()));
		c.setAgentPostalCode(blankIfNull(a.getPostalCode()));
		//TODO: verify phone display method (also exists 'formatted display' method)
		c.setAgentHomePhone(blankIfNull(ca.getHomePhone().getPhoneDisplay()));
		c.setAgentBusPhone(blankIfNull(ca.getBusinessPhone().getPhoneDisplay()));
		c.setAgentPhoneExtension(blankIfNull(ca.getBusinessPhone().getExtension()));				
	}
	
	private String blankIfNull(String value) {
		return (value == null)? "" : value;
	}

	protected void populateSection4(Form f, RenewalRecord r) {
		Vendor vendor = getVendor(r);
		populateSection4(f, r, vendor);
	}
	
	protected void populateSection4(Form f, RenewalRecord r, Vendor vendor) {
		f.setSection4(new Form1.Form.Section4());
		Section4 section4 = f.getSection4();		
		moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section4.Vendor v = 
				new moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section4.Vendor();
		section4.setVendor(v);

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
		v.setVendorUnitNo(blankIfNull(a.getUnitNum()));
		v.setVendorStreetNo(blankIfNull(a.getStreetNum()));
		v.setVendorStreetName(blankIfNull(a.getStreetName()));
		v.setVendorCity(blankIfNull(a.getCityTown()));
		v.setVendorProvince(blankIfNull(a.getProvinceShortName()));
		v.setVendorPostalCode(blankIfNull(a.getPostalCode()));
		v.setVendorSignature("");
//		v.setVendorSignDate();
	}

	protected Client getClient(RenewalRecord r) {
		try {
			 ClientService service = new ClientServiceImpl();
	         return service.getClientView(r.getHealthNumber()).getAdpClient();			
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestDBException("error getting client", e);
		}
	}

	protected Vendor getVendor(RenewalRecord r) {
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
	
	protected ClientAgent getClientAgent(RenewalRecord r) {
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
	
	protected String getFileName(Form1 f, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(fileName);
		sb.append("-");
		sb.append("VRX");
		sb.append("-");		
		sb.append(f.getForm().getSection4().getVendor().getAdpVendorRegNo());
		sb.append("-");
		sb.append(Math.abs(random.nextInt()));//fake the edt ref #
		sb.append("-");
		sb.append(System.currentTimeMillis() / 1000L); //unix time stamp
		sb.append(".xml");
		return sb.toString();
	}

}
