package moh.adp.db.model;

import java.io.Serializable;

import java.util.List;

import moh.adp.db.convert.Convert;
import static moh.adp.db.common.Util.notNull;

public class ClaimRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String adamClientHealthNumber;
	private long adamClientId;
	private long adamPhysicianId;
	private long adamVendor1Id;
	private int recordSetId;
	private String deviceCategory;
	private SignatureType signatureType;
	private List<ClaimRecVariance> claimRecVariances;

	public enum SignatureType {
		applicant,
		agent
	}
	
	public ClaimRecord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDeviceCategory() {
		return deviceCategory;
	}

	public void setDeviceCategory(String deviceCategory) {
		this.deviceCategory = deviceCategory;
	}

	public long getAdamClientId() {
		return this.adamClientId;
	}

	public void setAdamClientId(long adamClientId) {
		this.adamClientId = adamClientId;
	}

	public String getAdamClientHealthNumber() {
		return adamClientHealthNumber;
	}

	public void setAdamClientHealthNumber(String adamClientHealthNumber) {
		this.adamClientHealthNumber = adamClientHealthNumber;
	}

	public long getAdamPhysicianId() {
		return this.adamPhysicianId;
	}

	public void setAdamPhysicianId(long adamPhysicianId) {
		this.adamPhysicianId = adamPhysicianId;
	}

	public long getAdamVendor1Id() {
		return this.adamVendor1Id;
	}

	public void setAdamVendor1Id(long adamVendor1Id) {
		this.adamVendor1Id = adamVendor1Id;
	}

	public int getRecordSetId() {
		return recordSetId;
	}

	public void setRecordSetId(int recordSetId) {
		this.recordSetId = recordSetId;
	}

	public List<ClaimRecVariance> getClaimRecVariances() {
		return notNull(claimRecVariances);
	}

	public void setClaimRecVariances(List<moh.adp.db.jpa.ClaimRecVariance> claimRecVariances) {
		if (claimRecVariances == null)
			return;
		claimRecVariances.forEach(cr -> this.getClaimRecVariances().add(Convert.bean2Bean(cr, ClaimRecVariance.class)));
	}

	public SignatureType getSignatureType() {
		return signatureType;
	}

	public void setSignatureType(SignatureType signatureType) {
		this.signatureType = signatureType;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Claim record, client id: ");
		sb.append(this.getAdamClientId());
		sb.append("\n");
		getClaimRecVariances().forEach(crv -> sb.append(crv));
		return sb.toString();
	}

}