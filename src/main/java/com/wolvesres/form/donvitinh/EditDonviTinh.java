package com.wolvesres.form.donvitinh;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.form.sanpham.JDialogDonViTinh;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelDonViTinh;
import java.awt.Color;
import javax.swing.JFrame;

public class EditDonviTinh extends javax.swing.JDialog {

	JFrame frame;
	private boolean insert = true;
	private boolean dispose = true;
	private ModelDonViTinh dvt = null;
	private JDialogDonViTinh formParent = new JDialogDonViTinh(frame, true);

	public EditDonviTinh(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		this.frame = (JFrame) parent;
		setLocationRelativeTo(null);
	}

	public ModelDonViTinh getDonViTinh() {
		return this.dvt;
	}

	public void setDonViTinh(ModelDonViTinh entity) {
		this.dvt = entity;
	}

	//
	public void setInsert(boolean isInsert) {
		this.insert = isInsert;
	}

	//
	public ModelDonViTinh getForm() {
		ModelDonViTinh entity = new ModelDonViTinh();
		entity.setTenDVT(txtTenDVT.getText().trim());
		if (!lblMaDVT.getText().trim().isEmpty()) {
			entity.setMaDVT(Integer.parseInt(lblMaDVT.getText().trim()));
		}
		return entity;
	}

	//
	public void setForm() {
		lblMaDVT.setText(this.dvt.getMaDVT() + "");
		txtTenDVT.setText(this.dvt.getTenDVT());
	}

	//
	public void clearForm() {
		txtTenDVT.setText("");
	}

	//
	public boolean isDispose() {
		return this.dispose;
	}

	public boolean valideForm() {
		String DVT = txtTenDVT.getText().trim();
		int ma = 0;
		//
//        try {
//            ma = Integer.parseInt(lblMaDVT.getText().trim());
//        } catch (Exception e) {
//        }
		//
		if (!FormValidator.isTextIsNotEmpty(DVT)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ các trường thông tin!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		for (int i = 0; i < formParent.getList().size(); i++) {
			if (insert) {
				if (formParent.getList().get(i).getTenDVT().equalsIgnoreCase(DVT)) {
					ROptionDialog.showAlert(frame, "Lỗi", "Đơn Vị Tính Này đã tồn tại với Má Là: " + i,
							ROptionDialog.WARNING, Color.red, Color.black);
					return false;
				}
			} else {
				if (ma == formParent.getList().get(i).getMaDVT()) {
					if (ma != formParent.getList().get(i).getMaDVT()
							&& formParent.getList().get(i).getTenDVT().equalsIgnoreCase(DVT)) {
						ROptionDialog.showAlert(frame, "Lỗi", "Đơn Vị Tính Này đã tồn tại với Má Là: " + i,
								ROptionDialog.WARNING, Color.red, Color.black);
						return false;
					}

				}
			}
		}
		this.setDonViTinh(getForm());
		return true;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		dateChooser = new com.raven.datechooser.DateChooser();
		fileChoocer = new javax.swing.JFileChooser();
		buttonGroup1 = new javax.swing.ButtonGroup();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		lblMaDVT = new javax.swing.JLabel();
		txtTenDVT = new com.swing.custom.raven.RTextField.RTextField();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnXacNhan = new com.swing.custom.raven.RButton.RButton();
		btnLamMoi = new com.swing.custom.raven.RButton.RButton();
		btnHuy = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel2 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel3 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setBackground(new java.awt.Color(209, 220, 208));
		setMaximumSize(new java.awt.Dimension(650, 400));
		setMinimumSize(new java.awt.Dimension(650, 400));

		rRoundPanel3.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(209, 220, 208), 2));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		lblMaDVT.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
		lblMaDVT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		rRoundPanel3.add(lblMaDVT, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 650, 40));

		txtTenDVT.setLabelText("Tên Đơn Vị Tính");
		rRoundPanel3.add(txtTenDVT, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, 300, -1));

		rRoundPanel1.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirmation.png"))); // NOI18N
		btnXacNhan.setText("Xác nhận");
		btnXacNhan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnXacNhan, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 10, 120, 30));

		btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
		btnLamMoi.setText("Làm mới");
		btnLamMoi.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLamMoiActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 110, 30));

		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnHuy.setText("Hủy");
		btnHuy.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});
		rRoundPanel1.add(btnHuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 90, -1));

		rRoundPanel3.add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 650, 50));

		rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel2.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("ĐƠN VỊ TÍNH");
		rRoundPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, 50));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel2.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 100, 80));

		jLabel3.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(255, 255, 255));
		jLabel3.setText("WolvesRes");
		rRoundPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

		rRoundPanel3.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 120));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				rRoundPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE,
				javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
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
		if (valideForm()) {
			dispose = false;
			dispose();
		}
	}// GEN-LAST:event_btnXacNhanActionPerformed

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
			java.util.logging.Logger.getLogger(EditDonviTinh.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EditDonviTinh.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EditDonviTinh.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EditDonviTinh.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				EditDonviTinh dialog = new EditDonviTinh(new javax.swing.JFrame(), true);
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
	private com.raven.datechooser.DateChooser dateChooser;
	private javax.swing.JFileChooser fileChoocer;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel lblMaDVT;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RTextField.RTextField txtTenDVT;
	// End of variables declaration//GEN-END:variables
}
