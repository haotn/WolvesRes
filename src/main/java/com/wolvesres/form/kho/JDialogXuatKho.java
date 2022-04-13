package com.wolvesres.form.kho;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.ChiTietLichSuDAO;
import com.wolvesres.dao.KhoDAO;
import com.wolvesres.dao.LichSuDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.form.FormKho;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.model.ModelChiTietLichSu;
import com.wolvesres.model.ModelKho;
import com.wolvesres.model.ModelLichSu;
import com.wolvesres.model.ModelNhapKho;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.swing.table.EventActionBlackList;
import com.wolvesres.swing.table.ModelProfile;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Xuất Kho, lưu lịch sử, lưu lịch sử chi tiết Lien Quan:ModelKho(update),
 * ModelLichSu(insert), ModelChiTietLichSu(insert)
 */
public class JDialogXuatKho extends javax.swing.JDialog {

	private JFrame frame;
	private int id = 1;
	private List<ModelKho> listKho = new ArrayList<>();
	private List<ModelKho> listKhoadd = new ArrayList<>();
	private List<ModelNhapKho> listNhapKho = new ArrayList<>();
	private List<ModelSanPham> listSP = new ArrayList();
	private List<ModelLichSu> listLS = new ArrayList();
	private List<ModelChiTietLichSu> listCT = new ArrayList();
	private FormKho formParent = new FormKho(frame);
	private KhoDAO khoDAO = new KhoDAO();
	private SanPhamDAO spDAO = new SanPhamDAO();
	private LichSuDAO lsDAO = new LichSuDAO();
	private ChiTietLichSuDAO ctDAO = new ChiTietLichSuDAO();
	private FormValidator validator = new FormValidator();

	public JDialogXuatKho(java.awt.Frame parent, boolean modal) {
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

	// Xóa sản phẩm khỏi list xuất kho
	public void deleteToListKho(ModelNhapKho entity) {
		listNhapKho.remove(entity);
		fillToTableXuat();
	}

	//
	private void init() {
		initTableKho();
		initTableXuat();
		loadList();
		fillToTableKho();

		// Date today = new Date();
		// dateChooser.setSelectedDate(XDate.addDays(XDate.toDate(XDate.toString(today,
		// "dd-MM-yyyy"), "dd-MM-yyyy"), 365));
	}

//load dữ liệu vào list kho
	private void loadList() {
		listKho.clear();
		for (ModelKho modelKho : khoDAO.selectAll()) {
			if (modelKho.getSoLuong() > 0) {
				listKho.add(modelKho);
			}

		}

	}

//khởi tạo table xuất kho
	private void initTableXuat() {
		DefaultTableModel modelKho = new DefaultTableModel(new Object[][] {},
				new Object[] { "Sản phẩm", "Số lượng", "Giá", "Hạn SD", "Thao tac" });
		tblXuat.setModel(modelKho);
		tblXuat.setOpaque(true);
		tblXuat.setBackground(new Color(255, 255, 255));
		tblXuat.setFillsViewportHeight(true);
		tblXuat.fixTable(jScrollPane1);
		tblXuat.setForeground(Color.white);
		tblXuat.setFont(new Font("SansSerif", 1, 12));
		tblXuat.setColumnAction(4);
		tblXuat.setActionWhiteList(false);
	}

//fill table sản phẩm xuất kho
	private void fillToTableXuat() {
		DefaultTableModel modelKho = new DefaultTableModel(new Object[][] {},
				new Object[] { "Sản phẩm", "Số lượng", "Giá", "Hạn SD", "Thao tac" });
		tblXuat.setModel(modelKho);
		for (ModelNhapKho entity : listNhapKho) {
			tblXuat.addRow(entity.toRowTableXK(event));
		}
	}

////////////////////////
//khỏi tạo bảng kho
	private void initTableKho() {
		DefaultTableModel modelSanPham = new DefaultTableModel(new Object[][] {},
				new Object[] { "Sản phẩm", "SL", "Giá", "HSD" });
		tblKho.setModel(modelSanPham);
		tblKho.setOpaque(true);
		tblKho.setBackground(new Color(255, 255, 255));
		tblKho.setFillsViewportHeight(true);
		tblKho.fixTable(jScrollPane2);
		tblKho.setForeground(Color.white);
		tblKho.setFont(new Font("SansSerif", 1, 12));
		tblKho.setColumnAction(10);
	}

//fill table bảng kho
	private void fillToTableKho() {
		DefaultTableModel modelSanPham = (DefaultTableModel) tblKho.getModel();
		modelSanPham.setRowCount(0);
		for (ModelKho entity : listKho) {
			tblKho.addRow(entity.toRowTableXK());
		}
	}

//thêm sản phẩm từ kho vào list nhập kho
	public void addToListNhapKho(ModelNhapKho entity) {
		listNhapKho.add(entity);
		fillToTableXuat();
	}

//sử sản phẩm list nhập kho 
	private void updateToListNhapKho(ModelNhapKho entity) {
		for (int i = 0; i < listNhapKho.size(); i++) {
			if (entity.getId() == listNhapKho.get(i).getId()) {
				listNhapKho.set(i, entity);
			}
		}
		fillToTableXuat();
	}

//lấy sản phẩm từ hàng chọn của kho
	private ModelNhapKho getFormRowTableNhapKho(int row) {
		ModelNhapKho entity = new ModelNhapKho();
		entity = listNhapKho.get(row);
		return entity;
	}

//    định dạng ngày
	private String toYMD(String ngay) {
		return XDate.toString(XDate.toDate(ngay, "dd-MM-yyyy"), "dd-MM-yyy");
	}

//kiểm tra số lượng
	public boolean checkSL(int select) {
		int sl = 0;
		int slkho = 0;
		try {
			sl = Integer.parseInt(txtSoLuong.getText().trim());
			if (validator.isLessThan(sl, 1)) {
				sl = listNhapKho.get(select).getSoLuong();
				txtSoLuong.setText(sl + "");
				ROptionDialog.showAlert(frame, "Lỗi", "Số lượng xuất kho tối thiểu là 1!", ROptionDialog.WARNING,
						Color.red, Color.black);
				return false;
			}
			slkho = listNhapKho.get(select).getSoLuong();
			if (validator.isGreaterThan(sl, slkho)) {
				ROptionDialog.showAlert(frame, "Lỗi",
						"Số lượng xuất vượt quá số lượng đang có Trong kho :" + (sl - slkho), ROptionDialog.WARNING,
						Color.red, Color.black);
				sl = listNhapKho.get(select).getSoLuong();
				txtSoLuong.setText(sl + "");
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
			if (validator.isLessThan(gia, 1)) {
				gia = listNhapKho.get(select).getGia();
				txtGia.setText(gia + "");
				ROptionDialog.showAlert(frame, "Lỗi", "Giá phải lớn hơn không!", ROptionDialog.WARNING, Color.red,
						Color.black);
				return false;
			}
			float gt = listNhapKho.get(select).getGia() / 2;
			if (validator.isLessThan(gia, gt)) {
				gia = listNhapKho.get(select).getGia();
				txtGia.setText(gia + "");
				ROptionDialog.showAlert(frame, "Lỗi", "Giá nhập quá nhỏ so với giá nhập ban đầu!",
						ROptionDialog.WARNING, Color.red, Color.black);
				return false;
			}
		} catch (Exception e) {
			gia = listNhapKho.get(select).getGia();
			txtGia.setText(gia + "");
			ROptionDialog.showAlert(frame, "Lỗi", "Giá phải là số", ROptionDialog.WARNING, Color.red, Color.black);
			return false;
		}
		return true;
	}

	/**
	 * Xử lý xuất kho
	 * 
	 * @param Modelkho
	 * @method update
	 * @param ModelLichSu
	 * @method insert
	 * @param ModelChiTietLichSu
	 * @method insert
	 */
	public void XuatKho() {
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Xác nhận xuất kho?", ROptionDialog.WARNING, Color.yellow,
				Color.black)) {
			String NV = Auth.user.getMaNV();
			if (validator.isLessThan(listNhapKho.size(), 0)) {
				ROptionDialog.showAlert(frame, "Thông báo", "Vui lòng chọn Sản Phẩm để nhập",
						ROptionDialog.PRIORITY_HIGHT, Color.red, Color.black);
			} else {
				// Lịch sử
				ModelLichSu mdLS = new ModelLichSu();
				mdLS.insert(listNhapKho, false, NV);
				listLS.clear();
				listLS.addAll(lsDAO.selectAll());
				int idLS = listLS.get(listLS.size() - 1).getId();
				// String toDay = XDate.toString(new Date(), "dd-MM-yyyy");
				for (int i = 0; i < listNhapKho.size(); i++) {
					String MSP = listNhapKho.get(i).getMaSP();
					String date = toYMD(listNhapKho.get(i).getHanSD());
					int soLuong = listNhapKho.get(i).getSoLuong();
					float Gia = listNhapKho.get(i).getGia();
					float tongTien = soLuong * Gia;
					int idkho = listNhapKho.get(i).getIDK();
					int soLuongXuat =0;
					// Kho
					ModelKho mdkho = new ModelKho();
					for (int j = 0; j < listKho.size(); j++) {
						if (idkho == listKho.get(j).getId()) {
							mdkho = listKho.get(j);
							if (soLuong == listKho.get(j).getSoLuong()) {
								soLuongXuat = 0;
								mdkho.setSoLuong(soLuongXuat);
								mdkho.setTrangThai(false);
								break;
							}
							if (soLuong < listKho.get(j).getSoLuong()) {
								soLuongXuat = listKho.get(j).getSoLuong() - soLuong;
								mdkho.setSoLuong(soLuongXuat);
								break;
							}
						}
					}
					// update kho
					mdkho.update(idkho);
					//LS
					// LSCT
					ModelChiTietLichSu mdctls = new ModelChiTietLichSu();
					mdctls.insert(idLS, MSP,soLuong, Gia);
				}
//				listKho.clear();
//				listKho.addAll(khoDAO.selectAll());
				listNhapKho.clear();
				loadList();
				fillToTableXuat();
				fillToTableKho();
			}
		}
	}

	public List<ModelKho> getListKho() {
		return listKho;
	}

//    public ModelSanPham getSPbyMaSP(int row) {
//        ModelSanPham sp = new ModelSanPham();
//        Object o = tblKho.getValueAt(row, 0);
//        if (o instanceof ModelSanPham) {
//            sp = (ModelSanPham) o;
//        }
//        return sp;
//    }
	private ModelKho getFromRowTable(int row) {
		ModelKho entity = new ModelKho();
		Object o = tblKho.getValueAt(row, 0);
		if (o instanceof ModelKho) {
			entity = (ModelKho) o;
		}
		return entity;
	}

	public com.swing.custom.raven.RTextField.RTextField getTxtGia() {
		return txtGia;
	}

	public void setTxtGia(com.swing.custom.raven.RTextField.RTextField txtGia) {
		this.txtGia = txtGia;
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
		jScrollPane2 = new javax.swing.JScrollPane();
		tblKho = new com.wolvesres.swing.table.Table();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel2 = new javax.swing.JLabel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblXuat = new com.wolvesres.swing.table.Table();
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
		jScrollPane2.setViewportView(tblKho);

		rRoundPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 380, 560));

		rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 120, 380, 640));

		rRoundPanel3.setBackground(new java.awt.Color(32, 29, 37));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel2.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(225, 225, 225));
		jLabel2.setText("Thông tin Xuất Nhập Kho");
		rRoundPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

		tblXuat.setModel(
				new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblXuat.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblXuatMousePressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblXuat);

		rRoundPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 630, 560));

		rRoundPanel6.setBackground(new java.awt.Color(255, 255, 255));
		rRoundPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		txtNgayHetHan.setEnabled(false);
		txtNgayHetHan.setLabelText("Hạn sử dụng");
		rRoundPanel6.add(txtNgayHetHan, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 210, 50));

		txtSoLuong.setLabelText("Số lượng");
		rRoundPanel6.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 140, 50));

		txtGia.setLabelText("Giá");
		rRoundPanel6.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 180, 50));

		rRoundPanel3.add(rRoundPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, -1, -1));

		txtCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/edit.png"))); // NOI18N
		txtCapNhat.setText("Chỉnh");
		txtCapNhat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
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
		jLabel5.setText("XUẤT KHO");
		rRoundPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 30, -1, 50));

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

		btnNhapKho.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/export.png"))); // NOI18N
		btnNhapKho.setText("Xuất Kho");
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

	private void tblKhoMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblKhoMousePressed
		int select = tblKho.getSelectedRow();
		if (select >= 0 && listNhapKho.size() >= 0) {
			ModelNhapKho xk = new ModelNhapKho();
			ModelKho kho = getFromRowTable(select);
			boolean exists = false;
			for (int i = 0; i < listNhapKho.size(); i++) {
				if (listNhapKho.get(i).getMaSP().equals(kho.getMaSP())) {
					exists = true;
				}
			}
			if (!exists) {
				xk.setMaSP(listKho.get(select).getMaSP());
				xk.setId(id);
				xk.setIDK(listKho.get(select).getId());
				xk.setGia(listKho.get(select).getGiaSanPham(listKho.get(select).getIdls(),
						listKho.get(select).getMaSP()));
				xk.setHanSD(listKho.get(select).getHanSuDung());
				xk.setSoLuong(listKho.get(select).getSoLuong());
				addToListNhapKho(xk);
			}
		}
	}

	private void tblXuatMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblXuatMousePressed
		if (tblXuat.getSelectedRow() >= 0) {
			ModelNhapKho entity = new ModelNhapKho();
			entity = getFormRowTableNhapKho(tblXuat.getSelectedRow());
			txtGia.setText(String.valueOf(entity.getGia()));
			txtSoLuong.setText(String.valueOf(entity.getSoLuong()));
			dateChooser.setSelectedDate(XDate.toDate(entity.getHanSD(), "dd-MM-yyyy"));
		}

	}// GEN-LAST:event_tblXuatMousePressed

	private void txtCapNhatActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtCapNhatActionPerformed
		if (tblXuat.getSelectedRow() >= 0) {
			int i = tblXuat.getSelectedRow();
			if (checkSL(i) && checkGia(i)) {
				ModelNhapKho entity = new ModelNhapKho();
				entity = getFormRowTableNhapKho(tblXuat.getSelectedRow());
				listNhapKho.set(i, entity);
				listNhapKho.get(i).setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
				listNhapKho.get(i).setGia(Float.parseFloat(txtGia.getText().trim()));
				listNhapKho.get(i).setHanSD(txtNgayHetHan.getText().trim());
				fillToTableXuat();
				//
				txtSoLuong.setText("");
				txtGia.setText("");
				txtNgayHetHan.setText("");
			}

		}
	}// GEN-LAST:event_txtCapNhatActionPerformed

	private void btnNhapKhoActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnNhapKhoActionPerformed
		XuatKho();
	}// GEN-LAST:event_btnNhapKhoActionPerformed

	private void rButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rButton3ActionPerformed
		listNhapKho.clear();
		fillToTableXuat();
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
			java.util.logging.Logger.getLogger(JDialogXuatKho.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(JDialogXuatKho.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(JDialogXuatKho.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(JDialogXuatKho.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				JDialogXuatKho dialog = new JDialogXuatKho(new javax.swing.JFrame(), true);
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
	private com.wolvesres.swing.table.Table tblXuat;
	private com.swing.custom.raven.RButton.RButton txtCapNhat;
	private com.swing.custom.raven.RTextField.RTextField txtGia;
	private com.swing.custom.raven.RTextField.RTextField txtNgayHetHan;
	private com.swing.custom.raven.RTextField.RTextField txtSoLuong;
	// End of variables declaration//GEN-END:variables
}
