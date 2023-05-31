package moh.adp.test.random;

import java.util.ArrayList;
import java.util.List;

import moh.adp.model.claim.Claim;

public class RandomClaimGenerator {

	@SuppressWarnings("unchecked")
	public <U extends Claim> List<U> generateClaims(String deviceCode, int count) {
		switch(deviceCode) {
		case "GM":
			return (List<U>) RandomGM.generateGM(count);
		default:
			return new ArrayList<>();
		}
	}

}
