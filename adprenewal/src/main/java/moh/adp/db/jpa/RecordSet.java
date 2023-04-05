package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

import moh.adp.db.jpa.RenewalRecord;

/**
 * The persistent class for the RECORD_SET database table.
 * 
 */
@Entity
@Table(name="RECORD_SET", schema="APP")
@NamedQuery(name="RecordSet.findAll", query="SELECT r FROM RecordSet r")
public class RecordSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	private String description;

	@Column(name="FILE_NAME")
	private String fileName;

	//bi-directional many-to-one association to ClaimRecord
	@OneToMany(mappedBy="recordSet")
	private List<ClaimRecord> claimRecords;

	//bi-directional many-to-one association to InvoiceRecord
	@OneToMany(mappedBy="recordSet")
	private List<InvoiceRecord> invoiceRecords;

	//bi-directional many-to-one association to TestSet
	@ManyToOne
	@JoinColumn(name="TEST_SET_ID")
	private TestSet testSet;

	//bi-directional many-to-one association to RenewalRecord
	@OneToMany(mappedBy="recordSet", fetch=FetchType.EAGER)
	private List<RenewalRecord> renewalRecords;

	public RecordSet() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public List<ClaimRecord> getClaimRecords() {
		return this.claimRecords;
	}

	public void setClaimRecords(List<ClaimRecord> claimRecords) {
		this.claimRecords = claimRecords;
	}

	public ClaimRecord addClaimRecord(ClaimRecord claimRecord) {
		getClaimRecords().add(claimRecord);
		claimRecord.setRecordSet(this);

		return claimRecord;
	}

	public ClaimRecord removeClaimRecord(ClaimRecord claimRecord) {
		getClaimRecords().remove(claimRecord);
		claimRecord.setRecordSet(null);

		return claimRecord;
	}

	public List<InvoiceRecord> getInvoiceRecords() {
		return this.invoiceRecords;
	}

	public void setInvoiceRecords(List<InvoiceRecord> invoiceRecords) {
		this.invoiceRecords = invoiceRecords;
	}

	public InvoiceRecord addInvoiceRecord(InvoiceRecord invoiceRecord) {
		getInvoiceRecords().add(invoiceRecord);
		invoiceRecord.setRecordSet(this);

		return invoiceRecord;
	}

	public InvoiceRecord removeInvoiceRecord(InvoiceRecord invoiceRecord) {
		getInvoiceRecords().remove(invoiceRecord);
		invoiceRecord.setRecordSet(null);

		return invoiceRecord;
	}

	public TestSet getTestSet() {
		return this.testSet;
	}

	public void setTestSet(TestSet testSet) {
		this.testSet = testSet;
	}

	public List<RenewalRecord> getRenewalRecords() {
		return this.renewalRecords;
	}

	public void setRenewalRecords(List<RenewalRecord> renewalRecords) {
		this.renewalRecords = renewalRecords;
	}

	public RenewalRecord addRenewalRecord(RenewalRecord renewalRecord) {
		getRenewalRecords().add(renewalRecord);
//		renewalRecord.setRecordSet(this);

		return renewalRecord;
	}

	public RenewalRecord removeRenewalRecord(RenewalRecord renewalRecord) {
		getRenewalRecords().remove(renewalRecord);
	//	renewalRecord.setRecordSet(null);

		return renewalRecord;
	}

}