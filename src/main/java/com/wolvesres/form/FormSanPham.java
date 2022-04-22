package com.wolvesres.form;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RIcon.GoogleMaterialDesignIcons;
import com.swing.custom.raven.RIcon.IconFontSwing;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.dao.LichSuGiaDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.form.sanpham.BlackListSanPham;
import com.wolvesres.form.sanpham.EditSanPham;
import com.wolvesres.form.sanpham.JDialogDanhMuc;
import com.wolvesres.form.sanpham.JDialogLichSuGia;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XFormatMoney;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelDonViTinh;
import com.wolvesres.model.ModelKhuBan;
import com.wolvesres.model.ModelLichSuGia;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.swing.table.EventAction;
import com.wolvesres.swing.table.ModelProfile;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
/**
 * Chỉnh sửa tìm kiếm, comment các hàm
 * Liên quan: ModelSanPham
 * @author huynh
 *
 */
public class FormSanPham extends javax.swing.JPanel {

	public FormSanPham(JFrame frame) {
		initComponents();
		setOpaque(false);
		init();
		txtFind.setLabelText("Tìm kiếm Loại Sản Phẩm");
		this.frame = frame;
	}

	JFrame frame;
	List<ModelSanPham> whiteList = new ArrayList<ModelSanPham>();
	List<ModelSanPham> listSP = new ArrayList<ModelSanPham>();
	List<ModelDanhMuc> listDM = new ArrayList<ModelDanhMuc>();
	List<ModelDonViTinh> listDVT = new ArrayList<ModelDonViTinh>();
	SanPhamDAO daoSanPham = new SanPhamDAO();
	DanhMucDAO daoDanhMuc = new DanhMucDAO();
	DonViTinhDAO daoDonViTinh = new DonViTinhDAO();
	LichSuGiaDAO daolsg = new LichSuGiaDAO();
	DefaultTableModel model;
	private int index = -1;
	private boolean desc = true;
	//////////////////////////////////////////////////////////////
	DefaultComboBoxModel<ModelDanhMuc> modelcboDanhmuc = new DefaultComboBoxModel();
	/**
	 * Load dư liệu lên combobox
	 */
	public void loadCboDanhmuc() {
		cboDanhMuc.setModel(modelcboDanhmuc);
		modelcboDanhmuc.removeAllElements();
		for (ModelDanhMuc modelDanhMuc : listDM) {
			modelcboDanhmuc.addElement(modelDanhMuc);
		}
	}
	
	public void actionCboDanhmuc() {
		ModelDanhMuc DM = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
		fillToTable(DM.getMaDanhMuc());
	}

	/**
     * nút update và nút delete trên bảng
     */
	EventAction<ModelSanPham> eventAction = new EventAction<ModelSanPham>() {
		public void delete(ModelSanPham sanpham) {
			if (daoSanPham.checkForeignKho(sanpham.getMaSP()) == null
					&& daoSanPham.checkForeignChiTietLS(sanpham.getMaSP()) == null
					&& daoSanPham.checkForeignHoaDonCT(sanpham.getMaSP()) == null) {
				if (ROptionDialog.showConfirm(frame, "Xác nhận", "Xác nhận xóa sản phẩm?", ROptionDialog.WARNING,
						Color.yellow, Color.black)) {
					daolsg.delete(Integer.parseInt(sanpham.getMaSP()));
					daoSanPham.delete(sanpham.getMaSP());
					ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
					deleteFromList(sanpham);
					fillToTable(danhMuc.getMaDanhMuc());
				}
			} else {
				if (ROptionDialog.showConfirm(frame, "Thông báo xác nhận",
						"Không thể xóa, xác nhận ngưng kinh doanh sản phẩm?", ROptionDialog.PRIORITY_HIGHT, Color.red,
						Color.black)) {
					addToBlackList(sanpham);
				}
			}
		}

		public void update(ModelSanPham sanpham) {
			ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
			EditSanPham editForm = new EditSanPham(frame, true);
			editForm.setInsert(false);
			editForm.setSanPham(sanpham);
			editForm.setForm();
			editForm.setVisible(true);
			if (editForm.getIsDispose() == false) {
				daoSanPham.update(editForm.getSanPham(), editForm.getSanPham().getMaSP());
				updateToList(editForm.getSanPham());
				if (editForm.getSanPham().getGiaBan() != sanpham.getGiaBan()) {
					insertLSG(editForm.getSanPham());
				}
				fillToTable(danhMuc.getMaDanhMuc());
				ROptionDialog.showAlert(frame, "Thông báo", "Cập nhật thành công!", ROptionDialog.NOTIFICATIONS,
						new Color(0, 199, 135), Color.black);
				btnLichSuGia.setVisible(false);
			}
		}
	};

	/**
     * Hàm tổng hợp các hàm bên dưới
     */
	public void init() {
		initTable();
		loadToList();
		loadCboDanhmuc();
		// fillToTable();
		getlistDanhMuc();
		//Phân quyền
		if (!Auth.isBoss() && !Auth.isManager()) {
			btnDanhMuc.setVisible(false);
			btnDuaVaoDSD.setVisible(false);
			btnThem.setVisible(false);
			btnLichSuGia.setVisible(false);
			btnXemDSDen.setVisible(false);
		}
		showDetail(0);
		btnLichSuGia.setVisible(false);
		Icon iconThemSP = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.LIBRARY_ADD, 32, new Color(0, 199, 135));
		btnThem.setIcon(iconThemSP);
		Icon iconDSDen = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FEATURED_PLAY_LIST, 32,
				new Color(0, 199, 135));
		btnXemDSDen.setIcon(iconDSDen);
		Icon iconXemDSDen = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.REMOVE_CIRCLE, 32,
				new Color(0, 199, 135));
		btnDuaVaoDSD.setIcon(iconXemDSDen);
	}
/////////////////////////////////////////////////////////
	// Load List San Pham

	/**
	 * Load dữ liệu lên bảng
	 */
	public void loadToList() {
		listSP.addAll(daoSanPham.selectAll());
		listDM.addAll(daoDanhMuc.selectAll());
		listDVT.addAll(daoDonViTinh.selectAll());
	}

	/**
	 * fill dư liệu trên bảng
	 * @param maDM
	 */
	public void fillToTable(String maDM) {
		loadToWhiteList(maDM);
		model.setRowCount(0);
		for (ModelSanPham sp : whiteList) {
			if (sp.getPathAnh() != null) {
				sp.setIcon(XImage.readImageThucDon(sp.getPathAnh()));
			} else {
				sp.setIcon(null);
			}
			if (!Auth.isBoss() && !Auth.isManager()) {
				tblSanpham.addRow(sp.toRowTableMINV(listDM));
			} else {
				tblSanpham.addRow(sp.toRowTable(eventAction, listDM));
			}

		}
	}

	/**
     * Hàm desigs bảng trên form 
     */
	private void initTable() {
		tblSanpham.setOpaque(true);
		tblSanpham.setBackground(new Color(255, 255, 255));
		tblSanpham.setFillsViewportHeight(true);
		tblSanpham.fixTable(jScrollPane2);
		if (!Auth.isBoss() && !Auth.isManager()) {
			model = new DefaultTableModel(new Object[][] {},
					new Object[] { "Tên sản phẩm", "Danh mục", "Giá bán", "Loại" });
		} else {
			model = new DefaultTableModel(new Object[][] {},
					new Object[] { "Tên sản phẩm", "Danh mục", "Giá bán", "Loại", "Thao tác" });
		}

		tblSanpham.setModel(model);
		tblSanpham.setColumnAction(4);
	}

	/**
	 * select bảng
	 * @param selectedRow
	 */
	public void showDetail(int selectedRow) {
		if (selectedRow >= 0) {
			ModelSanPham sp = whiteList.get(selectedRow);
			if (sp.getPathAnh() != null) {
				lblAvatar.setIcon(XImage.readImageThucDon(sp.getPathAnh()));
			}
			lblMaSP.setText(sp.getMaSP());
			lblTenSP.setText(sp.getTenSP());
			lblLoaiSP.setText(sp.getTenDanhMuc());
			lblGia.setText(XFormatMoney.formatMoney(sp.getGiaBan()));
			lblMathang.setText(sp.getLoaiSanPham());
			lblTrangthai.setText(sp.isTrangThai() ? "Đang hoạt động" : "Ngừng kinh doanh");
			lblDonvitinh.setText(sp.getTenDonViTinh());
		}
	}

	public List<ModelDanhMuc> getlistDanhMuc() {
		return this.listDM;
	}

	/**
	 * Hàm clear form
	 */
	public void clearForm() {
		lblAvatar.setIcon(null);
		lblMaSP.setText("");
		lblTenSP.setText("");
		lblLoaiSP.setText("");
		lblGia.setText("");
		lblMathang.setText("");
		lblTrangthai.setText("");
		lblDonvitinh.setText("");
	}

	public List<ModelSanPham> getList() {
		return this.listSP;
	}

	/**
	 * Add to whitelist
	 * @param maDM
	 */
	public void loadToWhiteList(String maDM) {
		whiteList.clear();
		for (ModelSanPham entity : listSP) {
			if (entity.isTrangThai() && entity.getMaDanhMuc().equals(maDM)) {
				whiteList.add(entity);
			}
		}
	}

	/**
	 * Hàm update sản phẩm
	 * @param ud
	 */
	public void updatedata(ModelSanPham ud) {
		daoSanPham.update(ud, ud.getMaSP());
	}
	
	public void updateList(ModelSanPham ud) {
		for (int i = 0; i < listSP.size(); i++) {
			if (ud.getMaSP().equals(listSP.get(i).getMaSP())) {
				listSP.set(i, ud);
				break;
			}
		}
	}
	
	public void updateSanPham(ModelSanPham ud) {
		ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
		updatedata(ud);
		updateList(ud);
		fillToTable(danhMuc.getMaDanhMuc());
	}
	
	

	/**
	 * Hàm insert lịch sử giá
	 * @param ud
	 */
	public void insertLSG(ModelSanPham sp) {
		insertData(sp);
		ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
		fillToTable(danhMuc.getMaDanhMuc());
	}
	
	public void insertData(ModelSanPham sp) {
		ModelLichSuGia ls = new ModelLichSuGia();
		ls.setMaSP(sp.getMaSP());
		ls.setGia(sp.getGiaBan());
		ls.setNgayThayDoi(XDate.toString(new Date(), "dd-MM-yyyy"));
		daolsg.insert(ls);
	}
	
	/**
	 * Hàm thêm sản phẩm
	 * @param entity
	 */
	public void insertSanPham(ModelSanPham entity) {
		insertdata(entity);
		fillinsert(entity);
	}
	
	public void insertdata(ModelSanPham entity) {
		daoSanPham.insert(entity);
	}
	
	public void fillinsert(ModelSanPham entity) {
		listSP.add(entity);
//		ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
//		fillToTable(danhMuc.getMaDanhMuc());
	}
	
	/**
	 * Hàm update bảng
	 */
	public void updateToList(ModelSanPham entity) {
		updataToListData(entity);
		fillupdataToList(entity);
	}
	
	public void updataToListData(ModelSanPham entity) {
		daoSanPham.update(entity, entity.getMaSP());
	}
	
	public void fillupdataToList(ModelSanPham entity) {
		ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
		for (int i = 0; i < listSP.size(); i++) {
			if (entity.getMaSP().equals(listSP.get(i).getMaSP())) {
				listSP.set(i, entity);
				break;
			}
		}
		fillToTable(danhMuc.getMaDanhMuc());
	}
	
	/**
	 * xóa trên bảng
	 */
	public void deleteFromList(ModelSanPham entity) {
		deleteFomListData(entity);
		fillDeleteFromList(entity);
	}
	
	public void deleteFomListData(ModelSanPham entity) {
		daoSanPham.delete(entity.getMaSP());
	}
	
	public void fillDeleteFromList(ModelSanPham entity) {
		listSP.remove(entity);
		ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
		fillToTable(danhMuc.getMaDanhMuc());
	}

	/**
	 * Lấy tài khoản từ 1 dòng trên bảng
	 * @param row
	 * @return
	 */
	private ModelSanPham getSanPhambyRowTable(int row) {
		ModelSanPham entity = new ModelSanPham();
		Object o = (Object) tblSanpham.getValueAt(row, 0);
		if (o instanceof ModelProfile) {
			ModelProfile mpf = (ModelProfile) o;
			entity = mpf.getProduct();
		}
		return entity;
	}

	/**
	 * Đưa tài khoản vào danh sách đen
	 * @param entity
	 */
	public void addToBlackList(ModelSanPham entity) {
		addToBlackListData(entity);
		updateSanPham(entity);
		model.setRowCount(0);
		ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
		fillToTable(danhMuc.getMaDanhMuc());
	}
	
	public void addToBlackListData(ModelSanPham entity) {
		entity.setTrangThai(false);
		daoSanPham.update(entity, entity.getMaSP());
	}

	/**
	 * Lấy tài khoản từ 1 dòng trên bảng
	 * @param row
	 * @return
	 */
	private ModelSanPham getSPformRowtable(int row) {
		ModelSanPham sanPham = new ModelSanPham();
		Object o = (Object) tblSanpham.getValueAt(row, 0);
		if (o instanceof ModelProfile) {
			ModelProfile mpf = (ModelProfile) o;
			sanPham = mpf.getProduct();
		}
		return sanPham;
	}
	
	/**
	 * tìm kiếm theo tên sản phẩm
	 */
	 public List<ModelSanPham> timkiem(String keyword){
	        List<ModelSanPham> listFind = new ArrayList<>();
	        	if(keyword.trim().length() > 0) {
	        		listFind = daoSanPham.timkiem(keyword);
	        	}else {
	        		listFind = daoSanPham.selectAll();
	        	}
	        return listFind;
	    }

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		txtFind = new com.swing.custom.raven.RTextField.RTextField();
		jLabel2 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		lblTrangthai = new javax.swing.JLabel();
		jLabel12 = new javax.swing.JLabel();
		lblMaSP = new javax.swing.JLabel();
		lblTenSP = new javax.swing.JLabel();
		lblLoaiSP = new javax.swing.JLabel();
		lblGia = new javax.swing.JLabel();
		lblMathang = new javax.swing.JLabel();
		jLabel18 = new javax.swing.JLabel();
		lblDonvitinh = new javax.swing.JLabel();
		btnDuaVaoDSD = new com.swing.custom.raven.RButton.RButton();
		lblAvatar = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		rRoundPanel5 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel10 = new javax.swing.JLabel();
		btnThem = new com.swing.custom.raven.RButton.RButton();
		filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0),
				new java.awt.Dimension(0, 0));
		btnXemDSDen = new com.swing.custom.raven.RButton.RButton();
		btnDanhMuc = new com.swing.custom.raven.RButton.RButton();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblSanpham = new com.wolvesres.swing.table.Table();
		btnLichSuGia = new com.swing.custom.raven.RButton.RButton();
		cboDanhMuc = new com.swing.custom.raven.RComboBox.RComboBoxSuggestion();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel6 = new javax.swing.JLabel();
		jLabel7 = new javax.swing.JLabel();
		rImageAvatar2 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();

		setMaximumSize(new java.awt.Dimension(1170, 730));
		setMinimumSize(new java.awt.Dimension(1170, 730));
		setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rRoundPanel1.setBackground(new java.awt.Color(21, 25, 29));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rRoundPanel2.setBackground(new java.awt.Color(6, 7, 13));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		txtFind.setBackground(new java.awt.Color(6, 7, 13));
		txtFind.setForeground(new java.awt.Color(255, 255, 255));
		txtFind.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		txtFind.setLabelText("Tìm Kiếm trong thực đơn");
		txtFind.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtFindKeyReleased(evt);
			}
		});
		rRoundPanel2.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 50));

		jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("Loại Sản Phẩm:");
		rRoundPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 380, -1, -1));

		jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel4.setForeground(new java.awt.Color(255, 255, 255));
		jLabel4.setText("Loại:");
		rRoundPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 440, -1, -1));

		jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("Trạng Thái:");
		rRoundPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 470, -1, -1));

		jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel8.setForeground(new java.awt.Color(255, 255, 255));
		jLabel8.setText("Giá:");
		rRoundPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, -1, -1));

		jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel9.setForeground(new java.awt.Color(255, 255, 255));
		jLabel9.setText("Tên Sản Phẩm:");
		rRoundPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, -1, -1));

		lblTrangthai.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblTrangthai.setForeground(new java.awt.Color(255, 255, 255));
		lblTrangthai.setText("Đang hoạt động");
		rRoundPanel2.add(lblTrangthai, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 470, 170, -1));

		jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel12.setForeground(new java.awt.Color(255, 255, 255));
		jLabel12.setText("Mã Sản Phẩm:");
		rRoundPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, 110, -1));

		lblMaSP.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblMaSP.setForeground(new java.awt.Color(255, 255, 255));
		lblMaSP.setText("SP01");
		rRoundPanel2.add(lblMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 110, -1));

		lblTenSP.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblTenSP.setForeground(new java.awt.Color(255, 255, 255));
		lblTenSP.setText("Pessi");
		rRoundPanel2.add(lblTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 350, 260, -1));

		lblLoaiSP.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblLoaiSP.setForeground(new java.awt.Color(255, 255, 255));
		lblLoaiSP.setText("DM01");
		rRoundPanel2.add(lblLoaiSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 380, 260, -1));

		lblGia.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblGia.setForeground(new java.awt.Color(255, 255, 255));
		lblGia.setText("20000");
		rRoundPanel2.add(lblGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 410, 260, -1));

		lblMathang.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblMathang.setForeground(new java.awt.Color(255, 255, 255));
		lblMathang.setText("Mặt hàng");
		rRoundPanel2.add(lblMathang, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 440, 110, -1));

		jLabel18.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel18.setForeground(new java.awt.Color(255, 255, 255));
		jLabel18.setText("DVT");
		rRoundPanel2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 320, -1, -1));

		lblDonvitinh.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblDonvitinh.setForeground(new java.awt.Color(255, 255, 255));
		lblDonvitinh.setText("Lon");
		rRoundPanel2.add(lblDonvitinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 320, 70, -1));

		btnDuaVaoDSD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/minus.png"))); // NOI18N
		btnDuaVaoDSD.setText("Thêm vào Danh Sách Đen");
		btnDuaVaoDSD.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDuaVaoDSDActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnDuaVaoDSD, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 570, -1, 40));
		rRoundPanel2.add(lblAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 70, 220, 210));

		rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 110, 440, 620));

		rRoundPanel5.setBackground(new java.awt.Color(6, 7, 13));
		rRoundPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel10.setForeground(new java.awt.Color(255, 255, 255));
		jLabel10.setText("Danh sách Sản Phẩm");
		rRoundPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 190, 30));

		btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/plus.png"))); // NOI18N
		btnThem.setText("Thêm Sản Phẩm");
		btnThem.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnThem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		btnThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThemActionPerformed(evt);
			}
		});
		rRoundPanel5.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 160, 32));
		rRoundPanel5.add(filler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 700, -1, -1));

		btnXemDSDen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/invoice_1.png"))); // NOI18N
		btnXemDSDen.setText("Xem danh sách đem");
		btnXemDSDen.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnXemDSDen.setPreferredSize(new java.awt.Dimension(175, 30));
		btnXemDSDen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXemDSDenActionPerformed(evt);
			}
		});
		rRoundPanel5.add(btnXemDSDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 570, 190, 40));

		btnDanhMuc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/categories.png"))); // NOI18N
		btnDanhMuc.setText("Danh Mục Sản Phẩm");
		btnDanhMuc.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnDanhMuc.setPreferredSize(new java.awt.Dimension(175, 30));
		btnDanhMuc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDanhMucActionPerformed(evt);
			}
		});
		rRoundPanel5.add(btnDanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 570, 200, 40));

		jScrollPane2.setBorder(null);

		tblSanpham.setAutoCreateRowSorter(true);
		tblSanpham
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblSanpham.setOpaque(false);
		tblSanpham.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				tblSanphamFocusGained(evt);
			}

			public void focusLost(java.awt.event.FocusEvent evt) {
				tblSanphamFocusLost(evt);
			}
		});
		tblSanpham.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				tblSanphamMouseClicked(evt);
			}

			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblSanphamMousePressed(evt);
			}
		});
		jScrollPane2.setViewportView(tblSanpham);

		rRoundPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 720, 510));

		btnLichSuGia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/salary.png"))); // NOI18N
		btnLichSuGia.setText("Lịch sử giá");
		btnLichSuGia.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnLichSuGia.setPreferredSize(new java.awt.Dimension(175, 30));
		btnLichSuGia.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLichSuGiaActionPerformed(evt);
			}
		});
		rRoundPanel5.add(btnLichSuGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 570, 200, 40));

		cboDanhMuc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboDanhMucActionPerformed(evt);
			}
		});
		rRoundPanel5.add(cboDanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 200, 30));

		rRoundPanel1.add(rRoundPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 720, 620));

		rRoundPanel3.setBackground(new java.awt.Color(0, 199, 135));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel6.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel6.setForeground(new java.awt.Color(255, 255, 255));
		jLabel6.setText("THỰC ĐƠN");
		rRoundPanel3.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 0, 250, 110));

		jLabel7.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
		jLabel7.setForeground(new java.awt.Color(255, 255, 255));
		jLabel7.setText("WolvesRes");
		rRoundPanel3.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, 150, 30));

		rImageAvatar2.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel3.add(rImageAvatar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 120, 110));

		rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

		add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 730));
	}// </editor-fold>//GEN-END:initComponents

	private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed

		EditSanPham editform = new EditSanPham(frame, true);
		editform.setInsert(true);
		editform.setVisible(true);
		if (editform.getSanPham() != null) {
			insertSanPham(editform.getSanPham());
			insertLSG(editform.getSanPham());
			ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
			fillToTable(danhMuc.getMaDanhMuc());
		}
	}// GEN-LAST:event_btnThemActionPerformed

	private void btnXemDSDenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXemDSDenActionPerformed
		BlackListSanPham bl = new BlackListSanPham(frame, true);
		bl.setVisible(true);
		if (bl.getChangeData()) {
			for (int i = 0; i < listSP.size(); i++) {
				for (int j = 0; j < bl.getListReturn().size(); j++) {
					if (listSP.get(i).getMaSP().equals(bl.getListReturn().get(j).getMaSP())) {
						listSP.set(i, bl.getListReturn().get(j));
					}
				}
			}
		}
		ModelDanhMuc danhMuc = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
		fillToTable(danhMuc.getMaDanhMuc());
	}// GEN-LAST:event_btnXemDSDenActionPerformed

	private void btnDanhMucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDanhMucActionPerformed
		new JDialogDanhMuc(frame, true).setVisible(true);
	}// GEN-LAST:event_btnDanhMucActionPerformed

	private void tblSanphamMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblSanphamMouseClicked

	}// GEN-LAST:event_tblSanphamMouseClicked

	private void btnDuaVaoDSDActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDuaVaoDSDActionPerformed
		if (index >= 0) {
			if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc chắn muốn đưa vào danh sách đen?",
					ROptionDialog.WARNING, Color.yellow, Color.black)) {
				ModelSanPham entity = whiteList.get(tblSanpham.getSelectedRow());
				addToBlackList(entity);
			}
		} else {
			ROptionDialog.showAlert(frame, "Thông báo", "Vui lòng chọn dữ liệu!", ROptionDialog.WARNING, Color.red,
					Color.black);
		}
	}// GEN-LAST:event_btnDuaVaoDSDActionPerformed

	private void txtFindKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtFindKeyReleased
		String keyword = txtFind.getText().trim();
    	listSP = timkiem(keyword);
		ModelDanhMuc DM = (ModelDanhMuc) cboDanhMuc.getSelectedItem();
		fillToTable(DM.getMaDanhMuc());
	}// GEN-LAST:event_txtFindKeyReleased

	private void btnLichSuGiaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLichSuGiaActionPerformed
		JDialogLichSuGia lichsu = new JDialogLichSuGia(frame, true);
		ModelSanPham sanpham = getSPformRowtable(index);
		lichsu.setSp(sanpham);
		lichsu.init();
		lichsu.setVisible(true);
		System.out.println(sanpham.getMaSP());
	}// GEN-LAST:event_btnLichSuGiaActionPerformed

	private void tblSanphamMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblSanphamMousePressed
		index = tblSanpham.getSelectedRow();
		showDetail(tblSanpham.getSelectedRow());
		// btnLichSuGia.setVisible(true);
	}// GEN-LAST:event_tblSanphamMousePressed

	private void tblSanphamFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_tblSanphamFocusGained
		if (tblSanpham.getSelectedRow() >= 0) {
			btnLichSuGia.setVisible(true);
		}
	}// GEN-LAST:event_tblSanphamFocusGained

	private void tblSanphamFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_tblSanphamFocusLost
		btnLichSuGia.setVisible(false);
	}// GEN-LAST:event_tblSanphamFocusLost

	private void cboDanhMucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboDanhMucActionPerformed
		actionCboDanhmuc();//////////////////////////////////////
	}// GEN-LAST:event_cboDanhMucActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.swing.custom.raven.RButton.RButton btnDanhMuc;
	private com.swing.custom.raven.RButton.RButton btnDuaVaoDSD;
	private com.swing.custom.raven.RButton.RButton btnLichSuGia;
	private com.swing.custom.raven.RButton.RButton btnThem;
	private com.swing.custom.raven.RButton.RButton btnXemDSDen;
	private com.swing.custom.raven.RComboBox.RComboBoxSuggestion cboDanhMuc;
	private javax.swing.Box.Filler filler1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel12;
	private javax.swing.JLabel jLabel18;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JScrollPane jScrollPane2;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar lblAvatar;
	private javax.swing.JLabel lblDonvitinh;
	private javax.swing.JLabel lblGia;
	private javax.swing.JLabel lblLoaiSP;
	private javax.swing.JLabel lblMaSP;
	private javax.swing.JLabel lblMathang;
	private javax.swing.JLabel lblTenSP;
	private javax.swing.JLabel lblTrangthai;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel5;
	private com.wolvesres.swing.table.Table tblSanpham;
	private com.swing.custom.raven.RTextField.RTextField txtFind;
	// End of variables declaration//GEN-END:variables
}
