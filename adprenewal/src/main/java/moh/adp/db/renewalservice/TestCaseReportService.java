package moh.adp.db.renewalservice;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import moh.adp.db.jpa.RenewalErrorOutcome;
import moh.adp.outcome.TestOutcome;

public class TestCaseReportService {
	private static TestCaseReportService tcrs;
	private static Map<String, String> properties;

	static {
		tcrs = new TestCaseReportService();
	}
	
	private TestCaseReportService(){		
	}

	public static void setProperties(Map<String, String> applicationProperties) {
		properties = applicationProperties;
	}
	
	public static TestCaseReportService instance() {
		return tcrs;
	}

	public TestOutcome runGMRenewalReport(String testId, EntityManager em) {
		TestOutcome outcome = queryForErrorOutcomes(testId, em);
		//outcome.addAll(queryForPositiveOutcomes(testId, em));
		return outcome;
	}

	private TestOutcome queryForErrorOutcomes(String testId, EntityManager em) {
		List<RenewalErrorOutcome> expectedOutcome = getExpectedOutcomes(testId, em); 
		expectedOutcome.forEach(reo -> System.out.println("error outcome " + reo));

		TestOutcome to = new TestOutcome();
		return to;
	}
	
	private List<RenewalErrorOutcome> getExpectedOutcomes(String testId, EntityManager em) {
		return em.createNamedQuery("RenewalErrorOutcome.byTestId", moh.adp.db.jpa.RenewalErrorOutcome.class)
				.setParameter("name", testId).getResultList();
	}

	private TestOutcome queryForPositiveOutcomes(String testId, EntityManager em) {
		em.createNamedQuery("RenewalPositiveOutcome.byTestId", moh.adp.db.jpa.RenewalPositiveOutcome.class) //moh.adp.db.jpa.RenewalErrorOutcomeEntity.class
		.setParameter("name", testId)
		.getSingleResult();
		return null;
	}

}
