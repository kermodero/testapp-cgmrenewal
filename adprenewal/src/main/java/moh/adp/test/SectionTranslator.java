package moh.adp.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import moh.adp.model.claim.Claim;
import moh.adp.test.conversion.FieldCopy;

public abstract class SectionTranslator {

	/*
	 * Default assumption is that bean class determines which setter to call.
	 * e.g., if bean is Physician.class, then Form will have just one setter accepting a physician.
	 * 
	 * Where this does not hold - vendor1, 2 (others?) we will pass in a property name and map to class, to get round this.
	 * 
	 */	
	private Map<String, Class<?>> setterMappings; 	
	/*
	 *  NOTE: keys are properties (no 'get' prefix, lowercase); values are lists of getters
	 *  With Apache beanutils in FieldCopy, this is simplest.
	 */
	private Map<Class<?>, Map<String, List<String>>> beanFields; 
	
	public SectionTranslator(){
		beanFields = new HashMap<>();
		setterMappings = new HashMap<>();
	}

	protected void addField(Class<?> beanClass, String sinkProperty, String...srcProperties){
		Map<String, List<String>> fields = beanFields.get(beanClass);
		if (fields == null) {
			fields = new HashMap<>();
			beanFields.put(beanClass, fields);
		}
		fields.put(sinkProperty, asGetters(srcProperties));
	}
	
	protected void addBooleanField(Class<?> beanClass, String sinkProperty, String...srcProperties){
		Map<String, List<String>> fields = beanFields.get(beanClass);
		if (fields == null) {
			fields = new HashMap<>();
			beanFields.put(beanClass, fields);
		}
		fields.put(sinkProperty, asGettersWithBoolean(srcProperties));
	}

	private List<String> asGettersWithBoolean(String[] srcProperties) {
		List<String> properties = new ArrayList<>();
		properties.addAll(Arrays.asList(srcProperties));
		List<String> getters = new ArrayList<>();
		asBooleanGetter(getters, properties);
		return getters;
	}

	private void asBooleanGetter(List<String> getters, List<String> properties) {
		String propName = properties.get(0);
		properties.remove(0);
		if (properties.isEmpty()) //this is the last in the chain, so make it a boolean getter
			getters.add("is" + StringUtils.capitalize(propName));
		else { //we're still going through the list, so make it a normal getter and recur.
			getters.add("get" + StringUtils.capitalize(propName));
			asBooleanGetter(getters, properties);
		}
	}

	private String asGetter(String propName) {
		System.out.println("got getter " + "get" + StringUtils.capitalize(propName));
		return "get" + StringUtils.capitalize(propName);
	}

	private List<String> asGetters(String[] srcProperties) {
		List<String> getters = new ArrayList<>();
		for(String srcProperty : srcProperties)
			getters.add(asGetter(srcProperty));
		return getters;
	}
	
	protected <V> void populateAll(Class<?> beanClass, Claim claim, V v) {
		Map<String, List<String>> fields = beanFields.get(beanClass);
		if (fields == null)
			return;  //TODO should this be an exception - should this ever be correct?
		for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
			try {
				FieldCopy.copy(v, claim, entry.getKey(), entry.getValue());
			} catch (Exception e) {
				System.out.println("error copying field " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	protected <V> void populateAll(Class<?> beanClass, Claim claim, String getter, V v) {
		Map<String, List<String>> fields = beanFields.get(beanClass);
		if (fields == null)
			return;  //TODO should this be an exception - should this ever be correct?
		for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
			try {
				FieldCopy.copy(v, claim, entry.getKey(), entry.getValue());
			} catch (Exception e) {
				System.out.println("error copying field " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	protected void setClassMapping(String propertyName, Class<?> beanClass) {
		this.setterMappings.put(propertyName, beanClass);
	}
	
	protected <W> W translatePart(Class<?> clazz, Claim claim, W w) {
		populateAll(clazz, claim, w);		
		return w;
	}
	
	protected <W, X> W translatePart(Class<W> beanClass,  X x) {
		try {
			W w = beanClass.getConstructor().newInstance();
			if (x == null)
				return w;
			Map<String, List<String>> fields = beanFields.get(beanClass);
			if (fields == null)
				return null;  //TODO should this be an exception - should this ever be correct?
			for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
				try {
					FieldCopy.copy(w, x, entry.getKey(), entry.getValue());
				} catch (Exception e) {
					System.out.println("error copying field " + e.getMessage());
					e.printStackTrace();
				}
			}
			return w;			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
	
	protected <W, X> W translatePart(Class<?> beanClass, X x, W w) {
		Map<String, List<String>> fields = beanFields.get(beanClass);
		if (fields == null)
			return null;  //TODO should this be an exception - should this ever be correct?
		for (Map.Entry<String, List<String>> entry : fields.entrySet()) {
			try {
				FieldCopy.copy(w, x, entry.getKey(), entry.getValue());
			} catch (Exception e) {
				System.out.println("error copying field " + e.getMessage());
				e.printStackTrace();
			}
		}
		return w;
	}
	
	protected <W> W translatePart(Class<?> clazz, Claim claim, String getter, W w) {
		populateAll(clazz, claim, getter, w);		
		return w;
	}
		
}
