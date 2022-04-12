package com.wolvesres.form.taikhoan;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.NhanVienDAO;
import com.wolvesres.dao.TaiKhoanDAO;
import com.wolvesres.form.FormTaiKhoan;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelNhanVien;
import com.wolvesres.model.ModelTaiKhoan;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;

/**
 * Chỉnh sửa hàm bắt lỗi, comment ở các hàm
 * 
 * @author huynh
 *
 */
public class EditTaiKhoan extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;

	public EditTaiKhoan(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		this.frame = (JFrame) parent;
		init();
		setLocationRelativeTo(null);
	}

	private ModelTaiKhoan tk = null;
	public boolean isInsert;
	private boolean dispose = true;
	FormTaiKhoan formTK = new FormTaiKhoan(frame);
	DefaultComboBoxModel<ModelNhanVien> model;
	private NhanVienDAO nvDAO = new NhanVienDAO();
	private TaiKhoanDAO tkDAO = new TaiKhoanDAO();
	private List<ModelNhanVien> listNhanVien = new ArrayList<>();
	private List<ModelTaiKhoan> listTaiKhoan = new ArrayList<>();

	/**
	 * Hàm tổng hợp các hàm bên dưới
	 */
	public void init() {
		cboNhanVien.setLabeText("Tên nhân viên");
		txtTenTaiKhoan.setLabelText("Mã nhân viên");
		txtTenTaiKhoan.setEditable(false);
		pwdMatKhau.setLabelText("Mật khẩu");
		pwdNhapLaiMK.setLabelText("Nhập lại mật khẩu:");
		pwdMatKhau.setEchoChar((char) 0);
		pwdNhapLaiMK.setEchoChar((char) 0);
		loadListNhanVien();
		loadLisTaiKhoan();
		// Load dư liệu của nhân viên vào combobox
		model = (DefaultComboBoxModel<ModelNhanVien>) cboNhanVien.getModel();
		for (ModelNhanVien emp : listNhanVien()) {
			model.addElement(emp);
		}
	}

	/**
	 * Hàm load dữ liệu
	 */
	private void loadLisTaiKhoan() {
		listTaiKhoan.addAll(tkDAO.selectAll());
	}

	/**
	 * Hàm load dữ liệu
	 */
	private void loadListNhanVien() {
		listNhanVien.addAll(nvDAO.selectAll());
	}

	/**
	 * Hàm load dữ liệu của nhân viên lên combobox
	 * 
	 * @return
	 */
	public List<ModelNhanVien> listNhanVien() {
		List<ModelNhanVien> listnv = new ArrayList<>();
		for (int i = 0; i < listNhanVien.size(); i++) {
			if (listNhanVien.get(i).getChucVu() <= 3) {
				listnv.add(listNhanVien.get(i));
			}
		}
		for (int j = 0; j < listTaiKhoan.size(); j++) {
			for (int k = 0; k < listnv.size(); k++) {
				if (listTaiKhoan.get(j).getTaiKhoan().equals(listnv.get(k).getMaNV())) {
					listnv.remove(k);
				}
			}
		}
		return listnv;
	}

	public boolean getIsDispose() {
		return this.dispose;
	}

	public ModelTaiKhoan getTaiKhoan() {
		return this.tk;
	}

	public void setTaiKhoan(ModelTaiKhoan entity) {
		this.tk = entity;
	}

	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}

	public ModelTaiKhoan getForm() {
		ModelTaiKhoan entity = new ModelTaiKhoan();
		ModelNhanVien NV = (ModelNhanVien) cboNhanVien.getSelectedItem();
		entity.setTaiKhoan(NV.getMaNV());
		entity.setTaiKhoan(txtTenTaiKhoan.getText().trim());
		entity.setMatKhau(pwdMatKhau.getText());
		entity.setTrangThai(true);
		return entity;
	}

	public void setForṃ() {
		ModelNhanVien nv = null;
		for (ModelNhanVien NV : listNhanVien()) {
			if (NV.getMaNV().equals(tk.getTaiKhoan())) {
				nv = NV;
			}
		}
	}

	/**
	 * Hàm clean form như bình thường
	 */
	public void cleanForm() {
		txtTenTaiKhoan.setText("");
		pwdMatKhau.setText("");
		pwdNhapLaiMK.setText("");
		cboNhanVien.setSelectedItem(false);
	}

	/**
	 * Hàm bắt lỗi nhập mật khẩu phải có bao nhiêu kí tự...
	 */
	public static final Pattern VALID_PASS_ADDRESS_REGEX = Pattern.compile("[a-z0-9_-]{6,16}$",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Hàm set thằng ở trên
	 * 
	 * @param passStr
	 * @return
	 */
	public static boolean validatePass(String passStr) {
		Matcher matcher = VALID_PASS_ADDRESS_REGEX.matcher(passStr);
		return matcher.find();
	}

	/**
	 * Hàm bắt lỗi
	 * 
	 * @return
	 */
	public boolean valideForm() {
		String Tennv = txtTenTaiKhoan.getText().trim();
		String Matkhau = String.valueOf(pwdMatKhau.getPassword()).trim();
		String NhaplaiMK = String.valueOf(pwdNhapLaiMK.getPassword()).trim();
		if (!FormValidator.isTextIsNotEmpty(Tennv) || !FormValidator.isTextIsNotEmpty(Matkhau)
				|| !FormValidator.isTextIsNotEmpty(NhaplaiMK)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ thông tin!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else {
			if (!NhaplaiMK.equalsIgnoreCase(Matkhau)) {
				ROptionDialog.showAlert(frame, "Lỗi", "Nhập lại mật khẩu chưa chính xác", ROptionDialog.WARNING,
						Color.red, Color.black);
				return false;
			}
			if (!validatePass(Matkhau)) {
				ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập từ 6 đến 16 ký tự", ROptionDialog.WARNING,
						Color.red, Color.black);
				return false;
			}
		}
		setTaiKhoan(getForm());
		return true;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		buttonGroup1 = new javax.swing.ButtonGroup();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		txtTenTaiKhoan = new com.swing.custom.raven.RTextField.RTextField();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel1 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel5 = new javax.swing.JLabel();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnXacNhan = new com.swing.custom.raven.RButton.RButton();
		btnLamMoi = new com.swing.custom.raven.RButton.RButton();
		btnHuy = new com.swing.custom.raven.RButton.RButton();
		cboNhanVien = new com.swing.custom.raven.RComboBox.RCombobox();
		pwdNhapLaiMK = new com.swing.custom.raven.RPasswordField.RPasswordField();
		pwdMatKhau = new com.swing.custom.raven.RPasswordField.RPasswordField();
		sbtnTrangThai = new com.swing.custom.raven.RSwitchButton.SwitchButton();
		lblTrangThai = new javax.swing.JLabel();
		lblAvatar = new com.swing.custom.raven.RImageAvatar.RImageAvatar();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowActivated(java.awt.event.WindowEvent evt) {
				formWindowActivated(evt);
			}
		});

		rRoundPanel3.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(209, 220, 208), 2));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		txtTenTaiKhoan.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		rRoundPanel3.add(txtTenTaiKhoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, 280, -1));

		rRoundPanel1.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel1.setPreferredSize(new java.awt.Dimension(650, 120));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("TÀI KHOẢN");
		rRoundPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, 40));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel1.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 100, 80));

		jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("WolvesRes");
		rRoundPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

		rRoundPanel3.add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, -1));

		rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel2.setPreferredSize(new java.awt.Dimension(650, 50));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirmation.png"))); // NOI18N
		btnXacNhan.setText("Xác nhận");
		btnXacNhan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 120, 40));

		btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
		btnLamMoi.setText("Làm mới");
		btnLamMoi.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLamMoiActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 110, 40));

		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnHuy.setText("Hủy");
		btnHuy.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 90, 40));

		rRoundPanel3.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 650, 60));

		cboNhanVien.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		cboNhanVien.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboNhanVienActionPerformed(evt);
			}
		});
		rRoundPanel3.add(cboNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 270, -1));

		pwdNhapLaiMK.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		rRoundPanel3.add(pwdNhapLaiMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 280, -1));

		pwdMatKhau.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		rRoundPanel3.add(pwdMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, 270, -1));

		sbtnTrangThai.setSelected(true);
		rRoundPanel3.add(sbtnTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 530, 70, -1));

		lblTrangThai.setForeground(new java.awt.Color(102, 102, 102));
		lblTrangThai.setText("Trạng thái:");
		rRoundPanel3.add(lblTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, -1, -1));

		lblAvatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/userRemember.png"))); // NOI18N
		rRoundPanel3.add(lblAvatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 130, 210, 170));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(rRoundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rRoundPanel3,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXacNhanActionPerformed
		if (valideForm()) {
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

	private void cboNhanVienActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboNhanVienActionPerformed
		// TODO add your handling code here:
		ModelNhanVien nv = (ModelNhanVien) cboNhanVien.getSelectedItem();
		txtTenTaiKhoan.setText(nv.getMaNV());
		if (nv.getPathHinhAnh() != null) {
			lblAvatar.setIcon(XImage.readImageNhanVien(nv.getPathHinhAnh()));
		}
	}// GEN-LAST:event_cboNhanVienActionPerformed

	private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiActionPerformed
		cleanForm();
	}// GEN-LAST:event_btnLamMoiActionPerformed

	private void formWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowActivated
		if (isInsert) {
			lblTrangThai.setVisible(false);
			sbtnTrangThai.setVisible(false);
		}
	}// GEN-LAST:event_formWindowActivated

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
			java.util.logging.Logger.getLogger(EditTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EditTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EditTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EditTaiKhoan.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				EditTaiKhoan dialog = new EditTaiKhoan(new javax.swing.JFrame(), true);
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
	private com.swing.custom.raven.RButton.RButton btnLamMoi;
	private com.swing.custom.raven.RButton.RButton btnXacNhan;
	private javax.swing.ButtonGroup buttonGroup1;
	private com.swing.custom.raven.RComboBox.RCombobox cboNhanVien;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel5;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar lblAvatar;
	private javax.swing.JLabel lblTrangThai;
	private com.swing.custom.raven.RPasswordField.RPasswordField pwdMatKhau;
	private com.swing.custom.raven.RPasswordField.RPasswordField pwdNhapLaiMK;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RSwitchButton.SwitchButton sbtnTrangThai;
	private com.swing.custom.raven.RTextField.RTextField txtTenTaiKhoan;
	// End of variables declaration//GEN-END:variables
}
