package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelKhuBan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author FPT
 */
public class KhuBanDAO implements WolvesResDAO<ModelKhuBan, String> {

	@Override
	public List<ModelKhuBan> selectAll() {
		List<ModelKhuBan> list = new ArrayList<>();
		list = selectBySQL("select * from KHUBAN");
		return list;
	}

	@Override
	public ModelKhuBan selectById(String ID) {
		List<ModelKhuBan> list = new ArrayList<>();
		list = selectBySQL("select * from KHUBAN where MaKhuBan = ?", ID);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;

	}

	@Override
	public List<ModelKhuBan> selectBySQL(String sql, Object... thamSo) {
		List<ModelKhuBan> list = new ArrayList<>();
		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, thamSo);
				while (result.next()) {
					ModelKhuBan khuBan = new ModelKhuBan(result.getString(1), result.getString(2), result.getString(3));
					list.add(khuBan);
				}
			} finally {
				result.getStatement().getConnection().close();
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public void insert(ModelKhuBan khuBanMoi) {
		try {
			String sql = "INSERT INTO KHUBAN(MaKhuBan, TenKhuBan, GhiChu) VALUES (?,?,?)";
			XJdbc.update(sql, khuBanMoi.getMaKhuBan(), khuBanMoi.getTenKhuBan(), khuBanMoi.getGhiChu());
		} catch (SQLException ex) {
		}
	}

	@Override
	public void update(ModelKhuBan khuBanMoi, String ID) {
		try {
			String sql = "UPDATE KHUBAN SET TenKhuBan = ?, GhiChu = ? WHERE MaKhuBan = ?";
			XJdbc.update(sql, khuBanMoi.getTenKhuBan(), khuBanMoi.getGhiChu(), khuBanMoi.getMaKhuBan());
		} catch (SQLException ex) {
		}
	}

	@Override
	public void delete(String ID) {
		try {
			String sql = "DELETE FROM KHUBAN WHERE MaKhuBan = ?";
			XJdbc.update(sql, ID);
		} catch (SQLException ex) {
		}
	}

	public ModelKhuBan checkForeignKey(String ID) {
		ModelKhuBan entity = null;
		ResultSet result;
		try {
			String sql = "SELECT * FROM KHUBAN KB JOIN BAN BN ON KB.MaKhuBan=BN.MaKhuBan WHERE KB.MaKhuBan = ?";
			result = XJdbc.query(sql, ID);
			if (result.next()) {
				entity = new ModelKhuBan(result.getString(1), result.getString(2), result.getString(3));
			}
		} catch (SQLException ex) {
		}
		return entity;
	}

	public List<ModelKhuBan> timkiem(String keyword) {
		List<ModelKhuBan> list = new ArrayList<ModelKhuBan>();
		String sql = "select * from KHUBAN where TenKhuBan like ?";
		list = selectBySQL(sql, "%" + keyword + "%");
		return list;
	}
}
