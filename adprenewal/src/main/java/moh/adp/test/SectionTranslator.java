package moh.adp.test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import moh.adp.model.claim.Claim;
import moh.adp.test.conversion.FieldCopy;
import moh.adp.xml.model.gm.v202301.GMForm1;

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

	protected <U> void nullsToEmptyObjects(U u) {
		try {
			nullsToEmpties(u);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected <U> void nullsToEmpties(U u) throws Exception {
		for (Method m : u.getClass().getDeclaredMethods()) {
			if (!m.getName().startsWith("get") || m.getParameterCount() > 0)
				continue;
			processGetter(m, u);
		}
	}

	private <U> void processGetter(Method m, U u) throws Exception {
		Object o = m.invoke(u);
		if (o == null)
			processNull(m, u);
		else
			nullsToEmptyObjects(o);		
	}

	private <U> void processNull(Method m, U u ) throws Exception {
		Method setter = getSetter(m, u);
		if (setter == null || setter.getName().startsWith("setQ")) //TODO way too crude!
			return;
		Class<?> clazz = m.getReturnType();		
		Object obj = clazz.newInstance();
		setter.invoke(u, new Object[]{obj});		
		if (clazz.getPackage().getName().startsWith("moh"))
			nullsToEmptyObjects(obj); //recur
	}

	private <U> Method getSetter(Method getter, U u) {
		String setter = getter.getName();
		setter = "s" + setter.substring(1);
		try {
			return u.getClass().getDeclaredMethod(setter, new Class<?>[]{getter.getReturnType()});
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
	}
	
	protected void unsex(GMForm1 form) {
		form.getSection1().setSex(null);
	}
	
	protected void removeNonGM(GMForm1 form) {
		form.getSection3().getSig().setPayee(null);
		form.getSection4().setPrescriber(null);
		form.getSection4().setAuthorizer(null);
		form.getSection4().setClinicInfo(null);
		form.getSection4().setVendor2(null);
		form.getSection4().setRehabilitationAssessor(null);
		form.getSection4().setTherapist(null);
        form.getSection4().setEquipmentSpec   (null);
        form.getSection4().setProofOfDelivery (null);
        form.getSection4().setPagesAttachments(null);
        form.getSection4().setNoteToADP       (null);
        form.getSection4().setAudiologist     (null);	
        form.getSection4().getVendor().setInvoiceNo(null);
        form.getSection4().getVendor().setNonRegisterVendor(null);
	}
	
}
