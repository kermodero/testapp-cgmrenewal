package moh.adp.db.etl;

import java.io.ByteArrayOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;

import moh.adp.db.jpa.RecordSet;
import moh.adp.db.jpa.TestEntity;
import moh.adp.db.jpa.TestSet;
import static moh.adp.db.common.Util.persist;

public class ExcelETL {
	private static String excelFileFolderTmp = "C:/TEST/Excel_Folder/";
	private static String etlOut = "C:/TEST/out/";
	private static AtomicInteger counter = new AtomicInteger(0);

	public static void importRecords(String testName, String testDescription, String excelFileFolder,
			EntityManager em) {
		try {
			TestEntity test = createTest(testName, testDescription, em);
			TestSet ts = createTestSet(test, testName, testDescription, em);
			Path folder = Paths.get(excelFileFolderTmp);
			Files.list(folder).forEach(excelFile -> {
				RecordSet rs = createRecordSet(ts, excelFile, em);
				List<ExcelClaimRecord> records = ExcelReader.extract(excelFile);
				DBImport.persist(rs, records, em);
				print(records);
			});
			System.out.println("Import of records complete.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static RecordSet createRecordSet(TestSet ts, Path f, EntityManager em) {
		RecordSet rs = new RecordSet();
		System.out.println("file " + f.getFileName().toString());
		rs.setFileName(f.getFileName().toString());
		rs.setDescription(ExcelReader.getDeviceCategory(f.toFile()));
		rs.setTestSet(ts);
		persist(em, rs);
		return rs;
	}

	private static TestSet createTestSet(TestEntity test, String testName, String testDescription, EntityManager em) {
		TestSet ts = new TestSet();
		ts.setTest(test);
		persist(em, ts);
		return ts;
	}

	private static TestEntity createTest(String testName, String testDescription, EntityManager em) {
		TestEntity te = new TestEntity();
		te.setName(testName);
		te.setDescription(testDescription);
		persist(em, te);
		return te;
	}

	private static void print(List<ExcelClaimRecord> records) {
		records.forEach(r -> print(r));
	}

	private static void print(ExcelClaimRecord r) {
		System.out.println("record" + r);
		Path path = getPath(counter);
		try {
			Path p = Files.createFile(path);
			ByteArrayOutputStream baos = getContent(r);
			Files.write(p, baos.toByteArray(), StandardOpenOption.CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Path getPath(AtomicInteger index) {
		Path p = Paths.get(etlOut + "eSub_" + index + ".xml");
		if (p.toFile().exists()){
			index.incrementAndGet();
			return getPath(index);
		}
		return p;
	}

	private static ByteArrayOutputStream getContent(ExcelClaimRecord r) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(ExcelClaimRecord.class);
		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		JAXBElement<ExcelClaimRecord> rootElement = new JAXBElement<>(new QName("customer"), ExcelClaimRecord.class, r);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		marshaller.marshal(rootElement, baos);
		return baos;
	}

}
