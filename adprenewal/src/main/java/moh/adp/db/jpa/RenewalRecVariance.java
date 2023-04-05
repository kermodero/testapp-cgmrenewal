package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RENEWAL_REC_VARIANCE database table.
 * 
 */
@Entity
@Table(name="RENEWAL_REC_VARIANCE", schema="APP")
@NamedQuery(name="RenewalRecVariance.findAll", query="SELECT r FROM RenewalRecVariance r")
public class RenewalRecVariance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="FORM_SECTION")
	private int formSection;

	@Column(name="PROPERTY_NAME")
	private String propertyName;

	@Column(name="STRING_PROPERTY_VALUE")
	private String stringPropertyValue;

	//bi-directional many-to-one association to RenewalRecord
	@ManyToOne
	@JoinColumn(name="RENEWAL_RECORD_ID")
	private RenewalRecord renewalRecord;

	public RenewalRecVariance() {
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

	public String getStringPropertyValue() {
		return this.stringPropertyValue;
	}

	public void setStringPropertyValue(String stringPropertyValue) {
		this.stringPropertyValue = stringPropertyValue;
	}

	public RenewalRecord getRenewalRecord() {
		return this.renewalRecord;
	}

	public void setRenewalRecord(RenewalRecord renewalRecord) {
		this.renewalRecord = renewalRecord;
	}

}