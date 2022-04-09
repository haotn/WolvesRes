package com.wolvesres.form.danhmuc;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.AutoDAO;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.helper.FormValidator;
import com.wolvesres.model.ModelDanhMuc;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

public class EditDanhMuc extends javax.swing.JDialog {

	JFrame frame;

	public boolean insert = true;
	private ModelDanhMuc dm = null;
	// public int msdvt = 0;
	private boolean dispose = true;
	// private JDialogDanhMuc formParent = new JDialogDanhMuc(frame, true);

	public EditDanhMuc(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		this.frame = (JFrame) parent;
		setLocationRelativeTo(null);
		intit();
	}

	private List<ModelDanhMuc> listDM = new ArrayList<>();

	private DanhMucDAO dmdao = new DanhMucDAO();

	private AutoDAO auto = new AutoDAO();

	private void intit() {
		loadList();
		txtMaDanhMuc.setText(auto.AuToDanhMuc());
		txtMaDanhMuc.setEditable(false);
	}

	private void loadList() {
		listDM.addAll(dmdao.selectAll());
	}

	//
	//
	private ModelDanhMuc getForm() {
		ModelDanhMuc entity = new ModelDanhMuc();
		entity.setMaDanhMuc(txtMaDanhMuc.getText().trim());
		entity.setTenDanhMuc(txtTenDanhMuc.getText().trim());
		entity.setMatHang(rdoMatHang.isSelected());
		return entity;
	}

	public void setForm() {
		txtMaDanhMuc.setText(dm.getMaDanhMuc());
		txtMaDanhMuc.setEditable(false);
		txtTenDanhMuc.setText(this.dm.getTenDanhMuc());
		if (dm.isMatHang()) {
			rdoMatHang.setSelected(true);
		} else {
			rdoMonAn.setSelected(true);
		}
	}

	public ModelDanhMuc getDm() {
		return dm;
	}

	public void setDm(ModelDanhMuc dm) {
		this.dm = dm;
	}

	public void setInsert(boolean isInsert) {
		this.insert = isInsert;
	}

	public boolean isDispose() {
		return this.dispose;
	}

	private boolean valideForm() {
		String TenDM = txtTenDanhMuc.getText().trim();
		String MaDM = txtMaDanhMuc.getText().trim();
		boolean matHang = rdoMatHang.isSelected();
		if (!FormValidator.isTextIsNotEmpty(TenDM) || !FormValidator.isTextIsNotEmpty(MaDM)) {
			ROptionDialog.showAlert(frame, "Lỗi", "Vui lòng nhập đầy đủ các trường thông tin!", ROptionDialog.WARNING,
					Color.red, Color.black);
			return false;
		}
		for (int i = 0; i < listDM.size(); i++) {
			if (insert) {
				if (listDM.get(i).getMaDanhMuc().equals(MaDM)) {
					ROptionDialog.showAlert(frame, "Lỗi", "Mã Danh mục đã tồn tại!", ROptionDialog.WARNING, Color.red,
							Color.black);
					return false;
				}
				if (listDM.get(i).getTenDanhMuc().equalsIgnoreCase(TenDM) && (matHang == listDM.get(i).isMatHang())) {
					ROptionDialog.showAlert(frame, "Lỗi", "Danh Mục đã có:" + " Tên: " + listDM.get(i).getTenDanhMuc()
							+ " DVT: " + listDM.get(i).getTenLoaiHang(), ROptionDialog.WARNING, Color.red, Color.black);
					return false;
				}
			} else {
				if (MaDM.equals(listDM.get(i).getMaDanhMuc()) && listDM.get(i).getTenDanhMuc().equalsIgnoreCase(TenDM)
						&& listDM.get(i).isMatHang() == matHang) {
				} else if (listDM.get(i).getTenDanhMuc().equalsIgnoreCase(TenDM)
						&& listDM.get(i).isMatHang() == matHang) {
					ROptionDialog.showAlert(frame, "Lỗi", "Danh Mục đã có:" + " Tên: " + listDM.get(i).getTenDanhMuc()
							+ " DVT: " + listDM.get(i).getTenLoaiHang(), ROptionDialog.WARNING, Color.red, Color.black);
					return false;
				}
			}
		}
		setDm(getForm());
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
		txtTenDanhMuc = new com.swing.custom.raven.RTextField.RTextField();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnXacNhan = new com.swing.custom.raven.RButton.RButton();
		btnLamMoi = new com.swing.custom.raven.RButton.RButton();
		btnHuy = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel3 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel2 = new javax.swing.JLabel();
		txtMaDanhMuc = new com.swing.custom.raven.RTextField.RTextField();
		rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
		rdoMatHang = new com.swing.custom.raven.RRadioButton.RRadioButton();
		rdoMonAn = new com.swing.custom.raven.RRadioButton.RRadioButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setMinimumSize(new java.awt.Dimension(650, 400));

		rRoundPanel3.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		txtTenDanhMuc.setLabelText("Tên Danh Mục");
		rRoundPanel3.add(txtTenDanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 300, -1));

		rRoundPanel1.setBackground(new java.awt.Color(71, 92, 77));

		btnXacNhan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/confirmation.png"))); // NOI18N
		btnXacNhan.setText("Xác nhận");
		btnXacNhan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnXacNhanActionPerformed(evt);
			}
		});

		btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/clean.png"))); // NOI18N
		btnLamMoi.setText("Làm mới");
		btnLamMoi.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N

		btnHuy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnHuy.setText("Hủy");
		btnHuy.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnHuy.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnHuyActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout rRoundPanel1Layout = new javax.swing.GroupLayout(rRoundPanel1);
		rRoundPanel1.setLayout(rRoundPanel1Layout);
		rRoundPanel1Layout
				.setHorizontalGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(rRoundPanel1Layout.createSequentialGroup().addGap(139, 139, 139)
								.addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18)
								.addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(18, 18, 18).addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE, 85,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(130, Short.MAX_VALUE)));
		rRoundPanel1Layout.setVerticalGroup(rRoundPanel1Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rRoundPanel1Layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(rRoundPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 28,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(btnHuy, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(34, 34, 34)));

		rRoundPanel3.add(rRoundPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 650, 60));

		rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel3.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel3.setForeground(new java.awt.Color(255, 255, 255));
		jLabel3.setText("DANH MỤC");
		rRoundPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, 50));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel2.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 10, 100, 80));

		jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("WolvesRes");
		rRoundPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 90, -1, -1));

		rRoundPanel3.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 650, 120));

		txtMaDanhMuc.setLabelText("Mã danh mục");
		rRoundPanel3.add(txtMaDanhMuc, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, 230, -1));

		rRoundPanel4.setBackground(new java.awt.Color(255, 255, 255));
		rRoundPanel4.setBorder(
				javax.swing.BorderFactory.createTitledBorder(null, "Loại hàng", javax.swing.border.TitledBorder.LEFT,
						javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 13))); // NOI18N

		buttonGroup1.add(rdoMatHang);
		rdoMatHang.setSelected(true);
		rdoMatHang.setText("Mặt hàng");
		rdoMatHang.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

		buttonGroup1.add(rdoMonAn);
		rdoMonAn.setText("Món ăn");
		rdoMonAn.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N

		javax.swing.GroupLayout rRoundPanel4Layout = new javax.swing.GroupLayout(rRoundPanel4);
		rRoundPanel4.setLayout(rRoundPanel4Layout);
		rRoundPanel4Layout
				.setHorizontalGroup(rRoundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(rRoundPanel4Layout.createSequentialGroup().addContainerGap(90, Short.MAX_VALUE)
								.addComponent(rdoMatHang, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(52, 52, 52)
								.addComponent(rdoMonAn, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(36, 36, 36)));
		rRoundPanel4Layout.setVerticalGroup(rRoundPanel4Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(rRoundPanel4Layout.createSequentialGroup()
						.addGroup(rRoundPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(rdoMatHang, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(rdoMonAn, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addGap(0, 0, Short.MAX_VALUE)));

		rRoundPanel3.add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 340, 60));

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
		if (valideForm()) {
			dispose = false;
			dispose();
		}
	}// GEN-LAST:event_btnXacNhanActionPerformed

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
			java.util.logging.Logger.getLogger(EditDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(EditDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(EditDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(EditDanhMuc.class.getName()).log(java.util.logging.Level.SEVERE, null,
					ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				EditDanhMuc dialog = new EditDanhMuc(new javax.swing.JFrame(), true);
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
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
	private com.swing.custom.raven.RRadioButton.RRadioButton rdoMatHang;
	private com.swing.custom.raven.RRadioButton.RRadioButton rdoMonAn;
	private com.swing.custom.raven.RTextField.RTextField txtMaDanhMuc;
	private com.swing.custom.raven.RTextField.RTextField txtTenDanhMuc;
	// End of variables declaration//GEN-END:variables
}
