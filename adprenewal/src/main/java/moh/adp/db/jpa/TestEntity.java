package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the TEST database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Test.findAll", 
				query="SELECT t FROM TestEntity t"),	
	@NamedQuery(name="Test.allByName", 
			    query="SELECT T " +
			            "FROM TestEntity T " +
				        "   LEFT JOIN T.testSets TS " +
			            "   LEFT JOIN TS.recordSets RS " +
			            "   LEFT JOIN RS.claimRecords CR " +
			            "   LEFT JOIN CR.claimRecVariances CRV " + 
			            "WHERE T.name = :name"),
	@NamedQuery(name="Test.byName", 
    			query="SELECT T " +
			            "FROM TestEntity T " +
			            "WHERE T.name = :name")
	})
@Table (name = "TEST", schema = "APP")
public class TestEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @Column(name = "DESCRIPTION")
	private String description;

    @Column(name = "NAME")
	private String name;

	//bi-directional many-to-one association to TestSet
	@OneToMany(mappedBy="test", fetch=FetchType.EAGER)
	private List<TestSet> testSets;

	public TestEntity() {
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
		return this.testSets;
	}

	public void setTestSets(List<TestSet> testSets) {
		this.testSets = testSets;
	}

	public TestSet addTestSet(TestSet testSet) {
		getTestSets().add(testSet);
		testSet.setTest(this);

		return testSet;
	}

	public TestSet removeTestSet(TestSet testSet) {
		getTestSets().remove(testSet);
		testSet.setTest(null);

		return testSet;
	}

}