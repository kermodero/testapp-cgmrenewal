package moh.adp.test;



import moh.adp.model.claim.Claim;
import moh.adp.model.claim.ClaimSignature;
import moh.adp.xml.model.XmlForm;
import moh.adp.xml.model.common.Section3;
import moh.adp.xml.model.common.Section3.Sig;


public class Section3Translator <T, U> extends SectionTranslator {
	
	public Section3Translator(){
		init();
	}

	protected void translateSection(Claim claim, XmlForm form){
		Section3 s3 = form.getSection3();
		
		Section3.Contact contact = new Section3.Contact();
		s3.setContact(contact);
		this.setRelationship(claim, contact);
		populateAll(Section3.Contact.class, claim, contact);

		Section3.Sig sig = new Section3.Sig();
		s3.setSig(sig);
		setPayee(claim, sig);
		setPerson(claim, sig);
		populateAll(Section3.Sig.class, claim, sig);		

	}
	
	private void init() {
		initSig();
		initContact();
	}

	private void initSig() {
		//addField(Section3.Sig.class, "signature" , "consentSignature", "??"); //?? 
//		addField(Section3.Sig.class, "person"	 , "consentSignature", "fullName");
		addField(Section3.Sig.class, "date"      , "consentSignature", "signedDate");
	}

	private void initContact() {
		addField(Section3.Contact.class, "applicantLastname"	   , "clientAgent", "lastName");
		addField(Section3.Contact.class, "applicantFirstname"	   , "clientAgent", "firstName");
		addField(Section3.Contact.class, "applicantMiddleinitial"  , "clientAgent", "middleName");
		addField(Section3.Contact.class, "unitNo"		           , "clientAgent", "mailingAddress", "unitNum");
		addField(Section3.Contact.class, "streetNo"		           , "clientAgent", "mailingAddress", "streetNum");
		addField(Section3.Contact.class, "streetName"		       , "clientAgent", "mailingAddress", "streetName");		
		addField(Section3.Contact.class, "rrRoute"		           , "clientAgent", "mailingAddress", "lotRrAddress");
		addField(Section3.Contact.class, "city"		               , "clientAgent", "mailingAddress", "cityTown");
		addField(Section3.Contact.class, "postalCode"		       , "clientAgent", "mailingAddress", "postalCode");
		addField(Section3.Contact.class, "homePhone"		       , "clientAgent", "homePhone",      "phoneNum");
		addField(Section3.Contact.class, "busPhone"		           , "clientAgent", "businessPhone",  "phoneNum");
		addField(Section3.Contact.class, "phoneExtension"          , "clientAgent", "businessPhone",  "extension");
	}

	
	private void setRelationship(Claim claim, Section3.Contact contact) {
	    String SPOUSE = "spouse";
	    String PARENT = "parent";
	    String GUARDIAN = "guardian";
	    String TRUSTEE = "trustee";
	    String ATTORNEY = "attorney";

	    if (claim.getClientAgent().isRelationSpouse() )
	    	contact.setRelationship(SPOUSE);
	    else if (claim.getClientAgent().isRelationParent() )
	    	contact.setRelationship(PARENT);
	    else if (claim.getClientAgent().isRelationGuardian() )
	    	contact.setRelationship(GUARDIAN);
	    else if (claim.getClientAgent().isRelationTrustee() )
	    	contact.setRelationship(TRUSTEE);
	    else if (claim.getClientAgent().isRelationPowerAttorney())
	    	contact.setRelationship(ATTORNEY);		
	}
		
	//c.f. ConsentSignatureParser
	private void setPayee(Claim claim, Section3.Sig sig) {
		if (!claim.isDeviceCategoryInsulinPump()) 
			return;
		sig.setPayee(claim.isPayeeApplicant() ? "Applicant" : (claim.isPayeeAgent() ? "Agent" : claim.isPayeeLtcHome()? "LTCH" : null));		
	}

	private void setPerson(Claim claim, Section3.Sig sig) {
		ClaimSignature cs = claim.getConsentSignature();
		sig.setPerson(cs.isClientSigned()? "Applicant" : "Agent");				
	}


}
