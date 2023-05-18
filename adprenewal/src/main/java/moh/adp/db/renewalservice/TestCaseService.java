package moh.adp.db.renewalservice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import moh.adp.db.common.TestDBException;
import moh.adp.db.common.TestOutcome;
import moh.adp.db.convert.Convert;
import moh.adp.db.etl.ExcelETL;
import moh.adp.db.jpa.RenewalRecord;
import moh.adp.db.jpa.TestEntity;
import moh.adp.db.jpa.TestSet;
import moh.adp.db.model.Test;
import moh.adp.xml.RenewalTranslator;
import moh.adp.xml.RenewalTranslatorFactory;

//Old fashioned singleton because 
//injection is too convoluted
public class TestCaseService {
	private static TestCaseService tcs;
	
	private TestCaseService(){		
	}

	static {
		tcs = new TestCaseService();
	}
	
	public static TestCaseService instance() {
		return tcs;
	}
	
	public Test getTestCase(String name, EntityManager em) {
		moh.adp.db.jpa.TestEntity testEntity = em.createNamedQuery("Test.byName", moh.adp.db.jpa.TestEntity.class)
												.setParameter("name", name)
												.getSingleResult();
		return Convert.bean2Bean(testEntity, Test.class);
	}

	public void runETL(String directory, String testName, EntityManager em) {
		String description = testName + " data extracted from Excel sheet.";
		ExcelETL.importRecords(testName, description, directory,  em);
	}
	
	public TestResult runGMRenewalRandom(String numberOfRecords, EntityManager em) {
		try {
			return runGMRenewalRandomDetails(numberOfRecords, em);
		} catch (Exception e) {
			e.printStackTrace();
			return new TestResult(TestOutcome.TEST_APP_FAILURE, "e-Renewal test case. An error was encountered.");
		}	
	}
		
	private TestResult runGMRenewalRandomDetails(String numberOfRecords, EntityManager em) {
		int numberOfClaims = Integer.parseInt(numberOfRecords);		
		Map<String, String> eClaimXMLDocs = getCGMESubXMLs(numberOfClaims); 
		Map<String, String> eRenewalXMLDocs = getCGMRenewalESubXMLs(eClaimXMLDocs); 
		saveToSFTS(eClaimXMLDocs);
		saveToSFTS(eRenewalXMLDocs);
		return new TestResult(TestOutcome.EXPECTED_OUTCOME, "Ran renewal random details.");
	}

	private Map<String, String> getCGMRenewalESubXMLs(Map<String, String> eClaimXMLDocs) {
		Map<String, String> renewals = new HashMap<>();
		eClaimXMLDocs.forEach( (f, c) -> {
			
		});		
		return renewals;
	}

	private Map<String, String> getCGMESubXMLs(int numberOfClaims) {
		Map<String, String> claims = new HashMap<>();
		for (int i=0; i<numberOfClaims; i++) {
			
		}			
		return claims;
	}

	public TestResult runGMRenewalTestCase(String testCase, EntityManager em) {
		try {
			return runGMRenewalTestCaseDetails(testCase, em);
		} catch (Exception e) {
			e.printStackTrace();
			return new TestResult(TestOutcome.TEST_APP_FAILURE, "e-Renewal test case. An error was encountered.");
		}		
	}
	
	public TestResult runGMRenewalTestCaseDetails(String testCase, EntityManager em) {
		List<TestSet> testSets = getTestCases(testCase, em);
		List<RenewalRecord> records = getRecords(testSets); 
		Map<String, String> eRenewalXMLDocs = getCGMRenewalESubXMLs(records); 
		saveToSFTS(eRenewalXMLDocs);
		return new TestResult(TestOutcome.EXPECTED_OUTCOME, "alright, running test case");
	}
	
	private void saveToSFTS(Map<String, String> eRenewalXMLDocs) {
		if (eRenewalXMLDocs != null)
			eRenewalXMLDocs.forEach((name, doc) -> saveFile(name, doc));
		System.out.println();
	}

	protected void saveFile(String name, String doc) {
		System.out.println("DOC: " + doc);
		try {
			Files.write(Paths.get("c:/TEST/renewals/" + name), doc.getBytes(), new OpenOption[]{});
		} catch (IOException e) {
			throw new TestDBException("couldn't save file", e);
		}
	}	
	
	private Map<String, String> getCGMRenewalESubXMLs(List<RenewalRecord> records) {
		Map<String, String> results = new HashMap<>();
		RenewalTranslator<RenewalRecord> translator = getTranslator(records); 
		records.forEach( r -> translator.translate(r, results));	
		return results;
	}

	@SuppressWarnings("unchecked")
	private RenewalTranslator<RenewalRecord> getTranslator(List<RenewalRecord> records) {
		if (records == null || records.isEmpty())
			throw new TestDBException("No renewal records specified.");
		RenewalRecord r = records.get(0);
		return (RenewalTranslator<RenewalRecord>)RenewalTranslatorFactory.getRenewalTranslator(r);
	}

	private List<RenewalRecord> getRecords(List<TestSet> testSets) {
		return testSets.stream()
				.flatMap(ts -> ts.getRecordSets().stream()
						.flatMap(rs -> rs.getRenewalRecords().stream()))
							.collect(Collectors.toList());
	}

	private List<TestSet> getTestCases(String testCase, EntityManager em) {
		TestEntity te = em.createNamedQuery("Test.byName", moh.adp.db.jpa.TestEntity.class)
							.setParameter("name", testCase)
							.getSingleResult();
		return te.getTestSets();
	}
	
}
