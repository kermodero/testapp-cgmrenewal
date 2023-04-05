package moh.adp.test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import moh.adp.model.claim.Claim;
//import moh.adp.rest.exception.ADPRestException;
import moh.adp.xml.model.XmlForm;
import moh.adp.xml.model.communication.v202301.CAForm1;
import moh.adp.xml.model.communication.v202301.CAObjectFactory;
import moh.adp.xml.model.me.v202301.MEForm1;
import moh.adp.xml.model.me.v202301.MEObjectFactory;

public class TestGenerator {
	private Translator translator;
	
	public TestGenerator(){
		translator = new Translator();
	}
	
	public void generate(String deviceCategory, Claim claim) throws JAXBException {
/*
		try {
			DeviceCategory dc = DeviceCategory.valueOf(deviceCategory);
			Marshaller marshaller = JAXBContext.newInstance(Class.forName("moh.adp.xml.model.communication." + dc.name() + "ObjectFactory")).createMarshaller();
			java.nio.file.Path path = Paths.get("C:/temp/" + dc.name() + "marshallingTest.xml");
			XmlForm form = (XmlForm) Class.forName(dc.name() + "Form1").newInstance();
			translator.translate(DeviceCategory.CA, claim, form);
			File file = path.toFile();
			marshaller.marshal(form, file);		
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	*/
		Marshaller marshaller= null;
		Path path;
		XmlForm form = null;
		DeviceCategory dc = DeviceCategory.valueOf(deviceCategory);
		switch (dc) {
		case CA:
			marshaller = JAXBContext.newInstance(CAObjectFactory.class).createMarshaller();
			form = new CAForm1();
			break;
		case ME:
			marshaller = JAXBContext.newInstance(MEObjectFactory.class).createMarshaller();
			form = new MEForm1();
			break;			
		default:
			//throw new ADPRestException("unrecognized DeviceCategory in TetGenerator " + deviceCategory, Status.BAD_REQUEST);			
		}
		path = Paths.get("C:/temp/" + deviceCategory.toUpperCase() + "marshallingTest.xml");
		translator.translate(dc, claim, form);
		File file = path.toFile();
		marshaller.marshal(form, file);		

	}

}
