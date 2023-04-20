package moh.adp.db.jpa;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the RENEWAL_RECORD database table.
 * 
 */
@Entity
@Table(name = "RENEWAL_RECORD", schema = "APP")
@NamedQuery(name = "RenewalRecord.findAll", query = "SELECT r FROM RenewalRecord r")
public class RenewalRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/*
	 * @Column(name="ADAM_PHYSICIAN_ID") private long adamPhysicianId;
	 */

	@Column(name = "ADAM_VENDOR_ID")
	private Long adamVendorId;

	@Column(name = "CLIENT_AGENT_ID")
	private Long clientAgentId;

	@Column(name = "DEVICE_CATEGORY")
	private String deviceCategory;

	@Column(name = "FORM_VERSION")
	private String formVersion;

	@Column(name = "RANDOM_RECORDS")
	private Integer randomRecords;

	@Column(name = "HEALTH_NUMBER")
	private String healthNumber;

	@Column(name = "ORIGINAL_CLAIM_NUM")
	private String originalClaimNum;

	@Column(name = "QUESTION1")
	private String question1;

	@Column(name = "QUESTION2")
	private String question2;

	@Column(name = "QUESTION3")
	private String question3;

	@Column(name = "CERTIFICATION")
	private String certification;

	@Column(name = "SIGNED_BY")
	private String signedBy;

	@Column(name = "SIGNATURE")
	private String signature;

	@Column(name = "SIGN_DATE")
	private Date signDate;

	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "EXPECTED_OUTCOME")
	private String expectedOutcome;
	
	@Column(name = "ERROR_MESSAGE")
	private String errorMessage;

	// bi-directional many-to-one association to RecordSet
	@ManyToOne
	@JoinColumn(name = "RECORD_SET_ID")
	private RecordSet recordSet;

	// bi-directional many-to-one association to RenewalRecField
	@OneToMany(mappedBy = "renewalRecord", fetch=FetchType.EAGER)
	private List<RenewalRecField> renewalRecFields;

	// bi-directional many-to-one association to RenewalRecVariance
	@OneToMany(mappedBy = "renewalRecord")
	private List<RenewalRecVariance> renewalRecVariances;

	public RenewalRecord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	 * public long getAdamPhysicianId() { return this.adamPhysicianId; }
	 * 
	 * public void setAdamPhysicianId(long adamPhysicianId) {
	 * this.adamPhysicianId = adamPhysicianId; }
	 */

	public Long getAdamVendorId() {
		return this.adamVendorId;
	}

	public void setAdamVendorId(long adamVendorId) {
		this.adamVendorId = adamVendorId;
	}

	public Long getClientAgentId() {
		return this.clientAgentId;
	}

	public void setClientAgentId(long clientAgentId) {
		this.clientAgentId = clientAgentId;
	}

	public String getDeviceCategory() {
		return this.deviceCategory;
	}

	public void setDeviceCategory(String deviceCategory) {
		this.deviceCategory = deviceCategory;
	}

	public String getFormVersion() {
		return this.formVersion;
	}

	public void setFormVersion(String formVersion) {
		this.formVersion = formVersion;
	}

	public Integer getRandomRecords() {
		return randomRecords;
	}

	public void setRandomRecords(Integer randomRecords) {
		this.randomRecords = randomRecords;
	}

	public String getHealthNumber() {
		return this.healthNumber;
	}

	public void setHealthNumber(String healthNumber) {
		this.healthNumber = healthNumber;
	}

	public String getOriginalClaimNum() {
		return this.originalClaimNum;
	}

	public void setOriginalClaimNum(String originalClaimNum) {
		this.originalClaimNum = originalClaimNum;
	}

	public String getQuestion1() {
		return question1;
	}

	public void setQuestion1(String question1) {
		this.question1 = question1;
	}

	public String getQuestion2() {
		return question2;
	}

	public void setQuestion2(String question2) {
		this.question2 = question2;
	}

	public String getQuestion3() {
		return question3;
	}

	public void setQuestion3(String question3) {
		this.question3 = question3;
	}

	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	public String getSignedBy() {
		return signedBy;
	}

	public void setSignedBy(String signedBy) {
		this.signedBy = signedBy;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Date getSignDate() {
		return signDate;
	}

	public void setSignDate(Date signDate) {
		this.signDate = signDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getExpectedOutcome() {
		return expectedOutcome;
	}

	public void setExpectedOutcome(String expectedOutcome) {
		this.expectedOutcome = expectedOutcome;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public RecordSet getRecordSet() {
		return this.recordSet;
	}

	public void setRecordSet(RecordSet recordSet) {
		this.recordSet = recordSet;
	}

	public List<RenewalRecField> getRenewalRecFields() {
		return this.renewalRecFields;
	}

	public void setRenewalRecFields(List<RenewalRecField> renewalRecFields) {
		this.renewalRecFields = renewalRecFields;
	}

	public RenewalRecField addRenewalRecField(RenewalRecField renewalRecField) {
		getRenewalRecFields().add(renewalRecField);
		renewalRecField.setRenewalRecord(this);

		return renewalRecField;
	}

	public RenewalRecField removeRenewalRecField(RenewalRecField renewalRecField) {
		getRenewalRecFields().remove(renewalRecField);
		renewalRecField.setRenewalRecord(null);

		return renewalRecField;
	}

	public List<RenewalRecVariance> getRenewalRecVariances() {
		return this.renewalRecVariances;
	}

	public void setRenewalRecVariances(List<RenewalRecVariance> renewalRecVariances) {
		this.renewalRecVariances = renewalRecVariances;
	}

	public RenewalRecVariance addRenewalRecVariance(RenewalRecVariance renewalRecVariance) {
		getRenewalRecVariances().add(renewalRecVariance);
		renewalRecVariance.setRenewalRecord(this);

		return renewalRecVariance;
	}

	public RenewalRecVariance removeRenewalRecVariance(RenewalRecVariance renewalRecVariance) {
		getRenewalRecVariances().remove(renewalRecVariance);
		renewalRecVariance.setRenewalRecord(null);

		return renewalRecVariance;
	}

}