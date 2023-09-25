package moh.adp.test.translator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.tuple.Pair;

import moh.adp.db.common.TestDBException;
import moh.adp.model.claim.form.ClaimOxygen;

import moh.adp.test.DCTranslator;
import moh.adp.xml.model.common.Section1;
import moh.adp.xml.model.common.Section3;
import moh.adp.xml.model.common.Section4;
import moh.adp.xml.model.oxFirstTime.v202301.*;


public class OXFTranslator extends DCTranslator<ClaimOxygen, OxFirstTimeForm1> {

	public Pair<String, OxFirstTimeForm1> translate(ClaimOxygen c, String fileName) {
		OxFirstTimeForm1 form = new OxFirstTimeForm1();
		translate(c, form);
		return Pair.of(getFileName(form, fileName), form);
	}
	
	@Override
	public void translate(ClaimOxygen claim, OxFirstTimeForm1 form) {
		initialiseForm(form);
		form.getForm().setDeviceCategory("OXF");
		form.getForm().setVersionNumber("202301");
		translateCommonSections(claim, form);
		translateSection2(claim, form);
		nullsToEmptyObjects(form);
		unsex(form);
		removeNonOXF(form);
	}

	private void initialiseForm(OxFirstTimeForm1 form) {
		OxFirstTimeForm1.Form f = new OxFirstTimeForm1.Form();
		f.setSection1(new Section1());
		f.setSection2(new OxFirstTimeForm1.Form.Section2());		
		f.setSection3(new Section3());
		f.setSection4(new Section4());
		form.setForm(f);
	}

	//See GMSection2Parser (reversing logic)
	protected void translateSection2(ClaimOxygen claimGM, OxFirstTimeForm1 form) {

	}	

	private void removeNonOXF(OxFirstTimeForm1 form) {
		form.getSection4().setAudiologist(null);
		form.getSection4().setClinicInfo(null);
		form.getSection4().setDiabetesProgram(null);
		form.getSection4().setEquipmentSpec(null);
		form.getSection4().setNoteToADP(null);
		form.getSection4().setPagesAttachments(null);
		form.getSection4().setPrescriber(null);
		form.getSection4().setProofOfDelivery(null);
		form.getSection4().setRehabilitationAssessor(null);
		form.getSection4().setVendor2(null);
	}

	private boolean getProperty(ClaimOxygen claimGM, String methodName) {
		try {
			Method method = ClaimOxygen.class.getMethod(methodName);
			return (Boolean) method.invoke(claimGM);
		} catch (NoSuchMethodException e) {
			System.out.println("no such method " + methodName);	
			return false;
		} catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			throw new TestDBException("Exception translating GM Claim", e);
		}
	}
	
	protected String getFileName(OxFirstTimeForm1 f, String fileName) {
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

	protected void unsex(OxFirstTimeForm1 form) {
		form.getSection1().setSex(null);
	}
}
