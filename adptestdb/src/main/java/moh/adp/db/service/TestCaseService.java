package moh.adp.db.service;

import javax.persistence.EntityManager;

import moh.adp.db.convert.Convert;
import moh.adp.db.etl.ExcelETL;
import moh.adp.db.model.Test;

public class TestCaseService {
	
	public TestCaseService(){
		
	}
	
	public Test getTestCase(String name, EntityManager em) {
		moh.adp.db.jpa.TestEntity test = em.createNamedQuery("Test.byName", moh.adp.db.jpa.TestEntity.class)
												.setParameter("name", name)
												.getSingleResult();
		return Convert.bean2Bean(test, Test.class);
	}

	public void runETL(String directory, String testName, EntityManager em) {
		String description = testName + " data extracted from Excel sheet.";
		ExcelETL.importRecords(testName, description, directory,  em);
	}
	
	// https://stackoverflow.com/questions/11693552/java-code-to-xml-xsd-without-using-annotation
	
}
