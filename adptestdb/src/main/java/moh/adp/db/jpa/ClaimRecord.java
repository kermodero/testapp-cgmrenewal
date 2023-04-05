package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the CLAIM_RECORD database table.
 * 
 */
@Entity
@Table(name="CLAIM_RECORD", schema = "APP")
@NamedQueries ({
	@NamedQuery(name="ClaimRecord.findAll", query="SELECT c FROM ClaimRecord c"),
	@NamedQuery(name="ClaimRecord.byRecordSetId", 
				query="SELECT c "
						+ "FROM ClaimRecord c "
						+ "WHERE c.recordSet.id = :id")
})
public class ClaimRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;

	@Column(name="HEALTH_NUMBER")
	private String healthNumber;

	@Column(name="ADAM_PHYSICIAN_ID")
	private long adamPhysicianId;

	@Column(name="ADAM_VENDOR1_ID")
	private long adamVendor1Id;
	
	@Column(name="DEVICE_CATEGORY")
	private String deviceCategory;	

	//bi-directional many-to-one association to RecordSet
	@ManyToOne
	@JoinColumn(name="RECORD_SET_ID")
	private RecordSet recordSet;

	//bi-directional many-to-one association to ClaimRecVariance
	@OneToMany(mappedBy="claimRecord", fetch=FetchType.EAGER)
	private List<ClaimRecVariance> claimRecVariances;
	
	//bi-directional many-to-one association to ClaimRecField
	@OneToMany(mappedBy="claimRecord", fetch=FetchType.EAGER)
	private List<ClaimRecField> claimRecFields;	

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

	public String getHealthNumber() {
		return this.healthNumber;
	}

	public void setHealthNumber(String healthNumber) {
		this.healthNumber = healthNumber;
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

	public RecordSet getRecordSet() {
		return this.recordSet;
	}

	public void setRecordSet(RecordSet recordSet) {
		this.recordSet = recordSet;
	}

	public List<ClaimRecVariance> getClaimRecVariances() {
		return this.claimRecVariances;
	}
	
	public void setClaimRecVariances(List<ClaimRecVariance> claimRecVariances) {
		this.claimRecVariances = claimRecVariances;
	}
	
	public ClaimRecVariance addClaimRecVariance(ClaimRecVariance claimRecVariance) {
		getClaimRecVariances().add(claimRecVariance);
		claimRecVariance.setClaimRecord(this);

		return claimRecVariance;
	}

	public ClaimRecVariance removeClaimRecVariance(ClaimRecVariance claimRecVariance) {
		getClaimRecVariances().remove(claimRecVariance);
		claimRecVariance.setClaimRecord(null);

		return claimRecVariance;
	}
	
	public List<ClaimRecField> getClaimRecFields() {
		return this.claimRecFields;
	}

	public void setClaimRecFields(List<ClaimRecField> claimRecFields) {
		this.claimRecFields = claimRecFields;
	}
	
	public ClaimRecField addClaimRecField(ClaimRecField claimRecField) {
		getClaimRecFields().add(claimRecField);
		claimRecField.setClaimRecord(this);

		return claimRecField;
	}

	public ClaimRecField removeClaimRecField(ClaimRecField claimRecField) {
		getClaimRecFields().remove(claimRecField);
		claimRecField.setClaimRecord(null);

		return claimRecField;
	}


}