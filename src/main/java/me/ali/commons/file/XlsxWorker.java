package me.ali.commons.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XlsxWorker {

	private static List<Row> extractRowsFromXlsx(File input) throws FileNotFoundException, IOException {
		try (FileInputStream file = new FileInputStream(input); XSSFWorkbook workbook = new XSSFWorkbook(file)) {
			
			List<Row> result = new ArrayList<Row>();
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				result.add(row);
			}
			return result;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private static void writeRowsToCsv(List<Row> rows, File output) throws IOException {
		try (FileWriter writer = new FileWriter(output)) {
			for (Row row : rows) {
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						writer.append(String.valueOf(cell.getNumericCellValue()));
						break;
					case Cell.CELL_TYPE_STRING:
						writer.append(cell.getStringCellValue());
						break;
					}
					writer.append(',');
				}
				writer.append('\n');
			}
			System.out.println("Csv written successfully..");
		}
	}
	
	public static List<Row> merge(List<File> files) throws Exception {
		List<Row> result = new ArrayList<>();
		for (File file : files) {
			result.addAll(extractRowsFromXlsx(file));
		}
		return result;
	}

	public static void convertToCsv(File input, File output) throws FileNotFoundException, IOException {
		List<Row> rows = extractRowsFromXlsx(input);
		writeRowsToCsv(rows, output);
	}
}
