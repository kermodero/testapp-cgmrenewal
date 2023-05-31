package moh.adp.test;

import java.util.Random;

import moh.adp.model.claim.Claim;
import moh.adp.xml.model.XmlForm;
import moh.adp.xml.model.renewal.gm.v202311.Form1;

/*
 * Device category translator. Abstract Superclass for all 
 * concrete device category translators.
 * 
 * Converts an ADAM Claim object into a suitable XMLForm
 * This Form can then be rendered into XML by JAXB
 * (The reverse of the parsing done when processing e-submissions)
 */
public abstract class DCTranslator <T, U> extends SectionTranslator {
	protected Random random = new Random();
	private Section1Translator<T,U> s1tr = new Section1Translator<>();
	private Section3Translator<T,U> s3tr = new Section3Translator<>();
	private Section4Translator<T,U> s4tr = new Section4Translator<>();
	
	public DCTranslator() {
		random.setSeed(System.currentTimeMillis());
	}
	
	protected abstract void translate(T t, U u);
	protected abstract void translateSection2(T t, U u);

	protected void translateCommonSections(Claim claim, XmlForm form){
		s1tr.translateSection(claim, form);
		s3tr.translateSection(claim, form);
		s4tr.translateSection(claim, form);
	}

	protected String getFileName(Form1 f, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(fileName);
		sb.append("-");
		sb.append("VCX");
		sb.append("-");		
		sb.append(f.getForm().getSection4().getVendor().getAdpVendorRegNo());
		sb.append("-");
		sb.append(Math.abs(random.nextInt()));//fake the edt ref #
		sb.append("-");
		sb.append(System.currentTimeMillis() / 1000L); //unix time stamp
		sb.append(".xml");
		return sb.toString();
	}
	
}
