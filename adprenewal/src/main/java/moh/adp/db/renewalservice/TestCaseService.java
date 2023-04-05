package moh.adp.db.renewalservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import javax.persistence.EntityManager;

import moh.adp.db.common.TestOutcome;
import moh.adp.db.convert.Convert;
import moh.adp.db.etl.ExcelETL;
import moh.adp.db.jpa.RenewalRecord;
import moh.adp.db.jpa.TestEntity;
import moh.adp.db.jpa.TestSet;
import moh.adp.db.model.Test;
import moh.adp.xml.RenewalTranslator;
import moh.adp.xml.RenewalTranslatorFactory;
//import moh.adp.test.common.log.UILog;

//Old fashioned singleton because 
//injection is too convoluted
public class TestCaseService {
	private static TestCaseService tcs;
		
	private TestCaseService(){
		
	}
	
	public static TestCaseService instance() {
		if (tcs == null)
			tcs = new TestCaseService();
		return tcs;
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
	
	public TestResult runTestCase(String testCase, EntityManager em) {
		try {
			return runTestCaseDetails(testCase, em);
		} catch (Exception e) {
			//UILog.log("alright, running test case " + em);
			e.printStackTrace();
			return new TestResult(TestOutcome.TEST_APP_FAILURE, "e-Renewal test case. An error was encountered.");
		}		
	}
	
	public TestResult runTestCaseDetails(String testCase, EntityManager em) {
		System.out.println("alright, running test case " + em);
		List<TestSet> testSets = getTestCases(testCase, em);
		List<RenewalRecord> records = getRecords(testSets); 
		List<String> eRenewalXMLDocs = getCGMRenewalESubXMLs(records); 
		saveToSFTS(eRenewalXMLDocs);
		return new TestResult(TestOutcome.EXPECTED_OUTCOME, "alright, running test case");
	}

	public TestResult tempTest(String testCase, EntityManager em) {
		System.out.println("alright, running test case " + em);
		moh.adp.db.jpa.TestEntity test = em.createNamedQuery("Test.byName", moh.adp.db.jpa.TestEntity.class)
				.setParameter("name", "GENERIC_RENEWAL")
				.getSingleResult();
		if (test == null)
			System.out.println("was null");
		else {
			System.out.println("test " + test.getDescription() + " - " + test.getTestSets());		
			test.getTestSets().forEach(ts -> ts.getDescription());
		}
		return new TestResult(TestOutcome.EXPECTED_OUTCOME, "alright, running test case");
	}
	
	private void saveToSFTS(List<String> eRenewalXMLDocs) {
		if (eRenewalXMLDocs != null)
			eRenewalXMLDocs.forEach(doc -> System.out.println("DOC " + doc));
		System.out.println();
	}

	private List<String> getCGMRenewalESubXMLs(List<RenewalRecord> records) {
		List<String> results = new ArrayList<String>();
		RenewalTranslator<RenewalRecord> translator = getTranslator(records); 
		if (translator == null)
			return results;
		records.forEach( r -> results.add(translator.translate(r)) );				
		return results;
	}

	@SuppressWarnings("unchecked")
	private RenewalTranslator<RenewalRecord> getTranslator(List<RenewalRecord> records) {
		if (records == null || records.isEmpty())
			return null;
		RenewalRecord r = records.get(0);
		return (RenewalTranslator<RenewalRecord>)RenewalTranslatorFactory.getRenewalTranslator(r);
	}

	private List<RenewalRecord> getRecords(List<TestSet> testSets) {
		return testSets.stream()
				.flatMap(ts -> ts.getRecordSets().stream()
						.flatMap(rs -> rs.getRenewalRecords().stream()))
							.collect(Collectors.toList()); //TODO - check this logic...
	}

	private List<TestSet> getTestCases(String testCase, EntityManager em) {
		TestEntity te = em.createNamedQuery("Test.byName", moh.adp.db.jpa.TestEntity.class)
							.setParameter("name", testCase)
							.getSingleResult();
		return te.getTestSets();
	}
	
	// https://stackoverflow.com/questions/11693552/java-code-to-xml-xsd-without-using-annotation
	
}
