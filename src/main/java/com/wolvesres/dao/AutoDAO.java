package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import java.sql.ResultSet;

public class AutoDAO {

    private String kq = "";
    private String sql = "";
    //DAO tự sinh mã nhân viên
    public String AuToNhanVien() {
        try {
            ResultSet ketQua = null;
            sql = "{call SP_NHANVIEN_TUSINHNHANVIEN}";
            try {
                ketQua = XJdbc.query(sql);
                while (ketQua.next()) {
                    kq = ketQua.getString(1);
                }
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
                ketQua.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return kq;
    }
    //DAO tự sinh mã danh mục
    public String AuToDanhMuc() {
        try {
            ResultSet ketQua = null;
            sql = "{call SP_DANHMUC_TUSINHDANHMUC}";
            try {
                ketQua = XJdbc.query(sql);
                while (ketQua.next()) {
                    kq = ketQua.getString(1);
                }
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
                ketQua.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return kq;
    }
    //DAO tự sinh mã sản phẩm
    public String AuToSanPham() {
        try {
            ResultSet ketQua = null;
            sql = "{call SP_SANPHAM_TUSINHSANPHAM}";
            try {
                ketQua = XJdbc.query(sql);
                while (ketQua.next()) {
                    kq = ketQua.getString(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ketQua.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return kq;
    }
    //DAO tự sinh mã khu bàn
    public String AuToKhuBan() {
        try {
            ResultSet ketQua = null;
            sql = "{call SP_KHUBAN_TUSINHKHUBAN}";
            try {
                ketQua = XJdbc.query(sql);
                while (ketQua.next()) {
                    kq = ketQua.getString(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                ketQua.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return kq;
    }
    //DAO tự sinh mã bàn
    public String AuToBan() {
        try {
            ResultSet ketQua = null;
            sql = "{call SP_BAN_TUSINHBAN}";
            try {
                ketQua = XJdbc.query(sql);
                while (ketQua.next()) {
                    kq = ketQua.getString(1);
                }
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
                ketQua.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return kq;
    }
//    //DAO tự sinh mã voucher
//    public String AuToVouCher() {
//        try {
//            ResultSet ketQua = null;
//            sql = "{call SP_VOUCHER_TUSINHVOUCHER}";
//            try {
//                ketQua = XJdbc.query(sql);
//                while (ketQua.next()) {
//                    kq = ketQua.getString(1);
//                }
//            } catch (Exception e) {
//                System.out.println("lỗi voucher: " + e);
//            } finally {
//                ketQua.getStatement().getConnection().close();
//            }
//        } catch (Exception e) {
//        }
//        return kq;
//    }
    //DAO tự sinh mã hóa đơn
    public String AuToHoaDon() {
        try {
            ResultSet ketQua = null;
            sql = "{call SP_HOADON_TUSINHHOADON}";
            try {
                ketQua = XJdbc.query(sql);
                while (ketQua.next()) {
                    kq = ketQua.getString(1);
                }
            } catch (Exception e) {
               e.printStackTrace();
            } finally {
                ketQua.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return kq;
    }

}
