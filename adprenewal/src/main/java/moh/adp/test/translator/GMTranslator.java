package moh.adp.test.translator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.tuple.Pair;

import moh.adp.db.common.TestDBException;
import moh.adp.model.claim.form.ClaimGlucoseMonitor;
import moh.adp.server.esubmission.xml.parser.util.XMLParserConsts;
import moh.adp.test.DCTranslator;
import moh.adp.xml.model.common.Section1;
import moh.adp.xml.model.common.Section3;
import moh.adp.xml.model.common.Section4;
import moh.adp.xml.model.gm.v202301.GMForm1;
import moh.adp.xml.model.gm.v202301.GMForm1.Form.Section2.Confirmation;


public class GMTranslator extends DCTranslator<ClaimGlucoseMonitor, GMForm1> {

	public Pair<String, GMForm1> translate(ClaimGlucoseMonitor claim, String fileName) {
		GMForm1 form = new GMForm1();
		translate(claim, form);
		return Pair.of(getFileName(form, fileName), form);
	}
	
	@Override
	public void translate(ClaimGlucoseMonitor claim, GMForm1 form) {
		initialiseForm(form);
		form.getForm().setDeviceCategory("GM");
		form.getForm().setVersionNumber("202301");
		translateCommonSections(claim, form);
		translateSection2(claim, form);
		nullsToEmptyObjects(form);
		unsex(form);
		removeNonGM(form);
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
		Confirmation conf = new GMForm1.Form.Section2.Confirmation();
		form.getForm().getSection2().setConfirmation(conf);
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
		} catch (NoSuchMethodException e) {
			System.out.println("no such method " + setterName);	
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { 
			System.out.println("Exception trying to invoke " + setterName + "on " + val);
			e.printStackTrace();
			throw new TestDBException("Exception translating GM Claim", e);
		} catch (Exception e) {
			System.out.println("something has gone wrong " + conf + " " + val + " " + setterName);
		}
	}

	private boolean getProperty(ClaimGlucoseMonitor claimGM, String methodName) {
		try {
			Method method = ClaimGlucoseMonitor.class.getMethod(methodName);
			return (Boolean) method.invoke(claimGM);
		} catch (NoSuchMethodException e) {
			System.out.println("no such method " + methodName);	
			return false;
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new TestDBException("Exception translating GM Claim", e);
		}
	}
	
	protected String getFileName(GMForm1 f, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(fileName);
		sb.append("-");
		sb.append("VCX");
		sb.append("-");		
		sb.append(f.getForm().getSection4().getVendor().getAdpVendorRegNo());
		sb.append("-");
		sb.append(Math.abs(random.nextInt())); //fake the edt ref #
		sb.append("-");
		sb.append(System.currentTimeMillis() / 1000L); //unix time stamp
		sb.append(".xml");
		return sb.toString();
	}	
	
}
