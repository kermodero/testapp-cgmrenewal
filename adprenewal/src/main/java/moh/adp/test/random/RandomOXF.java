package moh.adp.test.random;

import java.util.ArrayList;
import java.util.List;

import moh.adp.model.claim.form.ClaimOxygen;

public class RandomOXF extends RandomClaim<ClaimOxygen>{

	public static List<ClaimOxygen> generateOXF(int count) {
		RandomOXF roxf = new RandomOXF();
		List<ClaimOxygen> claims = new ArrayList<>();		
		for (int i=0; i<count; i++)
			claims.add(roxf.generate());
		return claims;
	}

	@Override
	public ClaimOxygen generate() {
		ClaimOxygen cg = new ClaimOxygen();
		populateDeviceSpecific(cg);
		populate(cg);
		return cg;
	}

	@Override
	public void populateDeviceSpecific(ClaimOxygen cg) {
/*		populateFunding(cg);
		populateDelivery(cg);
		populateReason(cg);
		populateOxTherapy(cg);
		populateIndependentAssessment(cg);
		populateTestConfirmation(cg);*/
		
	}

	private void populateTestConfirmation(ClaimOxygen cg) {
		// TODO Auto-generated method stub
		
	}

	private void populateIndependentAssessment(ClaimOxygen cg) {
		// TODO Auto-generated method stub
		
	}

	private void populateOxTherapy(ClaimOxygen cg) {
		// TODO Auto-generated method stub
		
	}

	private void populateReason(ClaimOxygen cg) {
		// TODO Auto-generated method stub
		
	}

	private void populateDelivery(ClaimOxygen cg) {
		
	}

	private void populateFunding(ClaimOxygen cg) {
		long code = 165000 + rand.nextInt(5) + 1; //see CodeValueConsts.OXYGEN_FUNDING_PROGRAM_REGULAR_CD  
		cg.setFundingProgramCd(code);
		long period = 166000 + rand.nextInt(5) + 1;
		cg.setFormFundingPeriodCd(period);
	}

}
