package moh.adp.db.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ClaimRecVariance implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private Date datePropertyValue;
	private BigDecimal numericPropertyValue;
	private String propertyName;
	private String stringPropertyValue;
	private int claimRecordId;

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
		this.propertyName = propertyName;
	}

	public String getStringPropertyValue() {
		return this.stringPropertyValue;
	}

	public void setStringPropertyValue(String stringPropertyValue) {
		this.stringPropertyValue = stringPropertyValue;
	}

	public int getClaimRecordId() {
		return claimRecordId;
	}

	public void setClaimRecordId(int claimRecordId) {
		this.claimRecordId = claimRecordId;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Claim Record Variance, claim id: ");
		sb.append(this.getClaimRecordId());
		sb.append("\n");
		if (this.getStringPropertyValue() != null)
			sb.append(this.getStringPropertyValue());
		else if (this.getNumericPropertyValue() != null)
			sb.append(this.getNumericPropertyValue());
		else if (this.getDatePropertyValue() != null)
			sb.append(this.getDatePropertyValue());		
		sb.append("\n");
		return sb.toString();
	}

}