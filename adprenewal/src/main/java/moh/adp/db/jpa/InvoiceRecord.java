package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the INVOICE_RECORD database table.
 * 
 */
@Entity
@Table(name="INVOICE_RECORD", schema = "APP")
@NamedQuery(name="InvoiceRecord.findAll", query="SELECT i FROM InvoiceRecord i")
public class InvoiceRecord implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="ADAM_CLAIM_NUM")
	private String adamClaimNum;

	//bi-directional many-to-one association to RecordSet
	@ManyToOne
	@JoinColumn(name="RECORD_SET_ID")
	private RecordSet recordSet;

	public InvoiceRecord() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdamClaimNum() {
		return this.adamClaimNum;
	}

	public void setAdamClaimNum(String adamClaimNum) {
		this.adamClaimNum = adamClaimNum;
	}

	public RecordSet getRecordSet() {
		return this.recordSet;
	}

	public void setRecordSet(RecordSet recordSet) {
		this.recordSet = recordSet;
	}

}