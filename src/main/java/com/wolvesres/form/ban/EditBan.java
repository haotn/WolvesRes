package com.wolvesres.form.ban;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.dao.KhuBanDAO;
import com.wolvesres.form.FormBan;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelKhuBan;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;

/**
 * Cac class lien quan: ModelBan, BanDAO, FormBan
 * 
 * @author Brian
 *
 */
public class EditBan extends javax.swing.JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EditBan(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setLocationRelativeTo(null);
		this.frame = (JFrame) parent;
		init();
	}

	/**
	 * Generate global variable
	 */
	private JFrame frame;
	private List<ModelKhuBan> listKhuBan = new ArrayList<>();
	private ModelBan ban = null;
	private boolean dispose = true;
	private boolean insert = true;
	private KhuBanDAO kbDAO = new KhuBanDAO();
	private BanDAO banDao = new BanDAO();
	private AutoDAO autoDAO = new AutoDAO();
	private FormBan formParent = new FormBan(frame);
	private DefaultComboBoxModel<ModelKhuBan> model = new DefaultComboBoxModel<>();

	/**
	 * init method
	 */
	public void init() {
		lblMaBan.setText(autoDAO.AuToBan());
		loadToList();
		fillToCBO();
	}

	/**
	 * Set value of this.insert
	 * 
	 * @param insert
	 */
	public void setInsert(boolean insert) {
		this.insert = insert;
	}

	/**
	 * Get value of this.insert
	 * 
	 * @return is insert
	 */
	public boolean isInsert() {
		return this.insert;
	}

	/**
	 * Get value of this.dipose
	 * 
	 * @return
	 */
	public boolean isDispose() {
		return this.dispose;
	}

	/**
	 * Load to list
	 */
	private void loadToList() {
		listKhuBan.addAll(kbDAO.selectAll());
	}

	/**
	 * Fill to combobox
	 */
	private void fillToCBO() {
		cboKhuBan.setModel(model);
		model.removeAllElements();
		for (ModelKhuBan kbBan : listKhuBan) {
			model.addElement(kbBan);
		}
	}

	/**
	 * Set value of this.ban
	 * 
	 * @param ban
	 */
	public void setBan(ModelBan ban) {
		this.ban = ban;
	}

	/**
	 * Get value of this.ban
	 * 
	 * @return
	 */
	public ModelBan getBan() {
		return this.ban;
	}

	/**
	 * Set form data
	 */
	public void setForm() {
		lblMaBan.setText(ban.getMaBan());
		txtTenBan.setText(ban.getTenBan());
		for (ModelKhuBan kbBan : listKhuBan) {
			if (kbBan.getMaKhuBan().equals(ban.getMaKhuBan())) {
				model.setSelectedItem(kbBan);
			}
		}
	}

	/**
	 * Valid form
	 * 
	 * @return is valid
	 */
	public boolean validForm() {
		if (!FormValidator.isTextIsNotEmpty(txtTenBan.getText())) {
			ROptionDialog.showAlert(frame, "Thông báo", "Tên bàn không được để trống!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		setBan(getForm());
		return true;
	}

	/**
	 * Get form data
	 * 
	 * @return ModelBan
	 */
	public ModelBan getForm() {
		ModelBan tb = new ModelBan();
		tb.setMaBan(lblMaBan.getText());
		tb.setTenBan(txtTenBan.getText().trim());
		ModelKhuBan kb = (ModelKhuBan) cboKhuBan.getSelectedItem();
		tb.setMaKhuBan(kb.getMaKhuBan());
		tb.setHoatDong(false);
		return tb;
	}

	/**
	 * Reset form data
	 */
	public void clearForm() {
		txtTenBan.setText("");
		cboKhuBan.setSelectedIndex(0);
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		txtTenBan = new com.swing.custom.raven.RTextField.RTextField();
		rRoundPanel5 = new com.swing.custom.raven.RPanel.RRoundPanel();
		lblTitle = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel2 = new javax.swing.JLabel();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnXacNhan = new com.swing.custom.raven.RButton.RButton();
		btnLamMoi = new com.swing.custom.raven.RButton.RButton();
		btnHuy = new com.swing.custom.raven.RButton.RButton();
		lblMaBan = new javax.swing.JLabel();
		cboKhuBan = new com.swing.custom.raven.RComboBox.RComboBoxSuggestion();
		jLabel3 = new javax.swing.JLabel();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowActivated(java.awt.event.WindowEvent evt) {
				formWindowActivated(evt);
			}
		});

		rRoundPanel3.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		txtTenBan.setLabelText("Tên bàn");
		rRoundPanel3.add(txtTenBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 196, 314, -1));

		rRoundPanel5.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		lblTitle.setBackground(new java.awt.Color(255, 255, 255));
		lblTitle.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		lblTitle.setForeground(new java.awt.Color(255, 255, 255));
		lblTitle.setText("Bàn");
		rRoundPanel5.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel5.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 110, 80));

		jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("WolvesRes");
		rRoundPanel5.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 120, -1));

		rRoundPanel3.add(rRoundPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 400, 120));

		rRoundPanel1.setBackground(new java.awt.Color(71, 92, 77));

		btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirmation.png"))); // NOI18N
		btnXacNhan.setText("Xác nhận");
		btnXacNhan.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});

		btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
		btnLamMoi.setText("Làm Mới");
		btnLamMoi.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnLamMoiActionPerformed(evt);
			}
		});

		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnHuy.setText("Hủy");
		btnHuy.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout rRoundPanel1Layout = new javax.swing.GroupLayout(rRoundPanel1);
		rRoundPanel1.setLayout(rRoundPanel1Layout);
		rRoundPanel1Layout.setHorizontalGroup(rRoundPanel1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(rRoundPanel1Layout.createSequentialGroup().addGap(34, 34, 34)
						.addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(27, 27, 27)
						.addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
						.addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 73,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(46, 46, 46)));
		rRoundPanel1Layout.setVerticalGroup(rRoundPanel1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(rRoundPanel1Layout.createSequentialGroup().addContainerGap()
						.addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 29,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 30,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		rRoundPanel3.add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 474, 400, -1));

		lblMaBan.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
		lblMaBan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		rRoundPanel3.add(lblMaBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 140, 300, 40));
		rRoundPanel3.add(cboKhuBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 310, 30));

		jLabel3.setForeground(new java.awt.Color(102, 102, 102));
		jLabel3.setText("Khu bàn");
		rRoundPanel3.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 290, -1, -1));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(rRoundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addComponent(rRoundPanel3, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnHuyActionPerformed
		dispose();
	}// GEN-LAST:event_btnHuyActionPerformed

	private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnXacNhanActionPerformed
		if (validForm()) {
			dispose = false;
			if (insert) {
				System.out.println("Insert");
				getBan().insert();
			} else {
				System.out.println("Update");
				getBan().update();
			}
			dispose();
		}
	}// GEN-LAST:event_btnXacNhanActionPerformed

	private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnLamMoiActionPerformed
		clearForm();
	}// GEN-LAST:event_btnLamMoiActionPerformed

	private void formWindowActivated(java.awt.event.WindowEvent evt) {// GEN-FIRST:event_formWindowActivated

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
			java.util.logging.Logger.getLogger(EditBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EditBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EditBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EditBan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				EditBan dialog = new EditBan(new javax.swing.JFrame(), true);
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
	private com.swing.custom.raven.RComboBox.RComboBoxSuggestion cboKhuBan;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel lblMaBan;
	private javax.swing.JLabel lblTitle;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel5;
	private com.swing.custom.raven.RTextField.RTextField txtTenBan;
	// End of variables declaration//GEN-END:variables
}
