package moh.adp.test;

import moh.adp.common.CodeValueConsts;
import moh.adp.model.claim.Claim;
import moh.adp.model.claim.form.ClaimMaxIntraoral;
import moh.adp.model.party.prescriber.PhysicianView;
import moh.adp.model.party.vendor.VendorView;
import moh.adp.xml.model.XmlForm;
import moh.adp.xml.model.common.Authorizer;
import moh.adp.xml.model.common.ClinicInfo;
import moh.adp.xml.model.common.DiabetesProgram;
import moh.adp.xml.model.common.NoteToADP;
import moh.adp.xml.model.common.Physician;
import moh.adp.xml.model.common.Section4;
import moh.adp.xml.model.common.Therapist;
import moh.adp.xml.model.common.Vendor;

public class Section4Translator<T, U> extends SectionTranslator {

	public Section4Translator() {
		init();
	}

	private void init() {
		initPhysician();
		initAuthorizer();
		initClinicInfo();
		initVendor();
		initTherapist();
		initRehabilitationAssessor();
		initPrescriber();
		initDiabetesProgram();
		initNoteToADP();
		initAudiologist();
	}

	protected void translateSection(Claim claim, XmlForm form) {
		Section4 s4 = form.getSection4();
		s4.setPhysician(translatePart(Physician.class, claim.getClaimPhysician()));
		populatePhysicianOther(claim, s4.getPhysician());
		s4.setAuthorizer(translatePart(Authorizer.class, claim));
		populateAuthorizerOther(claim, s4.getAuthorizer());
		s4.setClinicInfo(translatePart(ClinicInfo.class, claim.getClaimClinic()));
		s4.setVendor(translatePart(Vendor.class, claim.getClaimVendor()));
		s4.setVendor2(translatePart(Vendor.class, claim.getClaimSecondVendor()));
		s4.setTherapist(translatePart(Therapist.class, claim.getClaimRespiratoryTherapist()));
//??	s4.setRehabilitationAssessor(translatePart(RehabilitationAssessor.class, claim.get, new RehabilitationAssessor()));
		//s4.setPrescriber(translatePart(Prescriber.class, new Prescriber()));
		s4.setDiabetesProgram(translatePart(DiabetesProgram.class, claim.getClaimClinic()));
//		s4.setNoteToADP(translatePart(NoteToADP.class, claim, new NoteToADP()));
//		s4.setAudiologist(translatePart(Audiologist.class, claim, new Audiologist()));
		populateVendor(claim.getClaimVendor(), s4.getVendor());
		populateVendor(claim.getClaimSecondVendor(), s4.getVendor2());
	}
		
	private void initPhysician(){
		//"profession" -see populatePhysicianOther
		addField(Physician.class, "physicianLastname", "lastName");
		addField(Physician.class, "physicianFirstname", "firstName");
		addField(Physician.class, "busPhone", "phone", "phoneNum");
		addField(Physician.class, "phoneExtension", "phone", "extension");
		addField(Physician.class, "healthBilling", "billingNum");
		//"signature" - unused?
		addField(Physician.class, "date", "endDate");		
	}

	private void initAuthorizer(){
		addField(Authorizer.class, "authorizerLastname", "claimAuthorizer", "lastName");
		addField(Authorizer.class, "authorizerFirstname", "claimAuthorizer", "firstName");
		addField(Authorizer.class, "busPhone", "claimAuthorizer", "phone", "phoneNum");
		addField(Authorizer.class, "phoneExtension", "claimAuthorizer", "phone", "extension");
		addField(Authorizer.class, "adpNo", "claimAuthorizer", "regNum"); //? TODO - see ParsedRegNo; have to reverse.
		//"signature" - unused?
		addField(Authorizer.class, "date","authorizerSignature", "signedDate");
		addField(Authorizer.class, "profession",	   ""); //?? TODO
		addField(Authorizer.class, "fitterLastname", "fitterSignature", "lastName"); 
		addField(Authorizer.class, "fitterFirstname", "fitterSignature", "firstName"); 
		addField(Authorizer.class, "fitterbusPhone", "fitterSignature", "phoneNum"); 		
		addField(Authorizer.class, "fitterphoneExtension", "fitterSignature", "extension");
		addField(Authorizer.class, "fitteradpNo", "fitterSignature", "registrationNum");
		//addField(Authorizer.class, "fitterSignature", "fitterSignature", "");		
		addField(Authorizer.class, "fitterDate", "fitterSignature", "signedDate");
	}

	private void initClinicInfo(){
	}

	private void initVendor(){
		addField(Vendor.class, "vendorBusName",  "businessName");
		addField(Vendor.class, "adpVendorRegNo", "regNum");
		addField(Vendor.class, "vendorLastname", "lastName");
		addField(Vendor.class, "vendorFirstname","firstName");
		//addField(Vendor.class, "positionTitle",	 "");
		addField(Vendor.class, "busPhone", "phone", "phoneNum");
		addField(Vendor.class, "phoneExtension", "phone", "extension");		
		addField(Vendor.class, "vendorLocation", "address", "municipalityKey");
		//addField(Vendor.class, "signature",	 "");
		addField(Vendor.class, "date",		 "lastAllowableInvoiceDate");
		//addField(Vendor.class, "invoiceNo",	 "");
	}

	private void initTherapist(){
	}

	private void initRehabilitationAssessor(){
	}

	private void initPrescriber(){
	}

	private void initDiabetesProgram(){
		addField(DiabetesProgram.class, "adpClinicNo",	"regNum");	//See moh.adp.xml.model.common.DiabetesProgram: case of field name may cause problems.
	}

	private void initNoteToADP(){
		//addField(NoteToAdp.class, "vendorReplacement", "");
		//addField(NoteToAdp.class, "vendorCustom", "");
		//addField(NoteToAdp.class, "fundingChart", "");
		addField(NoteToADP.class, "letter", "letterRemark"); //?? TODO
	}

	private void initAudiologist(){
	}

	private void populatePhysicianOther(Claim claim, Physician physician) {
		PhysicianView pv = claim.getClaimPhysician();
		if (pv == null)
			return;
		if (pv.isMD())
			physician.setProfession("physician");
		else if (pv.isNP())
			physician.setProfession("nurse");
		else if (pv.isDS())
			physician.setProfession("dentist");		
		else if (pv.isOP())
			physician.setProfession("optometrist"); //is this right?
	}

	private void populateVendor(VendorView vendorView, Vendor vendor) {
		if (vendorView == null)
			return;
		vendor.setNonRegisterVendor(CodeValueConsts.VENDOR_STATUS_REGISTERED_CD.equals(vendorView.getStatusCd()) ? "Y" : "N");
	}

	private void populateAuthorizerOther(Claim claim, Authorizer authorizer) {
		if (claim.isDeviceCategoryMaxIntraoral()) { 
			if (((ClaimMaxIntraoral) claim).isAuthorizedByDentist())
				authorizer.setProfession("generalDentist");
			else if (((ClaimMaxIntraoral) claim).isAuthorizedByProsthodontist())
				authorizer.setProfession("prosthodontist");
		} else if (claim.isDeviceCategoryOxygen()) {
			//see SignaturesParser (Call hierarchy of getProfession() ...)
			
		}
		
	}

}
