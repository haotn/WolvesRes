package com.wolvesres.form.sanpham;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.wolvesres.dao.DanhMucDAO;
import com.wolvesres.dao.SanPhamDAO;
import com.wolvesres.form.FormSanPham;
import com.wolvesres.helper.XImage;
import com.wolvesres.model.ModelDanhMuc;
import com.wolvesres.model.ModelSanPham;
import com.wolvesres.swing.table.EventActionBlackList;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
/**
 * Chỉnh sửa tìm kiếm, comment các hàm
 * Liên quan: ModelSanPhan
 * @author huynh
 *
 */
public class BlackListSanPham extends javax.swing.JDialog {

	public BlackListSanPham(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		setLocationRelativeTo(null);
		txtFindBlackList.setLabelText("Tìm kiếm trong danh sách đen");
		this.frame = (JFrame) parent;
		init();
	}

	/**
	 * hàm load các hàm bên dưới
	 */
	public void init() {
		initTable();
		loadToList();
		danhMucDAO.loadToBlackList(this);
		fillToTable();
	}

	JFrame frame;
	DefaultTableModel model;
	private boolean isChangeData = false;
	private List<ModelSanPham> listReturn = new ArrayList<>();
	private List<ModelDanhMuc> listDanhMuc = new ArrayList<>();
	public List<ModelSanPham> listSP = new ArrayList<>();
	SanPhamDAO daoSP = new SanPhamDAO();
	public FormSanPham formSP = new FormSanPham(frame);
	DanhMucDAO danhMucDAO = new DanhMucDAO();
	/**
	 * nút sửa xóa trên form
	 */
	EventActionBlackList<ModelSanPham> eventAction = new EventActionBlackList<ModelSanPham>() {
		@Override
		public void update(ModelSanPham sanpham) {
			if (ROptionDialog.showConfirm(frame, "Xác nhận", "Bạn có chắc chắn muốn đưa ra khỏi danh sách đen không",
					ROptionDialog.WARNING, Color.yellow, Color.black)) {
				isChangeData = true;
				sanpham.addToBlackList(formSP.getList());
				fillToTable();
				addToListReturn(sanpham);
			}
		}
	};

	/**
	 * Hàm desigs bảng
	 */
	public void initTable() {
		tblBlackListSP.setOpaque(true);
		tblBlackListSP.setBackground(new Color(255, 255, 255));
		tblBlackListSP.setFillsViewportHeight(true);
		tblBlackListSP.fixTable(jScrollPane1);
		model = new DefaultTableModel(new Object[][] {},
				new Object[] { "Tên sản phẩm", "Danh mục", "Giá bán", "Loại", "Thao tác" });
		tblBlackListSP.setModel(model);
		tblBlackListSP.setColumnAction(4);
		tblBlackListSP.setActionWhiteList(false);
	}

	/**
	 * Hàm load dữ liệu
	 */
	private void loadToList() {
		listDanhMuc.addAll(danhMucDAO.selectAll());
	}

	/**
	 * Hàm return bảng dữ liệu
	 */
	private void addToListReturn(ModelSanPham entity) {
		listReturn.add(entity);
	}

	public List<ModelSanPham> getListReturn() {
		return this.listReturn;
	}

	/**
	 * Hàm fill dữ liệu trên bảng
	 */
	public void fillToTable() {
		danhMucDAO.loadToBlackList(this);
		model.setRowCount(0);
		for (int i = 0; i < listSP.size(); i++) {
			if (listSP.get(i).getPathAnh() != null) {
				listSP.get(i).setIcon(XImage.readImageThucDon(listSP.get(i).getPathAnh()));
			} else {
				listSP.get(i).setIcon(null);
			}
			tblBlackListSP.addRow(listSP.get(i).toRowTableBlackList(eventAction, listDanhMuc));
		}
	}

	public boolean getChangeData() {
		return this.isChangeData;
	}
	
	/**
	 * Tìm thoe tên sản phẩm
	 * @param keyword
	 * @return
	 */
	 public List<ModelSanPham> timkiem(String keyword){
	        List<ModelSanPham> listFind = new ArrayList<>();
	        	if(keyword.trim().length() > 0) {
	        		listFind = daoSP.timkiem(keyword);
	        	}else {
	        		listFind = daoSP.selectAll();
	        	}
	        return listFind;
	    }

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		btnDuaRaKBL = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel1 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		tblBlackListSP = new com.wolvesres.swing.table.Table();
		rRoundPanel2 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel1 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		jLabel2 = new javax.swing.JLabel();
		rRoundPanel3 = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnDong = new com.swing.custom.raven.RButton.RButton();
		txtFindBlackList = new com.swing.custom.raven.RTextField.RTextField();

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
		setMinimumSize(new java.awt.Dimension(720, 730));

		rRoundPanel1.setBackground(new java.awt.Color(209, 220, 208));
		rRoundPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(209, 220, 208), 2));
		rRoundPanel1.setOpaque(true);
		rRoundPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		tblBlackListSP.setAutoCreateRowSorter(true);
		tblBlackListSP
				.setModel(new javax.swing.table.DefaultTableModel(
						new Object[][] { { null, null, null, null }, { null, null, null, null },
								{ null, null, null, null }, { null, null, null, null } },
						new String[] { "Title 1", "Title 2", "Title 3", "Title 4" }));
		jScrollPane1.setViewportView(tblBlackListSP);

		rRoundPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 730, 490));

		rRoundPanel2.setBackground(new java.awt.Color(71, 92, 77));
		rRoundPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("DANH SÁCH NGỪNG KINH DOANH");
		rRoundPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, 40));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel2.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 10, 100, 80));

		jLabel2.setFont(new java.awt.Font("Showcard Gothic", 1, 18)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("WolvesRes");
		rRoundPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 90, -1, -1));

		rRoundPanel1.add(rRoundPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 730, 123));

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
		rRoundPanel3.add(btnDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 90, -1));

		rRoundPanel1.add(rRoundPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 730, 50));

		txtFindBlackList.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
		txtFindBlackList.setLabelText("Tìm kiếm sản phẩm trong danh sách đen");
		txtFindBlackList.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyReleased(java.awt.event.KeyEvent evt) {
				txtFindBlackListKeyReleased(evt);
			}
		});
		rRoundPanel1.add(txtFindBlackList, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 730, 50));

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(rRoundPanel1,
						javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
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

	private void txtFindBlackListKeyReleased(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_txtFindBlackListKeyReleased
		String keyword = txtFindBlackList.getText().trim();
    	listSP = timkiem(keyword);
		fillToTable();
	}// GEN-LAST:event_txtFindBlackListKeyReleased

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
			java.util.logging.Logger.getLogger(BlackListSanPham.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(BlackListSanPham.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(BlackListSanPham.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(BlackListSanPham.class.getName()).log(java.util.logging.Level.SEVERE,
					null, ex);
		}
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>
		// </editor-fold>

		/* Create and display the dialog */
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				BlackListSanPham dialog = new BlackListSanPham(new javax.swing.JFrame(), true);
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
	private javax.swing.JLabel jLabel2;
	private javax.swing.JScrollPane jScrollPane1;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel2;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel3;
	private com.wolvesres.swing.table.Table tblBlackListSP;
	private com.swing.custom.raven.RTextField.RTextField txtFindBlackList;
	// End of variables declaration//GEN-END:variables
}
