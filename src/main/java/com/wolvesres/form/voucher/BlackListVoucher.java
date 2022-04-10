package com.wolvesres.form.voucher;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.VoucherDAO;
import com.wolvesres.form.FormVoucher;
import com.wolvesres.model.ModelVouCher;
import com.wolvesres.swing.table.EventActionBlackList;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

/**
 * Cac class lien quan FormVoucher, ModelVouCher, EditVoucher, VoucherDAO,
 * CanVoucher
 * 
 * @author Brian
 *
 */
public class BlackListVoucher extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * 
	 * @param parent
	 * @param modal
	 */
	public BlackListVoucher(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setLocationRelativeTo(null);
		init();
		this.frame = (JFrame) parent;
	}

	/**
	 * Generate global variable
	 */
	private JFrame frame;
	private DefaultTableModel model;
	private List<ModelVouCher> blackList = new ArrayList<>();
	private List<ModelVouCher> listReturn = new ArrayList<>();
	private VoucherDAO dao = new VoucherDAO();
	private FormVoucher formParent = new FormVoucher(frame);
	private boolean changeData = false;
	private int index = -1;
	/**
	 * Event action on table
	 */
	private EventActionBlackList<ModelVouCher> eventAction = new EventActionBlackList<ModelVouCher>() {
		@Override
		public void update(ModelVouCher voucher) {
			if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc muốn đưa nhân viên khỏi danh sách đen không?",
					ROptionDialog.WARNING, Color.yellow, Color.black)) {
				changeData = true;
				updateVoucher(voucher);
				listReturn.add(voucher);
				fillToTable();
			}
		}
	};

	/**
	 * Init method
	 */
	private void init() {
		initTable();
		fillToTable();
	}

	/**
	 * Update Voucher to database, set trangthai=true
	 * 
	 * @param vc
	 */
	private void updateVoucher(ModelVouCher vc) {
		vc.addToWhiteList();
	}

	/**
	 * Update voucher to list
	 * 
	 * @param entity
	 */
	public void updateToList(ModelVouCher entity) {
		for (int i = 0; i < formParent.getList().size(); i++) {
			if (formParent.getList().get(i).getMaVoucher().equals(entity.getMaVoucher())) {
				formParent.getList().set(i, entity);
				break;
			}
		}
	}

	/**
	 * Get value of this.changeData
	 * 
	 * @return
	 */

	public boolean isChangeData() {
		return this.changeData;
	}

	/**
	 * Load voucher to black list
	 */
	private void loadToBlackList() {
		blackList.clear();
		for (ModelVouCher vc : formParent.getList()) {
			if (!vc.isTrangThai()) {
				blackList.add(vc);
			}
		}
	}

	/***
	 * Get list will return to form parent
	 * 
	 * @return
	 */
	public List<ModelVouCher> getListReturn() {
		return this.listReturn;
	}

	/**
	 * Fill to table
	 */
	private void fillToTable() {
		model.setRowCount(0);
		loadToBlackList();
		for (ModelVouCher vc : blackList) {
			tblBlackList.addRow(vc.toRowTableBlackList(eventAction));
		}
	}

	/**
	 * Init table
	 */
	private void initTable() {
		tblBlackList.setOpaque(true);
		tblBlackList.setBackground(new Color(255, 255, 255));
		tblBlackList.setFillsViewportHeight(true);
		tblBlackList.fixTable(jScrollPane1);
		model = new DefaultTableModel(new Object[][] {},
				new Object[] { "Mã Voucher", "Giảm giá", "Số lượng", "Thao tác" });
		tblBlackList.setModel(model);
		tblBlackList.setColumnAction(3);
		tblBlackList.setActionWhiteList(false);
	}

	/**
	 * Get voucher from row table
	 * 
	 * @param row
	 * @return ModelVouCher
	 */
	private ModelVouCher getVoucherFromRowTable(int row) {
		ModelVouCher vc = null;
		if (row >= 0) {
			String maVC = String.valueOf(tblBlackList.getValueAt(tblBlackList.getSelectedRow(), 0));
			for (int i = 0; i < blackList.size(); i++) {
				if (maVC.equals(blackList.get(i).getMaVoucher())) {
					vc = blackList.get(i);
				}
			}
		}
		return vc;
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		btnDuaRaKBL = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblBlackList = new com.wolvesres.swing.table.Table();
		txtFindBlackList = new com.swing.custom.raven.RTextField.RTextField();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel1 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel5 = new javax.swing.JLabel();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnDong = new com.swing.custom.raven.RButton.RButton();

		btnDuaRaKBL.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/back.png"))); // NOI18N
		btnDuaRaKBL.setText("Đưa ra khỏi danh sách đen");
		btnDuaRaKBL.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnDuaRaKBL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDuaRaKBLActionPerformed(evt);
			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Danh sách đen");
		setMinimumSize(new java.awt.Dimension(720, 710));

		rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(209, 220, 208), 2));
		rRoundPanel1.setMaximumSize(new java.awt.Dimension(720, 710));
		rRoundPanel1.setOpaque(true);
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		tblBlackList.setAutoCreateRowSorter(true);
		tblBlackList
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		tblBlackList.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				tblBlackListMousePressed(evt);
			}
		});
		jScrollPane1.setViewportView(tblBlackList);

		rRoundPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 700, 490));

		txtFindBlackList.setLabelText("Tìm kiếm");
		rRoundPanel1.add(txtFindBlackList, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 700, -1));

		rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("DANH SÁCH NGƯNG HOẠT ĐỘNG");
		rRoundPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, 50));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel2.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 10, 100, 80));

		jLabel5.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel5.setForeground(new java.awt.Color(255, 255, 255));
		jLabel5.setText("WolvesRes");
		rRoundPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 90, -1, -1));

		rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 720, 120));

		rRoundPanel3.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		btnDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/huy.png"))); // NOI18N
		btnDong.setText("Đóng");
		btnDong.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnDong.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnDongActionPerformed(evt);
			}
		});
		rRoundPanel3.add(btnDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 10, 90, -1));

		rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 720, 50));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addComponent(rRoundPanel1,
						javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(0, 0, Short.MAX_VALUE)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rRoundPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	private void btnDongActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDongActionPerformed
		dispose();
	}// GEN-LAST:event_btnDongActionPerformed

	private void btnDuaRaKBLActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnDuaRaKBLActionPerformed

	}// GEN-LAST:event_btnDuaRaKBLActionPerformed

	private void tblBlackListMousePressed(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_tblBlackListMousePressed
		if (index >= 0) {
			if (getVoucherFromRowTable(index).getSoLuong() > 0) {
				btnDuaRaKBL.setEnabled(true);
			} else {
				btnDuaRaKBL.setEnabled(false);
			}
		}
	}// GEN-LAST:event_tblBlackListMousePressed

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
			java.util.logging.Logger.getLogger(BlackListVoucher.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(BlackListVoucher.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(BlackListVoucher.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(BlackListVoucher.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				BlackListVoucher dialog = new BlackListVoucher(new javax.swing.JFrame(), true);
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
	private com.swing.custom.raven.RButton.RButton btnDong;
	private com.swing.custom.raven.RButton.RButton btnDuaRaKBL;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JScrollPane jScrollPane1;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.wolvesres.swing.table.Table tblBlackList;
	private com.swing.custom.raven.RTextField.RTextField txtFindBlackList;
	// End of variables declaration//GEN-END:variables
}
