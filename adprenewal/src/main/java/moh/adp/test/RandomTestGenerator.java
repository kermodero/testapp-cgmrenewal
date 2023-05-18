package moh.adp.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import moh.adp.db.common.TestDBException;
import moh.adp.model.claim.Claim;
import moh.adp.model.claim.form.ClaimCommunicationAid;
import moh.adp.model.claim.form.ClaimGlucoseMonitor;
import moh.adp.model.claim.form.ClaimMaxExtraoral;
//import moh.adp.rest.exception.ADPRestException;
import moh.adp.xml.model.XmlForm;
import moh.adp.xml.model.communication.v202301.CAForm1;
import moh.adp.xml.model.communication.v202301.CAObjectFactory;
import moh.adp.xml.model.gm.v202301.GMForm1;
import moh.adp.xml.model.gm.v202301.GMObjectFactory;
import moh.adp.xml.model.me.v202301.MEForm1;
import moh.adp.xml.model.me.v202301.MEObjectFactory;

public class RandomTestGenerator {
	private Translator translator;
	public Random random = new Random();
	
	public RandomTestGenerator(){
		translator = new Translator();
	}

	public void generate(String deviceCategory, Map<String, String> results, int fileNumber) throws JAXBException {
		DeviceCategory dc = DeviceCategory.valueOf(deviceCategory);
		Triple<Marshaller, XmlForm, ? extends Claim> jaxb = getJaxbRequirements(dc);
		
		//randomize (populate) the claim!
		
		translator.translateRandom(dc, jaxb.getRight(), jaxb.getMiddle());
		String xml = marshal(jaxb.getLeft(), jaxb.getMiddle());
		String fileName = dc + "Test" + fileNumber;
		results.put(getFileName(jaxb.getMiddle(), fileName), xml);
	}

	private Triple<Marshaller, XmlForm, ? extends Claim> getJaxbRequirements(DeviceCategory dc) throws JAXBException {
		switch (dc) {
		case CA:
			return Triple.of(JAXBContext.newInstance(CAObjectFactory.class).createMarshaller(), new CAForm1(), new ClaimCommunicationAid());
		case ME:
			return Triple.of(JAXBContext.newInstance(MEObjectFactory.class).createMarshaller(), new MEForm1(), new ClaimMaxExtraoral());
		case GM:
			return Triple.of(JAXBContext.newInstance(GMObjectFactory.class).createMarshaller(), new GMForm1(), new ClaimGlucoseMonitor());
		default:
			//throw new ADPRestException("unrecognized DeviceCategory in TetGenerator " + deviceCategory, Status.BAD_REQUEST);			
		}
		return null;
	}

	private String marshal(Marshaller marshaller, XmlForm f) {
		File file = getTempFile();
		JAXB.marshal(f, file);
		try {
			String content = new String(Files.readAllBytes(file.toPath()));
			return content;
		} catch (IOException e) {
			e.printStackTrace();
			throw new TestDBException("Exception marshalling file", e);
		}
	}    
	
	protected File getTempFile() {
		UUID uid = UUID.randomUUID();
		return Paths.get("C:/TEMP/" + uid).toFile();
	}
		
	protected String getFileName(XmlForm f, String fileName) {
		StringBuilder sb = new StringBuilder();
		sb.append(fileName);
		sb.append("-");
		sb.append("VCX");
		sb.append("-");		
		sb.append(f.getSection4().getVendor().getAdpVendorRegNo());
		sb.append("-");
		sb.append(Math.abs(random.nextInt()));//fake the edt ref #
		sb.append("-");
		sb.append(System.currentTimeMillis() / 1000L); //unix time stamp
		sb.append(".xml");
		return sb.toString();
	}
}
