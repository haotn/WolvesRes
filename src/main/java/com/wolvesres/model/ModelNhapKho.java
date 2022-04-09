package com.wolvesres.model;

import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.swing.table.EventActionBlackList;
import com.wolvesres.swing.table.ModelActionBlackList;
import java.util.ArrayList;
import java.util.List;

public class ModelNhapKho {

    private int id;
    private ModelSanPham sanPham;
    private float gia;
    private int soLuong;
    private String hanSD;

    @Override
    public String toString() {
        return sanPham.getTenSP();
    }

    public ModelNhapKho() {
    }

    public ModelNhapKho(int id, ModelSanPham sanPham, float gia, int soLuong, String hanSD) {
        this.id = id;
        this.sanPham = sanPham;
        this.gia = gia;
        this.soLuong = soLuong;
        this.hanSD = hanSD;
    }

    public ModelSanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(ModelSanPham sanPham) {
        this.sanPham = sanPham;
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

    /**
     * @return the hanSD
     */
    public String getHanSD() {
        return hanSD;
    }

    /**
     * @param hanSD the hanSD to set
     */
    public void setHanSD(String hanSD) {
        this.hanSD = hanSD;
    }

    public Object[] toRowTable(EventActionBlackList event) {
        return new Object[]{getSanPham(), getSoLuong(), XFormatMoney.formatMoney(getGia()), getHanSD(), new ModelActionBlackList(this, event)};
    }
    List<ModelSanPham> sp = new ArrayList<>();
    SanPhamDAO spdao = new SanPhamDAO();
    private String maSP;

    public String getMaSP() {
        return maSP;
    }
////////////////Xuat kho

    /**
     * @param hanSD the hanSD to set
     */
    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    //
    private int IDLS;

    public int getIDK() {
        return IDLS;
    }

    public void setIDK(int IDLS) {
        this.IDLS = IDLS;
    }

    //
    public ModelNhapKho(int id, float gia, int soLuong, String hanSD, String maSP) {
        this.id = id;
        this.sanPham = sanPham;
        this.gia = gia;
        this.soLuong = soLuong;
        this.hanSD = hanSD;
        this.maSP = maSP;
    }

    public String TenSP(String ma) {
        String ten = "";
        sp.addAll(spdao.selectAll());
        for (ModelSanPham m : sp) {
            if (ma.equals(m.getMaSP())) {
                ten = m.getTenSP();
            }
        }
        return ten;
    }

    public Object[] toRowTableXK(EventActionBlackList event) {
        return new Object[]{TenSP(getMaSP()), getSoLuong(), XFormatMoney.formatMoney(getGia()), getHanSD(), new ModelActionBlackList(this, event)};
    }
//    @Override
//    public Object[] toTableRow() {
//        return new Object[]{sanPham.toString(), soLuong, gia, hanSD};
//    }
}
