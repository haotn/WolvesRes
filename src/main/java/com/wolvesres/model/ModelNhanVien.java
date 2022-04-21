package com.wolvesres.model;

import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.helper.XImage;
import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.EventActionBlackList;
import com.wolvesres.swing.table.ModelAction;
import com.wolvesres.swing.table.ModelActionBlackList;
import com.wolvesres.swing.table.ModelProfile;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Chinh sua ten phuong thuc getTenChucVu()| Them phuong thuc insert(),
 * update(), delete() | Cac class lien quan: FormNhanVien, NhanVienDAO,
 * EditNhanVien, BlackListNhanVien
 * 
 * @author Brian
 *
 */
public class ModelNhanVien {

	private Icon icon;
	private String maNV;
	private String hoTen;
	private boolean gioiTinh;
	private String ngaySinh;
	private String CMND;
	private String soDT;
	private String email;
	private String pathHinhAnh;
	private int ChucVu;
	private boolean trangThai;
	
	public String print() {
		return this.getMaNV() + " - " + this.getHoTen() + " - " + this.isGioiTinh() + " - " + this.getNgaySinh() + " - "
				+ this.getCMND() + " - " + this.getSoDT() + " - " + this.getEmail() + " - " + this.getPathHinhAnh()
				+ " - " + this.getChucVu() + " - " + this.isTrangThai();
	}
	
	public String toString() {
		return this.getMaNV() + " - " + this.getHoTen();
	}

	public ModelNhanVien() {
	}

	public ModelNhanVien(String maNV, String hoTen, boolean gioiTinh, String ngaySinh, String CMND, String soDT,
			String email, String pathHinhAnh, int ChucVu, boolean trangThai) {
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.CMND = CMND;
		this.soDT = soDT;
		this.email = email;
		this.pathHinhAnh = pathHinhAnh;
		this.ChucVu = ChucVu;
		this.trangThai = trangThai;
	}

	public ModelNhanVien(Icon icon, String maNV, String hoTen, boolean gioiTinh, String ngaySinh, String CMND,
			String soDT, String email, String pathHinhAnh, int ChucVu, boolean trangThai) {
		this.icon = icon;
		this.maNV = maNV;
		this.hoTen = hoTen;
		this.gioiTinh = gioiTinh;
		this.ngaySinh = ngaySinh;
		this.CMND = CMND;
		this.soDT = soDT;
		this.email = email;
		this.pathHinhAnh = pathHinhAnh;
		this.ChucVu = ChucVu;
		this.trangThai = trangThai;
	}

	/**
	 * @return the maNV
	 */
	public String getMaNV() {
		return maNV;
	}

	/**
	 * @param maNV the maNV to set
	 */
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	/**
	 * @return the hoTen
	 */
	public String getHoTen() {
		return hoTen;
	}

	/**
	 * @param hoTen the hoTen to set
	 */
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}

	/**
	 * @return the CMND
	 */
	public String getCMND() {
		return CMND;
	}

	/**
	 * @param CMND the CMND to set
	 */
	public void setCMND(String CMND) {
		this.CMND = CMND;
	}

	/**
	 * @return the soDT
	 */
	public String getSoDT() {
		return soDT;
	}

	/**
	 * @param soDT the soDT to set
	 */
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pathHinhAnh
	 */
	public String getPathHinhAnh() {
		return pathHinhAnh;
	}

	/**
	 * @param pathHinhAnh the pathHinhAnh to set
	 */
	public void setPathHinhAnh(String pathHinhAnh) {
		this.pathHinhAnh = pathHinhAnh;
	}

	/**
	 * @return the ChucVu
	 */
	public int getChucVu() {
		return ChucVu;
	}

	/**
	 * @param ChucVu the ChucVu to set
	 */
	public void setChucVu(int ChucVu) {
		this.ChucVu = ChucVu;
	}

	/**
	 * @return the trangThai
	 */
	public boolean isTrangThai() {
		return trangThai;
	}

	/**
	 * @param trangThai the trangThai to set
	 */
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}

	/**
	 * @return the icon
	 */
	public Icon getIcon() {
		if (icon == null) {
			icon = XImage.readImageNhanVien(getPathHinhAnh());
		}
		return icon;
	}

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * Generate dao class
	 */
	private NhanVienDAO dao = new NhanVienDAO();

	/**
	 * Insert current NhanVien to database
	 */
	public void insert() {
		dao.insert(this);
	}

	/**
	 * Update current NhanVien to database
	 */
	public void update() {
		dao.update(this, this.maNV);
	}

	/**
	 * Delete current NhanVien from database
	 */
	public void remove() {
		dao.delete(this.maNV);
	}

	/**
	 * Change trangthai to false
	 */
	public void addToBlackList() {
		this.setTrangThai(false);
		dao.update(this, this.maNV);
	}

	/**
	 * Change trangthai to true
	 */
	public void addToWhiteList() {
		this.setTrangThai(true);
		dao.update(this, this.maNV);
	}

	/**
	 * Convert properties to Object array
	 * 
	 * @param event
	 * @return Object[]
	 */

	public Object[] toRowTable(EventAction event) {
		return new Object[] { new ModelProfile(icon, hoTen, this), gioiTinh ? "Nam" : "Nữ", getTenChucVu(ChucVu), soDT,
				new ModelAction("Cập nhật thông tin", "Xóa nhân viên", null, null, this, event) };
	}

	/**
	 * Convert properties to Object array, show in table blacklist
	 * 
	 * @param event
	 * @return Object[]
	 */
	public Object[] toRowTableBlackList(EventActionBlackList event) {
		Icon icon = new ImageIcon(getClass().getResource("/com/wolvesres/icon/return.png"));
		return new Object[] { new ModelProfile(getIcon(), hoTen, this), gioiTinh ? "Nam" : "Nữ", getTenChucVu(ChucVu),
				soDT, new ModelActionBlackList(icon, "Đưa ra khỏi danh sách đen", this, event) };
	}

	/**
	 * @return the ngaySinh
	 */
	public String getNgaySinh() {
		return ngaySinh;
	}

	/**
	 * @param ngaySinh the ngaySinh to set
	 */
	public void setNgaySinh(String ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	/**
	 * @return the gioiTinh
	 */
	public boolean isGioiTinh() {
		return gioiTinh;
	}

	/**
	 * @param gioiTinh the gioiTinh to set
	 */
	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	/**
	 * Get ten chuc vu
	 * 
	 * @param chucVu
	 * @return ten chuc vu
	 */
	public String getTenChucVu(int chucVu) {
		String chucVuString = null;
		if (chucVu == 1) {
			chucVuString = "Chủ nhà hàng";
		} else if (chucVu == 2) {
			chucVuString = "Quản lý";
		} else if (chucVu == 3) {
			chucVuString = "Thu ngân";
		} else {
			chucVuString = "Nhân viên khác";
		}
		return chucVuString;
	}
}
