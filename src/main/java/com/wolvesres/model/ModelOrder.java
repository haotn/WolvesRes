package com.wolvesres.model;

import com.wolvesres.dao.OrderDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.swing.table.EventActionBlackList;
import com.wolvesres.swing.table.ModelActionBlackList;
import java.util.ArrayList;
import java.util.List;

public class ModelOrder {

	private int id;
	private String maSP;
	private String maBan;
	private float gia;
	private int soLuong;

	@Override
	public String toString() {
		return "ModelOrder [maSP=" + maSP + ", maBan=" + maBan + ", gia=" + gia + ", soLuong=" + soLuong
				+ "]";
	}

	public ModelOrder() {
	}

	public ModelOrder(int id, String maSP, String maBan, float gia, int soLuong) {
		this.id = id;
		this.maSP = maSP;
		this.maBan = maBan;
		this.gia = gia;
		this.soLuong = soLuong;
	}

	public ModelOrder(String maSP, String maBan, float gia, int soLuong) {
		this.maSP = maSP;
		this.maBan = maBan;
		this.gia = gia;
		this.soLuong = soLuong;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) {
		this.maBan = maBan;
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
	 * @return the gia
	 */
	public float getGia() {
		return gia;
	}

	/**
	 * @param gia the gia to set
	 */
	public void setGia(float gia) {
		this.gia = gia;
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

	OrderDAO dao = new OrderDAO();

	/**
	 * Insert to database
	 */
	public void insert() {
		dao.insert(this);
	}

	/**
	 * Udpate to database
	 */
	public void update() {
		dao.update(this, "null available");
	}

	/**
	 * Delete from database
	 */
	public void remove() {
		dao.delete(this.maBan);
	}

	public void removeProduct() {
		dao.deleteProduct(this.maSP, this.maBan);
	}

	List<ModelSanPham> listSanPham = new ArrayList<>();
	SanPhamDAO spdao = new SanPhamDAO();

	/**
	 * Get sanPham
	 * 
	 * @return
	 */
	public ModelSanPham getSanPham() {
		ModelSanPham product = new ModelSanPham();
		for (ModelSanPham entity : spdao.selectAll()) {
			if (getMaSP().equals(entity.getMaSP())) {
				product = entity;
			}
		}
		return product;
	}

	/**
	 * Get ten sp
	 * 
	 * @param ma
	 * @return tensp
	 */
	public String getTenSanPham(String ma) {
		String ten = "";
		listSanPham.addAll(spdao.selectAll());
		for (ModelSanPham m : listSanPham) {
			if (ma.equals(m.getMaSP())) {
				ten = m.getTenSP();
			}
		}
		return ten;
	}

	/**
	 * Convert to Object[]
	 * 
	 * @param event
	 * @param list
	 * @return Object[]
	 */
	public Object[] toRowTable(EventActionBlackList event, List<ModelSanPham> list) {
		String tenSP = "";
		for (ModelSanPham sp : list) {
			if (getMaSP().equals(sp.getMaSP())) {
				tenSP = sp.getTenSP();
			}
		}
		return new Object[] { tenSP, XFormatMoney.formatMoney(getGia()), getSoLuong(),
				XFormatMoney.formatMoney(getSoLuong() * getGia()), new ModelActionBlackList(this, event) };
	}

}
