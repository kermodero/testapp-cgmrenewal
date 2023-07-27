package moh.adp.testapp.rest.common;

import java.util.ArrayList;
import java.util.List;

import moh.adp.db.renewalservice.TestResult;

public class Result {
	public enum Outcome {
		SUCCESS,
		ERROR,
		INVALID_TEST,
		ADAM_EXCEPTION,
		SERVER_EXCEPTION,
		UNKNOWN_EXCEPTION,
		UNKNOWN
	};
	private List<String> messages;
	private Outcome outcome;
	private Object content;
	private TestResult testResult;
	
	public Result() {
		this.outcome = Outcome.UNKNOWN;
		messages = new ArrayList<>();
	}	
	public Result(String message, Outcome outcome) {
		super();
		messages = new ArrayList<>();
		this.outcome = outcome;
	}
	public Result(String message, TestResult tr, Outcome outcome) {
		this(message, outcome);
		this.testResult = tr;
	}
	public String getMessage() {
		if (messages.isEmpty())
			return "No message";
		return messages.get(0);
	}
	public void setMessage(String message) {
		messages.add(message);
	}
	public Outcome getOutcome() {
		return outcome;
	}
	public void setOutcome(Outcome outcome) {
		this.outcome = outcome;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void addMessage(String msg) {
		messages.add(msg);
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	public TestResult getTestResult() {
		return testResult;
	}
	public void setTestResult(TestResult testResult) {
		this.testResult = testResult;
	}

}
