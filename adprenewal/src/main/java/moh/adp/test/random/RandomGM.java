package moh.adp.test.random;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import moh.adp.db.common.TestDBException;
import moh.adp.model.claim.form.ClaimGlucoseMonitor;


public class RandomGM extends RandomClaim<ClaimGlucoseMonitor> {

	public static List<ClaimGlucoseMonitor> generateGM(int count) {
		RandomGM rgm = new RandomGM();
		List<ClaimGlucoseMonitor> claims = new ArrayList<>();		
		for (int i=0; i<count; i++)
			claims.add(rgm.generate());
		return claims;
	}
	
	@Override
	public ClaimGlucoseMonitor generate() {
		ClaimGlucoseMonitor cg = new ClaimGlucoseMonitor();
		populateDeviceSpecific(cg);
		populate(cg);
		return cg;
	}

	@Override
	public void populateDeviceSpecific(ClaimGlucoseMonitor cg) {
		setQuestion(cg, "A1");
		setQuestion(cg, "A2");
		setQuestion(cg, "A3");
		setQuestion(cg, "A4");
		setQuestion(cg, "A5");
		setQuestion(cg, "A6");
		setQuestion(cg, "A7");
		setQuestion(cg, "A8");	
	}
	
	private void setQuestion(ClaimGlucoseMonitor cg, String method) {
		//just set them all to YES
		setProperty(cg, "set" + method + "Yes", Boolean.TRUE);
	}

	private void setProperty(ClaimGlucoseMonitor conf, String setterName, Boolean val) {
		try {
			Method method = ClaimGlucoseMonitor.class.getMethod(setterName, boolean.class);
			method.invoke(conf, val);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new TestDBException("Exception translating GM Claim", e);
		}	
	}

}
