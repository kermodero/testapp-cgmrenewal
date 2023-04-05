package moh.adp.test.brultest;

import moh.adp.db.common.TestOutcome;

public class BRULTestResult {
	private String message;
	private TestOutcome outcome;
	
	public BRULTestResult(String message, TestOutcome outcome) {
		super();
		this.message = message;
		this.outcome = outcome;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public TestOutcome getOutcome() {
		return outcome;
	}
	public void setOutcome(TestOutcome outcome) {
		this.outcome = outcome;
	}
	
}
