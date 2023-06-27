package moh.adp.db.renewalservice;

import java.util.Map;

import javax.persistence.EntityManager;

import moh.adp.outcome.TestOutcome;

public class TestCaseReportService {
	private static TestCaseReportService tcrs;
	private static Map<String, String> properties;
	
	private TestCaseReportService(){		
	}

	public static void setProperties(Map<String, String> applicationProperties) {
		properties = applicationProperties;
	}
	
	static {
		tcrs = new TestCaseReportService();
	}
	
	public static TestCaseReportService instance() {
		return tcrs;
	}

	public TestOutcome runGMRenewalReport(String testId, EntityManager em) {
		TestOutcome outcome = getErrorOutcomes(testId, em);
		outcome.addAll(getPositiveOutcomes(testId, em));
		return outcome;
	}

	private TestOutcome getPositiveOutcomes(String testId, EntityManager em) {
		TestOutcome outcome = getPositiveOutcomes(testId, em);
		outcome.addAll(getPositiveOutcomes(testId, em));
		return outcome;
	}

	private TestOutcome getErrorOutcomes(String testId, EntityManager em) {
		em.createNamedQuery("RenewalErrorOutcome.byName", moh.adp.db.jpa.TestEntity.class) //moh.adp.db.jpa.RenewalErrorOutcomeEntity.class
		.setParameter("name", testId)
		.getSingleResult();

		return null;
	}
	
	
	
	

}
