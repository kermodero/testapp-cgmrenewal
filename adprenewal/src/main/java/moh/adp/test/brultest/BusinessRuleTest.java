package moh.adp.test.brultest;

import javax.inject.Singleton;

import moh.adp.common.exception.AdpException;
import moh.adp.db.common.TestOutcome;
import moh.adp.model.AdpArrayList;
import moh.adp.model.AdpBaseDO;
import moh.adp.model.claim.Claim;
import moh.adp.model.client.RpdbClient;
import moh.adp.model.message.OutcomeMessage;
import moh.adp.server.claim.ClaimParsingService;
import moh.adp.server.lookup.RpdbClientLookupService;

@Singleton
public class BusinessRuleTest {

	public BRULTestResult test(Claim claim) {
		try {
			RpdbClient rpdbClient = RpdbClientLookupService.getRpdbClient(claim.getClaimClient().getHealthNumber(), claim.getSignedDate());			
			System.out.println("client HN " + rpdbClient.getHealthNumber() + " " + rpdbClient.getDisplayShortName());
			String messagesBefore = retrieveMessages(claim);			
			//ClaimParsingService.autoAdjudicateClaim(claim, rpdbClient);
			String messagesAfter = retrieveMessages(claim);
			if (!messagesBefore.equals(messagesAfter)) {
				System.out.println("Before: \n" + messagesBefore);
				System.out.println("After: " + messagesAfter);
			} else {
				System.out.println("AutoAdjudicate good.");
			}			
			return new BRULTestResult("succeeded " + messagesAfter, TestOutcome.EXPECTED_OUTCOME);
		} catch (AdpException e) {
			e.printStackTrace();
			return new BRULTestResult(e.getMessage(), TestOutcome.UNEXPECTED_OUTCOME);
		}
	}

	private String retrieveMessages(Claim claim) {
		AdpArrayList msgs = claim.getClaimMessages();
		StringBuilder sb = new StringBuilder();
		for (AdpBaseDO abd : msgs) {
			String txt = ((OutcomeMessage)abd).getMessageTxt();
			sb.append("=> ");
			sb.append(txt);
			sb.append("\n");
		}
		return sb.toString();
	}
	
}
