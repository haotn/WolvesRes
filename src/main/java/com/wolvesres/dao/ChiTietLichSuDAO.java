
package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelChiTietLichSu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChiTietLichSuDAO implements WolvesResDAO<ModelChiTietLichSu, Integer> {

    @Override
    public List<ModelChiTietLichSu> selectAll() {
        // List tạm
        List<ModelChiTietLichSu> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from CHITIETLICHSU");

        return list;
    }

    @Override
    public ModelChiTietLichSu selectById(Integer id) {
        // List tạm
        List<ModelChiTietLichSu> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("SELECT * FROM CHITIETLICHSU WHERE ID = ?", id);

        return list.get(0);
    }

    public ModelChiTietLichSu selectByObject(int idls, String masp) {
        // List tạm
        List<ModelChiTietLichSu> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("SELECT * FROM CHITIETLICHSU WHERE IDLS = ? and MaSP= ?", idls, masp);

        return list.get(0);
    }
    @Override
    public List<ModelChiTietLichSu> selectBySQL(String sql, Object... Entity) {
        // List tạm
        List<ModelChiTietLichSu> list = new ArrayList<>();

        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, Entity);

                // Trả kết quả
                while (result.next()) {
                    // Đối tượng KHO
                    ModelChiTietLichSu K = new ModelChiTietLichSu(result.getInt(1), result.getInt(2),result.getString(3),result.getInt(4),result.getFloat(5));

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
    public void insert(ModelChiTietLichSu Entity) {
        try {
            String sql = "INSERT INTO CHITIETLICHSU(IDLS, MaSP, DonGia, SoLuong) VALUES (?, ?, ?, ?)";
            XJdbc.update(sql, Entity.getIdLS(), Entity.getMaSP(), Entity.getDonGia(), Entity.getSoLuong());
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ModelChiTietLichSu Entity, Integer id) {
        
    }

    @Override
    public void delete(Integer id) {

    }

}
