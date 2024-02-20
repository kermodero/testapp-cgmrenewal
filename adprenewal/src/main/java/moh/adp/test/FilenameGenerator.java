package moh.adp.test;

import java.util.Random;

import moh.adp.xml.model.oxFirstTime.v202301.OxFirstTimeForm1;

public enum FilenameGenerator {
	INSTANCE;
	
	private Random random = new Random();

	private FilenameGenerator(){
		random.setSeed(System.currentTimeMillis());
	}
	
	public String getFileName(OxFirstTimeForm1 f, String fileName) {
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
