package moh.adp.db.etl;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;

public class FieldReader {
	public static DataFormatter df;
	
	static {
		df = new DataFormatter();
	}

	public static void setFields(Row headerRow, Row recordRow, SubSection section) {
		if (recordRow == null)
			return;
		Map<Integer, String> fieldNames = getFieldNames(headerRow);
		recordRow.forEach(c -> {
			if (fieldNames.get(c.getColumnIndex()) != null)
				section.fields.put(fieldNames.get(c.getColumnIndex()), df.formatCellValue(c));	
		});
	}

	private static Map<Integer, String> getFieldNames(Row headerRow) {
		Map<Integer, String> fieldNames = new HashMap<>();
		headerRow.forEach(c -> fieldNames.put(c.getColumnIndex(), df.formatCellValue(c)));
		return fieldNames;
	}

}
