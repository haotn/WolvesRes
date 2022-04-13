package com.wolvesres.form.nhanvien;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.AutoDAO;
import com.wolvesres.form.FormNhanVien;
import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelNhanVien;
import java.awt.Color;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.wolvesres.helper.DataGenerator;
import com.wolvesres.helper.FormValidator;

/**
 * Cac class lien quan: FormNhanVien, NhanVienDAO, EditNhanVien,
 * BlackListNhanVien
 * 
 * @author Brian
 *
 */
public class EditNhanVien extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Ham tao JDialog Edit nhan vien, thao tac insert va updateToList
	 *
	 * @param parent
	 * @param modal
	 */
	public EditNhanVien(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setLocationRelativeTo(null);
		init();
		this.frame = (JFrame) parent;
	}

	/**
	 * Khoi tao component
	 */
	public void init() {
		lblMaNV.setText(autoDao.AuToNhanVien());
		rdoNam.setSelected(true);

	}

	/**
	 * Khoi tao cac bien can thiet
	 */
	private boolean dispose = true;
	private ModelNhanVien emp = null;
	private boolean isInsert;
	JFrame frame;
	AutoDAO autoDao = new AutoDAO();
	FormNhanVien formParent = new FormNhanVien(frame);
	private DataGenerator data = new DataGenerator();

	/**
	 * getNhanVien tra ve mot doi tuong nhan vien duoc lay tu cac truong du lieu
	 *
	 * @return
	 */
	public ModelNhanVien getNhanVien() {
		return this.emp;
	}

	public ModelNhanVien getForm() {
		ModelNhanVien entity = new ModelNhanVien();
		entity.setMaNV(lblMaNV.getText());
		entity.setHoTen(txtHoTen.getText().trim());
		entity.setCMND(txtCCCD.getText().trim());
		entity.setChucVu(cboChucVu.getSelectedIndex() + 2);
		entity.setEmail(txtEmail.getText().trim());
		entity.setGioiTinh(rdoNam.isSelected());
		entity.setNgaySinh(txtNgaySinh.getText());
		entity.setSoDT(txtSoDT.getText().trim());
		entity.setPathHinhAnh(avatar.getToolTipText());
		entity.setTrangThai(true);
		return entity;
	}

	/**
	 * Reset form to default value
	 */
	public void clearForm() {
		txtHoTen.setText("");
		txtEmail.setText("");
		txtCCCD.setText("");
		txtSoDT.setText("");
	}

	/**
	 * Get this.dispose value
	 * 
	 * @return
	 */
	public boolean getIsDispose() {
		return this.dispose;
	}

	/**
	 * Khi isInsert = true, hien thi form trong de nhap thong tin them nhan vien moi
	 * Khi isInsert = false, hien thi form voi cac du lieu duoc lay tu phuong thuc
	 * setNhanVien
	 *
	 * @param isInsert
	 */
	public void setInsert(boolean isInsert) {
		this.isInsert = isInsert;
	}

	/**
	 * Set value for this.emp
	 * 
	 * @param entity
	 */
	public void setNhanVien(ModelNhanVien entity) {
		this.emp = entity;
	}

	/**
	 * Set data to form is isInsert=false
	 */
	public void setForm() {
		if (emp.getPathHinhAnh() != null) {
			avatar.setIcon(XImage.readImageNhanVien(emp.getPathHinhAnh()));
			avatar.setToolTipText(emp.getPathHinhAnh());
		}
		lblMaNV.setText(this.emp.getMaNV());
		txtHoTen.setText(this.emp.getHoTen());
		txtCCCD.setText(this.emp.getCMND());
		txtEmail.setText(this.emp.getEmail());
		if (emp.getSoDT() != null) {
			txtSoDT.setText(this.emp.getSoDT());
		}
		dateChooser.setSelectedDate(XDate.toDate(emp.getNgaySinh(), "dd-MM-yyyy"));
		cboChucVu.setSelectedItem(this.emp.getTenChucVu(this.emp.getChucVu()));
		if (this.emp.isGioiTinh()) {
			rdoNam.setSelected(true);
		} else {
			rdoNu.setSelected(true);
		}
	}

	/**
	 * Valid form data
	 * 
	 * @return is valid
	 */
	public boolean valideForm() {
		// Get form data
		String fullName = txtHoTen.getText().trim();
		String idEmp = lblMaNV.getText();
		String phoneNumber = txtSoDT.getText().trim();
		String email = txtEmail.getText().trim();
		String idNational = txtCCCD.getText().trim();
		// Get YearOfBirth
		int yearOfBirth = dateChooser.getSelectedDate().getYear();
		Date birthDay = XDate.toDate(txtNgaySinh.getText(), "dd-MM-yyyy");
		String pathAvatar = null;
		Date today = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(today);
		// Get year now
		int yearNow = cal.get(Calendar.YEAR);
		// Set value for pathAvatar
		pathAvatar = avatar.getToolTipText();

		if (!FormValidator.isTextIsNotEmpty(fullName) || !FormValidator.isTextIsNotEmpty(phoneNumber)
				|| !FormValidator.isTextIsNotEmpty(email) || !FormValidator.isTextIsNotEmpty(idNational)) {
			// Check if any information is empty
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ dữ liệu!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!FormValidator.isTextContainsSpace(fullName)) {
			// Check if fullname is invalid
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ họ tên!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!FormValidator.isValidTextLength(idNational, 12)
				&& !FormValidator.isValidTextLength(idNational, 9)) {
			// Check if idNational's length is invalid
			ROptionDialog.showAlert(frame, "Lỗi", "Độ dài số CMND/CCCD không hợp lệ!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!FormValidator.isIntNumber(idNational)) {
			// Check if idNational is not number
			ROptionDialog.showAlert(frame, "Lỗi", "CMND/CCCD phải là số!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		if (idNational.length() == 12) {
			// Is Id National
			if (!FormValidator.isValidGenderCode(idNational, rdoNam.isSelected(), yearOfBirth)) {
				// Check if gender code is invalid
				ROptionDialog.showAlert(frame, "Lỗi", "Số CCCD không hợp lệ (sai mã giới tính)!", ROptionDialog.WARNING,
						Color.red, Color.black);
				return false;
			} else if (!FormValidator.isValidYearOfBirthCode(idNational, yearOfBirth)) {
				// Check if year of birth code in id national is invalid
				ROptionDialog.showAlert(frame, "Lỗi", "Số CCCD không hợp lệ (sai mã năm sinh)!", ROptionDialog.WARNING,
						Color.red, Color.black);
				return false;
			} else if (!FormValidator.isValidIdNational(idNational)) {
				// Check if id national is invalid
				ROptionDialog.showAlert(frame, "Lỗi", "Số CCCD không đúng định dạng!", ROptionDialog.WARNING, Color.red,
						Color.black);
				return false;
			}
		} else {
			// Is CMND
			if (!FormValidator.isValidIdCMND(idNational)) {
				// Check if CMND is invalid
				ROptionDialog.showAlert(frame, "Lỗi", "Số CMND sai định dạng!", ROptionDialog.WARNING, Color.red,
						Color.black);
				return false;
			}
		}
		if (!FormValidator.isValidEmail(email)) {
			// Check if email format is invalid
			ROptionDialog.showAlert(frame, "Lỗi", "Email sai định dang!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!FormValidator.isValidTextLength(phoneNumber, 10)) {
			// Check if phone number length is invalid
			ROptionDialog.showAlert(frame, "Lỗi", "Số điện thoại phải có 10 số!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!FormValidator.isValidPhoneNumber(phoneNumber)) {
			// Check if phone number format is invalid
			ROptionDialog.showAlert(frame, "Lỗi", "Số điện thoại sai định dạng!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (pathAvatar == null) {
			// Check if avatar is null
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng chọn ảnh nhân viên!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!FormValidator.isValidAge(birthDay)) {
			// Check if birth day is invalid
			ROptionDialog.showAlert(frame, "Lỗi", "Tuổi nhân viên phải từ 18!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		if (idNational.length() == 12) {
			// Is Id National
			if (!FormValidator.isIdNationalNotDuplicate(idNational, formParent.getList(), isInsert, idEmp)) {
				// Check if id national duplicate
				return false;
			}
		} else {
			// Is CMND
			if (!FormValidator.isCMNDNotDuplicate(idNational, formParent.getList(), isInsert, idEmp)) {
				// Check if cmnd duplicate
				return false;
			}
		}
		if (!FormValidator.isPhoneNumberNotDuplicate(phoneNumber, formParent.getList(), isInsert, idEmp)) {
			// Check if phone number is duplicate
			ROptionDialog.showAlert(frame, "Lỗi", "Số điện thoại không được trùng!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		if (!FormValidator.isEmailNotDuplicate(email, formParent.getList(), isInsert, idEmp)) {
			// Check if email is duplicate
			ROptionDialog.showAlert(frame, "Lỗi", "Email không được trùng!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		setNhanVien(getForm());
		return true;
	}

	/**
	 * Set image for lblAvatar
	 */
	public void setIconLblAvatar() {
		if (fileChoocer.showOpenDialog(this) == fileChoocer.APPROVE_OPTION) {
			File file = fileChoocer.getSelectedFile();
			XImage.saveImageNhanVien(file);
			ImageIcon icon = XImage.readImageNhanVien(file.getName());
			avatar.setIcon(icon);
			avatar.setToolTipText(file.getName());
		}
	}

	/**
	 * Insert NhanVien to database
	 * 
	 * @param entity
	 */

	public void insertNhanVien(ModelNhanVien entity) {
		entity.insert();
	}

	/**
	 * Update NhanVien to database
	 * 
	 * @param entity
	 */
	public void updateNhanVien(ModelNhanVien entity) {
		entity.update();
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		dateChooser = new com.raven.datechooser.DateChooser();
		fileChoocer = new javax.swing.JFileChooser();
		buttonGroup1 = new javax.swing.ButtonGroup();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		avatar = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		txtNgaySinh = new com.swing.custom.raven.RTextField.RTextField();
		lblMaNV = new javax.swing.JLabel();
		txtCCCD = new com.swing.custom.raven.RTextField.RTextField();
		txtHoTen = new com.swing.custom.raven.RTextField.RTextField();
		txtEmail = new com.swing.custom.raven.RTextField.RTextField();
		txtSoDT = new com.swing.custom.raven.RTextField.RTextField();
		rdoNam = new com.swing.custom.raven.RRadioButton.RRadioButton();
		rdoNu = new com.swing.custom.raven.RRadioButton.RRadioButton();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		cboChucVu = new com.swing.custom.raven.RComboBox.RComboBoxSuggestion();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel1 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel5 = new javax.swing.JLabel();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnHuy = new com.swing.custom.raven.RButton.RButton();
		btnXacNhan = new com.swing.custom.raven.RButton.RButton();
		btnLamMoi = new com.swing.custom.raven.RButton.RButton();

		dateChooser.setForeground(new java.awt.Color(0, 0, 0));
		dateChooser.setTextRefernce(txtNgaySinh);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setMinimumSize(new java.awt.Dimension(650, 680));

		rRoundPanel3.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(209, 220, 208), 2));
		rRoundPanel3.setMaximumSize(new java.awt.Dimension(650, 650));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		avatar.setBackground(new java.awt.Color(255, 51, 102));
		avatar.setToolTipText("kh_johnny_dang.png");
		avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/kh_johnny_dang.png"))); // NOI18N
		avatar.setPreferredSize(new java.awt.Dimension(150, 150));
		avatar.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				avatarMousePressed(evt);
			}
		});
		rRoundPanel3.add(avatar, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

		txtNgaySinh.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtNgaySinh.setLabelText("Ngày sinh");
		rRoundPanel3.add(txtNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 300, -1));

		lblMaNV.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
		lblMaNV.setForeground(new java.awt.Color(102, 102, 102));
		lblMaNV.setText("Mã nhân viên");
		rRoundPanel3.add(lblMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 417, 40));

		txtCCCD.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtCCCD.setLabelText("Số CMND/CCCD");
		rRoundPanel3.add(txtCCCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 300, -1));

		txtHoTen.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtHoTen.setLabelText("Họ tên nhân viên");
		rRoundPanel3.add(txtHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 300, -1));

		txtEmail.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtEmail.setLabelText("Địa chỉ Email");
		rRoundPanel3.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 300, -1));

		txtSoDT.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtSoDT.setLabelText("Số điện thoại");
		rRoundPanel3.add(txtSoDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 300, -1));

		buttonGroup1.add(rdoNam);
		rdoNam.setText("Nam");
		rdoNam.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		rRoundPanel3.add(rdoNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, -1, -1));

		buttonGroup1.add(rdoNu);
		rdoNu.setText("Nữ");
		rdoNu.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		rRoundPanel3.add(rdoNu, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 320, -1, -1));

		jLabel2.setForeground(new java.awt.Color(102, 102, 102));
		jLabel2.setText("Giới tính");
		rRoundPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 310, -1, -1));

		jLabel3.setForeground(new java.awt.Color(102, 102, 102));
		jLabel3.setText("Chức vụ");
		rRoundPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 360, -1, -1));

		cboChucVu.setModel(
				new javax.swing.DefaultComboBoxModel(new String[] { "Quản lý", "Thu ngân", "Nhân viên khác ", " " }));
		rRoundPanel3.add(cboChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 380, 184, -1));

		rRoundPanel1.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel1.setPreferredSize(new java.awt.Dimension(650, 120));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("NHÂN VIÊN");
		rRoundPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 30, -1, 50));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rImageAvatar1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				rImageAvatar1MousePressed(evt);
			}
		});
		rRoundPanel1.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 100, 80));

		jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("WolvesRes");
		rRoundPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

		rRoundPanel3.add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, -1));

		rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel2.setPreferredSize(new java.awt.Dimension(650, 50));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnHuy.setText("Hủy");
		btnHuy.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 80, -1));

		btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirmation.png"))); // NOI18N
		btnXacNhan.setText("Xác nhận");
		btnXacNhan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, 120, 30));

		btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
		btnLamMoi.setText("Làm mới");
		btnLamMoi.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLamMoiActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 120, 30));

		rRoundPanel3.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 650, 60));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rRoundPanel3,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rRoundPanel3,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void avatarMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_avatarMousePressed
		setIconLblAvatar();
	}// GEN-LAST:event_avatarMousePressed

	private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXacNhanActionPerformed
		if (valideForm()) {
			if (isInsert) {
				insertNhanVien(this.getNhanVien());
			} else {
				updateNhanVien(this.getNhanVien());
			}
			dispose = false;
			dispose();
		}
	}// GEN-LAST:event_btnXacNhanActionPerformed

	private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiActionPerformed
		clearForm();
	}// GEN-LAST:event_btnLamMoiActionPerformed

	private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyActionPerformed
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn hủy không?", ROptionDialog.WARNING,
				Color.yellow, Color.black)) {
			dispose();
		}
	}// GEN-LAST:event_btnHuyActionPerformed

	private void rImageAvatar1MousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_rImageAvatar1MousePressed
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có muốn sử dụng dữ liệu mẫu", ROptionDialog.WARNING,
				Color.yellow, Color.black)) {
			txtCCCD.setText(
					data.generateIdNational(XDate.toDate(txtNgaySinh.getText(), "dd-MM-yyyy"), rdoNam.isSelected()));
			txtSoDT.setText(data.generateSDT());
		}
	}// GEN-LAST:event_rImageAvatar1MousePressed

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
			java.util.logging.Logger.getLogger(EditNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EditNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EditNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EditNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				EditNhanVien dialog = new EditNhanVien(new javax.swing.JFrame(), true);
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
	private com.swing.custom.raven.RImageAvatar.RImageAvatar avatar;
	private com.swing.custom.raven.RButton.RButton btnHuy;
	private com.swing.custom.raven.RButton.RButton btnLamMoi;
	private com.swing.custom.raven.RButton.RButton btnXacNhan;
	private javax.swing.ButtonGroup buttonGroup1;
	private com.swing.custom.raven.RComboBox.RComboBoxSuggestion cboChucVu;
	private com.raven.datechooser.DateChooser dateChooser;
	private javax.swing.JFileChooser fileChoocer;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel lblMaNV;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RRadioButton.RRadioButton rdoNam;
	private com.swing.custom.raven.RRadioButton.RRadioButton rdoNu;
	private com.swing.custom.raven.RTextField.RTextField txtCCCD;
	private com.swing.custom.raven.RTextField.RTextField txtEmail;
	private com.swing.custom.raven.RTextField.RTextField txtHoTen;
	private com.swing.custom.raven.RTextField.RTextField txtNgaySinh;
	private com.swing.custom.raven.RTextField.RTextField txtSoDT;
	// End of variables declaration//GEN-END:variables

}
