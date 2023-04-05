package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;

import moh.adp.db.jpa.ClaimRecord;


/**
 * The persistent class for the CLAIM_REC_FIELD database table.
 * 
 */
@Entity
@Table(name="CLAIM_REC_FIELD", schema="APP")
@NamedQuery(name="ClaimRecField.findAll", query="SELECT c FROM ClaimRecField c")
public class ClaimRecField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;

	@Column(name="PROPERTY_NAME")
	private String propertyName;

	@Column(name="PROPERTY_VALUE")
	private String propertyValue;

	//bi-directional many-to-one association to ClaimRecord
	@ManyToOne
	@JoinColumn(name="CLAIM_RECORD_ID")
	private ClaimRecord claimRecord;

	public ClaimRecField() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyValue() {
		return this.propertyValue;
	}
	
	public void setPropertyValue(String propertyValue) {
		this.propertyValue = propertyValue;
	}

	public void setPropertyNameAndValue(String propertyName, String propertyValue) {
		if (propertyName == null || propertyName.length() == 0)
			return;
		int len = (propertyName.length() > 56) ? 55 : propertyName.length(); 
		this.propertyName = propertyName.substring(0, len);
		this.propertyValue = propertyValue;
	}
	
	public ClaimRecord getClaimRecord() {
		return this.claimRecord;
	}

	public void setClaimRecord(ClaimRecord claimRecord) {
		this.claimRecord = claimRecord;
	}
	
}