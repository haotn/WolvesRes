package com.wolvesres.dao;

import com.wolvesres.helper.*;
import com.wolvesres.model.ModelKho;
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
public class KhoDAO implements WolvesResDAO<ModelKho, Integer> {

    @Override
    public List<ModelKho> selectAll() {
        // List tạm
        List<ModelKho> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from KHO");

        return list;
    }

    @Override
    public ModelKho selectById(Integer id) {
        // List tạm
        List<ModelKho> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("SELECT * FROM KHO WHERE ID = ?", id);
        return list.get(0);
    }

    public ModelKho selectByObject(String maSp, int idls) {
        // List tạm
        List<ModelKho> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("SELECT * FROM KHO WHERE MaSP = ? AND IDLS = ?", maSp,idls);
        return list.get(0);
    }
    @Override
    public List<ModelKho> selectBySQL(String sql, Object... Entity) {
        // List tạm
        List<ModelKho> list = new ArrayList<>();

        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, Entity);

                // Trả kết quả
                while (result.next()) {
                    // Đối tượng KHO
                    ModelKho K = new ModelKho(result.getInt(1), result.getInt(2), result.getString(3), result.getInt(4), XDate.toString(result.getDate(5), "dd-MM-yyyy"), result.getBoolean(6));

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
    public void insert(ModelKho Entity) {
        try {
            String sql = "INSERT INTO KHO(MaSP, IDLS, SoLuong, HanSuDung, TRANGTHAI) VALUES (?,?,?,?,?)";
            XJdbc.update(sql, Entity.getMaSP(),Entity.getIdls(), Entity.getSoLuong(), XDate.toDate(Entity.getHanSuDung(), "dd-MM-yyyy"), Entity.isTrangThai());
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ModelKho Entity, Integer id) {
        try {
            String sql = "UPDATE KHO SET  SoLuong= ? , TrangThai = ? WHERE ID= ?";
            XJdbc.update(sql,  Entity.getSoLuong(), Entity.isTrangThai(), id);
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            String sql = "DELETE FROM KHO WHERE ID= ?";
            XJdbc.update(sql, id);
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
