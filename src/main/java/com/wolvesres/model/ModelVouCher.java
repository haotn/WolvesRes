package com.wolvesres.model;

import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.EventActionBlackList;
import com.wolvesres.swing.table.ModelAction;
import com.wolvesres.swing.table.ModelActionBlackList;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class ModelVouCher {

    private String maVoucher;
    private String ngayBatDau;
    private String ngayKetThuc;
    private float giamGia;
    private int soLuong;
    private String pathQR;
    private boolean trangThai;

    public ModelVouCher() {
    }

    public ModelVouCher(String maVoucher, String ngayBatDau, String ngayKetThuc, float giamGia, int soLuong, String pathQR, boolean trangThai) {
        this.maVoucher = maVoucher;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.giamGia = giamGia;
        this.soLuong = soLuong;
        this.pathQR = pathQR;
        this.trangThai = trangThai;
    }

    /**
     * @return the maVoucher
     */
    public String getMaVoucher() {
        return maVoucher;
    }

    /**
     * @param maVoucher the maVoucher to set
     */
    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    /**
     * @return the ngayBatDau
     */
    public String getNgayBatDau() {
        return ngayBatDau;
    }

    /**
     * @param ngayBatDau the ngayBatDau to set
     */
    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    /**
     * @return the ngayKetThuc
     */
    public String getNgayKetThuc() {
        return ngayKetThuc;
    }

    /**
     * @param ngayKetThuc the ngayKetThuc to set
     */
    public void setNgayKetThuc(String ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    /**
     * @return the giamGia
     */
    public float getGiamGia() {
        return giamGia;
    }

    /**
     * @param giamGia the giamGia to set
     */
    public void setGiamGia(float giamGia) {
        this.giamGia = giamGia;
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
     * @return the pathQR
     */
    public String getPathQR() {
        return pathQR;
    }

    /**
     * @param pathQR the pathQR to set
     */
    public void setPathQR(String pathQR) {
        this.pathQR = pathQR;
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

    public Object[] toRowTable(EventAction<ModelVouCher> event) {
        return new Object[]{getMaVoucher(), getGiamGia(), getSoLuong(), new ModelAction(this, event)};
    }

    public Object[] toRowTableWINV() {
        return new Object[]{getMaVoucher(), getGiamGia(), getSoLuong()};
    }

    public Object[] toRowTableBlackList(EventActionBlackList event) {
        Icon icon = new ImageIcon(getClass().getResource("/com/wolvesres/icon/return.png"));
        return new Object[]{getMaVoucher(), getGiamGia(), getSoLuong(), new ModelActionBlackList(icon, this, event)};
    }
}
