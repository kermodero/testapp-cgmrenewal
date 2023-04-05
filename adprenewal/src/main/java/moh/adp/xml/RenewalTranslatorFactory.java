package moh.adp.xml;

import moh.adp.db.jpa.RenewalRecord;

public class RenewalTranslatorFactory {
	
	public static RenewalTranslator<?> getRenewalTranslator(RenewalRecord r) {
		String dc = r.getDeviceCategory();
		switch (dc) {
		case "GM" :
			return new GMRenewalTranslator();
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
