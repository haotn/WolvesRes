package com.wolvesres.model;

import com.wolvesres.dao.BanOrderDAO;

/**
 * 
 * Thêm: gopbandata, gopbannodata
 */
public class ModelBanOrder {

	private String maBan;
	private String maVoucher;
	private String ghiChu;
	private String maBanGop;
	private BanOrderDAO banoderdao = new BanOrderDAO();

	@Override
	public String toString() {
		return "ModelBanOrder [maBan=" + maBan + ", maVoucher=" + maVoucher + ", ghiChu=" + ghiChu + ", maBanGop="
				+ maBanGop + "]";
	}

	public ModelBanOrder() {
	}

	public ModelBanOrder(String maBan, String ghiChu, String maVoucher, String maBanGop) {
		this.maBan = maBan;
		this.maVoucher = maVoucher;
		this.ghiChu = ghiChu;
		this.maBanGop = maBanGop;
	}

	public String getMaVoucher() {
		return maVoucher;
	}

	public void setMaVoucher(String maVoucher) {
		this.maVoucher = maVoucher;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getMaBan() {
		return maBan;
	}

	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}

	public String getMaBanGop() {
		return maBanGop;
	}

	public void setMaBanGop(String maBanGop) {
		this.maBanGop = maBanGop;
	}

	private BanOrderDAO dao = new BanOrderDAO();

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
		dao.update(this, this.maBan);
	}

	/**
	 * Delete from database
	 */
	public void remove() {
		dao.delete(this.maBan);
	}

//gop ban có dữ liệu 
	public void gopbandata(String maBanGop) {
		this.setMaBanGop(maBanGop);
		banoderdao.update(this, this.maBan);
		banoderdao.delete(maBanGop);
	}

// gộp ban không dữ liệu
	public void gopnodata(String maBanht, String maBanGop) {
		this.setMaBan(maBanht);
		this.setGhiChu(null);
		this.setMaVoucher("NOVOUCHER");
		this.setMaBanGop(maBanGop);
		banoderdao.insert(this);
	}

}
