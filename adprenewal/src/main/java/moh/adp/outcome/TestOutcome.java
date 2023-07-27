package moh.adp.outcome;

import java.util.ArrayList;
import java.util.List;

import moh.adp.db.jpa.RenewalErrorOutcome;
import moh.adp.model.claim.eSubmission.ViewEClaimResult;

public class TestOutcome<U, V> {
	private List<U> expectedOutcomes = new ArrayList<>();
	private Class<?> expectedOutcomeType;
	private List<V> actualOutcomes = new ArrayList<>();
	private Class<?> actualOutcomeType;	

	public TestOutcome(List<U> expectedOutcomes, Class<?> class1, List<V> actualOutcomes, Class<?> class2) {
		expectedOutcomes.addAll(expectedOutcomes);
		actualOutcomes.addAll(actualOutcomes);
		expectedOutcomeType = class1;
		actualOutcomeType = class2;
	}

	public List<U> getExpectedOutcomes() {
		return expectedOutcomes;
	}

	public void setExpectedOutcomes(List<U> outcomes) {
		this.expectedOutcomes = outcomes;
	}

	public List<V> getActualOutcomes() {
		return actualOutcomes;
	}

	public void setActualOutcomes(List<V> actualOutcomes) {
		this.actualOutcomes = actualOutcomes;
	}

/*	public void addAll(TestOutcome positiveOutcomes) {
		outcomes.addAll(positiveOutcomes.getOutcomes());
	}*/
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EXPECTED:");
		expectedOutcomes.forEach(eo -> {
			sb.append("\n\t");
			sb.append(((RenewalErrorOutcome)eo).toString());
			});
		sb.append("ACTUAL:");
		actualOutcomes.forEach(eo -> {
			sb.append("\n\t");
			sb.append(((ViewEClaimResult)eo).toString());
			});		
		return sb.toString();
	}
	
	
}
