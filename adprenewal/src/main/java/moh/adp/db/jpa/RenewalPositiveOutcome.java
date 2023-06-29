package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RENEWAL_POSITIVE_OUTCOME database table.
 * 
 */
@Entity
@Table(name="RENEWAL_POSITIVE_OUTCOME", schema = "APP")
@NamedQuery(name="RenewalPositiveOutcome.findAll", query="SELECT r FROM RenewalPositiveOutcome r")
public class RenewalPositiveOutcome implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="EXPECTED_OUTCOME")
	private String expectedOutcome;

	@Column(name="PARAM1")
	private String param1;

	@Column(name="PARAM2")
	private String param2;

	@Column(name="PARAM3")
	private String param3;

	@Column(name="PARAM4")
	private String param4;

	@Column(name="RENEWAL_RECORD_ID")
	private int renewalRecordId;

	@Column(name="SQL_NAME")
	private String sqlName;

	public RenewalPositiveOutcome() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getExpectedOutcome() {
		return this.expectedOutcome;
	}

	public void setExpectedOutcome(String expectedOutcome) {
		this.expectedOutcome = expectedOutcome;
	}

	public String getParam1() {
		return this.param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return this.param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return this.param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return this.param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public int getRenewalRecordId() {
		return this.renewalRecordId;
	}

	public void setRenewalRecordId(int renewalRecordId) {
		this.renewalRecordId = renewalRecordId;
	}

	public String getSqlName() {
		return this.sqlName;
	}

	public void setSqlName(String sqlName) {
		this.sqlName = sqlName;
	}

}