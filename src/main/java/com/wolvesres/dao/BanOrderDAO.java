package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelBanOrder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BanOrderDAO implements WolvesResDAO<ModelBanOrder, String> {

	@Override
	public List<ModelBanOrder> selectAll() {
		List<ModelBanOrder> list = new ArrayList<>();
		list = selectBySQL("SELECT * FROM BANORDER");
		return list;
	}

	@Override
	public ModelBanOrder selectById(String ID) {
		List<ModelBanOrder> list = new ArrayList<>();
		list = selectBySQL("SELECT * FROM BANORDER WHERE MABAN = ?", ID);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ModelBanOrder> selectBySQL(String sql, Object... o) {
		List<ModelBanOrder> list = new ArrayList<>();

		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, o);
				while (result.next()) {
					ModelBanOrder entity = new ModelBanOrder(result.getString(1), result.getString(2),
							result.getString(3), result.getString(4));
					list.add(entity);
				}
			} finally {
				result.getStatement().getConnection().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void insert(ModelBanOrder entity) {
		try {
			String sql = "INSERT INTO BANORDER(MABAN, GHICHU, MAVOUCHER, MaBanGop) VALUES(?, ?, ?, ?)";
			XJdbc.update(sql, entity.getMaBan(), entity.getGhiChu(), entity.getMaVoucher(), entity.getMaBanGop());
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void update(ModelBanOrder entity, String ID) {
		try {
			String sql = "UPDATE BANORDER SET  GHICHU = ? , MAVOUCHER = ?,MaBanGop = ? WHERE MABAN = ? ";
			XJdbc.update(sql, entity.getGhiChu(), entity.getMaVoucher(), entity.getMaBanGop(), ID);
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Override
	public void delete(String ID) {
		try {
			String sql = "DELETE FROM BANORDER WHERE MABAN =?";
			XJdbc.update(sql, ID);
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void updateChuyenBan(ModelBan entity, String ID) {
		try {
			String sql = "UPDATE BANORDER SET MaBan = ? WHERE MABAN = ? ";
			XJdbc.update(sql, ID, entity.getMaBan());
		} catch (SQLException ex) {
			Logger.getLogger(BanOrderDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
