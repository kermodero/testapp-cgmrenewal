package moh.adp.testapp.rest.common;

public enum TestScope {
	ALL,
	PAYMENT,
	OXYGEN,
	RENEWAL,
	ECLAIM,
	INVOICE,
	CCAC,
	INVALID;

	public static TestScope get(String scope) {
		if (scope == null)
			return INVALID;
		try {
			return TestScope.valueOf(scope.trim().toUpperCase());
		} catch (Exception e) {
			return INVALID;
		}
	}
}
