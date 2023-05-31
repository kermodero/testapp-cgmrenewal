package moh.adp.testapp.adam;

import javax.inject.Inject;

import moh.adp.testapp.util.DummyDataService;



public class TestClaimService {
	@Inject //TODO Remove this
	public DummyDataService dummyData;		
   // private ClientService clientService;
   // private ClaimService claimService;
    
    public TestClaimService() {
    	
    }
    
/*    public List<Claim> createAll(Test test) {
		List<Claim> claims = new ArrayList<>();
		test.getTestSets().forEach(ts ->
			ts.getRecordSets().forEach(rs ->
				rs.getClaimRecords().forEach(cr -> 
					claims.add(createClaimRecord(cr, rs, ts, test))
				)
			)
		);
		return claims;
	}
	
	@PostConstruct
	private void init() {
	//	clientService = new ClientServiceImpl();		
	//	claimService = new ClaimServiceImpl();
	}

	private Claim createClaimRecord(ClaimRecord cr, RecordSet rs, TestSet ts, Test test) throws ADAMTestException {
		try {
			DeviceCategory dc = ClaimFactory.getDeviceCategory(cr.getDeviceCategory());
			Claim c = ClaimFactory.createClaim(dc);
			setClaimClient(c, cr);
			
			setDeviceSpecific(c, dc);
			return c;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ADAMTestException(e.getMessage(), e);
		}
	}

	private void setDeviceSpecific(Claim c, DeviceCategory dc) {
		// TODO Auto-generated method stub
		
	}

	private void setClaimClient(Claim c, ClaimRecord cr) throws AdpException {
		//Client client =  clientService.getClient(cr.getAdamClientId());
		//c.setClaimClient(client);
	}

	public void createOne(Test test) {
		// TODO Auto-generated method stub
		
	}

	public Claim createOne(ClaimRecord cr) {
		DeviceCategory dc = ClaimFactory.getDeviceCategory(cr.getDeviceCategory());
		Claim c = ClaimFactory.createClaim(dc);
		return c;
	}*/

	/*
	public void createOne(Test test) {
		ClaimRecordFull cr = dummyData.getDummy(test);
		try {
			DeviceCategory dc = ClaimFactory.getDeviceCategory(cr.getDeviceCategory());
			Claim c = ClaimFactory.createClaim(dc);
			populate(c, cr);
			RpdbClient rpdbClient = getRpdbClient(cr);
			claimService.recordClaim(c, rpdbClient);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ADAMTestException(e.getMessage(), e);
		}		
	}

	private RpdbClient getRpdbClient(ClaimRecordFull cr) throws AdpException {
		return RpdbClientLookupService.getRpdbClient(cr.getSection1().getHealthNumber(), asOfDate(cr));
	}

	private Date asOfDate(ClaimRecordFull cr) {
		Date signedDate = cr.getSection3().getSignatureDate();
		return signedDate == null? new Date() : signedDate;
	}

	private void populate(Claim claim, ClaimRecordFull cr) throws AdpException {
		ClientView cv = clientService.getClientView(cr.getSection1().getHealthNumber());
		ClientAgent ca = ClientAgentHelper.getClientAgent(cr.getSection3().getContactId());
		ClaimSignature cs = ClaimSignatureHelper.getClaimSignature(cr.getSection3().getClaimSignatureId(), cr.getSection3().getClaimSignatureCode());
		ClinicView clv = ClinicHelper.getClinicView(cr.getSection4().getAdpClinicNo(), cr.getSection4().getVendorNum(), cr.getSection4().getBillingNum());
		PhysicianView pv = PhysicianFinder.getRegisteredProvider(cr.getSection4().getBillingNum());
		VendorView vv = VendorHelper.getVendorView(cr.getSection4().getVendorNum());
		Authorizer auth = AuthorizerHelper.getAuthorizer(cr.getSection4().getAuthorizerId());	
		cr.getSection1().populate(claim, cv.getAdpClient());
		cr.getSection2().populate(claim);
		cr.getSection3().populate(claim, ca, cs);
		populate(cr, claim, pv, clv, vv, auth);
	}

	private void populate(ClaimRecordFull cr, Claim claim, PhysicianView pv, ClinicView clv, VendorView vv, Authorizer auth) {
		if (pv != null)
			populatePhysician(cr, claim, pv);

		
	}

	//See SignaturesParser
	private void populatePhysician(ClaimRecordFull cr, Claim claim, PhysicianView pv) {
		SigningAuthority view = claim.getClaimPhysician();
		Long stakeHolderTypeCd = CodeValueConsts.STAKEHOLDER_TYPE_PHYSICIAN_CD;
		((PhysicianView) view).setBillingNum(cr.getSection4().getBillingNum());
		// Device specific physician rules
		String profession = cr.getSection4().getPhysicianProfession();
		if (!EqualUtil.isEmpty(cr.getSection4().getPhysicianProfession())) {
			if (EqualUtil.equals(profession.trim(), "physician")) {
				((PhysicianView) view).setDisciplineCode("MD");
				if (claim.isDeviceCategoryMaxIntraoral()) {
					((ClaimMaxIntraoral) claim).setCompletedByPhysician(true);
				}
			} else if (EqualUtil.equals(profession.trim(), "nurse")) {
				((PhysicianView) view).setDisciplineCode("NP");
			} else if (EqualUtil.equals(profession.trim(), "dentist")) {
				// JIRA 391. !!!Do not setDisciplineCode!!!. It should leave to blank as it should be empty.
				// If <profession>dentist</profession> in xml, prescriber is a dentist. 
				// ClaimMaxIntraoral.getPrescriber() should return claimDentist object.
					((PhysicianView) view).setDisciplineCode("DS"); 
				// Instead, should reset the following properties of the claimPhysician object as the isEpty() need to check these.
				
				if (claim.isDeviceCategoryMaxIntraoral()) {
					((ClaimMaxIntraoral) claim).setCompletedByDentist(true);
				}
			} else if (EqualUtil.equals(profession.trim(), "optometrist")) {
				((PhysicianView) view).setDisciplineCode("TM");
			}
		}
		

		ClaimSignature signature = claim.getPhysicianSignature();
//		setRegNo(cr.getSection4().getBillingNum(), pv, signature, claim);

//		setContactInfo(signature, view, physician, stakeHolderTypeCd);

//		setSignedDate(claim, aForm, physician, signature);
		
	}

	private static void setRegNo(String regNo, Party party, ClaimSignature signature, Claim claim) {
		
		// Only authorizer (exclude RHP), fitter, rehadassessor requires
		// location split
		// The rest of the signature is set as is
		// Fitter signature is set above. However fitter form also has
		// authorizer signature which will be set here
		// Signature Common
		// Registration Number
		// JIRA 282 Fix.
		if (!EqualUtil.isEmpty(regNo)) {

			// JIRA 292.TODO:test
			ParsedRegNo prn = new ParsedRegNo(regNo);

			// 10 - 2 authorizer location handling
			if ((party.isAuthorizer() && !claim.isDeviceCategoryOxygen()) || party.isRehabilitationAssessor()) {
				signature.setRegistrationNum(prn.getRegistrationNumber());
				signature.setRelativeLocStr(prn.getLocationId()); // no harm in setting to null, if null
			} else {
				signature.setRegistrationNum(prn.getUnparsedRegistrationNumber());
			}

			// JIRA 283 should set registrationNum of AudiologistSignature
			if (party.isAudiologist()) {
				((ClaimHearing) claim).getAudiologistSignature().setRegistrationNum(regNo.trim());
			}
		}

	}
*/

	
}
