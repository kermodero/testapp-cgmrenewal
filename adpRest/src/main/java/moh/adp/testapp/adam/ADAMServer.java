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
import moh.adp.server.util.EntityManagerUtil;
import moh.adp.testapp.rest.common.Result;
import moh.adp.testapp.rest.common.TestScope;

/*import moh.adp.db.model.ClaimRecord;
import moh.adp.db.model.RecordSet;
import moh.adp.db.model.Test;
import moh.adp.db.model.TestSet;
import moh.adp.db.service.TestCaseService;
import moh.adp.testapp.rest.common.Result;
import moh.adp.testapp.rest.common.Result.Outcome;
import moh.adp.testapp.rest.common.TestScope;*/

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
	//	testCaseService  = new TestCaseService();
	}

	public Result runNightlyBatchDirectly(TestScope ts, String testId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createClaims(Result r, String testName) {
		// TODO Auto-generated method stub
		
	}

	public void runETL(Result r, String directory, String testName) {
		// TODO Auto-generated method stub
		
	}

	public Result runRenewal(String testId) {
	    System.out.println("running renewal. " + logger + " " + testId + " service? "); // + testCaseService);
		try {
			TestResult tr = TestCaseService.instance().runGMRenewalTestCase(testId, em);
			return new Result(tr.getMessage() + " " + tr.getOutcome(), Result.Outcome.UNKNOWN);
		} catch (Exception e) {
			return new Result("Test run failed: " + e.getMessage(), Result.Outcome.UNKNOWN_EXCEPTION);
		}

	}

/*	public Result runNightlyBatchDirectly(TestScope ts, String testId) {
		logger.debug("running regression for " + ts.name());
		Result result = new Result();
		result.setOutcome(Outcome.SUCCESS);
		switch (ts) {
		case ALL:
			runAll(result, testId);
			break;
		case CCAC:
			break;
		case ECLAIM:
			runEClaim(result, testId);
			break;
		case INVOICE:
			break;
		case OXYGEN:
			break;
		case PAYMENT:
			runPayment(result, testId);
			break;
		case RENEWAL:
			break;
		case INVALID:
			result.setOutcome(Outcome.INVALID_TEST);
			result.setMessage("Unrecognized test");
			break;			
		default:
			result.setOutcome(Outcome.INVALID_TEST);
			result.setMessage("Unknown test");
			break;		
		}
		return result;
	}

	public void runPayment(Result r, String testId) {
//		ProcessAdminBatchService service = new ProcessAdminBatchService();
		try {
			service.runPaymentBatchProcess();
		} catch (Exception e) {
			r.setMessage("Payment Batch failed " + e.getMessage());
			r.setOutcome(Outcome.ERROR);
		}
		r.setMessage("Payment Batch successful." );
	}
	
	public void runEClaim(Result r, String testId) {
		try {
			testDataService.prepareEClaimTest(testId);
//			EClaimBatchService.initSFTPConnection();
//			EClaimBatchService.loadEClaimFiles();
//			EClaimBatchService.processEClaimFiles();
		} catch (Exception e) {
			r.setMessage("ESubmission Batch failed " + e.getMessage());
			r.setOutcome(Outcome.ERROR);
		}
		r.setMessage("E-claim Batch successful." );
	}

	public void runAll(Result r, String testId) {
		try {
			testDataService.prepareAll(testId);
			ProcessAdminBatchService pabService = new ProcessAdminBatchService();
			pabService.runRenewalBatchProcess();
			pabService.runInvoiceBatchProcess();
			pabService.runPaymentBatchProcess();
			pabService.runHomeOxygenBatchProcess();
			pabService.runCcacUploadBatchProcess();
			runEClaim(r, testId);			
		} catch (Exception e) {
			r.setMessage("Full nightly Batch failed " + e.getMessage());
			r.setOutcome(Outcome.ERROR);
		}
		r.setMessage("Payment Batch successful." );
	}

	public void createClaims(Result r, String testId) {
		claimService = new TestClaimService();
		try {
			Test test = testCaseService.getTestCase("GENERIC_CLAIM", em);
			for (TestSet ts : test.getTestSets())
				for (RecordSet rs : ts.getRecordSets())
					for (ClaimRecord cr : rs.getClaimRecords()) {
						claimService.createOne(cr);						
					}
			r.setMessage("Claim creation succeeded");
			r.setOutcome(Outcome.SUCCESS);			
		} catch (Exception e) {
			r.setMessage("Claim creation failed " + e.getMessage());
			r.setOutcome(Outcome.ERROR);			
		}
	}

	public void runETL(Result r, String directory, String testName) {
		try {
			userTransaction.begin();
			testCaseService.runETL(directory, testName, em);
			userTransaction.commit();
		} catch (Exception e) {
			r.setMessage("ETL failed " + e.getMessage());
			r.setOutcome(Outcome.ERROR);
			e.printStackTrace();
		}
		r.setMessage("ETL completed." );
		r.setOutcome(Outcome.SUCCESS);
	}
	*/
}
