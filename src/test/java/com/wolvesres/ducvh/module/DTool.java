package com.wolvesres.ducvh.module;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.form.FormNhanVien;
import com.wolvesres.form.FormVoucher;
import com.wolvesres.form.sanpham.JDialogDonViTinh;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelNhapKho;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.model.ModelVouCher;

public class DTool {

//	hàm check danh mục
	public static boolean checkCategory(boolean insert, String MaDM, String TenDM, boolean matHang) {
		List<ModelDanhMuc> listDM = new ArrayList<>();
		try {
			if (!FormValidator.isTextIsNotEmpty(TenDM) || !FormValidator.isTextIsNotEmpty(MaDM)) {
				System.out.println("c.w.f.danhmuc: EditDanhMuc.line84.valideForm(): trống");
				return false;
			}
		} catch (Exception e) {
			System.out.println("c.w.f.danhmuc: EditDanhMuc.line84.valideForm(): trống");
			return false;
		}
		for (int i = 0; i < listDM.size(); i++) {
			if (insert) {
				if (listDM.get(i).getMaDanhMuc().equals(MaDM)) {
					System.out.println("c.w.f.danhmuc: EditDanhMuc.line84.valideForm(): trùng mã");
					return false;
				}
				if (listDM.get(i).getTenDanhMuc().equalsIgnoreCase(TenDM) && (matHang == listDM.get(i).isMatHang())) {
					System.out.println("c.w.f.danhmuc: EditDanhMuc.line84.valideForm(): trùng tên");
					return false;
				}
			} else {
				if (MaDM.equals(listDM.get(i).getMaDanhMuc()) && listDM.get(i).getTenDanhMuc().equalsIgnoreCase(TenDM)
						&& listDM.get(i).isMatHang() == matHang) {
				} else if (listDM.get(i).getTenDanhMuc().equalsIgnoreCase(TenDM)
						&& listDM.get(i).isMatHang() == matHang) {
					System.out.println("c.w.f.danhmuc: EditDanhMuc.line84.valideForm(): trùng tên");
					return false;
				}
			}
		}
		System.out.println("c.w.f.danhmuc: EditDanhMuc.line84.valideForm(): done");
		return true;
	}

	// hàm check đơn vị tính: valideForm() (EditDonviTinh - line64)
	public static boolean checkCalcUnit(boolean insert, String DVT) {
		int ma = 0;
		JDialogDonViTinh formParent = new JDialogDonViTinh(null, true);

		if (!FormValidator.isTextIsNotEmpty(DVT)) {
			System.out.println("c.w.f.donvitinh: EditDonviTinh.line64.valideForm(): trống");
			return false;
		}
		for (int i = 0; i < formParent.getList().size(); i++) {
			if (insert) {
				if (formParent.getList().get(i).getTenDVT().equalsIgnoreCase(DVT)) {
					System.out.println("c.w.f.donvitinh: EditDonviTinh.line64.valideForm(): trùng tên");
					return false;
				}
			} else {
				if (ma == formParent.getList().get(i).getMaDVT()) {
					if (ma != formParent.getList().get(i).getMaDVT()
							&& formParent.getList().get(i).getTenDVT().equalsIgnoreCase(DVT)) {
						System.out.println("c.w.f.donvitinh: EditDonviTinh.line64.valideForm(): trùng mã");
						return false;
					}

				}
			}
		}
		System.out.println("c.w.f.donvitinh: EditDonviTinh.line64.valideForm(): done");
		return true;
	}

	// hàm check bàn: validForm() (EditBan - line142)
	public static boolean checkTable(String tenBan) {
		if (!FormValidator.isTextIsNotEmpty(tenBan)) {
			System.out.println("c.w.f.ban: EditBan.line142.validForm(): trống");
			return false;
		}
		System.out.println("c.w.f.ban: EditBan.line142.validForm(): done");
		return true;
	}

	// hàm check voucher
	public static boolean checkVoucher(boolean insert, String maVoucher, String soLuong, String ngayKetThuc,
			String ngayBatDau, String giamGia) {
		ModelVouCher voucher = new ModelVouCher("V1", "17-02-2022", "17-04-2022", 1, 1, "QR", true);
//		FormVoucher formParent = new FormVoucher(null);
		if (!FormValidator.isTextIsNotEmpty(maVoucher) || !FormValidator.isTextIsNotEmpty(soLuong)
				|| !FormValidator.isTextIsNotEmpty(giamGia)) {
			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): trống");
			return false;
		} else if (!FormValidator.isValidTextMinLength(maVoucher, 5)) {
			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): mã - 5 ký tự");
			return false;
		}
		if (!FormValidator.isIntNumber(soLuong)) {
			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): số lượng - thực");
			return false;
		}
		if (!FormValidator.isGreaterThan(Integer.parseInt(soLuong), 0)) {
			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): số lượng - âm");
			return false;
		}
		try {
			Float.parseFloat(giamGia);
		} catch (Exception e) {
			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): giảm giá - chữ");
			return false;
		}
		if (!FormValidator.isLessThan(Float.parseFloat(giamGia), 101)
				|| !FormValidator.isGreaterThan(Float.parseFloat(giamGia), 0)) {
			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): % giảm giá - >0 & <100");
			return false;
		}
		Date now = new Date();
		if (!FormValidator.isBeginDateValid(XDate.toDate(ngayBatDau, "dd-MM-yyyy")) && insert) {
			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): ngày bắt đầu - hiện tại");
			return false;
		} else if (!FormValidator.isEndDayValid(XDate.toDate(ngayBatDau, "dd-MM-yyyy"),
				XDate.toDate(ngayKetThuc, "dd-MM-yyyy"))) {
			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): ngày kết thúc - trước sau");
			return false;
		}
		if (!insert) {
			if (!FormValidator.isDateBefore(XDate.toDate(voucher.getNgayBatDau(), "dd-MM-yyyy"), now)) {
				if (!!FormValidator.isDateEquals(XDate.toDate(ngayBatDau, "yyyy-MM-dd"),
						XDate.toDate(voucher.getNgayBatDau(), "yyyy-MM-dd"))) {
					System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): ngày bắt đầu - đổi");
					return false;
				}
			}
		}
//		if (!FormValidator.isVoucherNotDuplicate(maVoucher, formParent.getList(), insert)) {
//			System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): trùng mã");
//			return false;
//		}
		System.out.println("c.w.f.voucher: EditVoucher.line147.validateForm(): done");
		return true;
	}

	// hàm check số lượng nhập kho
	public static boolean checkStockImport(String txtSoLuong) {
		// bổ sung
		int select = 0;
		FormValidator validator = new FormValidator();
		List<ModelNhapKho> listNhapKho = new ArrayList<>();
		listNhapKho.add(new ModelNhapKho(1, 1, 1, "17-02-2023", "s0001"));
		//
		int sl = 0;
		try {
//			sl = Integer.parseInt(txtSoLuong.getText().trim());
			sl = Integer.parseInt(txtSoLuong.trim());
			if (validator.isLessThan(sl, 1)) {
				sl = listNhapKho.get(select).getSoLuong();
//				txtSoLuong.setText(sl + "");
				System.out.println("c.w.f.kho: JDialogNhapKho.line210.checkSL(): lượng nhập < 2");
				return false;
			}
		} catch (Exception e) {
			sl = listNhapKho.get(select).getSoLuong();
//			txtSoLuong.setText(sl + "");
			System.out.println("c.w.f.kho: JDialogNhapKho.line210.checkSL(): lượng nhập - chữ");
			return false;
		}
		System.out.println("c.w.f.kho: JDialogNhapKho.line210.checkSL(): done");
		return true;
	}

	//

	public static boolean checkStockPriceImport(String txtGia) {
		// bổ sung
		int select = 0;
		FormValidator validator = new FormValidator();
		List<ModelNhapKho> listNhapKho = new ArrayList<>();
		listNhapKho.add(new ModelNhapKho(1, 1000, 1000, "17-02-2023", "s0001"));
		//
		float gia = 0;
		try {
//			gia = Float.parseFloat(txtGia.getText().trim());
			gia = Float.parseFloat(txtGia.trim());
			if (gia <= 0) {
				gia = listNhapKho.get(select).getGia();
//				txtGia.setText(gia + "");
				System.out.println("c.w.f.kho: JDialogNhapKho.line232.checkGia(): giá nhập < 0");
				return false;
			}
			float gt = listNhapKho.get(select).getGia() + (listNhapKho.get(select).getGia() / 2);
			if (validator.isGreaterThan(gia, gt)) {
				gia = listNhapKho.get(select).getGia();
//				txtGia.setText(gia + "");
				System.out.println("c.w.f.kho: JDialogNhapKho.line232.checkGia(): giá nhập - giá lớn");
				return false;
			}
		} catch (Exception e) {
			gia = listNhapKho.get(select).getGia();
			System.out.println("c.w.f.kho: JDialogNhapKho.line232.checkGia(): giá nhập - chữ");
			return false;
		}
		System.out.println("c.w.f.kho: JDialogNhapKho.line232.checkGia(): done");
		return true;
	}

	//
	public static boolean checkStockQuanExport(String txtSoLuong) {
		// bổ sung
		int select = 0;
		FormValidator validator = new FormValidator();
		List<ModelNhapKho> listNhapKho = new ArrayList<>();
		listNhapKho.add(new ModelNhapKho(1, 1000, 1000, "17-02-2023", "s0001"));
		//
		int sl = 0;
		int slkho = 0;
		try {
//			sl = Integer.parseInt(txtSoLuong.getText().trim());
			sl = Integer.parseInt(txtSoLuong.trim());
			if (validator.isLessThan(sl, 1)) {
				sl = listNhapKho.get(select).getSoLuong();
//				txtSoLuong.setText(sl + "");
				System.out.println("c.w.f.kho: JDialogXuatKho.line170.checkSL(): lượng xuất < 1");
				return false;
			}
			slkho = listNhapKho.get(select).getSoLuong();
			if (validator.isGreaterThan(sl, slkho)) {
				System.out.println("c.w.f.kho: JDialogXuatKho.line170.checkSL(): lượng xuất > tồn kho");
				sl = listNhapKho.get(select).getSoLuong();
//				txtSoLuong.setText(sl + "");
				return false;
			}
		} catch (Exception e) {
			sl = listNhapKho.get(select).getSoLuong();
			System.err.println(e);
//			txtSoLuong.setText(sl + "");
			System.out.println("c.w.f.kho: JDialogXuatKho.line170.checkSL(): lượng xuất - chữ");
			return false;
		}
		System.out.println("c.w.f.kho: JDialogXuatKho.line170.checkSL(): done");
		return true;
	}

	//
	public static boolean checkStockPriceExport(String txtGia) {
		// bổ sung
		int select = 0;
		FormValidator validator = new FormValidator();
		List<ModelNhapKho> listNhapKho = new ArrayList<>();
		listNhapKho.add(new ModelNhapKho(1, 1000, 1000, "17-02-2023", "s0001"));
		//
		float gia = 0;
		try {
//			gia = Float.parseFloat(txtGia.getText().trim());
			gia = Float.parseFloat(txtGia.trim());
			if (validator.isLessThan(gia, 1)) {
				gia = listNhapKho.get(select).getGia();
//				txtGia.setText(gia + "");
				System.out.println("c.w.f.kho: JDialogXuatKho.line203.checkGia(): giá xuất < 1");
				return false;
			}
			float gt = listNhapKho.get(select).getGia() / 2;
			if (validator.isLessThan(gia, gt)) {
				gia = listNhapKho.get(select).getGia();
//				txtGia.setText(gia + "");
				System.out.println("c.w.f.kho: JDialogXuatKho.line203.checkGia(): giá xuất < giá kho");
				return false;
			}
		} catch (Exception e) {
			gia = listNhapKho.get(select).getGia();
//			txtGia.setText(gia + "");
			System.out.println("c.w.f.kho: JDialogXuatKho.line203.checkGia(): giá xuất - chữ");
			return false;
		}
		System.out.println("c.w.f.kho: JDialogXuatKho.line203.checkGia(): done");
		return true;
	}

	//
	public boolean valideForm(String idEmp, String fullName, String phoneNumber, String email, String idNational,
			String txtNgaySinh, String avatarpath, boolean rdoNam) {
//		bổ sung
		FormNhanVien formParent = new FormNhanVien(null);
		boolean isInsert = true;
		// Get form data
//		String fullName = txtHoTen.getText().trim();
//		String idEmp = lblMaNV.getText();
//		String phoneNumber = txtSoDT.getText().trim();
//		String email = txtEmail.getText().trim();
//		String idNational = txtCCCD.getText().trim();
		// Get YearOfBirth
//		int yearOfBirth = dateChooser.getSelectedDate().getYear();
		int yearOfBirth = 0;
//		Date birthDay = XDate.toDate(txtNgaySinh.getText(), "dd-MM-yyyy");
		Date birthDay = XDate.toDate(txtNgaySinh, "dd-MM-yyyy");
		String pathAvatar = null;
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		// Get year now
		int yearNow = cal.get(Calendar.YEAR);
		// Set value for pathAvatar
//		pathAvatar = avatar.getToolTipText();
		pathAvatar = avatarpath;

		if (!FormValidator.isTextIsNotEmpty(fullName) || !FormValidator.isTextIsNotEmpty(phoneNumber)
				|| !FormValidator.isTextIsNotEmpty(email) || !FormValidator.isTextIsNotEmpty(idNational)) {
			// Check if any information is empty
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): trống");
			return false;
		} else if (!FormValidator.isTextContainsSpace(fullName)) {
			// Check if fullname is invalid
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): tên trống");
			return false;
		} else if (!FormValidator.isValidTextLength(idNational, 12)
				&& !FormValidator.isValidTextLength(idNational, 9)) {
			// Check if idNational's length is invalid
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): CMND/CCCD <9 || >12");
			return false;
		} else if (!FormValidator.isIntNumber(idNational)) {
			// Check if idNational is not number
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): CMND/CCCD - chữ");
			return false;
		}
		if (idNational.length() == 12) {
			// Is Id National
			if (!FormValidator.isValidGenderCode(idNational, rdoNam, yearOfBirth)) {
				// Check if gender code is invalid
				System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): CMND/CCCD sai mã giới tính");
				return false;
			} else if (!FormValidator.isValidYearOfBirthCode(idNational, yearOfBirth)) {
				// Check if year of birth code in id national is invalid
				System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): CMND/CCCD sai mã năm sinh");
				return false;
			} else if (!FormValidator.isValidIdNational(idNational)) {
				// Check if id national is invalid
				System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): CCCD sai định dạng");
				return false;
			}
		} else {
			// Is CMND
			if (!FormValidator.isValidIdCMND(idNational)) {
				// Check if CMND is invalid
				System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): CMND sai mã giới tính");
				return false;
			}
		}
		if (!FormValidator.isValidEmail(email)) {
			// Check if email format is invalid
			return false;
		} else if (!FormValidator.isValidTextLength(phoneNumber, 10)) {
			// Check if phone number length is invalid
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): sdt != 10 độ dài");
			return false;
		} else if (!FormValidator.isValidPhoneNumber(phoneNumber)) {
			// Check if phone number format is invalid
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): sdt sai định dạng");
			return false;
		} else if (pathAvatar == null) {
			// Check if avatar is null
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): ảnh - chưa chọn");
			return false;
		} else if (!FormValidator.isValidAge(birthDay)) {
			// Check if birth day is invalid
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): tuổi < 18");
			return false;
		}
		if (idNational.length() == 12) {
			// Is Id National
			if (!FormValidator.isIdNationalNotDuplicate(idNational, formParent.getList(), isInsert, idEmp)) {
				// Check if id national duplicate
				return false;
			}
		} else {
			// Is CMND
			if (!FormValidator.isCMNDNotDuplicate(idNational, formParent.getList(), isInsert, idEmp)) {
				// Check if cmnd duplicate
				return false;
			}
		}
		if (!FormValidator.isPhoneNumberNotDuplicate(phoneNumber, formParent.getList(), isInsert, idEmp)) {
			// Check if phone number is duplicate
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): sdt - trùng");
			return false;
		}
		if (!FormValidator.isEmailNotDuplicate(email, formParent.getList(), isInsert, idEmp)) {
			// Check if email is duplicate
			System.out.println("c.w.f.nhanvien: EditNhanVien.line165.valideForm(): email - trùng");
			return false;
		}
//		setNhanVien(getForm());
		return true;
	}
	
	//
	
	public static int minInThem(int...mangList) {
		int min = 999;
		for (int i = 0; i < mangList.length; i++) {
			if(mangList[i] < min) {
				min = mangList[i];
			}
		}
		System.out.println(min);
		return min;
	}
	
	//
	
	public static int OneOrZero(Object checkNum) {
		int num = 0;
		try {
			if (Integer.valueOf(String.valueOf(checkNum)) > num) {
				num = 1;
			}
		} catch (Exception e) {
			num = 0;
		}
		return num;
	}

	//
}
