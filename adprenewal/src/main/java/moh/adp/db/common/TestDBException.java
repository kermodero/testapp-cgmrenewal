package moh.adp.db.common;

import moh.adp.common.exception.AdpException;

public class TestDBException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private Exception cause;
	private String message;
	
	public TestDBException(String msg, ReflectiveOperationException e) {
		this.cause = e;
		this.setMessage(msg);
	}

	public TestDBException(String msg, Exception e) {
		this.cause = e;
		this.message = msg;
	}

	public TestDBException(String msg) {
		this.message = msg;
	}

	public Exception getCause() {
		return cause;
	}

	public void setCause(Exception cause) {
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
