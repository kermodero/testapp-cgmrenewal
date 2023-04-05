package moh.adp.db.model;

import java.io.Serializable;
import java.util.List;

public class ClaimRecordFull implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String deviceCategory;
	private String versionNumber;	
	private Section1 section1;
	private Section2 section2;
	private Section3 section3;
	private Section4 section4;	
	private int recordSetId;
	private List<ClaimRecVariance> claimRecVariances;
	
	public ClaimRecordFull(){
		
	}	
	public int getId() {
		return id;
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
	public String getVersionNumber() {
		return versionNumber;
	}
	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}
	public Section1 getSection1() {
		section1 = (section1 == null) ? new Section1() : section1;
		return section1;
	}
	public void setSection1(Section1 section1) {
		this.section1 = section1;
	}
	public Section2 getSection2() {
		section2 = (section2 == null) ? new Section2() : section2;
		return section2;
	}
	public void setSection2(Section2 section2) {
		this.section2 = section2;
	}
	public Section3 getSection3() {
		section3 = (section3 == null) ? new Section3() : section3;
		return section3;
	}
	public void setSection3(Section3 section3) {
		this.section3 = section3;
	}
	public Section4 getSection4() {
		section4 = (section4 == null) ? new Section4() : section4;
		return section4;
	}
	public void setSection4(Section4 section4) {
		this.section4 = section4;
	}
	public int getRecordSetId() {
		return recordSetId;
	}
	public void setRecordSetId(int recordSetId) {
		this.recordSetId = recordSetId;
	}
	public List<ClaimRecVariance> getClaimRecVariances() {
		return claimRecVariances;
	}
	public void setClaimRecVariances(List<ClaimRecVariance> claimRecVariances) {
		this.claimRecVariances = claimRecVariances;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Claim record, client id: ");
		sb.append(this.getSection1().getHealthNumber());
		sb.append("\n");
		getClaimRecVariances().forEach(crv -> sb.append(crv));
		return sb.toString();
	}

}