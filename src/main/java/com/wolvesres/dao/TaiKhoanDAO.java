package com.wolvesres.dao;

import com.wolvesres.helper.Auth;
import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.model.ModelTaiKhoan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FPT
 */
public class TaiKhoanDAO implements WolvesResDAO<ModelTaiKhoan, String> {

	@Override
	public List<ModelTaiKhoan> selectAll() {
		List<ModelTaiKhoan> list = new ArrayList<>();
		list = selectBySQL("select * from TAIKHOAN");
		return list;
	}

	@Override
	public ModelTaiKhoan selectById(String ID) {
		List<ModelTaiKhoan> list = new ArrayList<>();
		list = selectBySQL("select * from TAIKHOAN WHERE TenTaiKhoan like ?", ID);
		if(list.size()>0) {
			return list.get(0);
		}
			return null;
		
	}

	@Override
	public List<ModelTaiKhoan> selectBySQL(String sql, Object... thamSo) {
		List<ModelTaiKhoan> list = new ArrayList<>();
		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, thamSo);
				while (result.next()) {
					ModelTaiKhoan taiKhoan = new ModelTaiKhoan(result.getString(1), result.getString(2),
							result.getBoolean(3));
					list.add(taiKhoan);
				}
			} finally {
				result.getStatement().getConnection().close();
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public void insert(ModelTaiKhoan taiKhoanMoi) {
		try {
			String sql = "INSERT INTO TAIKHOAN(TenTaiKhoan, MatKhau, TrangThai) VALUES (?,?,?)";
			XJdbc.update(sql, taiKhoanMoi.getTaiKhoan(), taiKhoanMoi.getMatKhau(), taiKhoanMoi.isTrangThai());
		} catch (SQLException ex) {
		}
	}

	@Override
	public void update(ModelTaiKhoan entity, String ID) {
		try {
			String sql = "UPDATE TAIKHOAN SET MatKhau = ?, TrangThai = ? where TenTaiKhoan = ?";
			XJdbc.update(sql, entity.getMatKhau(), entity.isTrangThai(), entity.getTaiKhoan());
		} catch (SQLException ex) {
		}
	}

	@Override
	public void delete(String ID) {
		try {
			String sql = "DELETE from TAIkHOAN WHERE TenTaiKhoan = ?";
			XJdbc.update(sql, ID);
		} catch (SQLException ex) {
		}
	}

	public void UpdateChangePass(String pass, String ID) {
		try {
			String sql = "UPDATE TAIKHOAN SET MatKhau = ? where TenTaiKhoan = ?";
			XJdbc.update(sql, pass, ID);
		} catch (SQLException ex) {
		}
	}

	public ModelTaiKhoan checkForeignKeyHoaDon(ModelTaiKhoan entity) {
		ModelTaiKhoan tk = new ModelTaiKhoan();
		String sql = "SELECT TK.TENTAIKHOAN FROM TAIKHOAN TK JOIN HOADON HD ON TK.TENTAIKHOAN = HD.NGUOIXUAT WHERE TK.TENTAIKHOAN = ?";
		ResultSet result;
		try {
			result = XJdbc.query(sql, entity.getTaiKhoan());
			if (result.next()) {
				tk.setTaiKhoan(result.getString(1));
			}
		} catch (SQLException ex) {
			Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return tk;
	}

	public ModelTaiKhoan checkForeignKeyLichSu(ModelTaiKhoan entity) {
		ModelTaiKhoan tk = new ModelTaiKhoan();
		String sql = "SELECT TK.TENTAIKHOAN FROM TAIKHOAN TK JOIN LICHSU LS ON TK.TENTAIKHOAN = LS.NGUOINHAP WHERE TK.TENTAIKHOAN = ?";
		ResultSet result;
		try {
			result = XJdbc.query(sql, entity.getTaiKhoan());
			if (result.next()) {
				tk.setTaiKhoan(result.getString(1));
			}
		} catch (SQLException ex) {
			Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return tk;
	}

	public ModelTaiKhoan checkLogin(String username, String password) {
		ModelTaiKhoan entity = null;
		String sql = "SELECT * FROM TAIKHOAN WHERE TENTAIKHOAN = ? AND MATKHAU= ? ";
		ResultSet result;
		try {
			result = XJdbc.query(sql, username, password);
			entity = getFromResult(result);
		} catch (SQLException ex) {
			Logger.getLogger(TaiKhoanDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return entity;
	}

	public ModelTaiKhoan getFromResult(ResultSet result) throws SQLException {
		ModelTaiKhoan entity = null;
		if (result.next()) {
			entity = new ModelTaiKhoan(result.getString(1), result.getString(2), result.getBoolean(3));
		}
		return entity;
	}
	
	public List<ModelTaiKhoan> timkiem(String keyword){
    	List<ModelTaiKhoan> list = new ArrayList<ModelTaiKhoan>();
    	String sql = "select tk.TenTaiKhoan, tk.MatKhau, tk.TrangThai from TAIKHOAN tk join NHANVIEN nv on tk.TenTaiKhoan = nv.MaNhanVien where nv.ChucVu != 1 and tk.TenTaiKhoan like ? and tk.TenTaiKhoan != ? and tk.trangthai = 1";
    			list = selectBySQL(sql, "%"+keyword+"%", Auth.user.getMaNV());
    	return list;
    }
}
