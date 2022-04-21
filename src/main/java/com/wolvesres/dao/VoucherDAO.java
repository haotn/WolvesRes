package com.wolvesres.dao;

import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelVouCher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cac class lien quan FormVoucher, BlackListVoucher, EditVoucher, ModelVoucher,
 * CanVoucher
 * 
 * @author Brian
 *
 */
public class VoucherDAO implements WolvesResDAO<ModelVouCher, String> {
	/**
	 * Select all voucher
	 */
	@Override
	public List<ModelVouCher> selectAll() {
		List<ModelVouCher> list = new ArrayList<>();
		list = selectBySQL("select * from VOUCHER");
		return list;
	}

	/**
	 * Select by id
	 */
	@Override
	public ModelVouCher selectById(String id) {
		List<ModelVouCher> list = new ArrayList<>();
		list = selectBySQL("SELECT * FROM VOUCHER WHERE MaVoucher = ?", id);
		if(list.size()>0) {
			return list.get(0);
		}
			return null;
	}

	/**
	 * Select by sql
	 */
	@Override
	public List<ModelVouCher> selectBySQL(String sql, Object... Entity) {
		List<ModelVouCher> list = new ArrayList<>();
		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, Entity);

				while (result.next()) {
					ModelVouCher K = new ModelVouCher(result.getString(1),
							XDate.toString(result.getDate(2), "dd-MM-yyyy"),
							XDate.toString(result.getDate(3), "dd-MM-yyyy"), result.getFloat(4), result.getInt(5),
							result.getString(6), result.getBoolean(7));
					list.add(K);
				}
			} finally {
				result.getStatement().getConnection().close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * Insert to database
	 */
	@Override
	public void insert(ModelVouCher Entity) {
		try {
			String sql = "INSERT INTO VOUCHER(MaVoucher, NgayBatDau, NgayKetThuc,GiamGia, SoLuong, QR, TrangThai)  VALUES (?, ?,?,?,?,?,?)";
			XJdbc.update(sql, Entity.getMaVoucher(),
					XDate.toDate(XDate.toString(XDate.toDate(Entity.getNgayBatDau(), "dd-MM-yyyy"), "yyyy-MM-dd"),
							"yyyy-MM-dd"),
					XDate.toDate(XDate.toString(XDate.toDate(Entity.getNgayKetThuc(), "dd-MM-yyyy"), "yyyy-MM-dd"),
							"yyyy-MM-dd"),
					Entity.getGiamGia(), Entity.getSoLuong(), Entity.getPathQR(), Entity.isTrangThai());
		} catch (SQLException ex) {
			Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Update to database
	 */
	@Override
	public void update(ModelVouCher Entity, String id) {
		try {
			String sql = "UPDATE VOUCHER SET MaVoucher =?, NgayBatDau= ?, NgayKetThuc= ?, GiamGia= ?, SoLuong= ?, QR=?, TrangThai=?  WHERE MaVoucher= ?";
			XJdbc.update(sql, Entity.getMaVoucher(),
					XDate.toDate(XDate.toString(XDate.toDate(Entity.getNgayBatDau(), "dd-MM-yyyy"), "yyyy-MM-dd"),
							"yyyy-MM-dd"),
					XDate.toDate(XDate.toString(XDate.toDate(Entity.getNgayKetThuc(), "dd-MM-yyyy"), "yyyy-MM-dd"),
							"yyyy-MM-dd"),
					Entity.getGiamGia(), Entity.getSoLuong(), Entity.getPathQR(), Entity.isTrangThai(), id);
		} catch (SQLException ex) {
			Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Delete from database
	 */
	@Override
	public void delete(String id) {
		try {
			String sql = "DELETE FROM VOUCHER WHERE MaVoucher=?";
			XJdbc.update(sql, id);
		} catch (SQLException ex) {
			Logger.getLogger(KhoDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Check foreign key
	 * 
	 * @param vc
	 * @return ModelVouCher is conflic
	 */
	public ModelVouCher checkForeignKey(ModelVouCher vc) {
		ModelVouCher voucher = null;
		try {
			String sql = "SELECT * FROM VOUCHER VC JOIN HOADON HD ON VC.MaVoucher = HD.MaVoucher WHERE VC.MaVoucher = ?";
			ResultSet result;
			result = XJdbc.query(sql, vc.getMaVoucher());
			if (result.next()) {
				voucher = new ModelVouCher(result.getString(1), XDate.toString(result.getDate(2), "dd-MM-yyyy"),
						XDate.toString(result.getDate(3), "dd-MM-yyyy"), result.getInt(4), result.getInt(5),
						result.getString(6), result.getBoolean(7));
			}
		} catch (SQLException ex) {
			Logger.getLogger(VoucherDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return voucher;
	}

	public List<ModelVouCher> findVoucher(String keyword) {
		List<ModelVouCher> list = new ArrayList<ModelVouCher>();
		String sql = "SELECT * FROM VOUCHER WHERE MAVOUCHER LIKE ? OR CONVERT( VARCHAR(20),NGAYBATDAU, 105) LIKE ? OR CONVERT(VARCHAR(20),NGAYKETTHUC,  105) LIKE ? ";
		list = selectBySQL(sql, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
		return list;
	}

}
