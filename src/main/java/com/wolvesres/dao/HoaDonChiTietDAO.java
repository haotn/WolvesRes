package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelHoaDonChiTiet;
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
public class HoaDonChiTietDAO implements WolvesResDAO<ModelHoaDonChiTiet, Integer> {

    @Override
    public List<ModelHoaDonChiTiet> selectAll() {
        // List tạm
        List<ModelHoaDonChiTiet> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from HOADONCHITIET");

        return list;
    }

    @Override
    public ModelHoaDonChiTiet selectById(Integer id) {
        // List tạm
        List<ModelHoaDonChiTiet> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("SELECT * FROM HOADONCHITIET WHERE MaHoaDonCT = ?", id);

        return list.get(0);
    }

    @Override
    public List<ModelHoaDonChiTiet> selectBySQL(String sql, Object... Entity) {
        // List tạm
        List<ModelHoaDonChiTiet> list = new ArrayList<>();

        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, Entity);

                // Trả kết quả
                while (result.next()) {
                    // Đối tượng KHO
                    ModelHoaDonChiTiet K = new ModelHoaDonChiTiet(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4), result.getFloat(5));
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
    public void insert(ModelHoaDonChiTiet Entity) {
        try {
            String sql = "INSERT INTO HOADONCHITIET(MaHoaDon, MaSP, SoLuong, DonGia) VALUES (?, ?, ?, ?)";
            XJdbc.update(sql, Entity.getMaHD(), Entity.getMaSP(), Entity.getSoLuong(), Entity.getDonGia());
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ModelHoaDonChiTiet Entity, Integer id) {
        
    }

    @Override
    public void delete(Integer id) {
        
    }

}
