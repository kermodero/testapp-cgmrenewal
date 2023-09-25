package moh.adp.testapp.adam;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;

import moh.adp.db.renewalservice.TestCaseService;
import moh.adp.db.renewalservice.TestResult;
import moh.adp.server.AdpServiceLocator;
import moh.adp.server.esubmission.eClaim.EClaimBatchService;
import moh.adp.server.esubmission.eRenewal.service.ERenewalService;
import moh.adp.testapp.rest.common.Result;
import moh.adp.testapp.rest.common.TestReport;
import moh.adp.testapp.rest.common.Result.Outcome;
import moh.adp.testapp.rest.common.TestScope;


@Singleton
public class ADAMServer {
	@Inject
	public Logger logger;
	@Inject
	public TestReportService testReportService;
	public TestClaimService claimService;	
	@PersistenceContext(unitName = "adptestdb")
	private EntityManager em;
	@Resource 
	private UserTransaction userTransaction;
	private Object renewalBatchLock = new Object();
	
	public ADAMServer() {
		
	}

	@PostConstruct
	public void init() {
		System.out.println("constructing ADAMServer. " + logger);
	}

	public Result runNightlyBatchDirectly(TestScope ts, String testId) {
		return null;
	}

	public Result runRenewal(String testId) {
	    logger.debug("running renewal. " + logger + " " + testId + " service? ");
	    TestResult tr = TestCaseService.instance().runGMRenewalTestCase(testId, em);
		return new Result("All good", tr, Result.Outcome.UNKNOWN);
	}

	public Result runReport(TestResult testResult) {
	    logger.debug("running test result " + logger + " " + testResult + " service? ");
	    TestReport testReport = testReportService.runGMRenewalReport(testResult, em);
		return new Result("report run " + testReport.toString(), Result.Outcome.UNKNOWN);
	}	
	
	public Result runRenewalRandom(String numberOfRecords) {
	    logger.debug("running renewal random ");
	    TestResult tr = TestCaseService.instance().runGMRenewalRandom(numberOfRecords, em);
		return new Result(tr.getMessage() + " " + tr.getOutcome(), Result.Outcome.UNKNOWN);
	}	

	public Result createRandomRenewals(String deviceCategory, int count) {
	    logger.debug("creating " + count + " random claims of device category " + deviceCategory);
	    TestResult tr = TestCaseService.instance().createRandom(deviceCategory, count, em);
		return new Result(tr.getMessage() + " " + tr.getOutcome(), Result.Outcome.UNKNOWN);
	}

	public Result runERenewal() {
		try {
			return tryToRunERenewal();	
		} catch (Exception e) {
			return new Result("ERenewal failed " + e.getMessage(), Outcome.ERROR);
		}
	}

	private Result tryToRunERenewal() throws Exception {
		synchronized (renewalBatchLock) { // ensure this blocks if concurrent attempts are made to execute it.		
			EClaimBatchService.initSFTPConnection();
			EClaimBatchService.loadEClaimFiles();
			ERenewalService eRenewalService = (ERenewalService) AdpServiceLocator.getBeanReference(ERenewalService.class);
			eRenewalService.processERenewalFiles();
		}		
		return new Result("Payment Batch successful.", Outcome.SUCCESS);
	}		
	
	public Result generateEOXFiles(String testId, int count) {
	    logger.debug("generating EOX: " + testId);
	    TestResult tr = TestCaseService.instance().generateEOXFiles(testId, count, em);
		return new Result("Generated EOX ", tr, Result.Outcome.UNKNOWN);
	}
		
	public void createClaims(Result r, String testName) {
		// TODO Auto-generated method stub
	}

	public void runETL(Result r, String directory, String testName) {
		// TODO Auto-generated method stub
	}

}
