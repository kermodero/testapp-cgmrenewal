package moh.adp.test.random;


import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import moh.adp.common.CodeValueConsts;
import moh.adp.common.exception.AdpException;
import moh.adp.db.common.TestDBException;
import moh.adp.model.claim.Claim;
import moh.adp.model.claim.ClaimSignature;
import moh.adp.model.claim.ClientAgent;
import moh.adp.model.client.Client;
import moh.adp.model.party.authorizer.AuthorizerView;
import moh.adp.model.party.prescriber.PhysicianView;
import moh.adp.model.party.vendor.Vendor;
import moh.adp.service.authorizer.AuthorizerService;
import moh.adp.service.authorizer.AuthorizerServiceImpl;
import moh.adp.service.client.ClientService;
import moh.adp.service.client.ClientServiceImpl;
import moh.adp.service.invoice.InvoiceService;
import moh.adp.service.invoice.InvoiceServiceImpl;
import moh.adp.service.vendor.VendorService;
import moh.adp.service.vendor.VendorServiceImpl;
import moh.adp.xml.RandomDataService;

public abstract class RandomClaim<U extends Claim> {
	protected Random rand = new Random();
	
	public RandomClaim(){
		rand.setSeed(System.currentTimeMillis());
	}
	
	public abstract U generate();
	public abstract void populateDeviceSpecific(U u);
	
	protected void populate(Claim claim) {
		populateClient(claim);
		populateClientAgent(claim);
		populatePhysician(claim);
	}

	private void populatePhysician(Claim claim) {
		claim.setClaimPhysician(getPhysician(claim));
	}

	protected void populateClient(Claim claim) {
		claim.setClaimClient(getClient());
		claim.setBenefitReceived(coinToss());
		claim.setAcsd(coinToss());
		claim.setOdsp(coinToss());
		claim.setOwp(coinToss());
	}
	
	protected void populateClientAgent(Claim claim) {
		claim.setClientAgent(getClientAgent());
		claim.setConsentSignature(getClaimSignature(CodeValueConsts.SIGNATURE_TYPE_CONSENT_CD));
	}
	
	private ClaimSignature getClaimSignature(Long signatureType) {
		ClaimSignature cs = new ClaimSignature(signatureType);
		cs.setSignedDate(getDate(-5));
		boolean signedByAgent = coinToss();
		cs.setAgentSigned(signedByAgent);
		cs.setClientSigned(!signedByAgent);
		return cs;
	}

	private Date getDate(int offset) {
		Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DATE, + offset);
	    return cal.getTime();
	}

	private Date getRecentDate() {
		return getDate(- Math.abs(rand.nextInt(30)) - 1); //a day in the last month, roughly.
	}
	
	protected Client getClient() {
		try {
			String randomHealthNum = RandomDataService.instance().getRandomHealthNum();
			ClientService service = new ClientServiceImpl();
			return service.getClientView(randomHealthNum).getAdpClient();
		} catch (Exception e) {
			e.printStackTrace();
			throw new TestDBException("error getting client", e);
		}
	}

	protected ClientAgent getClientAgent() {
	    try {
			Long id = RandomDataService.instance().getRandomClientAgent();
			InvoiceService service = new InvoiceServiceImpl();
			return service.getClientAgent(id);
		} catch (AdpException e) {
			e.printStackTrace();
			throw new TestDBException("error getting client agent", e);
		}			
	}
	
	protected Vendor getVendor() {
		try {
			Long id = RandomDataService.instance().getRandomVendor();		
			VendorService vs = new VendorServiceImpl();		
			return vs.getVendor(id);
		} catch (AdpException e) {
			e.printStackTrace();
			throw new TestDBException("error getting vendor " + e.getMessage(), e);
		}
	}

	protected PhysicianView getPhysician(Claim claim) {
		try {
			String authorizerRegNum = RandomDataService.instance().getRandomPhysician();		
			AuthorizerService as = new AuthorizerServiceImpl();
			AuthorizerView av = as.getAuthorizerView(authorizerRegNum, null, null);
			claim.setSignedDate(getRecentDate());
			return av2pv(av);
		} catch (AdpException e) {
			e.printStackTrace();
			throw new TestDBException("error getting vendor " + e.getMessage(), e);
		}
	}

	private PhysicianView av2pv(AuthorizerView av) {
		PhysicianView pv = new PhysicianView();
		pv.setFirstName(av.getFirstName());
		pv.setLastName(av.getLastName());
		pv.setPhone(av.getPhone());		
		pv.setBillingNum(av.getRegNum());
		pv.setDisciplineCode("MD");
		return pv;
	}

	private boolean coinToss() {
		return rand.nextBoolean();
	}

}
