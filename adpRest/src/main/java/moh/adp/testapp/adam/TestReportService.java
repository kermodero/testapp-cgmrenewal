package moh.adp.testapp.adam;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.transaction.UserTransaction;
import javax.persistence.EntityManager;

import org.slf4j.Logger;

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

	public TestReport runGMRenewalReport(String testId, EntityManager em) {
		
		
		
		return null;
	}
	

}
