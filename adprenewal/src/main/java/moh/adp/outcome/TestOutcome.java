package moh.adp.outcome;

import java.util.ArrayList;
import java.util.List;

public class TestOutcome {
	private List<RecordOutcome> outcomes = new ArrayList<>();

	public List<RecordOutcome> getOutcomes() {
		return outcomes;
	}

	public void setOutcomes(List<RecordOutcome> outcomes) {
		this.outcomes = outcomes;
	}

	public void addAll(TestOutcome positiveOutcomes) {
		outcomes.addAll(positiveOutcomes.getOutcomes());
	}
	
}
