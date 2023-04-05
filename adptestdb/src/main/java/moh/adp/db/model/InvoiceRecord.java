package moh.adp.db.model;

import java.io.Serializable;

public class InvoiceRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private int adamClaimId;

	private int recordSetId;

	public InvoiceRecord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAdamClaimId() {
		return this.adamClaimId;
	}

	public void setAdamClaimId(int adamClaimId) {
		this.adamClaimId = adamClaimId;
	}

	public int getRecordSetId() {
		return recordSetId;
	}

	public void setRecordSetId(int recordSetId) {
		this.recordSetId = recordSetId;
	}

}