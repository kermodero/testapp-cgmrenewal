package moh.adp.db.model;

import java.io.Serializable;
import java.util.List;

import moh.adp.db.convert.Convert;
import moh.adp.db.model.ClaimRecord;
import moh.adp.db.model.InvoiceRecord;
import static moh.adp.db.common.Util.notNull;

public class RecordSet implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String fileName;
	private int testSetId;
	private List<ClaimRecord> claimRecords;
	private List<InvoiceRecord> invoiceRecords;

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

	public int getTestSetId() {
		return testSetId;
	}

	public void setTestSetId(int testSetId) {
		this.testSetId = testSetId;
	}

	public List<ClaimRecord> getClaimRecords() {
		return notNull(claimRecords);
	}

	//evil
	public void setClaimRecords(List<moh.adp.db.jpa.ClaimRecord> claimRecords) {
		if (claimRecords == null)
			return;
		claimRecords.forEach(cr -> {
			ClaimRecord claimRecord = Convert.bean2Bean(cr, ClaimRecord.class);
			claimRecord.setRecordSetId(getId());
			this.getClaimRecords().add(claimRecord);	
		});
	}

	public List<InvoiceRecord> getInvoiceRecords() {
		return notNull(invoiceRecords);
	}

	public void setInvoiceRecords(List<InvoiceRecord> invoiceRecords) {
		//this.invoiceRecords = invoiceRecords;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Record set: ");
		sb.append(getFileName());
		sb.append("\n");
		getClaimRecords().forEach(cr -> sb.append(cr));
		sb.append("\n");
		getInvoiceRecords().forEach(ir -> sb.append(ir));
		return sb.toString();
	}
	
}