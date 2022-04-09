package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelGhiNho;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author hp
 */
public class GhiNhoDAO implements WolvesResDAO<ModelGhiNho, String> {

    @Override
    public List<ModelGhiNho> selectAll() {
        List<ModelGhiNho> list = new ArrayList<>();
        list = selectBySQL("select * from GHINHO");
        return list;
    }

    @Override
    public ModelGhiNho selectById(String ID) {
        List<ModelGhiNho> list = new ArrayList<>();
        list = selectBySQL("select * from GHINHO WHERE IPADDRESS = ?", ID);
        if (list.size()>0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<ModelGhiNho> selectBySQL(String sql, Object... thamSo) {
        List<ModelGhiNho> list = new ArrayList<>();
        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, thamSo);
                while (result.next()) {
                    ModelGhiNho taiKhoan = new ModelGhiNho(result.getString(1), result.getString(2), result.getString(3));
                    list.add(taiKhoan);
                }
            } finally {
                result.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public void insert(ModelGhiNho Entity) {
        try {
            String sql = "INSERT INTO GHINHO(IPADDRESS, TaiKhoan, MatKhau) VALUES (?,?,?)";
            XJdbc.update(sql, Entity.getIp(), Entity.getTaiKhoan(), Entity.getPassWord());
        } catch (SQLException ex) {
        }
    }

    @Override
    public void update(ModelGhiNho Entity, String ID) {
    }

    @Override
    public void delete(String ID) {
        try {
            String sql = "DELETE from GHINHO WHERE IPADDRESS = ?";
            XJdbc.update(sql, ID);
        } catch (SQLException ex) {
        }
    }

}
