package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the EXPECTED_OUTCOME database table.
 * 
 */
@Entity
@Table(name="EXPECTED_OUTCOME", schema="APP")
@NamedQuery(name="ExpectedOutcome.findAll", query="SELECT e FROM ExpectedOutcome e")
public class ExpectedOutcome implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;

	@Column(name="E_CLAIM_RECORD_ID")
	private int eClaimRecordId;

	@Temporal(TemporalType.DATE)
	@Column(name="EXPECTED_DATE_VALUE")
	private Date expectedDateValue;

	@Column(name="EXPECTED_NUMERIC_VALUE")
	private BigDecimal expectedNumericValue;

	@Column(name="EXPECTED_STRING_VALUE")
	private String expectedStringValue;

	@Column(name="PROPERTY_NAME")
	private String propertyName;

	public ExpectedOutcome() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getEClaimRecordId() {
		return this.eClaimRecordId;
	}

	public void setEClaimRecordId(int eClaimRecordId) {
		this.eClaimRecordId = eClaimRecordId;
	}

	public Date getExpectedDateValue() {
		return this.expectedDateValue;
	}

	public void setExpectedDateValue(Date expectedDateValue) {
		this.expectedDateValue = expectedDateValue;
	}

	public BigDecimal getExpectedNumericValue() {
		return this.expectedNumericValue;
	}

	public void setExpectedNumericValue(BigDecimal expectedNumericValue) {
		this.expectedNumericValue = expectedNumericValue;
	}

	public String getExpectedStringValue() {
		return this.expectedStringValue;
	}

	public void setExpectedStringValue(String expectedStringValue) {
		this.expectedStringValue = expectedStringValue;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

}