package moh.adp.db.etl;

import java.util.List;

import static moh.adp.db.common.Util.notNull;

public class ExcelClaimRecord {
	private String deviceCategory;
	private String gateKeeperDate;
	private String versionNumber;
	private Section1 section1;
	private Section2 section2;
	private Section3 section3;
	private Section4 section4;
	private List<TestError> expectedErrors;

	public ExcelClaimRecord(String deviceCategory) {
		this.deviceCategory = deviceCategory;
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
	public String getGateKeeperDate() {
		return gateKeeperDate;
	}
	public void setGateKeeperDate(String gateKeeperDate) {
		this.gateKeeperDate = gateKeeperDate;
	};	
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
	public List<TestError> getExpectedErrors() {
		return notNull(expectedErrors);
	}
	public void setExpectedErrors(List<TestError> expectedErrors) {
		this.expectedErrors = expectedErrors;
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.section1.healthNumber);
		sb.append(" ");
		sb.append(this.gateKeeperDate);
		sb.append(" ");
		section1.fields.forEach((key,val) -> {
			sb.append(" ");
			sb.append(key);
			sb.append(" - ");
			sb.append(val);
			sb.append("; ");
		});
		return sb.toString();
	}

}
