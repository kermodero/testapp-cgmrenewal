package moh.adp.db.renewalservice;

import java.util.List;
import java.util.Set;

import moh.adp.db.common.TestOutcome;

public class TestResult {
	private TestOutcome outcome;
	private String message;
	private String testId;
	private Set<String> fileNames;
	
	public TestResult() {
		
	}	
	public TestResult(TestOutcome outcome, String message) {
		super();
		this.outcome = outcome;
		this.message = message;
	}
	public TestResult(String testId, TestOutcome outcome, String message) {
		this(outcome, message);
		this.testId = testId;
	}
	public TestResult(String testId, Set<String> fileNames, TestOutcome outcome, String message) {
		this(testId, outcome, message);
		this.fileNames = fileNames;
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
	public Set<String> getFileNames() {
		return fileNames;
	}
	public void setFileNames(Set<String> fileNames) {
		this.fileNames = fileNames;
	}
	public String getTestId() {
		return testId;
	}
	public void setTestId(String testId) {
		this.testId = testId;
	}
	
}
