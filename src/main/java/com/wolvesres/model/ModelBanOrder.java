package com.wolvesres.model;

public class ModelBanOrder {

    private String maBan;
    private String maVoucher;
    private String ghiChu;
    private String maBanGop;

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
}
