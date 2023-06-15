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
import moh.adp.testapp.rest.common.Result;
import moh.adp.testapp.rest.common.TestScope;


@Singleton
public class ADAMServer {
	@Inject
	public Logger logger;
	@Inject
	public TestDataService testDataService;
	public TestClaimService claimService;	
	@PersistenceContext(unitName = "adptestdb")
	private EntityManager em;
	@Resource 
	private UserTransaction userTransaction;
	
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
		return new Result(tr.getMessage() + " " + tr.getOutcome(), Result.Outcome.UNKNOWN);
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

	public void createClaims(Result r, String testName) {
		// TODO Auto-generated method stub
		
	}

	public void runETL(Result r, String directory, String testName) {
		// TODO Auto-generated method stub
		
	}		
	
}
