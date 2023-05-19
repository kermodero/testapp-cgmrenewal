package moh.adp.test.translator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import moh.adp.common.util.EqualUtil;
import moh.adp.db.common.TestDBException;
import moh.adp.model.claim.form.ClaimGlucoseMonitor;
import moh.adp.server.esubmission.xml.parser.util.XMLParserConsts;
import moh.adp.test.DCTranslator;
import moh.adp.xml.model.common.Section1;
import moh.adp.xml.model.common.Section3;
import moh.adp.xml.model.common.Section4;
import moh.adp.xml.model.gm.v202301.GMForm1;
import moh.adp.xml.model.gm.v202301.GMForm1.Form.Section2;
import moh.adp.xml.model.gm.v202301.GMForm1.Form.Section2.Confirmation;

public class GMTranslator extends DCTranslator<ClaimGlucoseMonitor, GMForm1> {

	@Override
	public void translate(ClaimGlucoseMonitor claim, GMForm1 form) {
		initialiseForm(form);
		form.getForm().setDeviceCategory("GM");
		form.getForm().setVersionNumber("202311");
		translateCommonSections(claim, form);
		translateSection2(claim, form);
	}

	private void initialiseForm(GMForm1 form) {
		GMForm1.Form f = new GMForm1.Form();
		f.setSection1(new Section1());
		f.setSection2(new GMForm1.Form.Section2());		
		f.setSection3(new Section3());
		f.setSection4(new Section4());
		form.setForm(f);
	}

	//See GMSection2Parser (reversing logic)
	protected void translateSection2(ClaimGlucoseMonitor claimGM, GMForm1 form) {
		//Ad hoc parsing - very ugly code in ADAM, here.
		Confirmation conf = form.getForm().getSection2().getConfirmation();
		setQuestion(claimGM, "A1", conf, "setQ1");
		setQuestion(claimGM, "A2", conf, "setQ2A");
		setQuestion(claimGM, "A3", conf, "setQ2Bi");
		setQuestion(claimGM, "A4", conf, "setQ2Bii");
		setQuestion(claimGM, "A5", conf, "setQ2Biii");
		setQuestion(claimGM, "A6", conf, "setQ3A");
		setQuestion(claimGM, "A7", conf, "setQ3B");
		setQuestion(claimGM, "A8", conf, "setQ3C");		
	}	

	private void setQuestion(ClaimGlucoseMonitor claimGM, String method, Confirmation conf, String setterName) {
		if (getProperty(claimGM, "is" + method + "Yes"))
			setProperty(conf, setterName, XMLParserConsts.YES);
		else if (getProperty(claimGM, "is" + method + "Yes"))
			setProperty(conf, setterName, XMLParserConsts.NO);
		else if (getProperty(claimGM, "is" + method + "Na"))
			setProperty(conf, setterName, XMLParserConsts.NA);
	}
	
	private void setProperty(Confirmation conf, String setterName, String val) {
		try {
			Method method = Confirmation.class.getMethod(setterName, String.class);
			method.invoke(conf, val);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new TestDBException("Exception translating GM Claim", e);
		}	
	}

	private boolean getProperty(ClaimGlucoseMonitor claimGM, String methodName) {
		try {
			Method method = ClaimGlucoseMonitor.class.getMethod(methodName);
			return (Boolean) method.invoke(claimGM);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new TestDBException("Exception translating GM Claim", e);
		}
	}
	
}
