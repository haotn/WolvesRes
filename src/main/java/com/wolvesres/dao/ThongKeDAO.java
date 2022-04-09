package com.wolvesres.dao;

import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XJdbc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FPT
 */
public class ThongKeDAO {
    
    public List<Object[]> getListOfArray(String sql, String[] cols, Object... args) {
        try {
            List<Object[]> list = new ArrayList<>();
            ResultSet result = XJdbc.query(sql, args);
            while (result.next()) {
                Object[] vals = new Object[cols.length];
                for (int i = 0; i < cols.length; i++) {
                    vals[i] = result.getObject(cols[i]);
                }
                list.add(vals);
            }
            result.getStatement().getConnection().close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
  
    // Thống kê doanh thu
    public List<Object[]> getDoanhThu_TG(String Ngay1, String Ngay2, int type) {
        String sql = "{CALL TK_DOANHTHU_TG (?,?,?)}";
        String[] cols = {"ThoiGian", "SOHD", "DOANHTHU", "GIAMGIA", "THUE", "DOANHTHUTHUC"};
        return this.getListOfArray(sql, cols, XDate.toDate(XDate.toString(XDate.toDate(Ngay1, "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), XDate.toDate(XDate.toString(XDate.toDate(Ngay2, "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), type);
    }
    
    // Thống kê mặt hàng
    public List<Object[]> getMatHang_TG(String Ngay1, String Ngay2, int type) {
        String sql = "{CALL TK_MATHANG_TG (?,?,?)}";
        String[] cols = {"ThoiGian","TenSP", "SOLUONG"};
        return this.getListOfArray(sql, cols, XDate.toDate(XDate.toString(XDate.toDate(Ngay1, "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), XDate.toDate(XDate.toString(XDate.toDate(Ngay2, "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), type);
    }
    
    // Thống kê món ăn
    public List<Object[]> getMonAn_TG(String Ngay1, String Ngay2, int type) {
        String sql = "{CALL TK_MONAN_TG (?,?,?)}";
        String[] cols = {"ThoiGian", "TenSP", "SOLUONG"};
        return this.getListOfArray(sql, cols, XDate.toDate(XDate.toString(XDate.toDate(Ngay1, "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), XDate.toDate(XDate.toString(XDate.toDate(Ngay2, "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), type);
    } 
    
    // Thống kê kho
    public List<Object[]> getKho() {
        String sql = "{CALL TK_KHO}";
        String[] cols = {"TenSP", "SOLUONG", "SOLO", "HANSD"};
        return this.getListOfArray(sql, cols);
    } 
    
    // Thống kê bàn
    public List<Object[]> getBan_TG(String Ngay1, String Ngay2, int type) {
        String sql = "{CALL TK_BAN_TG (?,?,?)}";
        String[] cols = {"ThoiGian", "TenBan", "SOLUONGHD", "DOANHTHUTHUC"};
        return this.getListOfArray(sql, cols, XDate.toDate(XDate.toString(XDate.toDate(Ngay1, "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), XDate.toDate(XDate.toString(XDate.toDate(Ngay2, "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), type);
    }
    
}
