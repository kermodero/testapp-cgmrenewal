package moh.adp.test.translator;

import moh.adp.common.Constants;
import moh.adp.model.claim.form.ClaimCommunicationAid;
import moh.adp.test.DCTranslator;
import moh.adp.xml.model.common.Section1;
import moh.adp.xml.model.common.Section3;
import moh.adp.xml.model.common.Section4;
import moh.adp.xml.model.communication.v202301.CAForm1;
import moh.adp.xml.model.communication.v202301.CAForm1.Form.Section2;
import moh.adp.xml.model.communication.v202301.CAForm1.Form.Section2.Accessories;
import moh.adp.xml.model.communication.v202301.CAForm1.Form.Section2.CommunicationAids;
import moh.adp.xml.model.communication.v202301.CAForm1.Form.Section2.IntegratedSystems;
import moh.adp.xml.model.communication.v202301.CAForm1.Form.Section2.Other;
import moh.adp.xml.model.communication.v202301.CAForm1.Form.Section2.WritingAids;

public class CATranslator extends DCTranslator<ClaimCommunicationAid, CAForm1> {
	private static String PURCHASE = "purchase";
	private static String LEASE = "lease";

	@Override
	public void translate(ClaimCommunicationAid claim, CAForm1 form) {
		initialiseForm(form);
		form.getForm().setDeviceCategory("CA");
		form.getForm().setVersionNumber("123");
		translateCommonSections(claim, form);
		translateSection2(claim, form);
	}

	private void initialiseForm(CAForm1 form) {
		CAForm1.Form f = new CAForm1.Form();
		f.setSection1(new Section1());
		f.setSection2(new CAForm1.Form.Section2());		
		f.setSection3(new Section3());
		f.setSection4(new Section4());
		form.setForm(f);
	}

	protected void translateSection2(ClaimCommunicationAid claim, CAForm1 form) {
		Class<CAForm1.Form.Section2> clazz = CAForm1.Form.Section2.class;
		addField(clazz, "leaseBuyouts", "leaseBuyoutInd");
		addField(clazz, "modelDescription", "modelDesc");
		addField(clazz, "reason", "claimReasonCd"); 
		addBooleanField(clazz, "replacementChange", "replacement");
		addBooleanField(clazz, "replacementPhysical", "replacementReasonPhysicalGrowth");
		addBooleanField(clazz, "replacementNormal", "replacementReasonNormalWear");
		populateAll(clazz, claim, form.getForm().getSection2());
		
		initDiagnosis();
		populateAccessories(form.getForm().getSection2(), claim);
		populateCommunicationAids(form.getForm().getSection2(), claim);
		populateIntegratedSystems(form.getForm().getSection2(), claim);
		populateWritingAids(form.getForm().getSection2(), claim);
		populateOther(form.getForm().getSection2(), claim);
		
	}

	private void populateOther(Section2 section2, ClaimCommunicationAid claim) {
		Other other = new Other();
		section2.setOther(other);
		
		other.setFeeSetup(Constants.Y.equalsIgnoreCase(claim.getCacc5a().getSelectedInd()) ? PURCHASE : null);
		
		other.setFeeShipping(Constants.Y.equalsIgnoreCase(claim.getCacc5b1().getSelectedInd()) ? LEASE : null);
		other.setFeeShipping(Constants.Y.equalsIgnoreCase(claim.getCacc5b2().getSelectedInd()) ? PURCHASE : null);
		
	}

	private void populateWritingAids(Section2 section2, ClaimCommunicationAid claim) {
		WritingAids was = new WritingAids();
		section2.setWritingAids(was);
		
		was.setSimple         (Constants.Y.equalsIgnoreCase(claim.getCaia2a().getSelectedInd()) ? PURCHASE : null);
		was.setKeyguardSimple (Constants.Y.equalsIgnoreCase(claim.getCaia2b().getSelectedInd()) ? PURCHASE : null);
		was.setCarryCasesimple(Constants.Y.equalsIgnoreCase(claim.getCaia2c().getSelectedInd()) ? PURCHASE : null);

		was.setStationary     (Constants.Y.equalsIgnoreCase(claim.getCacc2a1().getSelectedInd()) ? LEASE : null);
		was.setStationary     (Constants.Y.equalsIgnoreCase(claim.getCacc2a2().getSelectedInd()) ? PURCHASE : null);

		was.setPortable	     (Constants.Y.equalsIgnoreCase(claim.getCacc2b1().getSelectedInd()) ? LEASE : null);
		was.setPortable	     (Constants.Y.equalsIgnoreCase(claim.getCacc2b2().getSelectedInd()) ? PURCHASE : null);

		was.setKeyguard	     (Constants.Y.equalsIgnoreCase(claim.getCacc2c1().getSelectedInd()) ? LEASE : null);
		was.setKeyguard	     (Constants.Y.equalsIgnoreCase(claim.getCacc2c1().getSelectedInd()) ? PURCHASE : null);

		was.setCarryCase	     (Constants.Y.equalsIgnoreCase(claim.getCacc2d1().getSelectedInd()) ? LEASE : null);
		was.setCarryCase	     (Constants.Y.equalsIgnoreCase(claim.getCacc2d2().getSelectedInd()) ? PURCHASE : null);

		was.setAdapSoftware   (Constants.Y.equalsIgnoreCase(claim.getCacc2e1().getSelectedInd()) ? LEASE : null);
		was.setAdapSoftware   (Constants.Y.equalsIgnoreCase(claim.getCacc2e2().getSelectedInd()) ? PURCHASE : null);

		was.setKeyboard	     (Constants.Y.equalsIgnoreCase(claim.getCacc2f1().getSelectedInd()) ? LEASE : null);
		was.setKeyboard	     (Constants.Y.equalsIgnoreCase(claim.getCacc2f2().getSelectedInd()) ? PURCHASE : null);

		was.setMouse	     (Constants.Y.equalsIgnoreCase(claim.getCacc2g1().getSelectedInd()) ? LEASE : null);
		was.setMouse	     (Constants.Y.equalsIgnoreCase(claim.getCacc2g2().getSelectedInd()) ? PURCHASE : null);

		was.setKbMouse	     (Constants.Y.equalsIgnoreCase(claim.getCacc2h1().getSelectedInd()) ? LEASE : null);
		was.setKbMouse	     (Constants.Y.equalsIgnoreCase(claim.getCacc2h2().getSelectedInd()) ? PURCHASE : null);

		was.setHardware       (Constants.Y.equalsIgnoreCase(claim.getCacc2i1().getSelectedInd()) ? LEASE : null);
		was.setHardware       (Constants.Y.equalsIgnoreCase(claim.getCacc2i2().getSelectedInd()) ? PURCHASE : null);
		
	}

	private void populateIntegratedSystems(Section2 section2, ClaimCommunicationAid claim) {
		IntegratedSystems iss = new IntegratedSystems();
		section2.setIntegratedSystems(iss);
		
		iss.setIntegratedSystems(Constants.Y.equalsIgnoreCase(claim.getCacc3a().getSelectedInd()) ? LEASE : null);
		iss.setIntegratedSystems(Constants.Y.equalsIgnoreCase(claim.getCacc3b().getSelectedInd()) ? PURCHASE : null);
	}

	private void populateCommunicationAids(Section2 section2, ClaimCommunicationAid claim) {
		CommunicationAids cas = new CommunicationAids();
		section2.setCommunicationAids(cas);
		
		cas.setElectrolarynx    (Constants.Y.equalsIgnoreCase(claim.getCaia1a().getSelectedInd()) ? PURCHASE : null);
		cas.setVoiceAmplifier   (Constants.Y.equalsIgnoreCase(claim.getCaia1b().getSelectedInd()) ? PURCHASE : null);
		cas.setVoiceRestoration (Constants.Y.equalsIgnoreCase(claim.getCaia1c().getSelectedInd()) ? PURCHASE : null);
		cas.setQuickMesgdevice  (Constants.Y.equalsIgnoreCase(claim.getCaia1d().getSelectedInd()) ? PURCHASE : null);
		cas.setSimpleSGD	    (Constants.Y.equalsIgnoreCase(claim.getCaia1e().getSelectedInd()) ? PURCHASE : null);
		cas.setDisplaySoftware  (Constants.Y.equalsIgnoreCase(claim.getCaia1f().getSelectedInd()) ? PURCHASE : null);
		cas.setKeyguardSimpleSGD(Constants.Y.equalsIgnoreCase(claim.getCaia1g().getSelectedInd()) ? PURCHASE : null);
		cas.setCarrySimpleSGD   (Constants.Y.equalsIgnoreCase(claim.getCaia1h().getSelectedInd()) ? PURCHASE : null);
		cas.setDisplayBoard     (Constants.Y.equalsIgnoreCase(claim.getCacc1a().getSelectedInd()) ? PURCHASE : null);

		cas.setSpeechDevice     (Constants.Y.equalsIgnoreCase(claim.getCacc1b1().getSelectedInd()) ? LEASE : null);
		cas.setSpeechDevice     (Constants.Y.equalsIgnoreCase(claim.getCacc1b2().getSelectedInd()) ? PURCHASE : null);
		
		cas.setKeyguardSGD      (Constants.Y.equalsIgnoreCase(claim.getCacc1c1().getSelectedInd()) ? LEASE : null);
		cas.setKeyguardSGD      (Constants.Y.equalsIgnoreCase(claim.getCacc1c2().getSelectedInd()) ? PURCHASE : null);
		
		cas.setCarryCaseSGD     (Constants.Y.equalsIgnoreCase(claim.getCacc1d1().getSelectedInd()) ? LEASE : null);
		cas.setCarryCaseSGD     (Constants.Y.equalsIgnoreCase(claim.getCacc1d2().getSelectedInd()) ? PURCHASE : null);
		
		cas.setSoftwareSGD      (Constants.Y.equalsIgnoreCase(claim.getCacc1e1().getSelectedInd()) ? LEASE : null);
		cas.setSoftwareSGD      (Constants.Y.equalsIgnoreCase(claim.getCacc1e2().getSelectedInd()) ? PURCHASE : null);
		
		cas.setHardwareSGD      (Constants.Y.equalsIgnoreCase(claim.getCacc1f1().getSelectedInd()) ? LEASE : null);
		cas.setHardwareSGD      (Constants.Y.equalsIgnoreCase(claim.getCacc1f2().getSelectedInd()) ? PURCHASE : null);

	}

	private void populateAccessories(Section2 section2, ClaimCommunicationAid claim) {
		Accessories accs = new Accessories();
		section2.setAccessories(accs);
		
		accs.setSimplePointer(Constants.Y.equalsIgnoreCase(claim.getCaia3a().getSelectedInd()) ? PURCHASE : null);
		accs.setSimpleSwitch(Constants.Y.equalsIgnoreCase(claim.getCaia3b().getSelectedInd()) ? PURCHASE : null);
		accs.setSimpleDevice(Constants.Y.equalsIgnoreCase(claim.getCaia3c().getSelectedInd()) ? PURCHASE : null);
		accs.setSimpleHardware(Constants.Y.equalsIgnoreCase(claim.getCaia3d().getSelectedInd()) ? PURCHASE : null);
		
		accs.setKeyboard(Constants.Y.equalsIgnoreCase(claim.getCacc4a1().getSelectedInd()) ? LEASE: null);
		accs.setKeyboard(Constants.Y.equalsIgnoreCase(claim.getCacc4a2().getSelectedInd()) ? PURCHASE : null);

		accs.setMouseAlternative(Constants.Y.equalsIgnoreCase(claim.getCacc4b1().getSelectedInd()) ? LEASE : null);
		accs.setMouseAlternative(Constants.Y.equalsIgnoreCase(claim.getCacc4b2().getSelectedInd()) ? PURCHASE : null);
		
		accs.setPointer(Constants.Y.equalsIgnoreCase(claim.getCacc4c1().getSelectedInd()) ? LEASE : null);
		accs.setPointer(Constants.Y.equalsIgnoreCase(claim.getCacc4c2().getSelectedInd()) ? PURCHASE : null);
		
		accs.setSwitches(Constants.Y.equalsIgnoreCase(claim.getCacc4d1().getSelectedInd()) ? LEASE : null);
		accs.setSwitches(Constants.Y.equalsIgnoreCase(claim.getCacc4d2().getSelectedInd()) ? PURCHASE : null);
		
		accs.setMountDevice(Constants.Y.equalsIgnoreCase(claim.getCacc4e1().getSelectedInd()) ? LEASE : null);
		accs.setMountDevice(Constants.Y.equalsIgnoreCase(claim.getCacc4e2().getSelectedInd()) ? PURCHASE : null);
		
		accs.setMountHardware(Constants.Y.equalsIgnoreCase(claim.getCacc4f1().getSelectedInd()) ? LEASE : null);
		accs.setMountHardware(Constants.Y.equalsIgnoreCase(claim.getCacc4f2().getSelectedInd()) ? PURCHASE : null);
		
		accs.setAccessories(Constants.Y.equalsIgnoreCase(claim.getCacc4g1().getSelectedInd()) ? LEASE : null);
		accs.setAccessories(Constants.Y.equalsIgnoreCase(claim.getCacc4g2().getSelectedInd()) ? PURCHASE : null);
		
		accs.setControl(Constants.Y.equalsIgnoreCase(claim.getCacc4h().getSelectedInd()) ? PURCHASE : null); //?? Lease?		

	}

	private void initDiagnosis() {
		addField(CAForm1.Form.Section2.Diagnosis.class, "primaryDiagnosis", "primaryDiagnosisDesc");
		addField(CAForm1.Form.Section2.Diagnosis.class, "secondaryDiagnosis", "secondaryDiagnosisDesc");
	}

}
