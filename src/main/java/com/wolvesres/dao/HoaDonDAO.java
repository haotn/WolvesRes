package com.wolvesres.dao;

import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelHoaDon;
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
public class HoaDonDAO implements WolvesResDAO<ModelHoaDon, String> {

	@Override
	public List<ModelHoaDon> selectAll() {
		// List tạm
		List<ModelHoaDon> list = new ArrayList<>();

		// Nhận đối tượng đầu tiên của list
		list = selectBySQL("select * from HOADON");

		return list;
	}

	@Override
	public ModelHoaDon selectById(String id) {
		// List tạm
		List<ModelHoaDon> list = new ArrayList<>();

		// Nhận đối tượng đầu tiên của list
		list = selectBySQL("SELECT * FROM HOADON WHERE maHoaDon = ?", id);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ModelHoaDon> selectBySQL(String sql, Object... Entity) {
		// List tạm
		List<ModelHoaDon> list = new ArrayList<>();

		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, Entity);

				// Trả kết quả
				while (result.next()) {
					// Đối tượng KHO
					ModelHoaDon K = new ModelHoaDon(result.getString(1), result.getString(2), result.getString(3),
							result.getString(4), result.getString(5), result.getFloat(6), result.getFloat(7),
							result.getBoolean(8));

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
	public void insert(ModelHoaDon Entity) {
		try {
			String sql = "INSERT INTO HOADON(MaHoaDon, NguoiXuat, NgayXuat, MaBan, MaVoucher, Thue, TienHang, TrangThai) VALUES (?, ?, GETDATE(), ?, ?, ?, ?, ?) ";
			XJdbc.update(sql, Entity.getMaHD(), Entity.getNguoiXuat(), Entity.getMaBan(), Entity.getMaVoucher(),
					Entity.getThue(), Entity.getTienHang(), Entity.isTrangThai());
		} catch (SQLException ex) {
			Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void update(ModelHoaDon Entity, String id) {
		try {
			String sql = "UPDATE HOADON SET  TrangThai = ? WHERE MaHoaDon= ?";
			XJdbc.update(sql, Entity.isTrangThai(), Entity.getMaHD());
		} catch (SQLException ex) {
			Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void delete(String id) {
		try {
			String sql = "DELETE FROM HOADON WHERE MaHoaDon= ?";
			XJdbc.update(sql, id);
		} catch (SQLException ex) {
			Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public List<ModelHoaDon> timKiem(String keyword) {
		List<ModelHoaDon> list = new ArrayList<>();
		String sql = "select * FROM HOADON where MaHoaDon like ? or convert(varchar, NgayXuat, 105) like ?";
		list.addAll(selectBySQL(sql, "%" + keyword + "%", "%" + keyword + "%"));
		return list;
	}

}
