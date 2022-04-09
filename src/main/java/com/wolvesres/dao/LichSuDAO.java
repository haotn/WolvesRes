package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelLichSu;
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
public class LichSuDAO implements WolvesResDAO<ModelLichSu, Integer> {

    @Override
    public List<ModelLichSu> selectAll() {
        // List tạm
        List<ModelLichSu> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("select * from LICHSU");

        return list;
    }

    @Override
    public ModelLichSu selectById(Integer id) {
        // List tạm
        List<ModelLichSu> list = new ArrayList<>();

        // Nhận đối tượng đầu tiên của list
        list = selectBySQL("SELECT * FROM LICHSU WHERE ID = ?", id);

        return list.get(0);
    }

    @Override
    public List<ModelLichSu> selectBySQL(String sql, Object... Entity) {
        // List tạm
        List<ModelLichSu> list = new ArrayList<>();

        try {
            ResultSet result = null;
            try {
                result = XJdbc.query(sql, Entity);

                // Trả kết quả
                while (result.next()) {
                    // Đối tượng KHO
                    ModelLichSu K = new ModelLichSu(result.getInt(1), result.getBoolean(2), result.getString(3), result.getFloat(4), result.getString(5));

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
    public void insert(ModelLichSu Entity) {
        try {
            String sql = "INSERT INTO LICHSU(NhapKho, ThoiGian, NguoiNhap, TongTien) VALUES (?,GETDATE() , ? , ?)";
            XJdbc.update(sql, Entity.isNhapKho(), Entity.getNguoiNhap(), Entity.getTongTien() );
        } catch (SQLException ex) {
            Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(ModelLichSu Entity, Integer ID) {
       
    }

    @Override
    public void delete(Integer ID) {
        
    }
    
    public List<ModelLichSu> TIMKIEM(String ma) {
        List<ModelLichSu> list = new ArrayList<>();
        String sql = "select * FROM LICHSU where convert(varchar, ThoiGian, 105) like ?";
        list.addAll(selectBySQL(sql, "%"+ma+"%"));
        return list;
    }

}
