package com.wolvesres.dao;

import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelVouCher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hp
 */
public class VoucherDAO implements WolvesResDAO<ModelVouCher, String> {

    @Override
    public List<ModelVouCher> selectAll() {
        // List tạm
        List<ModelVouCher> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from VOUCHER");

        return list;
    }

    @Override
    public ModelVouCher selectById(String id) {
        // List tạm
        List<ModelVouCher> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("SELECT * FROM VOUCHER WHERE MaVoucher = ?", id);

        return list.get(0);
    }

    @Override
    public List<ModelVouCher> selectBySQL(String sql, Object... Entity) {
        List<ModelVouCher> list = new ArrayList<>();

        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, Entity);

                while (result.next()) {
                    ModelVouCher K = new ModelVouCher(result.getString(1), XDate.toString(result.getDate(2), "dd-MM-yyyy"), XDate.toString(result.getDate(3), "dd-MM-yyyy"), result.getFloat(4), result.getInt(5), result.getString(6), result.getBoolean(7));

                    // Thêm vào list tạm
                    list.add(K);
                }
            } finally {
                result.getStatement().getConnection().close();
            }
        } catch (SQLException e) {
        }

        return list;
    }

    @Override
    public void insert(ModelVouCher Entity) {
        try {
            String sql = "INSERT INTO VOUCHER(MaVoucher, NgayBatDau, NgayKetThuc,GiamGia, SoLuong, QR, TrangThai)  VALUES (?, ?,?,?,?,?,?)";
            XJdbc.update(sql, Entity.getMaVoucher(), XDate.toDate(XDate.toString(XDate.toDate(Entity.getNgayBatDau(), "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), XDate.toDate(XDate.toString(XDate.toDate(Entity.getNgayKetThuc(), "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), Entity.getGiamGia(), Entity.getSoLuong(), Entity.getPathQR(), Entity.isTrangThai());
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ModelVouCher Entity, String id) {
        try {
            String sql = "UPDATE VOUCHER SET MaVoucher =?, NgayBatDau= ?, NgayKetThuc= ?, GiamGia= ?, SoLuong= ?, QR=?, TrangThai=?  WHERE MaVoucher= ?";
            XJdbc.update(sql, Entity.getMaVoucher(), XDate.toDate(XDate.toString(XDate.toDate(Entity.getNgayBatDau(), "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), XDate.toDate(XDate.toString(XDate.toDate(Entity.getNgayKetThuc(), "dd-MM-yyyy"), "yyyy-MM-dd"), "yyyy-MM-dd"), Entity.getGiamGia(), Entity.getSoLuong(), Entity.getPathQR(), Entity.isTrangThai(), id);
            System.out.println(Entity.getMaVoucher());
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(String id) {
        try {
            String sql = "DELETE FROM VOUCHER WHERE MaVoucher=?";
            XJdbc.update(sql, id);
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModelVouCher checkForeignKey(ModelVouCher vc) {
        ModelVouCher voucher = null;
        try {
            String sql = "SELECT * FROM VOUCHER VC JOIN HOADON HD ON VC.MaVoucher = HD.MaVoucher WHERE VC.MaVoucher = ?";
            ResultSet result;
            result = XJdbc.query(sql, vc.getMaVoucher());
            if (result.next()) {
                voucher = new ModelVouCher(result.getString(1), XDate.toString(result.getDate(2), "dd-MM-yyyy"), XDate.toString(result.getDate(3), "dd-MM-yyyy"), result.getInt(4), result.getInt(5), result.getString(6), result.getBoolean(7));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return voucher;
    }

}
