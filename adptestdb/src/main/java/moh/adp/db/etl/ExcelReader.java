package moh.adp.db.etl;
 
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	public static DataFormatter df;
	
	static {
		df = new DataFormatter();
	}
	
	public static List<ExcelClaimRecord> extract(Path p) {
		try {
			return extract(p.toFile());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<ExcelClaimRecord> extract(File excelFile) throws Exception {
		List<ExcelClaimRecord> contents = new ArrayList<>();
		if (!isExcelFile(excelFile))
			return contents;
		String deviceCategory = getDeviceCategory(excelFile);
		Workbook wb = WorkbookFactory.create(excelFile);
		for (int i=2; i <= wb.getSheetAt(0).getLastRowNum(); i++)
			processRow(wb, i, contents, deviceCategory);
		return contents;
	}
	
	private static void processRow(Workbook wb, int i, List<ExcelClaimRecord> contents, String deviceCategory) {
		String healthNumber‎ = get(wb.getSheetAt(0).getRow(i), 0);
		if (healthNumber‎ == null || healthNumber‎.trim().length() == 0)
			return;
		ExcelClaimRecord ecr = addRecord(deviceCategory, contents); 
		doSection1(wb, i, ecr);
		doSection2(wb, i, ecr);
		doSection3(wb, i, ecr);
		doSection4(wb, i, ecr);
	}

	protected static String getDeviceCategory(File excelFile) {
		String fileName = excelFile.getName();
		System.out.println("fileName " + fileName);
		fileName = fileName.split("\\.")[0];
		String deviceCategory = fileName.split("-")[0];
		System.out.println("DEVICE CATEGORY IS " + deviceCategory);
		return deviceCategory;
	}	
	
	private static void doSection1(Workbook wb, int i, ExcelClaimRecord ecr) {
		Row row = wb.getSheetAt(0).getRow(i);
		ecr.getSection1().healthNumber = get(row, 0);
		ecr.setGateKeeperDate(getDate(row, 1));
		ecr.getSection1().confBenefitQ1yn = get(row, 20);
		ecr.getSection1().confBenefitQ1IfYes = get(row, 21);
		ecr.getSection1().confBenefitQ2yn = get(row, 22);
		ecr.getSection1().confBenefitQ3yn = get(row, 23);
		FieldReader.setFields(wb.getSheetAt(0).getRow(0), row, ecr.getSection1());
	}

	private static void doSection2(Workbook wb, int i, ExcelClaimRecord ecr) {
		Row row = wb.getSheetAt(1).getRow(i);
		FieldReader.setFields(wb.getSheetAt(1).getRow(0), row, ecr.getSection2());
	}

	private static void doSection3(Workbook wb, int i, ExcelClaimRecord ecr) {
		Row row = wb.getSheetAt(2).getRow(i);
		FieldReader.setFields(wb.getSheetAt(2).getRow(0), row, ecr.getSection3());
	}

	private static void doSection4(Workbook wb, int i, ExcelClaimRecord ecr) {
		Row row = wb.getSheetAt(3).getRow(i);
		FieldReader.setFields(wb.getSheetAt(3).getRow(0), row, ecr.getSection4());
	}

	private static ExcelClaimRecord addRecord(String deviceCategory, List<ExcelClaimRecord> contents) {
		ExcelClaimRecord ecr = new ExcelClaimRecord(deviceCategory);
		contents.add(ecr);
		return ecr;
	}

	private static String get(Row row, int i) {
		Cell cell = row.getCell(i);
		return (cell == null)? "n/a" : df.formatCellValue(cell);
	}

	private static String getDate(Row row, int i) {
		Cell cell = row.getCell(i);
		return (cell == null)? "n/a" : cell.getStringCellValue();
	}

	private static boolean isExcelFile(File excelFile) {
		return ".xlsx".equalsIgnoreCase(excelFile.getName().substring(excelFile.getName().lastIndexOf('.')));
	}


}
