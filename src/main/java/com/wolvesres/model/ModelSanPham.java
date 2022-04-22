package com.wolvesres.model;

import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.helper.XImage;
import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.EventActionBlackList;
import com.wolvesres.swing.table.ModelAction;
import com.wolvesres.swing.table.ModelActionBlackList;
import com.wolvesres.swing.table.ModelProfile;

import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
/**
 * thêm các hàm thêm sửa xóa
 * @author huynh
 *
 */
public class ModelSanPham {

	private Icon icon;
	private String maSP;
	private String tenSP;
	private float giaBan;
	private String maDanhMuc;
	private String pathAnh;
	private int maDVT;
	private boolean trangThai;
	private SanPhamDAO dao = new SanPhamDAO();

	public int getMaDVT() {
		return maDVT;
	}

	public void setMaDVT(int maDVT) {
		this.maDVT = maDVT;
	}

	@Override
	public String toString() {
		String temp = String.format("%s-%s-%s-%s-%s-%s-%s", this.maSP, this.tenSP, this.giaBan, this.maDanhMuc, this.pathAnh, this.maDVT, this.trangThai);
		return temp;
	}
	
	/**
	 * Đưa ra khỏi anh sách đen
	 * @param list
	 */
	public void addToBlackList(List<ModelSanPham> list) {
		this.trangThai = true;
		dao.update(this, this.getMaSP());
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).getMaSP().equals(this.getMaSP())) {
				list.set(i, this);
				break;
			}
		}
	}
	

	public ModelSanPham() {
	}

	public ModelSanPham(String maSP, String tenSP, float giaBan, String maDanhMuc, String pathAnh, int maDVT,
			boolean trangThai) {
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.giaBan = giaBan;
		this.maDanhMuc = maDanhMuc;
		this.pathAnh = pathAnh;
		this.maDVT = maDVT;
		this.trangThai = trangThai;
	}

	public ModelSanPham(Icon icon, String maSP, String tenSP, float giaBan, String maDanhMuc, String pathAnh, int maDVT,
			boolean trangThai) {
		this.icon = icon;
		this.maSP = maSP;
		this.tenSP = tenSP;
		this.giaBan = giaBan;
		this.maDanhMuc = maDanhMuc;
		this.pathAnh = pathAnh;
		this.maDVT = maDVT;
		this.trangThai = trangThai;

	}

	public Object[] toRowTable(EventAction event, List<ModelDanhMuc> listDanhMucs) {
		Object[] row = new Object[] {};
		for (int i = 0; i < listDanhMucs.size(); i++) {
			if (getMaDanhMuc().equals(listDanhMucs.get(i).getMaDanhMuc())) {
				row = new Object[] { new ModelProfile(getIcon(), getTenSP(), this),
						String.valueOf(listDanhMucs.get(i).getTenDanhMuc()), XFormatMoney.formatMoney(getGiaBan()),
						String.valueOf(listDanhMucs.get(i).isMatHang() ? "Mặt hàng" : "Món ăn"),
						new ModelAction(this, event) };
			}
		}
		return row;
	}

	public Object[] toRowTableBlackList(EventActionBlackList event, List<ModelDanhMuc> listDanhMucs) {
		Icon icon = new ImageIcon(getClass().getResource("/com/wolvesres/icon/return.png"));
		Object[] row = new Object[] {};
		for (int i = 0; i < listDanhMucs.size(); i++) {
			if (getMaDanhMuc().equals(listDanhMucs.get(i).getMaDanhMuc())) {
				row = new Object[] { new ModelProfile(getIcon(), getTenSP(), this),
						String.valueOf(listDanhMucs.get(i).getTenDanhMuc()), XFormatMoney.formatMoney(getGiaBan()),
						String.valueOf(listDanhMucs.get(i).isMatHang() ? "Mặt hàng" : "Món ăn"),
						new ModelActionBlackList(icon, this, event) };
			}
		}
		return row;
	}

	public Object[] toRowTableMINV(List<ModelDanhMuc> listDanhMucs) {
		Object[] row = new Object[] {};
		for (int i = 0; i < listDanhMucs.size(); i++) {
			if (getMaDanhMuc().equals(listDanhMucs.get(i).getMaDanhMuc())) {
				row = new Object[] { new ModelProfile(getIcon(), getTenSP(), this), listDanhMucs.get(i).getTenDanhMuc(),
						XFormatMoney.formatMoney(getGiaBan()),
						listDanhMucs.get(i).isMatHang() ? "Mặt hàng" : "Món ăn" };
			}
		}
		return row;
	}

	DanhMucDAO danhMucDAO = new DanhMucDAO();
	DonViTinhDAO donViTinhDAO = new DonViTinhDAO();

	public String getTenDanhMuc() {
		String ten = "";
		for (ModelDanhMuc dm : danhMucDAO.selectAll()) {
			if (getMaDanhMuc().equals(dm.getMaDanhMuc())) {
				ten = dm.getTenDanhMuc();
			}
		}
		return ten;
	}

	public String getLoaiSanPham() {
		String tenLoai = "";
		for (ModelDanhMuc danhMuc : danhMucDAO.selectAll()) {
			if (getMaDanhMuc().equals(danhMuc.getMaDanhMuc())) {
				if (danhMuc.isMatHang()) {
					tenLoai = "Mặt hàng";
				} else {
					tenLoai = "Sản phẩm";
				}
			}
		}
		return tenLoai;
	}

	public String getTenDonViTinh() {
		String tenDonViTinh = "";
		for (ModelDonViTinh donViTinh : donViTinhDAO.selectAll()) {
			if (getMaDVT() == donViTinh.getMaDVT()) {
				tenDonViTinh = donViTinh.getTenDVT();
			}
		}
		return tenDonViTinh;
	}

	public boolean isMatHang() {
		ModelDanhMuc dm = new ModelDanhMuc();
		for (ModelDanhMuc danhMuc : danhMucDAO.selectAll()) {
			if (getMaDanhMuc().equals(danhMuc.getMaDanhMuc())) {
				dm = danhMuc;
			}
		}
		return dm.isMatHang();
	}

	public Object[] toRowTable(List<ModelDanhMuc> listDanhMucs) {
		Object[] row = new Object[] {};
		for (int i = 0; i < listDanhMucs.size(); i++) {
			if (getMaDanhMuc().equals(listDanhMucs.get(i).getMaDanhMuc())) {
				row = new Object[] { new ModelProfile(getIcon(), getTenSP(), this), listDanhMucs.get(i).getTenDanhMuc(),
						getGiaBan() };
			}
		}
		return row;
	}

	/**
	 * @return the icon
	 */
	/////////////////////////////////
	public Icon getIcon() {
		if (icon == null) {
			icon = (XImage.readImageThucDon(getPathAnh()));
		}
		return icon;
	}
/////////////////////////////////////////////////////////

	/**
	 * @param icon the icon to set
	 */
	public void setIcon(Icon icon) {
		this.icon = icon;
	}

	/**
	 * @return the maSP
	 */
	public String getMaSP() {
		return maSP;
	}

	/**
	 * @param maSP the maSP to set
	 */
	public void setMaSP(String maSP) {
		this.maSP = maSP;
	}

	/**
	 * @return the tenSP
	 */
	public String getTenSP() {
		return tenSP;
	}

	/**
	 * @param tenSP the tenSP to set
	 */
	public void setTenSP(String tenSP) {
		this.tenSP = tenSP;
	}

	/**
	 * @return the giaBan
	 */
	public float getGiaBan() {
		return giaBan;
	}

	/**
	 * @param giaBan the giaBan to set
	 */
	public void setGiaBan(float giaBan) {
		this.giaBan = giaBan;
	}

	/**
	 * @return the maDanhMuc
	 */
	public String getMaDanhMuc() {
		return maDanhMuc;
	}

	/**
	 * @param maDanhMuc the maDanhMuc to set
	 */
	public void setMaDanhMuc(String maDanhMuc) {
		this.maDanhMuc = maDanhMuc;
	}

	/**
	 * @return the pathAnh
	 */
	public String getPathAnh() {
		return pathAnh;
	}

	/**
	 * @param pathAnh the pathAnh to set
	 */
	public void setPathAnh(String pathAnh) {
		this.pathAnh = pathAnh;
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

	public Object[] toRowNhapKho(List<ModelDanhMuc> listDanhMucs) {
		Object[] row = new Object[] {};
		for (int i = 0; i < listDanhMucs.size(); i++) {
			if (getMaDanhMuc().equals(listDanhMucs.get(i).getMaDanhMuc())) {
				row = new Object[] { new ModelProfile(getIcon(), getTenSP(), this), listDanhMucs.get(i).getTenDanhMuc(),
						XFormatMoney.formatMoney(getGiaBan()) };
			}
		}
		return row;
	}

	public Object[] toRowTableBanHang(EventActionBlackList event, List<ModelKho> listKhos,
			List<ModelDanhMuc> listDanhMucs) {
		Object[] row = new Object[] {};
		for (int i = 0; i < listDanhMucs.size(); i++) {
			if (getMaDanhMuc().equals(listDanhMucs.get(i).getMaDanhMuc())) {
				if (listDanhMucs.get(i).isMatHang()) {
					for (ModelKho kho : listKhos) {
						if (getMaSP().equals(kho.getMaSP())) {
							if (kho.getSoLuong() > 0) {
								row = new Object[] { new ModelProfile(getIcon(), getTenSP(), this),
										listDanhMucs.get(i).getTenDanhMuc(), XFormatMoney.formatMoney(getGiaBan()),
										kho.getSoLuong(),
										new ModelActionBlackList(
												new ImageIcon(getClass()
														.getResource("/com/wolvesres/icon/addToListOrder.png")),
												this, event) };
							}
						}
					}
				} else {
					row = new Object[] { new ModelProfile(getIcon(), getTenSP(), this),
							listDanhMucs.get(i).getTenDanhMuc(), XFormatMoney.formatMoney(getGiaBan()), null,
							new ModelActionBlackList(
									new ImageIcon(getClass().getResource("/com/wolvesres/icon/addToListOrder.png")),
									this, event) };
				}
			}
		}
		return row;
	}
}
