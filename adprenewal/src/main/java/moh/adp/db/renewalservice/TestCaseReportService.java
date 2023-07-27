package moh.adp.db.renewalservice;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;

import moh.adp.common.exception.AdpException;
import moh.adp.db.common.TestDBException;
import moh.adp.db.jpa.RenewalErrorOutcome;
import moh.adp.model.AdpArrayList;
import moh.adp.model.claim.eSubmission.EClaimFile;
import moh.adp.model.claim.eSubmission.ViewEClaimResult;
import moh.adp.model.claim.eSubmission.ViewEClaimSearchCriteria;
import moh.adp.outcome.RecordOutcome;
import moh.adp.outcome.TestOutcome;
import moh.adp.server.AdpServiceLocator;
import moh.adp.server.HomeLocator;
import moh.adp.server.TransactionUtil;
import moh.adp.server.esubmission.eClaim.ViewEClaimSession;

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

	@SuppressWarnings("rawtypes")
	public TestOutcome runGMRenewalReport(TestResult testResult, EntityManager em) {
		TestOutcome errorOutcome = queryForErrorOutcomes(testResult, em);
		testResult.getFileNames().forEach(fn -> reportOnOneGMRenewal(fn, errorOutcome));
		
		//outcome.addAll(queryForPositiveOutcomes(testId, em));
		return errorOutcome;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void reportOnOneGMRenewal(String fn, TestOutcome outcome) {
		outcome.getExpectedOutcomes().forEach(oc -> {
			RenewalErrorOutcome reo = (RenewalErrorOutcome)oc;
			System.out.println("reo record fn: " + reo.getRenewalRecord().getFileName() + "; fn: " + fn); 
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private TestOutcome queryForErrorOutcomes(TestResult testResult, EntityManager em) {
		List<RenewalErrorOutcome> expectedErrorOutcomes = getExpectedErrorOutcomes(testResult.getTestId(), em); 
		expectedErrorOutcomes.forEach(reo -> System.out.println("error outcome " + reo));
		List<ViewEClaimResult> actualOutcomes = getActualOutcomes(testResult, em); 		
		actualOutcomes.forEach(rao -> System.out.println("actual outcome " + rao));
		TestOutcome to = new TestOutcome(expectedErrorOutcomes, RenewalErrorOutcome.class, actualOutcomes, ViewEClaimResult.class);
		return to;
	}
	
	private List<ViewEClaimResult> getActualOutcomes(TestResult testResult, EntityManager em) {
        try {
    		ViewEClaimSearchCriteria criteria = getCriteria(testResult); 
            ViewEClaimSession session = AdpServiceLocator.getBeanReference(ViewEClaimSession.class);
			AdpArrayList results = session.search(criteria);
			return results.stream().map(e -> (ViewEClaimResult)e).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	private ViewEClaimSearchCriteria getCriteria(TestResult testResult) {
		ViewEClaimSearchCriteria criteria = new ViewEClaimSearchCriteria();
		if (testResult.getFileNames() == null || testResult.getFileNames().isEmpty())
			throw new TestDBException("no e-renewal files.");
		criteria.setFileName("RWKGMRenewalTest");
		criteria.setGeneratedStartDate(Date.from(Instant.now().minusMillis(120 * 1000))); //look only at records created in the last minutes.
		return criteria;
	}

	private List<RenewalErrorOutcome> getExpectedErrorOutcomes(String testId, EntityManager em) {
		return new ArrayList<>(em.createNamedQuery("RenewalErrorOutcome.byTestId", moh.adp.db.jpa.RenewalErrorOutcome.class)
				.setParameter("name", testId).getResultList());
	}

	private TestOutcome getExpectedPositiveOutcomes(String testId, EntityManager em) {
		em.createNamedQuery("RenewalPositiveOutcome.byTestId", moh.adp.db.jpa.RenewalPositiveOutcome.class) //moh.adp.db.jpa.RenewalErrorOutcomeEntity.class
		.setParameter("name", testId)
		.getSingleResult();
		return null;
	}

}
