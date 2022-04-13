package com.wolvesres.form.kho;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.ChiTietLichSuDAO;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.LichSuDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.form.FormKho;
import com.wolvesres.form.sanpham.EditSanPham;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelChiTietLichSu;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelLichSu;
import com.wolvesres.model.ModelNhapKho;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.swing.table.EventActionBlackList;
import com.wolvesres.swing.table.ModelProfile;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Nhập kho, lưu lịch sử nhập kho, lưu lịch sử chi tiết nhập kho Lien
 * Quan:ModelKho(insert),ModelLichSu(insert),ModelChiTietLichSu(insert),
 */
public class JDialogNhapKho extends javax.swing.JDialog {

	private JFrame frame;
	private int id = 1;
	private List<ModelKho> listKho = new ArrayList<>();
	private List<ModelKho> listKhoadd = new ArrayList<>();
	private List<ModelNhapKho> listNhapKho = new ArrayList<>();
	private List<ModelSanPham> listSP = new ArrayList();
	private List<ModelSanPham> whitelistSP = new ArrayList();
	private List<ModelDanhMuc> listDanhMuc = new ArrayList<>();
	private List<ModelDanhMuc> listDanhMucMatHang = new ArrayList<>();
	private List<ModelLichSu> listLS = new ArrayList();
	private List<ModelChiTietLichSu> listCT = new ArrayList();
	private FormKho formParent = new FormKho(frame);
	private KhoDAO khoDAO = new KhoDAO();
	private SanPhamDAO spDAO = new SanPhamDAO();
	private LichSuDAO lsDAO = new LichSuDAO();
	private DanhMucDAO danhMucDAO = new DanhMucDAO();
	private ChiTietLichSuDAO ctDAO = new ChiTietLichSuDAO();
	private FormValidator validator = new FormValidator();

	public JDialogNhapKho(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setLocationRelativeTo(null);
		this.frame = (JFrame) parent;
		init();
	}

	private EventActionBlackList<ModelNhapKho> event = new EventActionBlackList<ModelNhapKho>() {

		@Override
		public void update(ModelNhapKho entity) {
			deleteToListKho(entity);
		}
	};

	//
	public void deleteToListKho(ModelNhapKho entity) {
		listNhapKho.remove(entity);
		fillToTableKho();
	}

	//
	private void init() {
		initTableSP();
		initTableKho();
		loadToListDanhMuc();
		fillToTableSanPham();
		listNhapKho.clear();
		fillToTableKho();
		listKho = khoDAO.selectAll();
		Date today = new Date();
		dateChooser
				.setSelectedDate(XDate.addDays(XDate.toDate(XDate.toString(today, "dd-MM-yyyy"), "dd-MM-yyyy"), 365));

	}

// load dữ liệu dnah mục
	private void loadToListDanhMuc() {
		listDanhMuc.addAll(danhMucDAO.selectAll());
	}

//Load dữ liệu sản phẩm
	private void loadToListSP() {
		listSP.clear();
		whitelistSP.clear();
		listSP.addAll(spDAO.selectAll());
		for (ModelSanPham spp : listSP) {
			if (spp.isTrangThai() == true && spp.isMatHang()) {
				whitelistSP.add(spp);
			}
		}
	}

//khởi tạo bảng nhấp kho
	private void initTableKho() {
		DefaultTableModel modelKho = new DefaultTableModel(new Object[][] {},
				new Object[] { "Sản phẩm", "Số lượng", "Giá", "Hạn SD", "Thao tac" });
		tblKho.setModel(modelKho);
		tblKho.setOpaque(true);
		tblKho.setBackground(new Color(255, 255, 255));
		tblKho.setFillsViewportHeight(true);
		tblKho.fixTable(jScrollPane1);
		tblKho.setForeground(Color.white);
		tblKho.setFont(new Font("SansSerif", 1, 12));
		tblKho.setColumnAction(4);
		tblKho.setActionWhiteList(false);
	}

//fill table kho
	private void fillToTableKho() {
		DefaultTableModel modelKho = new DefaultTableModel(new Object[][] {},
				new Object[] { "Sản phẩm", "Số lượng", "Giá", "Hạn SD", "Thao tac" });
		tblKho.setModel(modelKho);
		loadToListSP();
		for (ModelNhapKho entity : listNhapKho) {
			tblKho.addRow(entity.toRowTable(event));
		}
	}

//Khởi tạo bản danh mục chọn sản phẩm
	private void initTableSP() {
		DefaultTableModel modelSanPham = new DefaultTableModel(new Object[][] {},
				new Object[] { "Sản phẩm", "Danh mục", "Giá" });
		tblSanPham.setModel(modelSanPham);
		tblSanPham.setOpaque(true);
		tblSanPham.setBackground(new Color(255, 255, 255));
		tblSanPham.setFillsViewportHeight(true);
		tblSanPham.fixTable(jScrollPane2);
		tblSanPham.setForeground(Color.white);
		tblSanPham.setFont(new Font("SansSerif", 1, 12));
		tblSanPham.setColumnAction(10);
	}

//fill tabke sản phẩm
	private void fillToTableSanPham() {
		loadToListSP();
		DefaultTableModel modelSanPham = (DefaultTableModel) tblKho.getModel();
		modelSanPham.setRowCount(0);
		tblSanPham.setModel(modelSanPham);
		for (ModelSanPham entity : whitelistSP) {
			tblSanPham.addRow(entity.toRowNhapKho(listDanhMuc));
		}

	}

//    thêm vào list nhấpj kho
	private void addToListNhapKho(ModelNhapKho entity) {
		listNhapKho.add(entity);
		fillToTableKho();
	}

// update list nhập kho
	private void updateToListNhapKho(ModelNhapKho entity) {
		for (int i = 0; i < listNhapKho.size(); i++) {
			if (entity.getId() == listNhapKho.get(i).getId()) {
				listNhapKho.set(i, entity);
			}
		}
		fillToTableKho();
	}

//
	private ModelSanPham getFromRowTable(int row) {
		ModelSanPham entity = new ModelSanPham();
		Object o = tblSanPham.getValueAt(row, 0);
		if (o instanceof ModelProfile) {
			ModelProfile pf = (ModelProfile) o;
			entity = pf.getProduct();
		}
		return entity;
	}

//
	private ModelNhapKho getFormRowTableNhapKho(int row) {
		ModelNhapKho entity = new ModelNhapKho();
		ModelSanPham sp = new ModelSanPham();
		Object o = tblKho.getValueAt(row, 0);
		if (o instanceof ModelSanPham) {
			sp = (ModelSanPham) o;
		}
		for (int i = 0; i < listNhapKho.size(); i++) {
			if (listNhapKho.get(i).getSanPham().equals(sp)) {
				entity = listNhapKho.get(i);
			}
		}
		return entity;
	}
///////

	private String toYMD(String ngay) {
		return XDate.toString(XDate.toDate(ngay, "dd-MM-yyyy"), "dd-MM-yyy");
	}

//    kiểm tra số lượng
	public boolean checkSL(int select) {
		int sl = 0;
		try {
			sl = Integer.parseInt(txtSoLuong.getText().trim());
			if (FormValidator.isLessThan(sl, 1)) {
				sl = listNhapKho.get(select).getSoLuong();
				txtSoLuong.setText(sl + "");
				ROptionDialog.showAlert(frame, "Lỗi", "Số lượng nhập ít nhất là 1!", ROptionDialog.WARNING, Color.red,
						Color.black);
				return false;
			}
		} catch (Exception e) {
			sl = listNhapKho.get(select).getSoLuong();
			txtSoLuong.setText(sl + "");
			ROptionDialog.showAlert(frame, "Lỗi", "Số lượng phải là số!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		return true;
	}

	// kiểm tra giá
	public boolean checkGia(int select) {
		float gia = 0;
		try {
			gia = Float.parseFloat(txtGia.getText().trim());
			if (!FormValidator.isGreaterThan(gia, 0)) {
				gia = listNhapKho.get(select).getGia();
				txtGia.setText(gia + "");
				ROptionDialog.showAlert(frame, "Lỗi", "Giá phải lớn 0!", ROptionDialog.WARNING, Color.red, Color.black);
				return false;
			}
			float gt = listNhapKho.get(select).getGia() + (listNhapKho.get(select).getGia() / 2);
			if (FormValidator.isGreaterThan(gia, gt)) {
				gia = listNhapKho.get(select).getGia();
				txtGia.setText(gia + "");
				ROptionDialog.showAlert(frame, "Lỗi", "Giá nhập quá lớn so với giá hiện tại!", ROptionDialog.WARNING,
						Color.red, Color.black);
				return false;
			}
		} catch (Exception e) {
			gia = listNhapKho.get(select).getGia();
			txtGia.setText(gia + "");
			ROptionDialog.showAlert(frame, "Lỗi", "Giá phải là số!", ROptionDialog.WARNING, Color.red, Color.black);
			return false;
		}
		return true;
	}

	// kiem tra ngay
	public boolean checkNgay(int select) {
		Date today = new Date();
		today = XDate.toDate(XDate.toString(today, "dd-MM-yyyy"), "dd-MM-yyyy");
		Date minDay = XDate.addDays(today, 364);
		Date day = XDate.toDate(txtNgayHetHan.getText().trim(), "dd-MM-yyyy");
		if (!FormValidator.isDateAfter(day, minDay)) {
//        if (FormValidator.isDateAfter(today, day)) {
//        	ROptionDialog.showAlert(frame, "Lỗi", "Hết hạn sử dụng!", ROptionDialog.WARNING, Color.red,
//			Color.black);
//            txtNgayHetHan.setText(listNhapKho.get(select).getHanSD());
//            return false;
//        }

			ROptionDialog.showAlert(frame, "Lỗi", "Hạn sử dụng ít nhất!", ROptionDialog.WARNING, Color.red,
					Color.black);
			txtNgayHetHan.setText(listNhapKho.get(select).getHanSD());
			return false;
		}
		return true;
	}

	/**
	 * xử lý nhập kho
	 * 
	 * @param Modelkho
	 * @method insert
	 * @param ModelLichSu
	 * @method insert
	 * @param ModelChiTietLichSu
	 * @method insert
	 */
	public void NhapKho() {
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Xác nhận nhập kho?", ROptionDialog.WARNING, Color.yellow,
				Color.black)) {
			String NV = Auth.user.getMaNV();
			if (validator.isLessThan(listNhapKho.size(), 0)) {
				ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng chọn Sản Phẩm để nhập", ROptionDialog.WARNING,
						Color.red, Color.black);
			} else {
				// Lịch sử
				ModelLichSu mdLS = new ModelLichSu();
				// thêm lịch sử nhập
				mdLS.insert(listNhapKho, true, NV);
				listLS.clear();
				listLS.addAll(lsDAO.selectAll());
				int idLS = listLS.get(listLS.size() - 1).getId();
				// String toDay = XDate.toString(new Date(), "dd-MM-yyyy");
				for (int i = 0; i < listNhapKho.size(); i++) {
					String MSP = listNhapKho.get(i).getSanPham().getMaSP();
					String date = toYMD(listNhapKho.get(i).getHanSD());
					int soLuong = listNhapKho.get(i).getSoLuong();
					float Gia = listNhapKho.get(i).getGia();
					float tongTien = soLuong * Gia;
					// Kho
					ModelKho mdkho = new ModelKho();
					mdkho.insert(idLS, MSP, soLuong, date);
					// LSCT
					ModelChiTietLichSu mdctls = new ModelChiTietLichSu();
					mdctls.insert(idLS, MSP, soLuong, Gia);
				}
				ROptionDialog.showAlert(frame, "Thông báo", "Nhập Kho Thành Công!", ROptionDialog.NOTIFICATIONS_ACTIVE,
						new Color(0, 199, 135), Color.black);
				listKho.addAll(khoDAO.selectAll());
				listNhapKho.clear();
				fillToTableKho();
			}
		}
	}

	public List<ModelKho> getListKho() {
		return listKho;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		dateChooser = new com.raven.datechooser.DateChooser();
		rCard1 = new com.swing.custom.raven.RCard.RCard();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel3 = new javax.swing.JLabel();
		btnThem = new com.swing.custom.raven.RButton.RButtonBadges();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblSanPham = new com.wolvesres.swing.table.Table();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel2 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblKho = new com.wolvesres.swing.table.Table();
		rRoundPanel6 = new com.swing.custom.raven.RPanel.RRoundPanel();
		txtNgayHetHan = new com.swing.custom.raven.RTextField.RTextField();
		txtSoLuong = new com.swing.custom.raven.RTextField.RTextField();
		txtGia = new com.swing.custom.raven.RTextField.RTextField();
		txtCapNhat = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel5 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel6 = new javax.swing.JLabel();
		rRoundPanel5 = new com.swing.custom.raven.RPanel.RRoundPanel();
		rButton3 = new com.swing.custom.raven.RButton.RButton();
		rButton5 = new com.swing.custom.raven.RButton.RButton();
		btnNhapKho = new com.swing.custom.raven.RButton.RButton();

		dateChooser.setTextRefernce(txtNgayHetHan);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rRoundPanel2.setBackground(new java.awt.Color(32, 29, 37));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(255, 255, 255));
		jLabel3.setText("Chọn Sản Phẩm");
		rRoundPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 30));

		btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/addition.png"))); // NOI18N
		btnThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThemActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, -1, 40));

		tblSanPham
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblSanPham.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblSanPhamMousePressed(evt);
			}
		});
		jScrollPane2.setViewportView(tblSanPham);

		rRoundPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 380, 560));

		rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 380, 640));

		rRoundPanel3.setBackground(new java.awt.Color(32, 29, 37));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(225, 225, 225));
		jLabel2.setText("Thông tin Xuất Nhập Kho");
		rRoundPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

		tblKho.setModel(
				new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblKho.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblKhoMousePressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblKho);

		rRoundPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 630, 560));

		rRoundPanel6.setBackground(new java.awt.Color(255, 255, 255));
		rRoundPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		txtNgayHetHan.setLabelText("Ngày hết hạn ");
		rRoundPanel6.add(txtNgayHetHan, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 210, 50));

		txtSoLuong.setLabelText("Số lượng");
		rRoundPanel6.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 50));

		txtGia.setLabelText("Giá");
		rRoundPanel6.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 180, 50));

		rRoundPanel3.add(rRoundPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));

		txtCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/control-system.png"))); // NOI18N
		txtCapNhat.setText("Chỉnh");
		txtCapNhat.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				txtCapNhatActionPerformed(evt);
			}
		});
		rRoundPanel3.add(txtCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 80, 40));

		rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 630, 640));

		rRoundPanel4.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel5.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("NHẬP KHO");
		rRoundPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, -1, 40));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel4.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 10, 100, 80));

		jLabel6.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel6.setForeground(new java.awt.Color(255, 255, 255));
		jLabel6.setText("WolvesRes");
		rRoundPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 90, -1, -1));

		rRoundPanel1.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1020, 110));

		rRoundPanel5.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		rButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
		rButton3.setText("Làm Mới");
		rButton3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		rButton3.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rButton3ActionPerformed(evt);
			}
		});
		rRoundPanel5.add(rButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 141, 30));

		rButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		rButton5.setText("Hủy");
		rButton5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		rButton5.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rButton5ActionPerformed(evt);
			}
		});
		rRoundPanel5.add(rButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 149, 30));

		btnNhapKho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/import.png"))); // NOI18N
		btnNhapKho.setText("Nhập Kho");
		btnNhapKho.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnNhapKho.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnNhapKhoActionPerformed(evt);
			}
		});
		rRoundPanel5.add(btnNhapKho, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 147, 30));

		rRoundPanel1.add(rRoundPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 760, 1020, 50));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rRoundPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rRoundPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
		setLocationRelativeTo(null);
	}// </editor-fold>//GEN-END:initComponents

	private void rButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rButton5ActionPerformed
		dispose();
	}// GEN-LAST:event_rButton5ActionPerformed

	private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemActionPerformed
		EditSanPham editSP = new EditSanPham(frame, true);
		editSP.setVisible(true);
		if (!editSP.getIsDispose()) {
//            System.out.println(editSP.getSanPham().getTenSP());
//            System.out.println(editSP.getSanPham().getMaSP());
			spDAO.insert(editSP.getSanPham());
			// listSP.add(editSP.getSanPham());
			fillToTableSanPham();
			ROptionDialog.showAlert(frame, "Thông báo", "Thêm sản phẩm thành công!", ROptionDialog.NOTIFICATIONS_ACTIVE,
					new Color(0, 199, 135), Color.black);
		}
	}// GEN-LAST:event_btnThemActionPerformed

	private void tblSanPhamMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblSanPhamMousePressed
		ModelSanPham sp = getFromRowTable(tblSanPham.getSelectedRow());
		ModelNhapKho entity = new ModelNhapKho();

		entity.setSanPham(sp);
		boolean exists = false;
		for (int i = 0; i < listNhapKho.size(); i++) {
			if (listNhapKho.get(i).getSanPham().equals(entity.getSanPham())) {
				// listNhapKho.get(i).setSoLuong(listNhapKho.get(i).getSoLuong() + 1);
				exists = true;
			}
		}
		if (!exists) {
			entity.setId(id);
			entity.setGia(sp.getGiaBan());
			entity.setSoLuong(1);
			entity.setHanSD(XDate.toString(XDate.addDays(new Date(), 365), "dd-MM-yyyy"));
			addToListNhapKho(entity);
		}
		fillToTableKho();

		id++;
	}// GEN-LAST:event_tblSanPhamMousePressed

	private void tblKhoMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblKhoMousePressed
		if (tblKho.getSelectedRow() >= 0) {
			ModelNhapKho entity = new ModelNhapKho();
			entity = getFormRowTableNhapKho(tblKho.getSelectedRow());
			txtGia.setText(String.valueOf(entity.getGia()));
			txtSoLuong.setText(String.valueOf(entity.getSoLuong()));
			dateChooser.setSelectedDate(XDate.toDate(entity.getHanSD(), "dd-MM-yyyy"));
		}

	}// GEN-LAST:event_tblKhoMousePressed

	private void txtCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtCapNhatActionPerformed
		if (tblKho.getSelectedRow() >= 0) {
			int i = tblKho.getSelectedRow();
			if (checkSL(i) && checkGia(i) && checkNgay(i)) {
				ModelNhapKho entity = new ModelNhapKho();
				entity = getFormRowTableNhapKho(tblKho.getSelectedRow());
				listNhapKho.set(i, entity);
				listNhapKho.get(i).setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
				listNhapKho.get(i).setGia(Float.parseFloat(txtGia.getText().trim()));
				listNhapKho.get(i).setHanSD(txtNgayHetHan.getText().trim());
				fillToTableKho();
				//
				txtSoLuong.setText("");
				txtGia.setText("");
				txtNgayHetHan.setText("");
			}

		}
	}// GEN-LAST:event_txtCapNhatActionPerformed

	private void btnNhapKhoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNhapKhoActionPerformed
		NhapKho();
	}// GEN-LAST:event_btnNhapKhoActionPerformed

	private void rButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rButton3ActionPerformed
		listNhapKho.clear();
		fillToTableKho();
	}// GEN-LAST:event_rButton3ActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		/* Set the Nimbus look and feel */
		// <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
		// (optional) ">
		/*
		 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
		 * look and feel. For details see
		 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
		 */
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(JDialogNhapKho.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(JDialogNhapKho.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(JDialogNhapKho.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(JDialogNhapKho.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				JDialogNhapKho dialog = new JDialogNhapKho(new javax.swing.JFrame(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					@Override
					public void windowClosing(java.awt.event.WindowEvent e) {
						System.exit(0);
					}
				});
				dialog.setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.swing.custom.raven.RButton.RButton btnNhapKho;
	private com.swing.custom.raven.RButton.RButtonBadges btnThem;
	private com.raven.datechooser.DateChooser dateChooser;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private com.swing.custom.raven.RButton.RButton rButton3;
	private com.swing.custom.raven.RButton.RButton rButton5;
	private com.swing.custom.raven.RCard.RCard rCard1;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel5;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel6;
	private com.wolvesres.swing.table.Table tblKho;
	private com.wolvesres.swing.table.Table tblSanPham;
	private com.swing.custom.raven.RButton.RButton txtCapNhat;
	private com.swing.custom.raven.RTextField.RTextField txtGia;
	private com.swing.custom.raven.RTextField.RTextField txtNgayHetHan;
	private com.swing.custom.raven.RTextField.RTextField txtSoLuong;
	// End of variables declaration//GEN-END:variables
}
