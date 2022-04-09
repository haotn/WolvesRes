package com.wolvesres.model;

import com.wolvesres.dao.ChiTietLichSuDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.XFormatMoney;
import java.util.ArrayList;
import java.util.List;

public class ModelKho {

    private int id;
    private int idls;
    private String maSP;
    private int soLuong;
    private String hanSuDung;
    private boolean trangThai;
    private List<ModelSanPham> listSP;
/////////////////////////////

    @Override
    public String toString() {
        return getSP().getTenSP();
    }
    ///////////////////////////////////  

    public ModelKho() {
    }

    public ModelKho(int id, int idls, String maSP, int soLuong, String hanSuDung, boolean trangThai) {
        this.id = id;
        this.idls = idls;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.hanSuDung = hanSuDung;
        this.trangThai = trangThai;
        this.listSP = listSP;
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

    //
    public Object[] toRowTable(List<ModelSanPham> list) {
        ModelSanPham sp = null;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaSP().equals(getMaSP())) {
                sp = list.get(i);
            }
        }
        return new Object[]{getIdls(), sp.getTenSP(), getSoLuong(), getHanSuDung()};
    }

    public void setListSP(List<ModelSanPham> list) {
        this.listSP = list;
    }

    public Object[] toTableRow() {
        ModelSanPham sp = null;
        for (int i = 0; i < listSP.size(); i++) {
            if (listSP.get(i).getMaSP().equals(getMaSP())) {
                sp = listSP.get(i);
            }
        }
        return new Object[]{sp.getTenSP(), soLuong, hanSuDung};
    }

    List<ModelSanPham> sp = new ArrayList<>();
    SanPhamDAO spdao = new SanPhamDAO();

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
////////////////////////////////////////////////////

    public ModelSanPham getSP() {
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

    public float GiaSP(int ma, String masp) {
        float gia = 0;
        ct.addAll(ctdao.selectAll());
        for (ModelChiTietLichSu mdct : ct) {
            if (ma == mdct.getIdLS() && masp.equals(mdct.getMaSP())) {
                gia = mdct.getDonGia();
            }
        }
        return gia;
    }

    public Object[] toRowTable() {
        return new Object[]{TenSP(getMaSP()), getSoLuong(), XFormatMoney.formatMoney(GiaSP(getIdls(), getMaSP())), getHanSuDung()};
    }
///////////////////////////////////////////////////////////////////////////////////////

    public Object[] toRowTableXK() {
        return new Object[]{this, getSoLuong(), XFormatMoney.formatMoney(GiaSP(getIdls(), getMaSP())), getHanSuDung()};
    }
}
