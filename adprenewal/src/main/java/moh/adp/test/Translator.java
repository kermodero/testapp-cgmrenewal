package moh.adp.test;

import java.util.HashMap;
import java.util.Map;

import moh.adp.test.translator.CATranslator;
import moh.adp.test.translator.GMTranslator;
import moh.adp.test.translator.METranslator;
import moh.adp.xml.model.XmlForm;

public class Translator {
	private Map<DeviceCategory, DCTranslator<?,?>> dcTranslators;
		
	public Translator(){
		dcTranslators = new HashMap<>();
		init();
	}

	private void init() {
		dcTranslators.put(DeviceCategory.CA,  new CATranslator ());
		dcTranslators.put(DeviceCategory.GM,  new GMTranslator ());		
	/*	dcTranslators.put(DeviceCategory.HD,  new HDTranslator ());
		dcTranslators.put(DeviceCategory.DS,  new DSTranslator ());
		dcTranslators.put(DeviceCategory.LP,  new LPTranslator ());
	*/
		dcTranslators.put(DeviceCategory.ME,  new METranslator ());
	/*
		dcTranslators.put(DeviceCategory.MI,  new MITranslator ());
		dcTranslators.put(DeviceCategory.MD,  new MDTranslator ());
		dcTranslators.put(DeviceCategory.OP,  new OPTranslator ());
		dcTranslators.put(DeviceCategory.OR,  new ORTranslator ());
		dcTranslators.put(DeviceCategory.OXF, new OXFTranslator());
		dcTranslators.put(DeviceCategory.OXR, new OXRTranslator());
		dcTranslators.put(DeviceCategory.PM,  new PMTranslator ());
		dcTranslators.put(DeviceCategory.RE,  new RETranslator ());
		dcTranslators.put(DeviceCategory.VA,  new VATranslator ());	*/
	}

	@SuppressWarnings("unchecked")
	public <T,U> void translate(DeviceCategory dc, T t, U u) {
		DCTranslator<T, U> dcTranslator = (DCTranslator<T, U>) dcTranslators.get(dc);
		dcTranslator.translate(t, u);
	}

	@SuppressWarnings("unchecked")
	public <T, U> void translateRandom(DeviceCategory dc, T form, U claim) {
		DCTranslator<T, U> dcTranslator = (DCTranslator<T, U>) dcTranslators.get(dc);
		dcTranslator.translate(form, claim);		
	}
	
}
