package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RENEWAL_REC_FIELD database table.
 * 
 */
@Entity
@Table(name="RENEWAL_REC_FIELD", schema="APP")
@NamedQuery(name="RenewalRecField.findAll", query="SELECT r FROM RenewalRecField r")
public class RenewalRecField implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="FORM_SECTION")
	private int formSection;

	@Column(name="PROPERTY_NAME")
	private String propertyName;

	@Column(name="PROPERTY_VALUE")
	private String propertyValue;

	//bi-directional many-to-one association to RenewalRecord
	@ManyToOne
	@JoinColumn(name="RENEWAL_RECORD_ID")
	private RenewalRecord renewalRecord;

	public RenewalRecField() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFormSection() {
		return this.formSection;
	}

	public void setFormSection(int formSection) {
		this.formSection = formSection;
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

	public RenewalRecord getRenewalRecord() {
		return this.renewalRecord;
	}

	public void setRenewalRecord(RenewalRecord renewalRecord) {
		this.renewalRecord = renewalRecord;
	}

}