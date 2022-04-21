package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelBan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanDAO implements WolvesResDAO<ModelBan, String> {

	@Override
	public List<ModelBan> selectAll() {
		List<ModelBan> list = new ArrayList<>();
		list = selectBySQL("select * from BAN");
		return list;
	}

	@Override
	public ModelBan selectById(String ID) {
		List<ModelBan> list = new ArrayList<>();
		list = selectBySQL("select * from BAN where MaBan = ?", ID);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ModelBan> selectBySQL(String sql, Object... thamSo) {
		List<ModelBan> list = new ArrayList<>();
		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, thamSo);
				while (result.next()) {
					ModelBan entity = new ModelBan(result.getString(1), result.getString(2), result.getBoolean(3),
							result.getString(4));
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
	public void insert(ModelBan entity) {
		try {
			String sql = "INSERT INTO BAN(MaBan, TenBan, HoatDong, MaKhuBan) VALUES (?,?,?,?)";
			XJdbc.update(sql, entity.getMaBan(), entity.getTenBan(), entity.isHoatDong(), entity.getMaKhuBan());
		} catch (SQLException ex) {
		}
	}

	@Override
	public void update(ModelBan entity, String ID) {
		try {
			String sql = "UPDATE BAN SET TenBan = ?, HoatDong = ?, MaKhuBan = ? WHERE MaBan = ?";
			XJdbc.update(sql, entity.getTenBan(), entity.isHoatDong(), entity.getMaKhuBan(), entity.getMaBan());
		} catch (SQLException ex) {
		}
	}

	@Override
	public void delete(String ID) {
		try {
			String sql = "DELETE FROM BAN WHERE MaBan = ?";
			XJdbc.update(sql, ID);
		} catch (SQLException ex) {
		}
	}

	public ModelBan checkForeignKey(ModelBan entity) {
		ModelBan ban = new ModelBan();
		String sql = "SELECT B.MABAN FROM BAN B  JOIN HOADON HD ON B.MABAN = HD.MABAN WHERE B.MABAN =?";
		ResultSet resutl;
		try {
			resutl = XJdbc.query(sql, entity.getMaBan());
			if (resutl.next()) {
				ban.setMaBan(resutl.getString(1));
			}
		} catch (SQLException ex) {
			Logger.getLogger(BanDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return ban;
	}
}
