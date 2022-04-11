package exceldoing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcelExample {
	private static CellStyle cellStyleFormatNumber = null;

	public static void main(String[] args) throws IOException {
		final String path = "D:\\demo.xlsx";
		Object[][] data = {{"name4", "data4"}, {"name6", "data6"}, {"namexx", "xxx"}, {"xzzz", "zzzz"}};
		writeExcel(path, 0, 4, 0, "username,password", data);
	}

	public static void writeExcel(String path, int sheetnum, int rowstart, int column, String namedata, Object[][] data)
			throws IOException {
		// Create Workbook
		Workbook workbook = getWorkbook(path);
		// Create sheet
		Sheet sheet = workbook.getSheetAt(sheetnum);
		// Write data
		for (int i = 0; i < data.length; i++) {
			// tạo row theo rowstart
			Row row = sheet.createRow(rowstart);
			// Write data on row
			writeBook(row, namedata, data[i], column);
			rowstart++;
		}

		// Auto resize column witdth
		int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
		autosizeColumn(sheet, numberOfColumn);

		// Create file excel
		createOutputFile(workbook, path);
		System.out.println("Done!!!");
	}

	// Create workbook
	private static Workbook getWorkbook(String path) throws IOException {
		Workbook workbook = null;
		// Get file
		InputStream inputStream = new FileInputStream(new File(path));
		
		if (path.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (path.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		return workbook;
	}// done

	// Write data
	private static void writeBook(Row row, String namedata, Object[] datarow, int column) {
		if (cellStyleFormatNumber == null) {
			// Format number
			short format = (short) BuiltinFormats.getBuiltinFormat("#,##0");
			// DataFormat df = workbook.createDataFormat();
			// short format = df.getFormat("#,##0");

			// Create CellStyle
			Workbook workbook = row.getSheet().getWorkbook();
			cellStyleFormatNumber = workbook.createCellStyle();
			cellStyleFormatNumber.setDataFormat(format);
		}

		String[] nametemp = namedata.split(",");
		String datatemp = "";
		//
		for (int i = 0; i < nametemp.length; i++) {
			datatemp = datatemp + nametemp[i] + "= " + datarow[i]+ ";";
		}
		
		datatemp = datatemp.substring(0, datatemp.length()-1);
		
		Cell cell = row.createCell(column);
		cell.setCellValue(datatemp);
	}// done

	// Create CellStyle for header
	private static CellStyle createStyleForHeader(Sheet sheet) {
		// Create font
		Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman");
		font.setBold(true);
		font.setFontHeightInPoints((short) 14); // font size
		font.setColor(IndexedColors.WHITE.getIndex()); // text color

		// Create CellStyle
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		return cellStyle;
	}


	// Auto resize column width
	private static void autosizeColumn(Sheet sheet, int lastColumn) {
		for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
			sheet.autoSizeColumn(columnIndex);
		}
	}

	// Create output file
	private static void createOutputFile(Workbook workbook, String excelFilePath) throws IOException {
		try (OutputStream os = new FileOutputStream(excelFilePath)) {
			workbook.write(os);
		}
	}

}
