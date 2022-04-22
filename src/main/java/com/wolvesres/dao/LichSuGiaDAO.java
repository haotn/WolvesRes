package com.wolvesres.dao;

import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelLichSuGia;
import com.wolvesres.model.ModelSanPham;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author FPT
 */
public class LichSuGiaDAO implements WolvesResDAO<ModelLichSuGia, Integer> {

    @Override
    public List<ModelLichSuGia> selectAll() {
        List<ModelLichSuGia> list = new ArrayList<>();
        list = selectBySQL("select * from LICHSUGIA");
        return list;
    }

    @Override
    public ModelLichSuGia selectById(Integer ID) {
        List<ModelLichSuGia> list = new ArrayList<>();
        list = selectBySQL("select * from LICHSUGIA where ID = ?", ID);
        if(list.size()>0) {
        	return list.get(0);
        }
        	return null;
    }

    @Override
    public List<ModelLichSuGia> selectBySQL(String sql, Object... Entity) {
        List<ModelLichSuGia> list = new ArrayList<>();
        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, Entity);
                while (result.next()) {
                    ModelLichSuGia sanPham = new ModelLichSuGia(result.getInt(1), result.getString(3), XDate.toString(result.getDate(2), "dd-MM-yyyy HH:mm:ss"), result.getFloat(4));
                    list.add(sanPham);
                }
            } finally {
                result.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public void insert(ModelLichSuGia entity) {
        try {
            String sql = "INSERT INTO LICHSUGIA (MaSP, NgayThayDoi, Gia) VALUES (?,?,?)";
            XJdbc.update(sql, entity.getMaSP(), XDate.toDate(XDate.toString(XDate.toDate(entity.getNgayThayDoi(), "dd-MM-yyyy HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"), entity.getGia());
        } catch (SQLException ex) {
        }
    }

    @Override
    public void update(ModelLichSuGia Entity, Integer ID) {
    }

    @Override
    public void delete(Integer ID) {
        try {
            String sql = "DELETE FROM LICHSUGIA WHERE MASP = ?";
            XJdbc.update(sql, ID);
        } catch (SQLException ex) {
        }
    }
    
    public List<ModelLichSuGia> timkiem(String keyword){
    	List<ModelLichSuGia> list = new ArrayList<ModelLichSuGia>();
    	String sql = "select * from LICHSUGIA where TenSP like ?";
    			list = selectBySQL(sql, "%"+keyword+"%");
    	return list;
    }

}
