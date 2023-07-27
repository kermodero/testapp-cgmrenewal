package moh.adp.test.random;

import java.util.ArrayList;
import java.util.List;

import moh.adp.model.claim.form.ClaimOxygen;

public class RandomOXF extends RandomClaim<ClaimOxygen>{

	public static List<ClaimOxygen> generateOXF(int count) {
		RandomOXF roxf = new RandomOXF();
		List<ClaimOxygen> claims = new ArrayList<>();		
		for (int i=0; i<count; i++)
			claims.add(roxf.generate());
		return claims;
	}

	@Override
	public ClaimOxygen generate() {
		ClaimOxygen cg = new ClaimOxygen();
		populateDeviceSpecific(cg);
		populate(cg);
		return cg;
	}

	@Override
	public void populateDeviceSpecific(ClaimOxygen u) {
		// TODO Auto-generated method stub
		
	}

}
