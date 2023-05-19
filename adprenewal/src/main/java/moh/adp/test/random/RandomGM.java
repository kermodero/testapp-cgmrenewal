package moh.adp.test.random;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import moh.adp.db.common.TestDBException;
import moh.adp.model.claim.form.ClaimGlucoseMonitor;
import moh.adp.server.esubmission.xml.parser.util.XMLParserConsts;

public class RandomGM extends RandomClaim<ClaimGlucoseMonitor> {

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
		//set them all to YES
		setProperty(cg, "is" + method + "Yes", XMLParserConsts.YES);
	}

	private void setProperty(ClaimGlucoseMonitor conf, String setterName, String val) {
		try {
			Method method = ClaimGlucoseMonitor.class.getMethod(setterName, String.class);
			method.invoke(conf, val);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new TestDBException("Exception translating GM Claim", e);
		}	
	}



}
