package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelDonViTinh;
import com.wolvesres.model.ModelSanPham;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DonViTinhDAO implements WolvesResDAO<ModelDonViTinh, Integer> {

    @Override
    public List<ModelDonViTinh> selectAll() {
        // List tạm
        List<ModelDonViTinh> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from DONVITINH");

        return list;
    }

    @Override
    public ModelDonViTinh selectById(Integer id) {
        // List tạm
        List<ModelDonViTinh> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("SELECT * FROM DONVITINH WHERE MaDonViTinh = ?", id);
        if(list.size()>0) {
        	return list.get(0);
        }else {
        	return null;
        }
    }

    @Override
    public List<ModelDonViTinh> selectBySQL(String sql, Object... Entity) {
        // List tạm
        List<ModelDonViTinh> list = new ArrayList<>();

        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, Entity);

                // Trả kết quả
                while (result.next()) {
                    // Đối tượng KHO
                    ModelDonViTinh K = new ModelDonViTinh(result.getInt(1), result.getString(2));

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
    public void insert(ModelDonViTinh Entity) {
        try {
            String sql = "INSERT INTO DONVITINH (TenDonViTinh)  VALUES (?)";
            XJdbc.update(sql, Entity.getTenDVT());
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ModelDonViTinh Entity, Integer id) {
        try {
            String sql = "UPDATE DONVITINH SET TenDonViTinh = ? WHERE MaDonViTinh = ?";
            XJdbc.update(sql, Entity.getTenDVT(), Entity.getMaDVT());
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Integer id) {
        try {
            String sql = "DELETE FROM DONVITINH WHERE MaDonViTinh = ?";
            XJdbc.update(sql, id);
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ////

    public ModelDonViTinh checkForeignKey(Integer ID) {
        ModelDonViTinh entity = null;
        ResultSet result;
        try {
            String sql = "SELECT * FROM DONVITINH DVT JOIN DANHMUCSANPHAM DM ON DVT.MaDonViTinh = DM.DonViTinh WHERE DVT.MADONVITINH = ?";
            result = XJdbc.query(sql, ID);
            if (result.next()) {
                entity = new ModelDonViTinh(result.getInt(1), result.getString(2));
            }
        } catch (SQLException ex) {
            //Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return entity;
    }
    
    public List<ModelDonViTinh> timkiem(String keyword){
    	List<ModelDonViTinh> list = new ArrayList<ModelDonViTinh>();
    	String sql = "select * from DONVITINH where TenDonViTinh like ?";
    			list = selectBySQL(sql, "%"+keyword+"%");
    	return list;
    }

}
