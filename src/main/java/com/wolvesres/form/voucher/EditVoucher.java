package com.wolvesres.form.voucher;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.VoucherDAO;
import com.wolvesres.form.FormVoucher;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.helper.XDate;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelVouCher;
import java.awt.Color;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.awt.event.WindowEvent;

/**
 * Cac class lien quan FormVoucher, BlackListVoucher, VoucherDAO, VoucherDAO,
 * CanVoucher
 * 
 * @author Brian
 *
 */
public class EditVoucher extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param parent
	 * @param modal
	 */
	public EditVoucher(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		this.frame = (JFrame) parent;
		setLocationRelativeTo(null);
		init();
	}

	/**
	 * Generate global variable
	 */
	JFrame frame;
	private boolean insert = true;
	private boolean dispose = true;
	private ModelVouCher voucher = null;
	private FormVoucher formParent = new FormVoucher(frame);
	private VoucherDAO dao = new VoucherDAO();
	private String mavc = null;

	/**
	 * Init method
	 */
	public void init() {
		sbtnTrangThai.setSelected(true);
		txtMaVoucher.setEditable(true);
		sbtnTrangThai.setVisible(false);
	}

	/**
	 * Get form data
	 * 
	 * @return ModelVoucher
	 */
	public ModelVouCher getForm() {
		ModelVouCher vc = new ModelVouCher();
		vc.setMaVoucher(txtMaVoucher.getText().trim());
		vc.setGiamGia(Float.parseFloat(txtGiamGia.getText().trim()));
		vc.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));
		vc.setNgayBatDau(txtNgayBatDau.getText());
		vc.setNgayKetThuc(txtNgayKetThuc.getText());
		vc.setTrangThai(sbtnTrangThai.isSelected());
		return vc;
	}

	/**
	 * Set form data
	 */
	public void setForm() {
		txtMaVoucher.setText(voucher.getMaVoucher());
		txtGiamGia.setText(String.valueOf(voucher.getGiamGia()));
		txtSoLuong.setText(String.valueOf(voucher.getSoLuong()));
		dcrNgayBatDau.setSelectedDate(XDate.toDate(voucher.getNgayBatDau(), "dd-MM-yyyy"));
		dcrNgayKetThuc.setSelectedDate(XDate.toDate(voucher.getNgayKetThuc(), "dd-MM-yyyy"));
		sbtnTrangThai.setSelected(voucher.isTrangThai());
		sbtnTrangThai.setVisible(true);
	}

	/**
	 * Set value for this.voucher
	 * 
	 * @param vc
	 */
	public void setVoucher(ModelVouCher vc) {
		this.voucher = vc;
	}

	/**
	 * Get value of this.voucher
	 * 
	 * @return
	 */
	public ModelVouCher getVoucher() {
		return this.voucher;
	}

	/**
	 * Check if this action is insert or edit
	 * 
	 * @param isInsert
	 */
	public void isInsert(boolean isInsert) {
		this.insert = isInsert;
	}

	/**
	 * Get value of isInsert
	 * 
	 * @return isInsert
	 */
	public boolean isInsert() {
		return this.insert;
	}

	/**
	 * Check if action is dispose
	 * 
	 * @return isDispose
	 */
	public boolean isDidpose() {
		return this.dispose;
	}

	/**
	 * Valid form data
	 * 
	 * @return
	 */
	public boolean validateForm() {
		String maVoucher = txtMaVoucher.getText().trim();
		String soLuong = txtSoLuong.getText().trim();
		String ngayKetThuc = txtNgayKetThuc.getText().trim();
		String ngayBatDau = txtNgayBatDau.getText();
		String giamGia = txtGiamGia.getText().trim();
		if (!FormValidator.isTextIsNotEmpty(maVoucher) || !FormValidator.isTextIsNotEmpty(soLuong)
				|| !FormValidator.isTextIsNotEmpty(giamGia)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ thông tin!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		} else if (!FormValidator.isValidTextMinLength(maVoucher, 5)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Mã voucher phải từ 5 ký tự!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		if (!FormValidator.isIntNumber(soLuong)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Trường số lượng chỉ chấp nhận số nguyên!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		if (!FormValidator.isGreaterThan(Integer.parseInt(soLuong), 0)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Số lượng phải lớn hơn 0!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		try {
			Float.parseFloat(giamGia);
		} catch (Exception e) {
			ROptionDialog.showAlert(frame, "Lỗi", "Giảm giá chỉ chấp nhận giá trị số!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		if (!FormValidator.isLessThan(Float.parseFloat(giamGia), 101)
				|| !FormValidator.isGreaterThan(Float.parseFloat(giamGia), 0)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Phần trăm giảm giá là số nguyên lớn hơn 0 và nhỏ hơn hoặc bằng 100!",
					ROptionDialog.WARNING, Color.red, Color.black);
			return false;
		}
		Date now = new Date();
		if (!FormValidator.isBeginDateValid(XDate.toDate(ngayBatDau, "dd-MM-yyyy")) && insert) {
			ROptionDialog.showAlert(frame, "Lỗi", "Ngày bắt đầu phải từ ngày hiện tại!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		} else if (!FormValidator.isEndDayValid(XDate.toDate(ngayBatDau, "dd-MM-yyyy"),
				XDate.toDate(ngayKetThuc, "dd-MM-yyyy"))) {
			ROptionDialog.showAlert(frame, "Lỗi", "Ngày kết thúc phải sau ngày bắt đầu!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		if (!insert) {
			if (!FormValidator.isDateBefore(XDate.toDate(voucher.getNgayBatDau(), "dd-MM-yyyy"), now)) {
				if (!!FormValidator.isDateEquals(XDate.toDate(ngayBatDau, "yyyy-MM-dd"),
						XDate.toDate(voucher.getNgayBatDau(), "yyyy-MM-dd"))) {
					ROptionDialog.showAlert(frame, "Lỗi", "Ngày bắt đầu không thể thay đổi!", ROptionDialog.WARNING,
							Color.red, Color.black);
					return false;
				}
			}
		}
		if (!FormValidator.isVoucherNotDuplicate(maVoucher, formParent.getList(), insert)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Mã Voucher đã tồn tại!", ROptionDialog.WARNING, Color.red,
					Color.black);
			return false;
		}
		setVoucher(getForm());
		return true;
	}

	/**
	 * Insert entity to database
	 * 
	 * @param entity
	 */
	public void insertVoucher(ModelVouCher entity) {
		entity.insert();
	}

	/**
	 * Update entity to database
	 * 
	 * @param entity
	 */
	public void updateVoucher(ModelVouCher entity, String mavc) {
		entity.update(mavc);
	}

//    private final String qcip = "src\\com\\wolvesres\\qrcode\\";
//
//    private static String generateQRCode(String text, int width, int height, String filePath, String fileName)
//            throws Exception {
//        QRCodeWriter qcwobj = new QRCodeWriter();
//        BitMatrix bmobj = qcwobj.encode(text, BarcodeFormat.QR_CODE, width, height);
//        Path pobj = FileSystems.getDefault().getPath(filePath + fileName);
//        MatrixToImageWriter.writeToPath(bmobj, "PNG", pobj);
//        return filePath + fileName;
//    }
//
//    public void run() {
//        try {
//            String path = generateQRCode(txtMaVoucher.getText(), 1250, 1250, qcip, txtMaVoucher.getText() + ".png");
//            voucher.setPathQR(path);
//        } catch (Exception ex) {
//            Logger.getLogger(EditVoucher.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
	/**
	 * Folder will be save QRCode
	 */
	private final String qcip = "QRCode/";

	/**
	 * Generate QRCode
	 * 
	 * @param text
	 * @param width
	 * @param height
	 * @param filePath
	 * @param fileName
	 * @return filename
	 * @throws Exception
	 */
	private static String generateQRCode(String text, int width, int height, String filePath, String fileName)
			throws Exception {
		QRCodeWriter qcwobj = new QRCodeWriter();
		BitMatrix bmobj = qcwobj.encode(text, BarcodeFormat.QR_CODE, width, height);

		Path pobj = FileSystems.getDefault().getPath(filePath + fileName);
		MatrixToImageWriter.writeToPath(bmobj, "PNG", pobj);
		return filePath + fileName;
	}

	/**
	 * Generate and save QRCode
	 */
	public void run() {
		try {
			String path = generateQRCode(txtMaVoucher.getText(), 1250, 1250, qcip, txtMaVoucher.getText() + ".png");
			File file = new File(path);
			XImage.saveImageQR(file);
			voucher.setPathQR(file.getName());
		} catch (Exception ex) {
			Logger.getLogger(EditVoucher.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * Reset form data
	 */
	private void clearForm() {
		txtMaVoucher.setText("");
		txtSoLuong.setText("");
		txtGiamGia.setText("");
		dcrNgayBatDau.setSelectedDate(new Date());
		dcrNgayKetThuc.setSelectedDate(new Date());
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		dcrNgayBatDau = new com.raven.datechooser.DateChooser();
		fileChoocer = new javax.swing.JFileChooser();
		buttonGroup1 = new javax.swing.ButtonGroup();
		dcrNgayKetThuc = new com.raven.datechooser.DateChooser();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		txtNgayBatDau = new com.swing.custom.raven.RTextField.RTextField();
		txtGiamGia = new com.swing.custom.raven.RTextField.RTextField();
		txtMaVoucher = new com.swing.custom.raven.RTextField.RTextField();
		txtNgayKetThuc = new com.swing.custom.raven.RTextField.RTextField();
		txtSoLuong = new com.swing.custom.raven.RTextField.RTextField();
		jLabel1 = new javax.swing.JLabel();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel2 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel5 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnHuy = new com.swing.custom.raven.RButton.RButton();
		btnLamMoi = new com.swing.custom.raven.RButton.RButton();
		btnXacNhan = new com.swing.custom.raven.RButton.RButton();
		sbtnTrangThai = new com.swing.custom.raven.RSwitchButton.SwitchButton();

		dcrNgayBatDau.setTextRefernce(txtNgayBatDau);

		dcrNgayKetThuc.setTextRefernce(txtNgayKetThuc);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setLocationByPlatform(true);
		setMaximumSize(new java.awt.Dimension(650, 680));
		setMinimumSize(new java.awt.Dimension(650, 680));
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowActivated(java.awt.event.WindowEvent evt) {
				formWindowActivated(evt);
			}

			@Override
			public void windowOpened(WindowEvent e) {
				if (!insert) {
					mavc = getVoucher().getMaVoucher();
				}
			}
		});

		rRoundPanel3.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		txtNgayBatDau.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtNgayBatDau.setLabelText("Ngày bắt đầu");
		rRoundPanel3.add(txtNgayBatDau, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 180, 270, -1));

		txtGiamGia.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtGiamGia.setLabelText("Giảm giá (%)");
		rRoundPanel3.add(txtGiamGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, 270, -1));

		txtMaVoucher.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtMaVoucher.setLabelText("Mã Voucher");
		rRoundPanel3.add(txtMaVoucher, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 270, -1));

		txtNgayKetThuc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtNgayKetThuc.setLabelText("Ngày kết thúc");
		rRoundPanel3.add(txtNgayKetThuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 260, 270, -1));

		txtSoLuong.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtSoLuong.setLabelText("Số lượng");
		rRoundPanel3.add(txtSoLuong, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 260, -1));

		jLabel1.setForeground(new java.awt.Color(102, 102, 102));
		jLabel1.setText("Trạng thái");
		rRoundPanel3.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 340, -1, -1));

		rRoundPanel1.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel2.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("VOUCHER");
		rRoundPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 40, -1, 40));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel1.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 100, 80));

		jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("WolvesRes");
		rRoundPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

		rRoundPanel3.add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 120));
		rRoundPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 150, 150));

		rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnHuy.setText("Hủy");
		btnHuy.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 90, 30));

		btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
		btnLamMoi.setText("Làm mới");
		btnLamMoi.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLamMoiActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 10, 120, 30));

		btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirmation.png"))); // NOI18N
		btnXacNhan.setText("Xác nhận");
		btnXacNhan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});
		rRoundPanel2.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 120, 30));

		rRoundPanel3.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 630, 650, 50));
		rRoundPanel3.add(sbtnTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 360, -1, -1));

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

	private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyActionPerformed
		if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn hủy không?", ROptionDialog.WARNING,
				Color.yellow, Color.black)) {
			dispose();
		}
	}// GEN-LAST:event_btnHuyActionPerformed

	private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXacNhanActionPerformed
		if (validateForm()) {
			run();
			if (isInsert()) {
				insertVoucher(voucher);
			} else {
				updateVoucher(voucher, this.mavc);
			}
			dispose = false;
			dispose();
		}
	}// GEN-LAST:event_btnXacNhanActionPerformed

	private void formWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowActivated
		if (insert) {
			btnLamMoi.setEnabled(true);
		} else {
			btnLamMoi.setEnabled(false);
			if (dao.checkForeignKey(voucher) == null) {
				txtMaVoucher.setEditable(true);
			} else {
				txtMaVoucher.setEditable(false);
			}
		}
	}// GEN-LAST:event_formWindowActivated

	private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiActionPerformed
		clearForm();
	}// GEN-LAST:event_btnLamMoiActionPerformed

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
			java.util.logging.Logger.getLogger(EditVoucher.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EditVoucher.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EditVoucher.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EditVoucher.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				EditVoucher dialog = new EditVoucher(new javax.swing.JFrame(), true);
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
	private com.raven.datechooser.DateChooser dcrNgayBatDau;
	private com.raven.datechooser.DateChooser dcrNgayKetThuc;
	private javax.swing.JFileChooser fileChoocer;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel5;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RSwitchButton.SwitchButton sbtnTrangThai;
	private com.swing.custom.raven.RTextField.RTextField txtGiamGia;
	private com.swing.custom.raven.RTextField.RTextField txtMaVoucher;
	private com.swing.custom.raven.RTextField.RTextField txtNgayBatDau;
	private com.swing.custom.raven.RTextField.RTextField txtNgayKetThuc;
	private com.swing.custom.raven.RTextField.RTextField txtSoLuong;
	// End of variables declaration//GEN-END:variables
}
