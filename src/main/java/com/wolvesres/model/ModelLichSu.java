package com.wolvesres.model;

import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XFormatMoney;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ModelLichSu {

    private int id;
    private boolean nhapKho;
    private String thoiGian;
    private float tongTien;
    private String nguoiNhap;

    public ModelLichSu() {
    }

    public ModelLichSu(int id, boolean nhapKho, String thoiGian, float tongTien, String nguoiNhap) {
        this.id = id;
        this.nhapKho = nhapKho;
        this.thoiGian = thoiGian;
        this.tongTien = tongTien;
        this.nguoiNhap = nguoiNhap;
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
     * @return the nhapKho
     */
    public boolean isNhapKho() {
        return nhapKho;
    }

    /**
     * @param nhapKho the nhapKho to set
     */
    public void setNhapKho(boolean nhapKho) {
        this.nhapKho = nhapKho;
    }

    /**
     * @return the nguoiNhap
     */
    public String getNguoiNhap() {
        return nguoiNhap;
    }

    /**
     * @param nguoiNhap the nguoiNhap to set
     */
    public void setNguoiNhap(String nguoiNhap) {
        this.nguoiNhap = nguoiNhap;
    }

    /**
     * @return the thoiGian
     */
    public String getThoiGian() {
        return thoiGian;
    }

    /**
     * @param thoiGian the thoiGian to set
     */
    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    /**
     * @return the tongTien
     */
    public float getTongTien() {
        return tongTien;
    }

    /**
     * @param tongTien the tongTien to set
     */
    public void setTongTien(float tongTien) {
        this.tongTien = tongTien;
    }
//

    //
    List<ModelNhanVien> nv = new ArrayList<>();
    NhanVienDAO dao = new NhanVienDAO();

    public String NguoiNhap(String ma) {
        nv.addAll(dao.selectAll());
        String tenNV = "";
        for (ModelNhanVien nv : nv) {
            if (ma.equals(nv.getMaNV())) {
                tenNV = nv.getHoTen();
                break;
            }
        }
        return tenNV;
    }

    public String toYMD(String ngay) {
        return XDate.toString(XDate.toDate(ngay, "yyyy-MM-dd HH:mm"), "HH:mm dd-MM-yyyy");
    }

    KhoDAO khodao = new KhoDAO();
    List<ModelKho> kho = new ArrayList<>();

    public int idkho(int idls) {
        int lo = 0;
        kho.addAll(khodao.selectAll());
        for (ModelKho k : kho) {
            if (k.getIdls() == idls) {
                lo = k.getId();
            }
        }
        return lo;
    }

    ///////////
    public Object[] toRowTableHDCT() {
        return new Object[]{getId(), isNhapKho() ? "Nhập Kho" : "Xuất Kho", toYMD(getThoiGian()), NguoiNhap(getNguoiNhap()), XFormatMoney.formatMoney(getTongTien())};
    }

}
