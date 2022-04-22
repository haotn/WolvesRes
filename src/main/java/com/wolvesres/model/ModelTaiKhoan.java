package com.wolvesres.model;

import com.wolvesres.dao.GhiNhoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;

import com.wolvesres.helper.XImage;
import com.wolvesres.helper.XIpAddress;
import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.EventActionBlackList;
import com.wolvesres.swing.table.ModelAction;
import com.wolvesres.swing.table.ModelActionBlackList;
import com.wolvesres.swing.table.ModelProfile;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * Them: update(password)
 * */

/**
 * Thêm các hàm thêm sửa xóa addblacktolist
 * 
 * @author huynh
 *
 */
public class ModelTaiKhoan {

	private String taiKhoan;
	private String matKhau;
	private boolean trangThai;
	private TaiKhoanDAO dao = new TaiKhoanDAO();
	GhiNhoDAO gndao = new GhiNhoDAO();

	@Override
	public String toString() {
		return "ModelTaiKhoan [taiKhoan=" + taiKhoan + ", matKhau=" + matKhau + ", trangThai=" + trangThai + "]";
	}

	public ModelTaiKhoan() {
	}

	public ModelTaiKhoan(String taiKhoan, String matKhau, boolean trangThai) {
		this.taiKhoan = taiKhoan;
		this.matKhau = matKhau;
		this.trangThai = trangThai;
	}

	/**
	 * @return the taiKhoan
	 */
	public String getTaiKhoan() {
		return taiKhoan;
	}

	/**
	 * @param taiKhoan the taiKhoan to set
	 */
	public void setTaiKhoan(String taiKhoan) {
		this.taiKhoan = taiKhoan;
	}

	/**
	 * @return the matKhau
	 */
	public String getMatKhau() {
		return matKhau;
	}

	/**
	 * @param matKhau the matKhau to set
	 */
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
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

	private NhanVienDAO nhanVienDAO = new NhanVienDAO();

	public ModelNhanVien getNhanVien() {
		ModelNhanVien emp = new ModelNhanVien();
		for (ModelNhanVien modelNhanVien : nhanVienDAO.selectAll()) {
			if (getTaiKhoan().equals(modelNhanVien.getMaNV())) {
				emp = modelNhanVien;
			}
		}
		return emp;
	}

	public Object[] toRowTable(EventActionBlackList event) {
		Icon iconAction = new ImageIcon(getClass().getResource("/com/wolvesres/icon/return.png"));
		Icon icon = XImage.readImageNhanVien(getNhanVien().getPathHinhAnh());
		return new Object[] { new ModelProfile(icon, getNhanVien().getMaNV()), getNhanVien().getHoTen(),
				getNhanVien().getTenChucVu(getNhanVien().getChucVu()),
				new ModelActionBlackList(iconAction, this, event) };
	}

	public Object[] toRowTableTK(EventAction event) {
		Icon icon = XImage.readImageNhanVien(getNhanVien().getPathHinhAnh());
		Icon iconUpdate = new ImageIcon(getClass().getResource("/com/wolvesres/icon/disabled.png"));
		return new Object[] { new ModelProfile(icon, getNhanVien().getMaNV()), getNhanVien().getHoTen(),
				getNhanVien().getTenChucVu(getNhanVien().getChucVu()), new ModelAction(iconUpdate, null, this, event) };
	}

//    update
	public void update(String password) {
		this.setMatKhau(password);
		dao.update(this, this.getTaiKhoan());
		gndao.delete(XIpAddress.getIPAddres());
	}

}
