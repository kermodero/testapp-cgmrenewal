package moh.adp.db.model;

import java.io.Serializable;
import java.util.List;

import moh.adp.db.convert.Convert;
import moh.adp.db.model.TestSet;
import static moh.adp.db.common.Util.notNull;

public class Test implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String description;
	private String name;
	private List<TestSet> testSets;
	
	public Test() {
	}

	public Test(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
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

	public List<TestSet> getTestSets() {
		return notNull(testSets);
	}

	//evil
	public void setTestSets(List<moh.adp.db.jpa.TestSet> testSets) {
		if (testSets == null)
			return;
		testSets.forEach(ts -> {
			TestSet testSet = Convert.bean2Bean(ts, TestSet.class);
			testSet.setTestId(getId());
			this.getTestSets().add(testSet);			
		});
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Test: ");
		sb.append(getName());
		sb.append(" \\n ");
		getTestSets().forEach(ts -> sb.append(ts));
		return sb.toString();
	}
	
}
