package moh.adp.db.model;

import moh.adp.model.claim.Claim;
import moh.adp.model.claim.ClaimSignature;

public class Section4 extends Section {
	private Long stakeholderTypeCode;
	private Long physicianId;
	private Long authorizerId;
	private Long vendorId;
	private String adpClinicNo;
	private String vendorNum;
	private String billingNum;
	private String physicianProfession;
	
	public Long getPhysicianId() {
		return physicianId;
	}
	public void setPhysicianId(Long physicianId) {
		this.physicianId = physicianId;
	}
	public Long getAuthorizerId() {
		return authorizerId;
	}
	public void setAuthorizerId(Long authorizerId) {
		this.authorizerId = authorizerId;
	}
	public Long getVendorId() {
		return vendorId;
	}
	public void setVendorId(Long vendorId) {
		this.vendorId = vendorId;
	}
	public Long getStakeholderTypeCode() {
		return stakeholderTypeCode;
	}
	public void setStakeholderTypeCode(Long stakeholderTypeCode) {
		this.stakeholderTypeCode = stakeholderTypeCode;
	}	
	public String getAdpClinicNo() {
		return adpClinicNo;
	}
	public void setAdpClinicNo(String adpClinicNo) {
		this.adpClinicNo = adpClinicNo;
	}
	public String getVendorNum() {
		return vendorNum;
	}
	public void setVendorNum(String vendorNum) {
		this.vendorNum = vendorNum;
	}
	public String getBillingNum() {
		return billingNum;
	}
	public void setBillingNum(String billingNum) {
		this.billingNum = billingNum;
	}
	public String getPhysicianProfession() {
		return physicianProfession;
	}
	public void setPhysicianProfession(String physicianProfession) {
		this.physicianProfession = physicianProfession;
	}
	public void populate(Claim claim, ClaimSignature ps, ClaimSignature cls, ClaimSignature vs, ClaimSignature as) {
		if (this.physicianId != null)
			claim.setPhysicianSignature(ps);
		if (this.authorizerId != null)
			claim.setAuthorizerSignature(as);
		if (this.getVendorNum() != null)
			claim.setVendorSignature(vs);
		if (this.getAdpClinicNo() != null)
			claim.setClinicSignature(cls);		
		populateVariances(claim);		
	}


}
