package moh.adp.xml;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.List;

import javax.xml.bind.JAXB;

import moh.adp.common.DeviceCategory;
import moh.adp.db.jpa.RenewalRecord;
import moh.adp.xml.model.renewal.gm.v202311.Form1;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section1;
import moh.adp.xml.model.renewal.gm.v202311.Form1.Form.Section2;

public class GMRenewalTranslator extends RenewalTranslator<RenewalRecord> {
	private static final String FORM_TYPE="RENEWAL";
	int i=0;
	
	@Override
	public String translate(RenewalRecord r) {
		Form1 f = new Form1();
		populate(f, r);
		return marshall(f);
	}

	private void populate(Form1 f1, RenewalRecord r) {
		Form f = getForm(f1);
		f.setDeviceCategory(getDeviceCategory().getCategoryShortName());
		f.setFormType(FORM_TYPE);
		f.setVersionNumber("202311");
		populateSection1(f, r);
		populateSection2(f, r);
		populateSection3(f, r);
		populateSection4(f, r);
	}

	private void populateSection2(Form f, RenewalRecord r) {
		f.setSection2(new Form1.Form.Section2());
		Section2 section2 = f.getSection2();
		section2.setCertification("valid");
		section2.setQuestion1("t");
		section2.setQuestion2("t");
		section2.setQuestion3("f");
		section2.setSignature("Blah, blah");
		section2.setSignDate("03/30/2024");
		section2.setSignedBy("Mr Ganite");		
	}

	private String marshall(Form1 f) {
		File file = getFile(f);
		JAXB.marshal(f, file);
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "yaddayadda";
	}
	
	private File getFile(Form1 f) {
		String xml = "C:/TEMP/rwk_testing_" + ++i + ".xml";
		try {
			return Files.createFile(Paths.get(xml)).toFile();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public DeviceCategory getDeviceCategory() {
		return DeviceCategory.GM;
	}

	private Form getForm(Form1 f1) {
		f1.setForm(new Form1.Form());
		return f1.getForm();
	}

}
