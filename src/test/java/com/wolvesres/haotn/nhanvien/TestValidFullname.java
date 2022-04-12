package com.wolvesres.haotn.nhanvien;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelNhanVien;

import exceldoing.ExcelGo;

/**
 * Test valid fullname
 * 
 * @author Brian
 *
 */
public class TestValidFullname {

	private DataGenerator data;
	private ExcelGo excelHandle;

	private List<String> listFullname;

	/**
	 * Before class - Generate global variable value
	 * 
	 */
	@BeforeClass
	public void beforClass() {
		data = new DataGenerator();
		listFullname = new ArrayList<String>();
		for (int i = 0; i < 100; i++) {
			String fullname = "";
			int rand = data.randomMinMax(0, 6);
			switch (rand) {
			case 0:
				fullname = data.generateFullname(true, false, false);
				break;
			case 1:
				fullname = data.generateFullname(true, false, false).toUpperCase();
				break;
			case 2:
				fullname = data.generateFullname(true, false, false).toLowerCase();
				break;
			case 3:
				fullname = data.generateFullname(true, false, true);
				break;
			case 4:
				fullname = data.generateFullname(false, false, false);
				break;
			case 5:
				fullname = data.generateFullname(false, false, false).toUpperCase();
				break;
			case 6:
				fullname = data.generateFullname(false, false, false).toLowerCase();
				break;
			case 7:
				fullname = data.generateFullname(false, false, true);
				break;
			}
			listFullname.add(fullname);
		}
	}

	/**
	 * DataProvider for testValidFullnameSuccess
	 * 
	 * @return Object[][]
	 */
	@DataProvider(name = "data")
	public Object[][] data() {
		List<String> fullnames = new ArrayList<String>();
		Object[][] data = new Object[listFullname.size()][2];
		for (int i = 0; i < listFullname.size(); i++) {
			data[i][0] = listFullname.get(i);
			data[i][1] = true;
			fullnames.add(listFullname.get(i));
		}
		Object[][] toWrite = new Object[100][8];
		Map<String, String> email = this.data.generateMapEmail(fullnames);
		int i = 0;
		for (String key : email.keySet()) {
			ModelNhanVien nhanVien = new ModelNhanVien();
			int role = this.data.randomMinMax(2, 4);
			nhanVien.setChucVu(role);
			int gender = this.data.randomMinMax(0, 1);
			if (gender == 1) {
				nhanVien.setGioiTinh(true);
			} else {
				nhanVien.setGioiTinh(false);
			}
			nhanVien.setNgaySinh(XDate.toString(this.data.generateDate(1990, 2003), "dd-MM-yyyy"));
			nhanVien.setCMND(this.data.generateIdNational(XDate.toDate(nhanVien.getNgaySinh(), "dd-MM-yyyy"),
					nhanVien.isGioiTinh()));
			nhanVien.setMaNV("NV" + String.valueOf(i + 20));
			nhanVien.setPathHinhAnh("anhNhanVien" + String.valueOf(i + 20));
			nhanVien.setSoDT(this.data.generateSDT());
			nhanVien.setTrangThai(true);
			nhanVien.setEmail(key);
			nhanVien.setHoTen(email.get(key));
			toWrite[i][0] = nhanVien.getMaNV();
			toWrite[i][1] = nhanVien.getHoTen();
			toWrite[i][2] = nhanVien.getChucVu();
			toWrite[i][3] = nhanVien.getCMND();
			toWrite[i][4] = nhanVien.getEmail();
			toWrite[i][5] = nhanVien.getSoDT();
			toWrite[i][6] = nhanVien.getNgaySinh();
			toWrite[i][7] = nhanVien.getPathHinhAnh();
			i++;
		}
		try {
			ExcelGo.writeExcel("E:\\demo.xlsx", 0, 1, 0,
					"MaNhanVien,HoTen,ChucVu,CCCD/CMND,Email,SDT,NgaySinh,PathHinhAnh", toWrite);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * TestCase testValidFullnameSuccess
	 * 
	 * @param fullname
	 * @param expected
	 */
	@Test(dataProvider = "data")
	public void testValidFullnameSuccess(String fullname, Boolean expected) {
		Boolean actual = true;
		if (!FormValidator.isTextContainsSpace(fullname)) {
			actual = false;
		}
		Assert.assertEquals(expected, actual);
	}

}
