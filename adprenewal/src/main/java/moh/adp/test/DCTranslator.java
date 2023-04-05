package moh.adp.test;

import moh.adp.model.claim.Claim;
import moh.adp.xml.model.XmlForm;

public abstract class DCTranslator <T, U> extends SectionTranslator {
	private Section1Translator<T,U> s1tr = new Section1Translator<>();
	private Section3Translator<T,U> s3tr = new Section3Translator<>();
	private Section4Translator<T,U> s4tr = new Section4Translator<>();
	
	protected abstract void translate(T t, U u);
	protected abstract void translateSection2(T t, U u);

	protected void translateCommonSections(Claim claim, XmlForm form){
		s1tr.translateSection(claim, form);
		s3tr.translateSection(claim, form);
		s4tr.translateSection(claim, form);
	}

}
