package moh.adp.testapp.adam;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.UserTransaction;
import javax.persistence.EntityManager;

import org.slf4j.Logger;


import moh.adp.db.renewalservice.TestCaseReportService;
import moh.adp.db.renewalservice.TestResult;
import moh.adp.outcome.TestOutcome;
import moh.adp.testapp.rest.common.TestReport;


@Singleton
public class TestReportService {
	@Inject
	private Logger logger;
	
	public TestReportService(){
		
	}
	
	@PostConstruct
	public void init() {
		logger.debug("initializing Test Report Service");
		
	}

	public TestReport runGMRenewalReport(TestResult testResult, EntityManager em) {
		TestOutcome to = TestCaseReportService.instance().runGMRenewalReport(testResult, em);
		return createReport(to);
	}

	private TestReport createReport(TestOutcome to) {

		return null;
	}
	

}
