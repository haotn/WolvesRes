package com.wolvesres.dao;

import com.wolvesres.helper.XJdbc;
import com.wolvesres.model.ModelKhuBan;
import com.wolvesres.model.ModelSanPham;
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
public class SanPhamDAO implements WolvesResDAO<ModelSanPham, String> {

	@Override
	public List<ModelSanPham> selectAll() {
		List<ModelSanPham> list = new ArrayList<>();
		list = selectBySQL("select * from SANPHAM");
		return list;
	}

	@Override
	public ModelSanPham selectById(String ID) {
		List<ModelSanPham> list = new ArrayList<>();
		list = selectBySQL("select * from SANPHAM where MaSP like ?", ID);
		return list.get(0);
	}

	@Override
	public List<ModelSanPham> selectBySQL(String sql, Object... thamSo) {
		List<ModelSanPham> list = new ArrayList<>();
		try {
			ResultSet result = null;
			try {
				result = XJdbc.query(sql, thamSo);
				while (result.next()) {
					ModelSanPham sanPham = new ModelSanPham(result.getString(1), result.getString(2),
							result.getFloat(3), result.getString(4), result.getString(5), result.getInt(6),
							result.getBoolean(7));
					list.add(sanPham);
				}
			} finally {
				result.getStatement().getConnection().close();
			}
		} catch (Exception e) {
		}
		return list;
	}

	@Override
	public void insert(ModelSanPham sanPhamMoi) {
		try {
			String sql = "INSERT INTO SANPHAM(MaSP, TenSP, GiaBan, MaDanhMucSP, Hinh, DonViTinh, TrangThai) VALUES (?,?,?,?,?,?,?)";
			XJdbc.update(sql, sanPhamMoi.getMaSP(), sanPhamMoi.getTenSP(), sanPhamMoi.getGiaBan(),
					sanPhamMoi.getMaDanhMuc(), sanPhamMoi.getPathAnh(), sanPhamMoi.getMaDVT(),
					sanPhamMoi.isTrangThai());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void update(ModelSanPham sanPhamMoi, String ID) {
		try {
			String sql = "UPDATE SANPHAM SET TenSP = ?, GiaBan = ?, MaDanhMucSP =? , Hinh = ?, DonViTinh = ?, TrangThai = ? where MaSP = ?";
			XJdbc.update(sql, sanPhamMoi.getTenSP(), sanPhamMoi.getGiaBan(), sanPhamMoi.getMaDanhMuc(),
					sanPhamMoi.getPathAnh(), sanPhamMoi.getMaDVT(), sanPhamMoi.isTrangThai(), sanPhamMoi.getMaSP());
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void delete(String ID) {
		try {
			String sql = "DELETE FROM SANPHAM WHERE MaSP = ?";
			XJdbc.update(sql, ID);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public ModelSanPham checkForeignKho(String ID) {
		ModelSanPham entity = null;
		ResultSet result;
		try {
			String sql = "SELECT * FROM SANPHAM SP JOIN KHO KH  ON SP.MaSP = KH.MaSP WHERE SP.MaSP = ?";
			result = XJdbc.query(sql, ID);
			if (result.next()) {
				entity = new ModelSanPham(result.getString(1), result.getString(2), result.getFloat(3),
						result.getString(4), result.getString(5), result.getInt(6), result.getBoolean(7));
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return entity;
	}

	public ModelSanPham checkForeignChiTietLS(String ID) {
		ModelSanPham entity = null;
		ResultSet result;
		try {
			String sql = "SELECT * FROM SANPHAM SP JOIN CHITIETLICHSU LS  ON SP.MaSP = LS.MaSP WHERE SP.MaSP = ?";
			result = XJdbc.query(sql, ID);
			if (result.next()) {
				entity = new ModelSanPham(result.getString(1), result.getString(2), result.getFloat(3),
						result.getString(4), result.getString(5), result.getInt(6), result.getBoolean(7));
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return entity;
	}

	public ModelSanPham checkForeignLichSuGia(String ID) {
		ModelSanPham entity = null;
		ResultSet result;
		try {
			String sql = "SELECT * FROM SANPHAM SP JOIN LICHSUGIA LG  ON SP.MaSP = LG.MaSP WHERE SP.MaSP = ?";
			result = XJdbc.query(sql, ID);
			if (result.next()) {
				entity = new ModelSanPham(result.getString(1), result.getString(2), result.getFloat(3),
						result.getString(4), result.getString(5), result.getInt(6), result.getBoolean(7));
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return entity;
	}

	public ModelSanPham checkForeignHoaDonCT(String ID) {
		ModelSanPham entity = null;
		ResultSet result;
		try {
			String sql = "SELECT * FROM SANPHAM SP JOIN HOADONCHITIET HD  ON SP.MaSP = HD.MaSP WHERE SP.MaSP = ?";
			result = XJdbc.query(sql, ID);
			if (result.next()) {
				entity = new ModelSanPham(result.getString(1), result.getString(2), result.getFloat(3),
						result.getString(4), result.getString(5), result.getInt(6), result.getBoolean(7));
			}
		} catch (SQLException ex) {
			Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
		}
		return entity;
	}
	
	public List<ModelSanPham> timkiem(String keyword){
    	List<ModelSanPham> list = new ArrayList<ModelSanPham>();
    	String sql = "select * from SANPHAM where TenSP like ?";
    			list = selectBySQL(sql, "%"+keyword+"%");
    	return list;
    }
	
	
}
