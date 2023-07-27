package moh.adp.xml;

import moh.adp.db.jpa.RenewalRecord;

public class RenewalTranslatorFactory {
	
	public static RenewalTranslator<?> getRenewalTranslator(RenewalRecord r) {
		boolean isRandom = (r.getRandomRecords() > 0);
		if (isRandom)
			return getRandomRenewalTranslator(r);
		String dc = r.getDeviceCategory();
		switch (dc) {
		case "GM" :
			return new GMRenewalTranslator();
		} 
		
		return null;
	}	
	
	private static RenewalTranslator<?> getRandomRenewalTranslator(RenewalRecord r) {
		String dc = r.getDeviceCategory();
		switch (dc) {
		case "GM" :
			return new GMRenewalRandomTranslator();
		}
		return null;
	}

	public static RenewalTranslator<?> getRenewalTranslator(String dc) {
		switch (dc) {
		case "GM" :
			return new GMRenewalTranslator();
		}
		return null;
	}		
	
}
