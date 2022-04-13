package exceldoing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcelExample {

	public static void main(String[] args) throws IOException {
		final String path = "excel-file/fullname-data.xlsx";
		List<Object[]> list = readExcel(path, 0, 6, 0);
		for (Object[] book : list) {
			System.out.println(String.valueOf(book[0]) + " - " + String.valueOf(book[1]));
		}
	}

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

			// Get all cells
			Iterator<Cell> cellIterator = nextRow.cellIterator();

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

	// Get cell value
	private static Object getCellValue(Cell cell) {
		CellType cellType = cell.getCellTypeEnum();
		Object cellValue = null;
		switch (cellType) {
		case BOOLEAN:
			cellValue = cell.getBooleanCellValue();
			break;
		case FORMULA:
			Workbook workbook = cell.getSheet().getWorkbook();
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			cellValue = evaluator.evaluate(cell).getNumberValue();
			break;
		case NUMERIC:
			cellValue = cell.getNumericCellValue();
			break;
		case STRING:
			cellValue = cell.getStringCellValue();
			break;
		case _NONE:
		case BLANK:
		case ERROR:
			break;
		default:
			break;
		}

		return cellValue;
	}
}
