package com.wolvesres.dao;

import com.wolvesres.form.sanpham.BlackListSanPham;
import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelSanPham;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO implements WolvesResDAO<ModelDanhMuc, String> {

    @Override
    public List<ModelDanhMuc> selectAll() {
        List<ModelDanhMuc> list = new ArrayList<>();
        list = selectBySQL("select * from DANHMUCSANPHAM");
        return list;
    }

    @Override
    public ModelDanhMuc selectById(String ID) {
        List<ModelDanhMuc> list = new ArrayList<>();
        list = selectBySQL("select * from DANHMUCSANPHAM where MaDanhMucSP like ?", ID);
        return list.get(0);
    }

    @Override
    public List<ModelDanhMuc> selectBySQL(String sql, Object... thamSo) {
        List<ModelDanhMuc> list = new ArrayList<>();
        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, thamSo);
                while (result.next()) {
                    ModelDanhMuc entity = new ModelDanhMuc(result.getString(1), result.getString(2), result.getBoolean(3));
                    list.add(entity);
                }
            } finally {
                result.getStatement().getConnection().close();
            }
        } catch (Exception e) {
        }
        return list;
    }

    @Override
    public void insert(ModelDanhMuc entity) {
        try {
            String sql = "INSERT INTO DANHMUCSANPHAM(MaDanhMucSP, TenDanhMucSP, MatHang) VALUES (?,?,?)";
            XJdbc.update(sql, entity.getMaDanhMuc(), entity.getTenDanhMuc(), entity.isMatHang());
        } catch (SQLException ex) {
        }
    }

    @Override
    public void update(ModelDanhMuc entity, String ID) {
        try {
            String sql = "UPDATE DANHMUCSANPHAM SET TenDanhMucSP = ?, MatHang = ? WHERE MaDanhMucSP = ?";
            XJdbc.update(sql, entity.getTenDanhMuc(), entity.isMatHang(), ID);
        } catch (SQLException ex) {
        }
    }

    @Override
    public void delete(String ID) {
        try {
            String sql = "DELETE FROM DANHMUCSANPHAM WHERE MaDanhMucSP = ?";
            XJdbc.update(sql, ID);
        } catch (SQLException ex) {
        }
    }

    //
    public ModelDanhMuc checkForeignKey(String ID) {
        ModelDanhMuc entity = null;
        ResultSet result;
        try {
            String sql = "SELECT * FROM DANHMUCSANPHAM DM JOIN SANPHAM SP ON DM.MaDanhMucSP = SP.MaDanhMucSP WHERE DM.MaDanhMucSP = ?";
            result = XJdbc.query(sql, ID);
            if (result.next()) {
                entity = new ModelDanhMuc(result.getString(1), result.getString(2), result.getBoolean(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

	public void loadToBlackList(BlackListSanPham blackListSanPham) {
		blackListSanPham.listSP.clear();
		for (ModelSanPham sp : blackListSanPham.formSP.getList()) {
			if (sp.isTrangThai() == false) {
				blackListSanPham.listSP.add(sp);
			}
		}
	}
	
	public List<ModelDanhMuc> timkiem(String keyword){
    	List<ModelDanhMuc> list = new ArrayList<ModelDanhMuc>();
    	String sql = "select * from DANHMUCSANPHAM where TenDanhMucSP like ?";
    			list = selectBySQL(sql, "%"+keyword+"%");
    	return list;
    }
}
