package moh.adp.db.etl;

import java.util.Date;
//import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import moh.adp.common.exception.AdpException;
import moh.adp.db.common.Util;
import moh.adp.db.jpa.ClaimRecField;
import moh.adp.db.jpa.ClaimRecVariance;
import moh.adp.db.jpa.ClaimRecord;
import moh.adp.db.jpa.RecordSet;
import moh.adp.model.client.ClientView;
import moh.adp.model.party.prescriber.PhysicianView;
import moh.adp.server.lookup.PhysicianLookupService;
import moh.adp.service.client.ClientService;
import moh.adp.service.client.ClientServiceImpl;


public class DBImport {
	
	public static void persist(RecordSet rs, List<ExcelClaimRecord> records, EntityManager em) {
		records.forEach(ecr -> persistOne(rs, ecr, em));
	}

	private static void persistOne(RecordSet rs, ExcelClaimRecord ecr, EntityManager em) {
		ClaimRecord cr = createClaimRecord(rs, em);
		setAdamClientId(cr, ecr, em); // TODO
		setPhysicianId(cr, ecr, em);
		setVendor1Id(cr, ecr, em);
		addVariances(cr, ecr, em);
		addFields(cr, ecr, em);
	}

	private static void addFields(ClaimRecord cr, ExcelClaimRecord ecr, EntityManager em) {
		for (Entry<String, String> entry: ecr.getSection2().fields.entrySet()) {
			if (entry.getKey() == null || entry.getKey().length()==0)
				continue;
			ClaimRecField crv = createClaimRecField(cr, em);
			crv.setPropertyNameAndValue(entry.getKey(), entry.getValue());			
		}
	}

	private static void addVariances(ClaimRecord cr, ExcelClaimRecord ecr, EntityManager em) {
		for (Entry<String, String> entry: ecr.getSection2().fields.entrySet()) {
			if (entry.getKey() == null || entry.getKey().length()==0)
				continue;
			ClaimRecVariance crv = createClaimRecVariance(em);
			crv.setClaimRecord(cr);
			crv.setPropertyName(entry.getKey());
			crv.setStringPropertyValue(entry.getValue().toString()); // TODO
		}	
	}

	private static ClaimRecField createClaimRecField(ClaimRecord cr, EntityManager em) {
		ClaimRecField crf = new ClaimRecField();
		Util.persist(em, crf);
		crf.setClaimRecord(cr);
		return crf;
	}

	private static ClaimRecord createClaimRecord(RecordSet rs, EntityManager em) {
		ClaimRecord cr = new ClaimRecord();
		cr.setRecordSet(rs);
		Util.persist(em, cr);
		return cr;
	}

	private static void setVendor1Id(ClaimRecord cr, ExcelClaimRecord ecr, EntityManager em) {
		if (ecr.getSection4().vendorId != null)
			cr.setAdamVendor1Id(ecr.getSection4().vendorId);
	}

	private static void setPhysicianId(ClaimRecord cr, ExcelClaimRecord ecr, EntityManager em) {
		try {
			PhysicianView pv = PhysicianLookupService.getPhysicianByBillingNum(ecr.getSection4().physicianBillingNumber, new Date(System.currentTimeMillis()));
			if (pv != null && pv.getId() != null)
				cr.setAdamPhysicianId(pv.getId());
		} catch (AdpException e) {
			e.printStackTrace();
			System.out.println("physician finder broken " + e.getMessage());
			//System.exit(-1);
		}
	}

	private static void setAdamClientId(ClaimRecord cr, ExcelClaimRecord ecr, EntityManager em) {
		cr.setHealthNumber(ecr.getSection1().healthNumber);
/*		try {
            ClientService service = new ClientServiceImpl();
            ClientView cv = service.getClientView(ecr.getSection1().healthNumber);			
            System.out.println("client id was " + cv.getAdpClient().getId());
			//cr.setAdamClientId(cv.getAdpClient().getId());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("client lookup broken " + e.getMessage());
			//System.exit(-1);
		}*/
	}

	private static ClaimRecVariance createClaimRecVariance(EntityManager em) {
		ClaimRecVariance crv = new ClaimRecVariance();
		Util.persist(em, crv);
		return crv;
	}

}
