package moh.adp.db.renewalservice;

import moh.adp.db.common.TestOutcome;

public class TestResult {
	private TestOutcome outcome;
	private String message;
	
	public TestResult() {
		
	}	
	public TestResult(TestOutcome outcome, String message) {
		super();
		this.outcome = outcome;
		this.message = message;
	}
	public TestOutcome getOutcome() {
		return outcome;
	}
	public void setOutcome(TestOutcome outcome) {
		this.outcome = outcome;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
