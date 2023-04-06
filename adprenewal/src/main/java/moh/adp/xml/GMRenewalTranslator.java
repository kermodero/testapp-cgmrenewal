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
		return marshall(f, r);
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
		section2.setCertification(r.getCertification());
		section2.setQuestion1(r.getQuestion1());
		section2.setQuestion2(r.getQuestion2());
		section2.setQuestion3(r.getQuestion3());
		section2.setSignature(r.getSignature());
		section2.setSignDate(sdf.format(r.getSignDate()));
		section2.setSignedBy(r.getSignedBy());		
	}

	private String marshall(Form1 f, RenewalRecord r) {
		File file = getFile(r);
		JAXB.marshal(f, file);
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "yaddayadda";
	}
	
	private File getFile(RenewalRecord r) {
		try {
			return Files.createFile(Paths.get(r.getFileName())).toFile();
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
