package moh.adp.testapp.util;

public class ADAMTestException extends RuntimeException {
	private static final long serialVersionUID = 747403078475838783L;

	public ADAMTestException(String msg, Exception e) {
		super(msg, e);
	}

}
