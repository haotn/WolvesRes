package com.wolvesres.dao;

import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelNhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cac class lien quan: FormNhanVien, NhanVienDAO, EditNhanVien,
 * BlackListNhanVien
 * 
 * @author Brian
 *
 */
public class NhanVienDAO implements WolvesResDAO<ModelNhanVien, String> {

	@Override
	public List<ModelNhanVien> selectAll() {
		List<ModelNhanVien> list = new ArrayList<>();
		list = selectBySQL("select * from NHANVIEN");
		return list;
	}

	@Override
	public ModelNhanVien selectById(String ID) {
		List<ModelNhanVien> list = new ArrayList<>();
		list = selectBySQL("select * from NHANVIEN where MaNhanVien = ?", ID);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ModelNhanVien> selectBySQL(String sql, Object... thamSo) {
		List<ModelNhanVien> list = new ArrayList<>();

		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, thamSo);
				while (result.next()) {
					ModelNhanVien doituong = new ModelNhanVien(result.getString(1), result.getString(2),
							result.getBoolean(3), XDate.toString(result.getDate(4), "dd-MM-yyyy"), result.getString(5),
							result.getString(6), result.getString(7), result.getString(8), result.getInt(9),
							result.getBoolean(10));
					list.add(doituong);
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
	public void insert(ModelNhanVien entity) {
		try {
			String sql = "INSERT INTO NHANVIEN (MaNhanvien, HoTen,GioiTinh, NgaySinh, CMND, SDT, Email, HinhAnh, ChucVu, TrangThai) VALUES (?,?,?,?,?,?,?,?,?,?)";
			XJdbc.update(sql, entity.getMaNV(), entity.getHoTen(), entity.isGioiTinh(),
					XDate.toDate(XDate.toString(XDate.toDate(entity.getNgaySinh(), "dd-MM-yyyy"), "yyyy-MM-dd"),
							"yyyy-MM-dd"),
					entity.getCMND(), entity.getSoDT(), entity.getEmail(), entity.getPathHinhAnh(), entity.getChucVu(),
					entity.isTrangThai());
		} catch (SQLException ex) {
		}
	}

	@Override
	public void update(ModelNhanVien entity, String ID) {
		try {
			String sql = "UPDATE NHANVIEN SET HoTen = ?, GioiTinh =?, NgaySinh = ?, CMND = ?, SDT = ?, Email = ?, HinhAnh = ?, ChucVu = ?, TrangThai = ? where MaNhanVien = ?";
			XJdbc.update(sql, entity.getHoTen(), entity.isGioiTinh(),
					XDate.toDate(XDate.toString(XDate.toDate(entity.getNgaySinh(), "dd-MM-yyyy"), "yyyy-MM-dd"),
							"yyyy-MM-dd"),
					entity.getCMND(), entity.getSoDT(), entity.getEmail(), entity.getPathHinhAnh(), entity.getChucVu(),
					entity.isTrangThai(), entity.getMaNV());
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public ModelNhanVien checkForeignKey(String ID) {
		ModelNhanVien entity = null;
		ResultSet result;
		try {
			String sql = "SELECT * FROM NHANVIEN NV JOIN TAIKHOAN TK ON NV.MaNhanVien = TK.TenTaiKhoan WHERE NV.MaNhanVien = ?";
			result = XJdbc.query(sql, ID);
			if (result.next()) {
				entity = new ModelNhanVien(result.getString(1), result.getString(2), result.getBoolean(3),
						XDate.toString(result.getDate(4), "dd-MM-yyyy"), result.getString(5), result.getString(6),
						result.getString(7), result.getString(8), result.getInt(9), result.getBoolean(10));
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return entity;
	}

	@Override
	public void delete(String ID) {
		try {
			String sql = "DELETE from NHANVIEN where MaNhanVien = ?";
			XJdbc.update(sql, ID);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public List<ModelNhanVien> findNhanVien(String keyword) {
		List<ModelNhanVien> list = new ArrayList<ModelNhanVien>();
		String sql = "SELECT * FROM NHANVIEN WHERE HOTEN LIKE ? OR NGAYSINH LIKE ? OR EMAIL LIKE ? OR CMND LIKE ? OR SDT LIKE ?";
		list = selectBySQL(sql, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%",
				"%" + keyword + "%");
		return list;
	}
}
