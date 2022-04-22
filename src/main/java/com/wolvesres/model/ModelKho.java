package com.wolvesres.model;

import com.wolvesres.dao.ChiTietLichSuDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.XFormatMoney;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Them insert, update
 */
public class ModelKho {

	private int id;
	private int idls;
	private String maSP;
	private int soLuong;
	private String hanSuDung;
	private boolean trangThai;
	private List<ModelSanPham> listSP;
	private KhoDAO khodao = new KhoDAO();
/////////////////////////////

	@Override
	public String toString() {
		return "ModelKho [id=" + id + ", idls=" + idls + ", maSP=" + maSP + ", soLuong=" + soLuong + ", hanSuDung="
				+ hanSuDung + ", trangThai=" + trangThai + "]";
	}

	public ModelKho() {
	}

	public ModelKho(int id, int idls, String maSP, int soLuong, String hanSuDung, boolean trangThai) {
		this.id = id;
		this.idls = idls;
		this.maSP = maSP;
		this.soLuong = soLuong;
		this.hanSuDung = hanSuDung;
		this.trangThai = trangThai;
	}
 
	public ModelKho( int idls, String maSP, int soLuong, String hanSuDung, boolean trangThai) {
		this.id = id;
		this.idls = idls;
		this.maSP = maSP;
		this.soLuong = soLuong;
		this.hanSuDung = hanSuDung;
		this.trangThai = trangThai;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
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
	 * @return the soLuong
	 */
	public int getSoLuong() {
		return soLuong;
	}

	/**
	 * @param soLuong the soLuong to set
	 */
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	/**
	 * @return the hanSuDung
	 */
	public String getHanSuDung() {
		return hanSuDung;
	}

	/**
	 * @param hanSuDung the hanSuDung to set
	 */
	public void setHanSuDung(String hanSuDung) {
		this.hanSuDung = hanSuDung;
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

	public int getIdls() {
		return idls;
	}

	public void setIdls(int idls) {
		this.idls = idls;
	}

	KhoDAO dao = new KhoDAO();

	/**
	 * Insert to database
	 */
	public void insert() {
		dao.insert(this);
	}

	/**
	 * Update to database
	 */
	public void update() {
		dao.update(this, this.id);
	}

	/**
	 * Detete from database
	 */
	public void remove() {
		dao.delete(this.id);
	}

	/**
	 * Convert to Object[]
	 * 
	 * @param list
	 * @return Object[]
	 */
	public Object[] toRowTable(List<ModelSanPham> list) {
		ModelSanPham sp = null;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMaSP().equals(getMaSP())) {
				sp = list.get(i);
			}
		}
		return new Object[] { getIdls(), sp.getTenSP(), getSoLuong(), getHanSuDung() };
	}

	/**
	 * Set list sp
	 * 
	 * @param list
	 */
	public void setListSP(List<ModelSanPham> list) {
		this.listSP = list;
	}

	/**
	 * Convert to Object[]
	 * 
	 * @param list
	 * @return Object[]
	 */
	public Object[] toTableRow() {
		ModelSanPham sp = null;
		for (int i = 0; i < listSP.size(); i++) {
			if (listSP.get(i).getMaSP().equals(getMaSP())) {
				sp = listSP.get(i);
			}
		}
		return new Object[] { sp.getTenSP(), soLuong, hanSuDung };
	}

	List<ModelSanPham> sp = new ArrayList<>();
	SanPhamDAO spdao = new SanPhamDAO();

	public String getTenSanPham(String ma) {
		String ten = "";
		sp.addAll(spdao.selectAll());
		for (ModelSanPham m : sp) {
			if (ma.equals(m.getMaSP())) {
				ten = m.getTenSP();
			}
		}
		return ten;
	}
////////////////////////////////////////////////////

	public ModelSanPham getSanPham() {
		ModelSanPham sp = new ModelSanPham();
		for (ModelSanPham modelSanPham : spdao.selectAll()) {
			if (modelSanPham.getMaSP().equals(getMaSP())) {
				sp = modelSanPham;
			}
		}
		return sp;
	}

///////////////////////////////////
	List<ModelChiTietLichSu> ct = new ArrayList<>();
	ChiTietLichSuDAO ctdao = new ChiTietLichSuDAO();

	/**
	 * Get gia sanPham
	 * 
	 * @param ma
	 * @param masp
	 * @return giaSanPham
	 */
	public float getGiaSanPham(int ma, String masp) {
		float gia = 0;
		ct.addAll(ctdao.selectAll());
		for (ModelChiTietLichSu mdct : ct) {
			if (ma == mdct.getIdLS() && masp.equals(mdct.getMaSP())) {
				gia = mdct.getDonGia();
				break;
			}
		}
		return gia;
	}

	/**
	 * Convert to Object[]
	 * 
	 * @return Object[]
	 */
	public Object[] toRowTable() {
		return new Object[] { getTenSanPham(getMaSP()), getSoLuong(),
				XFormatMoney.formatMoney(getGiaSanPham(getIdls(), getMaSP())), getHanSuDung() };
	}

///////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Convert to Object[]
	 * 
	 * @return Object[]
	 */
	public Object[] toRowTableXK() {
		return new Object[] { this, getSoLuong(), XFormatMoney.formatMoney(getGiaSanPham(getIdls(), getMaSP())),
				getHanSuDung() };
	}

	// insert
	public void insert(int idLichSu, String maSanPham, int soLuong, String date) {
		this.setIdls(idLichSu);
		this.setMaSP(maSanPham);
		this.setSoLuong(soLuong);
		this.setHanSuDung(date);
		this.setTrangThai(true);
		khodao.insert(this);
	}

	// update
	public void update(int id) {
		khodao.update(this, id);
	}
	
}
