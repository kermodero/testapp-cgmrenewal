package moh.adp.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.stream.IntStream;

import javax.xml.bind.JAXB;

import moh.adp.common.DeviceCategory;
import moh.adp.common.exception.AdpException;
import moh.adp.db.common.TestDBException;
import moh.adp.db.jpa.RenewalRecord;
import moh.adp.model.claim.ClientAgent;
import moh.adp.model.client.Client;
import moh.adp.model.party.vendor.Vendor;
import moh.adp.service.client.ClientService;
import moh.adp.service.client.ClientServiceImpl;
import moh.adp.service.invoice.InvoiceService;
import moh.adp.service.invoice.InvoiceServiceImpl;
import moh.adp.service.vendor.VendorService;
import moh.adp.service.vendor.VendorServiceImpl;
import moh.adp.xml.model.renewal.gm.v202311.Form1;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form;

public abstract class RandomRenewalTranslator<U> extends RenewalTranslator<U> {
	public abstract DeviceCategory getDeviceCategory(); 
	
	public void translate(RenewalRecord r, Map<String, String> results) {
		Form1 f = new Form1();
		IntStream.range(0, r.getRandomRecords()).forEach( i -> {
			populate(f, r);
			String xml = marshal(f, r, i);
			results.put(getFileName(f, i, r.getFileName()), xml);
		});
	}

	protected abstract void populateSection2(Form f, RenewalRecord r);

	protected void populateSection1(Form f, RenewalRecord r) {
		Client c = getClient(r);
		populateSection1(f, r, c);
	}
	
	protected void populateSection3(Form f, RenewalRecord r) {
		ClientAgent ca = getClientAgent(r);
		populateSection3(f, r, ca);	
	}

	protected void populateSection4(Form f, RenewalRecord r) {
		Vendor vendor = getVendor(r);
		populateSection4(f, r, vendor);
	}
	
	protected Client getClient(RenewalRecord r) {
		try {
			String randomHealthNum = RandomDataService.instance().getRandomHealthNumWithClaim("310");
			ClientService service = new ClientServiceImpl();
			return service.getClientView(randomHealthNum).getAdpClient();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestDBException("error getting client", e);
		}
	}

	protected ClientAgent getClientAgent(RenewalRecord r) {
	    try {
			Long id = RandomDataService.instance().getRandomClientAgent();
			InvoiceService service = new InvoiceServiceImpl();
			return service.getClientAgent(id);
		} catch (AdpException e) {
			e.printStackTrace();
			throw new TestDBException("error getting client agent", e);
		}			
	}
	
	protected Vendor getVendor(RenewalRecord r) {
		try {
			Long id = RandomDataService.instance().getRandomVendor();		
			VendorService vs = new VendorServiceImpl();		
			return vs.getVendor(id);
		} catch (AdpException e) {
			e.printStackTrace();
			throw new TestDBException("error getting vendor " + e.getMessage(), e);
		}
	}
	
	private String marshal(Form1 f, RenewalRecord r, int i) {
		File file = getFile(r);
		JAXB.marshal(f, file);
		try {
			String content = new String(Files.readAllBytes(file.toPath()));
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			throw new TestDBException("failed to marshal file " + r.getFileName(), e);
		}
	}    
   	
	private String getFileName(Form1 f, int i, String fileName) {
		return "File" + i + "-" + getFileName(f, fileName);
	}

}
