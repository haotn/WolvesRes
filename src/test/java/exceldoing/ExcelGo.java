package exceldoing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelGo {

	public static void main(String[] args) throws IOException {
//		mẫu ghi file excel:
//			1> chú ý số dòng, số cột
//			2> nên tạo file backup (dự phòng) để tránh mất dữ liệu
//			3> dataname cách nhau bằng dấu phẩy: ,
		Object[][] dataprovider = {{"name4", "data4"}, {"name6", "data6"}, {"namexx", "xxx"}, {"xzzz", "zzzz"}};
		writeExcel("D:\\demo.xlsx", 0, 7, 0, "username,password", dataprovider);
	}
	
	private static CellStyle cellStyleFormatNumber = null;

	public static List<Object[]> readExcel(String path, int rowstart, int rownum, int column) throws IOException {
		List<Object[]> list = new ArrayList<Object[]>();

		// Get file
		InputStream inputStream = new FileInputStream(new File(path));

		// Get workbook
		Workbook workbook = getWorkbook(inputStream, path);

		// Get sheet
		Sheet sheet = workbook.getSheetAt(0);

		// Get all rows
		Iterator<Row> iterator = sheet.iterator();
		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
//			if (nextRow.getRowNum() == 0) {
//				// Ignore header
//				continue;
//			}
			// bỏ qua
			if (nextRow.getRowNum() < rowstart) {
				// Ignore header
				continue;
			}
			// đến đâu
			if (nextRow.getRowNum() > rowstart + rownum) {
				// Ignore header
				break;
			}

			// datarow nhận về
			Object[] datarow;
			// data gốc
			String dataorigin = nextRow.getCell(column).getStringCellValue();
			// mãng data tách ;
			String[] datatemp = dataorigin.split(";");
			for (int i = 0; i < datatemp.length; i++) {
				datatemp[i] = datatemp[i].substring(datatemp[i].indexOf(" ") + 1);
			}

			datarow = datatemp;

			list.add(datarow);
		}

		workbook.close();
		inputStream.close();

		return list;
	}

	// Get Workbook
	private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
		Workbook workbook = null;
		if (excelFilePath.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (excelFilePath.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		return workbook;
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
			datatemp = datatemp + nametemp[i] + "= " + datarow[i] + ";";
		}

		datatemp = datatemp.substring(0, datatemp.length() - 1);

		Cell cell = row.createCell(column);
		cell.setCellValue(datatemp);
	}// done

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
