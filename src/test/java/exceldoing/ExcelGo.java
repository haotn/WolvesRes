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
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelNhanVien;

public class ExcelGo {

	public static void main(String[] args) throws IOException {
//		mẫu ghi file excel:
//			1> chú ý số dòng, số cột
//			2> nên tạo file backup (dự phòng) để tránh mất dữ liệu
//			3> dataname cách nhau bằng dấu phẩy: ,
//		Object[][] dataprovider = { { "name4", "data4" }, { "name6", "data6" }, { "namexx", "xxx" },
//				{ "xzzz", "zzzz" } };
//
//		DataGenerator dataG = new DataGenerator();
//		List<ModelNhanVien> list = dataG.generateListNhanVien(true, false, true, true, true, 5);
//		Object[][] data = new Object[list.size()][10];
//		for (int i = 0; i < list.size(); i++) {
//			data[i][0] = list.get(i).getMaNV();
//			data[i][1] = list.get(i).getHoTen();
//			data[i][2] = list.get(i).getChucVu();
//			data[i][3] = list.get(i).getCMND();
//			data[i][4] = list.get(i).getEmail();
//			data[i][5] = list.get(i).getSoDT();
//			data[i][6] = list.get(i).getNgaySinh();
//			data[i][7] = list.get(i).getPathHinhAnh();
//			data[i][8] = list.get(i).isGioiTinh();
//			data[i][9] = list.get(i).isTrangThai();
//			if (i == list.size() - 1) {
//				System.out.println("=100");
//				writeExcelv2("excel-file/nhanVien-fail-idNational-data.xlsx", 0, 0, 0,
//						"MaNhanVien,HoTen,ChucVu,CCCD/CMND,Email,SDT,NgaySinh,PathHinhAnh,Gender,TrangThai", data);
//			}
//		}
//		SanPhamDAO spDao = new SanPhamDAO();
//		Object[][] data = new Object[5][6];
//		for (int i = 0; i < 5; i++) {
//			data[i][0] = i + 50;
//			data[i][1] = i + 150;
//			data[i][2] = spDao.selectAll().get(dataG.randomMinMax(0, spDao.selectAll().size() - 1)).getMaSP();
//			data[i][3] = dataG.randomMinMax(10, 1000);
//			data[i][4] = XDate.toString(dataG.generateDate(2022, 2022), "dd-MM-yyyy");
//			data[i][5] = true;
//			if (i == data.length - 1) {
//				System.out.println("=100");
//				writeExcelv2("excel-file/sanpham-fail-date.xlsx", 0, 0, 0, "ID,IDLS,MaSP,SoLuong,HanSuDung,TrangThai",
//						data);
//			}
//		}

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

		// Create file excel
		createOutputFile(workbook, path);
		System.out.println("Done!!!");
	}

	// Create workbook
	private static Workbook getWorkbook(String path) throws IOException {
		Workbook workbook = null;
		// Get file
		InputStream inputStream = new FileInputStream(new File(path));
		//
		if (path.endsWith("xlsx")) {
			workbook = new XSSFWorkbook(inputStream);
		} else if (path.endsWith("xls")) {
			workbook = new HSSFWorkbook(inputStream);
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		return workbook;
	}// done

	// Create workbook
	private static Workbook getNewWorkbook(String path) throws IOException {
		Workbook workbook = null;
		//
		if (path.endsWith("xlsx")) {
			workbook = new XSSFWorkbook();
		} else if (path.endsWith("xls")) {
			workbook = new HSSFWorkbook();
		} else {
			throw new IllegalArgumentException("The specified file is not Excel file");
		}
		return workbook;
	}// done

	// Write data
	private static void writeBook(Row row, String namedata, Object[] datarow, int column) {
		//
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

	//
	public static void writeExcelv2(String path, int sheetnum, int rowstart, int column, String namedata,
			Object[][] data) throws IOException {
		// đọc workbook(excel) nào
		Workbook workbook = getWorkbook(path);
		// lấy sheet mấy
		Sheet sheet = workbook.getSheetAt(sheetnum);
		// tạo workbook tạm
		Workbook workbooktemp = getWorkbook(path);
		// tạo sheet tạm
		Sheet sheettemp = workbooktemp.getSheetAt(sheetnum);
		// xử lý dữ liệu
		String[] nametemp = namedata.split(",");
		int rowIndex = rowstart;
		// lấy all rows có dữ liệu
		Iterator<Row> iteratorRow = sheet.iterator();
		Iterator<Row> iteratorRowtemp = sheettemp.iterator();
		while (iteratorRow.hasNext()) {
			// lấy row
			Row nextRow = iteratorRow.next();
			// continue nếu chưa tới, dữ liệu phía trên dòng bắt đầu được giữ nguyên
			if (nextRow.getRowNum() < rowstart) {
				continue;
			} else if (nextRow.getRowNum() == rowstart) {
				// lấy all cells
				Iterator<Cell> iteratorCell = nextRow.cellIterator();
				List<String> list = new ArrayList<String>();
				// chạy hết dữ liệu
				for (int i = 0; i < data.length; i++) {
					System.out.println(i + 1);
					// tạo row theo rowstart
					Row row = sheettemp.createRow(rowIndex);
					// xử lý namedata
					String datatemp = "";
					//
					for (int k = 0; k < nametemp.length; k++) {
						datatemp = datatemp + nametemp[k] + "= " + data[i][k] + ";";
					}
					datatemp = datatemp.substring(0, datatemp.length() - 1);
					while (iteratorCell.hasNext()) {
						// xét từng cell
						Cell cell = iteratorCell.next();
						Object cellValue = getCellValue(cell);
						if (cellValue == null) {
							list.add("");
						} else {
							list.add(String.valueOf(cellValue));
						}
					}
					//
					for (int z = 0; z < list.size(); z++) {//
						if (list.get(z) == null) {
							continue;
						}
						//
						if (z != column) {
							Cell celltemp = row.createCell(z);
							celltemp.setCellValue(String.valueOf(list.get(z)));
						} else if (z == column) {
							Cell celltemp = row.createCell(z);
							celltemp.setCellValue(String.valueOf(datatemp));
						}
					}
					rowIndex++;
				}
			} else {
				// lấy all cells
				Iterator<Cell> iteratorCell = nextRow.cellIterator();
				Row row = sheettemp.createRow(rowIndex);
				while (iteratorCell.hasNext()) {
					// xét từng cell
					Cell cell = iteratorCell.next();
					Object cellValue = getCellValue(cell);
					if (cellValue == null || cellValue.toString().isEmpty()) {
						continue;
					}
					// Set value for book object
					int columnIndex = cell.getColumnIndex();
					//
					Cell celltemp = row.createCell(columnIndex);
					celltemp.setCellValue(String.valueOf(cellValue));
				}
				rowIndex++;
			}
		}
		// Create file excel
		createOutputFile(workbooktemp, path);
		System.out.println("Done!!!");
	}

	//
	public static void writeExcelv3(String path, int sheetnum, int rowstart, int column, String namedata,
			Object[][] data) throws IOException {
		// đọc workbook(excel) nào
		Workbook workbook = getWorkbook(path);
		// lấy sheet mấy
		Sheet sheet = workbook.getSheetAt(sheetnum);
		// tạo workbook tạm
		Workbook workbooktemp = getWorkbook(path);
		// tạo sheet tạm
		Sheet sheettemp = workbooktemp.getSheetAt(sheetnum+1);
		// xử lý dữ liệu
		String[] nametemp = namedata.split(",");
		int rowIndex = rowstart;
		// lấy all rows có dữ liệu
		Iterator<Row> iteratorRow = sheet.iterator();
		Iterator<Row> iteratorRowTemp = sheettemp.iterator();
		while (iteratorRow.hasNext()) {
			// lấy row
			Row nextRow = iteratorRow.next();
			// continue nếu chưa tới, dữ liệu phía trên dòng bắt đầu được giữ nguyên
			if (nextRow.getRowNum() < rowstart) {
				continue;
			} else if (nextRow.getRowNum() == rowstart) {
				// lấy all cells
				Iterator<Cell> iteratorCell = nextRow.cellIterator();
				List<String> list = new ArrayList<String>();
				// chạy hết dữ liệu
				int sheetTempIndex = 0;
				int checkRow = 1;
				for (int i = 0; i < data.length; i++) {
					// xử lý namedata
					String datatemp = "";
					//
					for (int k = 0; k < nametemp.length; k++) {
						datatemp = datatemp + nametemp[k] + "= " + data[i][k] + ";";
					}
					datatemp = datatemp.substring(0, datatemp.length() - 1);
					//
					while (iteratorCell.hasNext()) {
						// xét từng cell
						Cell cell = iteratorCell.next();
						Object cellValue = getCellValue(cell);
						if (cellValue == null) {
							list.add("");
						} else {
							list.add(String.valueOf(cellValue));
						}
					}
					while (iteratorRowTemp.hasNext() && checkRow == 1) {
						Row nextRowTemp = iteratorRowTemp.next();
						Iterator<Cell> iteratorCellTemp = nextRowTemp.cellIterator();
						while(iteratorCellTemp.hasNext() && checkRow == 1) {
							Cell cellTemp = iteratorCellTemp.next();
							if (cellTemp.getColumnIndex() == 1) {
								System.err.print((sheetTempIndex)+" ");
								Object cellValue = getCellValue(cellTemp);
								if (cellValue == null) {
									checkRow = 0;
									sheetTempIndex++;
									break;
								} else {
									if (String.valueOf(cellValue).isEmpty()) {
										checkRow = 0;
										sheetTempIndex++;
										break;
									}else {
										sheetTempIndex++;
										break;
									}
								}
							}
						}
					}
					// tạo row theo rowstart
					Row row = sheettemp.createRow(sheetTempIndex);
					System.err.print((sheetTempIndex)+" ");
					//
					for (int z = 0; z < list.size(); z++) {//
//						if (list.get(z) == null) {
//							continue;
//						}
						//
						if (z != column) {
							Cell celltemp = row.createCell(z);
							if (list.get(z)==null) {
								celltemp.setCellValue("");
							}else {
								celltemp.setCellValue(String.valueOf(list.get(z)));
							}
						} else if (z == column) {
							Cell celltemp = row.createCell(z);
							celltemp.setCellValue(String.valueOf(datatemp));
						}
					}
					sheetTempIndex++;
				}
			}
		}
		// Create file excel
		createOutputFile(workbooktemp, path);
		System.out.println("Done!!!");
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

	//

}
