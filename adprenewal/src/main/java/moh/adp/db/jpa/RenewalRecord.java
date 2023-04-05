package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the RENEWAL_RECORD database table.
 * 
 */
@Entity
@Table(name="RENEWAL_RECORD", schema="APP")
@NamedQuery(name="RenewalRecord.findAll", query="SELECT r FROM RenewalRecord r")
public class RenewalRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

/*	@Column(name="ADAM_PHYSICIAN_ID")
	private long adamPhysicianId;*/

	@Column(name="ADAM_VENDOR_ID")
	private long adamVendorId;

	@Column(name="CLIENT_AGENT_ID")
	private long clientAgentId;

	@Column(name="DEVICE_CATEGORY")
	private String deviceCategory;

	@Column(name="FORM_VERSION")
	private String formVersion;

	@Column(name="HEALTH_NUMBER")
	private String healthNumber;

	@Column(name="ORIGINAL_CLAIM_NUM")
	private String originalClaimNum;

	//bi-directional many-to-one association to RecordSet
	@ManyToOne
	@JoinColumn(name="RECORD_SET_ID")
	private RecordSet recordSet;

	//bi-directional many-to-one association to RenewalRecField
	@OneToMany(mappedBy="renewalRecord")
	private List<RenewalRecField> renewalRecFields;

	//bi-directional many-to-one association to RenewalRecVariance
	@OneToMany(mappedBy="renewalRecord")
	private List<RenewalRecVariance> renewalRecVariances;

	public RenewalRecord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

/*	public long getAdamPhysicianId() {
		return this.adamPhysicianId;
	}

	public void setAdamPhysicianId(long adamPhysicianId) {
		this.adamPhysicianId = adamPhysicianId;
	}*/

	public long getAdamVendorId() {
		return this.adamVendorId;
	}

	public void setAdamVendorId(long adamVendorId) {
		this.adamVendorId = adamVendorId;
	}

	public long getClientAgentId() {
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