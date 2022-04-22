package com.wolvesres.model;

import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.XFormatMoney;
import java.util.ArrayList;
import java.util.List;

public class ModelHoaDonChiTiet {

    private int maHDCT;
    private String maHD;
    private String maSP;
    private int soLuong;
    private float donGia;

    public ModelHoaDonChiTiet() {
    }

    public ModelHoaDonChiTiet(int maHDCT, String maHD, String maSP, int soLuong, float donGia) {
        this.maHDCT = maHDCT;
        this.maHD = maHD;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    /**
     * @return the maHD
     */
    public String getMaHD() {
        return maHD;
    }

    /**
     * @param maHD the maHD to set
     */
    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    /**
     * @return the maHDCT
     */
    public int getMaHDCT() {
        return maHDCT;
    }

    /**
     * @param maHDCT the maHDCT to set
     */
    public void setMaHDCT(int maHDCT) {
        this.maHDCT = maHDCT;
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
     * @return the donGia
     */
    public float getDonGia() {
        return donGia;
    }

    /**
     * @param donGia the donGia to set
     */
    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }

    private List<ModelSanPham> sp = new ArrayList<>();
    SanPhamDAO spdao = new SanPhamDAO();

    //Chuyen ma thanh ten sp
    public String TenSP(String maSP) {
        String TenSP = "";
        sp.addAll(spdao.selectAll());
        for (ModelSanPham msp : sp) {
            if (maSP.equals(msp.getMaSP())) {
                TenSP = msp.getTenSP();
            }
        }
        return TenSP;
    }

    //
    public float Gia(float g, int sl) {
        float Tgia = 0;
        Tgia = g * sl;
        return Tgia;
    }

    //
    public Object[] toRowTableHDCT() {
        return new Object[]{TenSP(getMaSP()), XFormatMoney.formatMoney(getDonGia()), getSoLuong(), XFormatMoney.formatMoney(Gia(getDonGia(), getSoLuong()))};
    }

	@Override
	public String toString() {
		String temp = String.format("%s-%s-%s-%s", this.maHD, this.maSP, this.soLuong, this.donGia);
		return temp;
	}
}
