package com.wolvesres.model;

import com.wolvesres.dao.HoaDonDAO;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.VoucherDAO;
import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.swing.table.EventAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *Thêm hàm: chuyenTrangThaiHoaDon.
 * @author haotn
 */
public class ModelHoaDon {

    private String maHD;
    private String nguoiXuat;
    private String ngayXuat;
    private String maBan;
    private String maVoucher;
    private float thue;
    private float tienHang;
    private boolean trangThai;
    private HoaDonDAO hoadondao = new HoaDonDAO();
    public ModelHoaDon() {
    }

    public ModelHoaDon(String maHD, String nguoiXuat, String ngayXuat, String maBan, String maVoucher, float thue, float tienHang, boolean trangThai) {
        this.maHD = maHD;
        this.nguoiXuat = nguoiXuat;
        this.ngayXuat = ngayXuat;
        this.maBan = maBan;
        this.maVoucher = maVoucher;
        this.thue = thue;
        this.tienHang = tienHang;
        this.trangThai = trangThai;
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
     * @return the nguoiXuat
     */
    public String getNguoiXuat() {
        return nguoiXuat;
    }

    /**
     * @param nguoiXuat the nguoiXuat to set
     */
    public void setNguoiXuat(String nguoiXuat) {
        this.nguoiXuat = nguoiXuat;
    }

    /**
     * @return the ngayXuat
     */
    public String getNgayXuat() {
        return ngayXuat;
    }

    /**
     * @param ngayXuat the ngayXuat to set
     */
    public void setNgayXuat(String ngayXuat) {
        this.ngayXuat = ngayXuat;
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
     * @return the thue
     */
    public float getThue() {
        return thue;
    }

    /**
     * @param thue the thue to set
     */
    public void setThue(float thue) {
        this.thue = thue;
    }

    /**
     * @return the tienHang
     */
    public float getTienHang() {
        return tienHang;
    }

    /**
     * @param tienHang the tienHang to set
     */
    public void setTienHang(float tienHang) {
        this.tienHang = tienHang;
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

    //
    List<ModelNhanVien> nv = new ArrayList<>();
    NhanVienDAO dao = new NhanVienDAO();
    List<ModelVouCher> vc = new ArrayList<>();
    VoucherDAO daovc = new VoucherDAO();

    public String NguoiXuat(String ma) {
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

    //
    public String TrangThai(boolean tt) {
        String hd = "";
        if (tt == true) {
            hd = "Hoạt động";
        } else {
            hd = "Bị hủy";
        }
        return hd;
    }

    //
    public float GiamVC(String ma) {
        vc.addAll(daovc.selectAll());
        float giamVC = 0;
        for (ModelVouCher vc : vc) {
            if (ma.equals(vc.getMaVoucher())) {
                giamVC = vc.getGiamGia();
                break;
            }
        }
        return giamVC;
    }
    // SimpleDateFormat formater = new SimpleDateFormat("HH:mm: dd-MM-yyyy");

    //\
    // Phương thức chuyển ngày yyyy-MM-dd
    public String toYMD(String ngay) {
        return XDate.toString(XDate.toDate(ngay, "yyyy-MM-dd HH:mm:ss"), "HH:mm dd-MM-yyyy");
    }

    public Object[] toRowTable(EventAction event) {
        return new Object[]{getMaHD(), NguoiXuat(getNguoiXuat()), toYMD(getNgayXuat()), getMaBan(), getMaVoucher(), XFormatMoney.formatMoney(getThue()), XFormatMoney.formatMoney(getTienHang())};
    }

    public Object[] toRowTableHDCT() {
        return new Object[]{getMaHD(), NguoiXuat(getNguoiXuat()), toYMD(getNgayXuat()), XFormatMoney.formatMoney(getTienHang())};
    }

    //huy hoa don
    public void chuyenTrangThaiHoaDon(List<ModelHoaDon> list) {
    	this.setTrangThai(false);
    	hoadondao.update(this, this.getMaHD());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getMaHD().equals(this.getMaHD())) {
				list.set(i, this);
				break;
			}
		}
    }

	@Override
	public String toString() {
		return maHD + " - " + nguoiXuat + " - " + ngayXuat + " - " + maBan
				+ " - " + maVoucher + " - " + thue + " - " + tienHang + " - " + trangThai;
	}
    
    
    
}
