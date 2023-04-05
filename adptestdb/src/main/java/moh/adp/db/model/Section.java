package moh.adp.db.model;

import java.util.HashMap;
import java.util.Map;

import moh.adp.model.claim.Claim;

public abstract class Section {
	protected Map<String, Object> fields = new HashMap<>();
	public static String Y = "yes";
	public static String N = "no";
	public static String NA = "na";
	
	public Section (){

	}
	
	public Object get(String fieldName) {
		return fields.get(fieldName);
	}
	
	public Object addField(String fieldName, Object value) {
		return fields.put(fieldName, value);
	}
	
	protected void populateVariances(Claim claim) {

		
	}
	
	
}
