package moh.adp.test.conversion;

import java.util.List;

public class FieldCopyException extends RuntimeException {
	private static final long serialVersionUID = 2495030387206977789L;
	private Class<?> srcType;
	private Class<?> sinkType;
	private String sinkField;
	private List<String> srcFields;
		
	public FieldCopyException(Class<?> srcType, Class<?> sinkType, String sinkField, List<String> srcFields, String msg) {
		super(msg);
		this.srcType = srcType;
		this.sinkType = sinkType;
		this.sinkField = sinkField;
		this.srcFields = srcFields;
	}
	
	public Class<?> getSrcType() {
		return srcType;
	}
	public Class<?> getSinkType() {
		return sinkType;
	}
	public String getSinkField() {
		return sinkField;
	}
	public List<String> getSrcFields() {
		return srcFields;
	}
	
}
