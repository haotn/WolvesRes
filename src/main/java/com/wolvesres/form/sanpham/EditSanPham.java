package com.wolvesres.form.sanpham;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.DonViTinhDAO;
import com.wolvesres.form.FormSanPham;
import com.wolvesres.form.danhmuc.EditDanhMuc;
import com.wolvesres.form.donvitinh.EditDonviTinh;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelDonViTinh;
import com.wolvesres.model.ModelSanPham;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
/**
 * comment các hàm
 * @author huynh
 *
 */
public class EditSanPham extends javax.swing.JDialog {

	public EditSanPham(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setLocationRelativeTo(null);
		this.frame = (JFrame) parent;
		init();

	}

	private ModelSanPham sanPham = null;
	private boolean isInsert;
	private boolean dispose = true;
	DefaultComboBoxModel<ModelDanhMuc> modelCboDanhMuc;
	DefaultComboBoxModel<ModelDonViTinh> modelCboDVT;
	private DanhMucDAO dmDAO = new DanhMucDAO();
	private List<ModelDonViTinh> listDVT = new ArrayList<>();
	private DonViTinhDAO dvtdao = new DonViTinhDAO();
	JFrame frame;
	AutoDAO autodao = new AutoDAO();
	FormSanPham formSP = new FormSanPham(frame);

	/**
	 * hàm gọi các hàm bên dưới
	 */
	public void init() {
		//hàm tự xin mã
		lblMaSP.setText(autodao.AuToSanPham());
		loadToList();
		fillConboboxDanhMuc();
		fillToComboboxDVT();

	}

	/**
	 * Load dữ liệu lên
	 */
	private void loadToList() {
		listDVT.addAll(dvtdao.selectAll());
	}

	/**
	 * Hàm fill dữ liệu đơn vị tính vào combobox
	 */
	private void fillToComboboxDVT() {
		// Clear and add data to list
		listDVT.clear();
		listDVT.addAll(dvtdao.selectAll());
		// Fill to combobox
		modelCboDVT = new DefaultComboBoxModel();
		cboDVT.setModel(modelCboDVT);
		modelCboDVT.removeAllElements();
		for (ModelDonViTinh dvt : listDVT) {
			modelCboDVT.addElement(dvt);
		}
	}

	/**
	 * Hàm fill dữ liệu danh mục vào combobox
	 */
	private void fillConboboxDanhMuc() {
		modelCboDanhMuc = (DefaultComboBoxModel<ModelDanhMuc>) cboMaDanhMuc.getModel();
		cboMaDanhMuc.setModel(modelCboDanhMuc);
		modelCboDanhMuc.removeAllElements();
		for (ModelDanhMuc dm : formSP.getlistDanhMuc()) {
			modelCboDanhMuc.addElement(dm);
		}
	}

	public boolean getIsDispose() {
		return this.dispose;
	}

	public ModelSanPham getSanPham() {
		return this.sanPham;
	}

	public void setSanPham(ModelSanPham entity) {
		this.sanPham = entity;
	}

	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}

	/**
	 * Hàm get form
	 * @return
	 */
	public ModelSanPham getForm() {
		ModelSanPham sp = new ModelSanPham();
		sp.setMaSP(lblMaSP.getText());
		sp.setTenSP(txtTenSP.getText());
		ModelDanhMuc DM = (ModelDanhMuc) cboMaDanhMuc.getSelectedItem();
		sp.setMaDanhMuc(DM.getMaDanhMuc());
		sp.setGiaBan(Float.valueOf(txtGiaSP.getText()));
		ModelDonViTinh donViTinh = (ModelDonViTinh) cboDVT.getSelectedItem();
		sp.setMaDVT(donViTinh.getMaDVT());
		sp.setPathAnh(lblAvata.getToolTipText());
		sp.setTrangThai(true);
		return sp;
	}

	/**
	 * Hàm set form
	 * @return
	 */
	public void setForm() {
		if (sanPham.getPathAnh() != null) {
			lblAvata.setIcon(XImage.readImageThucDon(sanPham.getPathAnh()));
			lblAvata.setToolTipText(sanPham.getPathAnh());
		}
		lblMaSP.setText(sanPham.getMaSP());
		txtTenSP.setText(sanPham.getTenSP().trim());
		ModelDanhMuc dm = null;
		for (ModelDanhMuc Dm : formSP.getlistDanhMuc()) {
			if (Dm.getMaDanhMuc().equals(sanPham.getMaDanhMuc())) {
				dm = Dm;
			}
		}
		cboMaDanhMuc.setSelectedItem(dm);
		Long gia = Long.parseLong(String.valueOf(sanPham.getGiaBan()).replace(".", ""));
		txtGiaSP.setText(String.valueOf(gia));
		ModelDonViTinh donViTinh = new ModelDonViTinh();
		for (ModelDonViTinh dvt : listDVT) {
			if (sanPham.getMaDVT() == dvt.getMaDVT()) {
				donViTinh = dvt;
			}
		}
		cboDVT.setSelectedItem(donViTinh);
	}

	/**
	 * Hàm chọn avata
	 */
	public void setIconlblAvata() {
		JFileChooser chon = new JFileChooser();
		if (chon.showOpenDialog(this) == chon.APPROVE_OPTION) {
			File file = chon.getSelectedFile();
			XImage.saveImageThucDon(file);
			ImageIcon icon = XImage.readImageThucDon(file.getName());
			// ImageIcon pic = icon.getImage().getScaledInstance(lblAvata.getWidth(),
			// lblAvata.getHeight(), Image.SCALE_SMOOTH);
			lblAvata.setIcon(icon);
			lblAvata.setToolTipText(file.getName());
		}
	}

	/**
	 * Hàm clean form
	 */
	public void cleanForm() {
		lblAvata.setIcon(null);
		lblMaSP.setText("");
		txtTenSP.setText("");
		txtGiaSP.setText("");
	}

	/**
	 * Insert DonVitinh to database
	 * 
	 * @param entity ModelDonViTinh
	 */
	private void insertDonViTinh(ModelDonViTinh entity) {
		insertdata(entity);
		fillinsert(entity);
	}
	
	public void insertdata(ModelDonViTinh entity) {
		dvtdao.insert(entity);
	}
	
	public void fillinsert(ModelDonViTinh entity) {
		listDVT.add(entity);
		fillToComboboxDVT();
	}

	/**
	 * Hàm bắt lỗi
	 * @return
	 */
	public boolean validForm() {
		String MaSP = lblMaSP.getText().trim();
		String TenSP = txtTenSP.getText().trim();
		String GiaSP = txtGiaSP.getText().trim();
		String avata = null;
		avata = lblAvata.getToolTipText();
		if (!FormValidator.isTextIsNotEmpty(MaSP) || !FormValidator.isTextIsNotEmpty(TenSP)
				|| !FormValidator.isTextIsNotEmpty(GiaSP)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ thông tin!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (Long.parseLong(GiaSP.trim()) < 1) {
			ROptionDialog.showAlert(frame, "Lỗi", "Giá sản phẩm phải lớn hơn 0!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (avata == null) {
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng chọn ảnh sản phẩm!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		setSanPham(getForm());
		return true;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		lblAvata = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		lblMaSP = new javax.swing.JLabel();
		cboMaDanhMuc = new com.swing.custom.raven.RComboBox.RCombobox();
		rButton1 = new com.swing.custom.raven.RButton.RButton();
		txtTenSP = new com.swing.custom.raven.RTextField.RTextField();
		txtGiaSP = new com.swing.custom.raven.RTextField.RTextField();
		jLabel6 = new javax.swing.JLabel();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel1 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel5 = new javax.swing.JLabel();
		rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnXacNhan = new com.swing.custom.raven.RButton.RButton();
		btnHuy = new com.swing.custom.raven.RButton.RButton();
		btnMoi = new com.swing.custom.raven.RButton.RButton();
		btnThemDVT = new com.swing.custom.raven.RButton.RButton();
		cboDVT = new com.swing.custom.raven.RComboBox.RComboBoxSuggestion();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(209, 220, 208), 2));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		lblAvata.setBackground(new java.awt.Color(204, 255, 255));
		lblAvata.setToolTipText("bocube.jpg");
		lblAvata.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/bocube.jpg"))); // NOI18N
		lblAvata.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				lblAvataMousePressed(evt);
			}
		});
		rRoundPanel1.add(lblAvata, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 140, 120));

		lblMaSP.setBackground(new java.awt.Color(255, 255, 255));
		lblMaSP.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		lblMaSP.setForeground(new java.awt.Color(51, 51, 51));
		lblMaSP.setText("SP01");
		lblMaSP.setOpaque(true);
		rRoundPanel1.add(lblMaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 90, 30));

		cboMaDanhMuc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		cboMaDanhMuc.setLabeText("Danh Mục");
		cboMaDanhMuc.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboMaDanhMucActionPerformed(evt);
			}
		});
		rRoundPanel1.add(cboMaDanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 310, 50));

		rButton1.setBackground(new java.awt.Color(0, 0, 0));
		rButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/plus.png"))); // NOI18N
		rButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				rButton1ActionPerformed(evt);
			}
		});
		rRoundPanel1.add(rButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 210, 30, 30));

		txtTenSP.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtTenSP.setLabelText("Tên Sản Phẩm");
		rRoundPanel1.add(txtTenSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 130, 340, -1));

		txtGiaSP.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtGiaSP.setLabelText("Giá");
		rRoundPanel1.add(txtGiaSP, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 270, 330, -1));

		jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
		jLabel6.setForeground(new java.awt.Color(102, 102, 102));
		jLabel6.setText("Mã Sản Phẩm:");
		rRoundPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 110, -1));

		rRoundPanel3.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("SẢN PHẨM");
		rRoundPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 40, -1, 40));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel3.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 10, 100, 80));

		jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("WolvesRes");
		rRoundPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 90, -1, -1));

		rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 120));

		rRoundPanel4.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirmation.png"))); // NOI18N
		btnXacNhan.setText("Xác nhận");
		btnXacNhan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});
		rRoundPanel4.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 10, 120, 30));

		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnHuy.setText("Hủy");
		btnHuy.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});
		rRoundPanel4.add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 120, 30));

		btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
		btnMoi.setText("Làm mới");
		btnMoi.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		btnMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnMoiActionPerformed(evt);
			}
		});
		rRoundPanel4.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 10, 120, 30));

		rRoundPanel1.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 610, 50));

		btnThemDVT.setBackground(new java.awt.Color(0, 0, 0));
		btnThemDVT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/plus.png"))); // NOI18N
		btnThemDVT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnThemDVTActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnThemDVT, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 360, 30, 30));

		cboDVT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboDVTActionPerformed(evt);
			}
		});
		rRoundPanel1.add(cboDVT, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 360, 290, -1));

		jLabel2.setForeground(new java.awt.Color(102, 102, 102));
		jLabel2.setText("Đơn vị tính");
		rRoundPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, -1, -1));

		jLabel3.setFont(new java.awt.Font("SansSerif", 2, 12)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(255, 0, 0));
		jLabel3.setText("Lưu ý! giá bán phải bao gồm 10% thuế VAT");
		rRoundPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 320, 320, -1));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				rRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				rRoundPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.PREFERRED_SIZE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXacNhanActionPerformed
		if (validForm()) {
			dispose = false;
			dispose();
		}
	}// GEN-LAST:event_btnXacNhanActionPerformed

	private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyActionPerformed
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn hủy không?", ROptionDialog.WARNING,
				Color.yellow, Color.black)) {
			dispose();
		}
	}// GEN-LAST:event_btnHuyActionPerformed

	private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnMoiActionPerformed
		cleanForm();
	}// GEN-LAST:event_btnMoiActionPerformed

	private void rButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_rButton1ActionPerformed
		EditDanhMuc editDM = new EditDanhMuc(frame, true);
		editDM.setVisible(true);
		if (!editDM.isDispose()) {
			dmDAO.insert(editDM.getDm());
			formSP.getlistDanhMuc().add(editDM.getDm());
			fillConboboxDanhMuc();
		}
	}// GEN-LAST:event_rButton1ActionPerformed

	private void cboMaDanhMucActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboMaDanhMucActionPerformed
		// TODO add your handling code here:
		ModelDanhMuc nv = (ModelDanhMuc) cboMaDanhMuc.getSelectedItem();
	}// GEN-LAST:event_cboMaDanhMucActionPerformed

	private void lblAvataMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_lblAvataMousePressed
		setIconlblAvata();
	}// GEN-LAST:event_lblAvataMousePressed

	private void btnThemDVTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnThemDVTActionPerformed
		EditDonviTinh editDVT = new EditDonviTinh(frame, true);
		editDVT.setVisible(true);
		if (!editDVT.isDispose()) {
			insertDonViTinh(editDVT.getDonViTinh());
		}
	}// GEN-LAST:event_btnThemDVTActionPerformed

	private void cboDVTActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboDVTActionPerformed
		ModelDonViTinh nv = (ModelDonViTinh) cboDVT.getSelectedItem();
	}// GEN-LAST:event_cboDVTActionPerformed

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
			java.util.logging.Logger.getLogger(EditSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EditSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EditSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EditSanPham.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				EditSanPham dialog = new EditSanPham(new javax.swing.JFrame(), true);
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
	private com.swing.custom.raven.RButton.RButton btnHuy;
	private com.swing.custom.raven.RButton.RButton btnMoi;
	private com.swing.custom.raven.RButton.RButton btnThemDVT;
	private com.swing.custom.raven.RButton.RButton btnXacNhan;
	private javax.swing.ButtonGroup buttonGroup1;
	private com.swing.custom.raven.RComboBox.RComboBoxSuggestion cboDVT;
	private com.swing.custom.raven.RComboBox.RCombobox cboMaDanhMuc;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar lblAvata;
	private javax.swing.JLabel lblMaSP;
	private com.swing.custom.raven.RButton.RButton rButton1;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
	private com.swing.custom.raven.RTextField.RTextField txtGiaSP;
	private com.swing.custom.raven.RTextField.RTextField txtTenSP;
	// End of variables declaration//GEN-END:variables

}
