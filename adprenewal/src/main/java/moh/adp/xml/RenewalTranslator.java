package moh.adp.xml;

import java.text.SimpleDateFormat;

import moh.adp.common.CodeValueConsts;
import moh.adp.common.DeviceCategory;
import moh.adp.common.exception.AdpException;
import moh.adp.db.common.TestDBException;
import moh.adp.db.jpa.RenewalRecord;
import moh.adp.model.claim.ClientAgent;
import moh.adp.model.client.Client;
import moh.adp.model.party.vendor.Vendor;
import moh.adp.model.shared.Address;
import moh.adp.server.claim.ClientAgentHelper;
import moh.adp.server.party.vendor.VendorHelper;
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
	protected SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY");
	public abstract String translate(U u);
	public abstract DeviceCategory getDeviceCategory(); 
    public static final String SPOUSE = "spouse";
    public static final String PARENT = "parent";
    public static final String GUARDIAN = "guardian";
    public static final String TRUSTEE = "trustee";
    public static final String ATTORNEY = "attorney";
	
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
	    	contact.setRelationship(SPOUSE);
	    else if (ca.isRelationParent() )
	    	contact.setRelationship(PARENT);
	    else if (ca.isRelationGuardian() )
	    	contact.setRelationship(GUARDIAN);
	    else if (ca.isRelationTrustee() )
	    	contact.setRelationship(TRUSTEE);
	    else if (ca.isRelationPowerAttorney())
	    	contact.setRelationship(ATTORNEY);		
	}
	
}
