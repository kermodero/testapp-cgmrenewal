package moh.adp.db.renewalservice;

import java.util.Map;
import java.util.stream.IntStream;

import moh.adp.db.jpa.RenewalRecord;
import moh.adp.xml.RenewalTranslator;

//Old fashioned singleton because 
//injection is too convoluted
public class RecordGenerator {
	private static RecordGenerator rrg;

	private RecordGenerator(){
		
	}
	
	static {
		rrg = new RecordGenerator();
	}
	
	public static RecordGenerator instance(){
		return rrg;
	}

	public void generateRecords(RenewalRecord r, 
									Map<String, String> results,
									RenewalTranslator<RenewalRecord> translator) {
		if (r.getRandomRecords() == 0) 
			results.put(r.getFileName(), translator.translate(r));
		else 
			generateRandomRecords(r, results, translator);		
	}

	private void generateRandomRecords(RenewalRecord r, 
										Map<String, String> results,
										RenewalTranslator<RenewalRecord> translator) {
		IntStream.range(0, r.getRandomRecords()).forEach(i -> generateRandomRecord(r, results, translator));		
	}

	private void generateRandomRecord(RenewalRecord r, 
											Map<String, String> results,
											RenewalTranslator<RenewalRecord> translator) {



	}

	

	
	
	
	
}
