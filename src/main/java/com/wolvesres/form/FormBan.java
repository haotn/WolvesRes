package com.wolvesres.form;

import com.swing.custom.raven.RDialog.ROptionDialog;
import com.swing.custom.raven.RScrollbar.RScrollBarCustom;
import com.wolvesres.component.Ban;
import com.wolvesres.dao.BanDAO;
import com.wolvesres.dao.BanOrderDAO;
import com.wolvesres.dao.KhuBanDAO;
import com.wolvesres.form.ban.EditBan;
import com.wolvesres.form.ban.KhuBan;
import com.wolvesres.form.ban.jDialogChuyenBan;
import com.wolvesres.form.ban.jDialogGopBan;
import com.wolvesres.helper.Auth;
import com.wolvesres.main.Main;
import com.wolvesres.model.ModelBan;
import com.wolvesres.model.ModelBanOrder;
import com.wolvesres.model.ModelKhuBan;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;

/**
 * Cac class lien quan: ModelBan, BanDAO, EditBan
 * 
 * @author Brian
 *
 */
public class FormBan extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame frame;

	/**
	 * SubHeader Color [0,193,127]
	 *
	 * @param frame
	 */
	public FormBan(JFrame frame) {
		initComponents();
		this.frame = frame;
		setOpaque(false);
		init();
	}

	/**
	 * Generate global variable
	 */
	private Main main = new Main();
	private List<ModelBan> listBan = new ArrayList<ModelBan>();
	private List<ModelKhuBan> listKhuBan = new ArrayList<ModelKhuBan>();
	private List<Ban> listBanComp = new ArrayList<Ban>();
	private BanDAO banDAO = new BanDAO();
	private KhuBanDAO khuBanDAO = new KhuBanDAO();
	private ModelBan entity = new ModelBan();
	private ActionListener eventOder;
	private BanOrderDAO banOrderDAO = new BanOrderDAO();
	private MigLayout layoutPanel;
	private DefaultComboBoxModel<ModelKhuBan> modelCboKhuBan = new DefaultComboBoxModel<ModelKhuBan>();

	public void init() {
		jScrollPane1.setViewportView(pnlBan);
		jScrollPane1.setVerticalScrollBar(new RScrollBarCustom());
		loadList();
		if (!Auth.isBoss() && !Auth.isManager()) {
			btnAdd.setVisible(false);
			btnKhuBan.setVisible(false);
		}
	}

	private void loadList() {
		listBan.addAll(banDAO.selectAll());
		loadToListBanComp();
		fillCboKhuBan();
		ModelKhuBan khuBan = listKhuBan.get(0);
		fillPanelBan(khuBan);
	}

	/**
	 * Fill combobox
	 */
	private void fillCboKhuBan() {
		listKhuBan.addAll(khuBanDAO.selectAll());
		modelCboKhuBan.removeAllElements();
		cboKhuBan.setModel(modelCboKhuBan);
		for (ModelKhuBan modelKhuBan : listKhuBan) {
			modelCboKhuBan.addElement(modelKhuBan);
		}
	}

	/**
	 * Get BanOrder by maBan
	 * 
	 * @param maban
	 * @return ModelBanOrder
	 */
	private ModelBanOrder getBanOrderByMaBan(String maban) {
		ModelBanOrder banOrder = new ModelBanOrder();
		for (ModelBanOrder modelBanOrder : Main.listBanOrder) {
			if (modelBanOrder.getMaBan().equals(maban)) {
				banOrder = modelBanOrder;
			}
		}
		return banOrder;
	}

	private ModelBan getBanByMaBan(String maBan) {
		ModelBan ban = new ModelBan();
		for (ModelBan modelBan : listBan) {
			if (modelBan.getMaBan().equals(maBan)) {
				ban = modelBan;
			}
		}
		return ban;
	}

	private void loadToListBanComp() {
		listBanComp.clear();
		for (int i = 0; i < listBan.size(); i++) {
			final ModelBan ban = listBan.get(i);
			Ban banComp = new Ban();
			banComp = new Ban(ban);
			boolean active = false;
			for (int k = 0; k < Main.listBanOrder.size(); k++) {
				if (ban.getMaBan().equals(Main.listBanOrder.get(k).getMaBan())
						|| ban.getMaBan().equals(Main.listBanOrder.get(k).getMaBanGop())) {
					if (ban.getMaBan().equals(Main.listBanOrder.get(k).getMaBan())
							|| ban.getMaBan().equals(Main.listBanOrder.get(k).getMaBanGop())) {
						active = true;
					}
				}
			}
			banComp.setTrangThai(active);

			banComp.setTenBan(ban.getTenBan());
			banComp.setEventOrder(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setBan(ban);
					eventOder.actionPerformed(e);
				}
			});
			banComp.setEventUpdate(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					EditBan editForm = new EditBan(frame, true);
					editForm.setBan(ban);
					editForm.setForm();
					editForm.setInsert(false);
					editForm.setVisible(true);
					if (!editForm.isDispose()) {
						updateTableToList(editForm.getBan());
						ModelKhuBan khuBan = (ModelKhuBan) cboKhuBan.getSelectedItem();
						fillPanelBan(khuBan);
					}
				}
			});
			banComp.setEventDelete(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (banDAO.checkForeignKey(ban).getMaBan() == null) {
						if (ROptionDialog.showConfirm(frame, "Xác nhận", "Xác nhận xóa bàn?", ROptionDialog.WARNING,
								Color.RED, Color.black)) {
							deleteTableFromList(ban);
							ModelKhuBan khuBan = (ModelKhuBan) cboKhuBan.getSelectedItem();
							fillPanelBan(khuBan);
						}
					} else {
						ROptionDialog.showAlert(frame, "Thông báo", "Không thể xóa bàn này!",
								ROptionDialog.NOTIFICATIONS, Color.blue, Color.black);
					}
				}
			});
			listBanComp.add(banComp);
		}
	}

	/**
	 * Get value of this.entity
	 * 
	 * @return ModelBan
	 */
	public ModelBan getBan() {
		return this.entity;
	}

	/**
	 * Set value for this.entity
	 * 
	 * @param ban
	 */
	public void setBan(ModelBan ban) {
		this.entity = ban;
	}

	/**
	 * Set Action Order
	 * 
	 * @param action
	 */
	public void setActionOrder(ActionListener action) {
		this.eventOder = action;
	}

	/**
	 * Get value of listBan
	 * 
	 * @return
	 */
	public List<ModelBan> getListBan() {
		return listBan;
	}

	/**
	 * Get list ban component by ma khu ban
	 * 
	 * @param maKhuBan
	 * @return List<Ban>
	 */
	private List<Ban> getListBanCompByMaKhuBan(String maKhuBan) {
		List<Ban> list = new ArrayList<Ban>();
		for (Ban ban : listBanComp) {
			if (ban.getEntity().getMaKhuBan().equals(maKhuBan)) {
				list.add(ban);
			}
		}
		return list;
	}

	/**
	 * Fill panel Ban
	 * 
	 * @param khuBan
	 */
	private void fillPanelBan(ModelKhuBan khuBan) {
		loadToListBanComp();
		pnlBan.setLayout(null);
		pnlBan.removeAll();
		pnlBan.revalidate();
		pnlBan.repaint();
		List<Ban> list = getListBanCompByMaKhuBan(khuBan.getMaKhuBan());
		int x = 5;
		int y = 10;
		int width = 280;
		int height = 190;
		int yShift = 200;
		int xShift = 290;
		int rowCount = list.size() / 4;
		int du = list.size() % 4;
		if (du > 0) {
			rowCount += 1;
		}
		int indexComponent = 0;
		int pnlHeight = rowCount * yShift;
		pnlBan.setPreferredSize(new Dimension(1150, pnlHeight));
		for (int row = 0; row < rowCount; row++) {
			for (int col = 0; col < 4; col++) {
				pnlBan.add(list.get(indexComponent));
				list.get(indexComponent).setBounds(x, y, width, height);
				x += xShift;
				if (indexComponent == (list.size() - 1)) {
					break;
				} else {
					indexComponent++;
				}
			}
			x = 5;
			y += yShift;
		}
	}

	/**
	 * InsertTable
	 * 
	 * @param ban
	 */
	public void addToList(ModelBan ban) {
		listBan.add(ban);
//		ModelKhuBan khuBan = (ModelKhuBan) cboKhuBan.getSelectedItem();
//		fillPanelBan(khuBan);
	}

	/**
	 * Update to list
	 * 
	 * @param entity
	 */
	public void updateTableToList(ModelBan entity) {
		for (int i = 0; i < listBan.size(); i++) {
			if (listBan.get(i).getMaBan().equals(entity.getMaBan())) {
				listBan.set(i, entity);
				break;
			}
		}
	}

	/**
	 * Delete table from list
	 * 
	 * @param entity
	 */
	public void deleteTableFromList(ModelBan entity) {
		listBan.remove(entity);
//		ModelKhuBan khuBan = (ModelKhuBan) cboKhuBan.getSelectedItem();
//		fillPanelBan(khuBan);
	}

	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed" desc="Generated
	// Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jScrollPane1 = new javax.swing.JScrollPane();
		pnlBan = new com.swing.custom.raven.RPanel.RRoundPanel();
		btnGopBan = new com.swing.custom.raven.RButton.RButton();
		btnChuyenBan = new com.swing.custom.raven.RButton.RButton();
		btnKhuBan = new com.swing.custom.raven.RButton.RButton();
		btnAdd = new com.swing.custom.raven.RButton.RButton();
		rRoundPanel4 = new com.swing.custom.raven.RPanel.RRoundPanel();
		jLabel1 = new javax.swing.JLabel();
		rImageAvatar1 = new com.swing.custom.raven.RImageAvatar.RImageAvatar();
		lblTitle = new javax.swing.JLabel();
		cboKhuBan = new com.swing.custom.raven.RComboBox.RComboBoxSuggestion();
		jLabel2 = new javax.swing.JLabel();
		lblTenKhuBan = new javax.swing.JLabel();

		setBackground(new java.awt.Color(6, 7, 13));
		setPreferredSize(new java.awt.Dimension(1170, 730));
		setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jScrollPane1.setBackground(new java.awt.Color(51, 51, 51));
		jScrollPane1.setBorder(null);

		pnlBan.setBackground(new java.awt.Color(255, 255, 255));
		pnlBan.setPreferredSize(new java.awt.Dimension(1130, 100));
		pnlBan.setLayout(null);
		jScrollPane1.setViewportView(pnlBan);

		add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1170, 490));

		btnGopBan.setForeground(new java.awt.Color(0, 0, 0));
		btnGopBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/merge.png"))); // NOI18N
		btnGopBan.setText("Gộp bàn");
		btnGopBan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnGopBan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnGopBanActionPerformed(evt);
			}
		});
		add(btnGopBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 670, 150, 40));

		btnChuyenBan.setForeground(new java.awt.Color(0, 0, 0));
		btnChuyenBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/move.png"))); // NOI18N
		btnChuyenBan.setText("Chuyển bàn");
		btnChuyenBan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnChuyenBan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnChuyenBanActionPerformed(evt);
			}
		});
		add(btnChuyenBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 670, 170, 40));

		btnKhuBan.setForeground(new java.awt.Color(0, 0, 0));
		btnKhuBan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/square.png"))); // NOI18N
		btnKhuBan.setText("Khu bàn");
		btnKhuBan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnKhuBan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnKhuBanActionPerformed(evt);
			}
		});
		add(btnKhuBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 670, 130, 40));

		btnAdd.setForeground(new java.awt.Color(0, 0, 0));
		btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/addition.png"))); // NOI18N
		btnAdd.setText("Thêm bàn");
		btnAdd.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		btnAdd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btnAddActionPerformed(evt);
			}
		});
		add(btnAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 670, 140, 40));

		rRoundPanel4.setBackground(new java.awt.Color(0, 199, 135));
		rRoundPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
		jLabel1.setForeground(new java.awt.Color(255, 255, 255));
		jLabel1.setText("WolvesRes");
		rRoundPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 40, -1, -1));

		rImageAvatar1.setIcon(
				new javax.swing.ImageIcon(getClass().getResource("/com/wolvesres/icon/ThreeWolvesResLogo.png"))); // NOI18N
		rRoundPanel4.add(rImageAvatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 0, 120, 110));

		lblTitle.setBackground(new java.awt.Color(255, 255, 255));
		lblTitle.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
		lblTitle.setForeground(new java.awt.Color(255, 255, 255));
		lblTitle.setText("BÀN");
		rRoundPanel4.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 7, 110, 100));

		add(rRoundPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 110));

		cboKhuBan.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cboKhuBanActionPerformed(evt);
			}
		});
		add(cboKhuBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 120, 280, -1));

		jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
		jLabel2.setForeground(new java.awt.Color(255, 255, 255));
		jLabel2.setText("Khu Bàn");
		add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 120, -1, -1));

		lblTenKhuBan.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
		lblTenKhuBan.setForeground(new java.awt.Color(255, 255, 255));
		lblTenKhuBan.setText("Khu Bàn");
		add(lblTenKhuBan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 390, -1));
	}// </editor-fold>//GEN-END:initComponents

	private void btnKhuBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnKhuBanActionPerformed
		KhuBan khuBan = new KhuBan(frame, true);
		khuBan.setVisible(true);
		listKhuBan.clear();
		listKhuBan.addAll(khuBan.getListReturn());
		fillCboKhuBan();
	}// GEN-LAST:event_btnKhuBanActionPerformed

	private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddActionPerformed
		EditBan editForm = new EditBan(frame, true);
		editForm.setVisible(true);
		if (!editForm.isDispose()) {
			addToList(editForm.getBan());
			ModelKhuBan khuBan = (ModelKhuBan) cboKhuBan.getSelectedItem();
			fillPanelBan(khuBan);
		}
	}// GEN-LAST:event_btnAddActionPerformed

	private void btnGopBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGopBanActionPerformed
		jDialogGopBan gopban = new jDialogGopBan(main, true);
		gopban.setVisible(true);
		Main.listBanOrder.clear();
		Main.listBanOrder = gopban.getListBanOder();
		ModelKhuBan khuBan = (ModelKhuBan) cboKhuBan.getSelectedItem();
		fillPanelBan(khuBan);
	}// GEN-LAST:event_btnGopBanActionPerformed

	private void btnChuyenBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChuyenBanActionPerformed
		jDialogChuyenBan chuyenBan = new jDialogChuyenBan(main, true);
		chuyenBan.setVisible(true);
		if (!chuyenBan.isDispose()) {
			ModelBan banFrom = chuyenBan.getBanFrom();
			ModelBan banTo = chuyenBan.getBanTo();
			banFrom.setHoatDong(false);
			banTo.setHoatDong(true);
			banOrderDAO.updateChuyenBan(banFrom, banTo.getMaBan());
			Main.listBanOrder.clear();
			Main.listBanOrder.addAll(Main.banOrderDAO.selectAll());
			if (Main.listBanOrder.size() > 0) {
				Main.banOrderGlobal = Main.listBanOrder.get(0);
			}
			ModelKhuBan khuBan = (ModelKhuBan) cboKhuBan.getSelectedItem();
			fillPanelBan(khuBan);
		}
	}// GEN-LAST:event_btnChuyenBanActionPerformed

	private void cboKhuBanActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cboKhuBanActionPerformed
		ModelKhuBan khuBan = (ModelKhuBan) cboKhuBan.getSelectedItem();
		if (khuBan != null) {
			lblTenKhuBan.setText(khuBan.getTenKhuBan());
			fillPanelBan(khuBan);
		}

	}// GEN-LAST:event_cboKhuBanActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private com.swing.custom.raven.RButton.RButton btnAdd;
	private com.swing.custom.raven.RButton.RButton btnChuyenBan;
	private com.swing.custom.raven.RButton.RButton btnGopBan;
	private com.swing.custom.raven.RButton.RButton btnKhuBan;
	private com.swing.custom.raven.RComboBox.RComboBoxSuggestion cboKhuBan;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel lblTenKhuBan;
	private javax.swing.JLabel lblTitle;
	private com.swing.custom.raven.RPanel.RRoundPanel pnlBan;
	private com.swing.custom.raven.RImageAvatar.RImageAvatar rImageAvatar1;
	private com.swing.custom.raven.RPanel.RRoundPanel rRoundPanel4;
	// End of variables declaration//GEN-END:variables
}
