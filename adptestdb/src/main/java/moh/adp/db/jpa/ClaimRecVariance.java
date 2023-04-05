package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the CLAIM_REC_VARIANCE database table.
 * 
 */
@Entity
@Table(name="CLAIM_REC_VARIANCE", schema = "APP")
@NamedQueries ({
	@NamedQuery(name="ClaimRecVariance.findAll", query="SELECT c FROM ClaimRecVariance c"),
	@NamedQuery(name="ClaimRecVariance.byClaimRecordId", 
				query="SELECT c "
						+ "FROM ClaimRecVariance c "
						+ "WHERE c.claimRecord.id = :id")
})
public class ClaimRecVariance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_PROPERTY_VALUE")
	private Date datePropertyValue;

	@Column(name="NUMERIC_PROPERTY_VALUE")
	private BigDecimal numericPropertyValue;

	@Column(name="PROPERTY_NAME")
	private String propertyName;

	@Column(name="STRING_PROPERTY_VALUE")
	private String stringPropertyValue;

	//bi-directional many-to-one association to ClaimRecord
	@ManyToOne
	@JoinColumn(name="CLAIM_RECORD_ID")
	private ClaimRecord claimRecord;

	public ClaimRecVariance() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDatePropertyValue() {
		return this.datePropertyValue;
	}

	public void setDatePropertyValue(Date datePropertyValue) {
		this.datePropertyValue = datePropertyValue;
	}

	public BigDecimal getNumericPropertyValue() {
		return this.numericPropertyValue;
	}

	public void setNumericPropertyValue(BigDecimal numericPropertyValue) {
		this.numericPropertyValue = numericPropertyValue;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		if (propertyName == null || propertyName.length() == 0)
			return;
		int len = (propertyName.length() > 56) ? 55 : propertyName.length(); 
		this.propertyName = propertyName.substring(0, len);
	}

	public String getStringPropertyValue() {
		return this.stringPropertyValue;
	}

	public void setStringPropertyValue(String stringPropertyValue) {
		this.stringPropertyValue = stringPropertyValue;
	}

	public ClaimRecord getClaimRecord() {
		return this.claimRecord;
	}

	public void setClaimRecord(ClaimRecord claimRecord) {
		this.claimRecord = claimRecord;
	}

}