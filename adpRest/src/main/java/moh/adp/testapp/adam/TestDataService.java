package moh.adp.testapp.adam;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.slf4j.Logger;

@Singleton
public class TestDataService {
	@Inject
	private Logger logger;
	@Inject
	public SFTService sftpService;
	public final String USER = "USER";
	
	public void prepareAll(String testId) {
		logger.debug("preparing data for all");
		if (isDataSuppliedByUser(testId))
			return;
		prepareEClaimTest(testId);
		//TODO 
	}

	public void prepareEClaimTest(String testId) {
		logger.debug("preparing data for eclaim test");
		if (isDataSuppliedByUser(testId))
			return;
		
	}

	/* Convention:
	 * If testId is empty or "USER", do not provide data.
	 */
	private boolean isDataSuppliedByUser(String testId) {
		return (testId == null || USER.equalsIgnoreCase(testId));
	}
	
}
