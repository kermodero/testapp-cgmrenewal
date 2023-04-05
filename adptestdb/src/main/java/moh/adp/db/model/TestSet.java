package moh.adp.db.model;

import java.io.Serializable;
import java.util.List;

import moh.adp.db.convert.Convert;
import moh.adp.db.model.RecordSet;
import static moh.adp.db.common.Util.notNull;

public class TestSet implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String name;
	private int testId;
	private List<RecordSet> recordSets;
	
	public TestSet() {
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTestId() {
		return testId;
	}

	public void setTestId(int testId) {
		this.testId = testId;
	}

	public List<RecordSet> getRecordSets() {
		return notNull(recordSets);
	}

	//evil
	public void setRecordSets(List<moh.adp.db.jpa.RecordSet> recordSets) {
		if (recordSets == null)
			return;
		recordSets.forEach(rs -> {
			RecordSet recordSet = Convert.bean2Bean(rs, RecordSet.class);
			recordSet.setTestSetId(getId());
			this.getRecordSets().add(recordSet);
		});
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TestSet: ");
		sb.append(getName());
		sb.append("\n");
		getRecordSets().forEach(rs -> sb.append(rs));
		return sb.toString();
	}

}