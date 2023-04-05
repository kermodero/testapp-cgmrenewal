package moh.adp.db.model;

import java.util.Date;

import moh.adp.model.claim.Claim;
import moh.adp.model.claim.ClaimSignature;
import moh.adp.model.claim.ClientAgent;

public class Section3 extends Section {
	private Long claimSignatureId;
	private Long claimSignatureCode;
	private Person person;
	private Date signatureDate;
	private Long contactId;
	public enum Person {
		agent,
		applicant,
		LTCH
	};
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	public Date getSignatureDate() {
		return signatureDate;
	}
	public void setSignatureDate(Date signatureDate) {
		this.signatureDate = signatureDate;
	}
	public Long getContactId() {
		return contactId;
	}
	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}
	public Long getClaimSignatureId() {
		return claimSignatureId;
	}
	public void setClaimSignatureId(Long claimSignatureId) {
		this.claimSignatureId = claimSignatureId;
	}
	public Long getClaimSignatureCode() {
		return claimSignatureCode;
	}
	public void setClaimSignatureCode(Long claimSignatureCode) {
		this.claimSignatureCode = claimSignatureCode;
	}
	public void populate(Claim claim, ClientAgent ca, ClaimSignature cs) {
		claim.setClientAgent(ca);
		claim.setClientAgentId(ca.getId());
		claim.setSignedDate(signatureDate);
		claim.setConsentSignature(cs);
		claim.setConsentSignatureId(cs.getId());
		setPayee(claim);
		
		populateVariances(claim);
		
	}
	
	private void setPayee(Claim claim) {
		if (claim.isDeviceCategoryInsulinPump()) {
			switch(person) {
			case agent:
				claim.setPayeeAgent(true);
				break;
			case applicant:
				claim.setPayeeApplicant(true);
				break;
			default:
				claim.setPayeeLtcHome(true);
				break;		
			}
		}
	}
	
}
