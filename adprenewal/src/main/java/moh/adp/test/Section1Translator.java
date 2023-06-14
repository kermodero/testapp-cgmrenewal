package moh.adp.test;

import moh.adp.model.claim.Claim;
import moh.adp.server.esubmission.xml.parser.util.XMLParserConsts;
import moh.adp.xml.model.XmlForm;
import moh.adp.xml.model.common.ConfirmationOfBenefit;
import moh.adp.xml.model.common.Section1;

public class Section1Translator <T, U> extends SectionTranslator {
	
	public Section1Translator(){
		init();
	}
	
	private void init() {
		addField(Section1.class, "applicantLastname",   "claimClient", "lastName");
		addField(Section1.class, "applicantFirstname",  "claimClient", "firstName");
		addField(Section1.class, "applicantMiddleinitial", "claimClient", "middleName");
		addField(Section1.class, "healthNo",            "claimClient", "healthNumber");
		addField(Section1.class, "versionNo",           "claimClient", "versionCode");		
		addField(Section1.class, "dateOfBirth",         "claimClient", "dateOfBirth");
		addField(Section1.class, "nameLTCH", 		    "claimClient", "mailingAddress", "longTermCareHomeName");
		addField(Section1.class, "unitNo", 			    "claimClient", "mailingAddress", "unitNum");
		addField(Section1.class, "streetNo",  		    "claimClient", "mailingAddress", "streetNum");
		addField(Section1.class, "streetName",			"claimClient", "mailingAddress", "streetName");
		addField(Section1.class, "rrRoute",  			"claimClient", "mailingAddress", "lotRrAddress");
		addField(Section1.class, "province",			"claimClient", "mailingAddress", "provinceState");
		addField(Section1.class, "city", 				"claimClient", "mailingAddress", "cityTown");
		addField(Section1.class, "postalCode", 			"claimClient", "mailingAddress", "postalCode");		
		addField(Section1.class, "homePhone", 			"claimClient", "homePhone", 	 "phoneNum");
		addField(Section1.class, "busPhone", 			"claimClient", "businessPhone",  "phoneNum");
		addField(Section1.class, "phoneExtension", 		"claimClient", "businessPhone",  "extension");
		
		initConfirmationOfBenefit();
	}
	
	private void initConfirmationOfBenefit() {
		addBooleanField(ConfirmationOfBenefit.class, "q1Yn",     "benefitReceived");
		//		"q1Ifyes" is set below.
		addBooleanField(ConfirmationOfBenefit.class, "q2Yn",	 "wsibEligible");
		addBooleanField(ConfirmationOfBenefit.class, "q3Yn",	 "vacEligible");
		//		"q4" is set below.
		//		"q4Ifyes" is set below.
		//addBooleanField(ConfirmationOfBenefit.class, "q5Yn",	 "hospitalResident");
		//addBooleanField(ConfirmationOfBenefit.class, "q6Yn",     "formCcac"); 		
	}

	protected void translateSection(Claim claim, XmlForm form){
		Section1 s1 = form.getSection1();
		s1.setConfirmationOfBenefit(new ConfirmationOfBenefit());
		populateAll(Section1.class, claim, s1);
		translateConfirmationOfBenefit(claim, s1.getConfirmationOfBenefit());
	}

	private void translateConfirmationOfBenefit(Claim claim, ConfirmationOfBenefit confirmationOfBenefit) {
		//q1IfYes
		String q1IfYes = claim.isOwp() ? "OWP" : (claim.isOdsp()? "ODSP" : (claim.isAcsd() ? "ACSD" : ""));
		confirmationOfBenefit.setQ1Ifyes(q1IfYes);
		
/*		//q4IfYes
		if (claim.isDeviceCategoryMaxIntraoral()) {
			if (claim.isOmrpEligible()) {
				confirmationOfBenefit.setQ4Yn(XMLParserConsts.YES);
				confirmationOfBenefit.setQ4Ifyes(claim.getCaseNumber());				
			} else
				confirmationOfBenefit.setQ4Yn(XMLParserConsts.NO);
		} else {
			confirmationOfBenefit.setQ4Yn (claim.isLtcResident() ? XMLParserConsts.YES : XMLParserConsts.NO);
		}

		//q4
		String q4 = claim.isOwp() ? "OWP" : (claim.isOdsp()? "ODSP" : (claim.isAcsd() ? "ACSD" : ""));
		confirmationOfBenefit.setQ4Ifyes(q4);*/
		
		populateAll(ConfirmationOfBenefit.class, claim, confirmationOfBenefit);				
	}

}
