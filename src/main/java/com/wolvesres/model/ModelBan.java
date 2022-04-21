package com.wolvesres.model;

import com.wolvesres.dao.BanDAO;
import com.wolvesres.dao.KhuBanDAO;
import java.util.ArrayList;
import java.util.List;

public class ModelBan {
	private String maBan;
	private String tenBan;
	private boolean hoatDong;
	private String maKhuBan;



	@Override
	public String toString() {
		return maBan + " - " + tenBan + " - " + hoatDong + " - " + maKhuBan;
	}

	public ModelBan() {
	}

	public ModelBan(String maBan, String tenBan, boolean hoatDong, String maKhuBan) {
		this.maBan = maBan;
		this.tenBan = tenBan;
		this.hoatDong = hoatDong;
		this.maKhuBan = maKhuBan;
	}

	/**
	 * @return the maBan
	 */
	public String getMaBan() {
		return maBan;
	}

	/**
	 * @param maBan the maBan to set
	 */
	public void setMaBan(String maBan) {
		this.maBan = maBan;
	}

	/**
	 * @return the maKhuBan
	 */
	public String getMaKhuBan() {
		return maKhuBan;
	}

	/**
	 * @param maKhuBan the maKhuBan to set
	 */
	public void setMaKhuBan(String maKhuBan) {
		this.maKhuBan = maKhuBan;
	}

	/**
	 * @return the tenBan
	 */
	public String getTenBan() {
		return tenBan;
	}

	/**
	 * @param tenBan the tenBan to set
	 */
	public void setTenBan(String tenBan) {
		this.tenBan = tenBan;
	}

	/**
	 * @return the hoatDong
	 */
	public boolean isHoatDong() {
		return hoatDong;
	}

	/**
	 * DAO
	 */
	BanDAO banDao = new BanDAO();

	/**
	 * Insert to database
	 */
	public void insert() {
		banDao.insert(this);
	}

	/**
	 * Update to database
	 */
	public void update() {
		banDao.update(this, this.maBan);
	}

	/**
	 * Delete from database
	 */
	public void remove() {
		banDao.delete(this.maBan);
	}

	/**
	 * @param hoatDong the hoatDong to set
	 */
	public void setHoatDong(boolean hoatDong) {
		this.hoatDong = hoatDong;
	}

	KhuBanDAO dao = new KhuBanDAO();
	List<ModelKhuBan> li = new ArrayList<>();

	//
	public String TenKB(String ma) {
		String tenString = "";
		li.addAll(dao.selectAll());
		for (ModelKhuBan kb : li) {
			if (ma.equals(kb.getMaKhuBan())) {
				tenString = kb.getTenKhuBan();
			}
		}
		return tenString;
	}

	//
	public Object[] toRowTable() {
		return new Object[] { getTenBan(), isHoatDong() ? "Trống" : "Oder" };
	}
}
