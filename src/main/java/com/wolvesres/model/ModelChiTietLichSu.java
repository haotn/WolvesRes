package com.wolvesres.model;

import com.wolvesres.dao.ChiTietLichSuDAO;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.helper.XFormatMoney;
import java.util.ArrayList;
import java.util.List;
/**
 * Them : insert
 * */
public class ModelChiTietLichSu {

    private int id;
    private int idLS;
    private String maSP;
    private int soLuong;
    private float donGia;
    private ChiTietLichSuDAO chitietlsdao = new ChiTietLichSuDAO();
    public ModelChiTietLichSu() {
    }

    public ModelChiTietLichSu(int id, int idLS, String maSP, int soLuong, float donGia) {
        this.id = id;
        this.idLS = idLS;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public ModelChiTietLichSu(int idLS, String maSP, int soLuong, float donGia) {
        this.id = id;
        this.idLS = idLS;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
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
     * @return the idLS
     */
    public int getIdLS() {
        return idLS;
    }

    /**
     * @param idLS the idLS to set
     */
    public void setIdLS(int idLS) {
        this.idLS = idLS;
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

    /////////////////////
    private List<ModelSanPham> listSanPham = new ArrayList<>();
    private List<ModelDanhMuc> listDanhMuc = new ArrayList<>();
    private List<ModelDonViTinh> listDonViTinhs = new ArrayList<>();
    SanPhamDAO spdao = new SanPhamDAO();
    DanhMucDAO dmdao = new DanhMucDAO();
    DonViTinhDAO dvtdao = new DonViTinhDAO();

    public String getTenDanhMuc() {
        String tendm = "";
        String maDM = "";
        listSanPham.addAll(spdao.selectAll());
        for (ModelSanPham sanPham : listSanPham) {
            if (getMaSP().equals(sanPham.getMaSP())) {
                tendm = sanPham.getTenDanhMuc();
            }
        }
        return tendm;
    }

    public String getTenDonViTinh() {
        String tendm = "";
        String tenDVT = "";
        String maDM = "";
        listSanPham.addAll(spdao.selectAll());
        listDanhMuc.addAll(dmdao.selectAll());
        listDonViTinhs.addAll(dvtdao.selectAll());
        for (ModelSanPham sanPham : listSanPham) {
            if (getMaSP().equals(sanPham.getMaSP())) {
                for (ModelDonViTinh donViTinh : listDonViTinhs) {
                    if (sanPham.getMaDVT() == donViTinh.getMaDVT()) {
                        tenDVT = donViTinh.getTenDVT();
                        break;
                    }
                }
            }
        }
        return tenDVT;
    }

    public String getTenLoaiSanPham() {
        String TenLH = "";
        listSanPham.addAll(spdao.selectAll());
        ModelSanPham sanPham = new ModelSanPham();
        for (ModelSanPham msp : listSanPham) {
            if (getMaSP().equals(msp.getMaSP())) {
                sanPham = msp;
            }
        }
        for (ModelDanhMuc dm : dmdao.selectAll()) {
            if (sanPham.getMaDanhMuc().equals(dm.getMaDanhMuc())) {
                TenLH = dm.isMatHang() ? "Mặt hàng" : "Sản phẩm";
            }
        }
        return TenLH;
    }

    //Chuyen ma thanh ten sp
    public String getTenSanPham(String maSP) {
        String TenSP = "";
        for (ModelSanPham msp : spdao.selectAll()) {
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

    public Object[] toRowTableCTLS() {
        return new Object[]{getTenSanPham(getMaSP()), XFormatMoney.formatMoney(getDonGia()), getSoLuong(), Gia(getDonGia(), getSoLuong())};
    }
    //insert
    public void insert(int idLichSu, String maSanPham,int soLuong, float gia) {
    	this.setIdLS(idLichSu);
    	this.setMaSP(maSanPham);
    	this.setSoLuong(soLuong);
    	this.setDonGia(gia);
        chitietlsdao.insert(this);
    }

	@Override
	public String toString() {
		return "ModelChiTietLichSu [id=" + id + ", idLS=" + idLS + ", maSP=" + maSP + ", soLuong=" + soLuong
				+ ", donGia=" + donGia + "]";
	}
    
}
