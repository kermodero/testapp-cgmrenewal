package moh.adp.test.translator;

import java.util.Random;

import moh.adp.model.claim.form.ClaimOxygen;
import moh.adp.xml.model.oxFirstTime.v202301.OxFirstTimeForm1;
import moh.adp.xml.model.oxFirstTime.v202301.OxFirstTimeForm1.Form;
import moh.adp.xml.model.oxFirstTime.v202301.OxFirstTimeForm1.Form.Section2;


public class OXFRandomizingTranslator extends OXFTranslator {
	private Random rand = new Random();
	
	protected void translateSection2(ClaimOxygen claimGM, OxFirstTimeForm1 form) {
		rand.setSeed(System.currentTimeMillis());
		OxFirstTimeForm1.Form.Section2 s = form.getForm().getSection2();
		translateFunding(s);
		translateDelivery(s);
		translateReason(s);
		translateOxTherapy(s);
		translateIndependentAssessment(s);
		translateTestConfirmation(s);
	}

	private void translateTestConfirmation(Section2 s) {
		// TODO Auto-generated method stub
		
	}

	private void translateIndependentAssessment(Section2 s) {
		// TODO Auto-generated method stub
		
	}

	private void translateOxTherapy(Section2 s) {
		// TODO Auto-generated method stub
		
	}

	private void translateReason(Section2 s) {
		// TODO Auto-generated method stub
		
	}

	private void translateDelivery(Section2 s) {
		Section2.Delivery d = new Section2.Delivery();
		s.setDelivery(d);
		d.setSystem(rand.nextBoolean()?"system":"");
		switch (rand.nextInt(2)) {
		case 0: //small
			d.setSmallCylinder(String.valueOf(rand.nextInt(3) + 1));
			d.setSmallLowflow("lowFlow");
			break;
		case 1:
			d.setLargeCylinder(String.valueOf(rand.nextInt(3) + 1));
			d.setLargeLowflow("lowFlow");			
			break;
		}
//		d.setDateCylinstall();

	}

	private void translateFunding(Section2 s) {
		Section2.Funding f = new Section2.Funding();
		s.setFunding(f);
		f.setFundingYn(rand.nextBoolean()? "Y": "N");
		f.setFundingRequested(randomFunding());
	}

	private String randomFunding() {
		switch (rand.nextInt(4)) {
		case 0:
			return "ltRH90days";
		case 1:
			return "lt12months";
		case 2:
			return "palliative90days";			
		case 3:
			return "st60days";			
		}		
		return "invalidFunding";
	}	

}
