package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the RENEWAL_ERROR_OUTCOME database table.
 * 
 */
@Entity
@Table(name="RENEWAL_ERROR_OUTCOME", schema = "APP")
@NamedQueries({
	@NamedQuery(name="RenewalErrorOutcome.findAll", query="SELECT r FROM RenewalErrorOutcome r"),
	@NamedQuery(name="RenewalErrorOutcome.byTestId", 
					query="SELECT REO "
							+ "FROM TestEntity T "
							+ "LEFT JOIN T.testSets TS "
							+ "LEFT JOIN TS.recordSets RS "
							+ "LEFT JOIN RS.renewalRecords RR "
							+ "LEFT JOIN RR.renewalErrorOutcomes REO "
							+ "WHERE T.name = :name") })
public class RenewalErrorOutcome implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Column(name="ERROR_MESSAGE_ID")
	private long errorMessageId;

	@Column(name="ERROR_PARM1")
	private String errorParm1;

	@Column(name="ERROR_PARM2")
	private String errorParm2;

	@Column(name="ERROR_PARM3")
	private String errorParm3;

	//bi-directional many-to-one association to RenewalRecord
	@ManyToOne
	@JoinColumn(name="RENEWAL_RECORD_ID")
	private RenewalRecord renewalRecord;
	
	public RenewalErrorOutcome() {
		
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getErrorMessageId() {
		return this.errorMessageId;
	}

	public void setErrorMessageId(long errorMessageId) {
		this.errorMessageId = errorMessageId;
	}

	public String getErrorParm1() {
		return this.errorParm1;
	}

	public void setErrorParm1(String errorParm1) {
		this.errorParm1 = errorParm1;
	}

	public String getErrorParm2() {
		return this.errorParm2;
	}

	public void setErrorParm2(String errorParm2) {
		this.errorParm2 = errorParm2;
	}

	public String getErrorParm3() {
		return this.errorParm3;
	}

	public void setErrorParm3(String errorParm3) {
		this.errorParm3 = errorParm3;
	}



	public RenewalRecord getRenewalRecord() {
		return renewalRecord;
	}

	public void setRenewalRecord(RenewalRecord renewalRecord) {
		this.renewalRecord = renewalRecord;
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("REO: ");
		sb.append(errorMessageId);
		sb.append("; " );
		sb.append(errorParm1);
		sb.append("; " );
		sb.append(errorParm2);
		sb.append("; " );
		sb.append(errorParm3);
		return sb.toString();
	}
	
}