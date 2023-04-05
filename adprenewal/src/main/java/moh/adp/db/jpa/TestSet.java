package moh.adp.db.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TEST_SET database table.
 * 
 */
@Entity
@Table(name="TEST_SET", schema = "APP")
@NamedQueries({
	@NamedQuery(name = "TestSet.byTestId",
				query = "select ts " +
						" from TestSet ts " +
						" where ts.test.id = :id"),
	@NamedQuery(name="TestSet.findAll", query="SELECT t FROM TestSet t")
})

public class TestSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    @Column(name = "DESCRIPTION")
	private String description;

    @Column(name = "NAME")
	private String name;

	//bi-directional many-to-one association to RecordSet
	@OneToMany(mappedBy="testSet", fetch=FetchType.EAGER)
	private List<RecordSet> recordSets;

	//bi-directional many-to-one association to Test
	@ManyToOne
	private TestEntity test;

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

	public List<RecordSet> getRecordSets() {
		return this.recordSets;
	}

	public void setRecordSets(List<RecordSet> recordSets) {
		this.recordSets = recordSets;
	}

	public RecordSet addRecordSet(RecordSet recordSet) {
		getRecordSets().add(recordSet);
		recordSet.setTestSet(this);

		return recordSet;
	}

	public RecordSet removeRecordSet(RecordSet recordSet) {
		getRecordSets().remove(recordSet);
		recordSet.setTestSet(null);

		return recordSet;
	}

	public TestEntity getTest() {
		return this.test;
	}

	public void setTest(TestEntity test) {
		this.test = test;
	}

}