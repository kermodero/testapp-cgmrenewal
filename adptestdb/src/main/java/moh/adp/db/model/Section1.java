package moh.adp.db.model;

import moh.adp.common.Constants;
import moh.adp.common.util.EqualUtil;
import moh.adp.model.claim.Claim;
import moh.adp.model.client.Client;
import moh.adp.model.party.prescriber.PhysicianView;

public class Section1 extends Section {
	private String healthNumber;
	private String confirmationOfBenefitQ1YN;
	private String confirmationOfBenefitQ1IfYes;
	private String confirmationOfBenefitQ2YN;
	private String confirmationOfBenefitQ3YN;
	public static final String YES = "yes";
	public static final String NO = "no";
	public static final String NA = "na";
	private static final String OWP = "owp";
	private static final String ODSP = "odsp";
	private static final String ACSD = "acsd";
	
	public String getHealthNumber() {
		return healthNumber;
	}
	public void setHealthNumber(String healthNumber) {
		this.healthNumber = healthNumber;
	}
	public String getConfirmationOfBenefitQ1YN() {
		return confirmationOfBenefitQ1YN;
	}
	public void setConfirmationOfBenefitQ1YN(String confirmationOfBenefitQ1YN) {
		this.confirmationOfBenefitQ1YN = confirmationOfBenefitQ1YN;
	}
	public String getConfirmationOfBenefitQ1IfYes() {
		return confirmationOfBenefitQ1IfYes;
	}
	public void setConfirmationOfBenefitQ1IfYes(String confirmationOfBenefitQ1IfYes) {
		this.confirmationOfBenefitQ1IfYes = confirmationOfBenefitQ1IfYes;
	}
	public String getConfirmationOfBenefitQ2YN() {
		return confirmationOfBenefitQ2YN;
	}
	public void setConfirmationOfBenefitQ2YN(String confirmationOfBenefitQ2YN) {
		this.confirmationOfBenefitQ2YN = confirmationOfBenefitQ2YN;
	}
	public String getConfirmationOfBenefitQ3YN() {
		return confirmationOfBenefitQ3YN;
	}
	public void setConfirmationOfBenefitQ3YN(String confirmationOfBenefitQ3YN) {
		this.confirmationOfBenefitQ3YN = confirmationOfBenefitQ3YN;
	}
	public void populate(Claim claim, Client client) {
		claim.setClaimClientId(client.getId());
		claim.setClaimClient(client);
		
		if (EqualUtil.equals(YES, getConfirmationOfBenefitQ1YN())) {
			claim.setBenefitReceivedInd(Constants.Y);
		} else if (EqualUtil.equals(NO, getConfirmationOfBenefitQ1YN()))
			claim.setBenefitReceivedInd(Constants.N);

		if (EqualUtil.equals(getConfirmationOfBenefitQ1IfYes(), OWP))
			claim.setOwp(true);
		if (EqualUtil.equals(getConfirmationOfBenefitQ1IfYes(), ODSP))
			claim.setOdsp(true);
		if (EqualUtil.equals(getConfirmationOfBenefitQ1IfYes(), ACSD))
			claim.setAcsd(true);

		if (EqualUtil.equals(YES, getConfirmationOfBenefitQ2YN()))
			claim.setWsibEligibleInd(Constants.Y);		
		if (EqualUtil.equals(NO, getConfirmationOfBenefitQ2YN()))
			claim.setWsibEligibleInd(Constants.N);		

		if (EqualUtil.equals(YES, getConfirmationOfBenefitQ3YN()))
			claim.setVacEligibleInd(Constants.Y);
		else if (EqualUtil.equals(NO, getConfirmationOfBenefitQ3YN()))
			claim.setVacEligibleInd(Constants.N);
		
		populateVariances(claim);

	}

}
