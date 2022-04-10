package com.wolvesres.form;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RIcon.GoogleMaterialDesignIcons;
import com.swing.custom.raven.RIcon.IconFontSwing;
import com.wolvesres.dao.VoucherDAO;
import com.wolvesres.form.voucher.BlackListVoucher;
import com.wolvesres.form.voucher.EditVoucher;
import com.wolvesres.form.voucher.ViewVoucher;
import com.wolvesres.helper.Auth;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelVouCher;
import com.wolvesres.swing.table.EventAction;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Cac class lien quan VoucherDAO, BlackListVoucher, EditVoucher, ModelVoucher,
 * ScanVoucher
 * 
 * @author Brian
 *
 */
public class FormVoucher extends javax.swing.JPanel {
	/**
	 * Generate global variable
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private List<ModelVouCher> listVoucher = new ArrayList<ModelVouCher>();
	private List<ModelVouCher> whiteList = new ArrayList<ModelVouCher>();
	private VoucherDAO dao = new VoucherDAO();
	private DefaultTableModel model;
	private int index = -1;
	/**
	 * Event action on table row
	 */
	private EventAction<ModelVouCher> eventAction = new EventAction<ModelVouCher>() {
		@Override
		public void update(ModelVouCher entity) {
			EditVoucher editForm = new EditVoucher(frame, true);
			editForm.isInsert(false);
			editForm.setVoucher(entity);
			editForm.setForm();
			editForm.setVisible(true);
			if (!editForm.isDidpose()) {
				updateVoucherToList(editForm.getVoucher(), entity.getMaVoucher());
				fillToTable();
			}
		}

		@Override
		public void delete(ModelVouCher entity) {
			if (dao.checkForeignKey(entity) == null) {
				if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn xóa voucher không?",
						ROptionDialog.WARNING, Color.yellow, Color.black)) {
					deleteVoucher(entity);
					index = 0;
					fillToTable();
					showDetail(getVoucherFromRowTable(index));
				}
			} else {
				ROptionDialog.showAlert(frame, "Thông báo", "Voucher đã được sử dụng, không thể xóa!",
						ROptionDialog.WARNING, Color.red, Color.black);
			}
		}
	};

	/**
	 * Constructor
	 * 
	 * @param frame
	 */
	public FormVoucher(JFrame frame) {
		initComponents();
		setOpaque(false);
		init();
		this.frame = frame;
	}

	/**
	 * Init method
	 */
	public void init() {
		initTable();
		loadToList();
		fillToTable();
		if (!Auth.isBoss() && !Auth.isManager()) {
			btnDanhSachDen.setVisible(false);
			btnVoHieuHoa.setVisible(false);
			bntThem.setVisible(false);
		}
		showDetail(listVoucher.get(0));
		Icon iconThem = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.NOTE_ADD, 32, new Color(0, 199, 135));
		Icon iconXemDSDen = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.FEATURED_PLAY_LIST, 32,
				new Color(0, 199, 135));
		Icon iconAddDSDen = IconFontSwing.buildIcon(GoogleMaterialDesignIcons.REMOVE_CIRCLE, 32,
				new Color(255, 51, 51));
		bntThem.setIcon(iconThem);
		btnDanhSachDen.setIcon(iconXemDSDen);
		btnVoHieuHoa.setIcon(iconAddDSDen);
	}

	/**
	 * Load voucher from database
	 */
	private void loadToList() {
		listVoucher.addAll(dao.selectAll());
	}

	/**
	 * Load voucher to whiteList (trangthai = true)
	 */
	private void loadToWhiteList() {
		whiteList.clear();
		for (ModelVouCher vc : listVoucher) {
			if (vc.isTrangThai()) {
				whiteList.add(vc);
			}
		}
	}

	/**
	 * Return listVoucher
	 * 
	 * @return
	 */
	public List<ModelVouCher> getList() {
		return this.listVoucher;
	}

	/**
	 * Fill to table
	 */
	private void fillToTable() {
		loadToWhiteList();
		model.setRowCount(0);
		for (ModelVouCher vc : whiteList) {
			if (!Auth.isBoss() && !Auth.isManager()) {
				tblVoucher.addRow(vc.toRowTableWINV());
			} else {
				tblVoucher.addRow(vc.toRowTable(eventAction));
			}
		}
	}

	/**
	 * Init table
	 */
	private void initTable() {
		tblVoucher.setOpaque(true);
		tblVoucher.setBackground(new Color(255, 255, 255));
		tblVoucher.setFillsViewportHeight(true);
		tblVoucher.fixTable(jScrollPane2);
		if (!Auth.isBoss() && !Auth.isManager()) {
			model = new DefaultTableModel(new Object[][] {}, new Object[] { "Mã Voucher", "Giảm giá", "Số lượng" });
		} else {
			model = new DefaultTableModel(new Object[][] {},
					new Object[] { "Mã Voucher", "Giảm giá", "Số lượng", "Thao tác" });
		}
		tblVoucher.setModel(model);
		tblVoucher.setColumnAction(3);
	}

	/**
	 * Add Voucher to listVoucher
	 * 
	 * @param vc
	 */
	private void addVoucherToList(ModelVouCher vc) {
		listVoucher.add(vc);

	}

	/**
	 * Update Voucher to listvoucher
	 * 
	 * @param vc
	 */
	private void updateVoucherToList(ModelVouCher vc, String mavc) {
		for (int i = 0; i < listVoucher.size(); i++) {
			if (mavc.equals(listVoucher.get(i).getMaVoucher())) {
				listVoucher.set(i, vc);
				break;
			}
		}
	}

	/**
	 * GetVoucher from row on table
	 * 
	 * @param row
	 * @return ModelVouCher
	 */
	private ModelVouCher getVoucherFromRowTable(int row) {
		ModelVouCher emp = new ModelVouCher();
		if (row >= 0) {
			String maVC = String.valueOf(tblVoucher.getValueAt(row, 0));
			for (int i = 0; i < whiteList.size(); i++) {
				if (maVC.equals(whiteList.get(i).getMaVoucher())) {
					emp = whiteList.get(i);
				}
			}
		}
		return emp;
	}

	/**
	 * Show details Voucher
	 * 
	 * @param vc
	 */
	private void showDetail(ModelVouCher vc) {
		if (vc != null) {
			if (vc.getPathQR() != null) {
				ImageIcon pic = new ImageIcon(XImage.readImageQRCode(vc.getPathQR()).getImage().getScaledInstance(250,
						250, Image.SCALE_SMOOTH));
				lblQR.setIcon(pic);
				lblQR.setToolTipText(vc.getPathQR());
			} else {
				lblQR.setIcon(null);
			}
			lblMaVoucher.setText(vc.getMaVoucher());
			lblGiamGia.setText(vc.getGiamGia() + "%");
			lblSoLuong.setText(String.valueOf(vc.getSoLuong()));
			lblNgayBatDau.setText(vc.getNgayBatDau());
			lblNgayKetThuc.setText(vc.getNgayKetThuc());
			lblTrangThai.setText(vc.isTrangThai() ? "Đang hoạt động" : "Ngưng hoạt động!");

		}
	}

	/**
	 * Delete voucher from database
	 * 
	 * @param entity
	 */
	private void deleteVoucher(ModelVouCher entity) {
		entity.remove();
		fillToTable();
	}

	/**
	 * Delete voucher from listVoucher
	 * 
	 * @param entity
	 */
	public void deleteVoucherFromList(ModelVouCher entity) {
		listVoucher.remove(entity);
	}

	/**
	 * Find voucher
	 * 
	 * @param keyword
	 */
	public List<ModelVouCher> timKiemVoucher(String keyword) {
		List<ModelVouCher> listFound = new ArrayList<>();
		if (keyword.trim().length() != 0) {
			listFound = dao.findVoucher(keyword);
		} else {
			listFound = dao.selectAll();
		}
		return listFound;
	}

	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		lblQR = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel4 = new javax.swing.JLabel();
		jLabel5 = new javax.swing.JLabel();
		jLabel8 = new javax.swing.JLabel();
		jLabel9 = new javax.swing.JLabel();
		jLabel11 = new javax.swing.JLabel();
		lblMaVoucher = new javax.swing.JLabel();
		lblNgayBatDau = new javax.swing.JLabel();
		lblNgayKetThuc = new javax.swing.JLabel();
		lblGiamGia = new javax.swing.JLabel();
		lblSoLuong = new javax.swing.JLabel();
		lblTrangThai = new javax.swing.JLabel();
		txtTimKiem = new com.swing.custom.raven.RTextField.RTextField();
		btnVoHieuHoa = new com.swing.custom.raven.RButton.RButton();
		pnl = new com.swing.custom.raven.RPanel.RRoundPanel();
		jScrollPane2 = new javax.swing.JScrollPane();
		tblVoucher = new com.wolvesres.swing.table.Table();
		btnDanhSachDen = new com.swing.custom.raven.RButton.RButton();
		jLabel10 = new javax.swing.JLabel();
		bntThem = new com.swing.custom.raven.RButton.RButton();
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

		lblQR.setBackground(new java.awt.Color(255, 255, 255));
		lblQR.setOpaque(true);
		lblQR.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				lblQRMousePressed(evt);
			}
		});
		rRoundPanel2.add(lblQR, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 250, 250));

		jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("Ngày kết thúc");
		rRoundPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 430, -1, -1));

		jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel4.setForeground(new java.awt.Color(255, 255, 255));
		jLabel4.setText("Số lượng");
		rRoundPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 490, -1, -1));

		jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("Trạng thái");
		rRoundPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 520, -1, -1));

		jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel8.setForeground(new java.awt.Color(255, 255, 255));
		jLabel8.setText("Giảm giá");
		rRoundPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 460, -1, -1));

		jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel9.setForeground(new java.awt.Color(255, 255, 255));
		jLabel9.setText("Ngày bắt đầu");
		rRoundPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, -1, -1));

		jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel11.setForeground(new java.awt.Color(255, 255, 255));
		jLabel11.setText("Mã Voucher");
		rRoundPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, -1, -1));

		lblMaVoucher.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblMaVoucher.setForeground(new java.awt.Color(255, 255, 255));
		lblMaVoucher.setText("a");
		rRoundPanel2.add(lblMaVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 150, -1));

		lblNgayBatDau.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblNgayBatDau.setForeground(new java.awt.Color(255, 255, 255));
		lblNgayBatDau.setText("a");
		rRoundPanel2.add(lblNgayBatDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 400, 140, -1));

		lblNgayKetThuc.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblNgayKetThuc.setForeground(new java.awt.Color(255, 255, 255));
		lblNgayKetThuc.setText("a");
		rRoundPanel2.add(lblNgayKetThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 430, 120, -1));

		lblGiamGia.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblGiamGia.setForeground(new java.awt.Color(255, 255, 255));
		lblGiamGia.setText("a");
		rRoundPanel2.add(lblGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 460, 140, -1));

		lblSoLuong.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblSoLuong.setForeground(new java.awt.Color(255, 255, 255));
		lblSoLuong.setText("a");
		rRoundPanel2.add(lblSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 490, 220, -1));

		lblTrangThai.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		lblTrangThai.setForeground(new java.awt.Color(255, 255, 255));
		lblTrangThai.setText("a");
		rRoundPanel2.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 520, 150, -1));

		txtTimKiem.setBackground(new java.awt.Color(6, 7, 13));
		txtTimKiem.setForeground(new java.awt.Color(255, 255, 255));
		txtTimKiem.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtTimKiem.setLabelText("Tìm kiếm Voucher");
		txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtTimKiemKeyReleased(evt);
			}
		});
		rRoundPanel2.add(txtTimKiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 50));

		btnVoHieuHoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/disabled.png"))); // NOI18N
		btnVoHieuHoa.setText("Vô hiệu hóa");
		btnVoHieuHoa.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnVoHieuHoa.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnVoHieuHoaActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnVoHieuHoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 580, 170, 40));

		rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 110, 440, 620));

		pnl.setBackground(new java.awt.Color(6, 7, 13));
		pnl.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jScrollPane2.setBorder(null);

		tblVoucher.setAutoCreateRowSorter(true);
		tblVoucher
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblVoucher.setOpaque(false);
		tblVoucher.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblVoucherMousePressed(evt);
			}
		});
		jScrollPane2.setViewportView(tblVoucher);

		pnl.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 720, 520));

		btnDanhSachDen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/list.png"))); // NOI18N
		btnDanhSachDen.setText("Xem danh sách ngưng hoạt động");
		btnDanhSachDen.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnDanhSachDen.setPreferredSize(new java.awt.Dimension(175, 30));
		btnDanhSachDen.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDanhSachDenActionPerformed(evt);
			}
		});
		pnl.add(btnDanhSachDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 580, 290, 40));

		jLabel10.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		jLabel10.setForeground(new java.awt.Color(255, 255, 255));
		jLabel10.setText("Danh sách Voucher");
		pnl.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 190, 30));

		bntThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/plus.png"))); // NOI18N
		bntThem.setText("Thêm Voucher");
		bntThem.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		bntThem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
		bntThem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				bntThemActionPerformed(evt);
			}
		});
		pnl.add(bntThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 160, 40));

		rRoundPanel1.add(pnl, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 720, 620));

		rRoundPanel3.setBackground(new java.awt.Color(0, 199, 135));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel6.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel6.setForeground(new java.awt.Color(255, 255, 255));
		jLabel6.setText("VOUCHER");
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

	private void btnDanhSachDenActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDanhSachDenActionPerformed
		BlackListVoucher bls = new BlackListVoucher(frame, true);
		bls.setVisible(true);
		if (bls.isChangeData()) {
			for (int i = 0; i < listVoucher.size(); i++) {
				for (int j = 0; j < bls.getListReturn().size(); j++) {
					if (listVoucher.get(i).getMaVoucher().equals(bls.getListReturn().get(j).getMaVoucher())) {
						updateVoucherToList(bls.getListReturn().get(j), bls.getListReturn().get(j).getMaVoucher());
						break;
					}
				}
			}
			fillToTable();
		}

	}// GEN-LAST:event_btnDanhSachDenActionPerformed

	private void bntThemActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_bntThemActionPerformed
		EditVoucher editForm = new EditVoucher(frame, true);
		editForm.setVisible(true);
		if (!editForm.isDidpose()) {
			addVoucherToList(editForm.getVoucher());
			fillToTable();
			for (int i = 0; i < model.getRowCount(); i++) {
				if (String.valueOf(tblVoucher.getValueAt(i, 0)).equals(editForm.getVoucher().getMaVoucher())) {
					index = i;
					break;
				}
			}
			tblVoucher.setRowSelectionInterval(index, index);
			showDetail(editForm.getVoucher());
		}
	}// GEN-LAST:event_bntThemActionPerformed

	private void tblVoucherMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblVoucherMousePressed
		index = tblVoucher.getSelectedRow();
		showDetail(getVoucherFromRowTable(index));

	}// GEN-LAST:event_tblVoucherMousePressed

	private void lblQRMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblQRMousePressed
		ViewVoucher view = new ViewVoucher(frame, true);
		view.setPath(lblQR.getToolTipText());
		view.setVisible(true);
	}// GEN-LAST:event_lblQRMousePressed

	private void txtTimKiemKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtTimKiemKeyReleased
		listVoucher = timKiemVoucher(txtTimKiem.getText().trim());
		fillToTable();
	}// GEN-LAST:event_txtTimKiemKeyReleased

	private void btnVoHieuHoaActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnVoHieuHoaActionPerformed
		if (index >= 0) {
			if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn vô hiệu hóa Voucher không?",
					ROptionDialog.WARNING, Color.yellow, Color.black)) {
				ModelVouCher vc = getVoucherFromRowTable(index);
				vc.addToBlackList();
				updateVoucherToList(vc, vc.getMaVoucher());
				fillToTable();
			}
		}
	}// GEN-LAST:event_btnVoHieuHoaActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.swing.custom.raven.RButton.RButton bntThem;
	private com.swing.custom.raven.RButton.RButton btnDanhSachDen;
	private com.swing.custom.raven.RButton.RButton btnVoHieuHoa;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JLabel lblGiamGia;
	private javax.swing.JLabel lblMaVoucher;
	private javax.swing.JLabel lblNgayBatDau;
	private javax.swing.JLabel lblNgayKetThuc;
	private javax.swing.JLabel lblQR;
	private javax.swing.JLabel lblSoLuong;
	private javax.swing.JLabel lblTrangThai;
	private com.swing.custom.raven.RPanel.RRoundPanel pnl;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.wolvesres.swing.table.Table tblVoucher;
	private com.swing.custom.raven.RTextField.RTextField txtTimKiem;
	// End of variables declaration//GEN-END:variables
}
