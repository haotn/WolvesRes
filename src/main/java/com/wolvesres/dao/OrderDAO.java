package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelBanOrder;
import com.wolvesres.model.ModelOrder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAO implements WolvesResDAO<ModelOrder, String> {

	@Override
	public List<ModelOrder> selectAll() {
		List<ModelOrder> list = new ArrayList<>();
		list = selectBySQL("SELECT * FROM SANPHAMORDER");
		return list;
	}

	public List<ModelOrder> selectAllByIdBan(ModelBanOrder entity) {
		List<ModelOrder> list = new ArrayList<>();
		list = selectBySQL("SELECT * FROM SANPHAMORDER WHERE MABAN = ?", entity.getMaBan());
		return list;
	}

	@Override
	public ModelOrder selectById(String ID) {
		List<ModelOrder> list = new ArrayList<>();
		list = selectBySQL("SELECT * FROM SANPHAMORDER WHERE MABAN = ?", ID);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<ModelOrder> selectBySQL(String sql, Object... o) {
		List<ModelOrder> list = new ArrayList<>();
		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, o);
				while (result.next()) {
					ModelOrder entity = new ModelOrder(result.getInt(1), result.getString(2), result.getString(3),
							result.getFloat(4), result.getInt(5));
					list.add(entity);
				}
			} finally {
				result.getStatement().getConnection().close();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}

	@Override
	public void insert(ModelOrder entity) {
		try {
			String sql = "INSERT INTO SANPHAMORDER(MASP, MABAN, GIABAN, SOLUONG) VALUES(?, ?, ?, ?)";
			XJdbc.update(sql, entity.getMaSP(), entity.getMaBan(), entity.getGia(), entity.getSoLuong());
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void update(ModelOrder entity, String ID) {
		try {
			String sql = "UPDATE SANPHAMORDER SET SOLUONG = ? WHERE MABAN = ? AND MASP = ?";
			XJdbc.update(sql, entity.getSoLuong(), entity.getMaBan(), entity.getMaSP());
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void delete(String ID) {
		try {
			String sql = "DELETE FROM SANPHAMORDER WHERE MABAN = ?";
			XJdbc.update(sql, ID);
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void deleteProduct(String maSP, String maBan) {
		try {
			String sql = "DELETE FROM SANPHAMORDER WHERE MASP = ? AND MABAN = ? ";
			XJdbc.update(sql, maSP, maBan);
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void updateChuyenBanOder(ModelBan entity, String ID) {
		try {
			String sql = "UPDATE SANPHAMORDER SET MaBan = ? WHERE MABAN = ?";
			XJdbc.update(sql, entity.getMaBan(), ID);
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void updateMaBan(String maBan, String ID) {
		try {
			String sql = "UPDATE SANPHAMORDER SET MABAN = ? WHERE MABAN = ?";
			XJdbc.update(sql, maBan, ID);
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public List<ModelOrder> selectByBanAll(String maban) {
		List<ModelOrder> list = new ArrayList<>();
		list = selectBySQL("SELECT * FROM SANPHAMORDER WHERE MABAN = ?", maban);
		return list;
	}

	public void deleteByIdOder(int ID) {
		try {
			String sql = "DELETE FROM SANPHAMORDER WHERE ID = ?";
			XJdbc.update(sql, ID);
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void updateByIdOder(ModelOrder entity) {
		try {
			String sql = "UPDATE SANPHAMORDER SET SOLUONG = ? WHERE ID = ?";
			XJdbc.update(sql, entity.getSoLuong(), entity.getId());
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public ModelOrder selectOrderByMaBanVaMaSanPham(String maBan, String maSanPham) {
		ModelOrder entity = null;
		List<ModelOrder> list = selectBySQL("SELECT * FROM SANPHAMORDER WHERE MABAN = ? AND MASP = ?", maBan,
				maSanPham);
		if (list.size() > 0) {
			entity = list.get(0);
		}
		return entity;
	}
}
